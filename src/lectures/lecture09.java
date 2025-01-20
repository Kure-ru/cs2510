package lectures;

import tester.Tester;

/**
 * HtDC Lectures
 * Lecture 7: Abstract classes
 *
 * Copyright 2013 Viera K. Proulx
 * This program is distributed under the terms of the
 * GNU Lesser General Public License (LGPL)
 *
 * @since 14 September 2013
 */

/*
                                +----------------------------------------+
                                |  +------------------------------------+|
                                |  |                                    ||
                                v  v                                    ||
                    +----------------------------+                      ||
                    | IShape                     |                      ||
                    +----------------------------+                      ||
                    | double area()              |                      ||
                    | double distTo0()           |                      ||
                    | IShape grow(int inc)       |                      ||
                    | boolean biggerThan(IShape) |                      ||
                    | boolean contains(CartPt)   |                      ||
                    +----------------------------+                      ||
                                   |                                    ||
                                  / \                                   ||
                                  ---                                   ||
                                   |                                    ||
               ---------------------------------------------            ||
               |                                           |            ||
   +-----------------------------------+ +----------------------------+ ||
   | abstract AShape                   | | Combo                      | ||
   +-----------------------------------+ +----------------------------+ ||
+--| CartPt loc                        | | IShape top                 |-+|
|  | String color                      | | IShape bot                 |--+
|  +-----------------------------------+ +----------------------------+
|  | abstract double area()            | | double area()              |
|  | double distTo0()                  | | double distTo0()           |
|  | abstract IShape grow(int inc)     | | IShape grow(int inc)       |
|  | boolean biggerThan(IShape)        | | boolean biggerThan(IShape) |
|  | abstract boolean contains(CartPt) | | boolean contains(CartPt)   |
|  +-----------------------------------+ +----------------------------+
|                                  |
|                                 / \
|                                 ---
|                                  |
|                --------------------------------
|                |                              |
| +--------------------------+  +--------------------------+
| | Circle                   |  | Rect                     |
| +--------------------------+  +--------------------------+
| | int radius               |  | int width                |
| +--------------------------+  | int height               |
| | double area()            |  +--------------------------+
| | double distTo0()         |  | double area()            |
| | IShape grow(int inc)     |  | IShape grow(int inc)     |
| | boolean contains(CartPt) |  | boolean contains(CartPt) |
| +--------------------------+  +--------------------------+
|                                            / \
|                                            ---
|                                             |
|                               +-----------------------------+
|                               | Square                      |
|                               +-----------------------------+
|                               +-----------------------------+
|                               | IShape grow(int inc)        |
|                               +-----------------------------+
|
+-------+
        |
        v
  +-----------------------+
  | CartPt                |
  +-----------------------+
  | int x                 |
  | int y                 |
  +-----------------------+
  | double distTo0()      |
  | double distTo(CartPt) |
  +-----------------------+
 */

// to represent a geometric shape
interface IShape9 {
    // to compute the area of this shape
    public double area();

    // to compute the distance form this shape to the origin
    public double distTo0();

    // to increase the size of this shape by the given increment
    public IShape9 grow(int inc);

    // is the area of this shape is bigger than the area of the given shape?
    public boolean biggerThan(IShape9 that);

    // does this shape (including the boundary) contain the given point?
    public boolean contains(CartPt9 pt);
}

abstract class AShape implements IShape9 {
    CartPt9 location;
    String color;

    AShape(CartPt9 location, String color) {
        this.location = location;
        this.color = color;
    }

    public boolean biggerThan(IShape9 that){
        return this.area() >= that.area();
    }

    public double distTo0(){
        return this.location.distTo0();
    }

    public abstract double area();
    public abstract IShape9 grow(int inc);
    public abstract boolean contains(CartPt9 pt);
}

class Combo implements IShape9 {
    IShape9 top;
    IShape9 bot;

    Combo(IShape9 top, IShape9 bot) {
        this.top = top;
        this.bot = bot;
    }

