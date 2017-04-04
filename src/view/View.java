package view;

import enums.Place;
import enums.Task;
import structures.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Created by Tobiasz Rumian on 19.03.2017.
 */
public class View {
    private static Random random = new Random();
    private Boolean kill = false;
    private Table table = new Table();
    private BidirectionalList bidirectionalList = new BidirectionalList();
    private BinaryHeap binaryHeap = new BinaryHeap();
    private BstTree bstTree_ = new BstTree();
    private RedBlackTree redBlackTree = new RedBlackTree();
    public View() {
        mainMenu();
    }

    private void mainMenu() {
        message(messageStart(), false);
        do {
            message(messageMainMenu(), false);
            selectFromStartMenu(select("Podaj numer zadania:"));
        } while (!kill);
    }

    private String messageStart() {
        return "Witaj " + System.getProperty("user.name") + "." + "\n" +
                "Uruchomiles projekt nr 1 autorstwa Tobiasza Rumiana." + "\n" +
                "Rozsiadz sie wygodnie i wybierz co chcesz zrobic.";
    }

    private String messageMainMenu() {
        return title("menu glowne") +
                "1. Tablica" + "\n" +
                "2. Lista dwukierunkowa" + "\n" +
                "3. Kopiec binarny" + "\n" +
                "4. Drzewo BST" + "\n" +
                "5. Drzewo czerwono czarne" + "\n" +
                "0. Wyjscie";
    }

    public static String messageTask() {
        return title("wybor zadania") +
                "1. info" + "\n" +
                "2. Wczytaj z pliku" + "\n" +
                "3. Usun" + "\n" +
                "4. Dodaj" + "\n" +
                "5. Znajdz" + "\n" +
                "6. Wyswietl" + "\n" +
                "7. Test" + "\n" +
                "0. wyjscie";
    }

    public static String title(String title) {
        return "===========" + title.toUpperCase() + "===========\n";
    }

    public static Integer select(String message) {
        do {
            try {
                message(message, false);
                Scanner in = new Scanner(System.in);
                return Integer.parseInt(in.nextLine());
            } catch (NumberFormatException ignored) {
            }
        } while (true);
    }

    public static void message(String message, Boolean error) {
        if (error) System.err.println(message + "\n");
        if (!error) System.out.println(message + "\n");
    }

    private void selectFromStartMenu(Integer selected) {
        switch (selected) {
            case 1: selectTask(Task.TABLE);
                break;
            case 2: selectTask(Task.LIST);
                break;
            case 3: selectTask(Task.HEAP);
                break;
            case 4: selectTask(Task.TREE_BST);
                break;
            case 5: selectTask(Task.TREE_BW);
                break;
            case 0: exit();
                break;
            default: message("Wybierz odpowiedni numer!", true);
        }
    }

    private void exit() {
        kill();
    }


    private void selectTask(Task task) {
        Boolean kill = false;
        Structure structure;
        if (task == Task.TABLE) {
            structure = table;
        } else if (task == Task.LIST) {
            structure = bidirectionalList;
        } else if (task == Task.HEAP) {
            structure = binaryHeap;
        } else if (task == Task.TREE_BST) {
            structure = bstTree_;
        } else {
            structure = redBlackTree;
        }
        do {
            message(messageTask(), false);
            Place place = null;
            switch (View.select("Podaj numer zadania:")) {
                case 1: structure.info();
                    break;
                case 2:
                    loadFromFile(structure);
                    structure.show();
                    break;
                case 3: View.message(View.title("odejmowanie"), false);
                    if (structure.getClass() != BinaryHeap.class) {
                        View.message("Gdzie odjac liczbe?", false);
                        View.message("1. Poczatek", false);
                        View.message("2. Koniec", false);
                        View.message("3. Losowo", false);

                        do {
                            switch (View.select("Podaj liczbe miejsca z ktorego chcesz usunac")) {
                                case 1: place = Place.START;
                                    break;
                                case 2: place = Place.END;
                                    break;
                                case 3: place = Place.RANDOM;
                                    break;
                                default: message("Nieprawidłowy wybór!", true);
                            }
                        } while (place == null);
                    }
                    structure.subtract(place, null);
                    structure.show();
                    break;
                case 4:
                    View.message(View.title("dodawanie"), false);
                    if (structure.getClass() != BinaryHeap.class) {
                        View.message("Gdzie wstawic liczbe?", false);
                        View.message("1. Poczatek", false);
                        View.message("2. Koniec", false);
                        View.message("3. Losowo", false);
                        do {
                            switch (View.select("Podaj liczbe miejsca w ktore chcesz wstawic")) {
                                case 1: place = Place.START;
                                    break;
                                case 2: place = Place.END;
                                    break;
                                case 3: place = Place.RANDOM;
                                    break;
                                default: message("Nieprawidłowy wybór!", true);
                            }
                        } while (place == null);
                    }
                    structure.add(place, select("Podaj liczbe ktora chcesz wstawic"));
                    message(structure.show(),false);
                    break;
                case 5:
                    View.message(View.title("znajdowanie"), false);
                    if (structure.find(View.select("Podaj liczbe")) == -1)
                        message("Liczba nie znajduje się w strukturze", true);
                    else message("Liczba znajduje się w strukturze", false);
                    message(structure.show(),false);
                    break;
                case 6:
                    structure.show();
                    break;
                case 7:
                    test(structure);
                    structure.show();
                    break;
                case 0: kill = true;
                    break;
                default: View.message("Wybierz odpowiedni numer!", true);
            }
        } while (!kill);
    }

    private void kill() {
        kill = true;
    }

    public static Integer getRandom(Integer min, Integer max) {
        return random.nextInt(max - min) + min;
    }

    private void loadFromFile(Structure structure){
        FileChooser fileChooser = new FileChooser();
        try (Stream<String> stream = Files.lines(Paths.get(fileChooser.getPath()))) {
            ArrayList<String> arrayList = new ArrayList<>();
            stream.forEach(arrayList::add);
            arrayList.remove(0);
            arrayList.forEach(x -> structure.add(Place.END,Integer.parseInt(x)));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    private void test(Structure structure){

    }
}
