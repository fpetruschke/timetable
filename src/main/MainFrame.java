package main;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.BevelBorder;

/**
 * class MainFrame
 * 
 * Configuration of the gui.
 * Also holds the timetable model and the edit dialog.
 * 
 * @author f.petruschke
 *
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	protected static final Component JFrame = null;
	private JTable table;

	//Construktor
	public MainFrame(String t) throws Exception{

		// base class constructor
		super(t);
		// setting the title of the window
		setTitle("Stundenplanverwaltung");
		// initialization of the panes' layout
		getContentPane().setLayout(null);
		// instantiating a jTable with the TimetableModel and setting options
		table = new JTable(new TimetableModel());
		
		table.setPreferredScrollableViewportSize(new Dimension(700, 600));
		table.setFillsViewportHeight(true);
		table.setDefaultRenderer(Entry.class, new TimetableRenderer());
		table.setRowHeight(60);
		table.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		table.setColumnSelectionAllowed(true);
		
		// make the content of the days' timeslot editable via dialog after click on cell
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        // only make the course entries editable
		        if (row >= 0 && col >= 1) {
		        	
		        	//Entry cellObject = (Entry) table.getValueAt(row, col);
		        	Entry entry = TimetableModel.entry[row][col];
		        	
		        	// open the edit dialog for changing the entries' data
		        	EditDialog dialog = new EditDialog(
		        			(Frame) JFrame,
		        			entry.getWeekdayShortname(),
		        			entry.getTimeslotFrom(),
		        			entry.getRoom(),
		        			entry.getCourse(),
		        			entry.getTeacher()
        			);
		        	
		        	dialog.setSize(450, 300);
		        	dialog.setLocationRelativeTo(null);
		        	dialog.setModal(true);
		            dialog.setVisible(true);
		        }
		    }
		});
		
		//Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 12, 703, 622);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(table);
		
		// add button for exiting and closing the connection
		JButton btnNewButton = new JButton("Beenden");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					MongoDbConnector.closeConnection();
				} catch (Exception e1) {
					System.out.println("Connection could not be closed!");
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		btnNewButton.setBounds(423, 638, 304, 25);
		getContentPane().add(btnNewButton);
		
		// button for managing the base data in a dialog
		JButton btnStammdatenverwaltung = new JButton("Stammdatenverwaltung");
		btnStammdatenverwaltung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BaseDataDialog dialog = new BaseDataDialog((Frame) JFrame);
	        	dialog.setSize(450, 414);
	        	dialog.setLocationRelativeTo(null);
	        	dialog.setModal(true);
	            dialog.setVisible(true);
			}
		});
		btnStammdatenverwaltung.setBounds(34, 638, 252, 25);
		getContentPane().add(btnStammdatenverwaltung);
		

	}
}
