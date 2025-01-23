/*Exercise 4.6
Take a look at the class diagram in figure 16. Translate it into
interface and class definitions. Also create instances of each class.
 */

interface ITaxiVehicle {}

class Cab implements ITaxiVehicle {
    int idNum;
    int passengers;
    int pricePerMile;

    Cab(int idNum, int passengers, int pricePerMile){
        this.idNum = idNum;
        this.passengers = passengers;
        this.pricePerMile = pricePerMile;
    }
}

class Limo implements ITaxiVehicle {
    int minRental;
    int idNum;
    int passengers;
    int pricePerMile;

    Limo(int idNum, int passengers, int pricePerMile, int minRental){
        this.idNum = idNum;
        this.passengers = passengers;
        this.pricePerMile = pricePerMile;
        this.minRental = minRental;
    }
}

class Van implements ITaxiVehicle {
    boolean access;
    int idNum;
    int passengers;
    int pricePerMile;

    Van(int idNum, int passengers, int pricePerMile, boolean access){
        this.idNum = idNum;
        this.passengers = passengers;
        this.pricePerMile = pricePerMile;
        this.access = access;
    }
}


class ExamplesITaxiVehicle {
    ExamplesITaxiVehicle(){}

    ITaxiVehicle cab1 = new Cab(1, 4, 2);
    ITaxiVehicle limo1 = new Limo(2, 6, 3, 10);
    ITaxiVehicle van1 = new Van(3, 8, 4, true);
}