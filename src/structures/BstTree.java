package structures;

import addons.Settings;
import addons.TreePrinter;
import enums.Place;
import java.util.ArrayList;
import java.util.List;

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
		return "Dynamiczna struktura danych będąca drzewem binarnym, \n"
			+ "w którym lewe poddrzewo każdego węzła zawiera wyłącznie elementy\n"
			+ " o kluczach mniejszych niż klucz węzła a prawe poddrzewo zawiera wyłącznie elementy\n"
			+ " o kluczach nie mniejszych niż klucz węzła. Węzły, oprócz klucza, przechowują wskaźniki\n"
			+ " na swojego lewego i prawego syna oraz na swojego ojca.";
	}

	@Override //Wykorzystywane jest tylko pole number
	public void subtract(Place place, int number) throws IllegalArgumentException, IndexOutOfBoundsException {
		delete(search(root, number));
	}

	@Override //Wykorzystywane jest tylko pole number
	public void add(Place place, int number) throws IllegalArgumentException {
		insert(new Node(number));
	}

	@Override
	public boolean find(int find) {
		Node node = search(root, find);
		return node != null;
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

	private Node search(Node node, int key) {
		if (node == null || node.getValue() == key) {
			return node;
		}
		if (key < node.getValue()) {
			return search(node.getLeft(), key);
		}
		return search(node.getRight(), key);
	}

	private Node getMinKey(Node node) {
		while (node.getLeft() != null) {
			node = node.getLeft();
		}
		return node;
	}

	private void insert(Node node) {
		size++;
		Node y = null;
		Node x = root;
		while (x != null) {
			y = x;
			if (node.getValue() < x.getValue()) {
				x = x.getLeft();
			} else {
				x = x.getRight();
			}
		}
		node.setParent(y);
		if (y == null) {
			root = node;
		} else if (node.getValue() < y.getValue()) {
			y.setLeft(node);
		} else {
			y.setRight(node);
		}
		if (Settings.x) {
			DSW();
		}
	}

	private Node getSuccessor(Node node) {
		if (node.getRight() != null) {
			return getMinKey(node.getRight());
		}
		Node temp = node.getParent();
		while (temp != null && temp.getLeft() != node) {
			node = temp;
			temp = temp.getParent();
		}
		return temp;
	}

	private void delete(Node node) {
		if (node == null) {
			return;
		}
		size--;
		Node x, y;
		if (node.getLeft() == null || node.getRight() == null) {
			y = node;
		} else {
			y = getSuccessor(node);
		}
		if (y.getLeft() != null) {
			x = y.getLeft();
		} else {
			x = y.getRight();
		}
		if (x != null) {
			x.setParent(y.getParent());
		}
		if (y.getParent() == null) {
			root = x;
		} else if (y == y.getParent().getLeft()) {
			y.getParent().setLeft(x);
		} else {
			y.getParent().setRight(x);
		}
		if (y != node) {
			node.setValue(y.getValue());
		}
		if (Settings.x) {
			DSW();
		}
	}

	private void createLinearTree() {
		Node tmp = root;
		while (tmp != null) {
			if (tmp.getLeft() != null) {
				rotateRight(tmp);
				tmp = tmp.getParent();
			} else {
				tmp = tmp.getRight();
			}

		}
	}

	private int MSB(int n) {
		int ndx = 0;
		while (1 < n) {
			n = (n >> 1);
			ndx++;
		}
		return ndx;
	}

	private int calculateM(int size) {
		return (1 << MSB(size + 1)) - 1;
	}

	private void createBalancedTree() {
		int m = calculateM(size);
		List<Node> nodeList = new ArrayList<>();
		Node temp = root;
		for (int i = 0; i < size - m; i++) {
			nodeList.add(temp);
			temp = temp.getRight().getRight();
			if (temp == null) {
				break;
			}
		}
		nodeList.forEach(this::rotateLeft);
		while (m > 1) {
			nodeList.clear();
			temp = root;
			m = (int) Math.floor(m / 2);
			for (int i = 0; i < m; i++) {
				nodeList.add(temp);
				temp = temp.getRight().getRight();
			}
			nodeList.forEach(this::rotateLeft);
		}
	}

	/**
	 * Funkcja wywołująca algorytm DSW.
	 */
	public void DSW() {
		if (root != null) {
			createLinearTree();
			createBalancedTree();
		}
	}

	private boolean isRightChild(Node node) {
		return node.getParent().getRight() == node;
	}

	private boolean isLeftChild(Node node) {
		return node.getParent().getLeft() == node;
	}

	private void rotateRight(Node node) {
		if (node.getParent() == null) {
			root = node.getLeft();
			node.getLeft().setParent(null);
		} else {
			if (isRightChild(node)) {
				node.getParent().setRight(node.getLeft());
			}
			if (isLeftChild(node)) {
				node.getParent().setLeft(node.getLeft());
			}
			node.getLeft().setParent(node.getParent());
		}
		node.setParent(node.getLeft());
		node.setLeft(node.getParent().getRight());
		if (node.getParent().getRight() != null) {
			node.getParent().getRight().setParent(node);
		}
		node.getParent().setRight(node);
	}

	private void rotateLeft(Node node) {
		if (node.getParent() == null) {
			root = node.getRight();
			node.getRight().setParent(null);
		} else {
			if (isRightChild(node)) {
				node.getParent().setRight(node.getRight());
			}
			if (isLeftChild(node)) {
				node.getParent().setLeft(node.getRight());
			}
			node.getRight().setParent(node.getParent());
		}
		node.setParent(node.getRight());
		node.setRight(node.getParent().getLeft());
		if (node.getParent().getLeft() != null) {
			node.getParent().getLeft().setParent(node);
		}
		node.getParent().setLeft(node);
	}
}