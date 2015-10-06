public class Question3 {


    public static void main(String[] args) {
        int size = 10;
        System.out.println(perm(size));
        System.out.println(calculateSwaps(size));
        System.out.println(calculateSwapsString(size));
    }

    //Initial recursive call to perm
    private static int perm(int size) {
        return perm(size, 0);
    }

    //Recursive method to calculate number of swaps, based on the algorithm given in class
    private static int perm(int n, int i) {
        int swaps = 0;
        if (i != n) {
            for (int j = i; j < n; j++) {
                swaps += perm(n, i + 1);
            }
        }
        return swaps + 2*(n-i);
    }

    //Iterative method to calculate number of swaps
    private static int calculateSwaps(int n) {
        int swaps = 0;
        for (int i = 1; i <= n; i++) {
            int innerResult = n;
            for (int j = 1; j <= (n - i); j++) {
                innerResult*= (n - j);
            }
            swaps += innerResult;
        }
        return 2 * swaps; // The 2 has been factored out

        //size 3: 2(3+3(3-1)+3(3-1)(3-2))
    }

    //Iterative method to build the string for the number of swaps
    static String calculateSwapsString(int n) {
        String sum = "2(";
        //Case to handle 0
        if (n <= 0) {
            return sum + "0)";
        }
        //Build the string to determine the amount of swaps, not simplified but has 2 factored out
        for (int i = n; i > 0; i--) {
            String product = String.valueOf(n);
            for (int j = 1; j <= (n - i); j++) {
                product += "(" + n +"-" +j+")";
            }
            sum += product + "+";
        }
        return sum.substring(0, sum.length() - 1) + ")";
    }
}