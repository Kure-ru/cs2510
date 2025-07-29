package lectures;

import tester.Tester;

import java.util.ArrayList;

interface IFunc22<T, U> {
    U apply(T t);
}

interface IPred22<T> {
    boolean apply(T t);
}

class ArrayUtils22 {
    // EFFECT: Exchanges the values at the given two indices in the given array
    <T> void swap(ArrayList<T> arr, int index1, int index2) {
        T oldValueAtIndex2 = arr.get(index2);

        arr.set(index2, arr.get(index1));
        arr.set(index1, oldValueAtIndex2);
    }


    <T, U> ArrayList<U> map(ArrayList<T> arr, IFunc22<T, U> func) {
        ArrayList<U> result = new ArrayList<U>();
        for (T t : arr) {
            result.add(func.apply(t));
        }
        return result;
    }

    // Returns the index of the first item passing the predicate,
    // or -1 if no such item was found
    <T> int find(ArrayList<T> arr, IPred22<T> whichOne) {
        return findHelp(arr, whichOne, 0);
    }

    <T> int findHelp(ArrayList<T> arr, IPred22<T> whichOne, int index) {
        if (index >= arr.size()) {
            return -1;
        } else if (whichOne.apply(arr.get(index))) {
            return index;
        } else {
            return findHelp(arr, whichOne, index + 1);
        }
    }

    // Returns the index of the target string in the given ArrayList, or -1 if the string is not found
    // Assumes that the given ArrayList is sorted alphabetically
    int binarySearch(ArrayList<String> strings, String target) {
        return this.binarySearchHelp_v2(strings, target, 0, strings.size());
    }


    int binarySearchHelp_v1(ArrayList<String> strings, String target, int lowIdx, int highIdx) {
        int midIdx = (lowIdx + highIdx) / 2;

        if (lowIdx > highIdx) {
            return -1;
        } else if (target.compareTo(strings.get(midIdx)) == 0) {
            return midIdx; // found it!
        } else if (target.compareTo(strings.get(midIdx)) > 0) {
            return this.binarySearchHelp_v1(strings, target, midIdx + 1, highIdx); // too low
        } else {
            return this.binarySearchHelp_v1(strings, target, lowIdx, midIdx - 1); // too high
        }
    }

    // Assumes that [lowIdx, highIdx) is a semi-open interval of indices
    int binarySearchHelp_v2(ArrayList<String> strings, String target, int lowIdx, int highIdx) {
        int midIdx = (lowIdx + highIdx) / 2;

        if (lowIdx >= highIdx) {
            return -1;
        } else if (target.compareTo(strings.get(midIdx)) == 0) {
            return midIdx; // found it!
        } else if (target.compareTo(strings.get(midIdx)) > 0) {
            return this.binarySearchHelp_v1(strings, target, midIdx + 1, highIdx); // too low
        } else {
            return this.binarySearchHelp_v1(strings, target, lowIdx, midIdx); // too high
        }
    }

    int indexOfMinValue (ArrayList<String> arr, int startIdx){
        int minIdx = startIdx;

        for (int idx = startIdx + 1; idx < arr.size(); idx++){
            if (arr.get(idx).compareTo(arr.get(minIdx)) < 0){
                minIdx = idx;
            }
        }

        return minIdx;
    }

    //  EFFECT: Sorts the given list of strings alphabetically
    void sort(ArrayList<String> arr) {
        for (int idx = 0;
             idx < arr.size();
             idx = idx + 1) {
            int idxOfMinValue = indexOfMinValue(arr, idx);
            this.swap(arr, idx, idxOfMinValue);
        }
    }

    <T> ArrayList<T> interleave(ArrayList<T> arr1, ArrayList<T> arr2) {
        ArrayList<T> result = new ArrayList<>();

        for (int idx = 0; idx < arr1.size(); idx++) {
            result.add(arr1.get(idx));
            result.add(arr2.get(idx));
        }

        return result;
    }

    <T> ArrayList<T> unshuffle(ArrayList<T> arr) {
        ArrayList<T> odds = new ArrayList<>();
        ArrayList<T> evens = new ArrayList<>();

        for (int idx = 0; idx < arr.size(); idx++) {
            if (idx % 2 == 0) {
                odds.add(arr.get(idx));
            } else {
                evens.add(arr.get(idx));
            }
        }

        odds.addAll(evens);
        return odds;
    }

}