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
 * Interface for a queue: a collection of elements that are inserted
 * and removed according to the first-in first-out principle. Although
 * similar in purpose, this interface differs from java.util.Queue.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public interface Queue<E> {
  /**
   * Returns the number of elements in the queue.
   * @return number of elements in the queue
   */
  int size();

  /**
   * Tests whether the queue is empty.
   * @return true if the queue is empty, false otherwise
   */
  boolean isEmpty();

  /**
   * Inserts an element at the rear of the queue.
   * @param e  the element to be inserted
   */
  void enqueue(E e);

  /**
   * Returns, but does not remove, the first element of the queue.
   * @return the first element of the queue (or null if empty)
   */
  E first();

  /**
   * Removes and returns the first element of the queue.
   * @return element removed (or null if empty)
   */
  E dequeue();
}
