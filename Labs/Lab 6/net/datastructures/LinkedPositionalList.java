/*
 * Copyright 2014, Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 *
 * Developed for use with the book:
 *
 *    Data Structures and Algorithms in Java, Sixth Edition
 *    Michael T. Goodrich, Roberto Tamassia, and Michael H. Goldwasser
 *    John Wiley & Sons, 2014
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of a positional list stored as a doubly linked list.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class LinkedPositionalList<E> implements PositionalList<E> {
  //---------------- nested Node class ----------------
  /**
   * Node of a doubly linked list, which stores a reference to its
   * element and to both the previous and next node in the list.
   */
  private static class Node<E> implements Position<E> {

    /** The element stored at this node */
    private E element;               // reference to the element stored at this node

    /** A reference to the preceding node in the list */
    private Node<E> prev;            // reference to the previous node in the list

    /** A reference to the subsequent node in the list */
    private Node<E> next;            // reference to the subsequent node in the list

    /**
     * Creates a node with the given element and next node.
     *
     * @param e  the element to be stored
     * @param p  reference to a node that should precede the new node
     * @param n  reference to a node that should follow the new node
     */
    public Node(E e, Node<E> p, Node<E> n) {
      element = e;
      prev = p;
      next = n;
    }

    // public accessor methods
    /**
     * Returns the element stored at the node.
     * @return the stored element
     * @throws IllegalStateException if node not currently linked to others
     */
    public E getElement() throws IllegalStateException {
      if (next == null)                         // convention for defunct node
        throw new IllegalStateException("Position no longer valid");
      return element;
    }

    /**
     * Returns the node that precedes this one (or null if no such node).
     * @return the preceding node
     */
    public Node<E> getPrev() {
      return prev;
    }

    /**
     * Returns the node that follows this one (or null if no such node).
     * @return the following node
     */
    public Node<E> getNext() {
      return next;
    }

    // Update methods
    /**
     * Sets the node's element to the given element e.
     * @param e    the node's new element
     */
    public void setElement(E e) {
      element = e;
    }

    /**
     * Sets the node's previous reference to point to Node n.
     * @param p    the node that should precede this one
     */
    public void setPrev(Node<E> p) {
      prev = p;
    }

    /**
     * Sets the node's next reference to point to Node n.
     * @param n    the node that should follow this one
     */
    public void setNext(Node<E> n) {
      next = n;
    }
  } //----------- end of nested Node class -----------

  // instance variables of the LinkedPositionalList
  /** Sentinel node at the beginning of the list */
  private Node<E> header;                       // header sentinel

  /** Sentinel node at the end of the list */
  private Node<E> trailer;                      // trailer sentinel

  /** Number of elements in the list (not including sentinels) */
  private int size = 0;                         // number of elements in the list

  /** Constructs a new empty list. */
  public LinkedPositionalList() {
    header = new Node<>(null, null, null);      // create header
    trailer = new Node<>(null, header, null);   // trailer is preceded by header
    header.setNext(trailer);                    // header is followed by trailer
  }

  // private utilities
  /**
   * Verifies that a Position belongs to the appropriate class, and is
   * not one that has been previously removed. Note that our current
   * implementation does not actually verify that the position belongs
   * to this particular list instance.
   *
   * @param p   a Position (that should belong to this list)
   * @return    the underlying Node instance at that position
   * @throws IllegalArgumentException if an invalid position is detected
   */
  private Node<E> validate(Position<E> p) throws IllegalArgumentException {
    if (!(p instanceof Node)) throw new IllegalArgumentException("Invalid p");
    Node<E> node = (Node<E>) p;     // safe cast
    if (node.getNext() == null)     // convention for defunct node
      throw new IllegalArgumentException("p is no longer in the list");
    return node;
  }

  /**
   * Returns the given node as a Position, unless it is a sentinel, in which case
   * null is returned (so as not to expose the sentinels to the user).
   */
  private Position<E> position(Node<E> node) {
    if (node == header || node == trailer)
      return null;   // do not expose user to the sentinels
    return node;
  }

  // public accessor methods
  /**
   * Returns the number of elements in the list.
   * @return number of elements in the list
   */
  @Override
  public int size() { return size; }

  /**
   * Tests whether the list is empty.
   * @return true if the list is empty, false otherwise
   */
  @Override
  public boolean isEmpty() { return size == 0; }

  /**
   * Returns the first Position in the list.
   *
   * @return the first Position in the list (or null, if empty)
   */
  @Override
  public Position<E> first() {
    return position(header.getNext());
  }

  /**
   * Returns the last Position in the list.
   *
   * @return the last Position in the list (or null, if empty)
   */
  @Override
  public Position<E> last() {
    return position(trailer.getPrev());
  }

  /**
   * Returns the Position immediately before Position p.
   * @param p   a Position of the list
   * @return the Position of the preceding element (or null, if p is first)
   * @throws IllegalArgumentException if p is not a valid position for this list
   */
  @Override
  public Position<E> before(Position<E> p) throws IllegalArgumentException {
    Node<E> node = validate(p);
    return position(node.getPrev());
  }

  /**
   * Returns the Position immediately after Position p.
   * @param p   a Position of the list
   * @return the Position of the following element (or null, if p is last)
   * @throws IllegalArgumentException if p is not a valid position for this list
   */
  @Override
  public Position<E> after(Position<E> p) throws IllegalArgumentException {
    Node<E> node = validate(p);
    return position(node.getNext());
  }

  // private utilities
  /**
   * Adds an element to the linked list between the given nodes.
   * The given predecessor and successor should be neighboring each
   * other prior to the call.
   *
   * @param pred     node just before the location where the new element is inserted
   * @param succ     node just after the location where the new element is inserted
   * @return the new element's node
   */
  private Position<E> addBetween(E e, Node<E> pred, Node<E> succ) {
    Node<E> newest = new Node<>(e, pred, succ);  // create and link a new node
    pred.setNext(newest);
    succ.setPrev(newest);
    size++;
    return newest;
  }

  // public update methods
  /**
   * Inserts an element at the front of the list.
   *
   * @param e the new element
   * @return the Position representing the location of the new element
   */
  @Override
  public Position<E> addFirst(E e) {
    return addBetween(e, header, header.getNext());       // just after the header
  }

  /**
   * Inserts an element at the back of the list.
   *
   * @param e the new element
   * @return the Position representing the location of the new element
   */
  @Override
  public Position<E> addLast(E e) {
    return addBetween(e, trailer.getPrev(), trailer);     // just before the trailer
  }

  /**
   * Inserts an element immediately before the given Position.
   *
   * @param p the Position before which the insertion takes place
   * @param e the new element
   * @return the Position representing the location of the new element
   * @throws IllegalArgumentException if p is not a valid position for this list
   */
  @Override
  public Position<E> addBefore(Position<E> p, E e)
                                throws IllegalArgumentException {
    Node<E> node = validate(p);
    return addBetween(e, node.getPrev(), node);
  }

  /**
   * Inserts an element immediately after the given Position.
   *
   * @param p the Position after which the insertion takes place
   * @param e the new element
   * @return the Position representing the location of the new element
   * @throws IllegalArgumentException if p is not a valid position for this list
   */
  @Override
  public Position<E> addAfter(Position<E> p, E e)
                                throws IllegalArgumentException {
    Node<E> node = validate(p);
    return addBetween(e, node, node.getNext());
  }

  /**
   * Replaces the element stored at the given Position and returns the replaced element.
   *
   * @param p the Position of the element to be replaced
   * @param e the new element
   * @return the replaced element
   * @throws IllegalArgumentException if p is not a valid position for this list
   */
  @Override
  public E set(Position<E> p, E e) throws IllegalArgumentException {
    Node<E> node = validate(p);
    E answer = node.getElement();
    node.setElement(e);
    return answer;
  }

  /**
   * Removes the element stored at the given Position and returns it.
   * The given position is invalidated as a result.
   *
   * @param p the Position of the element to be removed
   * @return the removed element
   * @throws IllegalArgumentException if p is not a valid position for this list
   */
  @Override
  public E remove(Position<E> p) throws IllegalArgumentException {
    Node<E> node = validate(p);
    Node<E> predecessor = node.getPrev();
    Node<E> successor = node.getNext();
    predecessor.setNext(successor);
    successor.setPrev(predecessor);
    size--;
    E answer = node.getElement();
    node.setElement(null);           // help with garbage collection
    node.setNext(null);              // and convention for defunct node
    node.setPrev(null);
    return answer;
  }

  // support for iterating either positions and elements
  //---------------- nested PositionIterator class ----------------
  /**
   * A (nonstatic) inner class. Note well that each instance
   * contains an implicit reference to the containing list,
   * allowing us to call the list's methods directly.
   */
  private class PositionIterator implements Iterator<Position<E>> {

    /** A Position of the containing list, initialized to the first position. */
    private Position<E> cursor = first();   // position of the next element to report
    /** A Position of the most recent element reported (if any). */
    private Position<E> recent = null;       // position of last reported element

    /**
     * Tests whether the iterator has a next object.
     * @return true if there are further objects, false otherwise
     */
    public boolean hasNext() { return (cursor != null);  }

    /**
     * Returns the next position in the iterator.
     *
     * @return next position
     * @throws NoSuchElementException if there are no further elements
     */
    public Position<E> next() throws NoSuchElementException {
      if (cursor == null) throw new NoSuchElementException("nothing left");
      recent = cursor;           // element at this position might later be removed
      cursor = after(cursor);
      return recent;
    }

    /**
     * Removes the element returned by most recent call to next.
     * @throws IllegalStateException if next has not yet been called
     * @throws IllegalStateException if remove was already called since recent next
     */
    public void remove() throws IllegalStateException {
      if (recent == null) throw new IllegalStateException("nothing to remove");
      LinkedPositionalList.this.remove(recent);         // remove from outer list
      recent = null;               // do not allow remove again until next is called
    }
  } //------------ end of nested PositionIterator class ------------

  //---------------- nested PositionIterable class ----------------
  private class PositionIterable implements Iterable<Position<E>> {
    public Iterator<Position<E>> iterator() { return new PositionIterator(); }
  } //------------ end of nested PositionIterable class ------------

  /**
   * Returns an iterable representation of the list's positions.
   * @return iterable representation of the list's positions
   */
  @Override
  public Iterable<Position<E>> positions() {
    return new PositionIterable();       // create a new instance of the inner class
  }

  //---------------- nested ElementIterator class ----------------
  /* This class adapts the iteration produced by positions() to return elements. */
  private class ElementIterator implements Iterator<E> {
    Iterator<Position<E>> posIterator = new PositionIterator();
    public boolean hasNext() { return posIterator.hasNext(); }
    public E next() { return posIterator.next().getElement(); } // return element!
    public void remove() { posIterator.remove(); }
  }

  /**
   * Returns an iterator of the elements stored in the list.
   * @return iterator of the list's elements
   */
  @Override
  public Iterator<E> iterator() { return new ElementIterator(); }

  // Debugging code
  /**
   * Produces a string representation of the contents of the list.
   * This exists for debugging purposes only.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("(");
    Node<E> walk = header.getNext();
    while (walk != trailer) {
      sb.append(walk.getElement());
      walk = walk.getNext();
      if (walk != trailer)
        sb.append(", ");
    }
    sb.append(")");
    return sb.toString();
  }
}
