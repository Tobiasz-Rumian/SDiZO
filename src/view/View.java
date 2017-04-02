package view;

import enums.Place;
import enums.Task;
import structures.*;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by Tobiasz Rumian on 19.03.2017.
 */
public class View {
    private static Random random = new Random();
    private Boolean kill = false;
    private Table table = new Table();
    private BidirectionalList bidirectionalList = new BidirectionalList();
    private BinaryHeap binaryHeap = new BinaryHeap();
    private NewTree_BST newTree_bst = new NewTree_BST();

    public View() {
        message(messageStart(), false);
        message(messageMainMenu(), false);
        do {
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
        message(message, false);
        Scanner in = new Scanner(System.in);
        String x = in.nextLine();
        Integer selected = Integer.parseInt(x);
        return selected;
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

        Structures structure;
        if (task == Task.TABLE) {
            structure = table;
        } else if (task == Task.LIST) {
            structure = bidirectionalList;
        } else if (task == Task.HEAP) {
            structure = binaryHeap;
        } else if (task == Task.TREE_BST) {
            structure = newTree_bst;
        } else {
            structure = table;
        }
        do {
            message(messageTask(), false);
            Place place = null;
            switch (View.select("Podaj numer zadania:")) {
                case 1: structure.info();
                    break;
                case 2:
                    structure.loadFromFile();
                    structure.show();
                    break;
                case 3: View.message(View.title("odejmowanie"), false);
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
                    structure.subtract(place, null);
                    structure.show();
                    break;
                case 4:
                    View.message(View.title("dodawanie"), false);
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
                    structure.add(place, select("Podaj liczbe ktora chcesz wstawic"));
                    structure.show();
                    break;
                case 5:
                    View.message(View.title("znajdowanie"), false);
                    if (structure.find(View.select("Podaj liczbe")) == -1)
                        message("Liczba nie znajduje się w strukturze", true);
                    else message("Liczba znajduje się w strukturze", false);
                    structure.show();
                    break;
                case 6:
                    structure.show();
                    break;
                case 7:
                    structure.test();
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

}
