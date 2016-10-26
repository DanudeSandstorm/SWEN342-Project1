package src;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {


  public static void main(String[] args) {

    final ExecutorService es = Executors.newFixedThreadPool(13);
    final ArrayList<Actor> actors = new ArrayList<Actor>();

    /** Create shared objects **/
    final Clock clock = new Clock();
    final ConferenceRoom conferenceRoom = new ConferenceRoom();

    /** Create threads **/
    Manager manager = new Manager("Manager", clock, conferenceRoom);
    actors.add(manager);
    for(int i = 0; i < 3; i++) {
      TeamLead tl = new TeamLead("Team Lead " + i, clock, conferenceRoom,
	  manager);
      actors.add(tl);
      for(int j = 0; j < 3; j++) {
	actors.add(new Developer("Developer " + i + ":" + j, clock,
          conferenceRoom, tl));
      }
    }

    /** Run threads **/
    for(Actor act : actors) {
      act.start();
    }

    //Wait for all threads to be started before starting the day
    clock.startClock();

    //Wait for all Threads to finish then clean up.
    for(Actor act : actors) {
      try {
        act.join();
      } catch(InterruptedException e) {e.printStackTrace();}
    }
    
    FileWriter.terminateWriter();
  }
}
