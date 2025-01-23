/* Exercise 18.2
Abstract over the common fields of Lion, Snake, and Monkey
in the class diagram of figure 14 (page 32). First revise the class diagram,
then define the classes including the constructors.
 */

interface IZooAnimal{}

abstract class AZooAnimal implements IZooAnimal {
    String name;
    int weight;

    ZooAnimal(String name, int weight){
        this.name = name;
        this.weight = weight;
    }
}


class Lion extends AZooAnimal {
    int meat;

    Lion(String name, int weight, int meat){
        super(name, weight);
        this.meat = meat;
    }
}

class Snake extends AZooAnimal {
    int length;

    Snake(String name, int weight, int length){
        super(name, weight);
        this.length = length;
    }
}

class Monkey extends AZooAnimal {
    String food;
    String name;
    int weight;

    Monkey(String name, int weight, String food){
        super(name, weight);
        this.food = food;
    }
}


class ExamplesZooAnimals {
    ExamplesZooAnimals(){}

    IZooAnimal leo = new Lion("Leo", 300, 5);
    IZooAnimal boa = new Snake("Ana", 150, 5);
    IZooAnimal george = new Monkey("George", 150, "kiwi");
}