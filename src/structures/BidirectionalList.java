package structures;

import enums.Place;
import enums.PlaceOnList;
import addons.View;

import java.util.Objects;

/**
 * Klasa reprezentująca listę dwukierunkową.
 * Korzysta z interfejsu struktur.
 *
 * @author Tobiasz Rumian.
 */
public class BidirectionalList implements Structure {
    private Node firstElement;
    private Node lastElement;

    @Override
    public String info() {
        return "Lista – struktura danych służąca do reprezentacji zbiorów dynamicznych,\n" +
                " w której elementy ułożone są w liniowym porządku.\n" +
                " Rozróżniane są dwa podstawowe rodzaje list:\n" +
                " lista jednokierunkowa, w której z każdego elementu możliwe jest przejście do jego następnika, \n" +
                "oraz lista dwukierunkowa, w której z każdego elementu możliwe jest przejście \n" +
                "do jego poprzednika i następnika.";
    }

    @Override
    public Integer size() {
        if (firstElement == null) return 0;
        Integer size = 0;
        Node list = firstElement;
        do {
            list = list.getRightN();
            size++;
        } while (list != null);
        return size;
    }

    @Override
    public void clear() {
        firstElement = null;
        lastElement = null;
    }

    @Override
    public void subtract(Place place, Integer number){
        if (firstElement == null) return;
        if (firstElement.getRightN() == null||lastElement.getLeftN()==null) this.clear();
        switch (place) {
            case START:
                firstElement=firstElement.getRightN();
                firstElement.setLeft(null);
                break;
            case END:
                lastElement=lastElement.getLeftN();
                lastElement.setRight(null);
                break;
            case RANDOM:
                Node list = get(View.getRandom(0, this.size()));
                if(list==firstElement){
                    firstElement=firstElement.getRightN();
                    firstElement.setLeft(null);
                }else if(list==lastElement){
                    lastElement=lastElement.getLeftN();
                    lastElement.setRight(null);
                }else{
                    list.getLeftN().setRight(list.getRightN());
                    list.getRightN().setLeft(list.getLeftN());
                }

                break;
        }
    }

    @Override
    public void add(Place place, Integer number){
        if (firstElement == null) {
            Node list = new Node(number);
            firstElement = list;
            lastElement = list;
            return;
        }
        switch (place) {
            case START: {
                Node newList = new Node(firstElement, number, PlaceOnList.AFTER);
                firstElement.setLeft(newList);
                firstElement = newList;
                break;
            }
            case END: {
                Node newList = new Node(lastElement, number, PlaceOnList.BEFORE);
                lastElement.setRight(newList);
                lastElement = newList;
                break;
            }
            case RANDOM: {
                int i=View.getRandom(0, this.size());
                if(i==0){
                    Node newList = new Node(firstElement, number, PlaceOnList.AFTER);
                    firstElement.setLeft(newList);
                    firstElement = newList;
                }else if(i==size()){
                    Node newList = new Node(lastElement, number, PlaceOnList.BEFORE);
                    lastElement.setRight(newList);
                    lastElement = newList;
                }else{
                    Node list = get(i);
                    Node newList = new Node(list.getLeftN(), list, number);
                    list.getLeftN().setRight(newList);
                    list.setLeft(newList);
                }

                break;
            }
        }
    }

    @Override
    public boolean find(Integer find) {
        if (firstElement == null) return false;
        Node list = firstElement;
        while (!list.getInteger().equals(find)) {
            list = list.getRightN();
            if (list == null) return false;
        }
        return true;
    }

    @Override
    public String show() {
        StringBuilder sb = new StringBuilder();
        Node list = firstElement;
        while (list.getRightN() != null) {
            sb.append("[").append(list.getInteger()).append("]");
            list = list.getRightN();
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Lista dwukierunkowa";
    }


    /**
     * Funkcja pozwalająca zdobyć rekord pasujący do wartości.
     * @param index Wartość szukanego rekordu.
     * @return Zwraca szukany rekord.
     */
    private Node get(Integer index) {
        Integer counter = 0;
        Node list = firstElement;
        while (!Objects.equals(counter, index)) {
            list = list.getRightN();
            counter++;
        }
        return list;
    }
}
