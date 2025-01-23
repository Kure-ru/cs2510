/* Exercise 18.1
Add constructors to the following six classes:
1. train schedule
2. restaurant guides
3. vehicle management
 */

class Train {
    Schedule s;
    Route r;

    public Train(Schedule s, Route r){
        this.s = s;
        this.r = r;
    }
}


class ExpressTrain extends Train {
    Stops st;
    String name;

    ExpressTrain(Schedule s, Route r, Stops st, String name){
        super(s, r);
        this.st = st;
        this.name = name;
    }
}


class Restaurant {
    String name;
    String price;
    Place place;

    Restaurant(String name, String price, Place place){
        this.name = name;
        this.price = price;
        this.place = place;
    }
}

class ChineseRestaurant extends Restaurant {
    boolean usesMSG;

    ChineseRestaurant(String name, String price, Place place, boolean usesMSG){
        super(name, price, place);
        this.usesMSG = usesMSG;
    }
}

class Vehicle {
    int mileage;
    int price;

    Vehicle(int mileage, int price){
        this.mileage = mileage;
        this.price = price;
    }
}

class Sedan extends Vehicle {
    Sedan(int mileage, int price){
        super(mileage, price);
    }
}

