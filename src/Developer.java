package src;

public class Developer extends Actor {
	
    /* Inherited from Actor
     * protected Clock clock;
     * protected ConferenceRoom conferenceRoom;
     */
    
    private TeamLead lead;
    private long arrivalTime = 0;
    private boolean hasHadLunch;
    private int lunchLength;
    
    //statistics keeping
    private int working;
    private int lunch;
    private int meetings;
    private int waiting;
    
    
    public Developer(String name, Clock clock, ConferenceRoom room, 
            TeamLead lead) {
        super(name, clock, room);
        this.lead = lead;
        hasHadLunch = false;
        lunchLength = 30;
        
        working = 0;
        lunch = 0;
        meetings = 0;
        waiting = 0;
    }
    
    public void run() {
        clock.awaitStart();
        
        //Wait Random time to simulate when they come in.
        busy((int)(Math.random() * clock.convertMinutes(30)));
        arrivalTime = clock.getTimePassedMillis();
        printWithTime(getName() + " arrives for work.");
        
        //Find out how long lunch can be based on arrival time
        lunchLength = 30 + (int)(Math.random() * (30 - 
                clock.convertSimulated((int)arrivalTime)));
        
        //Go to daily Stand Up check every five minutes if the room is 
        //available No questions during this time.
        printWithTime(getName() + " tries to go to daily standup.");
        while(!conferenceRoom.arrive(lead)) {
            busy(clock.convertMinutes(5));
        }
        printWithTime(getName() + " finishes daily standup.");
        meetings += 15;
        
        //Idle until the meeting at 4PM
        idleUntil(clock.AbsoluteMinutes(16 * 60));
        
        //Go to end of day meeting
        printWithTime(getName() + " goes to summary meeting.");
        conferenceRoom.arriveFinal();
        meetings += 15;
        printWithTime(getName() + " finishes summary meeting.");
        
        //Idle until it's time to go home
        idleUntil(clock.convertSimulated((int)arrivalTime) + (8 * 60) + 
                lunchLength);
        printWithTime(getName() + " goes home.");
    }
    
    protected void idleUntil(int relativeMinutes) {
        while(clock.convertSimulated((int)clock.getTimePassedMillis()) < 
                relativeMinutes) {
            //Check if should go to lunch.
            if(!hasHadLunch) {
                //Currently Just checks if there's enough time to have lunch.
                if((relativeMinutes - clock.convertSimulated( 
                        (int)clock.getTimePassedMillis())) > lunchLength) {
                    //Go on lunch break
                    busy(lunchLength);
                    lunch += lunchLength;
                }
            } else {
                //Check if they are asking a question
                if(Math.random()*100 < 1) {
                    waiting += lead.askQuestion(this);
                    //Does asking a question count as working?
                    //working += 10;
                } else {
                    busy(1);
                    working += 1;
                }
            }
        }
    }
    
    
}
