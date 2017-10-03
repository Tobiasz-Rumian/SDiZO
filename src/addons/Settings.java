package addons;

import selector.Selector;
import view.Formatter;
import view.Message;

/**
 * Ustawienia
 *
 * @author Tobiasz Rumian
 */
public class Settings {
   public static boolean x = false;
   private static Integer howManyElements = 1000;
   private static Integer howManyRepeats = 1000;

   public static Integer getHowManyElements() {
      return howManyElements;
   }

   public static Integer getHowManyRepeats() {
      return howManyRepeats;
   }

   public static String menuMessage() {
      return Formatter.toTitle("ustawienia") + "\n" +
              "Ilosc elementów: " + howManyElements + "\n" +
              "Ilosc powtórzeń: " + howManyRepeats + "\n";
   }

   public static void changeSettings() {
      Message.displayNormal("Co chcesz zmienić?");
      Message.displayNormal("1. Ilość elementów\n" + "2. Ilość powtórzeń\n");
      int i = Selector.integerFromRange("Wybierz liczbę", 1, 2);
      if (i == 1) {
         Settings.howManyElements = Selector.integerFromRange("Podaj liczbę elementów", 1, Integer.MAX_VALUE);
      } else if (i == 2) {
         Settings.howManyRepeats = Selector.integerFromRange("Podaj liczbę powtórzeń", 1, Integer.MAX_VALUE);
      }
   }

   public static void setSettings(int howManyElements, int howManyRepeats) {
      Settings.howManyElements = howManyElements;
      Settings.howManyRepeats = howManyRepeats;
   }
}
