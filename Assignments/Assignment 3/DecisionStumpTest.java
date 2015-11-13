public class DecisionStumpTest {

    public static void main(String[] args) {

        // Build a decision tree
        // feature 0 is gender (0: female 1:male)
        // feature 1 is age
        // class 0 is does not wear glasses
        // class 1 is wear glasses
        DecisionStump.nClasses = 2;
        DecisionStump.dim = 2;

        // is age < 45
        DecisionStump s1 = new DecisionStump(1, 45);
        DecisionStump s2 = new DecisionStump(); // leaf
        s2.setProb(1, 89); // wear glasses at 89%

        // is man?
        DecisionStump s3 = new DecisionStump(0, 0.5);

        DecisionStump s4 = new DecisionStump(); // leaf
        s4.setProb(0, 61); // does not wear glasses at 61%

        // is age < 24
        DecisionStump s5 = new DecisionStump(1, 24);
        DecisionStump s6 = new DecisionStump(); // leaf
        s6.setProb(0, 62); // does not wear glasses at 62%
        DecisionStump s7 = new DecisionStump(); // leaf
        s7.setProb(1, 53); // wear glasses at 53%

        s1.setSmallerBranch(s3);
        s1.setGreaterBranch(s2);
        s3.setSmallerBranch(s5);
        s3.setGreaterBranch(s4);
        s5.setSmallerBranch(s6);
        s5.setGreaterBranch(s7);

        double[] test1 = {1, 55};
        System.out.println("Man age 55 = " + s1.getDecision(test1));
        double[] test2 = {1, 35};
        System.out.println("Man age 35 = " + s1.getDecision(test2));
        double[] test3 = {0, 75};
        System.out.println("Woman age 75 = " + s1.getDecision(test3));
        double[] test4 = {0, 27};
        System.out.println("Woman age 27 = " + s1.getDecision(test4));
        double[] test5 = {1, 15};
        System.out.println("Woman age 15 = " + s1.getDecision(test5));
    }

}
