package src;

import java.util.HashMap;
import java.util.Map;

public abstract class Actor extends Thread {

  protected Clock clock;
  protected ConferenceRoom conferenceRoom;
  
  //Shouldn't need to worry about concurrency, each thread only edits its own
  //stats
  private Map<String,Integer> stats = new HashMap<String,Integer>();
  
  public Actor(String name, Clock clock, ConferenceRoom conferenceRoom) {
      super(name);
      this.clock = clock;
      this.conferenceRoom = conferenceRoom;
  }

  protected abstract void idleUntil(int relativeMinutes);

  /* Utilities for all Actors */
  
  protected void addStat(String stat, int time) {
      if(stats.containsKey(stat)) {
          stats.replace(stat, stats.get(stat) + time);
      } else {
          stats.put(stat, time);
      }
  }
  
  protected void busy(int sleepTime) {
      try {
        Thread.sleep(sleepTime);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
  }
  
  protected void printWithTime(String text) {
    System.out.println(clock.getPrintableTime() + " " + text);
    FileWriter.writeLine(text);
  }
}
