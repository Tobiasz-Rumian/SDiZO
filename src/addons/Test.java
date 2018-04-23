package addons;

import enums.Place;
import structures.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import static addons.Settings.*;
import static addons.View.printMessage;

public class Test {
    private static Random random = new Random();
    private static Results results = new Results();

    public static void test(int task, Place place, Structure structure) {
        TimeTracker tracker = new TimeTracker();
        String label;
        BigDecimal resultTime = new BigDecimal(0);
        PopulationGenerator populationGenerator;
        printMessage(Messages.messageTest());
        switch (task) {
            case 1://Generuj populację struktury
                for (int i = 0; i < getHowManyRepeats(); i++) {
                    printMessage(showProgress(i, getHowManyRepeats()) + "     " +
                            structure.toString() + "  " + getHowManyElements() + "  " +
                            place.toString() + " Dodawanie");

                    populationGenerator = new PopulationGenerator();
                    if (structure.getClass() == Table.class) {
                        Table table = new Table();
                        table.addAll(populationGenerator.getPopulation());
                        table.subtract(Place.END, 0);
                        int rand = random.nextInt();
                        tracker.start();
                        table.add(place, rand);
                        resultTime = resultTime.add(tracker.getElapsedTime());
                    } else {
                        if (place == Place.RANDOM) {
                            for (int j = 0; j < populationGenerator.getPopulation().length - 1; j++)
                                structure.add(Place.END, populationGenerator.getPopulation()[j]);
                            int add = populationGenerator.getPopulation()[populationGenerator.getPopulation().length - 1];
                            tracker.start();
                            structure.add(place, add);
                            resultTime = resultTime.add(tracker.getElapsedTime());
                            structure.clear();
                        } else {
                            for (int j = 0; j < populationGenerator.getPopulation().length - 1; j++)
                                structure.add(place, populationGenerator.getPopulation()[j]);
                            int add = populationGenerator.getPopulation()[populationGenerator.getPopulation().length - 1];
                            tracker.start();
                            structure.add(place, add);
                            resultTime = resultTime.add(tracker.getElapsedTime());
                            structure.clear();
                        }
                    }
                }
                resultTime = resultTime.divide(BigDecimal.valueOf(getHowManyRepeats()), RoundingMode.UP);
                label = structure.toString() + "\t" + "Dodawanie" + "\t" + place.toString() + "\t" + getHowManyElements() + "\t" + getHowManyRepeats();
                printMessage(resultTime.toString());
                results.add(label, resultTime.longValue());
                break;
            case 2://Usun ze struktury
                for (int i = 0; i < getHowManyRepeats(); i++) {
                    printMessage(showProgress(i, getHowManyRepeats()) + "     " +
                            structure.toString() + "  " + getHowManyElements() + "  " +
                            place.toString() + " Odejmowanie");

                    populationGenerator = new PopulationGenerator();
                    if (structure.getClass() == Table.class) {
                        Table table = new Table();
                        table.addAll(populationGenerator.getPopulation());
                        tracker.start();
                        table.subtract(place, 0);
                        resultTime = resultTime.add(tracker.getElapsedTime());
                    } else {
                        for (int k = 0; k < populationGenerator.getPopulation().length; k++)
                            structure.add(Place.END, populationGenerator.getPopulation()[k]);
                        int rand = random.nextInt();
                        tracker.start();
                        structure.subtract(place, rand);
                        resultTime = resultTime.add(tracker.getElapsedTime());
                        structure.clear();
                    }
                }
                resultTime = resultTime.divide(BigDecimal.valueOf(getHowManyRepeats()), RoundingMode.UP);
                label = structure.toString() + "\t" + "Usuwanie" + "\t" + place.toString() + "\t" +
                        getHowManyElements() + "\t" + getHowManyRepeats();
                printMessage(resultTime.toString());
                results.add(label, resultTime.longValue());
                break;
            case 3://Wyszukaj w strukturze
                for (int i = 0; i < getHowManyRepeats(); i++) {
                    printMessage(showProgress(i, getHowManyRepeats()) + "     " +
                            structure.toString() + "  " + getHowManyElements() + " Wyszukiwanie");

                    populationGenerator = new PopulationGenerator();
                    if (structure.getClass() == Table.class) {
                        Table table = (Table) structure;
                        table.addAll(populationGenerator.getPopulation());
                        int rand = random.nextInt();
                        tracker.start();
                        table.find(rand);
                        resultTime = resultTime.add(tracker.getElapsedTime());
                    } else {
                        for (int k = 0; k < populationGenerator.getPopulation().length; k++)
                            structure.add(Place.END, populationGenerator.getPopulation()[k]);
                        int rand = random.nextInt();
                        tracker.start();
                        structure.find(rand);
                        resultTime = resultTime.add(tracker.getElapsedTime());
                        structure.clear();
                    }
                }
                resultTime = resultTime.divide(BigDecimal.valueOf(getHowManyRepeats()), RoundingMode.UP);
                label = structure.toString() + "\t" + "Wyszukiwanie" + "\t" + "-" + "\t" +
                        getHowManyElements() + "\t" + getHowManyRepeats();
                printMessage(resultTime.toString());
                results.add(label, resultTime.longValue());
                break;
            case 4://Ustawienia
                printMessage(Settings.message());
                changeSettings();
                break;
            case 5://Pokaż wyniki
                printMessage(results.show());
                break;
            case 0://Zakończ test.txt
                break;
        }
    }

    private static String showProgress(Integer now, Integer end) {
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
    public static void fullTest() {
        int[] howMany = {100000, 200000, 300000, 400000, 500000, 600000, 700000, 800000, 900000, 1000000};
        Structure[] structures = {new Table(), new BidirectionalList(), new BinaryHeap(), new BstTree()};//new Table(),new BidirectionalList(), new BinaryHeap(), new BstTree()
        Place[] places = {Place.START, Place.END, Place.RANDOM};
        int[] tests = {1, 2, 3};// Dodawanie, odejmowanie, szukanie


        for (Structure structure : structures) {
            for (int test : tests) {
                if ((structure.getClass() == Table.class && test != 3) || (structure.getClass() == BidirectionalList.class && test != 3)) {
                    for (Place place : places) {
                        for (int how : howMany) {
                            setSettings(how, getHowManyRepeats());
                            test(test, place, structure);
                        }
                        results.save();
                    }
                } else {
                    for (int how : howMany) {
                        setSettings(how, getHowManyRepeats());
                        test(test, Place.NULL, structure);
                    }
                    results.save();
                }
            }
        }
        results.save();
        results.clear();
    }
}
