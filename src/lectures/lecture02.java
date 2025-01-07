package lectures;

/**
 * HtDC Lectures
 * Lecture 3: Data Definitions: Unions
 *
 * Copyright 2013 Viera K. Proulx
 * This program is distributed under the terms of the
 * GNU Lesser General Public License (LGPL)
 *
 * @since 29 August 2013
 */

/*
 *            +----------+
             | IStation |
             +----------+
             +----------+
                   |
                  / \
                  ---
                   |
       -----------------------
       |                     |
+--------------+    +-----------------+
| TStop        |    | CommStation     |
+--------------+    +-----------------+
| String name  |    | String name     |
| String line  |    | String line     |
| double price |    | boolean express |
+--------------+    +-----------------+
 */


/*
 * RACKET
 * ;; IStation is one of
 * ;; -- T Stop
 * ;; -- Commuter Station
 *
 * ;; T Stop is (make-tstop String String Number)
 * (define-struct tstop (name line price))
 *
 * ;; Commuter Station is (make-commstation String String Boolean)
 * (define-struct commstation (name line express))
 *
 * (define harvard (make-tstop "Harvard" "red" 1.25))
 * (define kenmore (make-tstop "Kenmore" "green" 1.25))
 * (define riverside (make-tstop "Riverside" "green" 2.50))
 *
 * (define backbay (make-commstation "Back Bay" "Framingham" true))
 * (define wnewton (make-commstation "West Newton" "Framingham" false))
 * (define wellhills (make-commstation "Wellesley Hills" "Worcester" false))
 */

// to represent a train station
interface IStation {
}

// to represent a subway station
class TStop implements IStation {
    String name;
    String line;
    double price;

    TStop(String name, String line, double price) {
        this.name = name;
        this.line = line;
        this.price = price;
    }
}

// to represent a stop on a commuter line
class CommStation implements IStation {
    String name;
    String line;
    boolean express;

    CommStation(String name, String line, boolean express) {
        this.name = name;
        this.line = line;
        this.express = express;
    }
}

class ExamplesIStation {
    ExamplesIStation() {
    }

    IStation harvard = new TStop("Harvard", "red", 1.25);
    IStation kenmore = new TStop("Kenmore", "green", 1.25);
    IStation riverside = new TStop("Riverside", "green", 2.50);

    IStation backbay = new CommStation("Back Bay", "Framingham", true);
    IStation wnewton = new CommStation("West Newton", "Framingham", false);
    IStation wellhills = new CommStation("Wellesley Hills", "Worcester", false);
}

// Self-referencial unions: Ancestor trees

/*
 *              +------------------+
             |  +-------------+ |
             |  |             | |
             v  v             | |
           +-----+            | |
           | IAT |            | |
           +-----+            | |
             / \              | |
             ---              | |
              |               | |
      -----------------       | |
      |               |       | |
+---------+   +-------------+ | |
| Unknown |   | Person      | | |
+---------+   +-------------+ | |
+---------+   | String name | | |
              | IAT mom     |-+ |
              | IAT dad     |---+
              +-------------+
 */

// to represent an ancestor tree
interface IAT {
}

// to represent an unknown member of an ancestor tree
class Unknown implements IAT {
    Unknown() {
    }
}

// to represent a person with the person's ancestor tree
class Person implements IAT {
    String name;
    IAT mom;
    IAT dad;

    Person(String name, IAT mom, IAT dad) {
        this.name = name;
        this.mom = mom;
        this.dad = dad;
    }
}

// examples and tests for the class hierarchy that represents
// ancestor trees
class ExamplesIAT {
    ExamplesIAT() {
    }

    IAT unknown = new Unknown();

    IAT mary = new Person("Mary", this.unknown, this.unknown);
    IAT robert = new Person("Robert", this.unknown, this.unknown);
    IAT john = new Person("John", this.unknown, this.unknown);

    IAT jane = new Person("Jane", this.mary, this.robert);
    IAT dan = new Person("Dan", this.jane, this.john);
}