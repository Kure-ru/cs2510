interface IList {
    // sort this list
    IList sort();

    // insert o into this (sorted) list
    IList insert(IComp o);
}

interface IComp {
    // is this object less than o?
    boolean lessThan(Object o);
}

class Mt implements IList {
    Mt() {
    }

    public IList sort() {
        return this;
    }

    public IList insert(IComp o) {
        return new Cons(o, this);
    }
}

class Cons implements IList {
    IComp first;
    IList rest;

    Cons(IComp first, IList rest) {
        this.first = first;
        this.rest = rest;
    }

    public IList sort() {
        return rest.sort().insert(first);
    }

    public IList insert(IComp o) {
        if (first.lessThan(o)) {
            return new Cons(first, rest.insert(o));
        } else {
            return new Cons(o, this);
        }
    }
}

class PhoneBookEntry implements IComp {
    String name;
    int value;

    PhoneBookEntry(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public boolean lessThan(Object o) {
        PhoneBookEntry e;
        if (o instanceof PhoneBookEntry) {
            e = (PhoneBookEntry) o;
            return this.value < e.value;
        } else {
            throw new IllegalArgumentException("Object is not a phone book entry.");
        }
    }
}

class ExampleClass {
    IList phonebook = new Cons(new PhoneBookEntry("Alice", 123), new Cons(new PhoneBookEntry("Bob", 456), new Mt()));
}

/**
 * Exercise 31.8 Design Apple and Orange classes that represent individual
 * fruits (weight, ripeness, etc). Both classes should implement IComp where
 * lessThan returns false if the second input belongs to the wrong kind of class.
 * Then show that it is possible to compare Apples and Oranges. Is this good
 * or bad for programming?
 **/

class Apple implements IComp {
    int weight;

    Apple(int weight) {
        this.weight = weight;
    }

    public boolean lessThan(Object o) {
        if (o instanceof Apple) {
            Apple a = (Apple) o;
            return this.weight < a.weight;
        } else {
            return false;
        }
    }
}

class Orange implements IComp {
    int weight;

    Orange(int weight) {
        this.weight = weight;
    }

    public boolean lessThan(Object o) {
        if (o instanceof Orange) {
            Orange orange = (Orange) o;
            return this.weight < orange.weight;
        } else {
            return false;
        }
    }
}
