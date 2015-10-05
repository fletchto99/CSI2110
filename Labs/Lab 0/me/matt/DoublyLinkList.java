package me.matt;

/**
 * Builds a singly linked list of size 5 and prints it to the console.
 *
 * @author Jochen Lang
 */

class DoublyLinkList {
    Node<String> llist;

    DoublyLinkList(int sz) {
        if (sz <= 0) {
            llist = null;
        } else {
            // start with list of size 1
            llist = new Node("0", null);
            Node<String> current = llist; // temp Node for loop
            // add further nodes
            for (int i = 1; i < sz; ++i) {
                // create Node and attach it to the list
                Node<String> node2Add = new Node(Integer.toString(i), null);
                current.setNext(node2Add);   // add first Node
                current = node2Add;
            }
        }
    }

    /**
     * Print all the elements of the list assuming that they are Strings
     */
    public void print() {
    /* Print the list */
        Node current = llist; // point to the first Node
        while (current != null) {
            System.out.print(current.getElement() + " ");
            current = current.getNext(); // move to the next
        }
        System.out.println();
    }

    public void deleteFirst() {
        if (llist != null) {
            llist = llist.getNext();
        }
    }

    public void deleteLast() {
        if (llist == null) return; // no Node
        Node<String> prev = llist;
        Node<String> current = prev.getNext();
        if (current == null) { // only 1 Node
            llist = null;
            return;
        }
        while (current.getNext() != null) { // more than 1 Node
            prev = current;
            current = current.getNext();
        }
        prev.setNext(null);
    }

    // create and display a linked list
    public static void main(String[] args) {
	/* Create the list */
        DoublyLinkList llist = new DoublyLinkList(5);
	/* Print the list */
        llist.print();
	/* delete first and print */
        llist.deleteFirst();
        llist.print();
	/* delete last and print 5 times */
        for (int i = 0; i < 5; ++i) {
            llist.deleteLast();
            llist.print();
        }
    }
}
