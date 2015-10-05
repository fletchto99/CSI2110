// ==========================================================================
// $Id: addTemplate.cpp,v 1.1 2005/11/02 23:13:32 jlang Exp $
// CSI2110 Lab code Test driver for node list implementation
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

public class Sentence {

    public static void print(NodeList<String> stringNodeList) {
        String s = stringNodeList.first();
        System.out.print(s);
        System.out.print(' ');
        for (int cnt = 0; cnt < stringNodeList.size() - 1; ++cnt) {
            s = stringNodeList.next(s);
            System.out.print(s);
            System.out.print(' ');
        }
        System.out.println();
    }


    public static void main(String[] argv) {
        NodeList<String> stringNodeList = new NodeList<>();

        argv = "This is a test sentence".split(" ");

        // Add all the words to the NodeList
        for (String anArgv : argv) {
            stringNodeList.addLast(anArgv);
        }
        // Print out the sentence
        Sentence.print(stringNodeList);

        // Swap the first and last element
        String first = stringNodeList.first();
        String last = stringNodeList.last();
        stringNodeList.swapElements(first, last);
        // Print out the sentence
        Sentence.print(stringNodeList);


        // Insert a not before and after the center word
        String center = argv[stringNodeList.size() / 2];
        System.err.println(center);
        stringNodeList.addBefore(center, "not");
        // Print out the sentence
        Sentence.print(stringNodeList);
        stringNodeList.addAfter(center, "not");
        // Print out the sentence
        Sentence.print(stringNodeList);


        // Remove all original elements from the list
        for (String anArgv : argv) {
            stringNodeList.remove(anArgv);
        }
        // Print out the sentence
        Sentence.print(stringNodeList);

    }
}
