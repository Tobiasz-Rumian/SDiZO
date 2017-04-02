package structures;

import enums.Place;
import enums.PlaceOnList;
import view.FileChooser;
import view.View;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Created by Tobiasz Rumian on 30.03.2017.
 */
public class BidirectionalList implements Structures{
    private BidirectionalObject firstElement;
    private BidirectionalObject lastElement;

    public int size() {
        if (firstElement == null) return 0;
        Integer size = 0;
        BidirectionalObject list = firstElement;
        do {
            list = list.getAfterList();
            size++;
        } while (list != null);
        return size;
    }

    public BidirectionalObject get(Integer index){
        if (firstElement == null) return null;
        Integer counter=0;
        BidirectionalObject list=firstElement;
        while(!Objects.equals(counter, index)){
            list=list.getAfterList();
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
        return BidirectionalObject.INFO;
    }

    @Override
    public void loadFromFile() {
        FileChooser fileChooser = new FileChooser();
        try (Stream<String> stream = Files.lines(Paths.get(fileChooser.getPath()))) {
            stream.forEach(x -> add(Place.END, Integer.parseInt(x)));
            subtract(Place.START, null);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Override
    public void subtract(Place place, Integer number) throws IndexOutOfBoundsException,IllegalArgumentException{
        if(this.isEmpty())throw new IndexOutOfBoundsException("Lista jest pusta!");
        if(firstElement.getAfterList()==null) this.clear();

        if(place==Place.START) {
            firstElement.getAfterList().setBeforeList(null);
        }
        else if(place==Place.END) {
            lastElement.getBeforeList().setAfterList(null);
        }
        else if(place==Place.RANDOM) {
            BidirectionalObject list = get(View.getRandom(0,this.size()));
            list.getBeforeList().setAfterList(list.getAfterList());
            list.getAfterList().setBeforeList(list.getBeforeList());
        }
        else {
            throw new IllegalArgumentException("Podano nieprawidłowe miejsce!");
        }
    }

    @Override
    public void add(Place place, Integer number) throws IllegalArgumentException{
        if(this.isEmpty()){
            BidirectionalObject list=new BidirectionalObject(number);
            firstElement=list;
            lastElement=list;
        }
        if(place==Place.START) {
            BidirectionalObject newList=new BidirectionalObject(firstElement,number, PlaceOnList.AFTER);
            firstElement.setBeforeList(newList);
            firstElement=newList;
        }
        else if(place==Place.END) {
            BidirectionalObject newList=new BidirectionalObject(lastElement,number, PlaceOnList.BEFORE);
            lastElement.setAfterList(newList);
            lastElement=newList;
        }
        else if(place==Place.RANDOM) {
            BidirectionalObject list = get(View.getRandom(0,this.size()));
            BidirectionalObject newList = new BidirectionalObject(list.getBeforeList(),list,number);
            list.getBeforeList().setAfterList(newList);
            list.setBeforeList(newList);
        }
        else {
            throw new IllegalArgumentException("Podano nieprawidłowe miejsce!");
        }
    }

    @Override
    public Integer find(Integer find){
        if(this.isEmpty()) return -1;
        BidirectionalObject list = firstElement;
        Integer counter=0;
        while(!list.getInteger().equals(find)){
            list=list.getAfterList();
            if(list==null) return -1;
            counter++;
        }
        return counter;
    }

    @Override
    public String show() {
        StringBuilder sb=new StringBuilder();
        BidirectionalObject list = firstElement;
        while(list.getAfterList()!=null){
            sb.append("[").append(list.getInteger()).append("]");
            list=list.getAfterList();
        }
    return sb.toString();
    }

    @Override
    public void test() {

    }
}
