



public class NodeStack implements Stack {
  protected Node top;		// reference to the head node
  protected int size;		// number of elements in the stack
  public NodeStack() {	// constructs an empty stack
    top = null;
    size = 0;
  }
  public int size() {
    return size;
  }
  public boolean isEmpty() {
    if (top == null)
      return true;
    return false;
  }
  public void push(Object elem) {
    Node v = new Node(elem, top);	// create and link-in a new node
    top = v;
    size++;
  }
  public Object top() throws EmptyStackException {
    if (isEmpty())
      throw new EmptyStackException("Stack is empty.");
    return top.getElement();
  }
  public Object pop() throws EmptyStackException {
    if (isEmpty())
      throw new EmptyStackException("Stack is empty.");
    Object temp = top.getElement();
    top = top.getNext();	// link-out the former top node
    size--;
    return temp;
  }
}

