package philosophers;

import java.util.ArrayList;

class DinnerRoom {
	public void startDinner() throws Exception {
		ArrayList<Philosopher> ps = new ArrayList<Philosopher>();

		ps.add(new Philosopher("Kant"));
		ps.add(new Philosopher("Hegel"));
		ps.add(new Philosopher("Nietzsche"));
		ps.add(new Philosopher("Kierkegaard"));
		ps.add(new Philosopher("Wittgenstein"));

		ps.get(0).setNeighbour(ps.get(ps.size() - 1));

		for(int i = 1; i < ps.size(); i++)
			ps.get(i).setNeighbour(ps.get(i - 1));

		for(Philosopher p : ps) {
			Thread t = new Thread(() -> {
				while(!p.tryToEat())
					p.think();
			});

			t.join();
			t.start();
		}
	}
}