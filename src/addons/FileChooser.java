package addons;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * Klasa wyświetlająca okno umożliwiające wybór pliku tekstowego.
 * @author Tobiasz Rumian
 */
class FileChooser extends JFrame {
    private String path;

    FileChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Pliki tekstowe", "txt"));
        chooser.setCurrentDirectory(new File("."));
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            this.path = chooser.getSelectedFile().getPath();
            this.toFront();
            this.repaint();
            pack();
        }
    }

    String getPath() {
        return path;
    }
}
