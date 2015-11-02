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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of a map using an unsorted table.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class UnsortedTableMap<K,V> extends AbstractMap<K,V> {
  /** Underlying storage for the map of entries. */
  private ArrayList<MapEntry<K,V>> table = new ArrayList<>();

  /** Constructs an initially empty map. */
  public UnsortedTableMap() { }

  // private utility
  /** Returns the index of an entry with equal key, or -1 if none found. */
  private int findIndex(K key) {
    int n = table.size();
    for (int j=0; j < n; j++)
      if (table.get(j).getKey().equals(key))
        return j;
    return -1;                                   // special value denotes that key was not found
  }

  // public methods
  /**
   * Returns the number of entries in the map.
   * @return number of entries in the map
   */
  @Override
  public int size() { return table.size(); }

  /**
   * Returns the value associated with the specified key, or null if no such entry exists.
   * @param key  the key whose associated value is to be returned
   * @return the associated value, or null if no such entry exists
   */
  @Override
  public V get(K key) {
    int j = findIndex(key);
    if (j == -1) return null;                         // not found
    return table.get(j).getValue();
  }

  /**
   * Associates the given value with the given key. If an entry with
   * the key was already in the map, this replaced the previous value
   * with the new one and returns the old value. Otherwise, a new
   * entry is added and null is returned.
   * @param key    key with which the specified value is to be associated
   * @param value  value to be associated with the specified key
   * @return the previous value associated with the key (or null, if no such entry)
   */
  @Override
  public V put(K key, V value) {
    int j = findIndex(key);
    if (j == -1) {
      table.add(new MapEntry<>(key, value));          // add new entry
      return null;
    } else                                            // key already exists
      return table.get(j).setValue(value);            // replaced value is returned
  }

  /**
   * Removes the entry with the specified key, if present, and returns its value.
   * Otherwise does nothing and returns null.
   * @param key  the key whose entry is to be removed from the map
   * @return the previous value associated with the removed key, or null if no such entry exists
   */
  @Override
  public V remove(K key) {
    int j = findIndex(key);
    int n = size();
    if (j == -1) return null;                         // not found
    V answer = table.get(j).getValue();
    if (j != n - 1)
      table.set(j, table.get(n-1));                   // relocate last entry to 'hole' created by removal
    table.remove(n-1);                                // remove last entry of table
    return answer;
  }

  //---------------- nested EntryIterator class ----------------
  private class EntryIterator implements Iterator<Entry<K,V>> {
    private int j=0;
    public boolean hasNext() { return j < table.size(); }
    public Entry<K,V> next() {
      if (j == table.size()) throw new NoSuchElementException("No further entries");
      return table.get(j++);
    }
    public void remove() { throw new UnsupportedOperationException("remove not supported"); }
  } //----------- end of nested EntryIterator class -----------

  //---------------- nested EntryIterable class ----------------
  private class EntryIterable implements Iterable<Entry<K,V>> {
    public Iterator<Entry<K,V>> iterator() { return new EntryIterator(); }
  } //----------- end of nested EntryIterable class -----------

  /**
   * Returns an iterable collection of all key-value entries of the map.
   *
   * @return iterable collection of the map's entries
   */
  @Override
  public Iterable<Entry<K,V>> entrySet() { return new EntryIterable(); }
}
