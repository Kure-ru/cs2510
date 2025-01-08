package labs.lab1;

/*
;; to represent a person
;; A Person is (make-person String Number String)
(define-struct person [name age gender])

(define tim (make-person "Tim" 23 "Male"))
(define kate (make-person "Kate" 22 "Female"))
(define rebecca (make-person "Rebecca" 31 "Non-binary"))
 */

/*
 +---------------+
 | Person        |
 +---------------+
 | String name   |
 | int    age    |
 | String gender |
 +--------------+
 */

class Person {
    String name;
    int age;
    String gender;
    Address address;

    Person(String name, int age, String gender, Address address){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
    }
}

class Address {
    String city;
    String state;

    Address(String city, String state){
        this.city = city;
        this.state = state;
    }
}

class ExamplesPerson {
    ExamplesPerson(){};

    Address boston = new Address("Boston", "MA");
    Address warwick = new Address("Warwick", "RI");
    Address nashua = new Address("Nashua", "NH");

    Person tim = new Person("Tim", 23, "Male", this.boston);
    Person kate = new Person("Kate", 22, "Female", this.warwick);
    Person rebecca = new Person("Rebecca", 31, "Non-binary", this.nashua);
}