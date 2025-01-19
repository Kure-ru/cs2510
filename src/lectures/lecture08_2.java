package lectures;

import tester.Tester;

interface IInt {
    boolean isVariantA();
    boolean isVariantB();
    boolean isVariantC();
    boolean isVariantAHelper(boolean hasEven, boolean hasPositiveOdd, boolean hasInRange);
    boolean isVariantBHelper(boolean hasEven, boolean hasPositiveOdd, boolean hasInRange);
    boolean isVariantCHelper(boolean hasEven, boolean hasPositiveOdd, boolean hasInRange);
}

class MtInt implements  IInt {
    MtInt(){}

    public boolean isVariantA(){
        return false;
    }

    public boolean isVariantAHelper(boolean hasEven, boolean hasPositiveOdd, boolean hasInRange){
        return hasEven && hasPositiveOdd && hasInRange;
    }

    public boolean isVariantB(){
        return false;
    }

    public boolean isVariantBHelper(boolean hasEven, boolean hasPositiveOdd, boolean hasInRange){
        return hasEven && hasPositiveOdd && hasInRange;
    }

    public boolean isVariantC(){
        return false;
    }

    public boolean isVariantCHelper(boolean hasEven, boolean hasPositiveOdd, boolean hasInRange){
        return hasEven && hasPositiveOdd && hasInRange;
    }
}

class ConsLoInt implements IInt {
    int first;
    IInt rest;

    ConsLoInt(int first, IInt rest){
        this.first = first;
        this.rest = rest;
    }

    public boolean isVariantA(){
        return this.isVariantAHelper(false, false, false);
    }

    public boolean isVariantAHelper(boolean hasEven, boolean hasPositiveOdd, boolean hasInRange){
        boolean newHasEven = hasEven(this.first) || hasEven;
        boolean newHasPositiveOdd = hasPositiveOdd(this.first) || hasPositiveOdd;
        boolean newHasInRange = hasInRange(this.first) || hasInRange;

        return this.rest.isVariantAHelper(newHasEven, newHasPositiveOdd, newHasInRange);
    }

    public boolean isVariantB(){
        return this.isVariantBHelper(false, false, false);
    }

    public boolean isVariantBHelper(boolean hasEven, boolean hasPositiveOdd, boolean hasInRange){
        if (!hasEven && hasEven(first)){
            return rest.isVariantBHelper(true, hasPositiveOdd, hasInRange);
        } else if (!hasPositiveOdd && hasPositiveOdd(first)){
            return  rest.isVariantBHelper(hasEven, true, hasInRange);
        } else if (!hasInRange && hasInRange(first)){
            return rest.isVariantBHelper(hasEven, hasPositiveOdd, true);
        } else {
            return rest.isVariantBHelper(hasEven, hasPositiveOdd, hasInRange);
        }
    }

    public boolean isVariantC(){
        return this.isVariantCHelper(false, false, false);
    }

    public boolean isVariantCHelper(boolean hasEven, boolean hasPositiveOdd, boolean hasInRange){
        if (!hasEven && hasEven(first)){
            return rest.isVariantCHelper(true, hasPositiveOdd, hasInRange);
        } else if (!hasPositiveOdd && hasPositiveOdd(first)){
            return  rest.isVariantCHelper(hasEven, true, hasInRange);
        } else if (!hasInRange && hasInRange(first)){
            return rest.isVariantCHelper(hasEven, hasPositiveOdd, true);
        } else {
            return false;
        }
    }

    boolean hasEven(int number) {
        return number % 2 == 0;
    }

    boolean hasPositiveOdd(int number) {
        return number % 2 != 0 && number > 0;
    }

    boolean hasInRange(int number) {
        return number >= 5 && number <= 10;
    }
}

class ExamplesInt {
    IInt mt = new MtInt();
    IInt l1 = new ConsLoInt(6,  new ConsLoInt(5, this.mt));
    IInt l2 = new ConsLoInt(4,  new ConsLoInt(3, this.mt));
    IInt l3 = new ConsLoInt(6,  new ConsLoInt(5, new ConsLoInt(6, this.mt)));
    IInt l4 = new ConsLoInt(6,  new ConsLoInt(5, new ConsLoInt(42, new ConsLoInt(6, this.mt))));

    boolean testIsVariantA(Tester t) {
        return t.checkExpect(this.mt.isVariantA(), false) &&
                t.checkExpect(this.l1.isVariantA(), true) &&
                t.checkExpect(this.l2.isVariantA(), false);
    }

    boolean testIsVariantB(Tester t) {
        return t.checkExpect(this.mt.isVariantB(), false) &&
                t.checkExpect(this.l1.isVariantB(), false) &&
                t.checkExpect(this.l3.isVariantB(), true);
    }

    boolean testIsVariantC(Tester t) {
        return t.checkExpect(this.mt.isVariantC(), false) &&
                t.checkExpect(this.l3.isVariantC(), true) &&
                t.checkExpect(this.l4.isVariantC(), false);
    }
}

