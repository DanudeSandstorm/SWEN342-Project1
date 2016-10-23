package src;

public class TeamLead extends Actor {

	public TeamLead(Clock clock) {
		super(clock);
		//additional schedule
	}

	public void run() {
		
	}

	/**
	* Ask the team leader a question 
	* @Return the emount of time spent waiting on the manager
	* or 0 if the lead was able to answer
	*/
	public int askQuestion() {
		return 0;
	}


	protected void scheduleMeetings() {

	}

	protected void scheduleLunch() {

	}

	protected void scheduleLeave(long start) {

	}
	
}
