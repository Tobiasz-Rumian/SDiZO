package structures;

import addons.Settings;
import addons.View;
import enums.Place;
import enums.PlaceOnList;

/**
 * Klasa reprezentująca listę dwukierunkową.
 * Korzysta z interfejsu struktur.
 *
 * @author Tobiasz Rumian.
 */
public class BidirectionalList implements Structure {
    private Node firstElement;
    private Node lastElement;
    private int size = 0;

    @Override
    public String info() {
        return "Lista – struktura danych służąca do reprezentacji zbiorów dynamicznych,\n" +
                " w której elementy ułożone są w liniowym porządku.\n" +
                " Rozróżniane są dwa podstawowe rodzaje list:\n" +
                " lista jednokierunkowa, w której z każdego elementu możliwe jest przejście do jego następnika, \n" +
                "oraz lista dwukierunkowa, w której z każdego elementu możliwe jest przejście \n" +
                "do jego poprzednika i następnika.";
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        firstElement = null;
        lastElement = null;
        size = 0;
    }

    @Override
    public void subtract(Place place, int number) {
        if (firstElement.getRight() == null || lastElement.getLeft() == null) clear();
        if (size == 0) return;
        switch (place) {
            case START:
                firstElement = firstElement.getRight();//Wstawianie prawego dziecka pierwszego elementu w jego miejsce
                firstElement.setLeft(null);//Usuwanie referencji do poprzedniego pierwszego elementu
                break;
            case END:
                lastElement = lastElement.getLeft();//Wstawianie lewego dziecka ostatniego elementu w jego miejsce
                lastElement.setRight(null);//Usuwanie referencji do poprzedniego ostatniego elementu
                break;
            case RANDOM:
                Node node = get(View.getRandom(0, size() - 1));//Losowanie indeksu i pobieranie węzła
                if (node == firstElement) {//Jeżeli wylosowano pierwszy element, postępuj tak jak wyżej
                    firstElement = firstElement.getRight();
                    firstElement.setLeft(null);
                } else if (node == lastElement) {//Jeżeli wylosowano ostatni element, postępuj tak jak wyżej
                    lastElement = lastElement.getLeft();
                    lastElement.setRight(null);
                } else {
                    node.getLeft().setRight(node.getRight());//Ustaw prawe dziecko węzła, jako prawe dziecko lewego dziecka węzła
                    node.getRight().setLeft(node.getLeft());//Ustaw lewe dziecko węzła, jako lewe dziecko prawego dziecka węzła
                }
                break;
        }
        size--;//Zmniejsz rozmiar listy o 1
    }

    @Override
    public void add(Place place, int number) {
        if (size == 0) {//Jeżeli rozmiar tablicy==0 Ustaw nowy węzeł jako pierwszy i ostatni element
            Node list = new Node(number);
            firstElement = list;
            lastElement = list;
            size++;
            return;
        }
        switch (place) {
            case START: {
                Node newNode = new Node(firstElement, number, PlaceOnList.RIGHT);//Tworzenie nowego elementu z prawym dzieckiem jako pierwszym elementem tablicy
                firstElement.setLeft(newNode);//Ustaw nowy węzeł jako lewy węzeł pierwszego elementu
                firstElement = newNode;//Ustaw nowy węzeł jako pierwszy element
                break;
            }
            case END: {
                Node newNode = new Node(lastElement, number, PlaceOnList.LEFT);//Tworzenie nowego elementu z lewym dzieckiem jako ostatnim elementem tablicy
                lastElement.setRight(newNode);//Ustaw nowy węzeł jako prawy węzeł ostatniego elementu
                lastElement = newNode;//Ustaw nowy węzeł jako ostatni element
                break;
            }
            case RANDOM: {
                int i = 0;
                if (size != 1)
                    i = View.getRandom(0, size() - 1);//Jeżeli tablica ma więcej niż jeden element, wylosuj miejsce wstawienia
                if (i == 0) {//Postępuj jak z wstawianiem na początek
                    Node newNode = new Node(firstElement, number, PlaceOnList.RIGHT);
                    firstElement.setLeft(newNode);
                    firstElement = newNode;
                } else if (i == size()) {//Postępuj jak z wstawianiem na koniec
                    Node newNode = new Node(lastElement, number, PlaceOnList.LEFT);
                    lastElement.setRight(newNode);
                    lastElement = newNode;
                } else {
                    Node node = get(i);//Znajdź odpowiedni węzeł
                    Node newNode = new Node(node.getLeft(), node, number);//Stwórz nowy węzeł
                    node.getLeft().setRight(newNode);//Ustaw nowy węzeł jako prawe dziecko lewego dziecka wylosowanego węzła
                    node.setLeft(newNode);//Ustaw nowy węzeł jako lewe dziecko wylosowanego węzła
                }
                break;
            }
        }
        size++;
    }

