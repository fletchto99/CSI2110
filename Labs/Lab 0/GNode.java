/**
 * Node of a singly linked list, which stores references to its
 * element and to the next node in the list.
 *
 * @author Natasha Gelfand
 * @author Roberto Tamassia
 * @author Michael Goodrich
 */
//begin#fragment Node
public class GNode<E> {
    // Instance variables:
    private E element;
    private GNode<E> next;

    /**
     * Creates a node with null references to its element and next node.
     */
    public GNode() {
        this(null, null);
    }

    /**
     * Creates a node with the given element and next node.
     */
    public GNode(E e, GNode<E> n) {
        element = e;
        next = n;
    }

    // Accessor methods:
    public E getElement() {
        return element;
    }

    public GNode<E> getNext() {
        return next;
    }

    // Modifier methods:
    public void setElement(E newElem) {
        element = newElem;
    }

    public void setNext(GNode<E> newNext) {
        next = newNext;
    }
}
//end#fragment Node
