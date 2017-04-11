package addons;

/**
 * Created by Tobiasz Rumian on 10.04.2017.
 */
/** Node that can be printed */
public interface PrintableNode {
    /** Get left child */
    PrintableNode getLeft();


    /** Get right child */
    PrintableNode getRight();


    /** Get text to be printed */
    String getText();

    String getColorString();
}
