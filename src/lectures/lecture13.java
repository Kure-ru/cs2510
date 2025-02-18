package lectures;


/*
       +-----------+
       | ILoRunner |<-------------------+
       +-----------+                    |
       +-----------+                    |
             / \                        |
             ---                        |
              |                         |
    ----------------------              |
    |                    |              |
+------------+    +----------------+    |
| MtLoRunner |    | ConsLoR        |    |
+------------+    +----------------+    |
+------------+  +-| Runner first   |    |
                | | ILoRunner rest |----+
                | +----------------+
                |
                v
      +----------------+
      | Runner         |
      +----------------+
      | String name    |
      | int age        |
      | int bib        |
      | boolean isMale |
      | int pos        |
      | int time       |
      +----------------+
 */

import tester.Tester;

interface ILoRunner {
    ILoRunner find(IRunnerPredicate pred);
}

class MTLoRunner implements ILoRunner {
    MTLoRunner(){}

    public ILoRunner find(IRunnerPredicate pred) { return this; }
}

class ConsLoRunner implements  ILoRunner {
    Runner first;
    ILoRunner rest;

    ConsLoRunner(Runner first, ILoRunner rest){
        this.first = first;
        this.rest = rest;
    }

    public ILoRunner find(IRunnerPredicate pred) {
        if (pred.apply(this.first)){
            return new ConsLoRunner(this.first, this.rest.find(pred));
        } else {
            return this.rest.find(pred);
        }
    }
}

class Runner {
    String name;
    int age;
    int bib;
    boolean isMale;
    int pos;
    int time;

    Runner(String name, int age, int bib, boolean isMale, int pos, int time){
        this.name = name;
        this.age = age;
        this.bib = bib;
        this.isMale = isMale;
        this.pos = pos;
        this.time = time;
    }
}

interface IRunnerPredicate {
    boolean apply(Runner r);
}

class RunnerIsMale implements IRunnerPredicate {
    public boolean apply (Runner r){
        return r.isMale;
    }
}

class RunnerIsFemale implements IRunnerPredicate {
    public boolean apply(Runner r){
        return !r.isMale;
    }
}

class RunnerIsInFirst50 implements IRunnerPredicate {
    public boolean apply (Runner r){
        return r.pos <= 50;
    }
}

class FinishIn4Hours implements IRunnerPredicate {
    public boolean apply (Runner r){
        return r.time < 240;
    }
}

class RunnerIsYounger40 implements  IRunnerPredicate {
    public boolean apply (Runner r){
        return r.age < 40;
    }
}

class AndPredicate implements IRunnerPredicate {
    IRunnerPredicate left, right;

    AndPredicate(IRunnerPredicate left, IRunnerPredicate right){
        this.left = left;
        this.right = right;
    }

    public boolean apply(Runner r){
        return this.left.apply(r) && this.right.apply(r);
    }
}

class OrPredicate implements IRunnerPredicate {
    IRunnerPredicate left, right;

    OrPredicate(IRunnerPredicate left, IRunnerPredicate right){
        this.left = left;
        this.right = right;
    }

    public boolean apply(Runner r){
        return this.left.apply(r) || this.right.apply(r);
    }
}

class ExamplesRunners {
    ExamplesRunners(){}

    Runner johnny = new Runner("Kelly", 97, 999, true, 30, 360);
    Runner frank  = new Runner("Shorter", 32, 888, true, 245, 130);
    Runner bill = new Runner("Rogers", 36, 777, true, 119, 129);
    Runner joan = new Runner("Benoit", 29, 444, false, 18, 155);

    ILoRunner mtlist = new MTLoRunner();
    ILoRunner list1 = new ConsLoRunner(johnny, new ConsLoRunner(joan, mtlist));
    ILoRunner list2 = new ConsLoRunner(frank, new ConsLoRunner(bill, list1));

    boolean testFindMethods(Tester t) {
        return
                t.checkExpect(this.list2.find(new RunnerIsFemale()),
                        new ConsLoRunner(this.joan, new MTLoRunner())) &&
                        t.checkExpect(this.list2.find(new RunnerIsMale()),
                                new ConsLoRunner(this.frank,
                                        new ConsLoRunner(this.bill,
                                                new ConsLoRunner(this.johnny, new MTLoRunner()))));
    }

    boolean testFindUnder4Hours(Tester t) {
        return
                t.checkExpect(this.list2.find(new FinishIn4Hours()),
                        new ConsLoRunner(this.frank,
                                new ConsLoRunner(this.bill,
                                        new ConsLoRunner(this.joan, new MTLoRunner()))));
    }

    boolean testCombinedQuestions(Tester t) {
        return
                t.checkExpect(this.list2.find(
                                new AndPredicate(new RunnerIsMale(), new FinishIn4Hours())),
                        new ConsLoRunner(this.frank,
                                new ConsLoRunner(this.bill, new MTLoRunner()))) &&
                        t.checkExpect(this.list2.find(
                                        new AndPredicate(new RunnerIsFemale(),
                                                new AndPredicate(new RunnerIsYounger40(),
                                                        new RunnerIsInFirst50()))),
                                new ConsLoRunner(this.joan, new MTLoRunner())) &&
                        t.checkExpect(this.list2.find(new OrPredicate(new RunnerIsFemale(), new FinishIn4Hours())), new ConsLoRunner(this.frank, new ConsLoRunner(this.bill, new ConsLoRunner(this.joan, new MTLoRunner()))));
    }
}