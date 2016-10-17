

public class Clock {

  private long startTime = -1;
  

  /**
  * Constructor currently doesn't do anything.
  */
  public Clock() {
    
  }


  /**
  * Sets the real-time equivalent of the start of the simulated work day.
  * Pulls the current system time for use.
  */
  public void startDay() {
    startTime = System.currentTimeMillis();
  }


  /**
  * Returns the time that has passed since the start of the day in milliseconds.
  * Use getPrintableTime if you want the realtime equivalent.
  *
  * @return The current time of the simulation in real milliseconds that have
  * passed.
  */
  public long getTimePassedMillis() {
    return System.currentTimeMillis() - startTime;
  }


  /**
  * Returns a printable string version of the current simulated time.
  *
  * @return The current time in a printable string form.
  */
  public String getPrintableTime() { 
    long minutes = getTimePassedMillis()/10;
    int hours = minutes / 60 + 8;
    minutes = minutes % 60;

    //Doesn't print the difference between AM and PM, only work with times
    //between 8 AM and 5 PM. 
    return ((hours%12)+1) + ":" + minutes;
  }
}
