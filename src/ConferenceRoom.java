package src;

import java.util.concurrent.*;

public class ConferenceRoom {

  private final CountDownLatch finalMeeting;
  private CyclicBarrier standup;
  private TeamLead owner;


  public ConferenceRoom() {
    finalMeeting = new CountDownLatch(13);
    owner = null;
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

    return standup;
  }


  /**
  * @return a CyclicBarrier for the actor to wait on, 
  * or null if the room is reserved
  */
  public synchronized CyclicBarrier arrive(Developer d) {
    if(d.getLead().equals(standup)) {
      return standup;
    } else {
      return null;
    }
  }

  public synchronized CountDownLatch arriveFinal() {
    finalMeeting.countDown();
    return finalMeeting;
  }
}
