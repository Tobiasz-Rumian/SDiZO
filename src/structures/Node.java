package structures;

import enums.PlaceOnList;

/**
 * Created by Tobiasz Rumian on 30.03.2017.
 */
public class Node {
    private Node before = null;
    private Node after = null;
    private Integer integer = null;

    public Node(Node before, Node after, Integer integer) {
        this.before = before;
        this.after = after;
        this.integer = integer;
    }

    public Node(Node list, Integer integer, PlaceOnList placeOnList) {
        if (placeOnList==PlaceOnList.BEFORE) this.before = list;
        else this.after = list;
        this.integer = integer;
    }

    public Node(Integer integer) {
        this.integer = integer;
    }

    public Node getBefore() {
        return before;
    }

    public Node getAfter() {
        return after;
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

    public void setAfter(Node after) {

        this.after = after;
    }

    public void setBefore(Node before) {

        this.before = before;
    }
}
