package philosophers.logic;

import java.util.ArrayList;
import java.util.List;

public class DinnerRoom implements RoomObservable, DinerObserver {
    private List<Philosopher> philosophers = new ArrayList<Philosopher>();
    private List<RoomObserver> observers = new ArrayList<RoomObserver>();
    private int philosophersEating;
    
    public DinnerRoom(String... names) {
        for(String name : names) {
            Philosopher p = new Philosopher(name);
            
            if(!philosophers.isEmpty())
                p.setNeighbour(philosophers.get(philosophers.size() - 1));
            
            p.addObserver(this);
            
            philosophers.add(p);
        }
        
        philosophers.get(0).setNeighbour(philosophers.get(philosophers.size() - 1));
    }
    
    public void addObserver(DinerObserver o) {
        for(Philosopher p : philosophers)
            p.addObserver(o);
    }
    
	public void startDinner() {
	    philosophersEating = philosophers.size();
	    
		for(Philosopher p : philosophers) {
			Thread t = new Thread(p);
			
			try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
			
			t.start();
		}
	}

    public void somethingHappened(Philosopher p, Event e) {
        if(e == Event.DONE_EATING)
            philosophersEating--;
        
        if(philosophersEating == 0)
            notifyObservers();
    }

    public void addObserver(RoomObserver o) {
        observers.add(o);
    }

    public void notifyObservers() {
        for(RoomObserver o : observers)
            o.everyoneDoneEating();
    }
    
    public List<Philosopher> getPhilosophers() {
        return philosophers;
    }
}