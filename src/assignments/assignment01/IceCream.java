package assignments.assignment01;

interface IIceCream {
 }

class EmptyServing implements IIceCream {
    boolean cone;

    EmptyServing(boolean cone){
        this.cone = cone;
    }
}

class Scooped implements IIceCream {
    IIceCream more;
    String flavor;

    Scooped(IIceCream more, String flavor){
        this.more = more;
        this.flavor = flavor;
    }
}

class ExamplesIceCream {
    ExamplesIceCream(){}

    IIceCream cone = new EmptyServing(true);
    IIceCream cup = new EmptyServing(false);
    IIceCream order1 = new Scooped(new Scooped (new Scooped (new Scooped (this.cup, "mint chip"), "coffee"), "black raspberry"), "caramel swirl");
    IIceCream order2 = new Scooped(new Scooped ( new Scooped(this.cone, "strawberry"), "vanilla"), "chocolate");
}