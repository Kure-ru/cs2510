package assignments.assignment03;

import tester.Tester;

interface ILoString {
    String combine();
    ILoString sort();
    ILoString insert(String str);
    boolean isSorted();
    boolean isSortedHelper(String previous);
    ILoString interleave(ILoString that);
    ILoString merge(ILoString that);
    ILoString mergeHelper(String first, ILoString rest);
    ILoString reverse();
    ILoString reverseHelper(ILoString mt);
    boolean isDoubledList();
    boolean isDoubledListHelper(String first);
    boolean isPalindromeList();
}

class MtLoString implements ILoString {
    MtLoString(){}

    public String combine() {
        return "";
    }

    @Override
    public ILoString sort() {
        return this;
    }

    @Override
    public ILoString insert(String str) {
        return new ConsLoString(str, this);
    }

    @Override
    public boolean isSorted() {
        return true;
    }

    @Override
    public boolean isSortedHelper(String previous) {
        return true;
    }

    @Override
    public ILoString interleave(ILoString that) {
        return that;
    }

    @Override
    public ILoString merge(ILoString that) {
        return that;
    }

    @Override
    public ILoString mergeHelper(String first, ILoString rest) {
        return new ConsLoString(first, rest);
    }

    @Override
    public ILoString reverse() {
        return this;
    }

    public ILoString reverseHelper(ILoString mt){
        return mt;
    }

    @Override
    public boolean isDoubledList() {
        return true;
    }

    @Override
    public boolean isDoubledListHelper(String first) {
        return true;
    }

    @Override
    public boolean isPalindromeList() {
        return true;
    }
}

class ConsLoString implements ILoString {
    String first;
    ILoString rest;

    ConsLoString(String first, ILoString rest){
        this.first = first;
        this.rest = rest;
    }

    public String combine(){
        return this.first.concat(this.rest.combine());
    }

    @Override
    public ILoString sort() {
        return this.rest.sort().insert(this.first);
    }

    @Override
    public ILoString insert(String str) {
        if (this.first.toLowerCase().compareTo(str.toLowerCase()) < 0){
            return new ConsLoString(this.first, this.rest.insert(str));
        } else {
            return new ConsLoString(str, this);
        }
    }

    @Override
    public boolean isSorted() {
        return this.rest.isSortedHelper(this.first);
    }

    @Override
    public boolean isSortedHelper(String previous){
        return this.isBefore(previous) && this.rest.isSortedHelper(this.first);
    }

    @Override
    public ILoString interleave(ILoString that) {
        return new ConsLoString(first, that.interleave(rest));
    }

    @Override
    public ILoString merge(ILoString that) {
        return that.mergeHelper(this.first, this.rest);
    }

    public ILoString mergeHelper(String first, ILoString rest){
        if (isBefore(first)){
            return new ConsLoString(first, this.merge(rest));
        } else {
            return new ConsLoString(this.first, this.rest.merge(new ConsLoString(first, rest)));
        }
    }

    @Override
    public ILoString reverse() {
        return this.reverseHelper(new MtLoString());
    }

    public ILoString reverseHelper(ILoString mt){
        return this.rest.reverseHelper(new ConsLoString(first, mt));
    }

    public boolean isDoubledList() {
        return rest.isDoubledListHelper(this.first) ;
    }

    public boolean isDoubledListHelper(String first) {
        return first.equals(this.first) && rest.isDoubledList();
    }

    @Override
    public boolean isPalindromeList() {
        return this.interleave(this.reverse()).isDoubledList();
    }

    boolean isBefore(String str){
        return str.toLowerCase().compareTo(this.first.toLowerCase()) <= 0;
    }

}

class ExamplesStrings{
    ILoString mary = new ConsLoString("Mary ",
            new ConsLoString("had ",
                    new ConsLoString("a ",
                            new ConsLoString("little ",
                                    new ConsLoString("lamb.", new MtLoString())))));
    ILoString sortedMary = new ConsLoString("a ",
            new ConsLoString("had ",
                    new ConsLoString("lamb.",
                            new ConsLoString("little ",
                                    new ConsLoString("Mary ", new MtLoString())))));

    ILoString bob = new ConsLoString("Bob ",
            new ConsLoString("had ",
                    new ConsLoString("a ",
                            new ConsLoString("big ",
                                    new ConsLoString("dog.", new MtLoString())))));

    ILoString sortedBob = new ConsLoString("a ",
            new ConsLoString("big ",
                    new ConsLoString("Bob ",
                            new ConsLoString("dog.",
                                    new ConsLoString("had ", new MtLoString())))));

    boolean testCombine(Tester t){
        return t.checkExpect(this.mary.combine(), "Mary had a little lamb.");
    }

    boolean testSort(Tester t) {
        return t.checkExpect(this.mary.sort(), sortedMary) &&
                t.checkExpect(this.bob.sort(), sortedBob);
    }

    boolean testIsSorted(Tester t) {
        return t.checkExpect(mary.isSorted(), false) &&
                t.checkExpect(sortedMary.isSorted(), true) &&
                t.checkExpect(sortedBob.isSorted(), true);
    }

    boolean testInterleave(Tester t){
        return t.checkExpect(mary.interleave(bob), new ConsLoString("Mary ",
                new ConsLoString("Bob ",
                        new ConsLoString("had ",
                                new ConsLoString("had ",
                                        new ConsLoString("a ",
                                                new ConsLoString("a ",
                                                        new ConsLoString("little ",
                                                                new ConsLoString("big ",
                                                                        new ConsLoString("lamb.",
                                                                                new ConsLoString("dog.", new MtLoString())))))))))));
    }

    boolean testMerge(Tester t) {
        return t.checkExpect(sortedMary.merge(sortedBob),
                new ConsLoString("a ",
                        new ConsLoString("a ",
                                new ConsLoString("big ",
                                        new ConsLoString("Bob ",
                                                new ConsLoString("dog.",
                                                        new ConsLoString("had ",
                                                                new ConsLoString("had ",
                                                                        new ConsLoString("lamb.",
                                                                                new ConsLoString("little ",
                                                                                        new ConsLoString("Mary ", new MtLoString())))))))))));
    }

    boolean testReverse(Tester t){
        return t.checkExpect(mary.reverse(), new ConsLoString("lamb.",
                new ConsLoString("little ",
                        new ConsLoString("a ",
                                new ConsLoString("had ",
                                        new ConsLoString("Mary ", new MtLoString()))))));
    }

    boolean testIsDoubledList(Tester t){
        return t.checkExpect(mary.isDoubledList(), false) &&
                t.checkExpect(new ConsLoString("a ",
                        new ConsLoString("a ",
                                new ConsLoString("b ",
                                        new ConsLoString("b ", new MtLoString())))).isDoubledList(), true);
    }

    boolean testIsPalindromeList(Tester t){
        return t.checkExpect(mary.isPalindromeList(), false) &&
                t.checkExpect(new ConsLoString("a ",
                        new ConsLoString("b ",
                                new ConsLoString("b ",
                                        new ConsLoString("a ", new MtLoString())))).isPalindromeList(), true);
    }
}
