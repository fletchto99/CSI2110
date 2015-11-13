import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DecisionTreeTest {

    public static void main(String[] args) {
        // here is how to read the file containing the pre-classified samples
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("/Users/mattlanglois/Documents/Programming/School/CSI2110/Assignments/Assignment 3/iris.data.txt"), StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("Error reading file");
        }

        if (lines != null) {
            DecisionTree dt = new DecisionTree(6,3);
            dt.replace(dt.getRoot(), 0, 5);
            train(lines, dt);


            System.out.println("Printing first time");
            dt.print();

            System.out.println("Printing second time");
            System.out.println(dt.getSmallestMaxProb());

            System.out.println("printing 3rd time");
            dt.replace(dt.getSmallestMaxProb(), 2, 2.5);
            dt.resetAll();
            train(lines, dt);
            dt.print();

            dt.replace(dt.getSmallestMaxProb(), 1, 3);
            dt.resetAll();
            train(lines, dt);
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
            for(int i = 0; i < split.length; i++) {
                if (i < 4) {
                    path[i] = Double.parseDouble(split[i]);
                } else {
                    dt.train(path, getDecisionClass(split[i]));
                }
            }
        });
    }
}
