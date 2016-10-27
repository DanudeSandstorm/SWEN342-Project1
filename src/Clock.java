package src;

public class Clock {

  private long startTime;
  private final int CONVERSION_RATE = 10;
  boolean started;

  /**
  * Constructor sets the time to the current system time.
  * The day will be more accurate if startClock is called right before starting
  * the actor threads
  */
  public Clock() {
    startTime = System.currentTimeMillis();
    started = false;
  }


  /**
  * Allows the clock to be set to start at the same time as the Actor threads.
  * Uses system time as the analogue for the start of the work day.
  */
  public synchronized void startClock() {
    startTime = System.currentTimeMillis();
    this.notifyAll();
    started = true;
  }
  
  
  /**
   * Let's all of the actor threads wait for the clock to start before doing
   * their daily activities.
   */
  public synchronized void awaitStart() {
      try {
        if(!started) {
            this.wait();
        }
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
  }
  
  /**
   * Converts simulated minutes into real time.
   * 
   * @param minutes
   * @return
   */
  public int convertMinutes(int minutes) {
      return minutes * CONVERSION_RATE;
  }
  
  public int convertSimulated(int sim) {
      return sim / CONVERSION_RATE;
  }
  
  public int AbsoluteMinutes(int minutes) {
      return minutes - 480;
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
    long hours = minutes / 60 + 8;
    minutes = minutes % 60;

    //Doesn't print the difference between AM and PM, only work with times
    //between 8 AM and 5 PM. 
    return ((hours%12)+1) + ":" + minutes;
  }
}
