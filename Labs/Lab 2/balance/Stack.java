public interface Stack {

    // Return the number of elements.
    public abstract int size(); 

    // Tests if the stack is empty.
    public abstract boolean isEmpty(); 

    // Inspect the top element, without removing it or otherwise modifying the stack.
    public abstract Object top() throws StackEmptyException; 

    // Insert an element at the top.
    public abstract void push(Object element) throws StackFullException; 

    // Remove the top element.
    public abstract Object pop() throws StackEmptyException;

}