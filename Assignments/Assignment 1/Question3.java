public class Question3 {

    public static int swaps = 0;


    public static void main(String[] args) {

        perm(3, 0);
        System.out.println(swaps);
    }

    static void perm(int n, int i) {
        if (i != n) {
            for (int j = i; j < n; j++) {
                swaps += 2;
                perm(n, i + 1);
            }
        }
    }
}