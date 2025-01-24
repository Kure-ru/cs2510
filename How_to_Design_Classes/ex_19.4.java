/* Exercise 19.4
 Compare the two classes in figure 86. A Set contains each element at most once. A Bag allows multiple occurrences of elements.

Tasks:
1. Design a representation of lists of integers with MTin for the empty list and Cin for a list with an integer and an existing list.
2. Develop examples of Sets and Bags.
3. Create tests for all methods in Set and Bag.
4. Create a union interface ICollection and lift commonalities into an abstract superclass.
5. Develop the method size to determine the number of elements in a Bag or Set.
6. Develop the method rem to remove a given integer from a Bag or Set.
 */

// a list of integers
interface ILin {
    int howMany(int i);
    int count();
    ILin removeElements(int i);
}

class Mtin implements ILin {
    Mtin(){}

    @Override
    public int howMany(int i) {
        return 0;
    }

    public int count(){
        return 0;
    }

    public ILin removeElements(int i) {
        return this;
    }
}

class Cin implements ILin {
    int i;
    ILin elements;

    Cin(int i, ILin elements){
        this.i = i;
        this.elements = elements;
    }

    @Override
    public int howMany(int i) {
        if (this.i == i){
            return 1 + this.elements.howMany(i);
        } else {
            return this.elements.howMany(i);
        }
    }

    public int count(){
        return 1 + this.elements.count();
    }

    public ILin removeElements(int i) {
        if (this.i == i){
            return this.elements;
        } else {
            return new Cin(this.i, this.elements.removeElements(i));
        }
    }
}

interface ICollection {
    ACollection add(int i);
}

abstract class ACollection implements ICollection {
    ILin elements;
    ACollection(ILin elements) {
        this.elements = elements;
    }

    boolean in(int i) {
        return this.elements.howMany(i) > 0;
    }

    public int size () {
        return this.elements.count();
    }

    abstract ACollection rem(int i);
}

// a set of integers:
// contains an integer at most once
class Set extends ACollection {

    Set(ILin elements) {
        super(elements);
    }
    // add i to this set
    // unless it is already in there
    public Set add(int i) {
        if (this.in(i)) {
            return this; }
        else {
            return new Set(new Cin(i,this.elements));
        }
    }

    Set rem(int i) {
        return new Set(this.elements.removeElements(i));
    }
}

// a bag of integers
class Bag extends ACollection {

    Bag(ILin elements) {
        super(elements);
    }
    // add i to this bag
    public Bag add(int i) {
        return new Bag(new Cin(i,this.elements));
    }
    // how often is i in this bag?
    int howMany(int i) {
        return this.elements.howMany(i);
    }

    Bag rem(int i) {
        return new Bag(this.elements.removeElements(i));
    }
}

class ExamplesILin {
    ExamplesILin(){}

    ILin mt = new Mtin();
    ILin c1 = new Cin(1, mt);
    ILin c2 = new Cin(2, c1);
    ILin c3 = new Cin(3, c2);
    ILin c4 = new Cin(4, (new Cin(4, c3)));
    Set s1 = new Set(c1);
    Set s2 = new Set(c2);
    Bag b1 = new Bag(c3);
    Bag b2 = new Bag(c4);

    boolean testSetAdd(Tester t){
        return t.checkExpect(s1.add(1).in(1), true) &&
                t.checkExpect(s1.add(1).add(2).in(2), true) &&
                t.checkExpect(s1.add(1).add(2).add(1).in(1), true) &&
                t.checkExpect(s1.add(1).add(2).add(1).in(3), false);
    }

    boolean testSetIn(Tester t) {
        return t.checkExpect(s2.in(1), true) &&
                t.checkExpect(s2.in(2), true) &&
                t.checkExpect(s2.in(3), false);
    }

    boolean testBagAdd(Tester t) {
        return t.checkExpect(b1.add(1).howMany(1), 2) &&
                t.checkExpect(b1.add(1).add(2).howMany(2), 2) &&
                t.checkExpect(b1.add(1).add(2).add(1).howMany(1), 3);
    }

    boolean testBagIn(Tester t) {
        return t.checkExpect(b2.in(4), true) &&
                t.checkExpect(b2.in(3), true) &&
                t.checkExpect(b2.in(5), false);
    }

    boolean testBagHowMany(Tester t) {
        return t.checkExpect(b2.howMany(4), 2) &&
                t.checkExpect(b2.howMany(3), 1) &&
                t.checkExpect(b2.howMany(2), 1) &&
                t.checkExpect(b2.howMany(1), 1);
    }

    boolean testSize(Tester t) {
        return t.checkExpect(b2.size(), 5) &&
                t.checkExpect(b1.size(), 3) &&
                t.checkExpect(s2.size(), 2) &&
                t.checkExpect(s1.size(), 1);
    }

    boolean testRem(Tester t) {
        return t.checkExpect(b2.rem(4), new Bag(new Cin(4, c3))) &&
                t.checkExpect(b2.rem(4).size(), 4) &&
                t.checkExpect(b2.rem(4).howMany(4), 1) &&
                t.checkExpect(b2.rem(4).howMany(3), 1) &&
                t.checkExpect(b2.rem(4).howMany(2), 1) &&
                t.checkExpect(b2.rem(4).howMany(1), 1);
    }
}
