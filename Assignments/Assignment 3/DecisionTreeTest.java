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
                System.out.println("Creating decision tree");
                DecisionTree dt = new DecisionTree(6, 3);
                System.out.println();

                System.out.println("Updating root to use index=0 threshold=5");
                dt.replace(dt.getRoot(), 0, 5);
                System.out.println();

                System.out.println("Training decision tree from file specified");
                train(lines, dt);
                System.out.println();

                System.out.println("Printing a 1 node decision tree");
                dt.print();
                System.out.println();

                System.out.println("Replacing node with smallest maximal probability");
                dt.replace(dt.getSmallestMaxProb(), 2, 2.5);

                System.out.println("Resetting decision tree");
                dt.resetAll();
                System.out.println();

                System.out.println("Retraining decision tree");
                train(lines, dt);
                System.out.println();

                System.out.println("Printing a 2 node decision tree");
                dt.print();
                System.out.println();

                System.out.println("Replacing smallest max prob node");
                dt.replace(dt.getSmallestMaxProb(), 1, 3);
                System.out.println();


                System.out.println("Resetting decision tree");
                dt.resetAll();
                System.out.println();

                System.out.println("Retraining decision tree");
                train(lines, dt);
                System.out.println();

                System.out.println("Printing a 3 node decision tree");
                dt.print();
                System.out.println();



                final Random r = new Random();

                //Generate random threshold and indexes

                //Build the decision tree
                System.out.println("Generating a random decision tree");
                DecisionTree randomTree = new DecisionTree(6, 3);
                randomTree.replace(randomTree.getRoot(), r.nextInt(4), r.nextDouble() * 10.0);
                train(lines, randomTree);
                System.out.println();

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
