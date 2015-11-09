// ==========================================================================
// $Id: BubbleSort.java,v 1.1 2006/11/05 03:27:51 jlang Exp $
// CSI2110 Lab code; basic bubble sort 
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
// $Log: BubbleSort.java,v $
// Revision 1.1  2006/11/05 03:27:51  jlang
// Added lab8 on sorting.
//
//
// ==========================================================================
/**
 * Implements bubble sort.
 */
public class BubbleSort<T extends Comparable> {

  public BubbleSort(T[] _seq ) {
    for (int i = 0; i < _seq.length; i++) {
        for (int j = 1; j < _seq.length - i; j++) {
          // if (_seq[j - 1] > _seq[j]) {
          if (_seq[j - 1].compareTo(_seq[j]) > 0 ) {
            T temp = _seq[j];
            _seq[j] = _seq[j - 1];
            _seq[j - 1] = temp;
          }
        }
    }
  }
}
