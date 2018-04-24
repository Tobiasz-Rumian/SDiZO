package addons;

import java.math.BigDecimal;
import java.time.LocalTime;

/**
 * Klasa pozwalająca na śledzenie czasu w milisekundach.
 *
 * @author Tobiasz Rumian
 */
class TimeTracker {
    public static final int NANOSECONDS_IN_SECOND = 1000000000;
    private LocalTime startTime;

    /**
     * Funkcja rozpoczynająca odliczanie czasu czasu.
     */
    void start() {
        startTime = LocalTime.now();
    }

    /**
     * Funkcja podająca różnice między czasem w danej chwili, a czasem startowym.
     *
     * @return Zwraca różnicę  czasów.
     */
    BigDecimal getElapsedTime() {
        LocalTime endTime = LocalTime.now();
        BigDecimal endTimeInNanos = BigDecimal.valueOf(endTime.getSecond())
                .multiply(BigDecimal.valueOf(NANOSECONDS_IN_SECOND))
                .add(BigDecimal.valueOf( endTime.getNano()));
        BigDecimal startTimeInNanos = BigDecimal.valueOf(startTime.getSecond())
                .multiply(BigDecimal.valueOf(NANOSECONDS_IN_SECOND))
                .add(BigDecimal.valueOf(startTime.getNano()));
        return endTimeInNanos.subtract(startTimeInNanos);
    }
}
