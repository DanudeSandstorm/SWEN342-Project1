package src;

import java.util.concurrent.*;

public class Manager extends Actor {


  private final CountDownLatch dailyMeeting;

  public Manager(Clock clock, ConferenceRoom room) {
    super(clock, room);
    dailyMeeting = new CountDownLatch(3);
  }

  public void run() {
    startDay();
    //additional schedule
    //TODO
  }

  public synchronized CountDownLatch dailyPlanningMeeting() {
    dailyMeeting.countDown();
    return dailyMeeting;
  }

  public synchronized void askQuestion() {
    //TODO!
  }

  protected void scheduleMeetings() {
    addTask(new Task("Meeting", clock.convertTimeOfDay(600), clock.convertMinutes(60)) {
        @Override
        public void performTask() {
          outputAction("Manager went to meeting");
          busy(60);
        }
      }
    );

    addTask(new Task("Meeting", clock.convertTimeOfDay(840), clock.convertMinutes(60)) {
        @Override
        public void performTask() {
          outputAction("Manager went to meeting");
          busy(60);
        }
      }
    );
  }

  protected void scheduleLunch() {
    //Lunch at 12:00 for 1 hour
    addTask(new Task("Lunch", clock.convertTimeOfDay(720), clock.convertMinutes(60)) {
        @Override
        public void performTask() {
          outputAction("Manager went to lunch");
          busy(60);
        }
      }
    );
  }

  protected void scheduleLeave(long start) {
    addTask(new Task("Leave", clock.convertTimeOfDay(1020), clock.convertMinutes(60)) {
        @Override
        public void performTask() {
          outputAction("Manager went home");
          //TODO!
          //shutdown the thread
        }
      }
    );
  }

}
