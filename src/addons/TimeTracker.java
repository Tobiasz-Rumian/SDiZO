package addons;

import java.math.BigDecimal;

/**
 * Klasa pozwalająca na śledzenie czasu w milisekundach.
 *
 * @author Tobiasz Rumian
 */
class TimeTracker {

	private long startTime;

	/**
	 * Funkcja rozpoczynająca odliczanie czasu czasu.
	 */
	void start() {
		startTime = System.nanoTime();
	}

	/**
	 * Funkcja podająca różnice między czasem w danej chwili, a czasem startowym.
	 *
	 * @return Zwraca różnicę  czasów.
	 */
	BigDecimal getElapsedTime() {
		return BigDecimal.valueOf(System.nanoTime() - startTime);
	}
}
