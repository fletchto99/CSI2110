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
 * An implementation of a sorted map using a red-black tree.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class RBTreeMap<K,V> extends TreeMap<K,V> {

  /** Constructs an empty map using the natural ordering of keys. */
  public RBTreeMap() { super(); }

  /**
   * Constructs an empty map using the given comparator to order keys.
   * @param comp comparator defining the order of keys in the map
   */
  public RBTreeMap(Comparator<K> comp) { super(comp); }

  // we use the inherited aux field with convention that 0=black and 1=red
  // (note that new leaves will be black by default, as aux=0)
  private boolean isBlack(Position<Entry<K,V>> p) { return tree.getAux(p)==0;}

  private boolean isRed(Position<Entry<K,V>> p) { return tree.getAux(p)==1; }

  private void makeBlack(Position<Entry<K,V>> p) { tree.setAux(p, 0); }

  private void makeRed(Position<Entry<K,V>> p) { tree.setAux(p, 1); }

  private void setColor(Position<Entry<K,V>> p, boolean toRed) {
    tree.setAux(p, toRed ? 1 : 0);
  }

  /** Overrides the TreeMap rebalancing hook that is called after an insertion. */
  @Override
  protected void rebalanceInsert(Position<Entry<K,V>> p) {
    if (!isRoot(p)) {
      makeRed(p);                   // the new internal node is initially colored red
      resolveRed(p);                // but this may cause a double-red problem
    }
  }

  /** Remedies potential double-red violation above red position p. */
  private void resolveRed(Position<Entry<K,V>> p) {
    Position<Entry<K,V>> parent,uncle,middle,grand; // used in case analysis
    parent = parent(p);
    if (isRed(parent)) {                              // double-red problem exists
      uncle = sibling(parent);
      if (isBlack(uncle)) {                           // Case 1: misshapen 4-node
        middle = restructure(p);                      // do trinode restructuring
        makeBlack(middle);
        makeRed(left(middle));
        makeRed(right(middle));
      } else {                                        // Case 2: overfull 5-node
        makeBlack(parent);                            // perform recoloring
        makeBlack(uncle);
        grand = parent(parent);
        if (!isRoot(grand)) {
          makeRed(grand);                             // grandparent becomes red
          resolveRed(grand);                          // recur at red grandparent
        }
      }
    }
  }

  /** Overrides the TreeMap rebalancing hook that is called after a deletion. */
  @Override
  protected void rebalanceDelete(Position<Entry<K,V>> p) {
    if (isRed(p))                        // deleted parent was black
      makeBlack(p);                      // so this restores black depth
    else if (!isRoot(p)) {
      Position<Entry<K,V>> sib = sibling(p);
      if (isInternal(sib) && (isBlack(sib) || isInternal(left(sib))))
        remedyDoubleBlack(p);            // sib's subtree has nonzero black height
    }
  }

  /** Remedies a presumed double-black violation at the given (nonroot) position. */
  private void remedyDoubleBlack(Position<Entry<K,V>> p) {
    Position<Entry<K,V>> z = parent(p);
    Position<Entry<K,V>> y = sibling(p);
    if (isBlack(y)) {
      if (isRed(left(y)) || isRed(right(y))) { // Case 1: trinode restructuring
        Position<Entry<K,V>> x = (isRed(left(y)) ? left(y) : right(y));
        Position<Entry<K,V>> middle = restructure(x);
        setColor(middle, isRed(z)); // root of restructured subtree gets z's old color
        makeBlack(left(middle));
        makeBlack(right(middle));
      } else {                           // Case 2: recoloring
        makeRed(y);
        if (isRed(z))
          makeBlack(z);                  // problem is resolved
        else if (!isRoot(z))
          remedyDoubleBlack(z);          // propagate the problem
      }
    } else {                             // Case 3: reorient 3-node
      rotate(y);
      makeBlack(y);
      makeRed(z);
      remedyDoubleBlack(p);              // restart the process at p
    }
  }

  /** Ensure that current tree structure is valid RB tree (for debugging only)*/
  private boolean sanityCheck() {
    if (sanityRecurse(root()) == -1) {
      System.out.println("VIOLATION of RB tree properties");
      dump();
      return false;
    } else
      return true;
  }

  /** Returns black depth of subtree, if valid, or -1 if invalid. */
  private int sanityRecurse(Position<Entry<K,V>> p) {
    if (isExternal(p)) {
      if (isRed(p)) return -1;      // invalid; should be black
      else return 0;                // valid, with black-depth 0
    } else {
      if (isRoot(p) && isRed(p)) return -1;    // root must be black
      Position<Entry<K,V>> left = left(p);
      Position<Entry<K,V>> right = right(p);
      if (isRed(p) && (isRed(left) || isRed(right))) return -1;   // cannot have double red

      int a = sanityRecurse(left);
      if (a == -1) return -1;
      int b = sanityRecurse(right);
      if (a != b) return -1;          // two subtrees must have identical black depth

      return a + (isRed(p) ? 0 : 1);   // our black depth might be one greater
    }
  }
}
