package selector;

import enums.Place;
import view.Message;

import java.util.Random;
import java.util.Scanner;

public class Selector {

   public static int integerFromRange(String message, Integer min, Integer max) {
      int i;
      Scanner in = new Scanner(System.in);
      Message.displayNormal(message);
      do {
         i = Integer.parseInt(in.nextLine());
      } while (i <= max && i >= min);
      in.close();
      return i;
   }

   public static int randomIntegerFromRange(Integer min, Integer max) {
      Random random = new Random();
      return random.nextInt(max - min) + min;
   }

   public static int randomInteger() {
      Random random = new Random();
      return random.nextInt();
   }

   public static Place place(String label) {
      Place place = Place.NULL;
      Message.displayNormal("Gdzie odjac liczbe?");
      Message.displayNormal("1. Poczatek");
      Message.displayNormal("2. Koniec");
      Message.displayNormal("3. Losowo");
      Integer i = Selector.integerFromRange(label, 1, 3);
      if (i.equals(1)) {
         place = Place.START;
      } else if (i.equals(2)) {
         place = Place.END;
      } else if (i.equals(3)) {
         place = Place.RANDOM;
      }
      return place;
   }


}
