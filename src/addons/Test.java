package addons;

import static addons.Settings.changeSettings;
import static addons.Settings.getHowManyElements;
import static addons.Settings.getHowManyRepeats;
import static addons.Settings.setSettings;
import static addons.View.printMessage;

import enums.Place;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import structures.BidirectionalList;
import structures.BinaryHeap;
import structures.BstTree;
import structures.Structure;
import structures.Table;

public class Test {

	private Random random = new Random();
	private Results results = new Results();
	private Structure structure;
	private final TimeTracker tracker = new TimeTracker();

	public Test(int task, Place place, Structure structure) {
		this.structure = structure;
		BigDecimal resultTime = new BigDecimal(0);
		printMessage(Messages.messageTest());
		switch (task) {
		case 1://Generuj populację struktury
			addToStructureTest(place, resultTime);
			break;
		case 2://Usun ze struktury
			deleteFromStructureTest(place, resultTime);
			break;
		case 3://Wyszukaj w strukturze
			findInStructureTest(resultTime);
			break;
		case 4://Ustawienia
			printMessage(Settings.message());
			changeSettings();
			break;
		case 5://Pokaż wyniki
			printMessage(results.show());
			break;
		case 0://Zakończ test.txt
			break;
		}
	}

	private void findInStructureTest(BigDecimal resultTime) {
		PopulationGenerator populationGenerator;
		String label;
		for (int i = 0; i < getHowManyRepeats(); i++) {
			printProgress(i," Wyszukiwanie");
			populationGenerator = new PopulationGenerator();
			if (isTable()) {
				((Table) structure).addAll(populationGenerator.getPopulation());
				resultTime = doFindTest(resultTime);
			} else {
				for (int k = 0; k < populationGenerator.getPopulation().length; k++) {
					structure.add(Place.END, populationGenerator.getPopulation()[k]);
				}
				resultTime = doFindTest(resultTime);
			}
		}
		resultTime = resultTime.divide(BigDecimal.valueOf(getHowManyRepeats()), RoundingMode.HALF_DOWN);
		label = getLabel("Wyszukiwanie", "-");
		printMessage(resultTime.toString());
		results.add(label, resultTime.longValue());
		structure.clear();
	}

	private boolean isTable() {
		return structure.getClass() == Table.class;
	}

	private String getLabel(String task, String place) {
		return structure.toString() + "\t" + task + "\t" + place + "\t" + getHowManyElements() + "\t" + getHowManyRepeats();
	}

	private BigDecimal doFindTest(BigDecimal resultTime) {
		tracker.start();
		structure.find(random.nextInt());
		resultTime = resultTime.add(tracker.getElapsedTime());
		return resultTime;
	}

	private void printProgress(int i,String text) {
		printMessage(showProgress(i, getHowManyRepeats()) + "     " + structure.toString() + "  " + getHowManyElements()
			+ text);
	}

	private void deleteFromStructureTest(Place place, BigDecimal resultTime) {
		PopulationGenerator populationGenerator;
		String label;
		for (int i = 0; i < getHowManyRepeats(); i++) {
			printProgress(i," Odejmowanie");

			populationGenerator = new PopulationGenerator();
			if (isTable()) {
				((Table)structure).addAll(populationGenerator.getPopulation());
				doDeleteTest(place,resultTime);
			} else {
				for (int k = 0; k < populationGenerator.getPopulation().length; k++) {
					structure.add(Place.END, populationGenerator.getPopulation()[k]);
				}
				resultTime = doDeleteTest(place, resultTime);
			}
		}
		resultTime = resultTime.divide(BigDecimal.valueOf(getHowManyRepeats()), RoundingMode.UP);
		label = getLabel("Usuwanie", place.toString());
		printMessage(resultTime.toString());
		results.add(label, resultTime.longValue());
	}

	private BigDecimal doDeleteTest(Place place, BigDecimal resultTime) {
		tracker.start();
		structure.subtract(place, random.nextInt());
		resultTime = resultTime.add(tracker.getElapsedTime());
		structure.clear();
		return resultTime;
	}

