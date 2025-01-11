// Exercise 5.3 Consider a revision of the problem in exercise 3.1

interface ILoH{
}


class MTListing implements ILoH{
    MTListing(){}
}

class ConsListing implements ILoH {
    House first;
    ILoH rest;

    ConsListing(House first, ILoH rest){
        this.first = first;
        this.rest = rest;
    }
}

class House {
    String kind;
    int roomNum;
    Address address;
    double price;

    House(String kind, int roomNum, Address address, double price){
        this.kind = kind;
        this.roomNum = roomNum;
        this.address = address;
        this.price = price;
    }
}

class Address{
    int streetNum;
    String streetName;
    String city;

    Address(int streetNum, String streetName, String city){
        this.streetNum = streetNum;
        this.streetName = streetName;
        this.city = city;
    }
}

class ExamplesHouseAddress{
    ExamplesHouseAddress(){}
    Address brookline = new Address(23, "Maple Street", "Brookline");
    Address newton = new Address(5, "Joye Road", "Newton");
    Address waltham = new Address(83, "Winslow Road", "Waltham");

    House h1 = new House("Ranch", 7, this.brookline, 375000);
    House h2 = new House("Colonial", 9, this.newton, 450000);
    House h3 = new House("Cape", 6, this.waltham, 235000);

    ILoH mt = new MTListing();
    ILoH l1 = new ConsListing(this.h1, this.mt);
    ILoH l2 = new ConsListing(this.h2, this.l1);
    ILoH l3 = new ConsListing(this.h3, this.l2);
}
