package src;

import java.util.concurrent.*;

public class ConferenceRoom {

  private final CountDownLatch finalMeeting;
  private CyclicBarrier standup;
  private TeamLead owner = null;


  public ConferenceRoom() {
    finalMeeting = new CountDownLatch(13);
  }


  /**
  * Lets the team lead reserve the conference room.
  * The team lead reserves the room for his meeting with his reference and the
  * number of team members he's waiting for. He and all future members that
  * arrive wait for everyone to be there before starting the meeting.
  */
  public synchronized CyclicBarrier reserveRoom(TeamLead lead) {

    //TODO
    //lock
      owner = lead;
      standup = new CyclicBarrier(4);

    return arrive(lead);
  }


  /**
  * @return a CyclicBarrier for the actor to wait on, 
  * or null if the room is reserved
  */
  public synchronized CyclicBarrier arrive(Actor actor) {
    if ((actor instanceof TeamLead && actor == owner) ||
       (actor instanceof Developer && ((Developer) actor).getLead() == owner)) {
      return standup;
    }

    return null;
  }

  public synchronized CountDownLatch arriveFinal() {
    finalMeeting.countDown();
    return finalMeeting;
  }
}
