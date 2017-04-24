package structures;

import addons.TreePrinter;
import enums.Place;

/**
 * Klasa reprezentująca drzewo BST.
 * Korzysta z interfejsu struktur.
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
        if(size==0) return;
        if(size==1){
            clear();
            return;
        }
        Node parent = root;
        Node current = root;
        //Algorytm wyszukiwania w drzewie
        boolean isLeftChild = false;
        size--;
        while (current.getInteger()!=number) {
            parent = current;
            if (current.getLeftN() == null && current.getRightN() == null) return;//Jeżeli nie znaleziono węzła, zakończ funkcję.
            else if(current.getLeftN() == null){
                current=current.getRightN();
                isLeftChild = false;
            } else if(current.getRightN()==null){
                current=current.getLeftN();
                isLeftChild = true;
            } else if (current.getInteger() > number) {//Jeżeli wartość current jest większa od szukanego numeru, należy szukać w lewej gałęzi.
                isLeftChild = true;
                current = current.getLeftN();
            } else {
                isLeftChild = false;
                current = current.getRightN();
            }
        }
        //Znaleziono węzeł.

        //Przypadek 1: Znaleziony węzeł jest liściem.
        if (current.getLeftN() == null && current.getRightN() == null) {
            if (current == root) root = null;
            if (isLeftChild) parent.setLeft(null);
            else parent.setRight(null);
        }
        //Przypadek 2: Znaleziony węzeł ma jedno dziecko.
        else if (current.getRightN() == null) {
            if (current == root) root = current.getLeftN();
            else if (isLeftChild) parent.setLeft(current.getLeftN());
            else parent.setRight(current.getLeftN());
        } else if (current.getLeftN() == null) {
            if (current == root) root = current.getRightN();
            else if (isLeftChild) parent.setLeft(current.getRightN());
            else parent.setRight(current.getRightN());
        }
        //Przypadek 3: Znaleziony węzeł ma oba dzieci.
        else if (current.getLeftN() != null && current.getRightN() != null) {

            //now we have found the minimum element in the right sub tree
            Node successor = getSuccessor(current);
            if (current == root) root = successor;
            else if (isLeftChild) parent.setLeft(successor);
            else parent.setRight(successor);
            successor.setLeft(current.getLeftN());
        }
        DSW();//Algorytm sortujący drzewo
    }

    @Override
    public void add(Place place, int number) throws IllegalArgumentException {
        Node newNode = new Node(null,null,number);
        size++;
        if (root == null) {
            root = newNode;
            return;
        }
        Node current = root;
        Node parent;
        while (true) {
            parent = current;
            if (number < current.getInteger()) {
                current = current.getLeftN();
                if (current == null) {
                    parent.setLeft(newNode);
                    DSW();//Algorytm sortujący drzewo
                    return;
                }
            } else {
                current = current.getRightN();
                if (current == null) {
                    parent.setRight(newNode);
                    DSW();//Algorytm sortujący drzewo
                    return;
                }
            }
        }
    }

    @Override
    public boolean find(int find) {
        Node current = root;
        while (current.getInteger()!=find) {
            if (current.getLeftN() == null && current.getRightN() == null) return false;
            else if(current.getLeftN() == null) current=current.getRightN();
            else if(current.getRightN()==null) current=current.getLeftN();
            else if (current.getInteger() > find) current = current.getLeftN(); //Jeżeli wartość current jest większa od szukanego numeru, należy szukać w lewej gałęzi.
            else current = current.getRightN();
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
        size=0;
    }

    /**
     * Funkcja pozwalająca znaleźć najmniejszy element z szukanego fragmentu drzewa.
     *
     * @param node Korzeń, z którego rozpoczyna się szukanie.
     * @return Zwraca najmniejszy element z szukanego fragmentu drzewa.
     */
    private Node getSuccessor(Node node) {
        if (node.getRightN() == null) return node;
        Node successor = null;
        Node successorParent;
        Node current = node.getRightN();
        do {
            successorParent = successor;
            successor = current;
            current = current.getLeftN();
        } while (current != null);
        //Sprawdza, czy węzeł ma prawe dziecko, jeżeli tak, to dodaje je jako lewe dziecko rodzica węzła.
        if (successor.getRightN() != null) {
            successorParent.setLeft(successor.getRightN());
            successor.setRight(successor.getRightN());
        }
        return successor;
    }

    /**
     * Funkcja wywołująca algorytm DSW.
     */
    private void DSW() {
        if (root != null) {
            Node grandParent = null;
            Node parent = root;
            Node leftChild;

            while (parent != null) {
                leftChild = parent.getLeftN();
                if (leftChild != null) {
                    grandParent = rotateRight(grandParent, parent, leftChild);
                    parent = leftChild;
                } else {
                    grandParent = parent;
                    parent = parent.getRightN();
                }
            }
            int m = (1 << MSB(size + 1)) - 1;//Największa potęga dwójki, mniejsza niż n;
            makeRotations(size - m);
            while (m > 1) makeRotations(m /= 2);
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
        Node child = root.getRightN();
        for (; bound > 0; bound--) {
            try {
                if (child != null) {
                    rotateLeft(grandParent, parent, child);
                    grandParent = child;
                    parent = grandParent.getRightN();
                    child = parent.getRightN();
                } else break;
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
        if (grandParent != null) grandParent.setRight(rightChild);
        else root = rightChild;
        parent.setRight(rightChild.getLeftN());
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
        if (grandParent != null) grandParent.setRight(leftChild);
        else root = leftChild;
        parent.setLeft(leftChild.getRightN());
        leftChild.setRight(parent);
        return grandParent;
    }

}

