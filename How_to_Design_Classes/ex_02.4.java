// Exercise 2.4 Translate the class diagram in figure 6 into a class definition.
//Also create instances of the class.

/*
------------------
| Automobile      |
 ------------------
 | String model   |
 | int price      |
 | double mileage |
 | boolean used   |
 -----------------
 */


class Automobile {
    String model;
    int price;
    double mileage;
    boolean used;

    Automobile(String model, int price, double mileage, boolean used){
        this.model = model;
        this.price = price;
        this.mileage = mileage;
        this.used = used;
    }
}

class ExamplesAutomobile{
    ExamplesAutomobile(){}

    Automobile car1 = new Automobile("Toyota", 20000, 20.0, true);
    Automobile car2 = new Automobile("Honda", 25000, 15.0, false);
    Automobile car3 = new Automobile("Ford", 30000, 10.0, true);
}
