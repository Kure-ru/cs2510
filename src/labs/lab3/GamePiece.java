package labs.lab3;

/* Lab 03

The Game of 2048
A game piece is either a base tile or a merge tile formed from two component tiles. Each game piece can return its value and merge with another game piece to form a combined tile. Base tiles have a positive integer value, starting at 2.

Convert the following data definition into Java classes and interfaces:
;; A GamePiece is one of
;; -- (make-base-tile Number)
;; -- (make-merge-tile GamePiece GamePiece)

Design the following methods:
- getValue: returns the value of a game piece (sum of component values for merged tiles).
- merge: combines this game piece with another to form a merged piece.
- isValid: checks if the game piece was created according to the rules (only equal-valued pieces can merge).
 */


import tester.Tester;

interface IGamePiece {
    int getValue();
    IGamePiece merge(IGamePiece other);
    boolean isValid();
}


class BaseTile implements IGamePiece {
    int value;

    BaseTile(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    public IGamePiece merge(IGamePiece other) {
        return new MergeTile(this, other);
    }

    public boolean isValid(){
        return false;
    }
}

class MergeTile implements IGamePiece {
    IGamePiece piece1;
    IGamePiece piece2;

    MergeTile(IGamePiece piece1, IGamePiece piece2){
        this.piece1 = piece1;
        this.piece2 = piece2;
    }

    public int getValue(){
        return this.piece1.getValue() + this.piece2.getValue();
    }

    public IGamePiece merge(IGamePiece other) {
        return new MergeTile(this, other);
    }

    public boolean isValid() {
        return this.piece1.getValue() == this.piece2.getValue();
    }
}

class ExamplesGamePiece {
    ExamplesGamePiece(){}

    IGamePiece baseTile1 = new BaseTile(2);
    IGamePiece baseTile2 = new BaseTile(4);
    IGamePiece baseTile3 = new BaseTile(8);
    IGamePiece baseTile4 = new BaseTile(16);

    IGamePiece mergeTile1 = new MergeTile(baseTile1, baseTile2);
    IGamePiece mergeTile2 = new MergeTile(baseTile3, baseTile4);
    IGamePiece mergeTile3 = new MergeTile(mergeTile1, mergeTile2);
    IGamePiece mergeTile4 = new MergeTile(mergeTile2, mergeTile3);
    IGamePiece mergeTile5 = new MergeTile(baseTile1, new BaseTile(2));

    boolean testGetValue (Tester t){
        return t.checkExpect(baseTile1.getValue(), 2) &&
                t.checkExpect(baseTile2.getValue(), 4) &&
                t.checkExpect(baseTile3.getValue(), 8) &&
                t.checkExpect(baseTile4.getValue(), 16) &&
                t.checkExpect(mergeTile1.getValue(), 6) &&
                t.checkExpect(mergeTile2.getValue(), 24) &&
                t.checkExpect(mergeTile3.getValue(), 30) &&
                t.checkExpect(mergeTile4.getValue(), 54);
    }

    boolean testMerge (Tester t){
        return t.checkExpect(baseTile1.merge(baseTile2), new MergeTile(baseTile1, baseTile2)) &&
                t.checkExpect(baseTile2.merge(baseTile1), new MergeTile(baseTile2, baseTile1)) &&
                t.checkExpect(mergeTile1.merge(mergeTile2), new MergeTile(mergeTile1, mergeTile2)) &&
                t.checkExpect(mergeTile2.merge(mergeTile1), new MergeTile(mergeTile2, mergeTile1)) &&
                t.checkExpect(mergeTile3.merge(mergeTile4), new MergeTile(mergeTile3, mergeTile4)) &&
                t.checkExpect(mergeTile4.merge(mergeTile3), new MergeTile(mergeTile4, mergeTile3));
    }

    boolean testIsValid (Tester t){
        return  t.checkExpect(mergeTile5.isValid(), true) &&
                t.checkExpect(mergeTile3.isValid(), false) &&
                t.checkExpect(mergeTile4.isValid(), false);
    }
}
