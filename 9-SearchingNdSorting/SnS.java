import java.util.*;

public class SnS {
    

    public static class Students implements Comparable<Students> {
        int phy;
        int chem;
        int maths;


        public Students(int phy, int chem, int maths) {
            this.phy = phy;
            this.chem = chem;
            this.maths = maths;
        }

        public int compareTo(Students o) {
            if(this.phy == o.phy) {
                // decision depends on chemistry
                if(this.chem == o.chem) {
                    // decision depends on maths, default behaviour
                    return this.maths - o.maths;
                } else {
                    // reverse of default behaviour
                    return o.chem - this.chem;
                }
            } else {
                // default behaviour, increasing order requirement
                return this.phy - o.phy;
            }
        }
    }

    // marks of PCM https://practice.geeksforgeeks.org/problems/marks-of-pcm2529/1
    public void customSort (int phy[], int chem[], int math[], int N) {
        Students[] sarr = new Students[phy.length];

        for(int i = 0; i < phy.length; i++) {
            sarr[i] = new Students(phy[i], chem[i], math[i]);
        }

        Arrays.sort(sarr);
        for(int i = 0; i < phy.length; i++) {
            phy[i] = sarr[i].phy;
            chem[i] = sarr[i].chem;
            math[i] = sarr[i].maths;
        }
    }
    
    // union of two sorted arrays, https://practice.geeksforgeeks.org/problems/union-of-two-sorted-arrays/1
    public static ArrayList<Integer> findUnion(int arr1[], int arr2[], int n, int m) {
        ArrayList<Integer> res = new ArrayList<>();

        int i = 0;
        int j = 0;

        while(i < n && j < m) {
            if(arr1[i] == arr2[j]) {
                if(res.size() == 0 || res.get(res.size() - 1) != arr1[i]) {
                    res.add(arr1[i]);
                }
                i++;
                j++;
            } else if(arr1[i] < arr2[j]) {
                if(res.size() == 0 || res.get(res.size() - 1) != arr1[i]) {
                    res.add(arr1[i]);
                }
                i++;
            } else {
                if(res.size() == 0 || res.get(res.size() - 1) != arr2[j]) {
                    res.add(arr2[j]);
                }
                j++;
            }
        }

        // either i will remain or j will remain
        while(i < n) {
            if(res.size() == 0 || res.get(res.size() - 1) != arr1[i]) {
                res.add(arr1[i]);
            } 
            i++;
        }

        while(j < m) {
            if(res.size() == 0 || res.get(res.size() - 1) != arr2[j]) {
                res.add(arr2[j]);
            } 
            j++;
        }
        return res;
    }

    // search in 2D matrix, portal
    private static int findRowIndx(int[][] matrix, int target) {
        int indx = -1;
        int lo = 0;
        int hi = matrix.length - 1;

        while(lo <= hi) {
            int midRow = lo + (hi - lo) / 2;

            if(matrix[midRow][0] <= target && target <= matrix[midRow][matrix[0].length - 1]) {
                indx = midRow;
                break;
            } else if(target < matrix[midRow][0]) {
                hi = midRow - 1;
            } else {
                lo = midRow + 1;
            }
        }
        return indx;
    }

