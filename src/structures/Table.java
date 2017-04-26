package structures;

import enums.Place;
import addons.View;

/**
 * Klasa reprezentująca tablice.
 * Korzysta z interfejsu struktur.
 *
 * @author Tobiasz Rumian.
 */
public class Table implements Structure {
    private int[] table = null; //Tablica

    @Override
    public String info() {
        return "Tablica – kontener uporządkowanych danych takiego samego typu,\n" +
                " w którym poszczególne elementy dostępne są za pomocą kluczy (indeksu).\n" +
                " Indeks najczęściej przyjmuje wartości numeryczne.";
    }

    @Override
    public void subtract(Place place, int number) {
        if (table == null || table.length == 0) return;//Jeżeli tablica nie zawiera rekordów, nic nie rób
        if (table.length == 1) { //Sprawdzanie, czy tablica zawiera tylko jeden rekord, jeżeli tak, usuwanie tablicy
            table = null;
            return;
        }
        int[] x = new int[table.length - 1];//Tworzenie tablicy o jeden mniejszej od oryginału
        switch (place) {
            case START:
                for (int i = 1; i < table.length; i++) x[i - 1] = table[i];//Przekopiowywanie tablicy, zaczynając od drugiego rekordu
                break;
            case END:
                for (int i = 0; i < table.length - 1; i++) x[i] = table[i];//Przekopiowywanie tablicy, kończąc o jeden rekord wcześniej
                break;
            case RANDOM:
                int random = View.getRandom(0, table.length);//Generowanie losowego indeksu.
                for (int i = 0; i < random; i++) x[i] = table[i];//Przekopiowywanie tablicy od indeksu 0 do wylosowanego indeksu-1
                for (int i = random; i < x.length; i++) x[i] = table[i + 1];//Przekopiowywanie tablicy od wylosowanego indeksu+1 do końca
                break;
        }
        table = x;//Przypisywanie nowej tablicy w miejsce starej
    }

    @Override
    public void add(Place place, int number) {
        if (table == null || table.length == 0) {//Jeżeli tablica nie istnieje, stwórz nową
            table = new int[1];
            table[0] = number;
            return;
        }
        int[] x = new int[table.length + 1];//Tworzenie tablicy większej o 1
        switch (place) {
            case START:
                for (int i = 0; i < table.length; i++) x[i + 1] = table[i]; //Przekopiowywanie tablicy do nowej o indeksie i+1
                x[0] = number;//Wstawianie nowej liczby na miejsce 0
                break;
            case END:
                for (int i = 0; i < table.length; i++) x[i] = table[i];//Przekopiowywanie tablicy
                x[x.length - 1] = number;//Wstawianie nowej liczby na ostatnie miejsce
                break;
            case RANDOM:
                int random = View.getRandom(0, table.length); //Losowanie indeksu
                for (int i = 0; i < random; i++) x[i] = table[i];//Przekopiowywanie tablicy od indeksu 0 do wylosowanego indeksu-1
                x[random] = number;//Wstawianie nowej liczby
                for (int i = random + 1; i < x.length; i++) x[i] = table[i - 1];//Przekopiowywanie tablicy z indeksów i-1 do i
                break;
        }
        table = x;//Przypisywanie nowej tablicy w miejsce starej
    }

    @Override
    public boolean find(int find) {
        if (table.length > 0)
            for (int t : table)
                if (t == find) return true;
        return false;
    }

    @Override
    public String show() {
        if (table == null || table.length == 0) return "Brak danych";
        StringBuilder sb = new StringBuilder();
        for (int i : table) sb.append("[").append(i).append("]");
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Tablica";
    }

    @Override
    public int size() {
        return table.length;
    }

    @Override
    public void clear() {
        table = null;
    }
}
