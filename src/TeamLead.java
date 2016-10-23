package src;

public class TeamLead extends Actor {

	public TeamLead(Clock clock) {
		super(clock);
	}

	public void run() {
		startDay();
		//additional schedule
		//TODO
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


	protected void scheduleMeetings() {
		//TODO!
	}

	protected void scheduleLunch() {
		//TODO!
	}

	protected void scheduleLeave(long start) {
		//TODO!
	}
	
}
