package structures;

import enums.Place;

/**
 * Created by Tobiasz Rumian on 19.03.2017.
 */
public interface Structure {

    String info();

    void subtract(Place place,Integer number)throws IllegalArgumentException,IndexOutOfBoundsException;

    void add(Place place, Integer number) throws IllegalArgumentException;

    Integer find(Integer find);

    String show();

    String toString();

    Integer size();

    void clear();
}

