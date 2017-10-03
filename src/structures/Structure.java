package structures;

import enums.Place;

/**
 * Interfejs pozwalający ujednolicić korzystanie ze struktur.
 */
public interface Structure {
   /**
    * Funkcja wyświetlająca informacje o strukturze.
    *
    * @return Zwraca informacje o strukturze w postaci String'a.
    */
   String info();

   /**
    * Funkcja pozwalająca usunąć element ze struktury.
    * UWAGA!
    * Argument place wykorzystywany jest jedynie dla tablic i list.
    * Nie jest wtedy brany pod uwagę argument number.
    * W przypadku pozostałych struktur, jedynie argument number brany jest pod uwagę.
    *
    * @param place  Miejsce, z którego ma być usunięty rekord.
    * @param number Wartość rekordu, który ma być usunięty.
    */
   void subtract(Place place, int number);

   /**
    * Funkcja pozwalająca dodać element do struktury.
    * UWAGA!
    * Argument place wykorzystywany jest jedynie dla tablic i list.
    * W przypadku pozostałych struktur, jedynie argument number brany jest pod uwagę.
    *
    * @param place  Miejsce, w które ma być wstawiony rekord.
    * @param number Wartość rekordu, który ma być wstawiony
    */
   void add(Place place, int number);

   /**
    * Funkcja pozwalająca na sprawdzenie, czy dany element jest w strukturze.
    *
    * @param find Wartość szukanego elementu.
    * @return Zwraca wartość logiczną zapytania o istnienie elementu.
    */
   boolean find(int find);

   /**
    * Funkcja zwracająca reprezentację graficzną struktury w postaci String'a
    *
    * @return Zwraca reprezentację graficzną struktury.
    */
   String show();

   /**
    * Funkcja zwracająca nazwę struktury.
    *
    * @return Zwraca nazwę struktury.
    */
   String toString();

   /**
    * Funkcja zwracająca liczbę elementów w strukturze.
    *
    * @return Zwraca liczbę elementów w strukturze.
    */
   int size();

   /**
    * Funkcja pozwalająca na wyczyszczenie struktury.
    */
   void clear();
}

