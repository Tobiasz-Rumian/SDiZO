package addons;

/**
 * Created by Tobiasz Rumian on 10.04.2017.
 */
public class TimeTracker{
    private Long startTime;

    public void start(){
        startTime=System.nanoTime();
    }

    public Long getElapsedTime(){
        return System.nanoTime()-startTime;
    }
}
