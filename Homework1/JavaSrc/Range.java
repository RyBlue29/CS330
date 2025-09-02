public class Range {

    public Range() {}

    public int rangeCount(int[] A, int x, int y) {
        int first = binarySearchLow(A, x, 0);
        int last = binarySearchHigh(A, y, Math.max(x, 0));
        if (first == -1 && last == -1 ) {
            return A.length;
        }
        else if (first == -1) {
            return last;
        }
        else if (last == -1) {
            return A.length - first;
        }
        else {
            return findLast(A, last) - findFirst(A, first) + 1;
        }
    }

    public int binarySearchLow(int[] arr, int target, int low) {
        int high = arr.length-1;
        int nextBest = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == target) {
                return mid;
            }
            else if (arr[mid] > target) {
                high = mid - 1;
            }
            else if (arr[mid] < target) {
                low = mid + 1;
                nextBest = mid;
            }
        }
        if (nextBest == arr.length - 1) {
            nextBest = arr.length;
        }
        return nextBest;
    }

    public int binarySearchHigh(int[] arr, int target, int low) {
        int high = arr.length-1;
        int nextBest = -1;
        int lowCheck = low;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == target) {
                return mid;
            }
            else if (arr[mid] > target) {
                high = mid - 1;
                nextBest = mid;
            }
            else if (arr[mid] < target) {
                low = mid + 1;
            }
        }
        if (nextBest == lowCheck) {
            nextBest = 0;
        }
        return nextBest;
    }

    public int findFirst(int[] arr, int index) {
        while (index - 1 >= 0 && arr[index] == arr[index - 1]) {
            index--;
        }
        return index;
    }

    public int findLast(int[] arr, int index) {
        while (index + 1 < arr.length && arr[index] == arr[index + 1]) {
            index++;
        }
        return index;
    }

    public static void main(String[] args) {
        Range range = new Range();
        int[] input = {6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
        System.out.print(range.rangeCount(input, -1, 4));

    }
}
