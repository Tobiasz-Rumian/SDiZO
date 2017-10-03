package view;

public class Message {

   public static void displayNormal(String message) {
      System.out.println(message + "\n");
   }

   public static void displayError(String message) {
      System.err.println(message + "\n");
   }
}
