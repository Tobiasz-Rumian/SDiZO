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

/**
 * Klasa reprezentująca widok
 *
 * @author Tobiasz Rumian.
 */
public class View {
    private static Random random = new Random();//Generator pseudolosowy
    private Structure structure; // Struktura, na której odbywają się wszystkie zadania.
    private Results results = new Results();//Obiekt zawierający wyniki testów.

    /**
     * Główna pętla programu, wyświetla główne menu.
     */
    private View() {
        message(Messages.messageStart(), false);
        while (true) {
            message(Messages.messageMainMenu(), false);
            switch (select("Podaj numer zadania:", 0, 5)) {
                case 1: structure = new Table();
                    break;
                case 2: structure = new BidirectionalList();
                    break;
                case 3: structure = new BinaryHeap();
                    break;
                case 4: structure = new BstTree();
                    break;
                case 5: fullTest();
                    break;
                case 0: return;
            }
            selectTask();
        }
    }

    /**
     * Funkcja formatująca tytuł.
     *
     * @param title Tekst do sformatowania.
     * @return Zwraca sformatowany tytuł.
     */
    static String title(String title) {
        return "===========" + title.toUpperCase() + "===========\n";
    }

    /**
     * Funkcja obsługują wybieranie numeru przez użytkownika.
     * Obsługuje wybór z konsoli.
     *
     * @param message Wiadomość do wyświetlenia użytkownikowi.
     * @param min     Minimalna akceptowalna wartość.
     * @param max     Maksymalna akceptowalna wartość.
     * @return Zwraca odpowiedź użytkownika.
     */
    static int select(String message, Integer min, Integer max) {
        do {
            try {
                message(message, false);
                Scanner in = new Scanner(System.in);
                int i = Integer.parseInt(in.nextLine());
                if (i <= max && i >= min) return i;
            } catch (NumberFormatException ignored) {
            }
        } while (true);
    }

    /**
     * Funkcja pozwalająca wyświetlić wiadomość na ekranie.
     *
     * @param message Wiadomość do wyświetlenia.
     * @param error   Jeżeli true, wyświetla wiadomość jako błąd.
     */
    static void message(String message, Boolean error) {
        if (error) System.err.println(message + "\n");
        else System.out.println(message + "\n");
    }

    /**
     * Funkcja pozwalająca na wybranie zadania wykonywanego na strukturze.
     */
    private void selectTask() {
        do {
            message(Messages.messageTask(), false);
            Place place = null;
            switch (View.select("Podaj numer zadania:", 0, 7)) {
                case 1:
                    message(structure.info(), false);
                    break;
                case 2:
                    loadFromFile(structure);
                    message(structure.show(),false);
                    break;
                case 3:
                    View.message(View.title("odejmowanie"), false);
                    Integer number;
                    if (structure.getClass() == Table.class || structure.getClass() == BidirectionalList.class) {
                        place = choosePlace("Podaj liczbe miejsca z ktorego chcesz usunac");
                        number = null;
                    } else number = select("Podaj liczbe ktora chcesz usunąć",Integer.MIN_VALUE, Integer.MAX_VALUE);
                    structure.subtract(place, number);
                    message(structure.show(),false);
                    break;
                case 4:
                    View.message(View.title("dodawanie"), false);
                    if (structure.getClass() == Table.class || structure.getClass() == BidirectionalList.class)
                        place = choosePlace("Podaj liczbe miejsca, w ktore chcesz wstawic");
                    structure.add(place, select("Podaj liczbe ktora chcesz wstawic", Integer.MIN_VALUE, Integer.MAX_VALUE));
                    message(structure.show(), false);
                    break;
                case 5:
                    View.message(View.title("znajdowanie"), false);
                    if (structure.find(View.select("Podaj liczbe", Integer.MIN_VALUE, Integer.MAX_VALUE)))
                        message("Liczba znajduje się w strukturze", false);
                    else message("Liczba nie znajduje się w strukturze", true);
                    message(structure.show(), false);
                    break;
                case 6:
                    message(structure.show(), false);
                    break;
                case 7:
                    results.clear();
                    while (true) {
                        message(Messages.messageTest(), false);
                        int select = View.select("Podaj numer zadania:", 0, 5);
                        if (select == 0) break;
                        if (select == 1 || select == 2) {
                            if (structure.getClass() == Table.class || structure.getClass() == BidirectionalList.class)
                                place = choosePlace("Podaj miejsce, w które chcesz wstawiać");
                            else place = null;
                        }
                        test(select, place);
                    }
                    break;
                case 0:
                    return;
            }
        } while (true);
    }

    /**
     * Funkcja zwracająca wartość pseudolosową z podanego przedziału.
     *
     * @param min Minimalna wartość.
     * @param max Maksymalna wartość.
     * @return Zwraca liczbę pseudolosową z podanego przedziału.
     */
    public static Integer getRandom(Integer min, Integer max) {
        return random.nextInt(max - min) + min;
    }

