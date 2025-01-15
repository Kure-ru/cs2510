package labs.lab2;

import tester.Tester;

interface IMOT {
    boolean isMoreFuelEfficientThan(int mpg);
}

class Bicycle implements IMOT {
    String brand;

    Bicycle(String brand){
        this.brand = brand;
    }

    public boolean isMoreFuelEfficientThan(int mpg){
        return true;
    }
}

class Car implements IMOT {
    String make;
    int mpg; // represents the fuel efficiency in miles per gallon

    Car(String make, int mpg){
        this.make = make;
        this.mpg = mpg;
    }

    public boolean isMoreFuelEfficientThan(int mpg){
        return this.mpg > mpg;
    }
}

class Person {
    String name;
    IMOT mot;

    Person(String name, IMOT mot){
        this.name = name;
        this.mot = mot;
    }

    // Does this person's mode of transportation meet the given fuel
    // efficiency target (in miles per gallon)?
    boolean motMeetsFuelEfficiency(int mpg){
        return this.mot.isMoreFuelEfficientThan(mpg);
    }

}

class ExamplesPerson {
    IMOT diamondback = new Bicycle("Diamondback");
    IMOT toyota = new Car("Toyota", 30);
    IMOT lamborghini = new Car("Lamborghini", 17);

    Person bob = new Person("Bob", diamondback);
    Person ben = new Person("Ben", toyota);
    Person becca = new Person("Becca", lamborghini);

    boolean testPersonMotMeetsFuelEfficiency(Tester t){
        return t.checkExpect(bob.motMeetsFuelEfficiency(15), true) &&
                t.checkExpect(ben.motMeetsFuelEfficiency(15), true) &&
                t.checkExpect(becca.motMeetsFuelEfficiency(15), true) &&
                t.checkExpect(bob.motMeetsFuelEfficiency(25), true) &&
                t.checkExpect(ben.motMeetsFuelEfficiency(25), true) &&
                t.checkExpect(becca.motMeetsFuelEfficiency(25), false);
    }
}