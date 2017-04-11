package view;

import addons.*;
import enums.Place;
import enums.Task;
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

/**
 * Created by Tobiasz Rumian on 19.03.2017.
 */
public class View {
    private static Random random = new Random();
    private Boolean kill = false;
    private Table table = new Table();
    private BidirectionalList bidirectionalList = new BidirectionalList();
    private BinaryHeap binaryHeap = new BinaryHeap();
    private BstTree bstTree = new BstTree();
    private RedBlackTree1 redBlackTree = new RedBlackTree1();
    private Results results = new Results();

    public View() {
        mainMenu();
    }

    private void mainMenu() {
        message(Messages.messageStart(), false);
        do {
            message(Messages.messageMainMenu(), false);
            selectFromStartMenu(select("Podaj numer zadania:", 0, 5));
        } while (!kill);
    }

    public static String title(String title) {
        return "===========" + title.toUpperCase() + "===========\n";
    }

    public static Integer select(String message, Integer min, Integer max) {
        do {
            try {
                message(message, false);
                Scanner in = new Scanner(System.in);
                Integer i = Integer.parseInt(in.nextLine());
                if (i <= max && i >= min) return i;
            } catch (NumberFormatException ignored) {
            }
        } while (true);
    }

    public static void message(String message, Boolean error) {
        if (error) System.err.println(message + "\n");
        if (!error) System.out.println(message + "\n");
    }
    public static void printOnConsole(String string){
        System.out.print(string);
    }

    private void selectFromStartMenu(Integer selected) {
        if (selected.equals(1)) selectTask(Task.TABLE);
        else if (selected.equals(2)) selectTask(Task.LIST);
        else if (selected.equals(3)) selectTask(Task.HEAP);
        else if (selected.equals(4)) selectTask(Task.TREE_BST);
        else if (selected.equals(5)) selectTask(Task.TREE_BW);
        else if (selected.equals(0)) exit();
        else message("Wybierz odpowiedni numer!", true);
    }

    private void exit() {
        kill();
    }


    private void selectTask(Task task) {
        Boolean kill = false;
        Structure structure;
        if (task == Task.TABLE) structure = table;
        else if (task == Task.LIST) structure = bidirectionalList;
        else if (task == Task.HEAP) structure = binaryHeap;
        else if (task == Task.TREE_BST) structure = bstTree;
        else structure = redBlackTree;
        do {
            message(Messages.messageTask(), false);
            Place place = null;
            switch (View.select("Podaj numer zadania:", 0, 7)) {
                case 1:
                    message(structure.info(), false);
                    break;
                case 2:
                    loadFromFile(structure);
                    structure.show();
                    break;
                case 3:
                    View.message(View.title("odejmowanie"), false);
                    if (structure.getClass() != BinaryHeap.class)
                        place = choosePlace("Podaj liczbe miejsca z ktorego chcesz usunac");
                    structure.subtract(place, null);
                    structure.show();
                    break;
                case 4:
                    View.message(View.title("dodawanie"), false);
                    if (structure.getClass() != BinaryHeap.class)
                        choosePlace("Podaj liczbe miejsca w ktore chcesz wstawic");
                    structure.add(place, select("Podaj liczbe ktora chcesz wstawic", Integer.MIN_VALUE, Integer.MAX_VALUE));
                    message(structure.show(), false);
                    break;
                case 5:
                    View.message(View.title("znajdowanie"), false);
                    if (structure.find(View.select("Podaj liczbe", Integer.MIN_VALUE, Integer.MAX_VALUE)) == -1)
                        message("Liczba nie znajduje się w strukturze", true);
                    else message("Liczba znajduje się w strukturze", false);
                    message(structure.show(), false);
                    break;
                case 6:
                    message(structure.show(),false);
                    break;
                case 7:
                    results.clear();
                    test(structure);
                    structure.show();
                    break;
                case 0:
                    kill = true;
                    break;
            }
        } while (!kill);
    }

    private void kill() {
        kill = true;
    }

    public static Integer getRandom(Integer min, Integer max) {
        return random.nextInt(max - min) + min;
    }

