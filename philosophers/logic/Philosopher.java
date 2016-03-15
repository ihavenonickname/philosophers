package philosophers.logic;

import java.util.ArrayList;
import java.util.List;

public class Philosopher implements DinerObservable, Runnable {
	private enum ForkStatus {
		IN_USE, 
		NOT_IN_USE
	}
	
	private List<DinerObserver> observers = new ArrayList<DinerObserver>();
	private ForkStatus forkStatus = ForkStatus.NOT_IN_USE;
	private Philosopher neighbour;
	private String name; 

	public Philosopher(String n) {
		name = n;
	}
	
	public void run() {
        while(true) {
            notifyObservers(Event.WILL_TRY_EATING);
            
            if(tryToEat())
                break;
            
            think();
        }
        
        notifyObservers(Event.DONE_EATING);
    }

	public boolean tryToEat() {
		if(!setForkStatus(ForkStatus.IN_USE)) {
		    notifyObservers(Event.FORK_IN_USE);
			return false;
		}

		if(!neighbour.borrowFork()) {
		    notifyObservers(Event.NEIGHBOUR_FORK_IN_USE);
			setForkStatus(ForkStatus.NOT_IN_USE);
			return false;
		}

		notifyObservers(Event.GOT_FORKS);
		
		sleep(1000);
		
		setForkStatus(ForkStatus.NOT_IN_USE);
		neighbour.setForkStatus(ForkStatus.NOT_IN_USE);

		return true;
	}

	public void think() {
	    notifyObservers(Event.THINKING);
		sleep(1000);
	}

	public Philosopher getNeighbour() {
        return neighbour;
    }
	
	public void setNeighbour(Philosopher n) {
		neighbour = n;
	}
	
	public String getName() {
		return name;
	}
	
	public void addObserver(DinerObserver o) {
        observers.add(o);
    }

    public void notifyObservers(Event e) {
        observers.forEach(o -> o.somethingHappened(this, e));
        sleep(3000);
    }

	private void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch(Exception ex) {
		    System.out.println("got a real problem here: " + ex.getMessage());
		    System.exit(1);
		}
	}

	private boolean borrowFork() {
		return setForkStatus(ForkStatus.IN_USE);
	}

	private synchronized boolean setForkStatus(ForkStatus f) {
		if(f == ForkStatus.IN_USE && forkStatus == ForkStatus.IN_USE)
			return false;

		forkStatus = f;

		return true;
	}
}