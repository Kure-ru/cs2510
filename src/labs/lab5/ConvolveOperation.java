//package labs.lab5;
//
//import tester.Tester;
//
//interface IFunc<T, R> {
//    R apply(T arg1, T arg2);
//}
//
//
//interface IList<T> {
//    <R> IList<R> convolve(IList<T> other, IFunc<T, R> func);
//
//    <R> IList<R> convolveWithCons(ConsList<T> that, IFunc<T, R> func);
//}
//
//class MtList<T> implements IList<T> {
//    public <R> IList<R> convolve(IList<T> other, IFunc<T, R> func) {
//        return new MtList<>();
//    }
//
//    public <R> IList<R> convolveWithCons(ConsList<T> that, IFunc<T, R> func) {
//        return new MtList<>();
//    }
//}
//
//class ConsList<T> implements IList<T> {
//    T first;
//    IList<T> rest;
//
//    ConsList(T first, IList<T> rest) {
//        this.first = first;
//        this.rest = rest;
//    }
//
//    public <R> IList<R> convolve(IList<T> other, IFunc<T, R> func) {
//        if (other instanceof ConsList<?>) {
//            return other.convolveWithCons(this, func);
//        }
//        return new MtList<>();
//    }
//
//    public <R> IList<R> convolveWithCons(ConsList<T> that, IFunc<T, R> func) {
//        return new ConsList<>(
//                func.apply(this.first, that.first),
//                this.rest.convolve(that.rest, func)
//        );
//    }
//}
//
//
//interface ILoDouble {
//    ILoDouble convolve(ILoDouble other);
//
//    ILoDouble convolveWithDouble(double thatFirst, ILoDouble thatRest);
//}
//
//class MtLoDouble implements ILoDouble {
//    public ILoDouble convolve(ILoDouble other) {
//        return new MtLoDouble();
//    }
//
//    public ILoDouble convolveWithDouble(double thatFirst, ILoDouble thatRest) {
//        return new MtLoDouble();
//    }
//}
//
//class ConsLoDouble implements ILoDouble {
//    double first;
//    ILoDouble rest;
//
//    ConsLoDouble(double first, ILoDouble rest) {
//        this.first = first;
//        this.rest = rest;
//    }
//
//    public ILoDouble convolve(ILoDouble other) {
//        return other.convolveWithDouble(this.first, this.rest);
//    }
//
//    public ILoDouble convolveWithDouble(double thatFirst, ILoDouble thatRest) {
//        return new ConsLoDouble(this.first * thatFirst,
//                this.rest.convolve(thatRest));
//    }
//}
//
//class Pair<A, B> {
//    A first;
//    B second;
//
//    Pair(A first, B second) {
//        this.first = first;
//        this.second = second;
//    }
//}
//
//class MakePairString implements IFunc<String, Pair<String, String>> {
//    public Pair<String, String> apply(String a, String b) {
//        return new Pair<>(a, b);
//    }
//}
//
//class ExamplesConvolve {
//    ExamplesConvolve() {
//    }
//
//    ILoDouble list1 = new ConsLoDouble(0.3,
//            new ConsLoDouble(0.5,
//                    new ConsLoDouble(0.1,
//                            new MtLoDouble())));
//    ILoDouble list2 = new ConsLoDouble(2.0,
//            new ConsLoDouble(4.0,
//                    new ConsLoDouble(3.0,
//                            new MtLoDouble())));
//
//    ILoDouble list3 = new ConsLoDouble(0.6,
//            new ConsLoDouble(2.0,
//                    new ConsLoDouble(0.3,
//                            new MtLoDouble())));
//
//    IList<String> months = new ConsList<>("January",
//            new ConsList<>("February",
//                    new ConsList<>("March",
//                            new MtList<>())));
//
//    IList<String> days = new ConsList<>("31",
//            new ConsList<>("28",
//                    new ConsList<>("31",
//                            new MtList<>())));
//
//    IFunc<String, Pair<String, String>> makePair = new MakePairString();
//
//    boolean testConvolveProduct(Tester t) {
//        return t.checkInexact(list1.convolve(list2), list3, 0.1);
//    }
//
//    boolean testConvolveDaysAndMonths(Tester t) {
//        IList<Pair<String, String>> monthDays = months.convolve(days, makePair);
//        return t.checkExpect(monthDays,
//                new ConsList<>(new Pair<>("31", "January"),
//                        new ConsList<>(new Pair<>("February", "28"),
//                                new ConsList<>(new Pair<>("31", "March"),
//                                        new MtList<>()))));
//    }
//}