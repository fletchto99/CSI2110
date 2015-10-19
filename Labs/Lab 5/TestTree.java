/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;

/**
 * Test class for tree traversal lab
 *
 * @author Lachlan Plant
 */
public class TestTree {


    public static void testPreorderRecursive(LinkedBinarySearchTree<Integer> tree, String expected) {
        System.out.println("Expected Result: " + expected);
        tree.printPreorderRecursive();
        System.out.println();
    }

    public static void testPreorderIterator(LinkedBinarySearchTree<Integer> tree, String expected) {
        System.out.println("Expected Result: " + expected);
        System.out.print("Preorder Printing with Iterator: ");
        Iterator i = tree.preorderIterator();
        while (i.hasNext()) {
            System.out.print(i.next().toString() + ", ");
        }
        System.out.println();
        System.out.print("Printing First 5 Elements: ");
        i = tree.preorderIterator();
        for (int j = 0; j < 5; j++) {
            System.out.print(i.next().toString() + ", ");
        }
        System.out.println();
    }

    public static void testInorderRecursive(LinkedBinarySearchTree<Integer> tree, String expected) {
        System.out.println("Expected Result: " + expected);
        tree.printInorderRecursive();
        System.out.println();
    }

    public static void testInorderIterator(LinkedBinarySearchTree<Integer> tree, String expected) {
        System.out.println("Expected Result: " + expected);
        System.out.print("Inorder Printing with Iterator: ");
        Iterator i = tree.inorderIterator();
        while (i.hasNext()) {
            System.out.print(i.next().toString() + ", ");
        }
        System.out.println();
        System.out.print("Printing First 5 Elements: ");
        i = tree.inorderIterator();
        for (int j = 0; j < 5; j++) {
            System.out.print(i.next().toString() + ", ");
        }
        System.out.println();
    }

    public static void testPostorderRecursive(LinkedBinarySearchTree<Integer> tree, String expected) {
        System.out.println("Expected Result: " + expected);
        tree.printPostorderRecursive();
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("Testing binary search tree printing");
        LinkedBinarySearchTree<Integer> tree = new LinkedBinarySearchTree<>();
        int[] temp = {3, 5, 7, 1, 4, 2, 51, 17, 43, 28, 47, 89, 6};
        for (int aTemp : temp) {
            tree.add(aTemp);
        }
        testPreorderRecursive(tree, "3, 1, 2, 5, 4, 7, 6, 51, 17, 43, 28, 47, 89,");
        testPreorderIterator(tree, "3, 1, 2, 5, 4, 7, 6, 51, 17, 43, 28, 47, 89,");
        testInorderRecursive(tree, "1, 2, 3, 4, 5, 6, 7, 17, 28, 43, 47, 51, 89,");
        testInorderIterator(tree, "1, 2, 3, 4, 5, 6, 7, 17, 28, 43, 47, 51, 89,");
        testPostorderRecursive(tree, "2, 1, 4, 6, 28, 47, 43, 17, 89, 51, 7, 5, 3,");
    }
}
