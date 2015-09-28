/*  CSI2114 Lab 2 - TestDLinkedList.java
 *  
 *  Class to test doubly linked list   
 *  
 *  by Jeff Souza
 *
 */

class TestDLinkedList {

    public static void main(String[] args) {

        ListNode nNode = new ListNode();
        nNode.data = 1;
        DLinkedList list = new DLinkedList();
        list.firstNode = nNode;
        list.lastNode = nNode;
    
        // add items to linked list
        for (int i = 2; i < 11; i++) {
            nNode = new ListNode();
            nNode.data = i;
            list.AppendNode(nNode);
        }
 
        System.out.println();

        // print the content of the list
        list.print();

        System.out.println("items removed.");

        // remove items from linked list
        list.RemoveNode(list.firstNode);
        list.RemoveNode(list.firstNode);
        list.RemoveNode(list.lastNode);
 
        // print the content of the list
        list.print();
    }
}