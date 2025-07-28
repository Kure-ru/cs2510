package lectures;

interface IMutableList<T> {
    // adds an item to the (front of) the list
    void add(T t);
    // removes an item from list
    void remove(T t);
}

// Represents a sentinel at the start, a node in the middle,
// or the empty end of a list
abstract class APersonList {
    abstract void removePersonHelp(String name, ANode prev);

    APersonList() {
    }
}

// Represents a node in a list that has some list after it
class ANode extends APersonList {
    APersonList rest;

    ANode(APersonList rest) {
        this.rest = rest;
    }

    void removePersonHelp(String name, ANode prev) {
        return;
    }
}

class MtLoPerson extends APersonList {
    MtLoPerson() {
    }

    void removePersonHelp(String name, ANode prev) {
        return;
    }
}

class ConsLoPerson extends ANode {
    Person20 first;

    ConsLoPerson(Person20 first, APersonList rest) {
        super(rest);
        this.first = first;
    }

    public void removePersonHelp(String name, ANode prev) {
        if (this.first.sameName(name)) {
            prev.rest = this.rest;
        } else {
            this.rest.removePersonHelp(name, this);
        }
    }
}

class Sentinel extends ANode {
    Sentinel(APersonList rest) {
        super(rest);
    }

    void removePersonHelp(String name, ANode prev) {
        throw new RuntimeException("Can't try to remove on a Sentinel!");
    }
}

class MutablePersonList {
    Sentinel sentinel;

    MutablePersonList(){
        this.sentinel = new Sentinel(new MtLoPerson());
    }

    void removePerson(String name) {
        this.sentinel.rest.removePersonHelp(name, this.sentinel);
    }

    void addPerson(String name, int phone) {
        this.sentinel.rest = new ConsLoPerson(new Person20(name, phone), this.sentinel.rest);
    }
}

class Person20 {
    String name;
    int phone;

    Person20(String name, int phone) {
        this.name = name;
        this.phone = phone;
    }

    boolean sameName(String name) {
        return this.name.equals(name);
    }
}