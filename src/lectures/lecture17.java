//+----------------------------------------+
//        |               +------------+           |
//V               |            V           |
//        +--------------+       |     +---------------+  |
//        | Author       |       |     | Book          |  |
//        +--------------+       |     +---------------+  |
//        | String first |       |     | String title  |  |
//        | String last  |       |     | int price     |  |
//        | int yob      |       |     | int quantity  |  |
//        | Book book    |-------+     | Author author |--+
//        +--------------+             +---------------+

import tester.Tester;

class Author {
    String first;
    String last;
    int yob;
    Book book;

    Author(String first, String last, int yob, Book book) {
        this.first = first;
        this.last = last;
        this.yob = yob;
        this.book = book;
    }

    // Computes whether the given author has the same name and year of birth
    // as this author (i.e., we're ignoring their books to avoid infinite recursion)
    boolean sameAuthor(Author that) {
        return this.first.equals(that.first)
                && this.last.equals(that.last)
                && this.yob == that.yob;
    }
}

class Book {
    String title;
    int price;
    int quantity;
    Author author;

    Book(String title, int price, int quantity, Author author) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.author = author;
    }
}

class ExampleBooks {
    ExampleBooks() {
    }

    //  Java evaluates the right-hand side completely before assigning to the variable.
    //  At that moment, knuth is still null.
    //  Author knuth = new Author("Donald", "Knuth", 1938, new Book("The Art of Computer Programming (volume 1)", 100, 2, this.knuth));

    boolean testBookAuthorCycle(Tester t) {
        Author knuth = new Author("Donald", "Knuth", 1938, null);
        Book taocp = new Book("The Art of Computer Programming (volume 1)", 100, 2, knuth);

        knuth.book = taocp;

        return t.checkExpect(knuth.book.author, knuth);
    }
}