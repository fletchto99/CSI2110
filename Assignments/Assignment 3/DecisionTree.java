public class DecisionTree {

    private DecisionStump root;

    /**
     * Creates a decision tree
     *
     * @param dimension The dimension of the decision tree
     * @param numberOfClasses The number of classes
     */
    public DecisionTree(int dimension, int numberOfClasses) {
        DecisionStump.nClasses = numberOfClasses;
        DecisionStump.dim = dimension;
        root = new DecisionStump();
    }

    /**
     * Fetches the root of the decision tree
     *
     * @return The root of the decision tree
     */
    public DecisionStump getRoot() {
        return root;
    }

    /**
     * Retrieves the decision according to the vector input
     *
     * @param vector The vector to input
     * @return The decided class
     */
    public int getDecision(double[] vector) {
        return root.getDecision(vector);
    }

    /**
     * Replaces a leaf stump with a new decision stump
     *
     * @param leaf The leaf to replace
     * @param featureIndex The feature index being analyzed by the decision stump
     * @param threshold The threshold to meet
     * @throws NodeReplaceException Only external leafs can be replaced, if the leaf is not external then this exception is thrown.
     */
    public void replace(DecisionStump leaf, int featureIndex, double threshold) throws NodeReplaceException {

        //Ensure a leaf is being replaced
        if (!leaf.isExternal()) {
            throw new NodeReplaceException("Leaf being replaced must be external!");
        }

        //Build the replacement node
        DecisionStump replacement = new DecisionStump(featureIndex, threshold);
        replacement.setGreaterBranch(new DecisionStump());
        replacement.setSmallerBranch(new DecisionStump());

        //Retrieve the parent so that we can get the instance of the leaf within our decision tree
        DecisionStump parent = leaf.getParent();

        //Essentially do leaf=replacement; but act on the instance within the decision tree, instead of the local variable

        //Only the root node will have a null parent, if that is the case then replace the root
        if (parent != null) {
            //Replace the proper child
            if (parent.getSmallerBranch() == leaf) {
                parent.setSmallerBranch(replacement);
            } else {
                parent.setGreaterBranch(replacement);
            }
        } else {
            this.root = replacement;
        }

    }

    /**
     * Retrieve the external with the smallest probability for a proper decision
     * @return The external with the smallest probability for a proper decision
     */
    public DecisionStump getSmallestMaxProb() {
        //Initiate the recursive method to retrieve the smallest max prob
        return getSmallestMaxProbRecursive(this.root, null);
    }

    /**
     * Retrieve the external with the smallest probability for a proper decision
     *
     * @param current The current stump being analyzed; Should be initialized with the root
     * @param smallest The current smallest node; Should be initialized with null
     * @return The external with the smallest probability for a proper decision
     */
    private DecisionStump getSmallestMaxProbRecursive(DecisionStump current, DecisionStump smallest) {
        //Base case is when we've reached an external node, if we are not external then continue recursion
        if (!current.isExternal()) {

            //We must analyze all sleft and right sub-trees
            DecisionStump left = getSmallestMaxProbRecursive(current.getSmallerBranch(), smallest);
            DecisionStump right = getSmallestMaxProbRecursive(current.getGreaterBranch(), smallest);

            //Only return the smaller of the two subtrees
            return left.getMaxProb() < right.getMaxProb() ? left : right;
        }
        //We're at a base case, check if the node is still null and if it is then the current is the smallest
        //Null should only ever happen when the tree only has one stump, the root
        return (smallest == null || current.getMaxProb() < smallest.getMaxProb()) ? current : smallest;
    }

    /**
     * Trains a decision tree based on an input vector and the expected class
     *
     * @param vector The vector which should result in the expected class
     * @param classNumber The expected class
     */
    public void train(double[] vector, int classNumber) {
        root.updateProbCount(vector, classNumber);
    }

    /**
     * Resets the probability count of all of the nodes
     */
    public void resetAll() {
        //Initiate the recursive reset using the root node
        reset(root);
    }

    /**
     * Recursive resets all of the decision stump's probabilities
     * @param ds The decision stump to reset and whose subtrees should also be reset
     */
    private void reset(DecisionStump ds) {

        ds.resetProbCount();

        if (!ds.isExternal()) {
            reset(ds.getSmallerBranch());
            reset(ds.getGreaterBranch());
        }
    }

    /**
     * Prints all of the nodes in a pre-order traversal
     */
    public void print() {
        //Initiate the recursive pre-order print
        preOrderPrint(this.root);
    }

    /**
     * Prints the nodes in a pre order (parent, left child, right child)
     *
     * @param stump The stump to print
     */
    private void preOrderPrint(DecisionStump stump) {
        //Determine if this stump is undefined (when the max value probability is set)
        if (stump.getMaxProb() == Double.MAX_VALUE) {
            //Print out for an undefined probability
            System.out.printf("%s has an undefined probability%n", stump);
        } else {
            //Print-out for a defined probability
            System.out.printf("%s max probability class is %d with %.2f%%%n", stump, stump.getMaxClass(), (stump.getMaxProb() != Double.MAX_VALUE ? stump.getMaxProb() * 100 : -1));
        }

        DecisionStump left, right;

        //Recursively pre-order print the children if they are defined
        if ((left = stump.getSmallerBranch()) != null) {
            preOrderPrint(left);
        }
        if ((right = stump.getGreaterBranch()) != null) {
            preOrderPrint(right);
        }
    }

    /**
     * Calculates the average probability of all defined external decision stumps.
     * If the stump is node defined I.E. contains no decisions, then it is not included
     *
     * @return The average probability as a percentage 0.00 to 100.00
     */
    public double getAvgExternalProbibality() {
        //Calculate the sum of probabibilities of all defined external nodes
        double totalProb = getTotalProbiblality(this.root);

        //Determine how many external nodes are well defined
        int numExternal = getNumExternal(this.root);

        //Calculate the probability (Total defined external nodes summed / number of defined external nodes)
        return totalProb / numExternal;
    }

    /**
     * Calculates the sum of all defined decision stumps
     *
     * @param ds The decision stump to calculate for; Should initially be the root
     * @return The sum of all of the probabilities of all well defined external stumps
     */
    private double getTotalProbiblality(DecisionStump ds) {
        //Base case, we are an external node
        if (ds.isExternal()) {
            //Check if the node is valid
            if (ds.getMaxProb() == Double.MAX_VALUE) {
                //We are not defined, return 0 so nothing is added
                return 0;
            }
            return ds.getMaxProb() * 100;
        }

        //Calculate the sum of probabilities of the subtrees
        return getTotalProbiblality(ds.getSmallerBranch()) + getTotalProbiblality(ds.getGreaterBranch());
    }

    /**
     * Recursively calculates the number of well defined external decision stumps
     * @param ds The decision stump to add to the external count; Should initally be the root
     * @return The number of well defined external decision stumps
     */
    private int getNumExternal(DecisionStump ds) {

        //Base case we are an external decision stump
        if (ds.isExternal()) {
            //Check if we are defined
            if (ds.getMaxProb() == Double.MAX_VALUE) {
                //not defined, don't add it to the count
                return 0;
            }

            //Defined, increase the count by 1
            return 1;
        }

        //Calculate the number of well defined external nodes of the left and right sub trees
        return getNumExternal(ds.getGreaterBranch()) + getNumExternal(ds.getSmallerBranch());
    }
}