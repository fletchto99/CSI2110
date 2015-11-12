public class DecisionStump {

    private int featureIndex; // feature index on which
    private double threshold; // threshold is applied

    public static int dim; // input feature vector dimension
    public static int nClasses; // number of classes

    private int[] classFreq; // number of time instance of this class has reached this stump
    private int total; // number of trained samples having reached this stump

    private DecisionStump smallerBranch; // if feature value < threshold
    private DecisionStump greaterBranch; // if feature value >= threshold
    private DecisionStump parent; // a reference to the parent node

    // constructor for a leaf
    public DecisionStump() {

        this.featureIndex = -1;
        this.threshold = 0.0;

        classFreq = new int[nClasses];
    }

    // constructor for a stump
    public DecisionStump(int featureIndex, double threshold) {

        this.featureIndex = featureIndex;
        this.threshold = threshold;

        classFreq = new int[nClasses];
    }

    // get the class associated with this decision tree
    public int getDecision(double[] vector) {

        if (isExternal()) {
            return getMaxClass();
        }

        // go left
        if (vector[featureIndex] < threshold) {
            return smallerBranch.getDecision(vector);
        }
        // go right
        else {
            return greaterBranch.getDecision(vector);
        }
    }

    // return the class having highest probability
    public int getMaxClass() {

        double max = -1.;
        int index = 0;
        for (int i = 0; i < nClasses; i++) {
            if (classFreq[i] > max) {
                max = classFreq[i];
                index = i;
            }
        }

        return index;
    }

    // returns the number of samples that hit this node
    public int getTotal() {
        return total;
    }

    // return the highest probability
    public double getMaxProb() {

        if (total == 0) {
            return 0.0;
        }

        return (double) (classFreq[getMaxClass()]) / total;
    }

    // manually set the probability of class c (in %)
    // to use instead of updateProbCount
    void setProb(int c, int percentage) {

        classFreq[c] = percentage;
        total = 100;
    }

    // call this method to compute probability associated with each stump of the tree
    // the input vector is of class c
    void updateProbCount(double[] vector, int c) {

        total++;
        classFreq[c]++;
        if (!isExternal()) {

            if (vector[featureIndex] < threshold) {
                smallerBranch.updateProbCount(vector, c);
            } else {
                greaterBranch.updateProbCount(vector, c);
            }
        }
    }

    // reset to 0 all probability counts
    void resetProbCount() {

        total = 0;
        for (int i = 0; i < nClasses; i++) {
            classFreq[i] = 0;
        }
    }

    // adds a new branch on the left
    public void setSmallerBranch(DecisionStump ds) {

        smallerBranch = ds;
        ds.parent = this;
    }

    // adds a new branch on the right
    public void setGreaterBranch(DecisionStump ds) {

        greaterBranch = ds;
        ds.parent = this;
    }

    // returns the parent of this node
    public DecisionStump getParent() {

        return parent;
    }

    // returns the left child of this node
    public DecisionStump getSmallerBranch() {

        return smallerBranch;
    }

    // returns the right child of this node
    public DecisionStump getGreaterBranch() {

        return greaterBranch;
    }

    // checks if it is a leaf
    public boolean isExternal() {

        return featureIndex == -1;
    }

    // toString
    public String toString() {

        StringBuilder out = new StringBuilder("(" + featureIndex + " of " + dim + " ," + threshold + " , [" + classFreq[0]);
        for (int i = 1; i < nClasses; i++) {
            out.append(",").append(classFreq[i]);
        }
        out.append("] of ").append(total).append(")");

        return out.toString();
    }

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

