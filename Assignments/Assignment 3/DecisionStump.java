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

}

