package structures;

import enums.Place;

import java.util.NoSuchElementException;

/**
 * Created by Tobiasz Rumian on 19.03.2017.
 */
public class BinaryHeap implements Structure {
    private static final Integer lenght = 100000; //przechowuje informacje o wielkości całej tablicy
    private Integer heapSize; //przechowuje informacje o wielkości kopca
    private static final Integer[] heapTable = new Integer[lenght]; //tablica przechowująca kopiec
    private static final Integer heapChildren = 2; //ilość dzieci przypadająca na rodzica

    public BinaryHeap() {
        heapSize = 0;
    }

    @Override
    public String info() {
        return "Tablicowa struktura danych reprezentująca drzewo binarne,\n" +
                " którego wszystkie poziomy z wyjątkiem ostatniego muszą być pełne.\n" +
                " W przypadku, gdy ostatni poziom drzewa nie jest pełny,\n" +
                " liście ułożone są od lewej do prawej strony drzewa.";
    }

    @Override
    public void subtract(Place place, Integer number) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (isEmpty()) throw new NoSuchElementException("Underflow Exception");
        try {
            heapTable[find(number)] = heapTable[heapSize - 1];
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        heapSize--;
        heapifyDown(number);
    }

    @Override
    public void add(Place place, Integer number) throws IllegalArgumentException {
        if (isFull()) throw new NoSuchElementException("Overflow Exception");
        heapTable[heapSize++] = number;
        heapifyUp(heapSize - 1);
        show();
    }

    @Override
    public Integer find(Integer find){
        if(this.isEmpty()) return -1;
        Integer counter = 0;
        for (Integer i : heapTable) {
            if (i.equals(find))return counter;
            counter++;
        }
        return -1;
    }

    @Override
    public String show(){
        if (heapTable.length==0) throw new IndexOutOfBoundsException("Tablica nie zawiera rekordu.");
        StringBuilder sb=new StringBuilder();
        for (Integer i : heapTable)if(i!=null)sb.append("[").append(i).append("]");
        return sb.toString();
        //TODO: Wyświetlanie kopca w postaci graficznej
    }

    private boolean isEmpty() {
        return heapSize == 0;
    }

    private boolean isFull() {
        return heapSize == heapTable.length;
    }

    /**
     * Function to  get index parent of i
     **/
    private int parent(int i) {
        return (i - 1) / heapChildren;
    }

    /**
     * Function to get index of k th child of i
     **/
    private int kthChild(int i, int k) {
        return heapChildren * i + k;
    }

    /**
     * Function heapifyUp
     **/
    private void heapifyUp(int childInd) {
        int tmp = heapTable[childInd];
        while (childInd > 0 && tmp < heapTable[parent(childInd)]) {
            heapTable[childInd] = heapTable[parent(childInd)];
            childInd = parent(childInd);
        }
        heapTable[childInd] = tmp;
    }

    /**
     * Function heapifyDown
     **/
    private void heapifyDown(int ind) {
        int child;
        int tmp = heapTable[ind];
        while (kthChild(ind, 1) < heapSize) {
            child = minChild(ind);
            if (heapTable[child] < tmp)
                heapTable[ind] = heapTable[child];
            else
                break;
            ind = child;
        }
        heapTable[ind] = tmp;
    }
    /** Function to get smallest child **/
    private int minChild(int ind)
    {
        int bestChild = kthChild(ind, 1);
        int k = 2;
        int pos = kthChild(ind, k);
        while ((k <= heapChildren) && (pos < heapSize))
        {
            if (heapTable[pos] < heapTable[bestChild])
                bestChild = pos;
            pos = kthChild(ind, k++);
        }
        return bestChild;
    }
}

