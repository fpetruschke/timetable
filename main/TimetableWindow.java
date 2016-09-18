package main;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import com.mongodb.BasicDBList;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import javax.swing.border.BevelBorder;

import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;

/**
 * class TimetableWindow
 * 
 * Configuration of the gui.
 * Also holds the timetable model and the edit dialog.
 * 
 * @author f.petruschke
 * @author d.lentz
 *
 */
@SuppressWarnings("serial")
public class TimetableWindow extends JFrame {
	protected static final Component JFrame = null;
	private JTable table;

		//Construktor
		public TimetableWindow(String t) throws Exception{

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
			table.setDefaultRenderer(Lesson.class, new TimetableRenderer());
			table.setRowHeight(60);
			table.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			
			// make the content of the days' timeslot editable via dialog after click on cell
			table.addMouseListener(new java.awt.event.MouseAdapter() {
			    @Override
			    public void mouseClicked(java.awt.event.MouseEvent evt) {
			        int row = table.rowAtPoint(evt.getPoint());
			        int col = table.columnAtPoint(evt.getPoint());
			        if (row >= 0 && col >= 1) {
			        	//Object cellValue = table.getModel().getValueAt(row, col);
			        	Lesson cellObject = (Lesson) table.getValueAt(row, col);
			        	EditDialog dialog = new EditDialog((Frame) JFrame, cellObject);
			        	dialog.setSize(450, 300);
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
						DatabaseConnector.closeConnection();
					} catch (Exception e1) {
						System.out.println("Connection could not be closed!");
						e1.printStackTrace();
					}
					System.exit(0);
				}
			});
			btnNewButton.setBounds(423, 638, 304, 25);
			getContentPane().add(btnNewButton);
			
			// for debugging purposes
			JButton btnNewButton_1 = new JButton("Log Rooms, Teachers and Courses");
			btnNewButton_1.addActionListener(new ActionListener() {
				@SuppressWarnings("unchecked")
				public void actionPerformed(ActionEvent e) {
					
					// preparing the names, times and filters for queries
					String[] weekdayShortnames = new String[] { "Mo", "Di", "Mi", "Do", "Fr" };
					String[] timeslotStarts = new String[] { "7:45", "8:30", "9:30", "10:15", "11:30", "12:15", "13:30", "14:15", "15:15", "16:00" };
					String[] filters = new String[] { "room", "course", "teachers" };
					
					// get all the data for each day and timespan from the database
					for(String weekdayShortname : weekdayShortnames) {
						for (String timeslotStart : timeslotStarts) {
							
							DBObject weekday = DatabaseQueries.findOneInCollection("weekdays", "shortname", weekdayShortname);
							DBObject timeslot = DatabaseQueries.findOneInCollection("timeslots", "from", timeslotStart);
							
							DBCursor cursor = DatabaseQueries.findOneInCollectionAnd("timetable", "weekday", weekday, "timeslot", timeslot);
							
							// iterating over the resultset for timetable
							String result = "";
							while(cursor.hasNext()) {
								DBObject element = cursor.next();
								for(String filter : filters) {
									Object object = element.get(filter); 
									// if object is BasicDBList of teacher
									if(object instanceof BasicDBList) {
										for (int i = 0; i < ((ArrayList<Object>) object).size(); i++) {
											Object listElement = ((BasicBSONList) object).get(i);
											Object teacher = ((BasicBSONObject) listElement).get("teacher");
											String teacherName = (String) ((BasicBSONObject) teacher).get("name"); 
											result += teacherName + ", ";
										}
									} else {
										String name = (String) ((BasicBSONObject) object).get("name");
										result += name + ", ";
									}
								}
								// result the value of the room, the course and the teacher/s
								System.out.println(result);
							}
						}
					}
				}
			});
			btnNewButton_1.setBounds(24, 638, 304, 25);
			getContentPane().add(btnNewButton_1);

		}
}
