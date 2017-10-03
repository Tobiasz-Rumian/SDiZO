package test;

import addons.Messages;
import addons.PopulationGenerator;
import addons.Results;
import addons.Settings;
import addons.TimeTracker;
import enums.Place;
import selector.Selector;
import structures.BidirectionalList;
import structures.BstTree;
import structures.Structure;
import structures.Table;
import view.Formatter;
import view.Message;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static addons.Settings.changeSettings;
import static addons.Settings.getHowManyElements;
import static addons.Settings.getHowManyRepeats;
import static addons.Settings.setSettings;

public class Test {
   private static Structure structure; // Struktura, na której odbywają się wszystkie zadania.
   private static Results results = new Results();//Obiekt zawierający wyniki testów.

   /**
    * Funkcja pozwalająca na wykonanie testów na strukturze.
    *
    * @param task  Numer zadania do wykonania.
    * @param place Miejsce dodania/usunięcia ze struktury (wykorzystywane tylko dla list i tablic).
    */

   public static void startTest(int task, Place place, Structure structure) {
      Test.structure = structure;
      startTest(task, place);
   }

   private static void startTest(int task, Place place) {
      TimeTracker tracker = new TimeTracker();

      String label;
      BigDecimal resultTime = new BigDecimal(0);
      PopulationGenerator populationGenerator;
      Message.displayNormal(Messages.messageTest());
      switch (task) {
         case 1://Generuj populację struktury
            for (int i = 0; i < getHowManyRepeats(); i++) {
               Message.displayNormal(Formatter.toProgress(i, getHowManyRepeats()) + "     " +
                       structure.toString() + "  " + getHowManyElements() + "  " +
                       place.toString() + " Dodawanie");

               populationGenerator = new PopulationGenerator();
               if (structure.getClass() == Table.class) {
                  Table table = new Table();
                  table.addAll(populationGenerator.getPopulation());
                  table.subtract(Place.END, 0);
                  int rand = Selector.randomInteger();
                  tracker.start();
                  table.add(place, rand);
                  resultTime = resultTime.add(tracker.getElapsedTime());
               } else {
                  if (place == Place.RANDOM) {
                     for (int j = 0; j < populationGenerator.getPopulation().length - 1; j++) {
                        structure.add(Place.END, populationGenerator.getPopulation()[j]);
                     }
                     int add = populationGenerator.getPopulation()[populationGenerator.getPopulation().length - 1];
                     tracker.start();
                     structure.add(place, add);
                     resultTime = resultTime.add(tracker.getElapsedTime());
                     structure.clear();
                  } else {
                     for (int j = 0; j < populationGenerator.getPopulation().length - 1; j++) {
                        structure.add(place, populationGenerator.getPopulation()[j]);
                     }
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
            Message.displayNormal(resultTime.toString());
            results.add(label, resultTime.longValue());
            break;
         case 2://Usun ze struktury
            for (int i = 0; i < getHowManyRepeats(); i++) {
               Message.displayNormal(Formatter.toProgress(i, getHowManyRepeats()) + "     " +
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
                  for (int k = 0; k < populationGenerator.getPopulation().length; k++) {
                     structure.add(Place.END, populationGenerator.getPopulation()[k]);
                  }
                  int rand = Selector.randomInteger();
                  tracker.start();
                  structure.subtract(place, rand);
                  resultTime = resultTime.add(tracker.getElapsedTime());
                  structure.clear();
               }
            }
            resultTime = resultTime.divide(BigDecimal.valueOf(getHowManyRepeats()), RoundingMode.UP);
            label = structure.toString() + "\t" + "Usuwanie" + "\t" + place.toString() + "\t" +
                    getHowManyElements() + "\t" + getHowManyRepeats();
            Message.displayNormal(resultTime.toString());
            results.add(label, resultTime.longValue());
            break;
         case 3://Wyszukaj w strukturze
            for (int i = 0; i < getHowManyRepeats(); i++) {
               Message.displayNormal(Formatter.toProgress(i, getHowManyRepeats()) + "     " +
                       structure.toString() + "  " + getHowManyElements() + " Wyszukiwanie");

               populationGenerator = new PopulationGenerator();
               if (structure.getClass() == Table.class) {
                  Table table = (Table) structure;
                  table.addAll(populationGenerator.getPopulation());
                  int rand = Selector.randomInteger();
                  tracker.start();
                  table.find(rand);
                  resultTime = resultTime.add(tracker.getElapsedTime());
               } else {
                  for (int k = 0; k < populationGenerator.getPopulation().length; k++) {
                     structure.add(Place.END, populationGenerator.getPopulation()[k]);
                  }
                  int rand = Selector.randomInteger();
                  tracker.start();
                  structure.find(rand);
                  resultTime = resultTime.add(tracker.getElapsedTime());
                  structure.clear();
               }
            }
            resultTime = resultTime.divide(BigDecimal.valueOf(getHowManyRepeats()), RoundingMode.UP);
            label = structure.toString() + "\t" + "Wyszukiwanie" + "\t" + "-" + "\t" +
                    getHowManyElements() + "\t" + getHowManyRepeats();
            Message.displayNormal(resultTime.toString());
            results.add(label, resultTime.longValue());
            break;
         case 4://Ustawienia
            Settings.menuMessage();
            changeSettings();
            break;
         case 5://Pokaż wyniki
            Message.displayNormal(results.show());
            break;
         case 0://Zakończ test
            break;
      }
   }

   /**
    * Funkcja pozwalająca na wykonanie pełnych testów.
    */
   public static void startFullTest() {
      int[] howMany = {2000, 4000, 6000, 8000, 10000};
      Structure[] structures = {new BstTree()};//new Table(),new BidirectionalList(), new BinaryHeap(), new BstTree()
      Place[] places = {Place.START, Place.END, Place.RANDOM};//Place.START, Place.END, Place.RANDOM
      int[] tests = {1, 2, 3};//1, 2, 3
      for (Structure s : structures) {
         Test.structure = s;
         for (int test : tests) {
            if ((Test.structure.getClass() == Table.class && test != 3) || (Test.structure.getClass() == BidirectionalList.class && test != 3)) {
               for (Place place : places) {
                  for (int how : howMany) {
                     setSettings(how, getHowManyRepeats());
                     startTest(test, place);
                  }
                  results.save();
               }
            } else {
               for (int how : howMany) {
                  setSettings(how, getHowManyRepeats());
                  startTest(test, Place.NULL);
               }
               results.save();
            }
         }
      }
      results.save();
      results.clear();
   }

}
