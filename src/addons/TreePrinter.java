package addons;

import structures.Node;

import java.util.ArrayList;
import java.util.List;

public class TreePrinter {

   /**
    * Print a tree
    *
    * @param root tree root node
    */
   public static String print(Node root) {
      StringBuilder stringBuilder = new StringBuilder();
      List<List<String>> lines = new ArrayList<>();

      List<Node> level = new ArrayList<>();
      List<Node> next = new ArrayList<>();

      level.add(root);
      int nn = 1;

      int widest = 0;

      while (nn != 0) {
         List<String> line = new ArrayList<>();

         nn = 0;

         for (Node n : level) {
            if (n == null) {
               line.add(null);

               next.add(null);
               next.add(null);
            } else {
               String aa = n.getText();
               line.add(aa);
                if (aa.length() > widest) {
                    widest = aa.length();
                }

               next.add(n.getLeft());
               next.add(n.getRight());

                if (n.getLeft() != null) {
                    nn++;
                }
                if (n.getRight() != null) {
                    nn++;
                }
            }
         }

          if (widest % 2 == 1) {
              widest++;
          }

         lines.add(line);

         List<Node> tmp = level;
         level = next;
         next = tmp;
         next.clear();
      }

      int perPiece = lines.get(lines.size() - 1).size() * (widest + 4);
      for (int i = 0; i < lines.size(); i++) {
         List<String> line = lines.get(i);
         int hpw = (int) Math.floor(perPiece / 2f) - 1;

         if (i > 0) {
            for (int j = 0; j < line.size(); j++) {

               // split node
               char c = ' ';
               if (j % 2 == 1) {
                  if (line.get(j - 1) != null) {
                     c = (line.get(j) != null) ? '┴' : '┘';
                  } else {
                      if (j < line.size() && line.get(j) != null) {
                          c = '└';
                      }
                  }
               }
               stringBuilder.append(c);

               // lines and spaces
               if (line.get(j) == null) {
                  for (int k = 0; k < perPiece - 1; k++) {
                     stringBuilder.append(" ");
                  }
               } else {

                  for (int k = 0; k < hpw; k++) {
                     stringBuilder.append(j % 2 == 0 ? " " : "─");
                  }
                  stringBuilder.append(j % 2 == 0 ? "┌" : "┐");
                  for (int k = 0; k < hpw; k++) {
                     stringBuilder.append(j % 2 == 0 ? "─" : " ");
                  }
               }
            }
            stringBuilder.append("\n");
         }

         // print line of numbers
         for (String f : line) {

             if (f == null) {
                 f = "";
             }
            int gap1 = (int) Math.ceil(perPiece / 2f - f.length() / 2f);
            int gap2 = (int) Math.floor(perPiece / 2f - f.length() / 2f);

            // a number
            for (int k = 0; k < gap1; k++) {
               stringBuilder.append(" ");
            }
            stringBuilder.append(f);
            for (int k = 0; k < gap2; k++) {
               stringBuilder.append(" ");
            }
         }
         stringBuilder.append("\n");

         perPiece /= 2;
      }
      return stringBuilder.toString();
   }
}
