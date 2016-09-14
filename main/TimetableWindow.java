package main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import javax.swing.table.DefaultTableModel;
import javax.swing.border.BevelBorder;

/**
 * class TimetableWindow
 * 
 * Configuration of the gui
 * 
 * @author f.petruschke
 *
 */
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
			
			DBCollection timeslots = DatabaseConnector.db.getCollection("timeslots");			
			
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
			table.getColumnModel().getColumn(0).setPreferredWidth(95);
			table.getColumnModel().getColumn(1).setPreferredWidth(91);
			table.getColumnModel().getColumn(2).setPreferredWidth(93);
			table.getColumnModel().getColumn(3).setPreferredWidth(101);
			table.getColumnModel().getColumn(4).setPreferredWidth(98);
			table.getColumnModel().getColumn(5).setPreferredWidth(97);
			
			
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
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.exit(0);
				}
			});
			btnNewButton.setBounds(649, 321, 117, 25);
			getContentPane().add(btnNewButton);

			//aktualisiereSchiffListe();

		}

		public void aktualisiereSchiffListe() throws Exception{
			//DefaultListModel daten = new DefaultListModel();

			/*SchiffDatenbankZugriff schiffZugriff = new SchiffDatenbankZugriff();
			//Hinzufügen der Schiffe aus der Array-Liste
			for (Schiff einSchiff : schiffZugriff.leseSchiffe()) {
				daten.addElement(einSchiff);
			}
			//Zuweisen des DefaultListModel zur JList 
			schiffListe.setModel(daten);*/
			
			//DBCollection collection = DatabaseCollection.createCollection(db, "students");
			
			
			DBCursor cursor = DatabaseQueries.showAllFromCollection("students");
			while (cursor.hasNext()) { 
			   /*System.out.println(cursor.next());
			   String firstname = cursor.next().get("firstname");
			   lab1.setText(firstname);*/
				BasicDBObject obj = (BasicDBObject) cursor.next();
				//lab1.setText(obj.getString("firstname"));
			    //result.add(obj.getString("HomeTown"));
			}
		}
	
}
