package src;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class Main {


  public static void main(String[] args) {

    final ExecutorService es = Executors.newFixedThreadPool(13);
    
    final ArrayList<Actor> actors = new ArrayList<Actor>();

    /** Create shared objects **/
    final Clock clock = new Clock();

    final ConferenceRoom conferenceRoom = new ConferenceRoom();

    /** Create threads **/
    // 1 manager
    // 3 team leads (3 per manager)
    // 9 developers (3 per lead)

    /** Run threads **/
    for (int i = 0; i < actors.size(); i++)
    {
      es.submit(actors.get(i));
    }

    //Wait for all threads to be started before starting day
    clock.startClock();

  }
}
