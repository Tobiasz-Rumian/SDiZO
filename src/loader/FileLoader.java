package loader;

import enums.Place;
import structures.Structure;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileLoader {

   public static void loadFromFile(Structure structure) {
      FileChooser fileChooser = new FileChooser();
      if (fileChooser.getPath().isPresent()) {
         try (Stream<String> stream = Files.lines(Paths.get(fileChooser.getPath().get()))) {
            List<String> lineList = stream
                    .filter(line -> !line.equals(""))
                    .collect(Collectors.toList());
            lineList.remove(0);
            lineList.forEach(x -> structure.add(Place.END, Integer.parseInt(x)));
         } catch (IOException e) {
            e.getMessage();
         }
      }
   }
}

class FileChooser extends JFrame {
   private static final String FILE_FILTER_DESCRIPTION = "Pliki tekstowe";
   private static final String FILE_FILTER_EXTENSION = "txt";
   private static final String PATHNAME = ".";
   private String path;

   FileChooser() {
      JFileChooser chooser = new JFileChooser();
      chooser.setFileFilter(new FileNameExtensionFilter(FILE_FILTER_DESCRIPTION, FILE_FILTER_EXTENSION));
      chooser.setCurrentDirectory(new File(PATHNAME));
      int result = chooser.showOpenDialog(this);
      if (result == JFileChooser.APPROVE_OPTION) {
         this.path = chooser.getSelectedFile().getPath();
         this.toFront();
         this.repaint();
         pack();
      }
   }

   Optional<String> getPath() {
      return Optional.of(path);
   }
}