package structures;

import enums.Color;
import enums.Place;

/**
 * Created by Tobiasz Rumian on 11.04.2017.
 */
public class RedBlackTree implements Structure {
    private RedBlackNode root;
    private RedBlackNode nil = new RedBlackNode(null);
    private int size = 0;

    @Override
    public String info() {
        return null;
    }

    @Override
    public void subtract(Place place, int number) throws IllegalArgumentException, IndexOutOfBoundsException {
//TODO: dodać możliwość usuwania
    }

    @Override
    public void add(Place place, int number) throws IllegalArgumentException {
        size++;

        RedBlackNode newNode = new RedBlackNode(number);
        newNode.setLeft(null);
        newNode.setRight(null);
        size++;
        if (root == null) {
            newNode.setColor(Color.BLACK);
            root = newNode;
            return;
        }
        RedBlackNode current = root;
        RedBlackNode parent;
        while (true) {
            parent = current;
            if (number < current.getInteger()) {
                current = current.getLeft();
                if (current == null) {
                    parent.setLeft(newNode);
                    newNode.setUp(parent);
                    correct(newNode);
                    return;
                }
            } else {
                current = current.getRight();
                if (current == null) {
                    parent.setRight(newNode);
                    newNode.setUp(parent);
                    correct(newNode);
                    return;
                }
            }
        }
    }

    private void correct(RedBlackNode newNode){
        if (newNode.getUp().getColor() == Color.BLACK) return;
        if (newNode.getUp().getUp().getRight().getColor() == Color.RED) {
            newNode.getUp().getUp().getRight().setColor(Color.BLACK);
            newNode.getUp().setColor(Color.BLACK);
            if (!newNode.getUp().getUp().equals(root)) newNode.getUp().getUp().setColor(Color.RED);
            return;
        }
        if (newNode.getUp().getUp().getLeft().getColor() == Color.RED) {
            newNode.getUp().getUp().getLeft().setColor(Color.BLACK);
            newNode.getUp().setColor(Color.BLACK);
            if (!newNode.getUp().getUp().equals(root)) newNode.getUp().getUp().setColor(Color.RED);
            return;
        }
        if (newNode.getUp().getUp().getRight().getColor() == Color.BLACK && newNode.equals(newNode.getUp().getRight())) {
            rotateLeft(newNode);
            newNode.getUp().changeColor();
            newNode.changeColor();
            rotateRight(newNode);
        }
        else if (newNode.getUp().getUp().getLeft().getColor() == Color.BLACK && newNode.equals(newNode.getUp().getLeft())) {
            rotateRight(newNode);
            newNode.getUp().changeColor();
            newNode.changeColor();
            rotateRight(newNode);
        }
        if (newNode.getUp().getUp().getRight().getColor() == Color.BLACK && newNode.equals(newNode.getUp().getLeft())) {
            newNode.getUp().changeColor();
            newNode.changeColor();
            rotateRight(newNode);
        }
    }

    @Override
    public boolean find(int find) {
        return false;
    }

    @Override
    public String show() {
        //return TreePrinter.print(root);
        printRBT("","",root);
        return "";
    }

    private void printRBT(String sp, String sn, RedBlackNode p)
    {
        String cr = "┌─",cl= "└─", cp= "│ ";
        String t="";
        if(p != null)
        {
            t = sp;
            if(sn.equals(cr)) t=t.replace(t.charAt(t.length() - 2),' ');
            printRBT(t+cp,cr,p.getRight());
            //t = t.substring(0,sp.length()-2);
            System.out.println(t+sn+p.getColorString()+":"+p.getInteger());
            t = sp;
            if(sn.equals(cl)) t=t.replace(t.charAt(t.length() - 2),' ');
            printRBT(t+cp,cl,p.getLeft());
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root=null;
    }

    private RedBlackNode rotateRight(RedBlackNode node) {
        RedBlackNode parent = node.getUp();
        RedBlackNode grandParent= node.getUp().getUp();
        if(parent.equals(grandParent.getLeft()))grandParent.setLeft(node);
        if(parent.equals(grandParent.getRight()))grandParent.setRight(node);
        node.setUp(grandParent);
        parent.setLeft(node.getRight());
        node.setRight(parent);
        parent.setUp(node);
        return parent;
    }

    private RedBlackNode rotateLeft(RedBlackNode node) {
        RedBlackNode parent = node.getUp();
        RedBlackNode grandParent= node.getUp().getUp();
        if(parent.equals(grandParent.getLeft()))grandParent.setLeft(node);
        if(parent.equals(grandParent.getRight()))grandParent.setRight(node);
        node.setUp(grandParent);
        parent.setRight(node.getLeft());
        node.setLeft(parent);
        parent.setUp(node);
        return parent;
    }
}
