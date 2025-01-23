/* Exercise 18.3
 Abstract over the common fields in the class diagram of figure 16 (page 34). Revise the class diagram and the classes.
 */


interface ITaxiVehicle {}

abstract class ATaxiVehicle implements ITaxiVehicle {
    int idNum;
    int passengers;
    int pricePerMile;

    TaxiVehicle(int idNum, int passengers, int pricePerMile){
        this.idNum = idNum;
        this.passengers = passengers;
        this.pricePerMile = pricePerMile;
    }
}


class Cab extends ATaxiVehicle {
    Cab(int idNum, int passengers, int pricePerMile){
        super(idNum, passengers, pricePerMile);
    }
}

class Limo extends ATaxiVehicle {
    int minRental;

    Limo(int idNum, int passengers, int pricePerMile, int minRental){
        super(idNum, passengers, pricePerMile);
        this.minRental = minRental;
    }
}

class Van extends ATaxiVehicle {
    boolean access;

    Van(int idNum, int passengers, int pricePerMile, boolean access){
        super(idNum, passengers, pricePerMile);
        this.access = access;
    }
}


class ExamplesITaxiVehicle {
    ExamplesITaxiVehicle(){}

    ITaxiVehicle cab1 = new Cab(1, 4, 2);
    ITaxiVehicle limo1 = new Limo(2, 6, 3, 10);
    ITaxiVehicle van1 = new Van(3, 8, 4, true);
}
