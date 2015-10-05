import java.util.NoSuchElementException;
import java.util.LinkedList;

/**
 *
 */
public class NodeList<E> {

    protected LinkedList<E> linkedList = new LinkedList<>();

    public int size() {
        return linkedList.size();
    }

    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    public boolean isFirst(E element) {
        return element.equals(linkedList.getFirst());
    }

    public boolean isLast(E element) {
        return element.equals(linkedList.getLast());
    }

    public E first() throws NoSuchElementException {
        return linkedList.element();
    }

    public E last() throws NoSuchElementException {
        return linkedList.getLast();
    }

    E prev(E element) throws NoSuchElementException {
        int index = linkedList.indexOf(element);
        if (element == null || index < 1) {
            throw new NullPointerException();
        }
        return linkedList.get(index-1);
    }

    E next(E element) throws NoSuchElementException {
        int index = linkedList.indexOf(element);
        if (element == null || index < 0 || index > linkedList.size() - 2) {
            throw new NullPointerException();
        }
        return linkedList.get(index+1);
    }

    public void swapElements(E element1, E element2) throws NoSuchElementException {
        if (!linkedList.contains(element1) || !linkedList.contains(element2)) {
            throw new NoSuchElementException();
        }
        linkedList.set(linkedList.indexOf(element1), element2);
        linkedList.set(linkedList.indexOf(element2), element1);
    }


    public void set(E currElement, E repElement) throws NoSuchElementException {
        if (!linkedList.contains(currElement)) {
            throw new NoSuchElementException();
        }
        linkedList.set(linkedList.indexOf(currElement), repElement);
    }

    public void addFirst(E element) {
        linkedList.addFirst(element);
    }

    public void addLast(E element) {
        linkedList.addLast(element);
    }

    public void addBefore(E currElement, E addElement) throws NoSuchElementException {
        if (!linkedList.contains(currElement)) {
            throw new NoSuchElementException();
        }
        linkedList.add(linkedList.indexOf(currElement), addElement);
    }

    public void addAfter(E currElement, E addElement) throws NoSuchElementException {
        if (!linkedList.contains(currElement)) {
            throw new NoSuchElementException();
        }
        if (linkedList.indexOf(currElement) == linkedList.size() - 1) {
            linkedList.add(addElement);
        } else {
            linkedList.add(linkedList.indexOf(currElement) + 1, addElement);
        }

    }

    public E remove(E element) throws NoSuchElementException {
        if (!linkedList.contains(element)) {
            throw new NoSuchElementException();
        }
        linkedList.remove(element);
        return element;
    }
}
