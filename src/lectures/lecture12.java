package lectures;

import tester.Tester;

interface IShape12 {
    boolean sameShape(IShape12 other);
    boolean sameCircle12(Circle12 otherCircle12);
    boolean sameRectangle12(Rectangle12 otherRectangle12);
    boolean sameSquare12(Square12 otherSquare12);
    boolean sameTriangle12(Triangle12 otherTriangle12);
    boolean isCircle12();
    boolean isRectangle12();
    boolean isSquare12();
    boolean isTriangle12();
}

abstract class AShape12 implements IShape12 {
    public boolean isCircle12(){ return false; }
    public boolean isRectangle12(){ return false; }
    public boolean isSquare12(){ return false; }
    public boolean isTriangle12(){ return false; }
    public boolean sameCircle12(Circle12 otherCircle12) { return false; }
    public boolean sameRectangle12(Rectangle12 otherRectangle12) { return false; }
    public boolean sameSquare12(Square12 otherSquare12) { return false; }
    public boolean sameTriangle12(Triangle12 otherTriangle12) { return false; }
}

class Circle12 extends AShape12 {
    int x, y;
    int radius;

    Circle12(int x, int y, int radius){
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public boolean sameShape(IShape12 other){
        return other.sameCircle12(this);
    }

    public boolean isCircle12() { return true; }

    public boolean sameCircle12(Circle12 otherCircle12) {
        return this.x == otherCircle12.x &&
                this.y == otherCircle12.y &&
                this.radius == otherCircle12.radius;
    }
}

class Rectangle12 extends AShape12 {
    int x, y;
    int w, h;

    Rectangle12(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean sameShape(IShape12 other){
        return other.sameRectangle12(this);
    }

    public boolean sameRectangle12(Rectangle12 otherRectangle12) {
        return this.x == otherRectangle12.x &&
                this.y == otherRectangle12.y &&
                this.w == otherRectangle12.w &&
                this.h == otherRectangle12.h;
    }

    public boolean isRectangle12() { return true; }
}

class Square12 extends Rectangle12 {

    Square12(int x, int y, int size){
        super(x, y, size, size);
    }

    public boolean sameShape(IShape12 other) {
        return other.sameSquare12(this);
    }

    public boolean sameSquare12(Square12 other){
        return this.x == other.x &&
                this.y == other.y &&
                this.w == other.w;
    }

    @Override
    public boolean isRectangle12() { return false; }

    @Override
    public boolean isSquare12() { return true; }

    @Override
    public boolean sameRectangle12(Rectangle12 otherRectangle12) { return false; }
}

class Triangle12 extends AShape12 {
    int x;
    int y;
    int base;
    int height;

    Triangle12(int x, int y, int base, int height){
        this.x = x;
        this.y = y;
        this.base = base;
        this.height = height;
    }

    @Override
    public boolean sameShape(IShape12 other){
        return other.sameTriangle12(this);
    }

    @Override
    public boolean isTriangle12(){
        return true;
    }

    @Override
    public boolean sameTriangle12(Triangle12 otherTriangle12) {
        return this.x == otherTriangle12.x &&
                this.y == otherTriangle12.y &&
                this.base == otherTriangle12.base &&
                this.height == otherTriangle12.height;
    }

}


class ExamplesIShape12 {
    ExamplesIShape12(){}

    IShape12 c1 = new Circle12(3, 4, 5);
    IShape12 c2 = new Circle12(4, 5, 6);
    IShape12 c3 = new Circle12(3, 4, 5);

    IShape12 r1 = new Rectangle12(3, 4, 5, 5);
    IShape12 r2 = new Rectangle12(4, 5, 6, 7);
    IShape12 r3 = new Rectangle12(3, 4, 5, 5);

    IShape12 s1 = new Square12(3, 4, 5);

    IShape12 t1 = new Triangle12(3, 4, 5, 5);
    IShape12 t2 = new Triangle12(3, 4, 5, 5);

    boolean testIShape12Same(Tester t){
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
                t.checkExpect(s1.sameShape(r1), false) &&

                t.checkExpect(t1.sameShape(s1), false) &&
                t.checkExpect(r1.sameShape(t1), false) &&
                t.checkExpect(t1.sameShape(t2), true);

    }
}