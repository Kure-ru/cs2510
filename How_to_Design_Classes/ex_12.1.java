/* Exercise 12.1
Figure 12 (page 28) displays a class hierarchy that represents basic geometric shapes—dots,
squares, and circles—for a drawing program.

Add the following four methods to the class hierarchy: (1)
one that computes the area of shapes; (2) one that produces the
distance of shapes to the origin; (3) another one that determines
whether some point is inside some shapes; (4) and a final one
creates the bounding box of shapes
*/

interface IShape {
    double area();
    double distTo();
    boolean in(CartPt p);
    Square bb();
}

class CartPt {
    int x;
    int y;

    CartPt(int x, int y){
        this.x = x;
        this.y = y;
    }

    double distTo(){
        return Math.sqrt((this.x * this.x) + (this.y * this.y));
    }

    boolean same(CartPt p){
        return this.x == p.x && this.y == p.y;
    }

    double distanceTo (CartPt p){
        return Math.sqrt((this.x - p.x) * (this.x - p.x) +
                (this.y - p.y) * (this.y - p.y));
    }

    CartPt translate(int delta){
        return new CartPt(this.x - delta, this.y - delta);
    }
}

class Dot implements IShape {
    CartPt loc;

    Dot(CartPt loc){
        this.loc = loc;
    }

    public double area() {
        return 0;
    }

    public double distTo(){
        return this.loc.distTo();
    }

    public boolean in(CartPt p){
        return this.loc.same(p);
    }

    public Square bb(){
        return new Square(this.loc, 1);
    }
}

class Square implements IShape {
    CartPt loc;
    int size;

    Square(CartPt loc, int size){
        this.loc = loc;
        this.size = size;
    }

    public double area() {
        return this.size * this.size;
    }

    public double distTo(){
        return this.loc.distTo();
    }

    public boolean in(CartPt p){
        return this.between(this.loc.x, p.x, this.size) &&
                this.between(this.loc.y, p.y, this.size);
    }

    boolean between(int left, int x, int width){
        return left <= x && x <= left + width;
    }

    public Square bb(){
        return this;
    }
}

class Circle implements IShape {
    CartPt loc;
    int radius;

    Circle(CartPt loc, int radius){
        this.loc = loc;
        this.radius = radius;
    }

    public double area() {
        return (Math.PI * this.radius * this.radius);
    }

    public double distTo(){
        return this.loc.distTo() - this.radius;
    }

    public boolean in(CartPt p){
        return this.loc.distanceTo(p) <= this.radius;
    }

    public Square bb(){
        return new Square(this.loc.translate(- this.radius), 2 * this.radius);
    }
}

class ShapeExamples {
    ShapeExamples() { }

    IShape dot = new Dot(new CartPt(4, 3));
    IShape squ = new Square(new CartPt(4, 3), 3);
    IShape squ2 = new Square(new CartPt(100, 200), 40);
    IShape cir = new Circle(new CartPt(12, 5), 2);
    IShape cir2 = new Circle(new CartPt(0, 0), 20);

    boolean testIShapeArea(Tester t){
        return t.checkExpect(dot.area(), 0.0) &&
                t.checkExpect(squ.area(), 9.0) &&
                t.checkInexact(cir.area(), 12.56, 0.01);
    }

    boolean testIShapeDistTo(Tester t){
        return t.checkExpect(dot.distTo(), 5.0) &&
                t.checkExpect(squ.distTo(), 5.0) &&
                t.checkExpect(cir.distTo(), 11.00) &&
                t.checkExpect(new CartPt(4, 3).distTo(), 5.0) &&
                t.checkExpect(new CartPt(12, 5).distTo(), 13.0);
    }

    boolean testIShapeIn(Tester t){
        return t.checkExpect(dot.in(new CartPt(4, 3)), true) &&
                t.checkExpect(dot.in(new CartPt(100, 200)), false) &&
                t.checkExpect(squ2.in(new CartPt(120, 220)), true) &&
                t.checkExpect(squ2.in(new CartPt(80, 220)), false) &&
                t.checkExpect(cir2.in(new CartPt(4, 3)), true) &&
                t.checkExpect(cir.in(new CartPt(100, 50)), false);

    }

    boolean testIShapeBb(Tester t){
        return t.checkExpect(dot.bb(), new Square(new CartPt(4, 3), 1)) &&
                t.checkExpect(squ.bb(), squ) &&
                t.checkExpect(cir.bb(), new Square(new CartPt(14, 7), 4));
    }
}