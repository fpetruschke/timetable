package main;

import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	public EditDialog(Frame owner, Object cellValue) {
		super(owner);
	    init(cellValue);
	}
	
	// initializing the elements
	JLabel label1 = new JLabel("Stunden (von/bis");
	JLabel label2 = new JLabel("Kurs");
	JLabel label3 = new JLabel("Raum");
	JLabel label4 = new JLabel("Lehrer");
	JTextField timeslot = new JTextField();
	JTextField course = new JTextField();
	JTextField room = new JTextField();
	JTextField teacher = new JTextField();
	String[] newDataset = new String[4];
	JButton saveBtn = new JButton("Speichern");

	/**
	 * init
	 * 
	 * Initialization of the dialog
	 * 
	 * @param cellValue		Object		Cell value as object
	 */
	private void init(Object cellValue) {
		this.setTitle("Editiere Stunde");
		getContentPane().setLayout(new GridLayout(5, 2));
		getContentPane().add(label1);
		timeslot.setText((String) cellValue);
		getContentPane().add(timeslot);
		getContentPane().add(label2);
		getContentPane().add(course);
		getContentPane().add(label3);
		getContentPane().add(room);
		getContentPane().add(label4);
		getContentPane().add(teacher);
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] insertedValues = getValues();
				System.out.println(insertedValues);
				//@toDo: execute the update
				//@toDo: close dialog on save
			}
		});
		getContentPane().add(saveBtn);
	}

	/**
	 * getValues
	 * 
	 * @return		Returns an Array with the given cell values
	 */
	public String[] getValues() {
		newDataset[0] = timeslot.getText();
		newDataset[1] = course.getText();
		newDataset[2] = room.getText();
		newDataset[3] = teacher.getText();
		return newDataset;
	}
}
