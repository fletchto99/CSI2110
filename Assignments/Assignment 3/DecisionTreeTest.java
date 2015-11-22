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
            final List<String> lines = Files.readAllLines(Paths.get(args[0]), StandardCharsets.UTF_8);

            if (lines != null) {

                DecisionTree specificTree = buildSpecificTree(lines);
                DecisionTree randomTree = buildRandomTree(lines, true);

                Double specificProbibility = specificTree.getAvgExternalProbibality();

                System.out.printf("Generally the random algorithm in part C produces more accurate results than %nthe algorithm in part b. This can be seen because there are more external %ndecision stumps with a higher max probability. Therefore the classes are spread %nmore evenly. In this case the average probability for a decision of the %nexternal nodes in the random tree was %.2f and the specific tree was %.2f%n%n", randomTree.getAvgExternalProbibality(), specificProbibility);


                System.out.printf("Now generating 1000 decision trees to determine if a random tree is better %n%n");

                AtomicInteger greater = new AtomicInteger(0);
                IntStream.range(0, 1000).parallel().forEach(i -> {
                    try {
                        DecisionTree rand = buildRandomTree(lines, false);
                        if (rand.getAvgExternalProbibality() > specificProbibility) {
                            greater.getAndIncrement();
                        }
                    } catch (NodeReplaceException e) {
                        System.out.println("Error building random tree");
                        System.exit(1);
                    }
                });

                System.out.printf("Of the 1000 random decision trees built, %d had an average decision probability greater than that of the specific tree", greater.get());
            }
        } catch (IOException e) {
            System.out.println("Error reading file!");
        } catch (NodeReplaceException e) {
            System.out.println("Attempted to replace a node which was not external!");
        }
    }

    public static DecisionTree buildSpecificTree(List<String> lines) throws NodeReplaceException {
        System.out.println("--------PART B ANSWER--------");
        System.out.printf("Creating decision tree%n%n");
        final DecisionTree specificTree = new DecisionTree(6, 3);

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

        DecisionTree randomTree = new DecisionTree(6, 3);
        randomTree.replace(randomTree.getRoot(), r.nextInt(4), r.nextDouble() * 10.0);
        train(lines, randomTree);

        int iterations = r.nextInt(50);

        if (print) {
            System.out.printf("Performing %d training iterations on random tree%n%n", iterations);
        }

        IntStream.rangeClosed(0, iterations).forEach(i -> {
            //replace the smallest node
            try {
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

    private static void train(final List<String> lines, final DecisionTree dt) {
        lines.forEach(line -> {
            String[] split = line.split(",");
            if (split.length != 5) {
                throw new RuntimeException("Invalid file configuration");
            }
            double[] path = new double[4];
            for (int i = 0; i < 5; i++) {
                if (i < 4) {
                    path[i] = Double.parseDouble(split[i]);
                } else {
                    dt.train(path, getDecisionClass(split[i]));
                }
            }
        });
    }


}
