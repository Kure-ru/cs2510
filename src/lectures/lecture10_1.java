package lectures;



interface ITetrisPiece {
    int SCREEN_HEIGHT = 30;
}

abstract class ATetrisPiece implements ITetrisPiece {
    int xPos;
    int yPos;

    ATetrisPiece(int x, int y){
        this.xPos = x;
        this.yPos = y;
    }

    ATetrisPiece(int x){
        this(x, SCREEN_HEIGHT);
    }
}

class Square10 extends ATetrisPiece {

    Square10(int topLeftX, int topLeftY) {
        super(topLeftX, topLeftY);
    }

    Square10(int topLeftX) {
        super(topLeftX);
    }
}

class LShape extends ATetrisPiece {

    LShape(int cornerX, int cornerY) {
        super(cornerX, cornerY);
    }

    LShape(int cornerX) {
        super(cornerX);
    }
}

class ExamplesITetrisPiece {
    ITetrisPiece square1 = new Square10(3, 30);
    ITetrisPiece square2 = new Square10(3);
    ITetrisPiece lshape1 = new LShape(3, 30);
    ITetrisPiece lshape2 = new LShape(3);

}
