package addons;

import view.View;

/**
 * Created by Tobiasz Rumian on 10.04.2017.
 */
public class Settings {
    private static Integer howManyElements = 1000;
    private static Integer howManyRepeats = 100;

    public static Integer getHowManyElements() {
        return howManyElements;
    }

    public static Integer getHowManyRepeats() {
        return howManyRepeats;
    }

    public static void message() {
        View.message(View.title("ustawienia"), false);
        View.message("Ilosc elementów: " + howManyElements, false);
        View.message("Ilosc powtórzeń: " + howManyRepeats, false);
    }

    public static void changeSettings() {
        View.message("Co chcesz zmienić?", false);
        View.message("1. Ilość elementów\n" + "2. Ilość powtórzeń\n", false);
        switch (View.select("Wybierz liczbę",1,2)){
            case 1:Settings.howManyElements=View.select("Podaj liczbę elementów",1,Integer.MAX_VALUE);
                break;
            case 2:Settings.howManyRepeats=View.select("Podaj liczbę powtórzeń",1,Integer.MAX_VALUE);
                break;
        }
    }
}
