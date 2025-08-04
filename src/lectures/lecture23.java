package lectures;

import tester.Tester;

import java.util.ArrayList;

interface IFunc23<Integer, U> {
    U apply(Integer i);
}

class Author23 {
    String name;
    int yob;

    Author23(String name, int yob) {
        this.name = name;
        this.yob = yob;
    }
}


class Book23 {
    String title;
    Author23 author;

    Book23(String title, Author23 author) {
        this.title = title;
        this.author = author;
    }

    // EFFECT: Capitalizes this book's title
    void capitalizeTitle() {
        this.title = this.title.toUpperCase();
    }
}

class ArrayUtils23 {
    <U> ArrayList<U> buildList(int n, IFunc23<Integer, U> func) {
        ArrayList<U> result = new ArrayList<U>();
        for (int i = 0; i < n; i = i + 1) {
            result.add(func.apply(i));
        }
        return result;
    }

    void capitalizeTitles_bad(ArrayList<Book23> books) {
        for (Book23 b : books) {
            b = new Book23(b.title.toUpperCase(), b.author);
        }
    }

    void capitalizeTitles_good(ArrayList<Book23> books) {
        for (Book23 b : books) {
            b.capitalizeTitle();
        }
    }

    void capitalizeTitles_ok(ArrayList<Book23> books) {
        for (int i = 0; i < books.size(; i = i + 1) {
            Book23 oldB = books.get(i);
            Book23 newB = new Book23(oldB.title.toUpperCase(), oldB.author);
            books.set(i, newB);
        }
    }
}

class ExamplesCapitalize {
    void testCapitalizeTitles(Tester t) {
        Author23 mf = new Author23("Mathias Felleisen", 1953);
        Book23 htdp = new Book23("How to Design Programs", mf);
        ArrayList<Book23> books = new ArrayList<Book23>();
        books.add(htdp);

        // modify book
        (new ArrayUtils23()).capitalizeTitles_good(books);

        // test for changes
        t.checkExpect(books.get(0).title, "HOW TO DESIGN PROGRAMS");
    }
}