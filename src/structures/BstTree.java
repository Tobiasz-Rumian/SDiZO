package structures;

import addons.Settings;
import addons.TreePrinter;
import enums.Place;

/**
 * Klasa reprezentująca drzewo BST.
 * Korzysta z interfejsu struktur.
 *
 * @author Tobiasz Rumian.
 */
public class BstTree implements Structure {
   private static Node root; //Korzeń drzewa.
   private int size = 0; //Ilość elementów w drzewie.

   public BstTree() {
      root = null;
   }

   @Override
   public String info() {
      return "Dynamiczna struktura danych będąca drzewem binarnym, \n" +
              "w którym lewe poddrzewo każdego węzła zawiera wyłącznie elementy\n" +
              " o kluczach mniejszych niż klucz węzła a prawe poddrzewo zawiera wyłącznie elementy\n" +
              " o kluczach nie mniejszych niż klucz węzła. Węzły, oprócz klucza, przechowują wskaźniki\n" +
              " na swojego lewego i prawego syna oraz na swojego ojca.";
   }

   @Override
   public void subtract(Place place, int number) throws IllegalArgumentException, IndexOutOfBoundsException {
      if (size == 0) {
         return;
      }
      if (root.getInteger() == number) {
         clear();
         return;
      }
      Node parent = root;
      Node current = root;
      //Algorytm wyszukiwania w drzewie
      boolean isLeftChild = false;
      size--;
      while (current.getInteger() != number) {
         parent = current;
         if (current.getLeft() == null && current.getRight() == null) {
            return;//Jeżeli nie znaleziono węzła, zakończ funkcję.
         } else if (current.getLeft() == null) { //Jeżeli istnieje tylko prawy węzeł
            current = current.getRight(); //Idź do prawego węzła
            isLeftChild = false; //Ustaw flagę lewego węzła na false;
         } else if (current.getRight() == null) {//Jeżeli istnieje tylko lewy węzeł
            current = current.getLeft();//Idź do lewego węzła
            isLeftChild = true;//Ustaw flagę lewego węzła na true;
         } else if (current.getInteger() > number) {//Jeżeli wartość current jest większa od szukanego numeru, należy szukać w lewej gałęzi.
            isLeftChild = true; //Ustaw flagę lewego węzła na true
            current = current.getLeft();//Idź do lewego węzła
         } else {
            isLeftChild = false;//Ustaw flagę lewego węzła na false
            current = current.getRight();//Idź do prawego węzła
         }
      }
      //Znaleziono węzeł.

      //Przypadek 1: Znaleziony węzeł jest liściem.
      if (current.getLeft() == null && current.getRight() == null) {
         if (current == root) {
            root = null; //Jeżeli znaleziono korzeń, usuń drzewo
         }
         if (isLeftChild) {
            parent.setLeft(null);//Usuń powiązanie z liściem
         } else {
            parent.setRight(null);//Usuń powiązanie z liściem
         }
      }
      //Przypadek 2: Znaleziony węzeł ma jedno dziecko.
      else if (current.getRight() == null) {//Przypisz rodzicowi dziecko węzła, na miejsce węzła.
         if (current == root) {
            root = current.getLeft();
         } else if (isLeftChild) {
            parent.setLeft(current.getLeft());
         } else {
            parent.setRight(current.getLeft());
         }
      } else if (current.getLeft() == null) {
         if (current == root) {
            root = current.getRight();
         } else if (isLeftChild) {
            parent.setLeft(current.getRight());
         } else {
            parent.setRight(current.getRight());
         }
      }
      //Przypadek 3: Znaleziony węzeł ma oba dzieci.
      else if (current.getLeft() != null && current.getRight() != null) {
         Node successor = getSuccessor(current);//Znajdź następce
         successor.setLeft(current.getLeft());
         successor.setRight(current.getRight());
         if (current == root) {
            root = successor;//Jeżeli usuwany węzeł jest korzeniem, wstaw na miejsce korzenia następce
         } else if (isLeftChild) {
            parent.setLeft(successor);//Wstaw na miejsce węzła następce
         } else {
            parent.setRight(successor);
         }
      }
      if (Settings.x) {
         DSW();//Algorytm sortujący drzewo
      }
   }

   @Override
   public void add(Place place, int number) throws IllegalArgumentException {
      Node newNode = new Node(null, null, number);
      size++;
      if (root == null) {
         root = newNode;
         return;
      }
      Node current = root;
      Node parent;
      while (true) {
         parent = current;
         if (number < current.getInteger()) {//Jeżeli wstawiany numer jest mniejszy od przeszukiwanego węzła, idź w lewo
            current = current.getLeft();
            if (current == null) {
               parent.setLeft(newNode);//Wstaw nowy węzeł jako lewe dziecko
               if (Settings.x) {
                  DSW();//Algorytm sortujący drzewo
               }
               return;
            }
         } else {//Inaczej, idź w prawo
            current = current.getRight();
            if (current == null) {
               parent.setRight(newNode);//Wstaw węzeł jako prawe dziecko
               if (Settings.x) {
                  DSW();//Algorytm sortujący drzewo
               }
               return;
            }
         }
      }
   }

