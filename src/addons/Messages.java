package addons;

import view.View;

/**
 * Created by Tobiasz Rumian on 10.04.2017.
 */
public class Messages {
    public static String messageStart() {
        return "Witaj " + System.getProperty("user.name") + "." + "\n" +
                "Uruchomiles projekt nr 1 autorstwa Tobiasza Rumiana." + "\n" +
                "Rozsiadz sie wygodnie i wybierz co chcesz zrobic.";
    }

    public static String messageMainMenu() {
        return View.title("menu glowne") +
                "1. Tablica\n" +
                "2. Lista dwukierunkowa\n" +
                "3. Kopiec binarny\n" +
                "4. Drzewo BST\n" +
                "5. Drzewo czerwono czarne\n" +
                "0. Wyjscie";
    }

    public static String messageTask() {
        return View.title("wybor zadania") +
                "1. info" + "\n" +
                "2. Wczytaj z pliku" + "\n" +
                "3. Usun" + "\n" +
                "4. Dodaj" + "\n" +
                "5. Znajdz" + "\n" +
                "6. Wyswietl" + "\n" +
                "7. Test" + "\n" +
                "0. wyjscie";
    }

    public static String messageTest() {
        return View.title("test") +
                "1. Generuj populację struktury\n" +
                "2. Usun ze struktury\n" +
                "3. Wyszukaj w strukturze\n" +
                "4. Ustawienia\n" +
                "5. Pokaż wyniki\n"+
                "0. Zakończ test\n";
    }
}
