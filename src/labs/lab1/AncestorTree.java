package labs.lab1;

/*
;; An Ancestor Tree (AT) is one of
;; -- 'unknown
;; -- (make-tree Person AT AT)

;; A Person is defined as above

 */

interface IAT {

}

class Unknown implements IAT{
    Unknown(){}
}

class Person2 implements IAT {
    String name;
    IAT mom;
    IAT dad;

    Person2(String name, IAT mom, IAT dad){
        this.name = name;
        this.mom = mom;
        this.dad = dad;
    }
}

class ExamplesIAT {
    ExamplesIAT(){}

    Unknown unknown = new Unknown();
    IAT simone = new Person2("Simone", this.unknown, this.unknown);
    IAT rené = new Person2("René", this.unknown, this.unknown);

    IAT colette = new Person2("Colette", this.simone, this.rené);
    IAT jacques = new Person2("Jacques", this.unknown, this.unknown);

    IAT claire = new Person2("Claire", this.colette, this.jacques);
}