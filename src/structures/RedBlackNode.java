package structures;

import enums.Color;
import enums.PlaceOnList;

/**
 * Created by Tobiasz Rumian on 04.04.2017.
 */
public class RedBlackNode {
        private RedBlackNode before = null;
        private RedBlackNode after = null;
        private Integer integer = null;
        private Color color=null;

        public RedBlackNode(RedBlackNode before, RedBlackNode after, Integer integer) {
            this.before = before;
            this.after = after;
            this.integer = integer;
        }

        public RedBlackNode(RedBlackNode list, Integer integer, PlaceOnList placeOnList) {
            if (placeOnList==PlaceOnList.BEFORE) this.before = list;
            else this.after = list;
            this.integer = integer;
        }

        public RedBlackNode(Integer integer) {
            this.integer = integer;
        }

        public RedBlackNode getBefore() {
            return before;
        }

        public RedBlackNode getAfter() {
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

        public void setAfter(RedBlackNode after) {

            this.after = after;
        }

        public void setBefore(RedBlackNode before) {

            this.before = before;
        }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

