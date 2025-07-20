package lectures;

import tester.Tester;

interface IList18<T> {
    boolean contains(T t);
}

class MtList18<T> implements IList18<T> {
    public boolean contains(T t) {
        return false;
    }
}

class ConsList18<T> implements IList18<T> {
    T first;
    IList18<T> rest;

    ConsList18(T first, IList18<T> rest) {
        this.first = first;
        this.rest = rest;
    }

    public boolean contains(T t) {
        return this.first.equals(t) || this.rest.contains(t);
    }
}

class Author18 {
    String first;
    String last;
    int yob;
    IList18<Book18> books;

    Author18(String first, String last, int yob) {
        this.first = first;
        this.last = last;
        this.yob = yob;
        this.books = new MtList18<Book18>();
    }

    // Computes whether the given author has the same name and year of birth
    // as this author (i.e., we're ignoring their books to avoid infinite recursion)
    boolean sameAuthor(Author18 that) {
        return this.first.equals(that.first)
                && this.last.equals(that.last)
                && this.yob == that.yob;
    }

    boolean hasBook18(Book18 b){
        return this.books.contains(b);
    }

    // EFFECT: modifies this Author18's book field to refer to the given Book
//    void updateBook(Book b) {
//        if (!b.author.sameAuthor(this)){
//            throw new RuntimeException("book was not written by this author");
//        }
//        else if (this.book != null) {
//            throw new RuntimeException("trying to add second book to an author");
//        } else {
//            this.book = b;
//        }
//    }

    void addBook(Book18 b) {
        if (!b.author.sameAuthor(this)) {
            throw new RuntimeException("book was not written by this author");
        } else {
            this.books = new ConsList18<Book18>(b, this.books);
        }
    }

}

class Book18 {
    String title;
    int price;
    int quantity;
    Author18 author;

    Book18(String title, int price, int quantity, Author18 author) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.author = author;

        this.author.addBook(this);
    }
}

class ExamplesClass {

    Author18 knuth;
    Book18 taocp1, taocp2;

    void initTestConditions() {
        this.knuth = new Author18("Donald", "Knuth", 1938);
        this.taocp1 = new Book18("The Art of Computer Programming (volume 1)", 100, 2, knuth);
        this.taocp2 = new Book18("The Art of Computer Programming (volume 2)", 100, 2, knuth);
    }

    void testBook18Author18s(Tester t) {
        this.initTestConditions();

        // Knuth has written both books
        t.checkExpect(this.knuth.hasBook18(this.taocp1), true);
        t.checkExpect(this.knuth.hasBook18(this.taocp2), true);

        // Book18s refer back to knuth
        t.checkExpect(this.taocp1.author.sameAuthor(this.knuth), true);
        t.checkExpect(this.taocp2.author.sameAuthor(this.knuth), true);

        // Test exception when book is by wrong author
        Author18 shakespeare = new Author18("William", "Shakespeare", 1564);

        t.checkException(new RuntimeException("book was not written by this author"),
                shakespeare, "addBook18", this.taocp1);
    }
}