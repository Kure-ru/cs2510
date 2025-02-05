package lectures;

interface ILoRunner14 {
    ILoRunner14 sortBy(IRunnerComparator comp);
    ILoRunner14 insertBy(IRunnerComparator comp, Runner r);
    Runner findWinner();
    Runner getFirst();
    Runner findMin(IRunnerComparator comp);
    Runner findMinHelp(IRunnerComparator comp, Runner acc);
}

class MTLoRunner14 implements ILoRunner14 {
    MTLoRunner14(){}


    public ILoRunner14 sortBy(IRunnerComparator comp) {
        return this;
    }

    public ILoRunner14 insertBy(IRunnerComparator comp, Runner r) {
        return new ConsLoRunner14(r, this);
    }

    public Runner findWinner() {
        return this.findMin(new CompareByTime());
    }

    public Runner getFirst() {
        throw new RuntimeException("No first of an empty list of Runners");
    }

    public Runner findMin(IRunnerComparator comp){
        throw new RuntimeException("No minimum runner available in this list!");
    }

    public Runner findMinHelp(IRunnerComparator comp, Runner acc) {
        return acc;
    }
}

class ConsLoRunner14 implements ILoRunner14 {
    Runner first;
    ILoRunner14 rest;

    ConsLoRunner14(Runner first, ILoRunner14 rest){
        this.first = first;
        this.rest = rest;
    }

    public ILoRunner14 sortBy(IRunnerComparator comp) {
        return this.rest.sortBy(comp)
                .insertBy(comp, this.first);
    }

    public ILoRunner14 insertBy(IRunnerComparator comp, Runner r) {
        if (comp.compare(this.first, r) < 0){
            return new ConsLoRunner14(this.first, this.rest.insertBy(comp, r));
        } else {
            return new ConsLoRunner14(r, this);
        }
    }

    public Runner findWinner() {
        return this.sortBy(new CompareByTime()).getFirst();
    }

    public Runner getFirst() {
        return this.first;
    }

    public Runner findMin(IRunnerComparator comp){
        return this.rest.findMinHelp(comp, this.first);
    }

    public Runner findMinHelp(IRunnerComparator comp, Runner acc) {
        if (comp.compare(acc, this.first) < 0){
            return this.rest.findMinHelp(comp, acc);
        } else {
            return this.rest.findMinHelp(comp, this.first);
        }
    }
}

class Runner14 {
    String name;
    int age;
    int bib;
    boolean isMale;
    int pos;
    int time;

    Runner14(String name, int age, int bib, boolean isMale, int pos, int time){
        this.name = name;
        this.age = age;
        this.bib = bib;
        this.isMale = isMale;
        this.pos = pos;
        this.time = time;
    }
}


interface ICompareRunners {
    boolean comesBefore(Runner r1, Runner r2);
}

// To compute a three-way comparison between two Runners
interface IRunnerComparator {
    int compare(Runner r1, Runner r2);
}

class CompareByTime implements IRunnerComparator {
//    public boolean comesBefore(Runner r1, Runner r2) {
//        return r1.time < r2.time;
//    }

    public int compare(Runner r1, Runner r2){
        //        if (r1.time < r2.time)       { return -1; }
        //        else if (r1.time == r2.time) { return  0; }
        //        else                         { return  1; }
        return r1.time - r2.time;
    }
}

class CompareByName implements IRunnerComparator {
    public int compare(Runner r1, Runner r2){
        return r1.name.compareTo(r2.name);
    }
}

class ReverseComparator implements IRunnerComparator {
    IRunnerComparator comp;

    ReverseComparator(IRunnerComparator comp){
        this.comp = comp;
    }

    public int compare(Runner r1, Runner r2) {
        return comp.compare(r2, r1);
    }
}