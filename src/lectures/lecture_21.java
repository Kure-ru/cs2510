package lectures;

import tester.Tester;

import java.util.ArrayList;

interface IFunc21<T, U> {
    U apply(T args);
}

class ArrayUtils {
    // EFFECT: Exchanges the values at the given two indices in the given array
    <T> void swap(ArrayList<T> arr, int index1, int index2) {
        T oldValueAtIndex2 = arr.get(index2);

        arr.set(index2, arr.get(index1));
        arr.set(index1, oldValueAtIndex2);
    }

    // recursive map
    //    <T, U> ArrayList<U> map(ArrayList<T> arr, IFunc<T, U> func) {
    //        ArrayList<U> result = new ArrayList<U>();
    //        return this.mapHelp(arr, func, 0, result);
    //    }

    <T, U> ArrayList<U> map(ArrayList<T> arr, IFunc21<T, U> func) {
        ArrayList<U> result = new ArrayList<U>();
        for (T t : arr) {
            result.add(func.apply(t));
        }
        return result;
    }

    // Computes the result of mapping the given function over the source list
    // from the given current index to the end of the list, and returns the
    // given destination list
    // EFFECT: modifies the destination list to contain the mapped results
    //    <T, U> ArrayList<U> mapHelp(ArrayList<T> source, IFunc<T, U> func, int curIdx, ArrayList<U> dest) {
    //        if (curIdx >= source.size()) {
    //            return dest;
    //        } else {
    //            dest.add(func.apply(source.get(curIdx)));
    //            return this.mapHelp(source, func, curIdx + 1, dest);
    //        }
    //    }
}

class ExampleArrayLists {
    void testGet(Tester t) {
        ArrayList<String> someStrings = new ArrayList<String>();
        t.checkException(new IndexOutOfBoundsException("Index: 0, Size: 0"), someStrings, "get", 0);

        someStrings.add("First string");
        someStrings.add("Second string");

        t.checkExpect(someStrings.get(0), "First string");
        t.checkExpect(someStrings.get(1), "Second string");

        t.checkException(new IndexOutOfBoundsException("Index: 3, Size: 2"), someStrings, "get", 3);

        // Insert item at a given index
        someStrings.add(1, "Squeezed in");
        t.checkExpect(someStrings.get(0), "First string");
        t.checkExpect(someStrings.get(1), "Squeezed in");
        t.checkExpect(someStrings.get(2), "Second string");
    }

    void testSwap(Tester t) {
        ArrayList<String> someStrings = new ArrayList<String>();
        someStrings.add("second string");
        someStrings.add("first string");

        ArrayUtils utils = new ArrayUtils();
        utils.swap(someStrings, 0, 1);

        t.checkExpect(someStrings.get(0), "first string");
        t.checkExpect(someStrings.get(1), "second string");
    }
}