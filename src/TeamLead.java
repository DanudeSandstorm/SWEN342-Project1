package src;

import java.util.concurrent.*;

public class TeamLead extends Actor {

  protected Manager manager;
  protected int waiting;

  public TeamLead(String name, Clock clock, ConferenceRoom room, Manager manager) {
      super(name, clock, room);
      this.manager = manager;
      waiting = 0;
  }


  /**
  * Begin the day!
  * Gets start time and sets up shedual
  **/
  protected void startDay() {
    //TODO!
  }

  /**
  * Randomly assigns a duration at which to perform work
  **/
  @Override
  protected void doingWork() {
    //TODO
    //Work for random amount of time
    int duration = 0;
    doingWork(duration);
  }


  /**
  * @return the team lead's manager
  */
  protected Manager getManager() {
    return manager;
  }


  /**
  * Prints stats
  **/
  @Override
  protected void printStats() {
    printStats(working, lunch, meetings, waiting);
  }


  protected void scheduleDailyStandupWithManager() {
    //Meeting at 8:00 am
    addTask(new Task("Meeting", clock.convertTimeOfDay(480), clock.convertMinutes(15)) {
        @Override
        public void performTask() {
          outputAction(name + " arrived to the daily planning meeting.");
          //Wait for everyone to arrive
          CountDownLatch arrive = manager.dailyStandup();
          try { arrive.await(); } 
          catch (InterruptedException e) {}

          busy(15);
        }
      }
    );
  }


  protected void scheduleDailyStandupWithTeam() {
    //TODO
  }


  /**
  * Ask the team leader a question
  * If the team leader is able to answer, it calls give answer on the dev
  * Else it waits until the manager is available to ask the question
  */
  public synchronized void askQuestion(Developer dev) {
    final long start = System.currentTimeMillis();

    addTask(new Task("Question", start) {
      @Override
      public void performTask() {
          int time = 0;
          //TODO!
          //implement random 50% chance of answer
          boolean answer = true;

          // If can't answer, asks manager code
          if (!answer) {
            manager.askQuestion();
            long end = System.currentTimeMillis();
            time = clock.convertModelTime(end - start);
            waiting += time;

            outputAction(name + " asks their manager," +
                         manager.getName() + ", a question.");
            busy(10);
            
            outputAction("The manager, " + manager.getName() + 
                        ", answers the question.");
            dev.giveAnswer(time);
          }
          else {
            outputAction(name + " answers " + dev.getName() + "'s question.");
            dev.giveAnswer(0);
          }
        }
      }
    );
  }
  
}
