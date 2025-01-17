/* Exercise 15.2 & 15.3
 Develop a program that manages a runner’s training log.
(1) Every day the runner enters one entry concerning the day’s run
(2) The runner will want to see his log for a specific month of his training season.
(3) The runner wants to know the total distance run in a given month
(4) A runner wishes to know the length of his longest run ever
 */

interface ILog {
    double miles();
    ILog oneMonth(int month, int year);
    double oneMonthDistance(int month, int year);
    double longestRun();
}

class MTLog implements ILog {
    MTLog(){}

    public double miles() {
        return 0.0;
    }

    public ILog oneMonth(int month, int year) {
        return new MTLog();
    }

    public double oneMonthDistance(int month, int year) {
        return 0.0;
    }

    public double longestRun() {
        return 0.0;
    }
}

class ConsLog implements ILog {
    Entry first;
    ILog rest;

    ConsLog(Entry first, ILog rest){
        this.first = first;
        this.rest = rest;
    }

    public double miles() {
        return this.first.distance + this.rest.miles();
    }

    public ILog oneMonth(int month, int year) {
        if (this.first.sameMonthAndYear(month, year)) {
            return new ConsLog(this.first, this.rest.oneMonth(month, year));
        } else {
            return this.rest.oneMonth(month, year);
        }
    }

    public double oneMonthDistance(int month, int year) {
        if (this.first.sameMonthAndYear(month, year)) {
            return this.first.distance + this.rest.oneMonthDistance(month, year);
        } else {
            return this.rest.oneMonthDistance(month, year);
        }
    }

    public double longestRun() {
        if (this.first.distance > this.rest.longestRun()) {
            return this.first.distance;
        } else {
            return this.rest.longestRun();
        }
    }
}

class Entry {
    Date d;
    double distance;
    int duration;
    String comment;

    Entry(Date d, double distance, int duration, String comment){
        this.d = d;
        this.distance = distance;
        this.duration = duration;
        this.comment = comment;
    }

    boolean sameMonthAndYear(int month, int year){
        return this.d.sameMonthAndYear(month, year);
    }
}

class Date {
    int day;
    int month;
    int year;

    Date(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    boolean sameMonthAndYear(int month, int year) {
        return (this.month == month) && (this.year == year);
    }
}

class CompositeExamples {
    CompositeExamples() { }

    Date d1 = new Date(5, 5, 2003);
    Date d2 = new Date(6, 6, 2003);
    Date d3 = new Date(23, 6, 2003);
    Entry e1 = new Entry(d1, 5.0, 25, "Good");
    Entry e2 = new Entry(d2, 3.0, 24, "Tired");
    Entry e3 = new Entry(d3, 26.0, 156, "Great");
    ILog l1 = new MTLog();
    ILog l2 = new ConsLog(e1,l1);
    ILog l3 = new ConsLog(e2,l2);
    ILog l4 = new ConsLog(e3,l3);

    boolean testCompositeMiles(Tester t){
        return t.checkInexact(l1.miles(), 0.0, 0.1) &&
                t.checkInexact(l2.miles(), 5.0, 0.1) &&
                t.checkInexact(l3.miles(), 8.0, 0.1) &&
                t.checkInexact(l4.miles(), 34.0, 0.1);
    }

    boolean testCompositeOneMonth(Tester t){
        return t.checkExpect(l1.oneMonth(6, 2003), l1) &&
                t.checkExpect(l2.oneMonth(6, 2003), new MTLog()) &&
                t.checkExpect(l3.oneMonth(6, 2003), new ConsLog(e2, l1)) &&
                t.checkExpect(l4.oneMonth(6, 2003), new ConsLog(e3, new ConsLog(e2, l1)));
    }

    boolean testCompositeOneMonthDistance(Tester t){
        return t.checkExpect(l1.oneMonthDistance(6, 2003), 0.0) &&
                t.checkExpect(l2.oneMonthDistance(6, 2003), 0.0) &&
                t.checkExpect(l3.oneMonthDistance(6, 2003), 3.0) &&
                t.checkExpect(l4.oneMonthDistance(6, 2003), 29.0);
    }

    boolean testCompositeLongestRun(Tester t){
        return t.checkExpect(l1.longestRun(), 0.0) &&
                t.checkExpect(l2.longestRun(), 5.0) &&
                t.checkExpect(l3.longestRun(), 5.0) &&
                t.checkExpect(l4.longestRun(), 26.0);

    }
}
