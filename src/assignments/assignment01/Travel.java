package assignments.assignment01;

interface IHousing {

}

class Hut implements IHousing {
    int capacity;
    int population;

    Hut(int capacity, int population){
        this.capacity = capacity;
        this.population = population;
    }
}

class Inn implements IHousing {
    String name;
    int capacity;
    int population;
    int stalls;

    Inn(String name, int capacity, int population, int stalls){
        this.name = name;
        this.capacity = capacity;
        this.population = population;
        this.stalls = stalls;
    }
}

class Castle implements IHousing {
    String name;
    String familyName;
    int population;
    int carriageHouse;

    Castle(String name, String familyName, int population, int carriageHouse){
        this.name = name;
        this.familyName = familyName;
        this.population = population;
        this.carriageHouse = carriageHouse;
    }
}

interface ITransportation {
}

class Horse implements ITransportation {
    IHousing from;
    IHousing to;
    String name;
    String color;

    Horse(IHousing from, IHousing to, String name, String color){
        this.from = from;
        this.to = to;
        this.name = name;
        this.color = color;
    }
}

class Carriage implements ITransportation {
    IHousing from;
    IHousing to;
    int tonnage;

    Carriage(IHousing from, IHousing to, int tonnage){
        this.from = from;
        this.to = to;
        this.tonnage = tonnage;
    }
}

class ExampleTravel {
    ExampleTravel(){}

    IHousing hovel = new Hut(5, 1);
    IHousing smallHut = new Hut(3, 2);

    IHousing greenDragonInn = new Inn("Green Dragon Inn", 50, 30, 15);
    IHousing crossroads = new Inn("Inn At The Crossroads", 40, 20, 12);

    IHousing winterfell = new Castle("Winterfell", "Stark", 500, 6);
    IHousing kingsLanding = new Castle("King's Landing", "Baratheon", 1000, 20);

    ITransportation horse1 = new Horse(this.winterfell, this.greenDragonInn, "Shadowfax", "white");
    ITransportation horse2 = new Horse(this.kingsLanding, this.crossroads, "Binky", "black");
    ITransportation carriage1 = new Carriage(this.greenDragonInn, this.kingsLanding, 5);
    ITransportation carriage2 = new Carriage(this.crossroads, this.winterfell, 10);
}
