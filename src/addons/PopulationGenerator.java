package addons;

import static addons.Settings.getHowManyElements;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;

/**
 * Generator rekordów.
 *
 * @author Tobiasz Rumian
 */
public class PopulationGenerator {

	private int[] population;

	PopulationGenerator() {
		population = new int[getHowManyElements()];
		for (int i = 0; i < getHowManyElements(); i++) {
			Random random = new Random();
			population[i] = random.nextInt();
		}
	}

	public static void main(String[] args) {
		View.printMessage(Settings.message());
		Settings.changeSettings();
		PopulationGenerator populationGenerator = new PopulationGenerator();
		populationGenerator.saveToFile();
	}

	int[] getPopulation() {
		return population;
	}

	private void saveToFile() {
		try (PrintStream out = new PrintStream(new FileOutputStream("population.txt"))) {
			StringBuilder stringBuilder = new StringBuilder();
			for (int aPopulation : population) {
				stringBuilder.append(aPopulation).append("\n");
			}
			out.print(stringBuilder.toString());
		} catch (Exception e) {
			e.getMessage();
		}
	}
}
