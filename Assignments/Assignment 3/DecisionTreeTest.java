import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
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
                System.out.println("--------PART B ANSWER--------");
                System.out.printf("Creating decision tree%n%n");
                DecisionTree dt = new DecisionTree(6, 3);

                System.out.printf("Updating root to use index=0 threshold=5%n%n");
                dt.replace(dt.getRoot(), 0, 5);

                System.out.println("Training decision tree from file specified%n%n");
                train(lines, dt);

                System.out.println("Printing a 1 node decision tree%n%n");
                dt.print();

                System.out.printf("Replacing node with smallest maximal probability%n%n");
                dt.replace(dt.getSmallestMaxProb(), 2, 2.5);

                System.out.printf("Resetting decision tree%n%n");
                dt.resetAll();

                System.out.printf("Retraining decision tree%n%n");
                train(lines, dt);

                System.out.printf("Printing a 2 node decision tree%n%n");
                dt.print();

                System.out.printf("Replacing smallest max prob node%n%n");
                dt.replace(dt.getSmallestMaxProb(), 1, 3);


                System.out.printf("Resetting decision tree%n%n");
                dt.resetAll();

                System.out.printf("Retraining decision tree%n%n");
                train(lines, dt);

                System.out.printf("Printing a 3 node decision tree%n%n");
                dt.print();


                System.out.println("--------PART C ANSWER--------");

                //Create an instance of random
                final Random r = new Random();

                //Generate random threshold and indexes

                //Build the decision tree
                System.out.printf("Generating a random decision tree%n%n");
                DecisionTree randomTree = new DecisionTree(6, 3);
                randomTree.replace(randomTree.getRoot(), r.nextInt(4), r.nextDouble() * 10.0);
                train(lines, randomTree);

                int iterations = r.nextInt(50);
                System.out.printf("Performing %d training iterations on random tree%n%n", iterations);

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

                System.out.println("Printing a random decision tree");
                randomTree.print();
            }
        } catch (IOException e) {
            System.out.println("Error reading file!");
        } catch (NodeReplaceException e) {
            System.out.println("Attempted to replace a node which was not external!");
        }
    }

    private static int getDecisionClass(String clazz) {
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

    private static void train(List<String> lines, DecisionTree dt) {
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
