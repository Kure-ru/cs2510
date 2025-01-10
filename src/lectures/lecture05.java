package lectures;
import tester.Tester;

class Book5 {
    String title;
    String author;
    int price;
    int year;

    Book5(String title, String author, int price, int year){
        this.title = title;
        this.author = author;
        this.price = price;
        this.year = year;
    }

    boolean publishedBefore(int year){
        return this.year < year;
    }

    double salePrice(int discount){
        return this.price - (this.price * discount / 100.0);
    }

    boolean cheaperThan(Book5 that){
        return this.price < that.price;
    }

    boolean titleBefore(Book5 that){
        return this.title.compareTo(that.title) < 0;
    }

}

interface ILoBook{
    int count();
    int totalPrice();
    ILoBook cheaperThan(int price);
    ILoBook allBefore(int year);
    double salePrice(int discount);
    ILoBook sortByPrice();
    ILoBook sortByTitle();
    ILoBook insert(Book5 b);
    ILoBook insertLexically(Book5 b);
}

class MtLoBook implements ILoBook {
    MtLoBook(){}

    public int count() {
        return 0;
    }

    public int totalPrice(){
        return 0;
    }

    public ILoBook cheaperThan(int price){
        return this;
    }

    public ILoBook allBefore(int year){
        return this;
    }

    public double salePrice(int discount){
        return 0;
    }

    public ILoBook sortByPrice() {
        return this;
    }

    public ILoBook sortByTitle() {
        return this;
    }

    public ILoBook insert(Book5 b){
        return new ConsLoBook(b, this);
    }

    public ILoBook insertLexically(Book5 b){
        return new ConsLoBook(b, this);
    }
}

class ConsLoBook implements ILoBook {
    Book5 first;
    ILoBook rest;

    ConsLoBook(Book5 first, ILoBook rest){
        this.first = first;
        this.rest = rest;
    }

    public int count() {
        return 1 + this.rest.count();
    }

    public int totalPrice(){
        return this.first.price + this.rest.totalPrice();
    }

    public ILoBook cheaperThan(int price){
        if (this.first.price < price){
            return new ConsLoBook(this.first, this.rest.cheaperThan(price));
        } else {
            return this.rest.cheaperThan(price);
        }
    }

    public ILoBook allBefore(int year){
        if (this.first.publishedBefore(year)){
            return new ConsLoBook(this.first, this.rest.allBefore(year));
        } else {
            return this.rest.allBefore(year);
        }
    }

    public double salePrice(int discount) {
        return this.first.salePrice(discount) + this.rest.salePrice(discount);
    }

    public ILoBook sortByPrice(){
        return this.rest.sortByPrice()
                .insert(this.first);
    }

    public ILoBook insert(Book5 b){
        if (b.cheaperThan(this.first)){
            return new ConsLoBook(b, this);
        } else {
            return new ConsLoBook(this.first, this.rest.insert(b));
        }
    }

    public ILoBook insertLexically(Book5 b){
        if (b.titleBefore(this.first)){
            return new ConsLoBook(b, this);
        } else {
            return new ConsLoBook(this.first, this.rest.insert(b));
        }
    }

    public ILoBook sortByTitle() {
        return this.rest.sortByTitle()
                .insertLexically(this.first);
    }
}

class ILoBooksExample {
    ILoBooksExample() {}

    Book5 hp1 = new Book5("HP1", "JKR", 20, 2000);
    Book5 hp2 = new Book5("HP2", "JKR", 30, 2002);
    Book5 hp3 = new Book5("HP3", "JKR", 40, 2005);

    ILoBook hpList3 = new ConsLoBook(hp1, new ConsLoBook(hp2, new ConsLoBook(hp3, new MtLoBook())));
    ILoBook hpList1 = new ConsLoBook(hp1, new MtLoBook());
    ILoBook hpList4 = new ConsLoBook(hp1, hpList3);
    ILoBook emptyList = new MtLoBook();
    ILoBook sortedList = new ConsLoBook(hp1, new ConsLoBook(hp2, new ConsLoBook(hp3, new MtLoBook())));

    boolean testILoBookCount(Tester t) {
        return t.checkExpect(hpList3.count(), 3)
                && t.checkExpect(emptyList.count(), 0)
                && t.checkExpect(hpList1.count(), 1)
                && t.checkExpect(hpList4.count(), 4);
    }

    boolean testILoBookTotalPrice(Tester t) {
        return t.checkExpect(hpList3.totalPrice(), 90)
                && t.checkExpect(emptyList.totalPrice(), 0)
                && t.checkExpect(hpList1.totalPrice(), 20)
                && t.checkExpect(hpList4.totalPrice(), 110);
    }

    boolean testILoBookCheaperThan(Tester t) {
        return t.checkExpect(emptyList.cheaperThan(10), new MtLoBook())
                && t.checkExpect(hpList3.cheaperThan(40), new ConsLoBook(hp1, new ConsLoBook(hp2, new MtLoBook())))
                && t.checkExpect(hpList3.cheaperThan(50), hpList3)
                && t.checkExpect(hpList1.cheaperThan(10), new MtLoBook());
    }

    boolean testILoBookAllBefore(Tester t) {
        return t.checkExpect(hpList3.allBefore(2003), new ConsLoBook(hp1, new ConsLoBook(hp2, new MtLoBook())))
                && t.checkExpect(hpList3.allBefore(2000), new MtLoBook())
                && t.checkExpect(hpList1.allBefore(2001), new ConsLoBook(hp1, new MtLoBook()));
    }

    boolean testILoBookSalePrice(Tester t) {
        return t.checkExpect(hpList3.salePrice(10), 81.0)
                && t.checkExpect(emptyList.salePrice(10), 0.0)
                && t.checkExpect(hpList1.salePrice(50), 10.0);
    }

    boolean testILoBookSortByPrice(Tester t) {

        return t.checkExpect(hpList3.sortByPrice(), sortedList)
                && t.checkExpect(emptyList.sortByPrice(), new MtLoBook())
                && t.checkExpect(hpList1.sortByPrice(), hpList1);
    }

    boolean testILoBookInsert(Tester t) {
        return t.checkExpect(emptyList.insert(hp1), new ConsLoBook(hp1, new MtLoBook()))
                && t.checkExpect(hpList1.insert(hp2), new ConsLoBook(hp1, new ConsLoBook(hp2, new MtLoBook())))
                && t.checkExpect(hpList3.insert(hp1), new ConsLoBook(hp1, hpList3));
    }

    boolean testILoBookSortByTitle(Tester t) {
        ILoBook unsortedList = new ConsLoBook(hp2, new ConsLoBook(hp3, new ConsLoBook(hp1, new MtLoBook())));
        return t.checkExpect(hpList3.sortByTitle(), hpList3)
                && t.checkExpect(emptyList.sortByTitle(), new MtLoBook())
                && t.checkExpect(unsortedList.sortByTitle(), sortedList);
    }
}