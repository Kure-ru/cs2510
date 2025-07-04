//package lectures;// 16.1 Flawed attempt 1: Processing shapes with existing types of function objects
//// A function object that computes the area of IShapes
////class ShapeArea implements IFunc<IShape, Double> {
////    public Double apply(IShape shape) {
////        /* Template:
////         * Fields:
////         * Methods:
////         * Methods of fields:
////         */
////    }
////}
//
//// 16.2.1 Flawed attempt 2: Casting
//interface IFunc3<A, R> {
//    R apply(A args);
//}
//
////
//// A function object that computes the area of IShapes
//// class ShapeArea implements IFunc<IShape, Double> {
////    public Double apply (IShape shape) {
////        if (shape instanceof Circle)
////        {
////            return Math.PI * ((Circle) shape).radius * ((Circle)shape).radius;
////        }
////        else
////        {
////            return (double) (((Rect)shape).w * ((Rect)shape).h);
////        }
////    }
////}
//
//// Represents a function object defined over Shapes that returns a Double
//interface IShapeVisitor<R> extends IFunc3<IShape, R> {
//    R visitCircle(Circle16 circle);
//    R visitRect(Rect16 rect);
//    R visitSquare(Square16 square);
//}
//
//abstract class ShapeArea implements IShapeVisitor<Double> {
//    public Double visitCircle(Circle16 circle) {
//        return Math.PI * circle.radius * circle.radius;
//    }
//
//    public Double visitRect(Rect16 rect) {
//        return (double) (rect.w * rect.h);
//    }
//
//    public Double visitSquare(Square16 square) {
//        return (double) (square.size * square.size);
//    }
//
//    public Double apply(IShape16 shape) {
//        return shape.accept(this);
//    }
//}
//
//abstract class ShapePerimeter implements IShapeVisitor<Double> {
//    public Double visitCircle(Circle16 circle) {
//        return 2 * Math.PI * circle.radius;
//    }
//
//    public Double visitRect(Rect16 rect) {
//        return (double) (2 * (rect.h + rect.w));
//    }
//
//    public Double visitSquare(Square16 square) {
//        return (double) (4 * square.size);
//    }
//
//    public Double apply (IShape16 shape){
//        return shape.accept(this);
//    }
//}
//
//interface IShape16 {
//    <R> double accept(IShapeVisitor<R> visitor);
//}
//
//class Circle16 implements IShape16 {
//    int x, y;
//    int radius;
//    String color;
//
//    Circle16(int x, int y, int r, String color) {
//        this.x = x;
//        this.y = y;
//        this.radius = r;
//        this.color = color;
//    }
//
//    public <R> double accept(IShapeVisitor<R> visitor) {
//        return (double) visitor.visitCircle(this);
//    }
//}
//
//class Rect16 implements IShape16 {
//    int x, y, w, h;
//    String color;
//
//    Rect16(int x, int y, int w, int h, String color) {
//        this.x = x;
//        this.y = y;
//        this.w = w;
//        this.h = h;
//        this.color = color;
//    }
//
//    public <R> double accept(IShapeVisitor<R> visitor) {
//        return (double) visitor.visitRect(this);
//    }
//}
//
//class Square16 implements IShape16 {
//    int x, y, size;
//    String color;
//
//    Square16(int x, int y, int size, String color) {
//        this.x = x;
//        this.y = y;
//        this.size = size;
//        this.color = color;
//    }
//
//    public <R> double accept(IShapeVisitor<R> visitor){
//        return (double) visitor.visitSquare(this);
//    }
//}
