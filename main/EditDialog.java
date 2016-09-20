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

@SuppressWarnings("serial")
/**
 * class EditDialog
 * 
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
		this.setTitle("Editiere Unterrichtseinheit");
		getContentPane().setLayout(new GridLayout(5, 2));
		
		weekdayShortNameLabel.setText(weekdayShortname);
		getContentPane().add(weekdayShortNameLabel);
		
		timeFromLabel.setText(timeslotFrom);
		getContentPane().add(timeFromLabel);
		
		getContentPane().add(labelCourse);
		
		// TODO dynamically add courses
		course.setModel(new DefaultComboBoxModel<Object>(new String[] {"", "AE", "IT", "SKIL", "GUS", "SUK", "OGP", "WUG", "IT-WS"}));
		// set the selected value
		if(courseName =="" ) {
			course.setSelectedItem("");
		} else {
			course.setSelectedItem(courseName);
		}
		getContentPane().add(course);
		
		getContentPane().add(labelRoom);
		// TODO dynamically add rooms
		room.setModel(new DefaultComboBoxModel<Object>(new String[] {"", "221", "208", "H1.1"}));
		if(roomName == "") {
			room.setSelectedItem("");
		} else {
			room.setSelectedItem(roomName);
		}
		getContentPane().add(room);
		
		getContentPane().add(labelTeachers);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(teachers);
		// TODO dynamically add teachers
		teachers.setModel(new AbstractListModel<Object>() {
			String[] values = new String[] {"", "Wm", "Hr", "Zi", "l1", "Al", "Rt", "Hu"};
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
