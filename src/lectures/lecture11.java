package lectures;

import tester.Tester;

interface IShape11 {
    boolean sameShape(IShape11 other);
    boolean sameCircle11(Circle11 otherCircle);
    boolean sameRectangle(Rectangle otherRectangle);
    boolean isCircle11();
    boolean isRectangle();
    boolean isSquare11();
}

class Circle11 implements IShape11 {
    int x, y;
    int radius;

    Circle11(int x, int y, int radius){
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public boolean sameShape(IShape11 other){
//        if (other instanceof Circle11){
        if (other.isCircle11()){
            return this.sameCircle11((Circle11) other);
        } else {
            return false;
        }
    }

    public boolean isCircle11() { return true; }
    public boolean isRectangle() { return false; }
    public boolean isSquare11() { return false; }

    public boolean sameCircle11(Circle11 otherCircle11) {
        return this.x == otherCircle11.x &&
                this.y == otherCircle11.y &&
                this.radius == otherCircle11.radius;
    }

    public boolean sameRectangle(Rectangle otherRectangle) {
        return false;
    }

}

class Rectangle implements IShape11 {
    int x, y;
    int w, h;

    Rectangle(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean sameShape(IShape11 other){
//        if (other instanceof  Rectangle){
        if (other.isRectangle()){
            return this.sameRectangle((Rectangle) other);
        } else {
            return false;
        }
    }

    public boolean sameCircle11(Circle11 otherCircle11) {
        return false;
    }

    public boolean sameRectangle(Rectangle otherRectangle) {
        return this.x == otherRectangle.x &&
                this.y == otherRectangle.y &&
                this.w == otherRectangle.w &&
                this.h == otherRectangle.h;
    }

    public boolean isCircle11() { return false; }
    public boolean isRectangle() { return true; }
    public boolean isSquare11() { return false; }
}

class Square11 extends Rectangle {

    Square11(int x, int y, int size){
        super(x, y, size, size);
    }

    public boolean sameShape(IShape11 other) {
//        if (other instanceof Square11){
        if (other.isSquare11()){
            return this.sameSquare11((Square11) other);
        } else {
            return false;
        }
    }

    public boolean sameSquare11(Square11 other){
        return this.x == other.x &&
                this.y == other.y &&
                this.w == other.w;
    }

    @Override
    public boolean isRectangle() { return false; }

    @Override
    public boolean isSquare11() { return true; }
}

class ExamplesIShape11 {
    ExamplesIShape11(){}

    IShape11 c1 = new Circle11(3, 4, 5);
    IShape11 c2 = new Circle11(4, 5, 6);
    IShape11 c3 = new Circle11(3, 4, 5);

    IShape11 r1 = new Rectangle(3, 4, 5, 5);
    IShape11 r2 = new Rectangle(4, 5, 6, 7);
    IShape11 r3 = new Rectangle(3, 4, 5, 5);

    IShape11 s1 = new Square11(3, 4, 5);

    boolean testIShape11Same(Tester t){
        return  t.checkExpect(c1.sameShape(c2), false) &&
                t.checkExpect(c2.sameShape(c1), false) &&
                t.checkExpect(c1.sameShape(c3), true) &&
                t.checkExpect(c3.sameShape(c1), true) &&

                t.checkExpect(r1.sameShape(r2), false) &&
                t.checkExpect(r2.sameShape(r1), false) &&
                t.checkExpect(r1.sameShape(r3), true) &&
                t.checkExpect(r3.sameShape(r1), true) &&

                t.checkExpect(r1.sameShape(c1), false) &&
                t.checkExpect(c1.sameShape(r1), false) &&

                t.checkExpect(r1.sameShape(s1), false) &&
                t.checkExpect(s1.sameShape(r1), false);
    }
}