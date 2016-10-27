package src;

public class Developer extends Actor {

  protected TeamLead lead;
  protected int waiting;
  protected long lastQuestion;
  protected volatile boolean answered;
  
  public Developer(String name, Clock clock, ConferenceRoom room, TeamLead lead) {
      super(name, clock, room);
      this.lead = lead;
      waiting = 0;
      lastQuestion = 0;
      answered = false;
  }

  @Override
  protected void startDay() {
    //TODO!
    //Schedualing lunch needs to not exceed the length of day
    //(user can't leave after 5) 
  }

  /**
  * Randomly assigns a duration at which to perform work
  **/
  @Override
  protected void doingWork() {
    boolean question = false;
    
    //As time goes on, the chances that a developer asks a question increases
    if (Math.random() < (1 - (Math.exp(-(clock.getTimePassedMillis() - lastQuestion))))){
      question = true;
    }
    
    if (question) {
      lastQuestion = clock.getTimePassedMillis();
      question();
    }
    else {
      //Work for a random amount of time between 0 and 60 minutes
      int duration = (int)(Math.random() * clock.convertMinutes(60));
      doingWork(duration);
    }
  }


  /**
  * @return the developer's team lead
  */
  protected TeamLead getLead() {
    return lead;
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
    //In the daily standup task, the developer must keep "arriving"
    //at the conference room until their team lead is its owner
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
  * @param duration- the amount of time spent (waiting on the manager)
  * in minutes or 0 if the lead was able to answer the question
  **/
  public void giveAnswer(int duration) {
    answered = true;
    waiting += duration;
    notify();
  }

}
