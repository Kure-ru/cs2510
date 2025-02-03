package assignments.assignment04;

import tester.Tester;

class BagelRecipe {
    double flour;
    double water;
    double yeast;
    double salt;
    double malt;

    double TEASPOON_PER_CUP = 48.0;
    double YEAST_DENSITY = 5.0 / TEASPOON_PER_CUP;
    double SALT_DENSITY = 10.0 / TEASPOON_PER_CUP;
    double MALT_DENSITY = 11.0 /TEASPOON_PER_CUP;
    double WATER_DENSITY = 8.0;
    double FLOUR_DENSITY = 4.25;

    BagelRecipe(double flour, double water, double yeast, double salt, double malt){
        this.flour = flour;
        this.water = (new Utils().checkValue(water, flour, "Invalid recipe: the weight of the flour should be equal to the weight of the water"));
        this.yeast = yeast;
        this.malt = (new Utils().checkValue(yeast, malt, "Invalid recipe: the weight of the yeast should be equal to the weight of the malt"));
        this.salt = (new Utils().checkValue(salt,(flour / 20) - yeast, "Invalid recipe: the weight of the salt + yeast should be 1/20th the weight of the flour"));
    }

    BagelRecipe(double flour, double yeast){
        this.flour = flour;
        this.yeast = yeast;
        this.water = this.flour;
        this.malt = this.yeast;
        this.salt = (flour / 20) - yeast;
    }

    BagelRecipe(double flourCups, double yeastTeaspoons, double saltTeaspoons){
        this.flour = flourCups *  FLOUR_DENSITY;
        this.water = flourCups * WATER_DENSITY;
        this.yeast = yeastTeaspoons * YEAST_DENSITY;
        this.salt = saltTeaspoons * SALT_DENSITY;
        this.malt = yeastTeaspoons * MALT_DENSITY;
    }

    boolean sameRecipe (BagelRecipe other){
        return (new Utils().equalsTo(this.flour, other.flour) &&
                new Utils().equalsTo(this.water, other.water) &&
                new Utils().equalsTo(this.yeast, other.yeast) &&
                new Utils().equalsTo(this.salt, other.salt) &&
                new Utils().equalsTo(this.malt, other.malt));
    }
}

class Utils {
    double PRECISION = 0.001;

    Utils(){}

    double checkValue(double value, double expectedValue, String message){
        if (value == expectedValue){
            return value;
        } else {
            throw new IllegalArgumentException(message);
        }
    }

    boolean equalsTo(double a, double b){
        return Math.abs(a - b) <= PRECISION;
    }
}

class ExamplesBagelRecipe {
    ExamplesBagelRecipe() {}

    boolean testCheckConstructorException(Tester t) {
        return t.checkConstructorException(
                new IllegalArgumentException("Invalid recipe: the weight of the salt + yeast should be 1/20th the weight of the flour"),
                "BagelRecipe", 20.0, 20.0, 1.0, 0.5, 1.0
        ) && t.checkConstructorException(
                new IllegalArgumentException("Invalid recipe: the weight of the yeast should be equal to the weight of the malt"),
                "BagelRecipe", 20.0, 20.0, 1.0, 0.5, 1.1
        ) && t.checkConstructorException(
                new IllegalArgumentException("Invalid recipe: the weight of the flour should be equal to the weight of the water"),
                "BagelRecipe", 20.0, 19.0, 1.0, 0.5, 1.0
        );
    }

    boolean testSameRecipe(Tester t) {
        return t.checkExpect(new BagelRecipe(20.0, 20.0, 0.5, 0.5, 0.5).sameRecipe(new BagelRecipe(20.0, 20.0, 0.5, 0.5, 0.5)), true) &&
                t.checkExpect(new BagelRecipe(30.0, 30.0, 0.75, 0.75, 0.75).sameRecipe(new BagelRecipe(30.0, 30.0, 0.75, 0.75, 0.75)), true) &&
                t.checkExpect(new BagelRecipe(40.0, 40.0, 1.0, 1.0, 1.0).sameRecipe(new BagelRecipe(40.0, 40.0, 1.0, 1.0, 1.0)), true);
    }

}