package structures;

import addons.TreePrinter;
import enums.Place;

/**
 * Created by Tobiasz Rumian on 19.03.2017.
 */
public class BstTree implements Structure {
    public static Node root;
    private Integer size=0;

    public BstTree() {
        root = null;
    }

    @Override
    public String info() {
        return "Dynamiczna struktura danych będąca drzewem binarnym, \n" +
                "w którym lewe poddrzewo każdego węzła zawiera wyłącznie elementy\n" +
                " o kluczach mniejszych niż klucz węzła a prawe poddrzewo zawiera wyłącznie elementy\n" +
                " o kluczach nie mniejszych niż klucz węzła. Węzły, oprócz klucza, przechowują wskaźniki\n" +
                " na swojego lewego i prawego syna oraz na swojego ojca.";
    }

    @Override
    public void subtract(Place place, Integer number) throws IllegalArgumentException, IndexOutOfBoundsException {
        size--;
        Node parent = root;
        Node current = root;
        boolean isLeftChild = false;
        while (!current.getInteger().equals(number)) {
            parent = current;
            if (current.getInteger() > number) {
                isLeftChild = true;
                current = current.getBefore();
            } else {
                isLeftChild = false;
                current = current.getAfter();
            }
        }
        //if i am here that means we have found the bidirectionalObject
        //Case 1: if bidirectionalObject to be deleted has no children
        if (current.getBefore() == null && current.getAfter() == null) {
            if (current == root) root = null;
            if (isLeftChild) parent.setBefore(null);
            else parent.setAfter(null);
        }
        //Case 2 : if bidirectionalObject to be deleted has only one child
        else if (current.getAfter() == null) {
            if (current == root) root = current.getBefore();
            else if (isLeftChild) parent.setBefore(current.getBefore());
            else parent.setAfter(current.getBefore());
        } else if (current.getBefore() == null) {
            if (current == root) root = current.getAfter();
            else if (isLeftChild) parent.setBefore(current.getAfter());
            else parent.setAfter(current.getAfter());
        } else if (current.getBefore() != null && current.getAfter() != null) {

            //now we have found the minimum element in the right sub tree
            Node successor = getSuccessor(current);
            if (current == root) root = successor;
            else if (isLeftChild) parent.setBefore(successor);
            else parent.setAfter(successor);
            successor.setBefore(current.getBefore());
        }
        DSW();
    }

    @Override
    public void add(Place place, Integer number) throws IllegalArgumentException {
        Node newNode = new Node(number);
        size++;
        if (root == null) {
            root = newNode;
            return;
        }
        Node current = root;
        Node parent;
        while (true) {
            parent = current;
            if (number < current.getInteger()) {
                current = current.getBefore();
                if (current == null) {
                    parent.setBefore(newNode);
                    DSW();
                    return;
                }
            } else {
                current = current.getAfter();
                if (current == null) {
                    parent.setAfter(newNode);
                    DSW();
                    return;
                }
            }
        }
    }

    @Override
    public Integer find(Integer find) {
        Node current = root;
        Integer counter = 0;
        while (current != null) {
            if (current.getInteger().equals(find)) return counter;
            else if (current.getInteger() > find) current = current.getAfter();
            else current = current.getBefore();
            counter++;
        }
        return -1;
    }

    @Override
    public String show() {
        TreePrinter.print(root);
        return null;
    }

    private Node getSuccessor(Node deleleNode) {
        Node successor = null;
        Node successorParent = null;
        Node current = deleleNode.getAfter();
        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.getBefore();
        }
        //check if successor has the right child, it cannot have left child for sure
        // if it does have the right child, add it to the left of successorParent.
        //		successsorParent
        if (successor != deleleNode.getAfter()) {
            successorParent.setBefore(successor.getAfter());
            successor.setAfter(successor.getAfter());
        }
        return successor;
    }

    @Override
    public String toString() {
        return "Drzewo BST";
    }

    @Override
    public Integer size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
    }


    public void DSW() {
        if (null != root) {
            createBackbone();// effectively: createBackbone( root)
            createPerfectBST();//effectively: createPerfectBST( root)
        }
    }

    /**
     * Time complexity: O(n)
     */
    private void createBackbone() {
        Node grandParent = null;
        Node parent = root;
        Node leftChild;

        while (null != parent) {
            leftChild = parent.getBefore();
            if (null != leftChild) {
                grandParent = rotateRight(grandParent, parent, leftChild);
                parent = leftChild;
            } else {
                grandParent = parent;
                parent = parent.getAfter();
            }
        }
    }

    /************************************************************************
     *   Before      After
     *    Gr          Gr
     *     \           \
     *     Par         Ch
     *    /  \        /  \
     *   Ch   Z      X   Par
     *  /  \            /  \
     * X    Y          Y    Z
     ***********************************************************************/
    private Node rotateRight(Node grandParent, Node parent, Node leftChild) {
        if (null != grandParent) {
            grandParent.setAfter(leftChild);
        } else {
            root = leftChild;
        }
        parent.setBefore(leftChild.getAfter());
        leftChild.setAfter(parent);
        return grandParent;
    }

    /**
     * Time complexity: O(n)
     */
    private void createPerfectBST() {
        int n = 0;
        for (Node tmp = root; null != tmp; tmp = tmp.getAfter()) {
            n++;
        }
        //m = 2^floor[lg(n+1)]-1, ie the greatest power of 2 less than n: minus 1
        int m = greatestPowerOf2LessThanN(n + 1) - 1;
        makeRotations(n - m);

        while (m > 1) {
            makeRotations(m /= 2);
        }
    }

    /**
     * Time complexity: log(n)
     */
    private int greatestPowerOf2LessThanN(int n) {
        int x = MSB(n);//MSB
        return (1 << x);//2^x
    }

    /**
     * Time complexity: log(n)
     * return the index of most significant set bit: index of
     * least significant bit is 0
     */
    private int MSB(int n) {
        int ndx = 0;
        while (1 < n) {
            n = (n >> 1);
            ndx++;
        }
        return ndx;
    }

    private void makeRotations(int bound) {
        Node grandParent = null;
        Node parent = root;
        Node child = root.getAfter();
        for (; bound > 0; bound--) {
            try {
                if (null != child) {
                    rotateLeft(grandParent, parent, child);
                    grandParent = child;
                    parent = grandParent.getAfter();
                    child = parent.getAfter();
                } else {
                    break;
                }
            } catch (NullPointerException convenient) {
                break;
            }
        }
    }

    private void rotateLeft(Node grandParent, Node parent, Node rightChild) {
        if (null != grandParent) {
            grandParent.setAfter(rightChild);
        } else {
            root = rightChild;
        }
        parent.setAfter(rightChild.getBefore());
        rightChild.setBefore(parent);
    }
}

