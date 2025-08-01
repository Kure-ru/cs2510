import tester.Tester;
import java.util.ArrayList;

interface Predicate<T> {
    boolean apply(T t);
}

class ArrayUtils {
    <T> ArrayList<T> filter(ArrayList<T> arr, Predicate<T> pred) {
        return customFilter(arr, pred, true);
    }

    <T> ArrayList<T> filterNot(ArrayList<T> arr, Predicate<T> pred) {
        return customFilter(arr, pred, false);
    }

    private <T> ArrayList<T> customFilter(ArrayList<T> arr, Predicate<T> pred, boolean keepPassing) {
        ArrayList<T> result = new ArrayList<>();

        for (T item : arr) {
            if (keepPassing && pred.apply(item)) {
                result.add(item);
            } else if (!keepPassing && !pred.apply(item)) {
                result.add(item);
            }
        }
        return result;
    }

    <T> void removeFailing(ArrayList<T> arr, Predicate<T> pred) {
        customRemove(arr, pred,true);
    }

    <T> void removePassing(ArrayList<T> arr, Predicate<T> pred) {
        customRemove(arr, pred,false);
    }

    private <T> void customRemove(ArrayList<T> arr, Predicate<T> pred, boolean removeFailing) {
        for (int i = arr.size() - 1; i >= 0; i--) {
            if (removeFailing && !pred.apply(arr.get(i))) {
                arr.remove(i);
            } else if (!removeFailing && pred.apply(arr.get(i))) {
                arr.remove(i);
            }
        }
    }
}

class ExampleClass {

    ArrayUtils utils = new ArrayUtils();
    ArrayList<Integer> numbers;

    Predicate<Integer> isEven = new Predicate<Integer>() {
        public boolean apply(Integer n) {
            return n % 2 == 0;
        }
    };

    void initData() {
        numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
    }


    void testFilter(Tester t) {
        this.initData();

        ArrayList<Integer> evenNumbers = utils.filter(this.numbers, this.isEven);
        t.checkExpect(evenNumbers.size(), 2);
        t.checkExpect(evenNumbers.get(0), 2);
        t.checkExpect(evenNumbers.get(1), 4);
    }

    void testFilterNot(Tester t) {
        this.initData();

        ArrayList<Integer> oddNumbers = utils.filterNot(this.numbers, this.isEven);
        t.checkExpect(oddNumbers.size(), 3);
        t.checkExpect(oddNumbers.get(0), 1);
        t.checkExpect(oddNumbers.get(1), 3);
    }

    void testRemoveFailing(Tester t) {
        this.initData();

        utils.removeFailing(this.numbers, this.isEven);
        t.checkExpect(this.numbers.size(), 2);
        t.checkExpect(this.numbers.get(0), 2);
        t.checkExpect(this.numbers.get(1), 4);
    }

    void testRemovePassing(Tester t) {
        this.initData();

        utils.removePassing(this.numbers, this.isEven);
        t.checkExpect(this.numbers.size(), 3);
        t.checkExpect(this.numbers.get(0), 1);
        t.checkExpect(this.numbers.get(1), 3);
        t.checkExpect(this.numbers.get(2), 5);
    }
}