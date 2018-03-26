package addons;

/**
 * Ustawienia
 *
 * @author Tobiasz Rumian
 */
public class Settings {
    private static Integer howManyElements = 1000;
    private static Integer howManyRepeats = 100;
    public static boolean x=false;
    static Integer getHowManyElements() {
        return howManyElements;
    }

    static Integer getHowManyRepeats() {
        return howManyRepeats;
    }

    static String message() {
        return View.title("ustawienia") + "\n" +
                "Ilosc elementów: " + howManyElements + "\n" +
                "Ilosc powtórzeń: " + howManyRepeats + "\n";
    }

    static void changeSettings() {
        View.printMessage("Co chcesz zmienić?");
        View.printMessage("1. Ilość elementów\n" + "2. Ilość powtórzeń\n");
        switch (View.select("Wybierz liczbę",1,2)){
            case 1:Settings.howManyElements=View.select("Podaj liczbę elementów",1,Integer.MAX_VALUE);
                break;
            case 2:Settings.howManyRepeats=View.select("Podaj liczbę powtórzeń",1,Integer.MAX_VALUE);
                break;
        }
    }
    static void setSettings(int howManyElements,int howManyRepeats){
        Settings.howManyElements=howManyElements;
        Settings.howManyRepeats=howManyRepeats;
    }
}
