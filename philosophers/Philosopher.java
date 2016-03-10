package philosophers;

class Philosopher {
	enum ForkStatus {
		IN_USE, 
		NOT_IN_USE
	}

	private ForkStatus forkStatus = ForkStatus.NOT_IN_USE;
	private Philosopher neighbour;
	private String name; 

	public Philosopher(String name) {
		this.name = name;
	}

	public boolean tryToEat() {
		log("will try to eat");

		if(!setForkStatus(ForkStatus.IN_USE)) {
			log("faild eating 'cause his fork is in use");
			return false;
		}

		if(!neighbour.borrowFork()) {
			log("faild eating 'cause " + neighbour.getName() + " didn't borrow his fork");
			setForkStatus(ForkStatus.NOT_IN_USE);
			return false;
		}

		log("got forks and will eat");

		sleep(2000);

		log("is done eating");

		setForkStatus(ForkStatus.NOT_IN_USE);
		neighbour.setForkStatus(ForkStatus.NOT_IN_USE);

		return true;
	}

	public void think() {
		sleep(1000);
	}

	public void setNeighbour(Philosopher neighbour) {
		this.neighbour = neighbour;
	}

	public String getName() {
		return name;
	}

	private void log(String message) {
		System.out.println(name + " " + message);
	}

	private void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch(Exception ex) {
			log("got a real problem here: " + ex.getMessage());
		}
	}

	private boolean borrowFork() {
		return setForkStatus(ForkStatus.IN_USE);
	}

	private synchronized boolean setForkStatus(ForkStatus newStatus) {
		if(newStatus == ForkStatus.IN_USE) {
			if(forkStatus == ForkStatus.IN_USE)
				return false;

			forkStatus = ForkStatus.IN_USE;
		} else 
			forkStatus = ForkStatus.NOT_IN_USE;

		return true;
	}
}