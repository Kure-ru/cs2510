/* Exercise 3.1
 Develop a “real estate assistant” program. The “assistant” helps real estate agents locate available houses for clients.
The information about a house includes its kind, the number of
rooms, its address, and the asking price. An address consists of
a street number, a street name, and a city.
 */

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
}
