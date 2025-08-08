package assignments.assignment07;

import tester.Tester;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * +-------------------+
 * | IArith            |
 * +-------------------+
 * +-------------------+
 * /_\      /_\
 * |        |----------------------------------------------------------------
 * |        |                                                               |
 * +-------------+  +------------------------------+           +------------------------------------------+
 * | Const       |  | UnaryFormula                 |           |              BinaryFormula               |
 * +-------------+  +------------------------------+           +------------------------------------------+
 * | double num  |  | Function<Double,Double> func |           | BiFunction <Double, Double, Double> func |
 * +-------------+  | String name                  |           | String name                              |
 * | IArith child                 |           | IArith left                              |
 * +------------------------------+           | IArith right                             |
 * +------------------------------------------+
 **/


interface IArith {
    <R> R accept(IArithVisitor<R> visitor);
}

class Const implements IArith {
    double num;

    Const(double num) {
        this.num = num;
    }

    public <R> R accept(IArithVisitor<R> visitor) {
        return visitor.visitConst(this);
    }
}

class UnaryFormula implements IArith {
    Function<Double, Double> func;
    String name;
    IArith child;


    UnaryFormula(Function<Double, Double> func, String name, IArith child) {
        this.func = func;
        this.name = name;
        this.child = child;
    }

    public <R> R accept(IArithVisitor<R> visitor) {
        return visitor.visitUnary(this);
    }
}

class BinaryFormula implements IArith {
    BiFunction<Double, Double, Double> func;
    String name;
    IArith left;
    IArith right;

    BinaryFormula(BiFunction<Double, Double, Double> func, String name, IArith left, IArith right) {
        this.func = func;
        this.name = name;
        this.left = left;
        this.right = right;
    }

    public <R> R accept(IArithVisitor<R> visitor) {
        return visitor.visitBinary(this);
    }
}

interface IArithVisitor<R> {
    R visitConst(Const c);

    R visitUnary(UnaryFormula u);

    R visitBinary(BinaryFormula b);
}

class EvalVisitor implements IArithVisitor<Double>, Function<IArith, Double> {
    public Double apply(IArith expr) {
        return expr.accept(this);
    }

    @Override
    public Double visitConst(Const c) {
        return c.num;
    }

    @Override
    public Double visitUnary(UnaryFormula u) {
        Double val = u.child.accept(this);
        return u.func.apply(val);
    }

    @Override
    public Double visitBinary(BinaryFormula b) {
        Double leftVal = b.left.accept(this);
        Double rightVal = b.right.accept(this);

        return b.func.apply(leftVal, rightVal);
    }
}

class PrintVisitor implements IArithVisitor<String>, Function<IArith, String> {

    public String apply(IArith expr) {
        return expr.accept(this);
    }

    public String visitConst(Const c) {
        return Double.toString(c.num);
    }

    public String visitUnary(UnaryFormula u) {
        String childString = u.child.accept(this);
        return "(" + u.name + " " + childString + ")";
    }

    public String visitBinary(BinaryFormula b) {
        String leftString = b.left.accept(this);
        String rightString = b.right.accept(this);
        return "(" + leftString + " " + b.name + " " + rightString + ")";
    }
}

class MirrorVisitor implements IArithVisitor<IArith>, Function<IArith, IArith> {

    @Override
    public IArith visitConst(Const c) {
        return c;
    }

    @Override
    public IArith visitUnary(UnaryFormula u) {
        return u;
    }

    @Override
    public IArith visitBinary(BinaryFormula b) {
        IArith newLeft = b.right;
        IArith newRight = b.left;
        return new BinaryFormula(b.func, b.name, newLeft, newRight);
    }

    public IArith apply(IArith expr) {
        return expr.accept(this);
    }
}

class AllEvenVisitor implements IArithVisitor<Boolean>, Function<IArith, Boolean> {

    public Boolean visitConst(Const c) {
        return c.num % 2 == 0;
    }

    public Boolean visitUnary(UnaryFormula u) {
        return u.child.accept(this);
    }

    public Boolean visitBinary(BinaryFormula b) {
        return b.left.accept(this) && b.right.accept(this);
    }

    public Boolean apply(IArith expr) {
        return expr.accept(this);
    }
}

class ArithExamples {
    // Constants
    IArith c1 = new Const(1.0);
    IArith c2 = new Const(2.0);
    IArith c3 = new Const(3.0);
    IArith c4 = new Const(4.0);

    // Binary formulas
    IArith plus = new BinaryFormula((a, b) -> a + b, "plus", c2, c3);
    IArith minus = new BinaryFormula((a, b) -> a - b, "minus", c2, c3);
    IArith mul = new BinaryFormula((a, b) -> a * b, "mul", c2, c3);
    IArith div = new BinaryFormula((a, b) -> a / b, "div", c2, c3);
    IArith plusEven = new BinaryFormula((a, b) -> a + b, "plus", c2, new UnaryFormula(a -> a + 2, "plusEven", c4));

    // Unary formulas
    IArith neg = new UnaryFormula(a -> -a, "neg", c2);
    IArith sqr = new UnaryFormula(a -> a * a, "sqr", c3);

    void testEvalVisitor(Tester t) {
        EvalVisitor eval = new EvalVisitor();
        t.checkExpect(eval.apply(plus), 5.0);
        t.checkExpect(eval.apply(minus), -1.0);
        t.checkExpect(eval.apply(mul), 6.0);
        t.checkInexact(eval.apply(div), 0.6666666666666666, 0.0001);
        t.checkExpect(eval.apply(neg), -2.0);
    }

    void testPrintVisitor(Tester t) {
        PrintVisitor print = new PrintVisitor();
        t.checkExpect(print.apply(plus), "(2.0 plus 3.0)");
        t.checkExpect(print.apply(minus), "(2.0 minus 3.0)");
        t.checkExpect(print.apply(mul), "(2.0 mul 3.0)");
        t.checkExpect(print.apply(div), "(2.0 div 3.0)");
        t.checkExpect(print.apply(neg), "(neg 2.0)");
        t.checkExpect(print.apply(sqr), "(sqr 3.0)");

    }

    void testAllEvenVisitor(Tester t) {
        AllEvenVisitor allEven = new AllEvenVisitor();
        t.checkExpect(allEven.apply(plus), false);
        t.checkExpect(allEven.apply(plusEven), true);
    }

}