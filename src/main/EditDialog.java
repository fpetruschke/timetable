package main;

import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
/**
 * class EditDialog
 * Dialog for editing JTable entries.
 * Opens onclick event.
 * 
 * @author f.petruschke
 */
public class EditDialog extends JDialog {
	
	/**
 	 * EditDialog
	 * 
	 * The EditDialog constructor
	 * 
	 * @param owner				JFrame		parent JFrame
	 * @param weekdayShortname	String		german shortname of the weekday - e.g. "Mo" for monday
	 * @param timeslotFrom		String		timeslot from time as string - e.g. "7:45"
	 * @param room				String		roomname
	 * @param course			String		coursename
	 * @param teachers			ArrayList	ArrayList with teachers' names - e.g. "Wm"
	 */
	public EditDialog(Frame owner, String weekdayShortname, String timeslotFrom, String room, String course, @SuppressWarnings("rawtypes") ArrayList teachers) {
		super(owner);
	    init(weekdayShortname, timeslotFrom, room, course, teachers);
	}
	
	// initializing the elements of the dialog
	JLabel weekdayShortNameLabel = new JLabel();
	JLabel timeFromLabel = new JLabel();
	JLabel labelCourse = new JLabel("Kurs");
	JComboBox<Object> course = new JComboBox<Object>();
	JLabel labelRoom = new JLabel("Raum");
	JComboBox<Object> room = new JComboBox<Object>();
	JLabel labelTeachers = new JLabel("Lehrer");
	JList<Object> teachers = new JList<Object>();
	String[] newDataset = new String[4];
	JButton saveBtn = new JButton("Speichern");
	private final JScrollPane scrollPane = new JScrollPane();
	private final JButton btnLschen = new JButton("Leeren");

	/**
 	 * init
	 * 
	 * Initialization of the dialog
	 * 
	 * @param weekdayShortname	String		
	 * @param timeslotFrom		String		
	 * @param roomName			String		name of the room
	 * @param courseName		String		name of the course
	 * @param teacherNames		ArrayList	ArrayList with the teachers' names
	 */
	private void init(final String weekdayShortname, final String timeslotFrom, String roomName, String courseName, @SuppressWarnings("rawtypes") ArrayList teacherNames) {
		
		// configuring the dialog and adding elements
		this.setTitle("Editiere Unterrichtseinheit");
		getContentPane().setLayout(new GridLayout(5, 2));
		
		weekdayShortNameLabel.setText(weekdayShortname);
		getContentPane().add(weekdayShortNameLabel);
		timeFromLabel.setText(timeslotFrom);
		getContentPane().add(timeFromLabel);
		getContentPane().add(labelCourse);
		
		// read out available courses
		DBCursor coursesCursor = MongoDbQueries.showAllFromCollection("courses");
		ArrayList<String> courseNames = new ArrayList<String>();
		if(coursesCursor!=null && coursesCursor.count()>0) {
			while(coursesCursor.hasNext()) {
				DBObject courseObject = coursesCursor.next();
				String course = (String) courseObject.get("name");
				courseNames.add(course);
			}
		}
		course.setModel(new DefaultComboBoxModel<Object>(courseNames.toArray()));
		// set the selected value
		if(courseName =="" ) {
			course.setSelectedItem("");
		} else {
			course.setSelectedItem(courseName);
		}
		getContentPane().add(course);
		
		
		getContentPane().add(labelRoom);
		// read out available rooms
		DBCursor roomsCursor = MongoDbQueries.showAllFromCollection("rooms");
		ArrayList<String> roomNames = new ArrayList<String>();
		if(roomsCursor!=null && roomsCursor.count()>0) {
			while(roomsCursor.hasNext()) {
				DBObject roomObject = roomsCursor.next();
				String room = (String) roomObject.get("name");
				roomNames.add(room);
			}
		}
		room.setModel(new DefaultComboBoxModel<Object>(roomNames.toArray()));
		if(roomName == "") {
			room.setSelectedItem("");
		} else {
			room.setSelectedItem(roomName);
		}
		getContentPane().add(room);
		
		
		getContentPane().add(labelTeachers);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(teachers);

		//room.setModel(new DefaultComboBoxModel<Object>(roomNames.toArray()));
		teachers.setModel(new AbstractListModel<Object>() {
			String[] values = (String[]) MongoDbQueries.getTeacherNamesAsArray();
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		if(teacherNames.isEmpty()) {
			teachers.setSelectedValue("", true);
		} else {
			for (int i = 0; i < teacherNames.size(); i++) {
				teachers.setSelectedValue(teacherNames.get(i), true);
			}
		}
		
		/**
		 *  The save button fires the save event which updates the entry content and table cell
		 */
		saveBtn.setIcon(new ImageIcon(EditDialog.class.getResource("/main/add.png")));
		saveBtn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				
				// get all teachers
				ArrayList<String> teachersArray = new ArrayList<String>();
				for (Object s : teachers.getSelectedValues()) {
					teachersArray.add(s.toString());
				}
				
				// trying to update the entry
				try {
					System.out.println("[NOTICE] Trying to update entry...");
					if(MongoDbQueries.updateTimetableEntry(
							weekdayShortname, 
							timeslotFrom, 
							room.getSelectedItem().toString(), 
							course.getSelectedItem().toString(), 
							teachersArray
					)) {
						System.out.println("[SUCCESS] Sucessfully updated entry.");
					} else {
						System.out.println("[ERROR] Could not update entry.");
					}
				} catch (Exception e1) {
					System.out.println("[ERROR] " + e1.getMessage());
				}
				
				// refresh table entries and close the dialog
				TimetableModel.refreshTable();
				dispose();
			}
		});
		getContentPane().add(saveBtn);
		
		/**
		 * The delete button fires the save event with empty stings 
		 * which updates the entry content and table cell
		 */
		btnLschen.setIcon(new ImageIcon(EditDialog.class.getResource("/main/delete.png")));
		btnLschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> teachersArray = new ArrayList<String>();
				try {
					System.out.println("[NOTICE] Trying to delete entry...");
					if(MongoDbQueries.updateTimetableEntry(
							weekdayShortname, 
							timeslotFrom, 
							"", 
							"", 
							teachersArray
					)) {
						System.out.println("[SUCCESS] Sucessfully cleared entry.");
					} else {
						System.out.println("[ERROR] Could not update entry.");
					}
				} catch (Exception e1) {
					System.out.println("[ERROR] " + e1.getMessage());
				}
				
				// Update table entries and close JDialog
				TimetableModel.refreshTable();
				dispose();
			}
		});
		
		getContentPane().add(btnLschen);
	}
}
