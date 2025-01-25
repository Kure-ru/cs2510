package assignments.assignment03;

import javalib.worldcanvas.WorldCanvas;
import tester.*;                // The tester library
import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.funworld.*;      // the abstract World class and the big-bang library
import java.awt.Color;


interface ITree {
    WorldImage draw();
    boolean isDrooping();
    ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree rightTree);
}

class Leaf implements ITree {
    int size; // represents the radius of the leaf
    Color color; // the color to draw it

    Leaf(int size, Color color){
        this.size = size;
        this.color = color;
    }

    public WorldImage draw() {
        return new CircleImage(this.size, OutlineMode.SOLID, this.color);
    }

    @Override
    public boolean isDrooping() {
        return false;
    }

    @Override
    public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree rightTree) {
        return this;
    }
}

class Stem implements ITree {
    // How long this stick is
    int length;
    // The angle (in degrees) of this stem, relative to the +x axis
    double theta;
    // The rest of the tree
    ITree tree;

    Stem(int length, double theta, ITree tree){
        this.length = length;
        this.theta = theta;
        this.tree = tree;
    }

    public WorldImage draw() {
        int endX = (int) (this.length * Math.cos(Math.toRadians(this.theta)));
        int endY = (int) (-this.length * Math.sin(Math.toRadians(this.theta)));
        WorldImage treeImage = this.tree.draw().movePinhole(- this.tree.draw().getWidth() / 8, this.tree.draw().getHeight() / 1.5);
        WorldImage stemImage = new LineImage(new Posn(endX, endY), Color.DARK_GRAY);

        return new OverlayImage(treeImage, stemImage);
    }

    @Override
    public boolean isDrooping() {
        return this.theta > 180 || this.tree.isDrooping();
    }

    @Override
    public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree rightTree) {
        return new Branch(leftLength, rightLength, leftTheta, rightTheta, this, rightTree);
    }
}

class Branch implements ITree {
    // How long the left and right branches are
    int leftLength;
    int rightLength;
    // The angle (in degrees) of the two branches, relative to the +x axis,
    double leftTheta;
    double rightTheta;
    // The remaining parts of the tree
    ITree left;
    ITree right;

    Branch(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree left, ITree right){
        this.leftLength = leftLength;
        this.rightLength = rightLength;
        this.leftTheta = leftTheta;
        this.rightTheta = rightTheta;
        this.left = left;
        this.right = right;
    }

    public WorldImage draw() {
        int leftX = (int) (this.leftLength * Math.cos(Math.toRadians(this.leftTheta)));
        int leftY = (int) (-this.leftLength * Math.sin(Math.toRadians(this.leftTheta)));

        int rightX = (int) (this.rightLength * Math.cos(Math.toRadians(this.rightTheta)));
        int rightY = (int) (-this.rightLength * Math.sin(Math.toRadians(this.rightTheta)));

        OverlayImage left = new OverlayImage(this.left.draw().movePinhole(-leftX, -leftY),
                new LineImage(new Posn(leftX, leftY), Color.DARK_GRAY));

        OverlayImage right = new OverlayImage(this.right.draw().movePinhole(-rightX, -rightY),
                new LineImage(new Posn(rightX, rightY), Color.DARK_GRAY)
        );

        int offsetX = (rightX - leftX) / 2;
        return new OverlayImage(left.movePinhole(offsetX, 0), right);
    }

    @Override
    public boolean isDrooping() {
        return this.leftTheta > 180 || this.rightTheta > 180 || this.left.isDrooping() || this.right.isDrooping();
    }

    @Override
    public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree rightTree) {
        return new Branch(leftLength, rightLength, leftTheta, rightTheta, this, rightTree);
    }
}

class ExamplesTree {
    ExamplesTree(){}
    CircleImage l1 = new CircleImage(10, OutlineMode.SOLID, Color.RED);
    CircleImage l2 = new CircleImage(15, OutlineMode.SOLID, Color.BLUE);

    OverlayImage leaf1 = new OverlayImage(l1, new LineImage(new Posn(30, 30), Color.DARK_GRAY).movePinhole(-10, -10));
    OverlayImage leaf2 = new OverlayImage(l2, new LineImage(new Posn(30, -30), Color.DARK_GRAY).movePinhole(15, -15));

    OverlayImage branch1 = new OverlayImage(leaf1,
            leaf2.movePinhole(-55, 5));
    ITree tree1 = new Branch(30, 30, 135, 40, new Leaf(10, Color.RED), new Leaf(15, Color.BLUE));
    ITree tree2 = new Branch(30, 30, 115, 65, new Leaf(15, Color.GREEN), new Leaf(8, Color.ORANGE));

    OverlayImage stem1 = new OverlayImage(tree1.draw(),
            new LineImage(new Posn(0, 50), Color.DARK_GRAY).movePinhole(10, -35));
    OverlayImage stem2 = new OverlayImage(tree2.draw(),
            new LineImage(new Posn(0, 50), Color.DARK_GRAY).movePinhole(6, -39));

    boolean testDraw(Tester t) {
        WorldCanvas c = new WorldCanvas(500, 500);
        WorldScene s = new WorldScene(500, 500);
        return c.drawScene(s.placeImageXY(new Branch(40, 50, 150, 30, tree1, tree2).draw(), 250, 250)) && c.show();
    }

    boolean testDrawTree(Tester t) {
        return t.checkExpect(new Leaf(10, Color.RED).draw(), l1) &&
                t.checkExpect(new Leaf(15, Color.BLUE).draw(), l2);
    }
}