	private void addToStructureTest(Place place, BigDecimal resultTime) {
		PopulationGenerator populationGenerator;
		String label;
		for (int i = 0; i < getHowManyRepeats(); i++) {
			printProgress(i," Dodawanie");

			populationGenerator = new PopulationGenerator();
			if (isTable()) {
				((Table)structure).addAll(populationGenerator.getPopulation());
				structure.subtract(Place.END, 0);
				resultTime=doAddTest(place,resultTime,random.nextInt());
			} else {
				if (isRandom(place)) {
					fillStructure(Place.END, populationGenerator);
					int add = populationGenerator.getPopulation()[populationGenerator.getPopulation().length - 1];
					resultTime = doAddTest(place, resultTime, add);
				} else {
					fillStructure(place, populationGenerator);
					int add = populationGenerator.getPopulation()[populationGenerator.getPopulation().length - 1];
					resultTime = doAddTest(place, resultTime, add);
				}
			}
		}
		resultTime = resultTime.divide(BigDecimal.valueOf(getHowManyRepeats()), RoundingMode.UP);
		label = getLabel("Dodawanie", place.toString());
		printMessage(resultTime.toString());
		results.add(label, resultTime.longValue());
	}

	private void fillStructure(Place place, PopulationGenerator populationGenerator) {
		for (int j = 0; j < populationGenerator.getPopulation().length - 1; j++) {
			structure.add(place, populationGenerator.getPopulation()[j]);
		}
	}

	private BigDecimal doAddTest(Place place, BigDecimal resultTime, int add) {
		tracker.start();
		structure.add(place, add);
		resultTime = resultTime.add(tracker.getElapsedTime());
		structure.clear();
		return resultTime;
	}

	private boolean isRandom(Place place) {
		return place == Place.RANDOM;
	}

	private String showProgress(Integer now, Integer end) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		Integer percent = (int) ((now * 100.0f) / end);
		for (int i = 0; i <= percent; i++) {
			stringBuilder.append("=");
		}
		for (int i = 0; i <= 100 - percent; i++) {
			stringBuilder.append(" ");
		}
		stringBuilder.append("]");
		stringBuilder.append(" ").append(percent).append("%");
		return stringBuilder.toString();
	}

	public Results getResults() {
		return results;
	}

	/**
	 * Funkcja pozwalająca na wykonanie pełnych testów.
	 */
	public static void fullTest() {
		int[] howMany = { 100000, 200000, 300000, 400000, 500000, 600000, 700000, 800000, 900000, 1000000 };
		List<Structure> structures = Arrays.asList(new Table());//, new BidirectionalList(), new BinaryHeap(), new BstTree()
		List<Place> places = Arrays.asList(Place.START);//, Place.END, Place.RANDOM
		List<Integer> tests = Arrays.asList(1);//Dodawanie, odejmowanie, szukanie , 2, 3
		final Results results = new Results();
		structures.forEach(structure -> tests.forEach(test -> {
			if (isTableOrBidirectionalListAndTestNotThree(structure, test)) {
				places.forEach(place -> testMultipleTimes(howMany, results, structure, test, place));
			} else {
				testMultipleTimes(howMany, results, structure, test, Place.NULL);
			}
		}));
		results.save();
		results.clear();
	}

	private static void testMultipleTimes(int[] howMany, Results results, Structure structure, int test, Place place) {
		Test currentTest;
		for (int how : howMany) {
			setSettings(how, getHowManyRepeats());
			currentTest = new Test(test, place, structure);
			currentTest.getResults().getResults().forEach(results::add);
		}
	}

	private static boolean isTableOrBidirectionalListAndTestNotThree(Structure structure, int test) {
		boolean isTable = structure.getClass() == Table.class;
		boolean isBidirectionalList = structure.getClass() == BidirectionalList.class;
		boolean isTestNotEqualThree = test != 3;
		return (isTable && isTestNotEqualThree) || (isBidirectionalList && isTestNotEqualThree);
	}
}
