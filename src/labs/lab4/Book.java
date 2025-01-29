package labs.lab4;

/*
The following class diagram represents a library system that records the books that have been borrowed. There are three kinds of books: regular books, reference books, and audio books.

Reference books can be taken out for just two days, while other kinds of books may be borrowed for two weeks. The overdue fees are 10 cents per day for reference books and regular books, and 20 cents per day for audio books.

Audio books and regular books have both authors and titles; reference books only have titles.

The day when the book is taken out and the day due are counted as days since the library opened on New Year’s Day in 2001. So, for example, an audio book taken out recently would be recorded as taken out on the day 7778 with due date on the day 7792.

               +-------+
               | IBook |
               +-------+
                  / \
                  ---
                   |
       ---------------------------------------
       |                  |                  |
+---------------+  +---------------+  +---------------+
| Book          |  | RefBook       |  | AudioBook     |
+---------------+  +---------------+  +---------------+
| String title  |  | String title  |  | String title  |
| String author |  | int dayTaken  |  | String author |
| int dayTaken  |  +---------------+  | int dayTaken  |
+---------------+                     +---------------+
Design the interfaces and classes that represent the library borrowing system.

Define the abstract class ABook and lift those fields that can be lifted to this class.

Design the method daysOverdue that consumes the number that represents today in the library date-recording system and produces the number of days this book is overdue. If the number is negative, the book can still be out for that many days.

Design the method isOverdue that produces a boolean value that informs us whether the book is overdue on the given day.

Design the method computeFine that computes the fine for this book, if the book is returned on the given day.

For all methods, think carefully whether they should be designed being implemented solely in the abstract class, implemented solely in the concrete classes, or implemented in the abstract class and then overridden in some of the concrete classes.
 */

import tester.Tester;

interface IBook {
    int REGULAR_TIME_ALLOWANCE = 14;
    int SHORT_TIME_ALLOWANCE = 2;
    int REGULAR_LATE_FEE = 10;
    int SPECIAL_LATE_FEE = 20;

    int daysOverdue(int currentDate);
    int getDueDate();
    boolean isOverdue(int currentDate);
    int computeFine(int currentDate);
}

abstract class ABook implements IBook {
    String title;
    int dayTaken;

    ABook(String title, int dayTaken){
        this.title = title;
        this.dayTaken = dayTaken;
    }


    public int daysOverdue(int currentDate){
        return currentDate - this.getDueDate();
    }

    public int getDueDate() {
        return this.dayTaken + REGULAR_TIME_ALLOWANCE;
    }


    public boolean isOverdue(int currentDate) {
        return this.daysOverdue(currentDate) >= 0;
    }

    public int computeFine(int currentDate) {
        if (this.isOverdue(currentDate)){
            return this.daysOverdue(currentDate) * REGULAR_LATE_FEE;
        } else {
            return 0;
        }
    }
}


class Book extends ABook {
    String author;

    Book(String title, String author, int dayTaken){
        super(title, dayTaken);
        this.author = author;
    }

}

class RefBook extends ABook {

    RefBook(String title, int dayTaken){
        super(title, dayTaken);
    }

    @Override
    public int getDueDate() {
        return this.dayTaken + SHORT_TIME_ALLOWANCE;
    }
}

class AudioBook extends ABook {
    String author;

    AudioBook(String title, String author, int dayTaken){
        super(title, dayTaken);
        this.author = author;
    }

    @Override
    public int computeFine(int currentDate) {
        if (this.isOverdue(currentDate)){
            return this.daysOverdue(currentDate) * SPECIAL_LATE_FEE;
        } else {
            return 0;
        }
    }

}

class ExamplesIBook {
    ExamplesIBook() {}

    IBook htdp = new Book("How to Design Programs", "Matthias Felleisen", 7778);
    IBook ref1 = new RefBook("Encyclopedia Britannica", 7778);
    IBook htdpAudio = new AudioBook("How to Design Programs", "Matthias Felleisen", 7778);


    boolean testDaysOverdue (Tester t) {
        return t.checkExpect(htdp.daysOverdue(7778), -14) &&
                t.checkExpect(htdp.daysOverdue(7794), 2) &&
                t.checkExpect(ref1.daysOverdue(7778), -2) &&
                t.checkExpect(ref1.daysOverdue(7782), 2);

    }

    boolean testDaysIsOverdue (Tester t) {
        return t.checkExpect(htdp.isOverdue(7778), false) &&
                t.checkExpect(htdp.isOverdue(7794), true) &&
                t.checkExpect(ref1.isOverdue(7778), false) &&
                t.checkExpect(ref1.isOverdue(7782), true);

    }

    boolean testComputeFine(Tester t){
        return t.checkExpect(htdp.computeFine(7794), 20) &&
                t.checkExpect(htdpAudio.computeFine(7794), 40) &&
                t.checkExpect(htdp.computeFine(7778), 0);
    }
}
