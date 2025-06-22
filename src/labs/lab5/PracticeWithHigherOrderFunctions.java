package labs.lab5;

import tester.Tester;


interface IPred<T> {
    boolean apply(T t);
}

interface IFunc<A, R> {
    R apply(A args);
}

interface IFunc2<A1, A2, R> {
    R apply(A1 arg1, A2 arg2);
}

interface IList<T> {
    IList<T> filter(IPred<T> pred);

    <U> IList<U> map(IFunc<T, U> f);

    <U> U fold(U initial, IFunc2<T, U, U> f);
}

class MTList<T> implements IList<T> {

    public IList<T> filter(IPred<T> pred) {
        return new MTList<T>();
    }

    public <U> IList<U> map(IFunc<T, U> f) {
        return new MTList<U>();
    }

    public <U> U fold(U initial, IFunc2<T, U, U> f) {
        return initial;
    }
}

class ConsList<T> implements IList<T> {
    T first;
    IList<T> rest;

    ConsList(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest;
    }

    public IList<T> filter(IPred<T> pred) {
        if (pred.apply(this.first)) {
            return new ConsList<T>(this.first, this.rest.filter(pred));
        } else {
            return this.rest.filter(pred);
        }
    }

    public <U> IList<U> map(IFunc<T, U> f) {
        return new ConsList<U>(f.apply(this.first), this.rest.map(f));
    }

    public <U> U fold(U initial, IFunc2<T, U, U> f) {
        return f.apply(this.first, this.rest.fold(initial, f));
    }
}

class ExamplesLists {
    ExamplesLists() {
    }

    MTList<String> mtStringList = new MTList<>();
    IList<String> months = new ConsList<>("January",
            new ConsList<String>("February",
                    new ConsList<String>("March",
                            new ConsList<String>("April",
                                    new ConsList<String>("May",
                                            new ConsList<String>("June",
                                                    new ConsList<String>("July",
                                                            new ConsList<String>("August",
                                                                    new ConsList<String>("September",
                                                                            new ConsList<String>("October",
                                                                                    new ConsList<String>("November",
                                                                                            new ConsList<String>("December", mtStringList))))))))))));

    boolean testFilter(Tester t) {
        IPred<String> startsWithJ = new IPred<String>() {
            public boolean apply(String s) {
                return s.charAt(0) == 'J';
            }
        };

        IList<String> filteredMonths = this.months.filter(startsWithJ);
        return t.checkExpect(filteredMonths, new ConsList<>("January",
                new ConsList<>("June",
                        new ConsList<>("July", mtStringList))));
    }

    boolean testFold(Tester t) {
        IFunc2<String, Integer, Integer> endsWithEr = new IFunc2<String, Integer, Integer>() {
            public Integer apply(String month, Integer acc) {
                if (month.endsWith("er")) {
                    return acc + 1;
                } else {
                    return acc;
                }
            }
        };

        int result = months.fold(0, endsWithEr);

        return t.checkExpect(result, 4);
    }

    boolean testMap(Tester t) {
        IFunc<String, String> createAbbreviation = new IFunc<String, String>() {
            public String apply(String s) {
                return s.substring(0, 3);
            }
        };

        IList<String> listOfAbbreviations = months.map(createAbbreviation);
        IList<String> expectedAbbreviations = new ConsList<>("Jan",
                new ConsList<>("Feb",
                        new ConsList<>("Mar",
                                new ConsList<>("Apr",
                                        new ConsList<>("May",
                                                new ConsList<>("Jun",
                                                        new ConsList<>("Jul",
                                                                new ConsList<>("Aug",
                                                                        new ConsList<>("Sep",
                                                                                new ConsList<>("Oct",
                                                                                        new ConsList<>("Nov",
                                                                                                new ConsList<>("Dec", mtStringList))))))))))));

        return t.checkExpect(listOfAbbreviations, expectedAbbreviations);
    }
}

