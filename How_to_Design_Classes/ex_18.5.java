/*
Exercise 18.5 Develop examples for the original class hierarchy representing vehicles. Turn them into tests and run them. Then lift the common cost
method and re-run the tests.
 */

interface IVehicle {
    // compute the cost of refueling this vehicle,
    // given the current price (cp) of fuel
    double cost(double cp);
}


abstract class AVehicle implements IVehicle {
    double tank; // gallons

    AVehicle(double tank){
        this.tank = tank;
    }

    public double cost(double cp) {
        return this.tank * cp;
    }

}

class Car extends AVehicle {

    Car(double tank){
        super(tank);
    }
}

class Truck extends AVehicle {
    Truck(double tank){
        super(tank);
    }
}

class Bus extends AVehicle {
    Bus(double tank){
        super(tank);
    }
}

class ExamplesIVehicules {
    ExamplesIVehicules(){}

    IVehicle car = new Car(10);
    IVehicle truck = new Truck(20);
    IVehicle bus = new Bus(30);

    boolean testCost(Tester t){
        return t.checkInexact(this.car.cost(2.5), 25.0, 0.01) &&
                t.checkInexact(this.truck.cost(2.5), 50.0, 0.01) &&
                t.checkInexact(this.bus.cost(2.5), 75.0, 0.01);
    }
}
