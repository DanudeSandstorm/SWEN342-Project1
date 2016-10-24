package src;

public class TeamLead extends Actor {

	public TeamLead(String name, Clock clock, ConferenceRoom room) {
    	super(name, clock, room);
	}

	public void run() {
		//TODO
		//offset day start random up to 30 minutes
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


	protected void startDay() {
		//TODO!
	}
	
}