    @Override
    public boolean find(int find) {
        if (size == 0) return false;
        Node node = firstElement;
        for (int i = 0; i < size; i++) {
            if (node.getInteger() == find) return true;//Sprawdź, czy sprawdzany węzeł == szukany węzeł
            node = node.getRight();//Idź do prawego węzła
        }
        return false;
    }

    @Override
    public String show() {
        if (size == 0||firstElement==null||lastElement==null) return "";
        StringBuilder sb = new StringBuilder();


        Node list = firstElement;
        do {
            sb.append(" ").append(list.getInteger()).append(" ");
            list = list.getRight();
        } while (list != null);
        sb.append("\n");
        list = lastElement;
        do {
            sb.append(" ").append(list.getInteger()).append(" ");
            list = list.getLeft();
        } while (list != null);

        return sb.toString();
    }

    @Override
    public String toString() {
        return "Lista dwukierunkowa";
    }

    /**
     * Funkcja pozwalająca zdobyć rekord pasujący do wartości.
     *
     * @param index Wartość szukanego rekordu.
     * @return Zwraca szukany rekord.
     */
    private Node get(int index) {
        Node node;
        if (((size - 1) / 2) <= index) {
            node = firstElement;
            for (int i = 0; i < index; i++) node = node.getRight();
        } else {
            node = lastElement;
            for (int i = 0; i < (size - 1 - index); i++) node = node.getLeft();
        }
        return node;
    }

    public void add(int index, int value) {
        if (firstElement!=null&&lastElement!=null&&index > size) return;
        if (firstElement==null||lastElement==null||size == 0) {//Jeżeli rozmiar tablicy==0 Ustaw nowy węzeł jako pierwszy i ostatni element
            clear();
            Node list = new Node(value);
            firstElement = list;
            lastElement = list;
            size++;
            return;
        } else {
            if (index == 0) {//Postępuj jak z wstawianiem na początek
                Node newNode = new Node(firstElement, value, PlaceOnList.RIGHT);
                firstElement.setLeft(newNode);
                firstElement = newNode;
            } else if (index == size()) {//Postępuj jak z wstawianiem na koniec
                Node newNode = new Node(lastElement, value, PlaceOnList.LEFT);
                lastElement.setRight(newNode);
                lastElement = newNode;
            } else {
                Node node = get(index);//Znajdź odpowiedni węzeł
                Node newNode = new Node(node.getLeft(), node, value);//Stwórz nowy węzeł
                node.getLeft().setRight(newNode);//Ustaw nowy węzeł jako prawe dziecko lewego dziecka wylosowanego węzła
                node.setLeft(newNode);//Ustaw nowy węzeł jako lewe dziecko wylosowanego węzła
            }
        }
        size++;
    }

    public void subtract(int value) {
        if(firstElement==null||lastElement==null)clear();
        else if (firstElement.getRight() == null || lastElement.getLeft() == null) clear();
        else {
            Node node = firstElement;
            for (int i = 0; i < size; i++) {
                if (node.getInteger() == value) break;//Sprawdź, czy sprawdzany węzeł == szukany węzeł
                node = node.getRight();//Idź do prawego węzła
            }
            if(node==null)return;
            if (node == firstElement) {//Jeżeli wylosowano pierwszy element, postępuj tak jak wyżej
                firstElement = firstElement.getRight();
                firstElement.setLeft(null);
            } else if (node == lastElement) {//Jeżeli wylosowano ostatni element, postępuj tak jak wyżej
                lastElement = lastElement.getLeft();
                lastElement.setRight(null);
            } else {
                node.getLeft().setRight(node.getRight());//Ustaw prawe dziecko węzła, jako prawe dziecko lewego dziecka węzła
                node.getRight().setLeft(node.getLeft());//Ustaw lewe dziecko węzła, jako lewe dziecko prawego dziecka węzła
            }
        }
        size--;//Zmniejsz rozmiar listy o 1
    }
}
