// ==========================================================================
// $Id: addTemplate.cpp,v 1.1 2005/11/02 23:13:32 jlang Exp $
// CSI2110 Lab code testing list interfaces 
// ==========================================================================
// (C)opyright:
//
//   Jochen Lang
//   SITE, University of Ottawa
//   800 King Edward Ave.
//   Ottawa, On., K1N 6N5
//   Canada. 
//   http://www.site.uottawa.ca
// 
// Creator: jlang (Jochen Lang)
// Email:   jlang@site.uottawa.ca
// ==========================================================================
// $Log: addTemplate.cpp,v $
//
// ==========================================================================

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.LinkedList;


public class TestListImplementation {

    class MilliNanoTimer {
        long startTimeMilli;
        long startTimeNano;

        void start() {
            startTimeMilli = System.currentTimeMillis();
            startTimeNano = System.nanoTime();
        }

        String stop() {
            long diffMilli = System.currentTimeMillis() - startTimeMilli;
            long diffNano = System.nanoTime() - startTimeNano;
            diffNano -= 1000 * (diffNano / 1000);
            if (diffNano < 0) {
                return String.format("%6d.%03d", diffMilli - 1, 1000 + diffNano);
            } else {
                return String.format("%6d.%03d", diffMilli, diffNano);
            }
        }
    }

    public void timeOperations(List<Integer> integerList, int numElem) {
        MilliNanoTimer watch = new MilliNanoTimer();

        // number of Elements
        System.out.printf("%10d ", numElem);
        // insert N elements
        watch.start();
        for (int i = 0; i < numElem; i++) {
            integerList.add(0, new Integer(i % 100));
        }
        System.out.print(watch.stop() + " ");
        // iterate over all elements
        int k = 0;
        watch.start();
        for (Integer val : integerList) {
            k += val;
            k -= 50;
        }
        System.out.print(watch.stop() + " ");
        // random access all elements
        k = 0;
        watch.start();
        for (int i = 0; i < numElem; i++) {
            k += integerList.get(i);
            k -= 50;
        }
        System.out.print(watch.stop() + " ");
        // remove N elements at the front
        watch.start();
        for (int i = 0; i < numElem; i++) {
            integerList.remove(0);
        }
        System.out.print(watch.stop() + "\n");
        return;
    }


    public static void main(String[] argv) {
        TestListImplementation test = new TestListImplementation();
        int numElemStart = 512;
        int numSteps = 8;

        if (argv.length > 0) numSteps = Integer.valueOf(argv[0]);


        System.out.println("ArrayList");
        System.out.printf("%10s %10s %10s %10s %10s\n", "N", "insert",
                "iterate", "get", "remove");
        int numElem = numElemStart;
        for (int i = 0; i < numSteps; i++) {
            // create an ArrayList for Integer
            ArrayList<Integer> aList = new ArrayList<Integer>();
            // call our timing method
            test.timeOperations(aList, numElem);
            numElem <<= 1; // multiply by 2
        }
        System.out.println();
        System.out.println("LinkedList");
        System.out.printf("%10s %10s %10s %10s %10s\n", "N", "insert",
                "iterate", "get", "remove");
        numElem = numElemStart;
        for (int i = 0; i < numSteps; i++) {
            // create an LinkedList for Integer
            LinkedList<Integer> lList = new LinkedList<Integer>();
            // call our timing method
            test.timeOperations(lList, numElem);
            numElem <<= 1; // multiply by 2
        }
    }
}
