package lectures;

import tester.Tester;

interface IList19<T> {
    void find(IPred19<T> whichOne, IFunc19<T, Void> whatToDo);
}

interface IFunc19<T, Void> {
    Void apply(T t);
}

interface IPred19<T> {
    boolean apply(T t);
}

class Person19HasName implements IPred19<Person19> {
    String name;

    Person19HasName(String name) {
        this.name = name;
    }

    public boolean apply(Person19 p) {
        return p.name.equals(this.name);
    }
}

class ChangePerson19Phone implements IFunc19<Person19, Void> {
    int newNum;

    ChangePerson19Phone(int newNum) {
        this.newNum = newNum;
    }

    public Void apply(Person19 p) {
        p.changeNum(this.newNum);
        return null;
    }
}

class MtList19<T> implements IList19<T> {
    public void find(IPred19<T> whichOne, IFunc19<T, Void> whatToDo) {

    }
}

class ConsList19<T> implements IList19<T> {
    T first;
    IList19<T> rest;

    ConsList19(T first, IList19<T> rest) {
        this.first = first;
        this.rest = rest;
    }

    public void find(IPred19<T> whichOne, IFunc19<T, Void> whatToDo) {
        if (whichOne.apply(this.first)) {
            whatToDo.apply(this.first);
        } else {
            this.rest.find(whichOne, whatToDo);
        }
    }
}


class Person19 {
    String name;
    int phone;

    Person19(String name, int phone) {
        this.name = name;
        this.phone = phone;
    }

    // Returns true when the given person has the same name and phone number as this person
    boolean samePerson19(Person19 that) {
        return this.name.equals(that.name) && this.phone == that.phone;
    }

    // Return true when this person has the same name as a given String
    boolean sameName(String name) {
        return this.name.equals(name);
    }

    // Returns the number of this person when they have the same name as a given String
    int phoneOf(String name) {
        if (this.name.equals(name)) {
            return this.phone;
        } else {
            throw new RuntimeException("The given name does not match this person's name");
        }
    }

    // EFFECT: modifies this person's phone number to be the given one
    void changeNum(int newNum) {
        this.phone = newNum;
    }
}

class ExamplePhoneLists {
    Person19 anne = new Person19("Anne", 1234);
    Person19 bob = new Person19("Bob", 3456);
    Person19 clyde = new Person19("Clyde", 6789);
    Person19 dana = new Person19("Dana", 1357);
    Person19 eric = new Person19("Eric", 12469);
    Person19 frank = new Person19("Frank", 7294);
    Person19 gail = new Person19("Gail", 9345);
    Person19 henry = new Person19("Henry", 8602);
    Person19 irene = new Person19("Irene", 91302);
    Person19 jenny = new Person19("Jenny", 8675309);

    IList19<Person19> friends, family, work;

    void initData() {
        this.friends =
                new ConsList19<>(this.anne, new ConsList19<>(this.clyde,
                        new ConsList19<>(this.gail, new ConsList19<>(this.frank,
                                new ConsList19<>(this.jenny, new MtList19<>())))));
        this.family =
                new ConsList19<>(this.anne, new ConsList19<>(this.dana,
                        new ConsList19<>(this.frank, new MtList19<>())));
        this.work =
                new ConsList19<>(this.bob, new ConsList19<>(this.clyde,
                        new ConsList19<>(this.dana, new ConsList19<>(this.eric,
                                new ConsList19<>(this.henry, new ConsList19<>(this.irene,
                                        new MtList19<>()))))));
    }

    void testChangeNum(Tester t) {
        this.initData();
        t.checkExpect(this.frank.phone, 7294);
        this.family.find(new Person19HasName("Frank"), new ChangePerson19Phone(9021));

        // Since 'frank' is shared (aliased), this change affects all lists
        t.checkExpect(this.frank.phone, 9021);
        this.friends.find(new Person19HasName("Frank"), new ChangePerson19Phone(5555));
        t.checkExpect(this.frank.phone, 5555);
    }

    void testAliasing(Tester t) {
        // Create two Person19 objects that are the same
        Person19 johndoe1 = new Person19("John Doe", 12345);
        Person19 johndoe2 = new Person19("John Doe", 12345);
        // Alias johndoe1 to johndoe3
        Person19 johndoe3 = johndoe1;

        // Check that all three John Does are the same according to samePerson19
        t.checkExpect(johndoe1.samePerson19(johndoe2), true);
        t.checkExpect(johndoe1.samePerson19(johndoe3), true);

        // Modify johndoe1
        johndoe1.name = "Johnny Deere";

        // Now let's try the same tests.  Which of them will pass?
        // t.checkExpect(johndoe1.samePerson19(johndoe2), true);
        t.checkExpect(johndoe1.samePerson19(johndoe3), true);
    }
}