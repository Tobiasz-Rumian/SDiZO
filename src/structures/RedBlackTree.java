package structures;

import enums.Color;
import enums.Place;

/**
 * Created by Tobiasz Rumian on 20.03.2017.
 */

public class RedBlackTree implements Structure {

    private RedBlackNode current;
    private RedBlackNode parent;
    private RedBlackNode grand;
    private RedBlackNode great;
    private RedBlackNode header;
    private static RedBlackNode nullNode;
    static {
        nullNode = new RedBlackNode(nullNode, nullNode, 0);
    }

    /* Constructor */
    public RedBlackTree() {
        header = new RedBlackNode(nullNode, nullNode, null);
    }

    /* Function to check if tree is empty */
    public boolean isEmpty() {
        return header.getAfter() == nullNode;
    }

    /* Make the tree logically empty */
    public void makeEmpty() {
        header.setAfter(nullNode);
    }

    private void handleReorient(int item) {
        // Do the color flip
        current.setColor(Color.RED);
        current.getBefore().setColor(Color.BLACK);
        current.getAfter().setColor(Color.BLACK);

        if (parent.getColor() == Color.RED) {
            // Have to rotate
            grand.setColor(Color.RED);
            if (item < grand.getInteger() != item < parent.getInteger())
                parent = rotate(item, grand);  // Start dbl rotate
            current = rotate(item, great);
            current.setColor(Color.BLACK);
        }
        // Make root black
        header.getAfter().setColor(Color.BLACK);
    }

    private RedBlackNode rotate(int item, RedBlackNode parent) {
        RedBlackNode node;
        if (item < parent.getInteger()) {
            if (item < parent.getBefore().getInteger()) node = rotateWithLeftChild(parent.getBefore());
            else node = rotateWithRightChild(parent.getBefore());
            parent.setBefore(node);
            return parent.getBefore();
        }else{
            if(item < parent.getAfter().getInteger())node=rotateWithLeftChild(parent.getAfter());
            else node=rotateWithRightChild(parent.getAfter());
            parent.setAfter(node);
            return parent.getAfter();
        }
    }

    /* Rotate binary tree node with left child */
    private RedBlackNode rotateWithLeftChild(RedBlackNode k2) {
        RedBlackNode k1 = k2.getBefore();
        k2.setBefore(k1.getAfter());
        k1.setAfter(k2);
        return k1;
    }

    /* Rotate binary tree node with right child */
    private RedBlackNode rotateWithRightChild(RedBlackNode k1) {
        RedBlackNode k2 = k1.getAfter();
        k1.setAfter(k2.getBefore());
        k2.setBefore(k1);
        return k2;
    }

    /* Functions to count number of nodes */
    public int countNodes() {
        return countNodes(header.getAfter());
    }

    private int countNodes(RedBlackNode r) {
        if (r == nullNode)
            return 0;
        else {
            int l = 1;
            l += countNodes(r.getBefore());
            l += countNodes(r.getAfter());
            return l;
        }
    }

    /* Functions to search for an element */
    public boolean search(int val) {
        return search(header.getAfter(), val);
    }

    private boolean search(RedBlackNode r, int val) {
        boolean found = false;
        while ((r != nullNode) && !found) {
            int rval = r.getInteger();
            if (val < rval)
                r = r.getBefore();
            else if (val > rval)
                r = r.getAfter();
            else {
                found = true;
                break;
            }
            found = search(r, val);
        }
        return found;
    }

    /* Function for inorder traversal */
    public void inorder() {
        inorder(header.getAfter());
    }

    private void inorder(RedBlackNode r) {
        if (r != nullNode) {
            inorder(r.getBefore());
            char c = 'B';
            if (r.getColor() == Color.RED)
                c = 'R';
            System.out.print(r.getInteger() + "" + c + " ");
            inorder(r.getAfter());
        }
    }

    /* Function for preorder traversal */
    public void preorder() {
        preorder(header.getAfter());
    }

    private void preorder(RedBlackNode r) {
        if (r != nullNode) {
            char c = 'B';
            if (r.getColor() == Color.RED)
                c = 'R';
            System.out.print(r.getInteger() + "" + c + " ");
            preorder(r.getBefore());
            preorder(r.getAfter());
        }
    }

    /* Function for postorder traversal */
    public void postorder() {
        postorder(header.getAfter());
    }

    private void postorder(RedBlackNode r) {
        if (r != nullNode) {
            postorder(r.getBefore());
            postorder(r.getAfter());
            char c = 'B';
            if (r.getColor() == Color.RED)
                c = 'R';
            System.out.print(r.getInteger() + "" + c + " ");
        }
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public void subtract(Place place, Integer number) throws IllegalArgumentException, IndexOutOfBoundsException {

    }

    @Override
    public void add(Place place, Integer number) throws IllegalArgumentException {
        current = parent = grand = header;
        nullNode.setInteger(number);
        while (current.getInteger() != number) {
            great = grand;
            grand = parent;
            parent = current;
            current = number < current.getInteger() ? current.getBefore() : current.getAfter();
            // Check if two red children and fix if so
            if (current.getBefore().getColor() == Color.RED && current.getAfter().getColor() == Color.RED)
                handleReorient(number);
        }
        // Insertion fails if already present
        if (current != nullNode)
            return;
        current = new RedBlackNode( nullNode, nullNode,number);
        // Attach to parent
        if (number < parent.getInteger())
            parent.setBefore(current);
        else
            parent.setAfter(current);
        handleReorient(number);
    }

    @Override
    public Integer find(Integer find) {
        return search(header.getAfter(), find)?1:-1;
    }

    @Override
    public String show() {
        StringBuilder sb=new StringBuilder();
        RedBlackNode list = header;
        while(list.getAfter()!=null){
            sb.append("[").append(list.getInteger()).append("]");
            list=list.getAfter();
        }
        return sb.toString();
    }
}