    /**
     * Funkcja pozwalająca na załadowanie danych z pliku tekstowego do struktury.
     * Wyświetla okno pozwalające na wybór pliku.
     * Usuwa pierwszy wyraz, gdyż według specyfikacji projektowej, pierwsza wartość oznacza ilość elementów.
     *
     * @param structure Struktura, do której mają być załadowane dane.
     */
    private void loadFromFile(Structure structure) {
        FileChooser fileChooser = new FileChooser();
        try (Stream<String> stream = Files.lines(Paths.get(fileChooser.getPath()))) {
            ArrayList<String> arrayList = new ArrayList<>();
            stream.filter(x -> !x.equals("")).forEach(arrayList::add);
            arrayList.remove(0);
            arrayList.forEach(x -> structure.add(Place.END, Integer.parseInt(x)));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    /**
     * Funkcja pozwalająca na wykonanie testów na strukturze.
     */
    private void test(int task, Place place) {
        /*
                "1. Generuj populację struktury\n"+
                "2. Usun ze struktury\n"+
                "3. Wyszukaj w strukturze\n"+
                "4. Ustawienia\n"+
                "0. Zakończ test\n";
         */
        TimeTracker tracker = new TimeTracker();

        String label;
        BigDecimal resultTime = new BigDecimal(0);
        PopulationGenerator populationGenerator;
        message(Messages.messageTest(), false);
        switch (task) {
            case 1:
                //Dziesięć powtórzeń dla usprawnienia pamięci
                for (int x = 0; x < Settings.howManyRepeatsBeforeStart; x++) {
                    message(showProgress(x, Settings.howManyRepeatsBeforeStart), false);
                        populationGenerator = new PopulationGenerator();
                        for (int j = 0; j < Settings.getHowManyElements(); j++)
                            structure.add(place, populationGenerator.getPopulation()[j]);
                        structure.clear();
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
                //Dziesięć powtórzeń dla usprawnienia pamięci
                for (int x = 0; x < Settings.howManyRepeatsBeforeStart; x++) {
                    message(showProgress(x, Settings.howManyRepeatsBeforeStart), false);
                        populationGenerator = new PopulationGenerator();
                        for (int k = 0; k < Settings.getHowManyElements(); k++)
                            structure.add(Place.END, populationGenerator.getPopulation()[k]);
                        for (int j = 0; j < structure.size(); j++) structure.subtract(place, null);
                        structure.clear();
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
                for (int x = 0; x < Settings.howManyRepeatsBeforeStart; x++) {
                    message(showProgress(x, Settings.howManyRepeatsBeforeStart), false);
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
            case 0: break;
        }
    }


    /**
     * Funkcja pozwalająca na wybór, przez użytkownika, miejsca wstawienia danych.
     * Wybór odbywa się w konsoli.
     *
     * @param label Tekst, który ma zostać wyświetlony użytkownikowi.
     * @return Zwraca wybrane miejsce.
     */
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

    /**
     * Funkcja pozwalająca na zobaczenie postępu.
     *
     * @param now Wartość chwilowa.
     * @param end Wartość końcowa.
     * @return Zwraca postęp w postaci paska oraz procentu w postaci ułamka.
     */
    private String showProgress(Integer now, Integer end) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        Integer percent = (int) ((now * 100.0f) / end);
        for (int i = 0; i <= percent; i++) stringBuilder.append("=");
        for (int i = 0; i <= 100 - percent; i++) stringBuilder.append(" ");
        stringBuilder.append("]");
        stringBuilder.append(" ").append(percent).append("%");
        return stringBuilder.toString();
    }

    /**
     * Funkcja pozwalająca na wykonanie pełnych testów.
     */
    private void fullTest() {
        int[] how = {1000, 2000, 4000, 8000, 16000};
        Structure[] str = {new Table(), new BidirectionalList(), new BinaryHeap(), new BstTree()};
        Place[] p = {Place.START, Place.END, Place.RANDOM};
        int[] t ={1,2,3};
        for (Structure s : str) {
            structure = s;
            for(int i:t){
                if (structure.getClass() == Table.class || structure.getClass() == BidirectionalList.class) {
                    for (Place pl : p) {
                        for (int h : how) {
                            Settings.setSettings(h, 100);
                            message(structure.toString() + "\n" + Settings.getHowManyElements(), false);
                            test(i, pl);
                        }
                        results.save();
                    }
                } else {
                    for (int h : how) {
                        Settings.setSettings(h, 100);
                        message(structure.toString() + "\n" + Settings.getHowManyElements(), false);
                        test(i, null);
                    }
                    results.save();
                }
            }

        }
        results.save();
        results.clear();
    }

    public static void main(String[] args) {
        new View();
    }
}