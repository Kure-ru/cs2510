package assignments.assignment02;

/*
;;An EmbroideryPiece is a (make-embroidery-piece String Motif)
(define-struct embroidery-piece (name motif))

;; Motif is one of
;; -- CrossStitchMotif
;; -- ChainStitchMotif
;; -- GroupMotif

;; A CrossStitchMotif is a (make-cross-stitch-motif String Double)
(define-struct cross-stitch-motif (description difficulty))
;; interpretation: difficulty is a number between 0 and 5, with 5 being the most difficult

;; A ChainStitchMotif is a (make-chain-stitch-motif String Double)
(define-struct chain-stitch-motif (description difficulty))
;; interpretation: difficulty is a number between 0 and 5, with 5 being the most difficult

;; A GroupMotif is a (make-group-motif String [List-of Motif])
(define-struct group-motif (description motifs))
 */


import tester.Tester;

interface IMotif {
    int countMotifs();
    double getDifficulty();
    double averageDifficulty();
    String embroideryInfo();

}

interface ILoMotif {
    double totalDifficulty();
    double averageDifficulty();
    String getInfo();
    int countMotifs();
}

class EmbroideryPiece {
    String name;
    IMotif motif;

    EmbroideryPiece(String name, IMotif motif){
        this.name = name;
        this.motif = motif;
    }

    int countMotifs() {
        return this.motif.countMotifs();
    }

    double totalDifficulty() {
        return this.motif.getDifficulty();
    }

    double averageDifficulty() {
        return this.motif.averageDifficulty();
    }

    String embroideryInfo(){
        return name + ": " + this.motif.embroideryInfo();
    }

}

class CrossStitchMotif implements IMotif {
    String description;
    double difficulty;

    CrossStitchMotif(String description, double difficulty){
        this.description = description;
        this.difficulty = difficulty;
    }

    public int countMotifs() {
        return 1;
    }

    public double getDifficulty() {
        return this.difficulty;
    }

    public double averageDifficulty (){
        return this.difficulty;
    }

    public String embroideryInfo(){
        return this.description + " (cross stitch)";
    }

}

class ChainStitchMotif implements IMotif {
    String description;
    double difficulty;

    ChainStitchMotif(String description, double difficulty){
        this.description = description;
        this.difficulty = difficulty;
    }

    public int countMotifs() {
        return 1;
    }

    public double getDifficulty(){
        return this.difficulty;
    }

    public double averageDifficulty (){
        return this.difficulty;
    }

    public String embroideryInfo(){
        return this.description + " (chain stitch)";
    }


}

class GroupMotif implements IMotif {
    String description;
    ConsLoMotif motifs;

    GroupMotif(String description, ConsLoMotif motif){
        this.description = description;
        this.motifs = motif;
    }

    public double getDifficulty() {
        return this.motifs.totalDifficulty();
    }

    public int countMotifs() {
        return this.motifs.countMotifs();
    }

    public double averageDifficulty (){
        return motifs.averageDifficulty();
    }

    public String embroideryInfo(){
        return this.motifs.getInfo();
    }
}

class MtLoMotif implements ILoMotif {
    MtLoMotif(){}

    public int countMotifs() {
        return 0;
    }

    public double totalDifficulty() {
        return 0.0;
    }

    public double averageDifficulty() {
        return 0.0;
    }

    public String getInfo(){
        return "";
    }


}

class ConsLoMotif implements ILoMotif {
    IMotif first;
    ILoMotif rest;

    ConsLoMotif(IMotif first, ILoMotif rest){
        this.first = first;
        this.rest = rest;
    }

    public double totalDifficulty() {
        return this.first.getDifficulty() + this.rest.totalDifficulty();
    }

    public int countMotifs() {
        return this.first.countMotifs() + this.rest.countMotifs();
    }

    public double averageDifficulty() {
        return this.totalDifficulty() / this.countMotifs();
    }

    public String getInfo(){
        if (this.rest.getInfo().isEmpty()){
            return this.first.embroideryInfo();
        } else {
            return this.first.embroideryInfo() + ", " + this.rest.getInfo();
        }
    }
}

class ExamplesEmbroidery {
    MtLoMotif empty = new MtLoMotif();
    CrossStitchMotif bird = new CrossStitchMotif("bird", 4.5);
    ChainStitchMotif tree = new ChainStitchMotif("tree", 3.0);
    CrossStitchMotif rose = new CrossStitchMotif("rose", 5.0);
    ChainStitchMotif poppy = new ChainStitchMotif("poppy", 4.75);
    CrossStitchMotif daisy = new CrossStitchMotif("daisy", 3.2);

    GroupMotif flowers = new GroupMotif("flowers", new ConsLoMotif(rose, new ConsLoMotif(poppy, new ConsLoMotif(daisy, empty))));
    GroupMotif nature = new GroupMotif("nature", new ConsLoMotif(bird, new ConsLoMotif(tree, new ConsLoMotif(flowers, empty))));

    EmbroideryPiece pillowCover = new EmbroideryPiece("Pillow Cover", nature);

    boolean testEmbroideryAverageDifficulty(Tester t){
        return t.checkInexact(bird.averageDifficulty(), 4.5, 0.1) &&
                t.checkInexact(flowers.averageDifficulty(), 4.3, 0.1) &&
                t.checkInexact(nature.averageDifficulty(), 4.2, 0.1) &&
                t.checkInexact(pillowCover.averageDifficulty(), 4.2, 0.1);
    }

    boolean testEmbroideryInfo(Tester t){
        return t.checkExpect(pillowCover.embroideryInfo(),
                "Pillow Cover: bird (cross stitch), tree (chain stitch), rose (cross stitch), poppy (chain stitch), daisy (cross stitch)");
    }

    boolean testEmbroideryCountMotifs(Tester t){
        return t.checkExpect(empty.countMotifs(), 0) &&
                t.checkExpect(flowers.countMotifs(), 3) &&
                t.checkExpect(nature.countMotifs(), 5) &&
                t.checkExpect(pillowCover.countMotifs(), 5);
    }

    boolean testEmbroideryTotalDifficulty(Tester t){
        return t.checkInexact(empty.totalDifficulty(), 0.0, 0.1) &&
                t.checkInexact(flowers.getDifficulty(), 13.75, 0.1) &&
                t.checkInexact(nature.getDifficulty(), 20.45, 0.1) &&
                t.checkInexact(pillowCover.totalDifficulty(), 20.45, 0.1);
    }
}
