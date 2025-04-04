package lectures;

import tester.Tester;

//Generic interfaces
interface IPred<T> {
    boolean apply(T t);
}

interface IComparator<T> {
    int compare(T t1, T t2);
}

interface IFunc<A, R> {
    R apply(A args);
}

// Interface for two-argument function-objects with signature [A1, A2 -> R]
interface IFunc2<A1, A2, R> {
    R apply(A1 arg1, A2 arg2);
}

interface IList<T> {
    IList<T> filter(IPred<T> pred);
    IList<T> sort(IComparator<T> comp);
    IList<T> insert(IComparator<T> comp, T acc);
    <U> IList<U> map(IFunc<T, U> f);
    <U> U foldr(IFunc2<T, U, U> func, U base);
}

class Utils15 {
    Integer totalPrice(IList<Book15> books) {
        return books.foldr(new SumPricesOfBook15s(), 0);
    }
}

class SumPricesOfBook15s implements IFunc2<Book15, Integer, Integer> {
    public Integer apply (Book15 b, Integer sum) {
        return b.price() + sum;
    }
}

// List implementation

class MtList<T> implements IList<T> {
    public IList<T> filter(IPred<T> pred) { return this; }
    public IList<T> sort(IComparator<T> comp) { return this; }
    public IList<T> insert (IComparator<T> comp, T t) { return new ConsList<T>(t, this); }
    public <U> IList<U> map(IFunc<T, U> f) { return new MtList<U>(); }
    public <U> U foldr(IFunc2<T, U, U> func, U base) { return base; }
}

class ConsList<T> implements IList<T> {
    T first;
    IList<T> rest;

    ConsList(T first, IList<T> rest){
        this.first = first;
        this.rest = rest;
    }

    public IList<T> filter(IPred<T> pred) {
        if (pred.apply(this.first)) {
            return new ConsList<T>(this.first, this.rest.filter(pred));
        }
        else {
            return this.rest.filter(pred);
        }
    }

    public IList<T> sort (IComparator<T> comp) {
        return this.rest.sort(comp).insert(comp, this.first);
    }

    public IList<T> insert (IComparator<T> comp, T t){
        if (comp.compare(this.first, t) < 0){
            return new ConsList<T>(this.first, this.rest.insert(comp, t));
        } else {
            return new ConsList<T>(t, this);
        }
    }

    public <U> IList<U> map(IFunc<T, U> f) {
        return new ConsList<U>(f.apply(this.first), this.rest.map(f));
    }

    public <U> U foldr(IFunc2<T, U, U> func, U base) {
        return func.apply(this.first, this.rest.foldr(func, base));
    }
}

// Runner15 class

class Runner15 {
    String name;
    int age;
    int bib;
    boolean isMale;
    int pos;
    int time;

    Runner15(String name, int age, int bib, boolean isMale, int pos, int time){
        this.name = name;
        this.age = age;
        this.bib = bib;
        this.isMale = isMale;
        this.pos = pos;
        this.time = time;
    }
}

interface ILoRunner15 {
}

class MTLoRunner15 implements ILoRunner15 {
    MTLoRunner15(){}
}

class ConsLoRunner15 implements  ILoRunner15 {
    Runner15 first;
    ILoRunner15 rest;

    ConsLoRunner15(Runner15 first, ILoRunner15 rest){
        this.first = first;
        this.rest = rest;
    }
}


interface IRunner15Comparator {
    int compare (Runner15 r1, Runner15 r2);
}

class Runner15IsInFirst50 implements IPred<Runner15> {
    public boolean apply (Runner15 r){
        return r.pos <= 50;
    }
}

class Runner15Name implements IFunc<Runner15, String> {
    public String apply(Runner15 r) { return r.name; };
}

// Book15 class

class Book15 {
    String title;
    String author;
    int price;

    Book15(String title, String author, int price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    int price() {
        return this.price;
    }

}

class Book15ByAuthor implements IPred<Book15> {
    public boolean apply(Book15 b) {
        return true;
    }
}


interface IBook15Comparator {
    int compare (Book15 b1, Book15 b2);
}

// Circle15 class

class Circle15  {
    int x, y;
    int radius;

    Circle15(int x, int y, int radius){
        this.x = x;
        this.y = y;
        this.radius = radius;
    }
}

class Circle15Perimeter implements IFunc<Circle15, Double> {
    public Double apply(Circle15 c) { return 2.0 * Math.PI * c.radius; }
}


class ExamplesRunner15s {
    ExamplesRunner15s(){}

    Runner15 johnny = new Runner15("Kelly", 97, 999, true, 30, 360);
    Runner15 frank  = new Runner15("Shorter", 32, 888, true, 245, 130);
    Runner15 bill = new Runner15("Rogers", 36, 777, true, 119, 129);
    Runner15 joan = new Runner15("Benoit", 29, 444, false, 18, 155);

    ILoRunner15 mtlist = new MTLoRunner15();
    ILoRunner15 list1 = new ConsLoRunner15(johnny, new ConsLoRunner15(joan, mtlist));
    ILoRunner15 list2 = new ConsLoRunner15(frank, new ConsLoRunner15(bill, list1));

    IPred<Runner15> inFirst50 = new Runner15IsInFirst50();

    IList<Circle15> circs = new ConsList<Circle15>(new Circle15(3, 4, 5),
            new MtList<Circle15>());
    IList<Double> circPerims = circs.map(new Circle15Perimeter());

    boolean testFirst50(Tester t) {
        return t.checkExpect(inFirst50.apply(johnny), true) &&
                t.checkExpect(inFirst50.apply(frank), false);
    }
}



