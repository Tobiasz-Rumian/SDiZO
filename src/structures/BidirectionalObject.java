package structures;

import enums.PlaceOnList;

/**
 * Created by Tobiasz Rumian on 30.03.2017.
 */
public class BidirectionalObject {
    public static final String INFO = "Lista – struktura danych służąca do reprezentacji zbiorów dynamicznych,\n" +
            " w której elementy ułożone są w liniowym porządku.\n" +
            " Rozróżniane są dwa podstawowe rodzaje list:\n" +
            " lista jednokierunkowa, w której z każdego elementu możliwe jest przejście do jego następnika, \n" +
            "oraz lista dwukierunkowa, w której z każdego elementu możliwe jest przejście \n" +
            "do jego poprzednika i następnika.";

    private BidirectionalObject beforeList = null;
    private BidirectionalObject afterList = null;
    private Integer integer = null;

    public BidirectionalObject(BidirectionalObject beforeList, BidirectionalObject afterList, Integer integer) {
        this.beforeList = beforeList;
        this.afterList = afterList;
        this.integer = integer;
    }

    public BidirectionalObject(BidirectionalObject List, Integer integer, PlaceOnList placeOnList) {
        if (placeOnList==PlaceOnList.BEFORE) this.beforeList = List;
        else this.afterList = List;
        this.integer = integer;
    }

    public BidirectionalObject(Integer integer) {
        this.integer = integer;
    }

    public BidirectionalObject getBeforeList() {
        return beforeList;
    }

    public BidirectionalObject getAfterList() {
        return afterList;
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

    public void setAfterList(BidirectionalObject afterList) {

        this.afterList = afterList;
    }

    public void setBeforeList(BidirectionalObject beforeList) {

        this.beforeList = beforeList;
    }
}
