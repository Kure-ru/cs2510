package assignments.assignment02;

import tester.Tester;

interface IPicture {
    double getWidth();
    int countShape();
    int comboDepth();
    IPicture mirror();
    String pictureRecipe(int depth);
}

interface IOperation {
    double getWidth();
    int countShape();
    int comboDepth();
    IOperation mirror();
    String pictureRecipe(int depth);
}


class Shape implements IPicture {
    String kind;
    double size;

    Shape(String kind, double size){
        this.kind = kind;
        this.size = size;
    }

    public double getWidth() {
        return this.size;
    }

    public int countShape() {
        return 1;
    }

    public int comboDepth() {
        return 0;
    }

    public IPicture mirror() {
        return this;
    }

    public String pictureRecipe(int depth) {
        return this.kind;
    }
}

class Combo implements IPicture {
    String name;
    IOperation operation;

    Combo(String name, IOperation operation){
        this.name = name;
        this.operation = operation;
    }

    public double getWidth() {
        return this.operation.getWidth();
    }

    public int countShape() {
        return this.operation.countShape();
    }

    public int comboDepth() {
        return this.operation.comboDepth();
    }

    public IPicture mirror() {
        return new Combo(this.name, this.operation.mirror());
    }

    public String pictureRecipe(int depth) {
        if (depth == 0){
            return this.name;
        } else {
            return this.operation.pictureRecipe(depth);
        }
    }
}

class Scale implements IOperation {
    IPicture picture;

    Scale(IPicture picture){
        this.picture = picture;
    }

    public double getWidth() {
        return this.picture.getWidth() * 2;
    }

    public int countShape() {
        return this.picture.countShape();
    }

    public int comboDepth() {
        return this.picture.comboDepth() + 1;
    }

    public IOperation mirror() {
        return this;
    }

    public String pictureRecipe(int depth) {
        if (depth == 0) {
            return "";
        } else {
            return "scale(" + this.picture.pictureRecipe(depth - 1) + ")";
        }
    }
}

class Beside implements IOperation {
    IPicture picture1;
    IPicture picture2;

    Beside(IPicture picture1, IPicture picture2){
        this.picture1 = picture1;
        this.picture2 = picture2;
    }

    public double getWidth() {
        return this.picture1.getWidth() + this.picture2.getWidth();
    }

    public int countShape() {
        return this.picture1.countShape() + this.picture2.countShape();
    }

    public int comboDepth() {
        if (this.picture1.comboDepth() > this.picture2.comboDepth()) {
            return this.picture1.comboDepth() + 1;
        } else {
            return  this.picture2.comboDepth() + 1;
        }
    }

    public IOperation mirror() {
        return new Beside(this.picture2, this.picture1);
    }

    public String pictureRecipe(int depth) {
        if (depth == 0) {
            return "";
        } else {
            return "beside(" +
                    this.picture1.pictureRecipe(depth - 1) + ", " +
                    this.picture2.pictureRecipe(depth - 1) + ")";
        }
    }
}

class Overlay implements IOperation {
    IPicture picture1;
    IPicture picture2;

    Overlay(IPicture picture1, IPicture picture2){
        this.picture1 = picture1;
        this.picture2 = picture2;
    }

    public double getWidth() {
        if (this.picture1.getWidth() > this.picture2.getWidth()){
            return picture1.getWidth();
        } else {
            return picture2.getWidth();
        }
    }

    public int countShape() {
        return this.picture1.countShape() + this.picture2.countShape();
    }

    public int comboDepth() {
        if (this.picture1.comboDepth() > this.picture2.comboDepth()) {
            return this.picture1.comboDepth() + 1;
        } else {
            return  this.picture2.comboDepth() + 1;
        }
    }

    public IOperation mirror() {
        return this;
    }

    public String pictureRecipe(int depth) {
        if (depth == 0) {
            return "";
        } else {
            return "overlay(" +
                    this.picture1.pictureRecipe(depth - 1) + ", " +
                    this.picture2.pictureRecipe(depth - 1) + ")";
        }
    }
}

class ExamplesPicture {
    Shape circle = new Shape("circle", 20);
    Shape square = new Shape("square", 30);

    Combo bigCircle = new Combo("big circle", new Scale(circle));
    Combo squareOnCircle = new Combo("square on circle", new Overlay(square, bigCircle));
    Combo doubledSquareOnCircle = new Combo("doubled square on circle", new Beside(squareOnCircle, squareOnCircle));
    Combo bigSquare = new Combo("big square", new Scale(square));
    Combo circleOnSquare = new Combo("circle on square", new Overlay(circle, bigSquare));
    Combo doubledCircleOnSquare = new Combo("doubled circle on square", new Beside(circleOnSquare, circleOnSquare));
    Combo bigCircleBesideBigSquare = new Combo("big circle beside big square", new Beside(bigCircle, bigSquare));

    boolean testPictureGetWidth(Tester t){
        return t.checkExpect(circle.getWidth(), 20.0) &&
                t.checkExpect(bigCircle.getWidth(), 40.0) &&
                t.checkExpect(squareOnCircle.getWidth(), 40.0) &&
                t.checkExpect(doubledSquareOnCircle.getWidth(), 80.0);
    }

    boolean testPictureCountShape(Tester t){
        return t.checkExpect(bigSquare.countShape(), 1) &&
                t.checkExpect(circleOnSquare.countShape(), 2) &&
                t.checkExpect(doubledCircleOnSquare.countShape(), 4);
    }

    boolean testPictureComboDepth(Tester t){
        return t.checkExpect(bigCircle.comboDepth(), 1) &&
                t.checkExpect(squareOnCircle.comboDepth(), 2) &&
                t.checkExpect(doubledCircleOnSquare.comboDepth(), 3);
    }

    boolean testPictureMirror(Tester t){
        return t.checkExpect(bigCircle.mirror(), bigCircle) &&
                t.checkExpect(squareOnCircle.mirror(), squareOnCircle) &&
                t.checkExpect(bigCircleBesideBigSquare.mirror(),  new Combo("big circle beside big square", new Beside(bigSquare, bigCircle)));
    }

    boolean testPicturePictureRecipe(Tester t){
        return t.checkExpect(doubledSquareOnCircle.pictureRecipe(0), "doubled square on circle") &&
                t.checkExpect(doubledSquareOnCircle.pictureRecipe(2), "beside(overlay(square, big circle), overlay(square, big circle))") &&
                t.checkExpect(doubledSquareOnCircle.pictureRecipe(3), "beside(overlay(square, scale(circle)), overlay(square, scale(circle)))");
    }
}