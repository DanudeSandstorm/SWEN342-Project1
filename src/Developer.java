package src;

public class Developer extends Actor {

  protected TeamLead lead;
  protected int waiting;
  protected boolean answered;
  
  public Developer(String name, Clock clock, ConferenceRoom room, TeamLead lead) {
      super(name, clock, room);
      this.lead = lead;
      waiting = 0;
      answered = false;
  }

  @Override
  protected void startDay() {
    //TODO!
  }

  /**
  * Randomly assigns a duration at which to perform work
  **/
  @Override
  protected void doingWork() {
    //TODO
    //Chance to have a question
    //Or
    //Work for random amount of time
      //question
      question();
      //work
      int duration = 0;
      doingWork(duration);
  }


  /**
  * Prints stats
  **/
  @Override
  protected void printStats() {
    printStats(working, lunch, meetings, waiting);
  }


  /**
  *
  **/
  private void scheduleDailyStandup() {
    //TODO
  }


  /**
  * Asks the team lead a question
  **/
  private void question() {
    answered = false;
    outputAction(name + " asks their team lead, " + 
                lead.getName() +", a question.");
    lead.askQuestion(this);

    while (!answered) {
      try { wait(); }
      catch (InterruptedException e) {}
    }
  }

  /**
  * @param duration- the emount of time spent (waiting on the manager)
  * in minutes or 0 if the lead was able to answer the question
  **/
  public void giveAnswer(int duration) {
    answered = true;
    waiting += duration;
    notify();
  }

}