    private void loadFromFile(Structure structure) {
        FileChooser fileChooser = new FileChooser();
        try (Stream<String> stream = Files.lines(Paths.get(fileChooser.getPath()))) {
            ArrayList<String> arrayList = new ArrayList<>();
            stream.filter(x->!x.equals("")).forEach(arrayList::add);
            arrayList.remove(0);
            arrayList.forEach(x -> structure.add(Place.END, Integer.parseInt(x)));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    private void test(Structure structure) {
        /*
                "1. Generuj populację struktury\n"+
                "2. Usun ze struktury\n"+
                "3. Wyszukaj w strukturze\n"+
                "4. Ustawienia\n"+
                "0. Zakończ test\n";
         */
        Integer select;
        TimeTracker tracker = new TimeTracker();
        Boolean killTest = false;
        Place place;
        String label;
        BigDecimal resultTime = new BigDecimal(0);
        PopulationGenerator populationGenerator;
        message(Messages.messageTest(), false);

        while (!killTest) {
            message(Messages.messageTest(), false);
            select = View.select("Podaj numer zadania:", 0, 5);
            switch (select) {
                case 1:
                    if (structure.getClass() == Table.class || structure.getClass() == BidirectionalList.class)
                        place = choosePlace("Podaj miejsce, w które chcesz wstawiać");
                    else place = null;
                    //Dziesięć powtórzeń dla usprawnienia pamięci
                    for (int x = 0; x < 10; x++) {
                        message(showProgress(x, 10), false);
                        for (int i = 0; i < Settings.getHowManyRepeats(); i++) {
                            populationGenerator = new PopulationGenerator();
                            for (int j = 0; j < Settings.getHowManyElements(); j++)
                                structure.add(place, populationGenerator.getPopulation()[j]);
                            structure.clear();
                        }
                    }
                    //Oficjalny test
                    for (int i = 0; i < Settings.getHowManyRepeats(); i++) {
                        message(showProgress(i, Settings.getHowManyRepeats()), false);
                        populationGenerator = new PopulationGenerator();
                        tracker.start();
                        for (int j = 0; j < Settings.getHowManyElements(); j++)
                            structure.add(place, populationGenerator.getPopulation()[j]);
                        resultTime = resultTime.add(BigDecimal.valueOf(tracker.getElapsedTime()));
                        structure.clear();
                    }
                    resultTime = resultTime.divide(BigDecimal.valueOf(Settings.getHowManyRepeats()), RoundingMode.UP);
                    label = structure.toString() + "\t" + "Dodawanie" + "\t" + Settings.getHowManyElements() + "\t" + Settings.getHowManyRepeats();
                    message(resultTime.toString(), false);
                    results.add(label, resultTime.longValue());
                    break;
                case 2:
                    if (structure.getClass() == Table.class || structure.getClass() == BidirectionalList.class)
                        place = choosePlace("Podaj miejsce, z którego chcesz usuwać");
                    else place = null;
                    //Dziesięć powtórzeń dla usprawnienia pamięci
                    for (int x = 0; x < 10; x++) {
                        message(showProgress(x, 10), false);
                        for (int i = 0; i < Settings.getHowManyRepeats(); i++) {
                            populationGenerator = new PopulationGenerator();
                            for (int k = 0; k < Settings.getHowManyElements(); k++)
                                structure.add(Place.END, populationGenerator.getPopulation()[k]);
                            for (int j = 0; j < structure.size(); j++) structure.subtract(place, null);
                            structure.clear();
                        }
                    }
                    //Oficjalny test
                    for (int i = 0; i < Settings.getHowManyRepeats(); i++) {
                        message(showProgress(i, Settings.getHowManyRepeats()), false);
                        populationGenerator = new PopulationGenerator();
                        for (int k = 0; k < Settings.getHowManyElements(); k++)
                            structure.add(Place.END, populationGenerator.getPopulation()[k]);
                        tracker.start();
                        for (int j = 0; j < structure.size(); j++) structure.subtract(place, null);
                        resultTime = resultTime.add(BigDecimal.valueOf(tracker.getElapsedTime()));
                        structure.clear();
                    }
                    resultTime = resultTime.divide(BigDecimal.valueOf(Settings.getHowManyRepeats()), RoundingMode.UP);
                    label = structure.toString() + "\t" + "Usuwanie" + "\t" + Settings.getHowManyElements() + "\t" + Settings.getHowManyRepeats();
                    message(resultTime.toString(), false);
                    results.add(label, resultTime.longValue());
                    break;
                case 3:
                    //Dziesięć powtórzeń dla usprawnienia pamięci
                    for (int x = 0; x < 10; x++) {
                        message(showProgress(x, 10), false);
                        populationGenerator = new PopulationGenerator();
                        for (int k = 0; k < Settings.getHowManyElements(); k++)
                            structure.add(Place.END, populationGenerator.getPopulation()[k]);
                        populationGenerator = new PopulationGenerator();
                        for (int j = 0; j < Settings.getHowManyElements(); j++)
                            structure.find(populationGenerator.getPopulation()[j]);
                        structure.clear();
                    }
                    //Oficjalny test
                    for (int i = 0; i < Settings.getHowManyRepeats(); i++) {
                        message(showProgress(i, Settings.getHowManyRepeats()), false);
                        populationGenerator = new PopulationGenerator();
                        for (int k = 0; k < Settings.getHowManyElements(); k++)
                            structure.add(Place.END, populationGenerator.getPopulation()[k]);
                        populationGenerator = new PopulationGenerator();
                        tracker.start();
                        for (int j = 0; j < Settings.getHowManyElements(); j++)
                            structure.find(populationGenerator.getPopulation()[j]);
                        resultTime = resultTime.add(BigDecimal.valueOf(tracker.getElapsedTime()));
                        structure.clear();
                    }
                    resultTime = resultTime.divide(BigDecimal.valueOf(Settings.getHowManyRepeats()), RoundingMode.UP);
                    label = structure.toString() + "\t" + "Wyszukiwanie" + "\t" + Settings.getHowManyElements() + "\t" + Settings.getHowManyRepeats();
                    message(resultTime.toString(), false);
                    results.add(label, resultTime.longValue());
                    break;
                case 4:
                    Settings.message();
                    Settings.changeSettings();
                    break;
                case 5:
                    message(results.show(), false);
                    break;
                case 0:
                    killTest = true;
                    results.save();
                    break;
            }
        }
    }

    private Place choosePlace(String label) {
        Place place = null;
        View.message("Gdzie odjac liczbe?", false);
        View.message("1. Poczatek", false);
        View.message("2. Koniec", false);
        View.message("3. Losowo", false);
        Integer i = View.select(label, 1, 3);
        if (i.equals(1)) place = Place.START;
        else if (i.equals(2)) place = Place.END;
        else if (i.equals(3)) place = Place.RANDOM;
        return place;
    }

    private String showProgress(Integer now, Integer end) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        Integer percent = (int) ((now * 100.0f) / end);
        for (int i = 0; i <= percent; i++) stringBuilder.append("=");
        for (int i = 0; i <= 100 - percent; i++) stringBuilder.append(" ");
        stringBuilder.append("]");
        stringBuilder.append(" ").append(percent).append("/").append(100);
        return stringBuilder.toString();
    }
}
