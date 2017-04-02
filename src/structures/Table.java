package structures;

import enums.Place;
import view.FileChooser;
import view.View;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by Tobiasz Rumian on 19.03.2017.
 */
public class Table implements Structures {
    private Integer[] table = null;

    @Override
    public String info() {
        return "Tablica – kontener uporządkowanych danych takiego samego typu," +
                " w którym poszczególne elementy dostępne są za pomocą kluczy (indeksu)." +
                " Indeks najczęściej przyjmuje wartości numeryczne.";
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
    public void subtract(Place place, Integer number) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (table == null || table.length == 0) throw new IndexOutOfBoundsException("Tablica jest pusta!");
        if (table.length == 1) {
            table = null;
            return;
        }
        Integer[] x = new Integer[table.length - 1];
        if (place == Place.START) {
            for (Integer i = 1; i < table.length; i++) x[i - 1] = table[i];
        } else if (place == Place.END) {
            for (Integer i = 0; i < table.length - 1; i++) x[i] = table[i];
        } else if (place == Place.RANDOM) {
            Integer random = View.getRandom(0, table.length);
            for (int i = 0; i < random; i++) {
                x[i] = table[i];
            }
            for (int i = random; i < x.length; i++) {
                x[i] = table[i + 1];
            }
        } else throw new IllegalArgumentException("Podano nieprawidłowe miejsce!");
        table = x;
    }

    @Override
    public void add(Place place, Integer number) throws IllegalArgumentException {
        if (table == null || table.length == 0) {
            table = new Integer[1];
            table[0] = number;
            return;
        }
        Integer[] x = new Integer[table.length + 1];
        if (place == Place.START) {
            for (Integer i = 0; i < table.length; i++) {
                x[i + 1] = table[i];
                x[0] = number;
            }
        } else if (place == Place.END) {
            for (Integer i = 0; i < table.length; i++) {
                x[i] = table[i];
            }
            x[x.length - 1] = number;
        } else if (place == Place.RANDOM) {
            Integer random = View.getRandom(0, table.length);
            for (Integer i = 0; i < random; i++) {
                x[i] = table[i];
            }
            x[random] = number;
            for (Integer i = random + 1; i < x.length; i++) {
                x[i] = table[i - 1];
            }
        } else throw new IllegalArgumentException("Podano nieprawidłowe miejsce!");
        table = x;
    }

    @Override
    public Integer find(Integer find){
        if (table.length >= 1) {
            for (Integer i = 0; i < table.length; i++) {
                if (table[i].equals(find))return i;
            }
            return -1;
        } else {
            return -1;
        }
    }

    @Override
    public String show() {
        if (table == null||table.length==0) throw new IndexOutOfBoundsException("Tablica nie zawiera takiego rekordu.");
        StringBuilder sb=new StringBuilder();
        for (Integer i : table) sb.append("[").append(i).append("]");
        return sb.toString();
    }

    @Override
    public void test() {

    }
}
