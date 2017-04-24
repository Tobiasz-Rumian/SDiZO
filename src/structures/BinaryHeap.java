package structures;

import enums.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentująca tablice.
 * Korzysta z interfejsu struktur.
 *
 * @author Tobiasz Rumian.
 */
public class BinaryHeap implements Structure {
    private static final int length = 100000; //przechowuje informacje o wielkości całej tablicy
    private int heapSize = 0; //przechowuje informacje o wielkości kopca
    private static final Integer[] heapTable = new Integer[length]; //tablica przechowująca kopiec
    /*
    numer lewego syna = 2k + 1
    numer prawego syna = 2k + 2
    numer ojca = [(k - 1) / 2], dla k > 0
    lewy syn istnieje, jeśli 2k + 1 < n
    prawy syn istnieje, jeśli 2k + 2 < n
    węzeł k jest liściem, jeśli 2k + 2 > n
     */
    @Override
    public String info() {
        return "Tablicowa struktura danych reprezentująca drzewo binarne,\n" +
                " którego wszystkie poziomy z wyjątkiem ostatniego muszą być pełne.\n" +
                " W przypadku, gdy ostatni poziom drzewa nie jest pełny,\n" +
                " liście ułożone są od lewej do prawej strony drzewa.";
    }

    @Override
    public void subtract(Place place, int number) throws IllegalArgumentException, IndexOutOfBoundsException {
        /* odkomentuj aby odejmować konkretne liczby od kopca
        int counter = 0;
        for (int i : heapTable) {
            if(i==null)return;
            if (i == number) break;
            counter++;
        }
        heapTable[counter] = heapTable[heapSize - 1];
        heapSize--;
        heapifyDown(counter);
        */
        //Dla usuwania korzenia
        if(heapSize==0)return;
        if(heapSize==1){
            clear();
            return;
        }
        heapTable[0]=heapTable[heapSize-1];
        heapSize--;
        heapifyDown(0);
    }

    @Override
    public void add(Place place, int number) throws IllegalArgumentException {
        heapTable[heapSize] = number;
        if (heapSize == 0) {
            heapSize++;
            return;
        }
        heapifyUp(heapSize);
        heapSize++;
    }

    @Override
    public boolean find(int find) {
        if (heapSize==0) return false;
        for (Integer i : heapTable){
            if(i==null) return false;
            if (i==find) return true;
        }
        return false;
    }

    @Override
    public String show() {
        StringBuilder stringBuilder = new StringBuilder();
        List<List<String>> lines = new ArrayList<>();
        List<Integer> level = new ArrayList<>();
        List<Integer> next = new ArrayList<>();
        level.add(0);
        int nn = 1;
        int widest = 0;
        while (nn != 0) {
            List<String> line = new ArrayList<>();
            nn = 0;
            for (Integer n : level) {
                if (n == null || n >= heapSize) {
                    line.add(null);
                    next.add(null);
                    next.add(null);
                } else {
                    String aa =Integer.toString(heapTable[n]);
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();
                    next.add((2 * n) + 1);
                    next.add((2 * n) + 2);
                    if (((2 * n) + 1) < heapSize) nn++;
                    if (((2 * n) + 2) < heapSize) nn++;
                }
            }
            if (widest % 2 == 1) widest++;
            lines.add(line);
            List<Integer> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }
        int perPiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perPiece / 2f) - 1;
            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {
                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) c = (line.get(j) != null) ? '┴' : '┘';
                        else if (j < line.size() && line.get(j) != null) c = '└';
                    }
                    stringBuilder.append(c);
                    // lines and spaces
                    if (line.get(j) == null)
                        for (int k = 0; k < perPiece - 1; k++)
                            stringBuilder.append(" ");
                    else {
                        for (int k = 0; k < hpw; k++) {
                            stringBuilder.append(j % 2 == 0 ? " " : "─");
                        }
                        stringBuilder.append(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            stringBuilder.append(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                stringBuilder.append("\n");
            }
            // print line of numbers
            for (String f : line) {
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perPiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perPiece / 2f - f.length() / 2f);
                // a number
                for (int k = 0; k < gap1; k++) {
                    stringBuilder.append(" ");
                }
                stringBuilder.append(f);
                for (int k = 0; k < gap2; k++) {
                    stringBuilder.append(" ");
                }
            }
            stringBuilder.append("\n");
            perPiece /= 2;
        }
        return stringBuilder.toString();
    }

    @Override
    public int size() {
        return heapSize;
    }

    @Override
    public void clear() {
        heapSize = 0;
    }

    @Override
    public String toString(){
        return "Kopiec binarny";
    }

    /**
     * Funkcja układająca kopiec po dodaniu nowego elementu.
     * @param index indeks nowego elementu.
     */
    private void heapifyUp(int index) {
        if (index == 0) return;
        int value = heapTable[index];
        int parentIndex = (index - 1) / 2;
        if (value >= heapTable[parentIndex]) {
            heapTable[index] = heapTable[parentIndex];
            heapTable[parentIndex] = value;
        }
        heapifyUp(parentIndex);
    }
    /**
     * Funkcja układająca kopiec po usunięciu elementu.
     * @param index indeks usuwanego elementu.
     */
    private void heapifyDown(int index) {
        if (((2 * index) + 2) > heapSize) return;
        int value = heapTable[index];
        int leftChildIndex = (2 * index) + 1;
        int rightChildIndex = (2 * index) + 2;
        int biggestChild = Integer.max(heapTable[leftChildIndex], heapTable[rightChildIndex]);
        if (biggestChild==heapTable[leftChildIndex]) biggestChild = leftChildIndex;
        if (biggestChild==heapTable[rightChildIndex]) biggestChild = rightChildIndex;
        if (value <= heapTable[biggestChild]) {
            heapTable[index] = heapTable[biggestChild];
            heapTable[biggestChild] = value;
        }
        heapifyDown(biggestChild);
    }


}
