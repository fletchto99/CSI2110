

 /**
  * Implementation of the Stack interface using a fixed-length array.
  * An exception is thrown if a push operation is attempted when the
  * size of the stack is equal to the length of the array.
  *
  * @author Natasha Gelfand
  * @author Roberto Tamassia
  * @see FullStackException
  */
public class ArrayStack implements Stack {
 /**
  * Default length of the array used to implement the stack.
  */
  public static final int CAPACITY = 1000;
 /**
  * Length of the array used to implement the stack.
  */
  protected int capacity;
 /**
  * Array used to implement the stack.
  */
  protected Object S[];
 /**
  * Index of the top element of the stack in the array.
  */
  protected int top = -1;
 /**
  * Initialize the stack to use an array of default length CAPACITY.
  */
  public ArrayStack() {
    this(CAPACITY);
  }
 /**
  * Initialize the stack to use an array of given length.
  *
  * @param cap length of the array.
  */
  public ArrayStack(int cap) {
    capacity = cap;
    S = new Object[capacity];
  }


 /**
  * O(1) time.
  */
  public int size() {
    return (top + 1);
  }
 /**
  * O(1) time.
  */
  public boolean isEmpty() {
    return (top < 0);
  }
 /**
  * O(1) time.
  * @exception FullStackException if the array is full.
  */
  public void push(Object obj) throws FullStackException {
    if (size() == capacity)
      throw new FullStackException("Stack overflow.");
    S[++top] = obj;
  }
 /**
  * O(1) time.
  */
  public Object top() throws EmptyStackException {
    if (isEmpty())
      throw new EmptyStackException("Stack is empty.");
    return S[top];
    }
 /**
  * O(1) time.
  */
  public Object pop() throws EmptyStackException {
    Object elem;
    if (isEmpty())
      throw new EmptyStackException("Stack is Empty.");
    elem = S[top];
    S[top--] = null; // dereference S[top] for garbage collection.
    return elem;
  }
}

