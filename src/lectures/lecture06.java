package lectures;

import tester.Tester;

interface IAT06 {
    int count();
    int countHelper();
    int countFemaleAnc();
    int countFemaleAncHelper();
    boolean wellFormed();
    boolean wellFormedHelper(int yob);
}
class Unknown06 implements IAT06 {
    Unknown06() { }

    public int count(){
        return 0;
    }

    public int countHelper(){
        return 0;
    }

    public int countFemaleAnc(){
        return 0;
    }

    public int countFemaleAncHelper(){
        return 0;
    }

    public boolean wellFormed() {
        return true;
    }

    public boolean wellFormedHelper(int yob) {
        return true;
    }
}
class Person06 implements IAT06 {
    String name;
    int yob;
    boolean isMale;
    IAT06 mom;
    IAT06 dad;
    Person06(String name, int yob, boolean isMale, IAT06 mom, IAT06 dad) {
        this.name = name;
        this.yob = yob;
        this.isMale = isMale;
        this.mom = mom;
        this.dad = dad;
    }

    public int count(){
        return this.mom.countHelper() + this.dad.countHelper();
    }

    public int countHelper(){
        return 1 + this.mom.countHelper() + this.dad.countHelper();
    }

    public int countFemaleAnc(){
        return this.mom.countFemaleAncHelper() + this.dad.countFemaleAncHelper();
    }

    public int countFemaleAncHelper(){
        if (!this.isMale){
            return 1 + this.mom.countFemaleAncHelper() + this.dad.countFemaleAncHelper();
        } else {
            return this.mom.countFemaleAncHelper() + this.dad.countFemaleAncHelper();
        }
    }

    public boolean wellFormed() {
        return this.mom.wellFormedHelper(this.yob) &&
                this.dad.wellFormedHelper(this.yob);
    }

    public boolean wellFormedHelper(int childYob) {
        return this.yob < childYob &&
                this.mom.wellFormedHelper(this.yob) &&
                this.dad.wellFormedHelper(this.yob);
    }
}

interface ILoString {

}
class ConsLoString implements ILoString {
    String first;
    ILoString rest;
    ConsLoString(String first, ILoString rest) {
        this.first = first;
        this.rest = rest;
    }
}
class MtLoString implements ILoString {
    MtLoString() { }
}


class ExamplesIAT06 {
    IAT06 enid = new Person06("Enid", 1904, false, new Unknown06(), new Unknown06());
    IAT06 edward = new Person06("Edward", 1902, true, new Unknown06(), new Unknown06());
    IAT06 emma = new Person06("Emma", 1906, false, new Unknown06(), new Unknown06());
    IAT06 eustace = new Person06("Eustace", 1907, true, new Unknown06(), new Unknown06());

    IAT06 david = new Person06("David", 1925, true, new Unknown06(), this.edward);
    IAT06 daisy = new Person06("Daisy", 1927, false, new Unknown06(), new Unknown06());
    IAT06 dana = new Person06("Dana", 1933, false, new Unknown06(), new Unknown06());
    IAT06 darcy = new Person06("Darcy", 1930, false, this.emma, this.eustace);
    IAT06 darren = new Person06("Darren", 1935, true, this.enid, new Unknown06());
    IAT06 dixon = new Person06("Dixon", 1936, true, new Unknown06(), new Unknown06());

    IAT06 clyde = new Person06("Clyde", 1955, true, this.daisy, this.david);
    IAT06 candace = new Person06("Candace", 1960, false, this.dana, this.darren);
    IAT06 cameron = new Person06("Cameron", 1959, true, new Unknown06(), this.dixon);
    IAT06 claire = new Person06("Claire", 1956, false, this.darcy, new Unknown06());

    IAT06 bill = new Person06("Bill", 1980, true, this.candace, this.clyde);
    IAT06 bree = new Person06("Bree", 1981, false, this.claire, this.cameron);

    IAT06 andrew = new Person06("Andrew", 2001, true, this.bree, this.bill);

    boolean testIATCount(Tester t){
        return t.checkExpect(andrew.count(), 16);
    }

    boolean testIATCountFemaleAnc(Tester t){
        return t.checkExpect(andrew.countFemaleAnc(), 8);
    }

    boolean testIATWellFormed(Tester t){
        return t.checkExpect(andrew.wellFormed(), true);
    }

}