    /* TEMPLATE
    FIELDS
    ... this.top ...           -- IShape
    ... this.bot ...           -- IShape
    METHODS
    ... this.area() ...                  -- double
    ... this.distTo0() ...               -- double
    ... this.grow(int) ...               -- IShape
    ... this.biggerThan(IShape) ...      -- boolean
    ... this.contains(CartPt) ...        -- boolean
    METHODS FOR FIELDS:
    ... this.top.area() ...                  -- double
    ... this.top.distTo0() ...               -- double
    ... this.top.grow(int) ...               -- IShape
    ... this.top.biggerThan(IShape) ...      -- boolean
    ... this.top.contains(CartPt) ...        -- boolean

    ... this.bot.area() ...                  -- double
    ... this.bot.distTo0() ...               -- double
    ... this.bot.grow(int) ...               -- IShape
    ... this.bot.biggerThan(IShape) ...      -- boolean
    ... this.bot.contains(CartPt) ...        -- boolean
    */
    // to compute the area of this shape
    public double area() {
        return this.top.area() + this.bot.area();
    }

    // to compute the distance form this shape to the origin
    public double distTo0() {
        return Math.min(this.top.distTo0(), this.bot.distTo0());
    }

    // to increase the size of this shape by the given increment
    public IShape9 grow(int inc) {
        return new Combo(this.top.grow(inc), this.bot.grow(inc));
    }

    // is the area of this shape is bigger than the area of the given shape?
    public boolean biggerThan(IShape9 that) {
        return this.area() >= that.area();
    }

    // does this shape (including the boundary) contain the given point?
    public boolean contains(CartPt9 pt) {
        return this.top.contains(pt) || this.bot.contains(pt);
    }
}


// to represent a circle
class Circle9 extends AShape {
    int radius;

    Circle9(CartPt9 center, int radius, String color) {
        super(center, color);
        this.radius = radius;
    }

    /*  TEMPLATE
     Fields:
     ... this.ctr ...             -- CartPt
     ... this.rad ...             -- int
     ... this.color ...           -- String
     Methods:
     ... this.area() ...                  -- double
     ... this.distTo0() ...               -- double
     ... this.grow(int) ...               -- IShape
     ... this.biggerThan(IShape) ...      -- boolean
     ... this.contains(CartPt) ...        -- boolean
     Methods for fields:
     ... this.ctr.distTo0() ...           -- double
     ... this.ctr.distTo(CartPt) ...      -- double
     */

    public double area(){
        return Math.PI * this.radius * this.radius;
    }

    @Override
    public double distTo0(){
        return this.location.distTo0() - this.radius;
    }

    public IShape9 grow(int inc){
        return new Circle9(this.location, this.radius + inc, this.color);
    }

    public boolean contains(CartPt9 pt){
        return this.location.distTo(pt) <= this.radius;
    }

}

class Square9 extends Rect {
    int size;

    Square9(CartPt9 nw, int size, String color) {
        super(nw, size, size, color);
        this.size = size;
    }

    /*  TEMPLATE
     Fields:
     ... this.nw ...              -- CartPt
     ... this.size ...            -- int
     ... this.color ...           -- String
     Methods:
     ... this.area() ...                  -- double
     ... this.distTo0() ...               -- double
     ... this.grow(int) ...               -- IShape
     ... this.biggerThan(IShape) ...      -- boolean
     ... this.contains(CartPt) ...        -- boolean
     Methods for fields:
     ... this.nw.distTo0() ...            -- double
     ... this.nw.distTo(CartPt) ...       -- double
     */

    // to increase the size of this shape by the given increment
    public IShape9 grow(int inc){
        return new Square9(this.location, this.size + inc, this.color);
    }

}

class Rect extends AShape {
    int width;
    int height;

    Rect(CartPt9 nw, int width, int height, String color) {
        super(nw, color);
        this.width = width;
        this.height = height;
    }

    /* TEMPLATE
     FIELDS
     ... this.nw ...              -- CartPt
     ... this.width ...           -- int
     ... this.height ...          -- int
     ... this.color ...           -- String
     METHODS
     ... this.area() ...                  -- double
     ... this.distTo0() ...               -- double
     ... this.grow(int inc) ...           -- IShape
     ... this.biggerThan(IShape that) ... -- boolean
     ... this.contains(CartPt pt) ...     -- boolean
     METHODS FOR FIELDS:
     ... this.nw.distTo0() ...        -- double
     ... this.nw.distTo(CartPt) ...   -- double
     */

