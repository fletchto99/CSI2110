// ==========================================================================
// $Id: HeapSortInPlace.java,v 1.1 2006/11/05 03:27:51 jlang Exp $
// CSI2110 Lab code; Heap sort in place
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
// $Log: HeapSortInPlace.java,v $
// Revision 1.1  2006/11/05 03:27:51  jlang
// Added lab8 on sorting.
//
//
// ==========================================================================
/**
 * Implements an in-place array-based heap sort.
 */
public class HeapSortInPlace<T extends Comparable> {
  protected T[] d_seq;

  /**
   * Construct a HeapSort function object
   * _seq will be sorted on construction
   */
  public HeapSortInPlace( T[] _seq ) {
    d_seq = _seq;
    int size = d_seq.length;
    // index i is for a heap position; array indices are one less
    for ( int i = size/2; i >= 1; i-- ) {
      // do bottom-up heap construction on 'd_seq'
      heapify(i, size);				
    }
    // iteratively loop, remove max and fill in large elements from
    // the end
    for ( int i = size; i >= 2; i-- ) {
      // move max element to end (where it belongs)
      T temp = d_seq[0];					
      d_seq[0] = d_seq[i-1];
      d_seq[i-1] = temp;
      // rebuild *max* heap: heap has shrunk by 1 element
      heapify(1,i-1);					
    }
  }


  /**
   * Ensure a subset of an array between indices i and j has heap property.
   * Creates a heap with *max* element at i = 1 (seq[0])
   */
  protected void heapify(int i, int j)
  {
    // This method creates/restores the heap property over the range
    // seq[i-1] to seq[j-1].  The heap is a *maximum* heap, where the
    // maximum element is stored at the root, and a parent is greater
    // than its children.  This works better for heap sort to keep the
    // heap at the front of the array for the entire sorting process.
    //
    // There is some messy left shifting here to use the 0 element as
    // the root.  While parameters 'i' and 'j' are in the range 1 <=
    // ... <= size, and these values are used to find parents and
    // children, when a subscript is accessed, 1 is subtracted to be
    // in the range 0 <= ... <= size-1.

    // Essentially, this method implements "downheap" from the text,
    // where the start node is at i, and j is the last element in the
    // heap.

    // Credit: this algorithm adapted from Aho, Hopcroft, Ullman,
    // "Design and Analysis of Computer Algorithms"
	 
    if (i <= j / 2) {
      int currentIndex = i - 1;
      T currentKey = d_seq[currentIndex];
      int leftIndex = 2 * i - 1;
      T leftKey = d_seq[leftIndex];
      if ((i < j / 2) || (j % 2 == 1)) {
        // ensures both children must exist
        int rightIndex = leftIndex + 1;
        T rightKey = d_seq[rightIndex];
        // if ((currentKey < leftKey) || (currentKey < rightKey)) {
        if ((currentKey.compareTo(leftKey) < 0 ) || 
            (currentKey.compareTo(rightKey) < 0 )) {
          // see if a downheap is necessary
          // if (leftKey > rightKey) {
          if (leftKey.compareTo(rightKey) > 0 ) {
            d_seq[currentIndex] = leftKey;	// downheap to left child
            d_seq[leftIndex] = currentKey;
            heapify(2 * i, j);
          } else {
            d_seq[currentIndex] = rightKey;	// downheap to right child
            d_seq[rightIndex] = currentKey;
            heapify(2 * i + 1, j);
          }
        }
      } else {
        // only left child exists
        // if (currentKey < leftKey) {
        if (currentKey.compareTo(leftKey)<0) {
          // see if downheap is necessary
          d_seq[currentIndex] = leftKey;		// downheap to left child
          d_seq[leftIndex] = currentKey;
          heapify(2 * i, j);
        }
      }
    }
  }

}
