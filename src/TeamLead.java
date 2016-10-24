package src;

public class TeamLead extends Actor {

	private Manager manager;

	public TeamLead(String name, Clock clock, ConferenceRoom room, Manager manager) {
    	super(name, clock, room);
    	this.manager = manager;
	}

	public void run() {
		//TODO
		//offset day start random up to 30 minutes
		startDay();
		
		//TODO
		//Main execution loop
		while (!leave) {

		}
	}


	/**
	* Begin the day!
	* Gets start time and sets up shedual
	**/
	protected void startDay() {
		//TODO!
	}


	protected void scheduleDailyPlanningMeeting() {
	  //Meeting at 8:00 am
	  addTask(new Task("Meeting", clock.convertTimeOfDay(480), clock.convertMinutes(15)) {
	      @Override
	      public void performTask() {
	        outputAction(name + " arrived to the daily planning meeting.");
	        //Wait for everyone to arrive
	        CountDownLatch arrive = manager.dailyPlanningMeeting();
	        try { arrive.await(); } 
	        catch (InterruptedException e) {}

	        busy(15);
	      }
	    }
	  );
	}

	/**
	* Ask the team leader a question 
	* @Return the emount of time spent waiting on the manager
	* or 0 if the lead was able to answer
	*/
	public synchronized long askQuestion() {
		//TODO!
		return 0;
	}
	
}
