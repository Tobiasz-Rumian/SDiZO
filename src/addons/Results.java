package addons;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tobiasz Rumian on 10.04.2017.
 */
public class Results {
    private Map<String,Long> results = new HashMap<>();
    public void add(String label, Long value){
        if(results.containsKey(label)) results.replace(label,value);
        results.put(label,value);
    }
    public void clear(){
        results.clear();
    }
    public void save(){
        try (PrintStream out = new PrintStream(new FileOutputStream("results.txt"))) {
            StringBuilder stringBuilder = new StringBuilder();
            results.forEach((n,v)->stringBuilder.append(n).append("\t").append(v).append("\n"));
            out.print(stringBuilder.toString());
        }catch (Exception e){
            e.getMessage();
        }
    }
    public String show(){
        StringBuilder stringBuilder = new StringBuilder();
        results.forEach((n,v)->stringBuilder.append(n).append("\t").append(v).append("\n"));
        return stringBuilder.toString();
    }
}
