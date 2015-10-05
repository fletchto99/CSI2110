import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Question4 {

    public static void main(String[] args) {
        Stack<String> s = new Stack<>();
        s.push("a");
        s.push("b");
        s.push("c");
        s.push("d");
        s.push("e");
        System.out.println(s);
        swap(s, 1, 3);
        System.out.println(s);
    }

    public static void swap(Stack<String> stack, int i, int j) {
        Queue<String> q = new LinkedList<>();
        Stack<String> s = new Stack<>();
        int size = stack.size();
        for (int n = 0; n < size; n++) {
            if (n==i || n == j) {
                q.add(stack.pop());
            } else {
                s.push(stack.pop());
            }
        }
        for (int n = 0; n < size; n++) {
            if (n==i || n == j) {
                stack.push(q.poll());
            } else {
                stack.push(s.pop());
            }
        }
    }

}
