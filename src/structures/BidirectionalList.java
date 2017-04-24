package structures;

import addons.View;
import enums.Place;
import enums.PlaceOnList;

/**
 * Klasa reprezentująca listę dwukierunkową.
 * Korzysta z interfejsu struktur.
 *
 * @author Tobiasz Rumian.
 */
public class BidirectionalList implements Structure {
    private Node firstElement;
    private Node lastElement;
    private int size = 0;

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
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        firstElement = null;
        lastElement = null;
        size=0;
    }

    @Override
    public void subtract(Place place, int number) {
        if (firstElement.getRightN() == null || lastElement.getLeftN() == null) clear();
        if (size == 0) return;
        switch (place) {
            case START:
                firstElement = firstElement.getRightN();
                firstElement.setLeft(null);
                break;
            case END:
                lastElement = lastElement.getLeftN();
                lastElement.setRight(null);
                break;
            case RANDOM:
                Node list = get(View.getRandom(0, size()-1));
                if (list == firstElement) {
                    firstElement = firstElement.getRightN();
                    firstElement.setLeft(null);
                } else if (list == lastElement) {
                    lastElement = lastElement.getLeftN();
                    lastElement.setRight(null);
                } else {
                    list.getLeftN().setRight(list.getRightN());
                    list.getRightN().setLeft(list.getLeftN());
                }
                break;
        }
        size--;
    }

    @Override
    public void add(Place place, int number) {
        if (size==0) {
            Node list = new Node(number);
            firstElement = list;
            lastElement = list;
            size++;
            return;
        }
        switch (place) {
            case START: {
                Node newNode = new Node(firstElement, number, PlaceOnList.AFTER);
                firstElement.setLeft(newNode);
                firstElement = newNode;
                break;
            }
            case END: {
                Node newNode = new Node(lastElement, number, PlaceOnList.BEFORE);
                lastElement.setRight(newNode);
                lastElement = newNode;
                break;
            }
            case RANDOM: {
                int i=0;
                if(size!=1) i=View.getRandom(0, size()-1);
                if (i == 0) {
                    Node newNode = new Node(firstElement, number, PlaceOnList.AFTER);
                    firstElement.setLeft(newNode);
                    firstElement = newNode;
                } else if (i == size()-1) {
                    Node newNode = new Node(lastElement, number, PlaceOnList.BEFORE);
                    lastElement.setRight(newNode);
                    lastElement = newNode;
                } else {
                    Node node = get(i);
                    Node newNode = new Node(node.getLeftN(), node, number);
                    node.getLeftN().setRight(newNode);
                    node.setLeft(newNode);
                }
                break;
            }
        }
        size++;
    }

    @Override
    public boolean find(int find) {
        if (size==0) return false;
        Node node = firstElement;
        for(int i=0;i<size;i++){
           // if(node==null)return false;
            if(node.getInteger() == find) return true;
            node = node.getRightN();
        }
        return false;
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
     *
     * @param index Wartość szukanego rekordu.
     * @return Zwraca szukany rekord.
     */
    private Node get(int index) {
        Node node;
        if (((size-1) / 2) <= index){
            node = firstElement;
            for (int i = 0; i < index; i++) node = node.getRightN();
        } else{
            node = lastElement;
            for (int i = 0; i < (size-1-index); i++) node = node.getLeftN();
        }
        return node;


    }
}
