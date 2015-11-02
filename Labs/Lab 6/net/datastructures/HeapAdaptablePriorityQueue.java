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
 * An implementation of an adaptable priority queue using an array-based heap.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class HeapAdaptablePriorityQueue<K,V> extends HeapPriorityQueue<K,V>
                                    implements AdaptablePriorityQueue<K,V> {

  //---------------- nested AdaptablePQEntry class ----------------
  /** Extension of the PQEntry to include location information. */
  protected static class AdaptablePQEntry<K,V> extends PQEntry<K,V> {
    private int index;          // entry's current index within the heap
    public AdaptablePQEntry(K key, V value, int j) {
      super(key, value);        // this sets the key and value
      index = j;                // this sets the new field
    }
    public int getIndex() { return index; }
    public void setIndex(int j) { index = j; }
  } //----------- end of nested AdaptablePQEntry class -----------

  /** Creates an empty adaptable priority queue using natural ordering of keys. */
  public HeapAdaptablePriorityQueue() { super(); }

  /**
   * Creates an empty adaptable priority queue using the given comparator to order keys.
   * @param comp comparator defining the order of keys in the priority queue
   */
  public HeapAdaptablePriorityQueue(Comparator<K> comp) { super(comp);}

  // protected utilites
  /**
   * Validates an entry to ensure it is location-aware.
   * @param entry an entry instance
   * @return the entry cast as an AdaptablePQEntry instance
   * @throws IllegalArgumentException if the given entry was not valid
   */
  protected AdaptablePQEntry<K,V> validate(Entry<K,V> entry)
                                  throws IllegalArgumentException {
    if (!(entry instanceof AdaptablePQEntry))
      throw new IllegalArgumentException("Invalid entry");
    AdaptablePQEntry<K,V> locator = (AdaptablePQEntry<K,V>) entry;   // safe
    int j = locator.getIndex();
    if (j >= heap.size() || heap.get(j) != locator)
      throw new IllegalArgumentException("Invalid entry");
    return locator;
  }

  /** Exchanges the entries at indices i and j of the array list. */
  @Override
  protected void swap(int i, int j) {
    super.swap(i,j);                                        // perform the swap
    ((AdaptablePQEntry<K,V>) heap.get(i)).setIndex(i);      // reset entry's index
    ((AdaptablePQEntry<K,V>) heap.get(j)).setIndex(j);      // reset entry's index
  }

  /** Restores the heap property by moving the entry at index j upward/downward.*/
  protected void bubble(int j) {
    if (j > 0 && compare(heap.get(j), heap.get(parent(j))) < 0)
      upheap(j);
    else
      downheap(j);                   // although it might not need to move
  }
  // public methods

  /**
   * Inserts a key-value pair and return the entry created.
   * @param key     the key of the new entry
   * @param value   the associated value of the new entry
   * @return the entry storing the new key-value pair
   * @throws IllegalArgumentException if the key is unacceptable for this queue
   */
  @Override
  public Entry<K,V> insert(K key, V value) throws IllegalArgumentException {
    checkKey(key);                   // might throw an exception
    Entry<K,V> newest = new AdaptablePQEntry<>(key, value, heap.size());
    heap.add(newest);                // add to the end of the list
    upheap(heap.size() - 1);         // upheap newly added entry
    return newest;
  }

  /**
   * Removes the given entry from the priority queue.
   *
   * @param entry  an entry of this priority queue
   * @throws IllegalArgumentException if e is not a valid entry for the priority queue.
   */
  @Override
  public void remove(Entry<K,V> entry) throws IllegalArgumentException {
    AdaptablePQEntry<K,V> locator = validate(entry);
    int j = locator.getIndex();
    if (j == heap.size() - 1)        // entry is at last position
      heap.remove(heap.size() - 1);  // so just remove it
    else {
      swap(j, heap.size() - 1);      // swap entry to last position
      heap.remove(heap.size() - 1);  // then remove it
      bubble(j);                     // and fix entry displaced by the swap
    }
  }

  /**
   * Replaces the key of an entry.
   *
   * @param entry  an entry of this priority queue
   * @param key    the new key
   * @throws IllegalArgumentException if e is not a valid entry for the priority queue.
   */
  @Override
  public void replaceKey(Entry<K,V> entry, K key)
                         throws IllegalArgumentException {
    AdaptablePQEntry<K,V> locator = validate(entry);
    checkKey(key);                   // might throw an exception
    locator.setKey(key);             // method inherited from PQEntry
    bubble(locator.getIndex());      // with new key, may need to move entry
  }

  /**
   * Replaces the value of an entry.
   *
   * @param entry  an entry of this priority queue
   * @param value  the new value
   * @throws IllegalArgumentException if e is not a valid entry for the priority queue.
   */
  @Override
  public void replaceValue(Entry<K,V> entry, V value)
                           throws IllegalArgumentException {
    AdaptablePQEntry<K,V> locator = validate(entry);
    locator.setValue(value);         // method inherited from PQEntry
  }
}
