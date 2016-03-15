package philosophers;

import philosophers.ui.Window;

public class Main {
	public static void main(String[] args) {
		try {
			new Window().showUp();
		} catch(Exception ex) {
			System.exit(1);
		}
	}
}