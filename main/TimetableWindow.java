package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import com.mongodb.BasicDBList;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import javax.swing.table.DefaultTableModel;
import javax.swing.border.BevelBorder;

import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;

/**
 * class TimetableWindow
 * 
 * Configuration of the gui
 * 
 * @author f.petruschke
 *
 */
@SuppressWarnings("serial")
public class TimetableWindow extends JFrame {
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JTable table;

		//Konstruktor
		public TimetableWindow(String t) throws Exception{

			//Aufruf Basisklassenkonstruktor
			super(t);
			setTitle("Stundenplanverwaltung");
			getContentPane().setLayout(null);
			tabbedPane.setBounds(105, 5, 468, 459);
			
			getContentPane().add(tabbedPane);
			
			JPanel panel = new JPanel();
			tabbedPane.addTab("Stundenplan", null, panel, null);
			
			table = new JTable();
			table.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			panel.add(table);
						
			DBObject timeslot1 = DatabaseQueries.findOneInCollection("timeslots", "from", "7:45");
			DBObject timeslot2 = DatabaseQueries.findOneInCollection("timeslots", "from", "8:30");
			DBObject timeslot3 = DatabaseQueries.findOneInCollection("timeslots", "from", "9:30");
			DBObject timeslot4 = DatabaseQueries.findOneInCollection("timeslots", "from", "10:15");
			DBObject timeslot5 = DatabaseQueries.findOneInCollection("timeslots", "from", "11:30");
			DBObject timeslot6 = DatabaseQueries.findOneInCollection("timeslots", "from", "12:15");
			DBObject timeslot7 = DatabaseQueries.findOneInCollection("timeslots", "from", "13:30");
			DBObject timeslot8 = DatabaseQueries.findOneInCollection("timeslots", "from", "14:15");
			DBObject timeslot9 = DatabaseQueries.findOneInCollection("timeslots", "from", "15:15");
			DBObject timeslot10 = DatabaseQueries.findOneInCollection("timeslots", "from", "16:00");
			
			table.setModel(new DefaultTableModel(
				new Object[][] {
					{"1", timeslot1.get("from") + " - " + timeslot1.get("until"), null, null, null, null, null},
					{"2", timeslot2.get("from") + " - " + timeslot2.get("until"), null, null, null, null, null},
					{"3", timeslot3.get("from") + " - " + timeslot3.get("until"), null, null, null, null, null},
					{"4", timeslot4.get("from") + " - " + timeslot4.get("until"), null, null, null, null, null},
					{"5", timeslot5.get("from") + " - " + timeslot5.get("until"), null, null, null, null, null},
					{"6", timeslot6.get("from") + " - " + timeslot6.get("until"), null, null, null, null, null},
					{"7", timeslot7.get("from") + " - " + timeslot7.get("until"), null, null, null, null, null},
					{"8", timeslot8.get("from") + " - " + timeslot8.get("until"), null, null, null, null, null},
					{"9", timeslot9.get("from") + " - " + timeslot9.get("until"), null, null, null, null, null},
					{"10", timeslot10.get("from") + " - " + timeslot10.get("until"), null, null, null, null, null},
				},
				new String[] {
					"", "Uhrzeit", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag"
				}
			));
			table.getColumnModel().getColumn(0).setPreferredWidth(22);
			table.getColumnModel().getColumn(1).setPreferredWidth(130);
			table.getColumnModel().getColumn(2).setPreferredWidth(80);
			table.getColumnModel().getColumn(3).setPreferredWidth(100);
			table.getColumnModel().getColumn(4).setPreferredWidth(110);
			table.getColumnModel().getColumn(5).setPreferredWidth(90);
			
			
			// ##################################################################
			// attempt to autofill the table
			// ##################################################################
			/*final DefaultTableModel model;
			JTable table = new JTable(model = new DefaultTableModel(new Object[][]{},new Object[]{"_id", "weekday.name"}));
	        getContentPane().add(new JScrollPane(table));

	        DBCursor cursor = DatabaseQueries.showAllFromCollection("timeslots");
            while (cursor.hasNext()) {
            	model.addRow(new Object[]{cursor.next().toString()});
            	//BasicDBObject obj = (BasicDBObject) cursor.next();
            	//model.addRow(new Object[]{ obj.getString("name")});
            }*/
			
						
			JScrollPane scrollPane = new JScrollPane(table);
			panel.add(scrollPane);
			
			JPanel panel_1 = new JPanel();
			tabbedPane.addTab("New tab", null, panel_1, null);
			
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
			btnNewButton.setBounds(649, 321, 117, 25);
			getContentPane().add(btnNewButton);
			
			JButton btnNewButton_1 = new JButton("Log Rooms, Teachers and Courses");
			btnNewButton_1.addActionListener(new ActionListener() {
				@SuppressWarnings("unchecked")
				public void actionPerformed(ActionEvent e) {
					
					String[] weekdayShortnames = new String[] { "Mo", "Di", "Mi", "Do", "Fr" };
					String[] timeslotStarts = new String[] { "7:45", "8:30", "9:30", "10:15", "11:30", "12:15", "13:30", "14:15", "15:15", "16:00" };
					String[] filters = new String[] { "room", "course", "teachers" };
					
					for(String weekdayShortname : weekdayShortnames) {
						for (String timeslotStart : timeslotStarts) {
							
							DBObject weekday = DatabaseQueries.findOneInCollection("weekdays", "shortname", weekdayShortname);
							DBObject timeslot = DatabaseQueries.findOneInCollection("timeslots", "from", timeslotStart);
							
							DBCursor cursor = DatabaseQueries.findOneInCollectionAnd("timetable", "weekday", weekday, "timeslot", timeslot);
							
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
			btnNewButton_1.setBounds(585, 229, 304, 25);
			getContentPane().add(btnNewButton_1);

		}
}
