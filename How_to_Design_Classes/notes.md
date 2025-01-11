# How to Design Classes
## 1 The Varieties of Data
### 1.1 Primitive Forms of Data

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

## 2 Classes
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
## 3 Class References, Object Containment

A class can contain objects of other classes as fields, not just primitive types.

In class diagrams, relationships between classes are shown using **containment arrows**, indicating one class has a field that is an instance of another class.
```java
// In a runner's log, two classes Entry and Date are Defined
Date d1 = new Date(5, 6, 2003);
Entry e1 = new Entry(d1, 5.3, 27, "Good");
```

## 4 Unions of Classes
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

### 4.1 Types vs Classes

In Java, a **type** is either the name of an interface, a class, or a primitive type
(`int`, `double`, `boolean` or `String`).

```java
// s is of type IShape but holds an object of type Square
IShape s = new Square(...);
```

## 5 Unions, Self-References and Mutual References
### 5.1 Containment in Unions
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