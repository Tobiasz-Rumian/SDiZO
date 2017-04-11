package structures;

import addons.PrintableNode;
import enums.Color;

/**
 * Created by Tobiasz Rumian on 04.04.2017.
 */
public class RedBlackNode implements PrintableNode {
    private RedBlackNode left = null;
    private RedBlackNode right = null;
    private RedBlackNode up = null;
    private Integer integer = null;
    private Color color = null;

    public RedBlackNode(RedBlackNode left, RedBlackNode right, RedBlackNode up, Integer integer, Color color) {
        this.left = left;
        this.right = right;
        this.up = up;
        this.integer = integer;
        this.color = color;
    }

    public RedBlackNode(Integer integer, Color color) {
        this.integer = integer;
        this.color = color;
    }

    public RedBlackNode(Integer integer) {
        this.integer = integer;
    }

    @Override
    public RedBlackNode getLeft() {
        return left;
    }

    public void setLeft(RedBlackNode left) {
        this.left = left;
    }

    @Override
    public RedBlackNode getRight() {
        return right;
    }

    @Override
    public String getText() {
        return integer.toString();
    }

    public void setRight(RedBlackNode right) {
        this.right = right;
    }

    public RedBlackNode getUp() {
        return up;
    }

    public void setUp(RedBlackNode up) {
        this.up = up;
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


    public Color getColor() {
        return color;
    }

    @Override
    public String getColorString() {
        return color == Color.BLACK ? "B" : "R";
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void changeColor() {
        if (color == Color.BLACK) color = Color.RED;
        else color = Color.BLACK;
    }
}

