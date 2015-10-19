/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;

/**
 * LinkedBinarySearchTree for tree traversal lab
 *
 * @param <E>
 * @author Lachlan Plant
 */
public class LinkedBinarySearchTree<E extends Comparable<? super E>> implements Iterable<E> {

    private class Node<T> {
        T elem;
        Node<T> parent;
        Node<T> left;
        Node<T> right;

        public Node(T e, Node<T> p, Node<T> l, Node<T> r) {
            elem = e;
            parent = p;
            left = l;
            right = r;
        }
    }

    private Node<E> root;

    /**
     *
     */
    public LinkedBinarySearchTree() {
        root = null;
    }

    /**
     * Adds elem into BST
     *
     * @param elem The element to add
     */
    public void add(E elem) {
        if (root == null) {
            root = new Node<>(elem, null, null, null);
        } else {
            root = insert(elem, root, null);
        }
    }

    /**
     * Recursive BST insertion
     *
     * @param elem The element to add
     * @param curr The current element
     * @param from The element to add from
     * @return The inserted element
     */
    private Node<E> insert(E elem, Node<E> curr, Node<E> from) {
        if (curr == null) {
            curr = new Node<>(elem, from, null, null);
            return curr;
        }
        if (elem.compareTo(curr.elem) < 0) {
            curr.left = insert(elem, curr.left, curr);
        }
        if (elem.compareTo(curr.elem) > 0) {
            curr.right = insert(elem, curr.right, curr);
        }
        return curr;
    }


    /*****************************************************************
     *
     * Recursive Printing Functions
     *
     *
     *****************************************************************/

    /**
     * Caller method for preorder recursive printing
     */
    public void printPreorderRecursive() {
        System.out.print("Recursive Preorder Printing: ");
        preorderRecursive(root);
        System.out.println();
    }

    /**
     * preorder tree traversal, prints(curr.elem + ", ")
     *
     * @param curr The current element
     */
    private void preorderRecursive(Node<E> curr) {
        System.out.print(curr.elem + ", ");
        if (curr.left != null) {
            preorderRecursive(curr.left);
        }
        if (curr.right != null) {
            preorderRecursive(curr.right);
        }
    }

    /**
     * Caller method for inorder recursive printing
     */
    public void printInorderRecursive() {
        System.out.print("Recursive Inorder Printing: ");
        inorderRecursive(root);
        System.out.println();
    }

    /**
     * inorder tree traversal, prints(curr.elem + ", ")
     *
     * @param curr The current element
     */
    private void inorderRecursive(Node<E> curr) {
        if (curr.left != null) {
            inorderRecursive(curr.left);
        }

        System.out.print(curr.elem + ", ");

        if (curr.right != null) {
            inorderRecursive(curr.right);
        }
    }


    /**
     * Caller method for postorder recursive printing
     */
    public void printPostorderRecursive() {
        System.out.print("Recursive Postorder Printing: ");
        postorderRecursive(root);
        System.out.println();
    }

    /**
     * postorder tree traversal, prints(curr.elem + ", ")
     *
     * @param curr The current element
     */
    private void postorderRecursive(Node<E> curr) {
        if (curr.left != null) {
            postorderRecursive(curr.left);
        }

        if (curr.right != null) {
            postorderRecursive(curr.right);
        }

        System.out.print(curr.elem + ", ");
    }


    /*****************************************************************
     * Iterator Functions
     *****************************************************************/


    public Iterator<E> iterator() {
        return new InorderIterator();
    }

    public Iterator<E> inorderIterator() {
        return new InorderIterator();
    }

    public Iterator<E> preorderIterator() {
        return new PreorderIterator();
    }


    /*****************************************************************
     *
     * Iterators 
     *
     *
     *****************************************************************/


    /**
     * Tree Iterator using preorder traversal for ordering
     */
    private class PreorderIterator implements Iterator<E> {
        Node<E> next;

        public PreorderIterator() {
            next = root;
        }

        public boolean hasNext() {
            return next != null;
        }

        public E next() {
            E current = next.elem;
            Node<E> parent;
            Node<E> child;
            if (next.left != null) {
                next = next.left;
            } else {
                if (next.right != null) {
                    next = next.right;
                } else {
                    parent = next.parent;
                    child = next;
                    while (parent != null && (parent.right == child || parent.right == null)) {
                        child = parent;
                        parent = parent.parent;
                    }
                    if (parent == null) {
                        next = null;
                    } else {
                        next = parent.right;
                    }
                }
            }
            return current;
        }

        public void remove() {
            // not implemented
        }
    }

    /**
     * Tree Iterator using inorder traversal for ordering
     */
    private class InorderIterator implements Iterator<E> {

        Node<E> next;

        public InorderIterator() {
            next = root;
            if (next != null) {
                while(next.left != null) {
                    next = next.left;
                }
            }
        }

        public boolean hasNext() {
            return next != null;
        }

        public E next() {
            E current = next.elem;
            if (next.right != null) {
                next = next.right;
                while (next.left != null) {
                    next = next.left;
                }
            } else {
                while (true) { //If you can, go up till you came from the left
                    if (next.parent == null) {
                        next = null;
                        break;
                    }
                    if (next.parent.left == next) {
                        next = next.parent;
                        break;
                    }
                    next = next.parent;
                }
            }
            return current;
        }

        public void remove() {
            // not implemented
        }
    }
}
