// ==========================================================================
// $Id: QuickSortInPlace.java,v 1.1 2006/11/05 03:27:51 jlang Exp $
// CSI2110 Lab code, in-place quicksort
// ==========================================================================
// (C)opyright:
//
//   SITE, University of Ottawa
//   800 King Edward Ave.
//   Ottawa, On., K1N 6N5
//   Canada. 
//   http://www.site.uottawa.ca
// 
// Creator: unknown (Lab source without reference), adapted by J.Lang
// Email:   jlang@site.uottawa.ca
// ==========================================================================
// $Log: QuickSortInPlace.java,v $
// Revision 1.1  2006/11/05 03:27:51  jlang
// Added lab8 on sorting.
//
//
// ==========================================================================

import java.util.Random;

/**
 * Implements in-place array-based Quicksort.
 */
public class QuickSortInPlace<T extends Comparable> {
  protected T[] d_seq;

  /**
   * Construct a QuickSort object
   * _seq will be sorted on construction
   */
  public QuickSortInPlace(T[] _seq) {
    d_seq = _seq;
    // Note: Make sure right = size-1 for initial call! 
    quickSort(0, d_seq.length-1);
  }

  protected void quickSort( int _left, int _right )
  {
    // 
    // Instead of checking to end recursion at the start, we check to
    // see if recursion is needed when it arises.  It saves one level
    // of recursive calls, at the expense of a couple of comparisions.

    // Pivot selection is always d_seq[right]. It ought to be randomized.
    Random r = new Random();
    int pivotLoc = _left + r.nextInt((_right - _left) + 1);
    T pivot = d_seq[pivotLoc];

    int pIndex = partition( pivot, _left, _right );
    // The pivot is now at pIndex, so recur on both sides of it.
    // This is the point where we also check to stop recursion.
    if (_left < pIndex - 1) {
      quickSort( _left, pIndex - 1);
    }
    if (pIndex + 1 < _right) {
      quickSort( pIndex + 1, _right);
    }
  }

  /**
   * Partition the array in place After the call all elements smaller
   * than the pivot are on the left of the returned index; all
   * elements larger than the pivot are on the left of the returned
   * index.
   */
  protected int partition(T _pivot, int _left, int _right) {
    T temp;
    int leftIndex = _left; // will scan rightward
    int rightIndex = _right - 1; // will scan leftward
    while (leftIndex <= rightIndex) {
      // Scan rightward to find an element larger than the pivot.
      while ((leftIndex <= rightIndex) && 
             (d_seq[leftIndex].compareTo(_pivot)<0)) {
        leftIndex++;
      }
      // Scan leftward to find an element smaller than the pivot.
      while ((rightIndex >= leftIndex) && 
             (d_seq[rightIndex].compareTo(_pivot)>=0)) {
        rightIndex--;
      }
      if (leftIndex < rightIndex) {
        // Both elements were found.
        temp = d_seq[leftIndex];
        d_seq[leftIndex] = d_seq[rightIndex];
        d_seq[rightIndex] = temp;
      }
    } // The loop continues until the indices cross.
    // Place the pivot by swapping it with the element at leftIndex.
    temp = d_seq[leftIndex];
    d_seq[leftIndex] = d_seq[_right];
    d_seq[_right] = temp;
    return leftIndex;
  }
}
