package structures;

import enums.Place;

/**
 * Created by Tobiasz Rumian on 19.03.2017.
 */
public interface Structures {

    String info();

    void loadFromFile();

    void subtract(Place place,Integer number)throws IllegalArgumentException,IndexOutOfBoundsException;

    void add(Place place, Integer number) throws IllegalArgumentException;

    Integer find(Integer find);

    String show() throws NullPointerException;

    void test();
}

