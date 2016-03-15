package philosophers.ui;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import philosophers.logic.DinnerRoom;
import philosophers.logic.RoomObserver;

@SuppressWarnings("serial")
public class Window extends JFrame implements RoomObserver {
	DinnerRoom dinnerRoom;
	
    public Window() {
    	dinnerRoom = new DinnerRoom("Kant", 
                "Hegel", 
                "Nietzsche", 
                "Kierkegaard", 
                "Wittgenstein");
        
        dinnerRoom.addObserver(this);
        
        JTable table = new JTable(new PhilosophersViewer(dinnerRoom));
        JScrollPane jsp = new JScrollPane(table);
        
        jsp.setSize(650, 103);
        jsp.setLocation(10, 10);
        
        add(jsp);
        
        setTitle("Dining philosophers problem");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setSize(675, 150);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    public void showUp() throws InterruptedException {
        setVisible(true);
        dinnerRoom.startDinner();
    }

    public void everyoneDoneEating() {
        dinnerRoom.startDinner();
    }
}
