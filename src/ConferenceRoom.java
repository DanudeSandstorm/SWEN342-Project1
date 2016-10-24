package src;

import java.util.concurrent.*;

public class ConferenceRoom {

  private TeamLead owner;
  private int waitingFor;

  private final CountDownLatch finalMeeting;

  public ConferenceRoom() {
    owner = null;
    waitingFor = 0;
    finalMeeting = new CountDownLatch(13);
  }


  /**
  * Lets the team lead reserve the conference room.
  * The team lead reserves the room for his meeting with his reference and the
  * number of team members he's waiting for. He and all future members that
  * arrive wait for everyone to be there before starting the meeting.
  */
  public synchronized void reserveRoom(TeamLead lead, int members) {
    owner = lead;
    waitingFor = members;
  }


  /**
  * Lets a team member arrive at the conference room.
  * A team member arrives at the conference room and asks if it's currently
  * reserved by their team lead. If it is, they stay for the meeting and arrive
  * returns true, otherwise it returns false and returns immediately.
  */
  public synchronized boolean arrive(TeamLead lookingFor) {
    if (owner == null || !owner.equals(lookingFor)) {
      return false;
    } else {
      try {
        this.wait();
      }
      catch (InterruptedException e) {
        return false;
      }
      return true;
    }
  }

  public synchronized CountDownLatch arriveFinal() {
    finalMeeting.countDown();
    return finalMeeting;
  }
}
