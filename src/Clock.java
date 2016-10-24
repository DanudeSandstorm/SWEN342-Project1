package src;

public class Clock {
  /** Attributes **/
  private long startTime;
  private boolean started;
  
  /** Constructor **/
  public Clock() {
    started = false;
  }


  /**
  * Allows the clock to be set to start at the same time as the Actor threads.
  * Uses system time as the analogue for the start of the work day.
  **/
  public void startClock() {
    startTime = System.currentTimeMillis();
    started = true;
    notifyAll(); //Day starts
  }

  /**
  * Locks threads until the clock has started
  **/
  public synchronized long startDay() {
    while (!started) {
      try { wait(); }
      catch (InterruptedException e) {}
    }
    
    return startTime;
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
  * Converts minutes into the equivilent time in model
  **/
  public long convertMinutes(int minutes) {
    //TODO
    return (long) 0;
  }


  /**
  * Converts a period of the model's time scale to corrisponding minutes
  **/
  public int convertModelTime(long time) {
    //TODO
    return 0;
  }


  /**
  * Converts time of day in minutes to equivilent time in model
  * example: noon is 720 minutes
  **/
  public long convertTimeOfDay(int minutes) {
    //TODO
    //offset = minutes + math
    //return startTime + offset
    return (long) 0;
  }

  /**
  * Returns a printable string version of the current simulated time.
  *
  * @return The current time in a printable string form.
  */
  public String getPrintableTime() { 
    long minutes = getTimePassedMillis()/10;
    long hours = minutes / 60 + 8;
    minutes = minutes % 60;

    //Doesn't print the difference between AM and PM, only work with times
    //between 8 AM and 5 PM. 
    return ((hours%12)+1) + ":" + minutes;
  }
}
