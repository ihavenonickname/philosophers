package philosophers.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import philosophers.logic.DinerObserver;
import philosophers.logic.DinnerRoom;
import philosophers.logic.Event;
import philosophers.logic.Philosopher;

@SuppressWarnings("serial")
public class PhilosophersViewer extends AbstractTableModel implements DinerObserver {
    private List<Philosopher> philosophers;
    private String[][] data;
    private final String[] columns = {"Philosopher", "What is doing", "Dinners"};
    
    public PhilosophersViewer(DinnerRoom dr) {
        philosophers = dr.getPhilosophers();
        dr.addObserver(this);
        
        data = new String[columns.length][philosophers.size()];
        
        for(int i = 0; i < philosophers.size(); i++) {
            data[0][i] = philosophers.get(i).getName();
            data[1][i] = "Nothing";
            data[2][i] = "0";
        }
    }
    
    @Override
    public String getColumnName(int i) {
        return columns[i];
    }
    
    public int getColumnCount() {
        return columns.length;
    }

    public int getRowCount() {
        return philosophers.size();
    }

    public Object getValueAt(int line, int column) {
        return data[column][line];
    }

    public void somethingHappened(Philosopher p, Event e) {
        int index = philosophers.indexOf(p);
        
        switch (e) {
        case WILL_TRY_EATING:
            updateAction(index, "will try eating");
            break;
        
        case DONE_EATING:
            updateAction(index, "has done eating");
            updateDinnersCompleted(index);
            break;
            
        case FORK_IN_USE:
            updateAction(index, "failed eating, fork in use");
            break;
            
        case NEIGHBOUR_FORK_IN_USE:
            updateAction(index, "failed eating, neighbour's fork in use");
            break;
            
        case GOT_FORKS:
            updateAction(index, "got forks");
            break;
            
        case THINKING:
            updateAction(index, "is thinking");
        }
    }
    
    public synchronized void updateAction(int line, String action) {
        data[1][line] = action;
        fireTableCellUpdated(line, 1);
    }
    
    public synchronized void updateDinnersCompleted(int line) {
        int cur = Integer.parseInt(data[2][line]);
        data[2][line] = String.valueOf(1 + cur);
        fireTableCellUpdated(line, 2);
    }
}
