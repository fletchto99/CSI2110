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

import java.util.Comparator;

/**
 * An abstract base class to ease the implementation of the PriorityQueue interface.
 *
 * The base class provides four means of support:
 * 1) It defines a PQEntry class as a concrete implementation of the
 *    entry interface
 * 2) It provides an instance variable for a general Comparator and a
 *    protected method, compare(a, b), that makes use of the comparator.
 * 3) It provides a boolean checkKey method that verifies that a given key
 *    is appropriate for use with the comparator
 * 4) It provides an isEmpty implementation based upon the abstract size() method.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public abstract class AbstractPriorityQueue<K,V> implements PriorityQueue<K,V> {
  //---------------- nested PQEntry class ----------------
  /**
   * A concrete implementation of the Entry interface to be used within
   * a PriorityQueue implementation.
   */
  protected static class PQEntry<K,V> implements Entry<K,V> {
    private K k;  // key
    private V v;  // value

    public PQEntry(K key, V value) {
      k = key;
      v = value;
    }

    // methods of the Entry interface
    public K getKey() { return k; }
    public V getValue() { return v; }

    // utilities not exposed as part of the Entry interface
    protected void setKey(K key) { k = key; }
    protected void setValue(V value) { v = value; }
  } //----------- end of nested PQEntry class -----------

  // instance variable for an AbstractPriorityQueue
  /** The comparator defining the ordering of keys in the priority queue. */
  private Comparator<K> comp;

  /**
   * Creates an empty priority queue using the given comparator to order keys.
   * @param c comparator defining the order of keys in the priority queue
   */
  protected AbstractPriorityQueue(Comparator<K> c) { comp = c; }

  /** Creates an empty priority queue based on the natural ordering of its keys. */
  protected AbstractPriorityQueue() { this(new DefaultComparator<K>()); }

  /** Method for comparing two entries according to key */
  protected int compare(Entry<K,V> a, Entry<K,V> b) {
    return comp.compare(a.getKey(), b.getKey());
  }

  /** Determines whether a key is valid. */
  protected boolean checkKey(K key) throws IllegalArgumentException {
    try {
      return (comp.compare(key,key) == 0);  // see if key can be compared to itself
    } catch (ClassCastException e) {
      throw new IllegalArgumentException("Incompatible key");
    }
  }

  /**
   * Tests whether the priority queue is empty.
   * @return true if the priority queue is empty, false otherwise
   */
  @Override
  public boolean isEmpty() { return size() == 0; }
}
