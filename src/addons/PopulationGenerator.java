package addons;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;

import static addons.Settings.getHowManyElements;

/**
 * Created by Tobiasz Rumian on 10.04.2017.
 */
public class PopulationGenerator {
    private Integer[] population;
    public PopulationGenerator(){
        population=new Integer[getHowManyElements()];
        for(int i=0;i< getHowManyElements();i++){
            Random random = new Random();
            population[i]= random.nextInt();
        }
    }
    public Integer[] getPopulation(){
        return population;
    }
    public void saveToFile(){
        try (PrintStream out = new PrintStream(new FileOutputStream("population.txt"))) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Integer aPopulation : population) stringBuilder.append(aPopulation).append("\n");
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
