package addons;

/**
 * Ustawienia
 *
 * @author Tobiasz Rumian
 */
class Settings {
    private static Integer howManyElements = 1000;
    private static Integer howManyRepeats = 100;
    private static Integer howManyElementsBeforeStart=1000;

    static Integer getHowManyElements() {
        return howManyElements;
    }

    static Integer getHowManyRepeats() {
        return howManyRepeats;
    }

    static String message() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(View.title("ustawienia")).append("\n")
                .append("Ilosc elementów: ").append(howManyElements).append("\n")
                .append("Ilosc powtórzeń: ").append(howManyRepeats).append("\n");
        return stringBuilder.toString();
    }

    static void changeSettings() {
        View.message("Co chcesz zmienić?", false);
        View.message("1. Ilość elementów\n" + "2. Ilość powtórzeń\n", false);
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

    public static Integer getHowManyElementsBeforeStart() {
        return howManyElementsBeforeStart;
    }
}
