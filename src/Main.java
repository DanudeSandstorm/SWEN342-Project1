package src;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

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
    for (int i = 0; i < actors.size(); i++)
    {
      es.submit(actors.get(i));
    }

    //Wait for all threads to be started before starting the day
    clock.startClock();

  }
}
