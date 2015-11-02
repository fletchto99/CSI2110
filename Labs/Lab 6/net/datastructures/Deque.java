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
 * Interface for a double-ended queue: a collection of elements that can be inserted
 * and removed at both ends; this interface is a simplified version of java.util.Deque.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public interface Deque<E> {

  /**
   * Returns the number of elements in the deque.
   * @return number of elements in the deque
   */
  int size();

  /**
   * Tests whether the deque is empty.
   * @return true if the deque is empty, false otherwise
   */
  boolean isEmpty();

  /**
   * Returns (but does not remove) the first element of the deque.
   * @return first element of the deque (or null if empty)
   */
  E first();

  /**
   * Returns (but does not remove) the last element of the deque.
   * @return last element of the deque (or null if empty)
   */
  E last();

  /**
   * Inserts an element at the front of the deque.
   * @param e   the new element
   */
  void addFirst(E e);

  /**
   * Inserts an element at the back of the deque.
   * @param e   the new element
   */
  void addLast(E e);

  /**
   * Removes and returns the first element of the deque.
   * @return element removed (or null if empty)
   */
  E removeFirst();

  /**
   * Removes and returns the last element of the deque.
   * @return element removed (or null if empty)
   */
  E removeLast();
}