    // to compute the area of this shape
    public double area(){
        return this.width * this.height;
    }

    // to increase the size of this shape by the given increment
    public IShape9 grow(int inc){
        return new Rect(this.location, this.width + inc, this.height + inc,
                this.color);
    }

    // does this shape (including the boundary) contain the given point?
    public boolean contains(CartPt9 pt){
        return (this.location.x <= pt.x) && (pt.x <= this.location.x + this.width) &&
                (this.location.y <= pt.y) && (pt.y <= this.location.y + this.height);
    }
}


// to represent a Cartesian point
class CartPt9 {
    int x;
    int y;

    CartPt9(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /* TEMPLATE
     FIELDS
     ... this.x ...          -- int
     ... this.y ...          -- int
     METHODS
     ... this.distTo0() ...        -- double
     ... this.distTo(CartPt) ...   -- double
     */

    // to compute the distance form this point to the origin
    public double distTo0(){
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    // to compute the distance form this point to the given point
    public double distTo(CartPt9 pt){
        return Math.sqrt((this.x - pt.x) * (this.x - pt.x) +
                (this.y - pt.y) * (this.y - pt.y));
    }
}

class ExamplesShapes {
    ExamplesShapes() {}

    CartPt9 pt1 = new CartPt9(0, 0);
    CartPt9 pt2 = new CartPt9(3, 4);
    CartPt9 pt3 = new CartPt9(7, 1);

    IShape9 c1 = new Circle9(new CartPt9(50, 50), 10, "red");
    IShape9 c2 = new Circle9(new CartPt9(50, 50), 30, "red");
    IShape9 c3 = new Circle9(new CartPt9(30, 100), 30, "blue");

    IShape9 s1 = new Square9(new CartPt9(50, 50), 30, "red");
    IShape9 s2 = new Square9(new CartPt9(50, 50), 50, "red");
    IShape9 s3 = new Square9(new CartPt9(20, 40), 10, "green");

    IShape9 r1 = new Rect(new CartPt9(50, 50), 30, 20, "red");
    IShape9 r2 = new Rect(new CartPt9(50, 50), 50, 40, "red");
    IShape9 r3 = new Rect(new CartPt9(20, 40), 10, 20, "green");

    IShape9 cb1 = new Combo(this.r1, this.c1);
    IShape9 cb2 = new Combo(this.r2, this.r3);
    IShape9 cb3 = new Combo(this.cb1, this.cb2);


    boolean testDistTo0(Tester t) {
        return
                t.checkInexact(this.pt1.distTo0(), 0.0, 0.001) &&
                        t.checkInexact(this.pt2.distTo0(), 5.0, 0.001);
    }

    boolean testDistTo(Tester t) {
        return
                t.checkInexact(this.pt1.distTo(this.pt2), 5.0, 0.001) &&
                        t.checkInexact(this.pt2.distTo(this.pt3), 5.0, 0.001);
    }

    boolean testCircleArea(Tester t) {
        return
                t.checkInexact(this.c1.area(), 314.15, 0.01);
    }

    boolean testSquareArea(Tester t) {
        return
                t.checkInexact(this.s1.area(), 900.0, 0.01);
    }

    boolean testRectArea(Tester t) {
        return
                t.checkInexact(this.r1.area(), 600.0, 0.01);
    }

    boolean testCircleDistTo0(Tester t) {
        return
                t.checkInexact(this.c1.distTo0(), 60.71, 0.01) &&
                        t.checkInexact(this.c3.distTo0(), 74.40, 0.01);
    }

    boolean testSquareDistTo0(Tester t) {
        return
                t.checkInexact(this.s1.distTo0(), 70.71, 0.01) &&
                        t.checkInexact(this.s3.distTo0(), 44.72, 0.01);
    }

    boolean testRectDistTo0(Tester t) {
        return
                t.checkInexact(this.r1.distTo0(), 70.71, 0.01) &&
                        t.checkInexact(this.r3.distTo0(), 44.72, 0.01);
    }

    boolean testCircleGrow(Tester t) {
        return
                t.checkExpect(this.c1.grow(20), this.c2);
    }

    boolean testSquareGrow(Tester t) {
        return
                t.checkExpect(this.s1.grow(20), this.s2);
    }

    boolean testRectGrow(Tester t) {
        return
                t.checkExpect(this.r1.grow(20), this.r2);
    }

    boolean testCircleBiggerThan(Tester t) {
        return
                t.checkExpect(this.c1.biggerThan(this.c2), false) &&
                        t.checkExpect(this.c2.biggerThan(this.c1), true) &&
                        t.checkExpect(this.c1.biggerThan(this.s1), false) &&
                        t.checkExpect(this.c1.biggerThan(this.s3), true);
    }

    boolean testSquareBiggerThan(Tester t) {
        return
                t.checkExpect(this.s1.biggerThan(this.s2), false) &&
                        t.checkExpect(this.s2.biggerThan(this.s1), true) &&
                        t.checkExpect(this.s1.biggerThan(this.c1), true) &&
                        t.checkExpect(this.s3.biggerThan(this.c1), false);
    }

    boolean testRectBiggerThan(Tester t) {
        return
                t.checkExpect(this.r1.biggerThan(this.r2), false) &&
                        t.checkExpect(this.r2.biggerThan(this.r1), true) &&
                        t.checkExpect(this.r1.biggerThan(this.c1), true) &&
                        t.checkExpect(this.r3.biggerThan(this.s1), false);
    }

    boolean testCircleContains(Tester t) {
        return
                t.checkExpect(this.c1.contains(new CartPt9(100, 100)), false) &&
                        t.checkExpect(this.c2.contains(new CartPt9(40, 60)), true);
    }

    boolean testSquareContains(Tester t) {
        return
                t.checkExpect(this.s1.contains(new CartPt9(100, 100)), false) &&
                        t.checkExpect(this.s2.contains(new CartPt9(55, 60)), true);
    }

    boolean testRectContains(Tester t) {
        return
                t.checkExpect(this.r1.contains(new CartPt9(100, 100)), false) &&
                        t.checkExpect(this.r2.contains(new CartPt9(55, 60)), true);
    }

    boolean testShapeArea(Tester t) {
        return
                t.checkInexact(this.cb1.area(), 914.15926, 0.01) &&
                        t.checkInexact(this.cb2.area(), 2200.0, 0.01) &&
                        t.checkInexact(this.cb3.area(), 3114.15926, 0.01);
    }

    boolean testShapeDistTo0(Tester t) {
        return
                t.checkInexact(this.cb1.distTo0(), 60.71, 0.01) &&
                        t.checkInexact(this.cb2.distTo0(), 44.72, 0.01) &&
                        t.checkInexact(this.cb3.distTo0(), 44.72, 0.01);
    }

    boolean testShapeGrow(Tester t) {
        return
                t.checkExpect(this.cb1.grow(20), new Combo(this.r2, this.c2));
    }

    boolean testShapeBiggerThan(Tester t) {
        return
                t.checkExpect(this.c1.biggerThan(this.cb1), false) &&
                        t.checkExpect(this.s2.biggerThan(this.cb1), true) &&
                        t.checkExpect(this.r2.biggerThan(this.cb1), true) &&
                        t.checkExpect(this.r3.biggerThan(this.cb1), false) &&

                        t.checkExpect(this.cb2.biggerThan(this.r1), true) &&
                        t.checkExpect(this.cb1.biggerThan(this.r2), false) &&
                        t.checkExpect(this.cb1.biggerThan(this.c1), true) &&
                        t.checkExpect(this.cb1.biggerThan(this.c3), false) &&
                        t.checkExpect(this.cb1.biggerThan(this.s2), false) &&
                        t.checkExpect(this.cb2.biggerThan(this.s1), true) &&
                        t.checkExpect(this.cb1.biggerThan(this.cb3), false) &&
                        t.checkExpect(this.cb2.biggerThan(this.cb1), true);
    }

    boolean testShapeContains(Tester t) {
        return
                t.checkExpect(this.cb1.contains(new CartPt9(100, 100)), false) &&
                        t.checkExpect(this.cb2.contains(new CartPt9(55, 60)), true);
    }
}