    private static int findInIthRow(int[][] matrix, int target, int row) {
        int indx = -1;
        int lo = 0;
        int hi = matrix[0].length - 1;

        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if(matrix[row][mid] == target) {
                indx = mid;
                break;
            } else if(matrix[row][mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return indx;
    }

    public static boolean search(int[][]matrix, int target) {
        int rowIndx = findRowIndx(matrix, target);
        if(rowIndx == -1) return false;
        int colIndx = findInIthRow(matrix, target, rowIndx);
        return colIndx != -1;
    }

    // search in 2D matrix 2, portal
    public static boolean search2(int[][]matrix,int target) {
        int r = 0;
        int c = matrix[0].length - 1;

        while(r < matrix.length && c >= 0) {
            if(matrix[r][c] == target) {
                return true;
            } else if(matrix[r][c] < target) {
                // discard current row
                r++;
            } else {
                // discard column
                c--;
            }
        }
        return false;
    }

    // leetcode 724, https://leetcode.com/problems/find-pivot-index/
    public int pivotIndex(int[] nums) {
        int tsum = 0;
        for(int val : nums) 
            tsum += val;
        
        int lsum = 0;
        for(int i = 0; i < nums.length; i++) {
            tsum -= nums[i];
            if(lsum == tsum) {
                return i;
            }
            lsum += nums[i];
        }
        return -1;
    }

    // find k closest element using priority queue
    public static class Pair implements Comparable<Pair> {
        int val;
        int dist;

        public Pair(int val, int dist) {
            this.val = val;
            this.dist = dist;
        }

        public int compareTo(Pair o) {
            if(this.dist == o.dist) {
                return this.val - o.val;
            } else {
                return this.dist - o.dist;
            }
        }
    }

    public static ArrayList<Integer> findClosest(int[]arr,int k,int x) {
        ArrayList<Integer> res = new ArrayList<>();
        PriorityQueue<Pair> pq = new PriorityQueue<>(Collections.reverseOrder());
        for(int i = 0; i < arr.length; i++) {
            if(i < k) {
                pq.add(new Pair(arr[i], Math.abs(arr[i] - x)));
            } else {
                if(pq.peek().dist > Math.abs(arr[i] - x)) {
                    pq.remove();
                    pq.add(new Pair(arr[i], Math.abs(x - arr[i])));
                }
            }
        }

        while(pq.size() > 0) {
            res.add(pq.remove().val);
        }
        Collections.sort(res);
        return res;
    }

    // find pair with given difference, portal
    public static void findPair(int[]arr,int target) {
        int lo = 0;
        int hi = 1;
        Arrays.sort(arr);
        while(hi < arr.length && lo < hi) {
            int diff = arr[hi] - arr[lo];
            if(diff == target) {
                System.out.println(arr[lo] + " " + arr[hi]);
                return;
            } else if(diff > target) {
                lo++;
            } else {
                hi++;
            }
        }
        System.out.println(-1);
    }

    // roof top, portal
    public static int findMaxSteps(int[]arr) {
        int count = 0;
        int res = 0;

        for(int i = 1; i < arr.length; i++) {
            if(arr[i - 1] < arr[i]) {
                count++;
            } else {
                res = Math.max(res, count);
                count = 0;
            }
        }
        res = Math.max(res, count);
        return res;
    }

    // maximize sum of arr[i] * i, portal
    public static int maximise(int[]arr) {
        long sum = 0;
        int mod = (int)(1e9) + 7;
        Arrays.sort(arr);
        for(long i = 0; i < arr.length; i++) {
            sum = sum + ((long)(arr[(int)i]) * i);
        }
        return (int)(sum % mod);
    }

    // maximise config. arr[i] * i, portal
    public static int maximise1(int[]arr) {
        int sum  = 0;
        int sIm1 = 0; // s[i - 1]
        for(int i = 0; i < arr.length; i++) {
            sIm1 += arr[i] * i;
            sum += arr[i];
        }
        int res = sIm1;
        for(int i = 1; i < arr.length; i++) {
            int sI = sIm1 + sum - n * arr[n - i];
            res = Math.max(sI, res);
            sIm1 = sI;
        }
        return res;
    }

    // =================Binary Search======================
    // 1. Normal Binary Search
    public int binarySearch(int[] arr, int dtf) {
        int lo = 0;
        int hi = arr.length - 1;
        int indx = -1;

        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if(arr[mid] == dtf) {
                indx = mid;
                break;
            } else if(arr[mid] > dtf) {
                // discard right side
                hi = mid - 1;
            } else {
                // discard left side
                lo = mid + 1;
            }
        }
        return indx;
    }

    // 2. Binary Search First Index
    public int binarySearchFirstIndex(int[] arr, int dtf) {
        int lo = 0;
        int hi = arr.length - 1;
        int indx = -1;

        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if(arr[mid] == dtf) {
                indx = mid;
                hi = mid - 1;
            } else if(arr[mid] > dtf) {
                // discard right side
                hi = mid - 1;
            } else {
                // discard left side
                lo = mid + 1;
            }
        }
        return indx;
    }

    // 3. Binary Search Last Index
    public int binarySearchLastIndex(int[] arr, int dtf) {
        int lo = 0;
        int hi = arr.length - 1;
        int indx = -1;

        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if(arr[mid] == dtf) {
                indx = mid;
                lo = mid + 1;
            } else if(arr[mid] > dtf) {
                // discard right side
                hi = mid - 1;
            } else {
                // discard left side
                lo = mid + 1;
            }
        }
        return indx;
    }

    // first Transition Point, portal
    public static int findTransition(int[] arr) {
        int indx = -1;
        int lo = 0;
        int hi = arr.length - 1;
        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if(arr[mid] == 1) {
                indx = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return indx;
    }
    
    // leetcode 658, https://leetcode.com/problems/find-k-closest-elements/
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        LinkedList<Integer> res = new LinkedList<>();
        int lo = 0;
        int hi = arr.length - 1;
        int indx = 0;

        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            indx = Math.abs(arr[indx] - x) > Math.abs(arr[mid] - x) ? mid: indx;
            if(arr[mid] == x) {
                break;
            } else if(arr[mid] > x) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }

        lo = indx - 1;
        hi = indx;
        while(res.size() < k && lo >= 0 && hi < arr.length) {
            if(Math.abs(x - arr[lo]) <= Math.abs(arr[hi] - x)) {
                res.addFirst(arr[lo]);
                lo--;
            } else {
                res.addLast(arr[hi]);
                hi++;
            }
        }

        while(res.size() < k && lo >= 0) {
            res.addFirst(arr[lo]);
            lo--;
        }
        
        while(res.size() < k && hi < arr.length) {
            res.addLast(arr[hi]);
            hi++;
        }
        return res;
    }

    // leetcode 33, https://leetcode.com/problems/search-in-rotated-sorted-array/
    public int search(int[] arr, int target) {
        int lo = 0;
        int hi = arr.length - 1;
        int indx = -1;
        
        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if(arr[mid] == target) {
                // data found
                indx = mid;
                break;
            } else if(arr[mid] < arr[hi]) {
                // right side is sorted
                if(arr[mid] < target && target <= arr[hi]) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            } else {
                // left side is sorted
                if(arr[lo] <= target && target < arr[mid]) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
        }
        
        return indx;
    }

    // minimum in sorted rotated array + rotation count
    public static int findMinimum(int[]arr) {
        int lo = 0;
        int hi = arr.length - 1;

        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if(arr[mid] < arr[hi]) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return arr[hi];
    }
}