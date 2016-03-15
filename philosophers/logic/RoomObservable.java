package philosophers.logic;

public interface RoomObservable {
    public void addObserver(RoomObserver o);
    public void notifyObservers();
}
