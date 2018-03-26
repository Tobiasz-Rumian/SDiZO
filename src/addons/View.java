package addons;

import enums.Place;
import structures.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

import static addons.Settings.*;

/**
 * Klasa reprezentująca widok
 *
 * @author Tobiasz Rumian.
 */
public class View {
    private static Random random = new Random();//Generator pseudolosowy
    private Structure structure; // Struktura, na której odbywają się wszystkie zadania.
    private Results results = new Results();//Obiekt zawierający wyniki testów.
    
    private View() {
        printMessage(Messages.messageStart());
        while (true) {
            printMessage(Messages.messageMainMenu());
            switch (select("Podaj numer zadania:", 0, 5)) {
                case 1: structure = new Table();
                    break;
                case 2: structure = new BidirectionalList();
                    break;
                case 3: structure = new BinaryHeap();
                    break;
                case 4: structure = setBstTree();
                    break;
                case 5: Test.fullTest();
                    return;
                case 0: return;
            }
            selectTask();
        }
    }

    private Structure setBstTree(){
        printMessage("Z równoważeniem? (0-false,1-true)");
        int z = View.select("Podaj numer:", 0, 1);
        Settings.x = z != 0;
        return  new BstTree();
    }
    
    static String title(String title) {
        return "===========" + title.toUpperCase() + "===========\n";
    }


    static int select(String message, Integer min, Integer max) {
        do {
            try {
                printMessage(message);
                Scanner in = new Scanner(System.in);
                int i = Integer.parseInt(in.nextLine());
                if (i <= max && i >= min) return i;
            } catch (NumberFormatException ignored) {
            }
        } while (true);
    }

    static Integer select(String message) {
        while (true){
            try {
                printMessage(message);
                Scanner in = new Scanner(System.in);
                return Integer.parseInt(in.nextLine());
            } catch (NumberFormatException ignored) { }
        }
    }

   
    static void printMessage(String message) {
        System.out.println(message + "\n");
    }
    
    static void printErrorMessage(String message){
        System.err.println(message + "\n");
    }

    /**
     * Funkcja pozwalająca na wybranie zadania wykonywanego na strukturze.
     */
    private void selectTask() {
        while (true) {
            printMessage(Messages.messageTask());
            Place place = Place.NULL;

            switch (View.select("Podaj numer zadania:", 0, 7)) {
                case 1: displayInfo();
                    break;
                case 2: readFromFile();
                    break;
                case 3: deleteFromStructure(place);
                    break;
                case 4: addToStructure(place);
                    break;
                case 5: findInStructure();
                    break;
                case 6: displayStructure();
                    break;
                case 7: test(place);
                    break;
                case 0: return;
            }
        }
    }

    private void test(Place place) {
        clearResults();
        while (true) {
            printMessage(Messages.messageTest());
            int select = View.select("Podaj numer zadania:", 0, 5);
            if (select == 0) break;
            if (select == 1 || select == 2) {
                if (structure.getClass() == Table.class || structure.getClass() == BidirectionalList.class)
                    place = choosePlace("Podaj miejsce, w które chcesz wstawiać");
                else place = Place.NULL;
            }
            Test.test(select, place,structure);
        }
    }

    private void clearResults() {
        results.clear();
    }

    private void displayStructure() {
        printMessage(structure.show());
    }

    private void findInStructure() {
        View.printMessage(View.title("znajdowanie"));
        if (structure.find(View.select("Podaj liczbe")))printMessage("Liczba znajduje się w strukturze");
        else printErrorMessage("Liczba nie znajduje się w strukturze");
        displayStructure();
    }

    private void addToStructure(Place place) {
        View.printMessage(View.title("dodawanie"));
        if(structure.getClass() == Table.class ){
            ((Table)structure).add(selectIndex(), selectValue());
        }
        else if(structure.getClass() == BidirectionalList.class){
            ((BidirectionalList)structure).add(selectIndex(), selectValue());
        }
        else structure.add(place, selectValue());
        displayStructure();
    }

    private Integer selectValue() {
        return select("Podaj wartość");
    }

    private Integer selectIndex() {
        return select("Podaj indeks");
    }

    private void deleteFromStructure(Place place) {
        View.printMessage(View.title("odejmowanie"));
        if(structure.getClass() == Table.class )
            ((Table)structure).subtract(selectIndex());
        else if(structure.getClass() == BidirectionalList.class)
            ((BidirectionalList)structure).subtract(selectValue());
        else structure.subtract(place, selectValue());
        displayStructure();
    }

    private void readFromFile() {
        structure.clear();
        loadFromFile(structure);
        displayStructure();
    }

    private void displayInfo() {
        printMessage(structure.info());
    }

    public static int getRandom(Integer min, Integer max) {

        return random.nextInt(max - min) + min;
    }

    private void loadFromFile(Structure structure) {
        FileChooser fileChooser = new FileChooser();
        if (fileChooser.getPath() == null) return;
        try (Stream<String> stream = Files.lines(Paths.get(fileChooser.getPath()))) {
            ArrayList<String> arrayList = new ArrayList<>();
            stream.forEach(arrayList::add);
            ArrayList<Integer> integers = new ArrayList<>();
            for (int i=1;i<=Integer.parseInt(arrayList.get(0));i++){
                integers.add(Integer.parseInt(arrayList.get(i)));
            }
            integers.forEach(x -> structure.add(Place.END, x));
        } catch (IOException e) {
            printErrorMessage("Wystąpił błąd podczas ładowania pliku");
        }
    }

    private Place choosePlace(String label) {
        Place place = Place.NULL;
        View.printMessage("Gdzie odjac liczbe?");
        View.printMessage("1. Poczatek");
        View.printMessage("2. Koniec");
        View.printMessage("3. Losowo");
        Integer i = View.select(label, 1, 3);
        if (i.equals(1)) place = Place.START;
        else if (i.equals(2)) place = Place.END;
        else if (i.equals(3)) place = Place.RANDOM;
        return place;
    }

    public static void main(String[] args) {
        new View();
    }
}
