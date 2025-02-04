package assignments.assignment04;

import tester.*;

interface IEntertainment {
    //compute the total price of this Entertainment
    double totalPrice();
    //computes the minutes of entertainment of this IEntertainment
    int duration();
    //produce a String that shows the name and price of this IEntertainment
    String format();
    //is this IEntertainment the same as that one?
    boolean sameEntertainment(IEntertainment that);
    boolean sameMagazine(IEntertainment otherMagazine);
    boolean sameTVSeries(IEntertainment otherTVSeries);
    boolean samePodcast(IEntertainment otherPodcast);
}

abstract class AEntertainment implements IEntertainment {
    String name;
    double price;
    int installments;

    AEntertainment(String name, double price, int installments) {
        this.name = name;
        this.price = price;
        this.installments = installments;
    }

    public double totalPrice() {
        return this.price * this.installments;
    }

    public int duration() {
        return this.installments * 50;
    }

    public boolean sameMagazine(IEntertainment otherMagazine){ return false; }
    public boolean sameTVSeries(IEntertainment otherTVSeries) { return false; }
    public boolean samePodcast(IEntertainment otherPodcast) { return false; }

    //produce a String that shows the name and price of this Magazine
    public String format() {
        return this.name + ", " + this.price + ".";
    }
}

class Magazine extends AEntertainment {
    String genre;
    int pages;

    Magazine(String name, double price, String genre, int pages, int installments) {
        super(name, price, installments);
        this.genre = genre;
        this.pages = pages;
    }

    @Override
    public int duration() {
        return this.installments * this.pages * 5;
    }

    //is this Magazine the same as that IEntertainment?
    public boolean sameEntertainment(IEntertainment that) {
        return that.sameMagazine(this);
    }

    @Override
    public boolean sameMagazine(IEntertainment otherMagazine){
        return true;
    }

}

class TVSeries extends AEntertainment {
    String corporation;

    TVSeries(String name, double price, int installments, String corporation) {
        super(name, price, installments);
        this.corporation = corporation;
    }

    //is this TVSeries the same as that IEntertainment?
    public boolean sameEntertainment(IEntertainment that) {
        return that.sameTVSeries(this);
    }

    @Override
    public boolean sameTVSeries(IEntertainment otherTVSeries) {
        return true;
    }
}

class Podcast extends AEntertainment {
    Podcast(String name, double price, int installments) {
        super(name, price, installments);
    }

    //is this Podcast the same as that IEntertainment?
    public boolean sameEntertainment(IEntertainment that) {
        return that.samePodcast(this);
    }

    @Override
    public boolean samePodcast(IEntertainment otherPodcast) {
        return true;
    }
}

class ExamplesEntertainment {
    IEntertainment rollingStone = new Magazine("Rolling Stone", 2.55, "Music", 60, 12);
    IEntertainment elle = new Magazine("Elle", 3.0, "Fashion", 100, 12);
    IEntertainment houseOfCards = new TVSeries("House of Cards", 5.25, 13, "Netflix");
    IEntertainment breakingBad = new TVSeries("Breaking Bad", 4.0, 62, "AMC");
    IEntertainment serial = new Podcast("Serial", 0.0, 8);
    IEntertainment syntax = new Podcast("Syntax", 0.0, 10);


    //testing total price method
    boolean testTotalPrice(Tester t) {
        return t.checkInexact(this.rollingStone.totalPrice(), 2.55*12, .0001)
                && t.checkInexact(this.houseOfCards.totalPrice(), 5.25*13, .0001)
                && t.checkInexact(this.serial.totalPrice(), 0.0, .0001)
                && t.checkInexact(this.elle.totalPrice(), 3.0*12, .0001)
                && t.checkInexact(this.breakingBad.totalPrice(), 4.0*62, .0001)
                && t.checkInexact(this.syntax.totalPrice(), 0.0, .0001);
    }

    boolean testFormat(Tester t) {
        return t.checkExpect(this.rollingStone.format(), "Rolling Stone, 2.55.")
                && t.checkExpect(this.houseOfCards.format(), "House of Cards, 5.25.")
                && t.checkExpect(this.serial.format(), "Serial, 0.0.")
                && t.checkExpect(this.elle.format(), "Elle, 3.0.")
                && t.checkExpect(this.breakingBad.format(), "Breaking Bad, 4.0.")
                && t.checkExpect(this.syntax.format(), "Syntax, 0.0.");
    }

    boolean testSameEntertainment(Tester t) {
        return t.checkExpect(breakingBad.sameEntertainment(this.houseOfCards), true) &&
                t.checkExpect(rollingStone.sameEntertainment(this.elle), true) &&
                t.checkExpect(serial.sameEntertainment(this.syntax), true) &&
                t.checkExpect(elle.sameEntertainment(this.houseOfCards), false);
    }

    boolean testDuration(Tester t){
        return t.checkExpect(this.rollingStone.duration(), 60*12*5) &&
                t.checkExpect(this.houseOfCards.duration(), 13*50) &&
                t.checkExpect(this.serial.duration(), 8*50);
    }

}