package structures;

import addons.PrintableNode;
import enums.PlaceOnList;

/**
 * Created by Tobiasz Rumian on 30.03.2017.
 */
public class Node implements PrintableNode {
    private Node left = null;
    private Node right = null;
    private Integer integer = null;

    public Node(Node left, Node right, Integer integer) {
        this.left = left;
        this.right = right;
        this.integer = integer;
    }

    public Node(Node list, Integer integer, PlaceOnList placeOnList) {
        if (placeOnList==PlaceOnList.BEFORE) this.left = list;
        else this.right = list;
        this.integer = integer;
    }

    public Node(Integer integer) {
        this.integer = integer;
    }

    @Override
    public PrintableNode getLeft() {
        return left;
    }

    @Override
    public PrintableNode getRight() {
        return right;
    }


    public Node getLeftN() {
        return left;
    }


    public Node getRightN() {
        return right;
    }

    public Integer getInteger() {
        return integer;
    }

    @Override
    public boolean equals(Object o) {
        return this.integer == o;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(integer);
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    @Override
    public String getText() {
        return integer.toString();
    }

    @Override
    public String getColorString() {
        return "";
    }
}
