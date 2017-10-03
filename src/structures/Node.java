package structures;

import enums.PlaceOnList;

/**
 * Klasa tworząca obiekty zawierające dwie referencje do swoich węzłów, oraz wartość liczbową.
 *
 * @author Tobiasz Rumian
 */
public class Node {
   private Node left = null; //Lewy węzeł
   private Node right = null;//Prawy węzeł
   private int integer = 0;//Wartość

   /**
    * Konstruktor pełny
    *
    * @param left    Lewy węzeł
    * @param right   Prawy węzeł
    * @param integer Wartość
    */
   Node(Node left, Node right, int integer) {
      this.left = left;
      this.right = right;
      this.integer = integer;
   }

   /**
    * Konstruktor z jednym węzłem
    *
    * @param node        Przypisywany węzeł
    * @param integer     Wartość
    * @param placeOnList Miejsce, do którego ma być przypisany węzeł.
    */
   Node(Node node, int integer, PlaceOnList placeOnList) {
      if (placeOnList == PlaceOnList.LEFT) {
         this.left = node;
      } else {
         this.right = node;
      }
      this.integer = integer;
   }

   /**
    * Konstruktor bez węzłów (np. Korzeń)
    *
    * @param integer Wartość
    */
   Node(int integer) {
      this.integer = integer;
   }

   /**
    * Zwraca lewy węzeł
    *
    * @return Lewy węzeł
    */
   public Node getLeft() {
      return left;
   }

   /**
    * Ustawia lewy węzeł
    */
   void setLeft(Node left) {
      this.left = left;
   }

   /**
    * Zwraca prawy węzeł
    *
    * @return Prawy węzeł
    */
   public Node getRight() {
      return right;
   }

   /**
    * Ustawia prawy węzeł
    */
   void setRight(Node right) {
      this.right = right;
   }

   /**
    * Zwraca wartość
    *
    * @return Wartość
    */
   int getInteger() {
      return integer;
   }

   @Override
   public boolean equals(Object o) {
      return o.getClass() == Integer.class && integer == (int) o;
   }

   @Override
   public int hashCode() {
      return Integer.hashCode(integer);
   }

   /**
    * Zwraca wartość w postaci String'a
    *
    * @return Wartość w postaci String'a
    */
   public String getText() {
      return Integer.toString(integer);
   }

}
