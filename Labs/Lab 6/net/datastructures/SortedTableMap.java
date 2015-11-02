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
import java.util.Comparator;

/**
 * An implementation of a map using a sorted table. All accessors run
 * in O(log n) worst-case time, other than subMap, which runs in O(s + log n)
 * where s is the size of the resulting submap, and the complete iterations
 * that run in O(n) time.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class SortedTableMap<K,V> extends AbstractSortedMap<K,V> {

  private ArrayList<MapEntry<K,V>> table = new ArrayList<>();

  /** Constructs an empty map using the natural ordering of keys. */
  public SortedTableMap() { super(); }

  /**
   * Constructs an empty map using the given comparator to order keys.
   * @param comp comparator defining the order of keys in the map
   */
  public SortedTableMap(Comparator<K> comp) { super(comp); }

  // variant of binary search that returns relevant index
  /**
   * Returns the smallest index for range table[low..high] inclusive
   * storing an entry with a key greater than or equal to the given k.
   * If no such element exists, returns index high+1 by convention.
   * @param key the query key
   * @param low the lowest index of the relevant table range
   * @param high the highest index of the relevant table range
   * @return lowest j such that table[j] has key greater than or equal to given key
   * (or index high+1 if no such entry exists)
   */
  private int findIndex(K key, int low, int high) {
    if (high < low) return high + 1;         // no entry qualifies
    int mid = (low + high) / 2;
    int comp = compare(key, table.get(mid));
    if (comp == 0)
      return mid;                            // found exact match
    else if (comp < 0)
      return findIndex(key, low, mid - 1);   // answer is left of mid (or possibly mid)
    else
      return findIndex(key, mid + 1, high);  // answer is right of mid
  }

  /** Version of findIndex that searches the entire table */
  private int findIndex(K key) { return findIndex(key, 0, table.size() - 1); }

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
  public V get(K key) throws IllegalArgumentException {
    checkKey(key);
    int j = findIndex(key);
    if (j == size() || compare(key, table.get(j)) != 0) return null;   // no match
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
  public V put(K key, V value) throws IllegalArgumentException {
    checkKey(key);
    int j = findIndex(key);
    if (j < size() && compare(key, table.get(j)) == 0)                 // match exists
      return table.get(j).setValue(value);
    table.add(j, new MapEntry<K,V>(key,value));                        // otherwise new
    return null;
  }

  /**
   * Removes the entry with the specified key, if present, and returns
   * its associated value. Otherwise does nothing and returns null.
   * @param key  the key whose entry is to be removed from the map
   * @return the previous value associated with the removed key, or null if no such entry exists
   */
  @Override
  public V remove(K key) throws IllegalArgumentException {
    checkKey(key);
    int j = findIndex(key);
    if (j == size() || compare(key, table.get(j)) != 0) return null;  // no match
    return table.remove(j).getValue();
  }

  /** Utility returns the entry at index j, or else null if j is out of bounds. */
  private Entry<K,V> safeEntry(int j) {
    if (j < 0 || j >= table.size()) return null;
    return table.get(j);
  }

  /**
   * Returns the entry having the least key (or null if map is empty).
   * @return entry with least key (or null if map is empty)
   */
  @Override
  public Entry<K,V> firstEntry() { return safeEntry(0); }

  /**
   * Returns the entry having the greatest key (or null if map is empty).
   * @return entry with greatest key (or null if map is empty)
   */
  @Override
  public Entry<K,V> lastEntry() { return safeEntry(table.size()-1); }

  /**
   * Returns the entry with least key greater than or equal to given key
   * (or null if no such key exists).
   * @return entry with least key greater than or equal to given (or null if no such entry)
   * @throws IllegalArgumentException if the key is not compatible with the map
   */
  @Override
  public Entry<K,V> ceilingEntry(K key) throws IllegalArgumentException {
    return safeEntry(findIndex(key));
  }

  /**
   * Returns the entry with greatest key less than or equal to given key
   * (or null if no such key exists).
   * @return entry with greatest key less than or equal to given (or null if no such entry)
   * @throws IllegalArgumentException if the key is not compatible with the map
   */
  @Override
  public Entry<K,V> floorEntry(K key) throws IllegalArgumentException {
    int j = findIndex(key);
    if (j == size() || ! key.equals(table.get(j).getKey()))
      j--;    // look one earlier (unless we had found a perfect match)
    return safeEntry(j);
  }

  /**
   * Returns the entry with greatest key strictly less than given key
   * (or null if no such key exists).
   * @return entry with greatest key strictly less than given (or null if no such entry)
   * @throws IllegalArgumentException if the key is not compatible with the map
   */
  @Override
  public Entry<K,V> lowerEntry(K key) throws IllegalArgumentException {
    return safeEntry(findIndex(key) - 1);   // go strictly before the ceiling entry
  }

  /**
   * Returns the entry with least key strictly greater than given key
   * (or null if no such key exists).
   * @return entry with least key strictly greater than given (or null if no such entry)
   * @throws IllegalArgumentException if the key is not compatible with the map
   */
  @Override
  public Entry<K,V> higherEntry(K key) throws IllegalArgumentException {
    int j = findIndex(key);
    if (j < size() && key.equals(table.get(j).getKey()))
      j++;    // go past exact match
    return safeEntry(j);
  }

  // support for snapshot iterators for entrySet() and subMap() follow
  private Iterable<Entry<K,V>> snapshot(int startIndex, K stop) {
    ArrayList<Entry<K,V>> buffer = new ArrayList<>();
    int j = startIndex;
    while (j < table.size() && (stop == null || compare(stop, table.get(j)) > 0))
      buffer.add(table.get(j++));
    return buffer;
  }

  /**
   * Returns an iterable collection of all key-value entries of the map.
   *
   * @return iterable collection of the map's entries
   */
  @Override
  public Iterable<Entry<K,V>> entrySet() { return snapshot(0, null); }

  /**
   * Returns an iterable containing all keys in the range from
   * <code>fromKey</code> inclusive to <code>toKey</code> exclusive.
   * @return iterable with keys in desired range
   * @throws IllegalArgumentException if <code>fromKey</code> or <code>toKey</code> is not compatible with the map
   */
  @Override
  public Iterable<Entry<K,V>> subMap(K fromKey, K toKey) throws IllegalArgumentException {
    return snapshot(findIndex(fromKey), toKey);
  }
}
