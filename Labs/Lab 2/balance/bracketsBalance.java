package balance;/*  CSI2114 Lab 3 - lab3.java
 *  
 *  Class to check balanced brackets in math expressions  
 *
 *  Usage: java bracketsBalance <exp>
 *  
 *  by Jeff Souza
 *
 */

class bracketsBalance {

    private boolean bBalance(String exp) {
        Stack sq = new ArrayStack();
        Stack rd = new ArrayStack();
        Stack o = new ArrayStack();
        for (char c : exp.toCharArray()) {
            if (c == '[') {
                sq.push('[');
            } else if (c == ']') {
                sq.pop();
            } else if (c == '(') {
                rd.push('(');
            } else if (c == ')') {
                rd.pop();
            } else if (c == '{') {
                o.push('(');
            } else if (c == '}') {
                o.pop();
            }
        }
        return rd.isEmpty() && sq.isEmpty() && o.isEmpty();

    }

    public static void main(String[] args) {

        bracketsBalance b = new bracketsBalance();
        boolean result = b.bBalance("{{(})}");

        if (result) System.out.println("The expression is balanced.");
        else System.out.println("The expression is NOT balanced.");
    }
}