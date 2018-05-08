package structures;

import enums.PlaceOnList;

/**
 * Klasa tworząca obiekty zawierające dwie referencje do swoich węzłów, oraz wartość liczbową.
 *
 * @author Tobiasz Rumian
 */
public class Node {

	private Node left; //Lewy węzeł
	private Node right;//Prawy węzeł
	private Node parent;
	private int value;//Wartość

	/**
	 * Konstruktor pełny
	 *
	 * @param left Lewy węzeł
	 * @param right Prawy węzeł
	 * @param value Wartość
	 */
	Node(Node left, Node right, int value) {
		this.left = left;
		this.right = right;
		this.value = value;
	}

	public Node(Node left, Node right, Node parent, int value) {
		this.left = left;
		this.right = right;
		this.parent = parent;
		this.value = value;
	}

	/**
	 * Konstruktor z jednym węzłem
	 *
	 * @param node Przypisywany węzeł
	 * @param value Wartość
	 * @param placeOnList Miejsce, do którego ma być przypisany węzeł.
	 */
	Node(Node node, int value, PlaceOnList placeOnList) {
		if (placeOnList == PlaceOnList.LEFT) {
			this.left = node;
		} else {
			this.right = node;
		}
		this.value = value;
	}

	/**
	 * Konstruktor bez węzłów (np. Korzeń)
	 *
	 * @param value Wartość
	 */
	Node(int value) {
		this.value = value;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
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
	int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		return o.getClass() == Integer.class && value == (int) o;
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(value);
	}

	/**
	 * Zwraca wartość w postaci String'a
	 *
	 * @return Wartość w postaci String'a
	 */
	public String getText() {
		return Integer.toString(value);
	}

}
