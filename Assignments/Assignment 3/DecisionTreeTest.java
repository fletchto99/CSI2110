import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class DecisionTreeTest {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Please specify a path to the file!");
            System.exit(1);
        }

        try {

            //Read all of the lines of hte pre-define file specified
            final List<String> lines = Files.readAllLines(Paths.get(args[0]), StandardCharsets.UTF_8);

            //Ensure the file contains some lines
            if (lines != null) {

                //Build our initial trees
                DecisionTree specificTree = buildSpecificTree(lines);
                DecisionTree randomTree = buildRandomTree(lines, true);

                //Calculate the specific tree's probability
                Double specificProbibility = specificTree.getAvgExternalProbibality();

                //Answer the last part of question C
                System.out.printf("Generally the random algorithm in part C produces more accurate results than %nthe algorithm in part b. This can be seen because there are more external %ndecision stumps with a higher max probability. Therefore the classes are spread %nmore evenly. In this case the average probability for a decision of the %nexternal nodes in the random tree was %.2f%% and the specific tree was %.2f%%%n%n", randomTree.getAvgExternalProbibality(), specificProbibility);

                //Test a known decision to make sure it compares correctly
                double[] test = {5.2,3.5,1.5,0.2};
                System.out.println("Testing a known vector against both the random tree and specific tree: 5.2,3.5,1.5,0.2 [expected: Iris-setosa]");
                System.out.printf("Testing vector against specific tree: %s%n", getDecisionString(specificTree.getDecision(test)));
                System.out.printf("Testing vector against random tree: %s%n%n", getDecisionString(specificTree.getDecision(test)));

                //More proof to backup our answer to question c
                System.out.printf("Now generating 1000 decision trees to determine if a random tree is better %n%n");

                //Determine, in parallel, how many of 1000 trees have a higher avg probability than our specific tree
                AtomicInteger greater = new AtomicInteger(0);
                IntStream.range(0, 1000).parallel().forEach(i -> {
                    try {
                        //build a random tree with no output
                        DecisionTree rand = buildRandomTree(lines, false);

                        //compare the random tree to our specific tree's probability
                        if (rand.getAvgExternalProbibality() > specificProbibility) {
                            //Increment our counter
                            greater.getAndIncrement();
                        }
                    } catch (NodeReplaceException e) {
                        System.out.println("Error building random tree");
                        System.exit(1);
                    }
                });

                //Output the results from all of our random trees
                System.out.printf("Of the 1000 random decision trees built, %d had an average decision probability greater than that of the specific tree", greater.get());

            }
        } catch (IOException e) {
            //The file wasn't able to be read
            System.out.println("Error reading file!");
        } catch (NodeReplaceException e) {
            //Should never happen!
            System.out.println("Attempted to replace a node which was not external!");
        }
    }

    /**
     * Builds a 3 stump specific decision tree based on a list of lines
     * format: [4 doubles comma separated, followed by the expected class]
     *
     * @param lines The lines of the file
     * @return The generated decision tree
     * @throws NodeReplaceException Error replacing the node in the decision tree
     */
    public static DecisionTree buildSpecificTree(List<String> lines) throws NodeReplaceException {
        System.out.println("--------PART B ANSWER--------");
        System.out.printf("Creating decision tree%n%n");
        final DecisionTree specificTree = new DecisionTree(4, 3);

        System.out.printf("Updating root to use index=0 threshold=5%n%n");
        specificTree.replace(specificTree.getRoot(), 0, 5);

        System.out.printf("Training decision tree from file specified%n%n");
        train(lines, specificTree);

        System.out.printf("Printing a 1 node decision tree%n%n");
        specificTree.print();

        System.out.printf("Replacing node with smallest maximal probability%n%n");
        specificTree.replace(specificTree.getSmallestMaxProb(), 2, 2.5);

        System.out.printf("Resetting decision tree%n%n");
        specificTree.resetAll();

        System.out.printf("Retraining decision tree%n%n");
        train(lines, specificTree);

        System.out.printf("Printing a 2 node decision tree%n%n");
        specificTree.print();

        System.out.printf("Replacing smallest max prob node%n%n");
        specificTree.replace(specificTree.getSmallestMaxProb(), 1, 3);


        System.out.printf("Resetting decision tree%n%n");
        specificTree.resetAll();

        System.out.printf("Retraining decision tree%n%n");
        train(lines, specificTree);

        System.out.printf("Printing a 3 node decision tree%n%n");
        specificTree.print();

        return specificTree;
    }

    /**
     * Builds a random decision tree which is trained upto a maximum of 50 times based on a list of lines
     * format: [4 doubles comma seperated, followed by the expected class]
     *
     * @param lines The lines of the file
     * @param print Determines if the output should be printed
     * @return The generated decision tree
     * @throws NodeReplaceException Error replacing the node in the decision tree
     */
    public static DecisionTree buildRandomTree(List<String> lines, boolean print) throws NodeReplaceException {
        if (print) {
            System.out.printf("%n--------PART C ANSWER--------%n");
        }

        //Create an instance of random
        final Random r = new Random();

        //Generate random threshold and indexes

        //Build the decision tree
        if (print) {
            System.out.printf("Generating a random decision tree from file specified%n%n");
        }

        //build the tree
        DecisionTree randomTree = new DecisionTree(4, 3);

        //replace the root node
        randomTree.replace(randomTree.getRoot(), r.nextInt(4), r.nextDouble() * 10.0);

        //Train the decision tree
        train(lines, randomTree);

        //Determine a random amount of iterations to train the tree
        int iterations = r.nextInt(50);

        if (print) {
            System.out.printf("Performing %d training iterations on random tree%n%n", iterations);
        }

        IntStream.rangeClosed(0, iterations).forEach(i -> {
            //replace the smallest node
            try {
                //replace the most inaccurate node with a newly generated random node
                randomTree.replace(randomTree.getSmallestMaxProb(), r.nextInt(4), r.nextDouble() * 10.0);
            } catch (NodeReplaceException e) {
                System.out.println("Attempted to replace a node which is not external!");
                System.exit(1);
            }

            //Reset the tree
            randomTree.resetAll();

            //Train the tree with the lines we read
            train(lines, randomTree);

        });

        if (print) {
            System.out.println("Printing a random decision tree");
            randomTree.print();
            System.out.println();
        }
        return randomTree;
    }

    /**
     * Converts a class name to an index
     *
     * @param clazz The class name to convert to the index
     * @return The index of the class
     */
    private static int getDecisionClass(final String clazz) {
        switch (clazz) {
            case "Iris-setosa":
                return 0;
            case "Iris-versicolor":
                return 1;
            case "Iris-virginica":
                return 2;
        }
        return -1;
    }

    /**
     * Converts a class index to a class name
     *
     * @param clazz The index of the class
     * @return The name of the class
     */
    private static String getDecisionString(final int clazz) {
        switch (clazz) {
            case 0:
                return "Iris-setosa";
            case 1:
                return "Iris-versicolor";
            case 2:
                return "Iris-virginica";
        }
        return "Undefined class!";
    }

    /**
     * Trains a decision tree based on a set of pre-know decisions
     *
     * @param lines The lines of all of the already known decisions. All lines should be
     *              in the expected format [#,#,#,#,class]
     * @param dt The decision tree to train
     */
    private static void train(final List<String> lines, final DecisionTree dt) {
        //Iterate over all of the known decisions
        lines.forEach(line -> {
            //Break the line down into it's individual components which are seperated by a comma
            String[] split = line.split(",");
            //Validate the line
            if (split.length != 5) {
                throw new RuntimeException("Invalid file configuration");
            }
            double[] path = new double[4];
            for (int i = 0; i < 5; i++) {
                if (i < 4) {
                    //add to the vector while we are less than 4
                    path[i] = Double.parseDouble(split[i]);
                } else {
                    //train with our vector we built
                    dt.train(path, getDecisionClass(split[i]));
                }
            }
        });
    }


}
