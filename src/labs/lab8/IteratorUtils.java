package labs.lab8;

import java.util.ArrayList;
import java.util.Iterator;

class IteratorUtils {

    <X, Y> Iterator<Pair<X, Y>> zipStrict(ArrayList<X> arr1, ArrayList<Y> arr2) {
        // If the lists are of different sizes, an exception should be thrown.
        if (arr1.size() != arr2.size()) {
            throw new IllegalArgumentException("Lists must be of equal length!");
        }

        ArrayList<Pair<X, Y>> result = new ArrayList<>();

        for (int i = 0; i < arr1.size(); i++) {
            Pair<X, Y> pair = new Pair<>(arr1.get(i), arr2.get(i));
            result.add(pair);
        }

        return result.iterator();
    }

    <X, Y> Iterator<Pair<X, Y>> zipLists(ArrayList<X> arr1, ArrayList<Y> arr2) {
        // find shorter list
        int shorterList = arr1.size();

        if (arr2.size() < arr1.size()) {
            shorterList = arr2.size();
        }

        ArrayList<Pair<X, Y>> result = new ArrayList<>();

        for (int i = 0; i < shorterList; i++) {
            Pair<X, Y> pair = new Pair<>(arr1.get(i), arr2.get(i));
            result.add(pair);
        }

        return result.iterator();
    }

    <T> Iterator<T> concat(Iterator<T> iter1, Iterator<T> iter2) {
        ArrayList<T> combined = new ArrayList<>();

        while (iter1.hasNext()) {
            combined.add(iter1.next());
        }

        while (iter2.hasNext()) {
            combined.add(iter2.next());
        }

        return combined.iterator();
    }
}

class Pair<L, R> {
    L left;
    R right;

    Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public String toString() {
        return "(" + left + ", " + right + ")";
    }

}