package philosophers.logic;

public interface DinerObserver {
    public void somethingHappened(Philosopher p, Event e);
}
