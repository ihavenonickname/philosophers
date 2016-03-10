package philosophers;

public class Main {
	public static void main(String[] args) {
		try {
			new DinnerRoom().startDinner();
		} catch(Exception ex) {
			System.exit(1);
		}
	}
}