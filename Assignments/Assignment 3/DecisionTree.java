import java.util.*;
import java.io.*;

public class DecisionTree {

    private DecisionStump root;

    public DecisionTree(int dimension, int numberOfClasses) {
        DecisionStump.nClasses = numberOfClasses;
        DecisionStump.dim = dimension;
        root = new DecisionStump();
    }

    // returns the root of the decision tree
    public DecisionStump getRoot() {

        return root;
    }

    // get the class associated with this vector according to the decision tree
    public int getDecision(double[] vector) {
        return root.getDecision(vector);
    }

    // replace a leaf node by a DecisionStump with two leaves
    public void replace(DecisionStump leaf, int featureIndex, double threshold) {
        if (!leaf.isExternal()) {
            throw new RuntimeException("Leaf must be external to be replaced!");
        }
        DecisionStump replacement = new DecisionStump(featureIndex, threshold);
        replacement.setGreaterBranch(new DecisionStump());
        replacement.setSmallerBranch(new DecisionStump());
        DecisionStump parent;
        if ((parent = leaf.getParent()) != null) {
            if (parent.getSmallerBranch() == leaf) {
                parent.setSmallerBranch(replacement);
            } else {
                parent.setGreaterBranch(replacement);
            }
        } else {
            root = replacement;
        }

    }

    // gets the leaf with the smallest maximal class probability
    public DecisionStump getSmallestMaxProb() {
        return getSmallestMaxProbRecursive(this.root);
    }

    private DecisionStump getSmallestMaxProbRecursive(DecisionStump smallest) {
        return smallest.getSmallerBranch() != null ? smallest.getSmallerBranch() : smallest;
    }


    // updates the probability count of each node of the decision tree
    // based on a sample for which the associated class is known
    public void train(double[] vector, int classNumber) {

        root.updateProbCount(vector, classNumber);
    }

    // reset the probability counts of all nodes
    public void resetAll() {
        reset(root);
    }

    private void reset(DecisionStump ds) {

        ds.resetProbCount();

        if (!ds.isExternal()) {

            ds.getSmallerBranch().resetProbCount();
            ds.getGreaterBranch().resetProbCount();
        }
    }

    // pre-order print of all nodes
    public void print() {
        preOrderPrint(this.root);
    }

    private void preOrderPrint(DecisionStump stump) {
        System.out.println(stump);
        DecisionStump left, right;
        if ((left = stump.getSmallerBranch()) != null) {
            preOrderPrint(left);
        }
        if ((right = stump.getSmallerBranch()) != null) {
            preOrderPrint(right);
        }
    }

    public static void main(String[] args) {

        // here is how to read the file containing the pre-classified samples
        try {
            Scanner scanner = new Scanner(new File("iris.data.txt"));
            scanner.useDelimiter("[,\r\n]+");
            while (scanner.hasNextDouble()) {
                System.out.println("=" + scanner.nextDouble());
                System.out.println("=" + scanner.nextDouble());
                System.out.println("=" + scanner.nextDouble());
                System.out.println("=" + scanner.nextDouble());
                System.out.println("=" + scanner.next());
            }
        } catch (Exception e) {
            System.out.println("Error reading file...");
        }

        // ********* add code for part B and part C here *************** //
    }
}