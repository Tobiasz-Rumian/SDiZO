package addons;

/**
 * Klasa pozwalająca na śledzenie czasu w milisekundach.
 * @author Tobiasz Rumian
 */
class TimeTracker{
    private Long startTime;

    /**
     * Funkcja rozpoczynająca odliczanie czasu czasu.
     */
    void start(){
        startTime=System.nanoTime();
    }

    /**
     * Funkcja podająca różnice między czasem w danej chwili, a czasem startowym.
     * @return Zwraca różnicę  czasów.
     */
    Long getElapsedTime(){
        return System.nanoTime()-startTime;
    }
}
