package main;

import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
/**
 * class EditDialog
 * 
 * Dialog for editing JTable cell values.
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
	 * @param owner			JFrame 		parent JFrame
	 * @param cellValue		Object 		call value as object
	 */
	public EditDialog(Frame owner, Lesson cellValue) {
		super(owner);
	    init(cellValue);
	}
	
	// initializing the elements
	JLabel labelCourse = new JLabel("Kurs");
	JComboBox<Object> course = new JComboBox<Object>();
	
	JLabel labelRoom = new JLabel("Raum");
	JComboBox<Object> room = new JComboBox<Object>();
	
	JLabel labelTeachers = new JLabel("Lehrer");
	JList<Object> teachers = new JList<Object>();
	
	String[] newDataset = new String[4];
	JButton saveBtn = new JButton("Speichern");
	private final JScrollPane scrollPane = new JScrollPane();

	/**
	 * init
	 * 
	 * Initialization of the dialog
	 * 
	 * @param cellValue		Object		Cell value as object
	 */
	private void init(final Lesson cellValue) {
		this.setTitle("Editiere Unterrichtseinheit");
		getContentPane().setLayout(new GridLayout(4, 2));
		
		getContentPane().add(labelCourse);
		course.setModel(new DefaultComboBoxModel<Object>(new String[] {"AE", "IT", "SKIL", "GUS", "SUK", "OGP", "WUG", "IT-WS"}));
		// set the selected value from db
		course.setSelectedItem(cellValue.getCourse());
		getContentPane().add(course);
		
		getContentPane().add(labelRoom);
		room.setModel(new DefaultComboBoxModel<Object>(new String[] {"221", "208", "H1.1"}));
		course.setSelectedItem(cellValue.getRoom());
		getContentPane().add(room);
		
		getContentPane().add(labelTeachers);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(teachers);
		teachers.setModel(new AbstractListModel<Object>() {
			String[] values = new String[] {"Wm", "Hr", "Zi", "l1", "Al", "Rt", "Hu"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		teachers.addSelectionInterval(0,2);
		String[] teacherArray = cellValue.getTeacher().split("\\s+");
		for(String teacher : teacherArray) {
			teachers.setSelectedValue(teacher, true);
		}
		
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] insertedValues = getValues();
				
				for(String insertedValue : insertedValues) {
					System.out.println(insertedValue);
				}
				//@toDo: execute the update
				//@toDo: close dialog on save -important: cellValue holds more information!
			}
		});
		getContentPane().add(saveBtn);
	}
	
	/**
	 * getValues
	 * 
	 * @return		Returns an Array with the given cell values
	 */
	@SuppressWarnings("deprecation")
	public String[] getValues() {
		newDataset[0] = (String) course.getSelectedItem().toString();
		newDataset[1] = (String) room.getSelectedItem().toString();
		
		String teacherSTRING = "";
		for (Object s : teachers.getSelectedValues())
		{
		    teacherSTRING = s.toString() + "\t";
		}
		newDataset[2] = teacherSTRING;
		return newDataset;
	}
}
