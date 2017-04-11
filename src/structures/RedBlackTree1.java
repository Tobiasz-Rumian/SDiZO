package structures;

import addons.TreePrinter;
import enums.Color;
import enums.Place;

/**
 * Created by Tobiasz Rumian on 11.04.2017.
 */
public class RedBlackTree1 implements Structure {
    private RedBlackNode root;
    private RedBlackNode nil = new RedBlackNode(null);
    private Integer size = 0;

    @Override
    public String info() {
        return null;
    }

    @Override
    public void subtract(Place place, Integer number) throws IllegalArgumentException, IndexOutOfBoundsException {

    }

    @Override
    public void add(Place place, Integer number) throws IllegalArgumentException {
        size++;
        if (root == null) {
            root = new RedBlackNode(number, Color.BLACK);
            return;
        }
        RedBlackNode newNode = new RedBlackNode(number, Color.RED);
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
    public Integer find(Integer find) {
        return null;
    }

    @Override
    public String show() {
        TreePrinter.print(root);
        return null;
    }

    @Override
    public Integer size() {
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
