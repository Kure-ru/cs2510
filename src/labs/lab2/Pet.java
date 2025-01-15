package labs.lab2;
import tester.Tester;


class Person02 {
    String name;
    IPet pet;
    int age;

    Person02(String name, IPet pet, int age){
        this.name = name;
        this.pet = pet;
        this.age = age;
    }

    boolean isOlder(Person02 other){
        return this.age > other.age;
    }

    boolean sameNamePet(String name){
        return this.pet.isSameName(name);
    }

    Person02 perish(){
        return new Person02(this.name, new NoPet(), this.age);
    }
}

interface IPet {
    boolean isSameName(String name);
}

class NoPet implements IPet {
    NoPet(){}

    public boolean isSameName(String thatName){
        return false;
    }
}

class Cat implements IPet {
    String name;
    String kind;
    boolean longhaired;

    Cat(String name, String kind, boolean longhaired){
        this.name = name;
        this.kind = kind;
        this.longhaired = longhaired;
    }

    public boolean isSameName(String thatName){
        return this.name.equals(thatName);
    }
}

class Dog implements IPet {
    String name;
    String kind;
    boolean male;

    Dog(String name, String kind, boolean male){
        this.name = name;
        this.kind = kind;
        this.male = male;
    }

    public boolean isSameName(String thatName){
        return this.name.equals(thatName);
    }
}

class ExamplePerson02Pet {
    ExamplePerson02Pet(){}

    Person02 paul = new Person02("Paul", new Cat("Fluffy", "Persian", true), 20);
    Person02 sally = new Person02("Sally", new Dog("Rex", "German Shepherd", true), 25);
    Person02 jill = new Person02("Jill", new Cat("Whiskers", "Siamese", false), 30);
    Person02 claire = new Person02("Claire", new Dog("Pongo", "Dalmatian", true), 31);
    Person02 bob = new Person02("Bob", new NoPet(), 40);

    boolean testPerson02IsOlder(Tester t){
        return t.checkExpect(paul.isOlder(sally), false) &&
                t.checkExpect(claire.isOlder(jill), true);
    }

    boolean testSameNamePet(Tester t){
        return t.checkExpect(paul.sameNamePet("Fluffy"), true) &&
                t.checkExpect(jill.sameNamePet("Tobby"), false) &&
                t.checkExpect(bob.sameNamePet("Misty"), false);
    }
}