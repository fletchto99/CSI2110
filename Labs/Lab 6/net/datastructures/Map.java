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

/**
 * An interface for an associative map which binds a key uniquely to a value.
 * This interface is a simplified version of java.util.Map.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public interface Map<K,V> {

  /**
   * Returns the number of entries in the map.
   * @return number of entries in the map
   */
  int size();

  /**
   * Tests whether the map is empty.
   * @return true if the map is empty, false otherwise
   */
  boolean isEmpty();

  /**
   * Returns the value associated with the specified key, or null if no such entry exists.
   * @param key  the key whose associated value is to be returned
   * @return the associated value, or null if no such entry exists
   */
  V get(K key);

  /**
   * Associates the given value with the given key. If an entry with
   * the key was already in the map, this replaced the previous value
   * with the new one and returns the old value. Otherwise, a new
   * entry is added and null is returned.
   * @param key    key with which the specified value is to be associated
   * @param value  value to be associated with the specified key
   * @return the previous value associated with the key (or null, if no such entry)
   */
  V put(K key, V value);

  /**
   * Removes the entry with the specified key, if present, and returns
   * its associated value. Otherwise does nothing and returns null.
   * @param key  the key whose entry is to be removed from the map
   * @return the previous value associated with the removed key, or null if no such entry exists
   */
  V remove(K key);

  /**
   * Returns an iterable collection of the keys contained in the map.
   *
   * @return iterable collection of the map's keys
   */
  Iterable<K> keySet();

  /**
   * Returns an iterable collection of the values contained in the map.
   * Note that the same value will be given multiple times in the result
   * if it is associated with multiple keys.
   *
   * @return iterable collection of the map's values
   */
  Iterable<V> values();

  /**
   * Returns an iterable collection of all key-value entries of the map.
   *
   * @return iterable collection of the map's entries
   */
  Iterable<Entry<K,V>> entrySet();
}
