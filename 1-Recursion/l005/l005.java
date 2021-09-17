 public static int twoSubsetsEqualSum(int[] arr, int idx, int sum1, String str1, int sum2, String str2) {
        if (idx == arr.length) {
            if (sum1 == sum2) {
                System.out.println(str1 + " = " + str2);
                return 1;
            }
            return 0;
        }

        int count = 0;
        count += twoSubsetsEqualSum(arr, idx + 1, sum1 + arr[idx], str1 + arr[idx] + " ", sum2, str2);
        count += twoSubsetsEqualSum(arr, idx + 1, sum1, str1, sum2 + arr[idx], str2 + arr[idx] + " ");

        return count;
    }

    public static void twoProblem() {
        int[] arr = { 10, 20, 30, 40, 50, 60, 70, 80 };
        System.out.println(twoSubsetsEqualSum(arr, 0, 0, "", 0, ""));

    }

    public static void kSubSetEqualSum(int[] arr, int vidx, int[] totalSet, ArrayList<ArrayList<Integer>> ans) {

        if (vidx == arr.length) {
            int val = totalSet[0];
            for (int i = 1; i < totalSet.length; i++) {
                if (val != totalSet[i]) {
                    return;
                }
            }

            System.out.println(ans);

            return;
        }

        for (int i = 0; i < totalSet.length; i++) {

            if (totalSet[i] - arr[vidx] < 0) // if target getting negetive
                continue;

            boolean isFirstTime = false;
            if (ans.get(i).size() == 0)
                isFirstTime = true; // make leader

            totalSet[i] -= arr[vidx];
            ans.get(i).add(arr[vidx]);

            kSubSetEqualSum(arr, vidx + 1, totalSet, ans);

            totalSet[i] += arr[vidx];
            ans.get(i).remove(ans.get(i).size() - 1);

            if (isFirstTime)
                break;
        }

    }

    public static void kSubSetEqualSum(int[] arr, int k) {
        int sum = 0;
        for (int ele : arr)
    k        sum += ele;

        if (sum % k != 0 || arr.length < k)
            return;

        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < k; i++)
            ans.add(new ArrayList<>());

        int tar = sum / k;
        int[] totalSet = new int[k];
        Arrays.fill(totalSet, tar);
        kSubSetEqualSum(arr, 0, totalSet, ans);

    }

    public static void kSubSetProblem() {
        int[] arr = { 1, 2, 3, 4, 5, 6 };
        kSubSetEqualSum(arr, 3);
    }

    public static boolean isPlaindrome(String str) {
        int si = 0, ei = str.length() - 1;
        while (si <= ei) {
            if (str.charAt(si++) != str.charAt(ei--))
                return false;
        }

        return true;
    }

    public static void allPalindromicPartitions(String str, String asf) {
        if (str.length() == 0) {
            System.out.println(asf);
            return;
        }

        for (int i = 0; i < str.length(); i++) {
            String smallStr = str.substring(0, i + 1);
            if (isPlaindrome(smallStr)) {
                allPalindromicPartitions(str.substring(i + 1), asf + "(" + smallStr + ") ");
            }
        }
    }

    // Friends Pairing
    static int counter = 1;

    public static int friendsPairing(int totalFriends, boolean[] vis, String asf) {
        if (totalFriends == 0) {
            System.out.println(counter++ + "." + asf);
            return 1;
        }

        int firstUnusedFriend = 0;
        for (int i = 1; i < vis.length; i++) {
            if (!vis[i]) {
                firstUnusedFriend = i;
                break;
            }
        }

        int count = 0;
        vis[firstUnusedFriend] = true;

        count += friendsPairing(totalFriends - 1, vis, asf + "(" + firstUnusedFriend + ") "); // single

        for (int i = firstUnusedFriend + 1; i < vis.length; i++) {
            if (!vis[i]) {
                vis[i] = true;
                count += friendsPairing(totalFriends - 2, vis, asf + "(" + firstUnusedFriend + "," + i + ") "); // pairup
                vis[i] = false;
            }

        }

        vis[firstUnusedFriend] = false;

        return count;
    }

    public static void friendsPairing() {
        int N = 5;
        boolean[] vis = new boolean[N + 1];
        System.out.println(friendsPairing(N, vis, ""));
    }

    // Largest Possible Number in K Swap

    public static int maxNum = 0;

    public static void swap(StringBuilder sb, int i, int j) {
        char ch1 = sb.charAt(i);
        char ch2 = sb.charAt(j);

        sb.setCharAt(i, ch2);
        sb.setCharAt(j, ch1);
    }

    public static void largestNumber(StringBuilder sb, int k) {
        if (k == 0) {
            return;
        }

        boolean flag = false;
        for (int i = 0; i < sb.length(); i++) {
            char maxCh = sb.charAt(i);
            int idx = 0;
            for (int j = i + 1; j < sb.length(); j++) {
                if (sb.charAt(j) > maxCh) {
                    maxCh = sb.charAt(j);
                    idx = j;
                }
            }

            if (i == idx)
                continue;

            swap(sb, i, idx);
            flag = true;
            int possibleNum = Integer.parseInt(sb.toString());
            if (possibleNum > maxNum)
                maxNum = possibleNum;

            largestNumber(sb, k - 1);

            // swap(sb, i, idx);
        }

        if (!flag)
            return;

    }

    public static void largestNumber() {
        String str = "56294137";
        int k = 4;
        largestNumber(new StringBuilder(str), k);
        System.out.println(maxNum);
    }

    public static void main(String[] args) {
        // crossWord();
        // subSetProblem();
        // kSubSetProblem();
        // friendsPairing();
        largestNumber();
    }