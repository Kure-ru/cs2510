# How to Design Classes
## I. The Varieties of Data
### 1 Primitive Forms of Data

`int` 
- used to represent whole numbers (integers)
- range from `-2147483648` to `2147483647`

`double`
- represent real numbers (inexact numbers or decimal values)
- computation may result in approximations

`boolean`
- represent either `true` or `false`
- used for binary states like on/off, true/false, presence/absence

`String`
- represents a sequence of characters
- enclosed in double quotation marks `""`

### 2 Classes
In Java, a class is a blueprint or template that defines the structure of objects.
It specifies the **fields** (attribute) and the **methods** (functions) that objects of that class will have.

A class can represent complex data structures by grouping related pieces of information together. For example, in the context of coffee sales, you can create a `Coffee` class to represent the coffee type, its price, and its weight.

Fields represent the attributes of a class:
```java
class Coffee {
    String kind;
    int price;
    int weight;
}
```
A **constructor** is a special method used to create and initialize objects of a class.
```java
Coffee(String kind, int price, int weight) {
    this.kind = kind;
    this.price = price;
    this.weight = weight;
}
```
Once a class is defined, you can create **instances** of the class.
To keep track of multiple objects, you can create a class specifically for holding examples.

```java
class CoffeeExamples {
    Coffee kona = new Coffee("Kona", 2095, 100);
    Coffee ethi = new Coffee("Ethiopian", 800, 1000);
    Coffee colo = new Coffee("Colombian", 950, 20);
}
```
### 3 Class References, Object Containment

A class can contain objects of other classes as fields, not just primitive types.

In class diagrams, relationships between classes are shown using **containment arrows**, indicating one class has a field that is an instance of another class.
```java
// In a runner's log, two classes Entry and Date are Defined
Date d1 = new Date(5, 6, 2003);
Entry e1 = new Entry(d1, 5.3, 27, "Good");
```

### 4 Unions of Classes
**Union of classes** refers to a collection of different kinds of objects that are related but not the same. For example, in the case of shapes (like squares, circles, and dots), they are all part of a broader class but have different characteristics.

**Interface** is used to represent a union of related but distinct classes. For instance, `IShape` is an interface that groups all shapes (dots, squares, circles) without contributing any data itself.

In class diagrams, **inheritance arrows** (hollow heads) represent the relationship between a class and the interface it implements.

The **inheritance** relationship between a class and an interface is expressed with the keyword `implements` followed by an interface name.
```java
interface IShape { }

class Dot implements IShape { }
class Square implements IShape { }
class Circle implements IShape { }
```

#### 4.1 Types vs Classes

In Java, a **type** is either the name of an interface, a class, or a primitive type
(`int`, `double`, `boolean` or `String`).

```java
// s is of type IShape but holds an object of type Square
IShape s = new Square(...);
```

### 5 Unions, Self-References and Mutual References
#### 5.1 Containment in Unions
**Containment** refers to the idea of having one object or data structure contain other objects or data structures.

**Self-referential** data structures are those that reference themselves indirectly, such as a list where each element points to the next element, forming a chain. This creates a **circular reference** in the class diagram, where one class refers back to itself through its fields.

```java
interface ILog {}

class MTLog implements ILog {
    MTLog() {}
}

class ConsLog implements ILog {
    Entry fst;
    ILog rst;
    ConsLog(Entry fst, ILog rst) {
        this.fst = fst;
        this.rst = rst;
    }
}

class Entry {
    // Fields like date, distance, duration, comment
}
```

## II. Functional Methods 

### 10 Methods for Classes
#### 10.4 Conditional Computations
Methods often need to distinguish between different situations and compute results accordingly. 
To express **conditional computation**, Java provides **if-statement**, which can distinguish two possibilities.
```java
if (condition) {
    statement1 }
else {
    statement2 }
```

### 11 Methods and Object Containment

Classes can contain fields that are references to other classes, forming a **containment** relationship.
Methods operate on fields within their class and may delegate tasks to contained objects.
```java
// Rectangle.distance0() delegates computation of the distance to CartPt.distance0()
class Rectangle {
    ...

    double distance0() {
        return this.tlCorner.distance0();
    }
}

class CartPt {
    ...

    double distance0() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
}
```

### 12 Methods and Unions of Classes

#### 12.1 Example: Plain Geometric Shapes

A **wish list** is a checklist of the functions/methods still needed for making the program work.

**Union data** combines multiple types (e.g `Dot`, `Square`, `Circle` all implementing `IShape`)

Declaring method signatures in the interface enforces implementation in all subclasses.

### 14 Methods and Unions of Classes (Continued)
#### 14.1 How Libraries Work, Part 1: Drawing Geometric Shapes
Java provides a `Canva` class (via `draw` library) for rendering geometric shapes.
```java
import draw.∗;
import colors.∗;
import geometry.∗;
```

### 15 Methods and Classes with Mutual References

#### 15.1 Example: Managing a Runner’s Logs

Classes and methods can reference each other to form a structure where objects interact to solve problems.
Mutual references are common in recursive data structures like logs, where each element refers to another of the same type.
[see exercises](./ex_15.1~15.3.java)

#### 15.4 Example: River Systems
[see exercise](./ex_15.8.java)

## III. Abstracting with Classes

### 18 Similarities in Classes

#### 18.1 Common Fields, Superclasses

A **superclass** is introduced to capture commonalities between classes, which helps to reduce code duplication. 
> e.g. The superclass `Shape` **EXTENDS** `Dot`, `Square` and `Circle`.

Subclasses **inherit** fields, methods and obligations from the superclass. 

```java
// The parent class or superclass
class Shape implements IShape {
    CartPt loc;
    Shape(CartPt loc) {
        this.loc = loc;
    }
}

// Square is a subclass inherits from Shape
class Square extends Shape {
    int size;
    Square(CartPt loc,int size) {
        // super calls the constructor of a superclass
        super(loc);
        this.size = size;
    }
}
```

#### 18.2 Abstract Classes, Abstract Method

**Abstract methods** resolve the issue where a method (e.g., `area`) must be **implemented differently** in each subclass but still needs to be specified in a shared parent class.

Subclasses (like `Dot`, `Square`, and `Circle`) are required to **implement all abstract methods** from the abstract parent class (`AShape`) to become **concrete classes**.

[example code](../src/lectures/lecture09.java)

#### 18.3 Lifting Methods, Inheriting Methods

When multiple subclasses of an abstract class define the exact same method, you should **lift** the method into the abstract class to eliminate redundancy.
Process:
1. Move the common method into the abstract class.
2. Remove the method definitions from all subclasses.
3. Run tests to ensure correctness.

When lifting a method, if a subclass needs different behavior, you can **override the method** in that specific subclass.

[example code](../src/lectures/lecture09.java)

### 19 Designing Class Hierarchies with Methods

#### 19.4 Abstracting through the Creation of Unions

How to create a union retroactively:
1. **Comparison**: Examine commonalities in fields, methods, purpose.
2. **Abstraction**: If similarities are confirmed, create a common interface to define shared methods with compatible signatures
