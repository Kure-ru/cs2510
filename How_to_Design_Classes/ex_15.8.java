/* Exercise 15.8
The EPA’s software must represent river systems and monitor them.
(1) An EPA officer may wish to find out how many sources
feed a river system.
(2) An EPA officer may wish to find out whether some location
is a part of a river system
(3) An EPA officer may request the number of miles of a river
system, either starting from the river’s mouth or any of its confluence points.

Design the following methods for the class hierarchy representing river systems:
1. maxLength, which computes the length of the longest path through
the river system;
2. confluences, which counts the number of confluences in the river system; and
3. locations, which produces a list of all locations on this river, including
sources, mouths, and confluences.
 */

interface IRiver {
    int sources();
    boolean onRiver(Location aloc);
    int length();
    int maxLength();
    int confluences();
    int confluencesHelper();
    IRiver locations();
}

class Mouth {
    Location loc;
    IRiver river;

    Mouth(Location loc, IRiver river){
        this.loc = loc;
        this.river = river;
    }

    int sources(){
        return this.river.sources();
    }

    boolean onRiver(Location aloc){
        return this.loc.same(aloc) || this.river.onRiver(aloc);
    }

    int length() {
        return this.river.length();
    }
}

class Location {
    int x;
    int y;
    String name;

    Location(int x, int y, String name){
        this.x = x;
        this.y = y;
        this.name = name;
    }

    boolean same(Location aloc){
        return (this.x == aloc.x) && (this.y == aloc.y);
    }
}

class Source implements IRiver {
    int miles;
    Location loc;

    Source(int miles, Location loc){
        this.miles = miles;
        this.loc = loc;
    }

    public int sources(){
        return 1;
    }

    public boolean onRiver(Location aloc) {
        return this.loc.same(aloc);
    }

    public int length() {
        return this.miles;
    }

    public int maxLength(){
        return this.length();
    }

    public int confluences() {
        return 0;
    }

    public int confluencesHelper () {
        return 0;
    }

    public IRiver locations() {
        return this;
    }
}

class Confluence implements IRiver {
    int miles;
    Location loc;
    IRiver left;
    IRiver right;

    Confluence(int miles, Location loc, IRiver left, IRiver right){
        this.miles = miles;
        this.loc = loc;
        this.left = left;
        this.right = right;
    }

    public int sources(){
        return this.left.sources() + this.right.sources();
    }

    public boolean onRiver(Location aloc) {
        return this.loc.same(aloc) || this.left.onRiver(aloc) || this.right.onRiver(aloc);
    }

    public int length() {
        return this.miles + this.left.length() + this.right.length();
    }

    public int maxLength(){
        if (this.left.maxLength() > this.right.maxLength()) {
            return this.miles + this.left.maxLength();
        } else {
            return this.miles + this.right.maxLength();
        }
    }

    public int confluences() {
        return this.left.confluencesHelper() + this.right.confluencesHelper();
    }

    public int confluencesHelper () {
        return 1 + this.left.confluencesHelper() + this.right.confluencesHelper();
    }

    public IRiver locations() {
        return new Confluence(this.miles, this.loc, this.left.locations(), this.right.locations());
    }
}