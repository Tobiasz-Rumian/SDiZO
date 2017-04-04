package structures;

import enums.Place;

/**
 * Created by Tobiasz Rumian on 19.03.2017.
 */
public class BstTree implements Structure {
        public static Node root;
        public BstTree(){
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
    public void subtract(Place place, Integer number) throws IllegalArgumentException, IndexOutOfBoundsException {
        Node parent = root;
        Node current = root;
        boolean isLeftChild = false;
        while(!current.getInteger().equals(number)){
            parent = current;
            if(current.getInteger()>number){
                isLeftChild = true;
                current = current.getBefore();
            }else{
                isLeftChild = false;
                current = current.getAfter();
            }
        }
        //if i am here that means we have found the bidirectionalObject
        //Case 1: if bidirectionalObject to be deleted has no children
        if(current.getBefore()==null && current.getAfter()==null){
            if(current==root)root = null;
            if(isLeftChild)parent.setBefore(null);
            else parent.setAfter(null);
        }
        //Case 2 : if bidirectionalObject to be deleted has only one child
        else if(current.getAfter()==null){
            if(current==root)root = current.getBefore();
            else if(isLeftChild)parent.setBefore(current.getBefore());
            else parent.setAfter(current.getBefore());
        }
        else if(current.getBefore()==null){
            if(current==root)root = current.getAfter();
            else if(isLeftChild)parent.setBefore(current.getAfter());
            else parent.setAfter(current.getAfter());
        }else if(current.getBefore()!=null && current.getAfter()!=null){

            //now we have found the minimum element in the right sub tree
            Node successor	 = getSuccessor(current);
            if(current==root)root = successor;
            else if(isLeftChild)parent.setBefore(successor);
            else parent.setAfter(successor);
            successor.setBefore(current.getBefore());
        }
    }

    @Override
    public void add(Place place, Integer number) throws IllegalArgumentException {
        Node newNode = new Node(number);
        if(root==null){
            root = newNode;
            return;
        }
        Node current = root;
        Node parent = null;
        while(true){
            parent = current;
            if(number<current.getInteger()){
                current = current.getBefore();
                if(current==null){
                    parent.setBefore(newNode);
                    return;
                }
            }else{
                current = current.getAfter();
                if(current==null){
                    parent.setAfter(newNode);
                    return;
                }
            }
        }
    }

    @Override
    public Integer find(Integer find) {
        Node current = root;
        Integer counter =0;
        while(current!=null){
            if(current.getInteger().equals(find))return counter;
            else if(current.getInteger()>find)current = current.getAfter();
            else current = current.getBefore();
            counter++;
        }
        return -1;
    }

    @Override
    public String show() {
        StringBuilder sb=new StringBuilder();
        Node list = root;
        while(list.getAfter()!=null){
            sb.append("[").append(list.getInteger()).append("]");
            list=list.getAfter();
        }
        return sb.toString();
    }

    private Node getSuccessor(Node deleleNode){
        Node successor =null;
        Node successorParent =null;
        Node current = deleleNode.getAfter();
        while(current!=null){
            successorParent = successor;
            successor = current;
            current = current.getBefore();
        }
        //check if successor has the right child, it cannot have left child for sure
        // if it does have the right child, add it to the left of successorParent.
        //		successsorParent
        if(successor!=deleleNode.getAfter()){
            successorParent.setBefore(successor.getAfter());
            successor.setAfter(successor.getAfter());
        }
        return successor;
    }
}