   @Override
   public boolean find(int find) {
      if (root == null) {
         return false;
      }
      Node current = root;
      while (current.getInteger() != find) {
         if (current.getLeft() == null && current.getRight() == null) {
            return false;
         } else if (current.getInteger() < find) {
            if (current.getRight() == null) {
               return false;
            }
            current = current.getRight();//Jeżeli wartość current jest mniejsza od szukanego numeru, należy szukać w prawej gałęzi.
         } else {
            current = current.getLeft();
         }
      }
      return true;
   }

   @Override
   public String show() {
      return TreePrinter.print(root);
   }

   @Override
   public String toString() {
      return "Drzewo BST";
   }

   @Override
   public int size() {
      return size;
   }

   @Override
   public void clear() {
      root = null;
      size = 0;
   }

   /**
    * Funkcja pozwalająca znaleźć najmniejszy element z szukanego fragmentu drzewa.
    *
    * @param node Korzeń, z którego rozpoczyna się szukanie.
    * @return Zwraca najmniejszy element z szukanego fragmentu drzewa.
    */
   private Node getSuccessor(Node node) {
      if (node == null) {
         return null;
      }

      if (node.getRight() != null) {
         Node successor = node.getRight();
         Node successorParent;
         Node current = node.getRight();
         do {//Idziemy do lewego liścia poddrzewa prawego węzła.
            successorParent = successor;
            successor = current;
            current = current.getLeft();
         } while (current != null);
         if (successor.getRight() != null) { //Przenosimy prawe dziecko następnika do lewego dziecka rodzica następnika
            successorParent.setLeft(successor.getRight());
            successor.setRight(null);
         } else {
            successorParent.setLeft(null);
         }
         return successor;//Nie potrzebujemy sprawdzać kolejnych dwóch przypadków, ponieważ drzewo jest sortowane po każdym dodaniu/odejmowaniu.
      } else {
         return node;
      }
   }

   /**
    * Funkcja wywołująca algorytm DSW.
    */
   private void DSW() {
      if (root != null) {
         Node grandParent = null;
         Node parent = root;
         Node leftChild;
         while (parent != null) {//Rozwijanie drzewa w prawo.
            leftChild = parent.getLeft();
            if (leftChild != null) {
               grandParent = rotateRight(grandParent, parent, leftChild);
               parent = leftChild;
            } else {
               grandParent = parent;
               parent = parent.getRight();
            }
         }
         int m = (1 << MSB(size + 1)) - 1;//Największa potęga dwójki, mniejsza niż n;
         makeRotations(size - m);//Zwijanie drzewa.
         while (m > 1) {
            makeRotations(m /= 2);//Zwijanie drzewa.
         }
      }
   }

   /**
    * Funkcja zwracająca najbardziej znaczący bit.
    * Indeks najmniej znaczącego bitu to 0.
    */
   private int MSB(int n) {
      int ndx = 0;
      while (1 < n) {
         n = (n >> 1);
         ndx++;
      }
      return ndx;
   }

   /**
    * Funkcja przywracająca drzewu pierwotny kształt poprzez rotacje w lewo.
    *
    * @param bound Parametr okreśaljący ilość rotacji w lewo.
    */
   private void makeRotations(int bound) {
      Node grandParent = null;
      Node parent = root;
      Node child = root.getRight();
      for (; bound > 0; bound--) {
         try {
            if (child != null) {
               rotateLeft(grandParent, parent, child);
               grandParent = child;
               parent = grandParent.getRight();
               child = parent.getRight();
            } else {
               break;
            }
         } catch (NullPointerException convenient) {
            break;
         }
      }
   }

   /**
    * Funkcja pozwalająca obrócić węzeł w lewo.
    *
    * @param grandParent Dziadek węzła.
    * @param parent      Rodzic węzła.
    * @param rightChild  Prawe dziecko węzła.
    */
   private void rotateLeft(Node grandParent, Node parent, Node rightChild) {
      if (grandParent != null) {
         grandParent.setRight(rightChild);
      } else {
         root = rightChild;
      }
      parent.setRight(rightChild.getLeft());
      rightChild.setLeft(parent);
   }

   /**
    * Funkcja pozwalająca obrócić węzeł w prawo.
    *
    * @param grandParent Dziadek węzła.
    * @param parent      Rodzic węzła.
    * @param leftChild   Lewe dziecko węzła.
    * @return Zwraca dziadka węzła.
    */
   private Node rotateRight(Node grandParent, Node parent, Node leftChild) {
      if (grandParent != null) {
         grandParent.setRight(leftChild);
      } else {
         root = leftChild;
      }
      parent.setLeft(leftChild.getRight());
      leftChild.setRight(parent);
      return grandParent;
   }

}

