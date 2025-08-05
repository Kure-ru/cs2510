package lectures;

import java.util.ArrayList;

class ArrayUtils24 {
    int findMin(ArrayList<Integer> nums) {
        int minSoFar = nums.get(0);
        for (int i = 1; i < nums.size(); i++) {
            if (nums.get(i) < minSoFar) {
                minSoFar = nums.get(i);
            }
        }
        return minSoFar;
    }

    int findMin_whileloop(ArrayList<Integer> nums) {
        int minSoFar = nums.get(0);
        int i = 1;
        while (i < nums.size()) {
            if (nums.get(i) < minSoFar) {
                minSoFar = nums.get(i);
            }
            i = i + 1;
        }
        return minSoFar;
    }

    boolean getsToOne (int n) {
        while (n > 1) {
            if (n % 2 == 0) {
                n = n / 2;
            } else {
                n = 3 * n + 1;
            }
        }
        return true;
    }
}
