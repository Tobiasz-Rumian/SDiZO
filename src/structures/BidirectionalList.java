package structures;

import enums.Place;
import enums.PlaceOnList;
import view.View;

import java.util.Objects;

/**
 * Created by Tobiasz Rumian on 30.03.2017.
 */
public class BidirectionalList implements Structure {
    private Node firstElement;
    private Node lastElement;

    public Integer size() {
        if (firstElement == null) return 0;
        Integer size = 0;
        Node list = firstElement;
        do {
            list = list.getAfter();
            size++;
        } while (list != null);
        return size;
    }

    public Node get(Integer index){
        if (firstElement == null) return null;
        Integer counter=0;
        Node list=firstElement;
        while(!Objects.equals(counter, index)){
            list=list.getAfter();
            counter++;
        }
        return list;
    }

    public boolean isEmpty() {
        return firstElement == null;
    }

    public void clear() {
    firstElement=null;
    lastElement=null;
    }

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
    public void subtract(Place place, Integer number) throws IndexOutOfBoundsException,IllegalArgumentException{
        if(this.isEmpty())throw new IndexOutOfBoundsException("Lista jest pusta!");
        if(firstElement.getAfter()==null) this.clear();

        if(place==Place.START) {
            firstElement.getAfter().setBefore(null);
        }
        else if(place==Place.END) {
            lastElement.getBefore().setAfter(null);
        }
        else if(place==Place.RANDOM) {
            Node list = get(View.getRandom(0,this.size()));
            list.getBefore().setAfter(list.getAfter());
            list.getAfter().setBefore(list.getBefore());
        }
        else {
            throw new IllegalArgumentException("Podano nieprawidłowe miejsce!");
        }
    }

    @Override
    public void add(Place place, Integer number) throws IllegalArgumentException{
        if(this.isEmpty()){
            Node list=new Node(number);
            firstElement=list;
            lastElement=list;
        }
        if(place==Place.START) {
            Node newList=new Node(firstElement,number, PlaceOnList.AFTER);
            firstElement.setBefore(newList);
            firstElement=newList;
        }
        else if(place==Place.END) {
            Node newList=new Node(lastElement,number, PlaceOnList.BEFORE);
            lastElement.setAfter(newList);
            lastElement=newList;
        }
        else if(place==Place.RANDOM) {
            Node list = get(View.getRandom(0,this.size()));
            Node newList = new Node(list.getBefore(),list,number);
            list.getBefore().setAfter(newList);
            list.setBefore(newList);
        }
        else {
            throw new IllegalArgumentException("Podano nieprawidłowe miejsce!");
        }
    }

    @Override
    public Integer find(Integer find){
        if(this.isEmpty()) return -1;
        Node list = firstElement;
        Integer counter=0;
        while(!list.getInteger().equals(find)){
            list=list.getAfter();
            if(list==null) return -1;
            counter++;
        }
        return counter;
    }

    @Override
    public String show() {
        StringBuilder sb=new StringBuilder();
        Node list = firstElement;
        while(list.getAfter()!=null){
            sb.append("[").append(list.getInteger()).append("]");
            list=list.getAfter();
        }
    return sb.toString();
    }

    @Override
    public String toString(){
        return "Lista dwukierunkowa";
    }
}
