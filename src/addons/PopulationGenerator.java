package addons;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;

import static addons.Settings.getHowManyElements;
import static addons.Settings.getHowManyElementsBeforeStart;

/**
 * Generator rekordów.
 * @author Tobiasz Rumian
 */
public class PopulationGenerator {
    private int[] population;
    PopulationGenerator(){
        population=new int[getHowManyElements()+getHowManyElementsBeforeStart()];
        for(int i=0;i< getHowManyElements()+getHowManyElementsBeforeStart();i++){
            Random random = new Random();
            population[i]= random.nextInt();
        }
    }
    int[] getPopulation(){
        return population;
    }

    private void saveToFile(){
        try (PrintStream out = new PrintStream(new FileOutputStream("population.txt"))) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int aPopulation : population) stringBuilder.append(aPopulation).append("\n");
            out.print(stringBuilder.toString());
        }catch (Exception e){
            e.getMessage();
        }
    }

    public static void main(String[] args) {
        Settings.message();
        Settings.changeSettings();
        PopulationGenerator populationGenerator=new PopulationGenerator();
        populationGenerator.saveToFile();
    }
}
