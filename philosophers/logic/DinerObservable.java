package philosophers.logic;

interface DinerObservable {
    public void addObserver(DinerObserver o);
    public void notifyObservers(Event e);
}
