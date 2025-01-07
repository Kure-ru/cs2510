package lectures;

import tester.Tester;


/* RACKET
;; to represent a book in a bookstore
;; A Book is (make-book String String Number)
(define-struct book (title author price))

;; Examples:
(define htdp (make-book "HtDP" "FFFK" 60))
(define beaches (make-book "Beaches" "PC" 20))

;; compute the sale price of a book for the given discount
;; sale-price: Book Num -> Num
(define (sale-price abook discount)
  (- (book-price abook) (/ (* (book-price abook) discount) 100)))

(check-expect (sale-price htdp 30) 42)
(check-expect (sale-price beaches 20) 16)
 */

/*
  +---------------+
  | Book          |
  +---------------+
  | String title  |
  | Author author |--+
  | int price     |  |
  +---------------+  |
                     v
              +-------------+
              | Author      |
              +-------------+
              | String name |
              | int yob     |
              +-------------+
 */

class Book3 {
    String title;
    Author3 author3;
    int price;

    Book3(String title, Author3 author3, int price) {
        this.title = title;
        this.author3 = author3;
        this.price = price;
    }

/* TEMPLATE:
   Fields:
   ... this.title ...        -- String
   ... this.author ...       -- Author
   ... this.price ...        -- int

   Methods:
   ... this.salePrice(int) ...   -- int
   ... this.sameAuthor(Book) ... -- boolean
   ... this.reducePrice() ...  -- Book
*/

    // Compute the sale price of this Book given using
    // the given discount rate (as a percentage)
    int salePrice(int discount){
        return this.price - (this.price * discount) / 100;
    }

    boolean sameAuthor(Book3 that){
        return this.author3.sameAuthor(that.author3);
    }

    // produce a book like this one, but with the price reduced by 20%
    Book3 reducePrice(){
        return new Book3(this.title, this.author3, this.salePrice(20));
    }
}

class Author3 {
    String name;
    int yob;

    Author3(String name, int yob){
        this.name = name;
        this.yob = yob;
    }

    /* TEMPLATE
     Fields:
     ... this.name ...    -- String
     ... this.yob ...     -- int

     Methods:
     ... this.sameAuthor(Author) ... -- boolean
  */

    boolean sameAuthor(Author3 that){
        return this.name.equals(that.name) &&
                this.yob == that.yob;
    }
}

class ExamplesBooksAuthors {
    ExamplesBooksAuthors() {}

    Author3 pat = new Author3("Pat Conroy", 1948);
    Author3 dan = new Author3("Dan Brown", 1962);

    Book3 beaches = new Book3("Beaches", this.pat, 20);
    Book3 prince = new Book3("Prince of Tides", this.pat, 15);
    Book3 code = new Book3("Da Vinci Code", this.dan, 20);

    boolean testSalePrice(Tester t){
        return t.checkExpect(this.beaches.salePrice(20), 16)
                && t.checkExpect(this.prince.salePrice(30), 11);
    }

    boolean testSameBookAuthor(Tester t) {
        return t.checkExpect(this.beaches.sameAuthor(this.prince), true)
                && t.checkExpect(this.beaches.sameAuthor(this.code), false);
    }

    boolean testSameAuthor(Tester t) {
        return t.checkExpect(
                this.pat.sameAuthor(new Author3("Pat Conroy", 1948)),
                true)
                && t.checkExpect(this.pat.sameAuthor(this.dan), false);
    }

    boolean testReducePrice(Tester t) {
        return t.checkExpect(this.code.reducePrice(),
                new Book3("Da Vinci Code", this.dan, 16))
                && t.checkExpect(this.beaches.reducePrice(),
                new Book3("Beaches", this.pat, 16));
    }
}