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
public class EditDialog extends JDialog {

	public EditDialog(Frame owner, boolean modal) {
	    super(owner, modal);
	    init();
	  }
	
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


	  private void init() {
		this.setTitle("Editiere Stunde");
		getContentPane().setLayout(new GridLayout(5, 2));
		getContentPane().add(label1);
		getContentPane().add(timeslot);
		getContentPane().add(label2);
		getContentPane().add(course);
		getContentPane().add(label3);
		getContentPane().add(room);
		getContentPane().add(label4);
		getContentPane().add(teacher);
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//@toDo: close dialog on save
			}
		});
		getContentPane().add(saveBtn);
	  }

	  public String[] getAddress() {
		newDataset[0] = timeslot.getText();
		newDataset[1] = course.getText();
		newDataset[2] = room.getText();
		newDataset[3] = teacher.getText();
	    return newDataset;
	  }
	}
