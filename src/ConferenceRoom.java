package src;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ConferenceRoom {

    private TeamLead owner;

    private CyclicBarrier finalMeeting;
    private CyclicBarrier standUp;

    public ConferenceRoom() {
        owner = null;
        standUp = null;
        finalMeeting = new CyclicBarrier(13, new Runnable() {
            public void run() {
                try {
                    // Sleep for the duration of the meeting
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    /**
     * Lets the team lead reserve the conference room. The team lead reserves
     * the room for his meeting with his reference and the number of team
     * members he's waiting for. He and all future members that arrive wait for
     * everyone to be there before starting the meeting.
     */
    public void reserveRoom(TeamLead lead, int members) {
        synchronized (this) {
            while (owner != null) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            owner = lead;
            standUp = new CyclicBarrier(members, new Runnable() {
                public void run() {
                    try {
                        // Sleep for the duration of the meeting
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });

        }

        arrive(lead);

        synchronized (this) {
            owner = null;
            count = 0;
            this.notifyAll();
        }
    }

    int count = 0;

    /**
     * Lets a team member arrive at the conference room. A team member arrives
     * at the conference room and asks if it's currently reserved by their team
     * lead. If it is, they stay for the meeting and arrive returns true,
     * otherwise it returns false and returns immediately.
     */
    public boolean arrive(TeamLead lookingFor) {
        // If the check is passed then it cannot possibly change until the
        // barrier passes.
        synchronized (this) {
            if (owner == null || !owner.equals(lookingFor)) {
                return false;

            }
        }
        try {
            standUp.await();
        } catch (InterruptedException e) {
        } catch (BrokenBarrierException e) {
        }
        return true;

    }

    public void arriveFinal() {
        try {
            finalMeeting.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
