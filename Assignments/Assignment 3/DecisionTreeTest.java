import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class DecisionTreeTest {

    private static final boolean MODE_RANDOM = true;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Please specify a path to the file!");
            System.exit(1);
        }

        try {
            final List<String> lines = Files.readAllLines(Paths.get(args[0]), StandardCharsets.UTF_8);

            if (lines != null) {

                if (!MODE_RANDOM) {
                    System.out.println("Creating decision tree");
                    DecisionTree dt = new DecisionTree(6,3);
                    System.out.println();

                    System.out.println("Updating root to use index=0 threshold=5");
                    dt.replace(dt.getRoot(), 0, 5);
                    System.out.println();

                    System.out.println("Training decision tree from file specified");
                    train(lines, dt);
                    System.out.println();

                    System.out.println("Printing the nodes of the decision tree");
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

                    System.out.println("Printing decision tree");
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
                } else {

                    final Random r = new Random();

                    //Generate random threshold and indexes

                    //Build the decision tree
                    final DecisionTree dt = new DecisionTree(6, 3);
                    dt.replace(dt.getRoot(), r.nextInt(5),  r.nextDouble() * 10.0);
                    train(lines, dt);

                    int iterations = r.nextInt(50);

                    IntStream.rangeClosed(0 , iterations).forEach(i->{
                        //replace the smallest node
                        dt.replace(dt.getSmallestMaxProb(), r.nextInt(5), r.nextDouble() * 10.0);

                        //Reset the tree
                        dt.resetAll();

                        //Train the tree with the lines we read
                        train(lines, dt);

                    });

                    dt.print();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading file");
        }
    }

    private static int getDecisionClass(String clazz) {
        switch(clazz) {
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
            for(int i = 0; i < 5; i++) {
                if (i < 4) {
                    path[i] = Double.parseDouble(split[i]);
                } else {
                    dt.train(path, getDecisionClass(split[i]));
                }
            }
        });
    }
}
