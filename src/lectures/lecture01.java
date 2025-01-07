package lectures;

/**
 * HtDC Lectures
 * Lecture 2: Designing Simple Classes: Books and Authors
 *
 * Copyright 2013 Viera K. Proulx
 * This program is distributed under the terms of the
 * GNU Lesser General Public License (LGPL)
 *
 * @since 29 August 2013
 */

/*
 * We want to represent a book with an author as data in DrRacket
 * and then in Java.
 *
 * Here is the data definition in DrRacket:
 *
 * ;; to represent a book in a bookstore
 * ;; A Book is (make-book String Author Number)
 * (define-struct book (title author price))
 *
 * ;; to represent an author of a book (with the year of birth)
 * ;; An Author is (make-author String Number)
 * (define-struct author (name yob))
 *
 * ;; Examples:
 * (define pat (make-author "Pat Conroy" 1948))
 * (define beaches (make-book "Beaches" pat 20))
 *
 *
 * We have seen that all this information can be represented concisely
 * as s class diagram like this:
 *
 *

  +---------------------+
  | Book                |
  +---------------------+
  | String title        |
  | Author author       |--------------+
  | int price           |              |
  | Publisher publisher |              |
  +------------------+                 |
      |                                |
      |                                |
      v                                v
  +---------------------------+  +-------------+
  | Publisher                 |  | Author      |
  +---------------------------+  +-------------+
  | String name               |  | String name |
  | String countryOfOperation |  | int yob     |
  | int yearFounded           |  +-------------+
  +------------------------+

 *
 * We discussed the difference between exact and inexact numbers
 * and the fact that in Java we have to specify the kind of numbers
 * we intend to use. int stands for Integer.
 *
 * We record the book price in whole dollars only (for convenience).
 *
 * The code below shows how this class diagram and our examples can be
 * translated into a representation as Java classes
 * (a Java class hierarchy):
 */

// to represent a book in a bookstore
class Book {
    String title;
    Author03 author03;
    int price;
    Publisher publisher;

    // the constructor
    Book(String title, Author03 author03, int price, Publisher publisher) {
        this.title = title;
        this.author03 = author03;
        this.price = price;
        this.publisher = publisher;
    }
}

// to represent a author of a book in a bookstore
class Author {
    String name;
    int yob;

    // the constructor
    Author(String name, int yob) {
        this.name = name;
        this.yob = yob;
    }
}

// to represent a publisher of a book
class Publisher {
    String name;
    String country;
    int yearOpened;

    // the constructor
    Publisher(String name, String country, int yearOpened) {
        this.name = name;
        this.country = country;
        this.yearOpened = yearOpened;
    }
}

// examples and tests for the class hierarchy that represents
// books and authors
class ExamplesBooks {
    ExamplesBooks() {
    }

    Publisher doubleDay = new Publisher("Doubleday", "United States", 1897);
    Author03 pat = new Author03("Pat Conroy", 1948);
    Book3 beaches = new Book3("Beaches", this.pat, 20, this.doubleDay);
}

/**
 * Enhance the definitions of Book and Author above to include Publisher
 * information.
 * A Publisher should have fields representing their name (that is a String),
 * their country of operation (that is also a String),
 * and the year they opened for business (that is an int).
 *
 * Should Books or Authors have Publishers? Enhance the class diagram above to
 * include your new information.
 * Define the new class. And enhance the ExamplesBooks class to include examples
 * of the new data.
 */
