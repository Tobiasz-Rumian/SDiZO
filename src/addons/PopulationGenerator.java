package addons;

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
}
