package lectures;

import tester.Tester;

interface IShape {
    double area();
    double distanceToOrigin();
    boolean isBiggerThan(IShape other);
}

class CartPt{
    int x;
    int y;

    CartPt(int x, int y){
        this.x = x;
        this.y = y;
    }

    double distanceToOrigin(){
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
}

class Square implements IShape {
    CartPt topLeftCorner;
    int size;
    String color;

    Square(CartPt topLeftCorner, int size, String color){
        this.topLeftCorner = topLeftCorner;
        this.size = size;
        this.color = color;
    }

    public double area(){
        return size * size;
    }

    public double distanceToOrigin(){
        return this.topLeftCorner.distanceToOrigin();
    }

    public boolean isBiggerThan(IShape other){
        return this.area() > other.area();
    }
}

class Circle implements IShape {
    CartPt center;
    int radius;
    String color;

    Circle(CartPt center, int radius, String color){
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    public double area(){
        return Math.PI * radius * radius;
    }

    public double distanceToOrigin(){
        return this.center.distanceToOrigin() - this.radius;
    }

    public boolean isBiggerThan(IShape other){
        return this.area() > other.area();
    }
}

class Triangle implements IShape {
    CartPt topLeftCorner;
    int base;
    int height;
    String color;

    Triangle(CartPt topLeftCorner, int base, int height, String color){
        this.topLeftCorner = topLeftCorner;
        this.base = base;
        this.height = height;
        this.color = color;
    }

    public double area() {
        return 0.5 * base * height;
    }

    public double distanceToOrigin(){
        return this.topLeftCorner.distanceToOrigin();
    }

    public boolean isBiggerThan(IShape other){
        return this.area() > other.area();
    }
}

class ShapeUtil {
    ShapeUtil(){}

    double twiceTheArea(IShape shape){
        return shape.area() * 2;
    }
}

class ExamplesIShape{
    ExamplesIShape(){};

    ShapeUtil shapeUtil = new ShapeUtil();

    CartPt pt1 = new CartPt(0, 0);
    CartPt pt2 = new CartPt(3, 4);
    CartPt pt3 = new CartPt(7, 1);

    IShape c1 = new Circle(new CartPt(50, 50), 10, "red");
    IShape c2 = new Circle(new CartPt(50, 50), 30, "red");
    IShape c3 = new Circle(new CartPt(30, 100), 30, "blue");

    IShape s1 = new Square(new CartPt(50, 50), 30, "red");
    IShape s2 = new Square(new CartPt(50, 50), 50, "red");
    IShape s3 = new Square(new CartPt(20, 40), 10, "green");

    boolean testShapeUtil(Tester t){
        return t.checkInexact(shapeUtil.twiceTheArea(s1), 1800.0, 0.01);
    }

    boolean testCircleArea(Tester t) {
        return
                t.checkInexact(this.c1.area(), 314.15, 0.01);
    }

    boolean testSquareArea(Tester t) {
        return
                t.checkInexact(this.s1.area(), 900.0, 0.01);
    }

    boolean testDistanceToOrigin(Tester t) {
        return
                t.checkInexact(this.pt1.distanceToOrigin(), 0.0, 0.001) &&
                        t.checkInexact(this.pt2.distanceToOrigin(), 5.0, 0.001);
    }

    boolean testCircleIsBiggerThan(Tester t) {
        return
                t.checkExpect(this.c1.isBiggerThan(this.c2), false) &&
                        t.checkExpect(this.c2.isBiggerThan(this.c1), true) &&
                        t.checkExpect(this.c1.isBiggerThan(this.s1), false) &&
                        t.checkExpect(this.c1.isBiggerThan(this.s3), true);
    }

    boolean testSquareIsBiggerThan(Tester t) {
        return
                t.checkExpect(this.s1.isBiggerThan(this.s2), false) &&
                        t.checkExpect(this.s2.isBiggerThan(this.s1), true) &&
                        t.checkExpect(this.s1.isBiggerThan(this.c1), true) &&
                        t.checkExpect(this.s3.isBiggerThan(this.c1), false);
    }
}