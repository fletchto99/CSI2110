public class ArrayStack implements Stack{

    public static final int CAPACITY = 1000; //# default capacity of the stack
    private int capacity;                    // maximum capacity of the stack.
    private Object S[];                       // S holds the elements of the stack
    private int top = -1;                    // the top element of the stack.
 
    public ArrayStack() {       //# Initialize the stack with default capacity
        this(CAPACITY);
    }

    public ArrayStack(int cap) {  //# Initialize the stack with given capacity
        capacity = cap;
        S = new Object[capacity];
    }

    public int size() {          //# Return the current stack size
        return (top + 1);
    }

    public boolean isEmpty() {   //# Return true iff the stack is empty
        return (top < 0);
    }

    public void push(Object obj) throws StackFullException {  //# Push a new object on the stack
        if (size() == capacity)
            throw new StackFullException("Stack overflow.");
        S[++top] = obj;
    }

    public Object top() throws StackEmptyException {  //# Return the top stack element
        if (isEmpty())
            throw new StackEmptyException("Stack is empty.");
        return S[top];
    }

    public Object pop() throws StackEmptyException { //# Pop off the stack element
        Object elem;
        if (isEmpty())
            throw new StackEmptyException("Stack is Empty.");
        elem = S[top];
        S[top--] = null;               //# Dereference S[top] and decrement top
        return elem;
    }
}