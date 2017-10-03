package addons;

import enums.Place;
import loader.FileLoader;
import selector.Selector;
import structures.BidirectionalList;
import structures.BinaryHeap;
import structures.BstTree;
import structures.Structure;
import structures.Table;
import test.Test;
import view.Formatter;
import view.Message;

public class View {
   private Structure structure;

   private View() {
      Message.displayNormal(Messages.messageStart());
      while (true) {
         Message.displayNormal(Messages.messageMainMenu());
         int i = Selector.integerFromRange("Podaj numer zadania:", 0, 5);
         switch (i) {
            case 1:
               selectStructure(new Table());
               break;
            case 2:
               selectStructure(new BidirectionalList());
               break;
            case 3:
               selectStructure(new BinaryHeap());
               break;
            case 4:
               structure = new BstTree();
               Message.displayNormal("Z równoważeniem? (0-false,1-true)");
               int z = Selector.integerFromRange("Podaj numer:", 0, 1);
               Settings.x = z != 0;
               selectTask();
               break;
            case 5:
               Test.startFullTest();
               return;
            case 0:
               return;
         }
      }
   }

   public static void main(String[] args) {
      new View();
   }

   private void selectStructure(Structure structure) {
      this.structure = structure;
      selectTask();
   }

   private void selectTask() {
      do {
         Message.displayNormal(Messages.messageTask());
         Place place = Place.NULL;

         switch (Selector.integerFromRange("Podaj numer zadania:", 0, 7)) {
            case 1:
               Message.displayNormal(structure.info());
               break;
            case 2:
               structure.clear();
               FileLoader.loadFromFile(structure);
               Message.displayNormal(structure.show());
               break;
            case 3:
               Message.displayNormal(Formatter.toTitle("odejmowanie"));
               if (structure.getClass() == Table.class) {
                  ((Table) structure).subtract(Selector.integerFromRange("Podaj indeks", Integer.MIN_VALUE, Integer.MAX_VALUE));
               } else if (structure.getClass() == BidirectionalList.class) {
                  ((BidirectionalList) structure).subtract(Selector.integerFromRange("Podaj wartość", Integer.MIN_VALUE, Integer.MAX_VALUE));
               } else {
                  structure.subtract(place, Selector.integerFromRange("Podaj wartość", Integer.MIN_VALUE, Integer.MAX_VALUE));
               }
               Message.displayNormal(structure.show());
               break;
            case 4:
               Message.displayNormal(Formatter.toTitle("dodawanie"));
               if (structure.getClass() == Table.class) {
                  ((Table) structure).add(Selector.integerFromRange("Podaj indeks", Integer.MIN_VALUE, Integer.MAX_VALUE),
                          Selector.integerFromRange("Podaj wartość", Integer.MIN_VALUE, Integer.MAX_VALUE));
               } else if (structure.getClass() == BidirectionalList.class) {
                  ((BidirectionalList) structure).add(Selector.integerFromRange("Podaj indeks", Integer.MIN_VALUE, Integer.MAX_VALUE),
                          Selector.integerFromRange("Podaj wartość", Integer.MIN_VALUE, Integer.MAX_VALUE));
               } else {
                  structure.add(place, Selector.integerFromRange("Podaj wartość", Integer.MIN_VALUE, Integer.MAX_VALUE));
               }
               Message.displayNormal(structure.show());
               break;
            case 5:
               Message.displayNormal(Formatter.toTitle("znajdowanie"));
               if (structure.find(Selector.integerFromRange("Podaj liczbe", Integer.MIN_VALUE, Integer.MAX_VALUE))) {
                  Message.displayNormal("Liczba znajduje się w strukturze");
               } else {
                  Message.displayNormal("Liczba nie znajduje się w strukturze");
               }
               Message.displayNormal(structure.show());
               break;
            case 6:
               Message.displayNormal(structure.show());
               break;
            case 7:
               while (true) {
                  Message.displayNormal(Messages.messageTest());
                  int select = Selector.integerFromRange("Podaj numer zadania:", 0, 5);
                  if (select == 0) {
                     break;
                  }
                  if (select == 1 || select == 2) {
                     if (structure.getClass() == Table.class || structure.getClass() == BidirectionalList.class) {
                        place = Selector.place("Podaj miejsce, w które chcesz wstawiać");
                     } else {
                        place = Place.NULL;
                     }
                  }
                  Test.startTest(select, place, structure);
               }
               break;
            case 0:
               return;
         }
      } while (true);
   }
}
