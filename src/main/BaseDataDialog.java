package main;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JDialog;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;

@SuppressWarnings("serial")
public class BaseDataDialog extends JDialog{
	private JTextField newCourse;
	private JTextField newTeacher;
	private JTextField newRoom;
	
	private JList<String> roomsList;
	private DefaultListModel<String> roomListModel = new DefaultListModel<String>();
	
	private JList<String> coursesList;
	private DefaultListModel<String> courseListModel = new DefaultListModel<String>();
	
	private JList<String> teachersList;
	private DefaultListModel<String> teacherListModel = new DefaultListModel<String>();

	public BaseDataDialog(Frame owner) {
		super(owner);
		getContentPane().setLayout(null);
		
		JLabel coursesLbl = new JLabel("Fächer");
		coursesLbl.setFont(new Font("Dialog", Font.BOLD, 15));
		coursesLbl.setBounds(12, 113, 110, 35);
		getContentPane().add(coursesLbl);
		
		JScrollPane coursesScrollPane = new JScrollPane();
		coursesScrollPane.setBounds(12, 157, 114, 117);
		getContentPane().add(coursesScrollPane);
		
		// setting up a list model for the course list
		String[] coursesValues = BaseDataController.getNamesOfCollectionAsArray("courses");
		for(String courseName : coursesValues) {
			courseListModel.addElement(courseName);
		}
		coursesList = new JList<String>(courseListModel);
		coursesList.addListSelectionListener(new ListSelectionListener() {
			@Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting() && coursesList.getSelectedValue() != null) {
                  newCourse.setText(coursesList.getSelectedValue().toString());
                }
            }
		});
		coursesScrollPane.setViewportView(coursesList);
		
		newCourse = new JTextField();
		newCourse.setBounds(12, 286, 114, 19);
		getContentPane().add(newCourse);
		newCourse.setColumns(10);
		
		JButton newCourseBtn = new JButton("");
		newCourseBtn.setIcon(new ImageIcon(BaseDataDialog.class.getResource("/main/add.png")));
		newCourseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// inserting the new course
					BaseDataController.insertNewBaseData("courses", newCourse.getText());
					// appending the name to the course list model
					courseListModel.addElement(newCourse.getText());
					// recreating the list
					coursesList = new JList<String>(courseListModel);
					// clearing the input field
					newCourse.setText("");
					// refresh the table
					TimetableModel.refreshTable();
					
				} catch (Exception e1) {
					System.out.println("[ERROR] Could not save new course.");
					e1.printStackTrace();
				}
			}
		});
		newCourseBtn.setBounds(12, 317, 44, 25);
		getContentPane().add(newCourseBtn);
		
		JLabel teachersLbl = new JLabel("Lehrer");
		teachersLbl.setFont(new Font("Dialog", Font.BOLD, 15));
		teachersLbl.setBounds(157, 113, 93, 35);
		getContentPane().add(teachersLbl);
		
		JScrollPane teachersScrollPane = new JScrollPane();
		teachersScrollPane.setBounds(157, 157, 110, 117);
		getContentPane().add(teachersScrollPane);
		
		
		// setting up a list model for the teacher list
		String[] teacherValues = BaseDataController.getNamesOfCollectionAsArray("teachers");
		for(String teacherName : teacherValues) {
			teacherListModel.addElement(teacherName);
		}
		teachersList = new JList<String>(teacherListModel);
		teachersList.addListSelectionListener(new ListSelectionListener() {
			@Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting() && teachersList.getSelectedValue() != null) {
                  newTeacher.setText(teachersList.getSelectedValue().toString());
                }
            }
		});
		
		teachersScrollPane.setViewportView(teachersList);
		
		newTeacher = new JTextField();
		newTeacher.setBounds(157, 286, 114, 19);
		getContentPane().add(newTeacher);
		newTeacher.setColumns(10);
		
		JButton newTeacherBtn = new JButton("");
		newTeacherBtn.setIcon(new ImageIcon(BaseDataDialog.class.getResource("/main/add.png")));
		newTeacherBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// inserting new teacher
					BaseDataController.insertNewBaseData("teachers", newTeacher.getText());
					// updating the teacher list model
					teacherListModel.addElement(newTeacher.getText());
					// recreating the teacher list model
					teachersList = new JList<String>(teacherListModel);
					// clearing the input field
					newTeacher.setText("");
					// refresh the table
					TimetableModel.refreshTable();
				} catch (Exception e1) {
					System.out.println("[ERROR] Could not save new teacher.");
					e1.printStackTrace();
				}
			}
		});
		newTeacherBtn.setBounds(157, 317, 44, 25);
		getContentPane().add(newTeacherBtn);
		
		JLabel roomsLbl = new JLabel("Räume");
		roomsLbl.setFont(new Font("Dialog", Font.BOLD, 15));
		roomsLbl.setBounds(304, 116, 93, 28);
		getContentPane().add(roomsLbl);
		
		JScrollPane roomsScrollPane = new JScrollPane();
		roomsScrollPane.setBounds(304, 157, 114, 117);
		getContentPane().add(roomsScrollPane);
		
		// setting up a list model for the room list
		String[] roomValues = BaseDataController.getNamesOfCollectionAsArray("rooms");
		for(String roomName : roomValues) {
			roomListModel.addElement(roomName);
		}
		roomsList = new JList<String>(roomListModel);
		roomsList.addListSelectionListener(new ListSelectionListener() {
			@Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting() && roomsList.getSelectedValue() != null) {
                  newRoom.setText(roomsList.getSelectedValue().toString());
                }
            }
		});
		roomsScrollPane.setViewportView(roomsList);
		
		newRoom = new JTextField();
		newRoom.setBounds(304, 286, 114, 19);
		getContentPane().add(newRoom);
		newRoom.setColumns(10);
		
		JButton newRoomBtn = new JButton("");
		newRoomBtn.setIcon(new ImageIcon(BaseDataDialog.class.getResource("/main/add.png")));
		newRoomBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// insert new room
					BaseDataController.insertNewBaseData("rooms", newRoom.getText());
					// add new room to the list model
					roomListModel.addElement(newRoom.getText());
					// recreating the list
					roomsList = new JList<String>(roomListModel);
					// clearing the input
					newRoom.setText("");
					// refresh the table
					TimetableModel.refreshTable();
				} catch (Exception e1) {
					System.out.println("[ERROR] Could not save new room.");
					e1.printStackTrace();
				}
			}
		});
		newRoomBtn.setBounds(304, 317, 44, 25);
		getContentPane().add(newRoomBtn);
		
		JButton delCourse = new JButton("");
		delCourse.setIcon(new ImageIcon(BaseDataDialog.class.getResource("/main/delete.png")));
		delCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("[NOTICE] Trying to delete course ...");
				try {
					// deleting the course
					BaseDataController.deleteBaseDataDocument("courses", newCourse.getText());
					// removing the course name from the list model
					courseListModel.removeElement(newCourse.getText());
					// crecreating the list
					coursesList = new JList<String>(courseListModel);
					// clearing the input field
					newCourse.setText("");
					// refresh the table
					TimetableModel.refreshTable();
				} catch (Exception e1) {
					System.out.println("[ERROR] Could not delete course.");
					e1.printStackTrace();
				}
			}
		});
		delCourse.setBounds(78, 317, 44, 25);
		getContentPane().add(delCourse);
		
		JButton delTeacher = new JButton("");
		delTeacher.setIcon(new ImageIcon(BaseDataDialog.class.getResource("/main/delete.png")));
		delTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("[NOTICE] Trying to delete teacher ...");
				try {
					// deleting the teacher
					BaseDataController.deleteBaseDataDocument("teachers", newTeacher.getText());
					// removing name from the list model
					teacherListModel.removeElement(newTeacher.getText());
					// recreate the list
					teachersList = new JList<String>(teacherListModel);
					// clearing the input field
					newTeacher.setText("");
					// refresh the table
					TimetableModel.refreshTable();
				} catch (Exception e1) {
					System.out.println("[ERROR] Could not delete teacher.");
					e1.printStackTrace();
				}
			}
		});
		delTeacher.setBounds(223, 317, 44, 25);
		getContentPane().add(delTeacher);
		
		JButton delRoom = new JButton("");
		delRoom.setIcon(new ImageIcon(BaseDataDialog.class.getResource("/main/delete.png")));
		delRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("[NOTICE] Trying to delete room ...");
				try {
					// deleting the room
					BaseDataController.deleteBaseDataDocument("rooms", newRoom.getText());
					// removing the room name from the list model
					roomListModel.removeElement(newRoom.getText());
					// recreate the list
					roomsList = new JList<String>(roomListModel);
					// clear the input field
					newRoom.setText("");
					// refresh the table
					TimetableModel.refreshTable();
				} catch (Exception e1) {
					System.out.println("[ERROR] Could not delete room.");
					e1.printStackTrace();
				}
			}
		});
		delRoom.setBounds(378, 317, 44, 25);
		getContentPane().add(delRoom);
		
		JLabel notice1 = new JLabel("Bitte tragen Sie den Namen des Datensatzes");
		notice1.setFont(new Font("Dialog", Font.BOLD, 15));
		JLabel notice2 = new JLabel("unter die entsprechende Kategorie ein.");
		notice2.setFont(new Font("Dialog", Font.BOLD, 15));
		
		JLabel notice3 = new JLabel("Der Datensatz wird hinzugefügt - sofern neu");
		notice3.setIcon(new ImageIcon(BaseDataDialog.class.getResource("/main/add.png")));
		notice3.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel notice4 = new JLabel("Der Datensatz wird gelöscht - sofern vorhanden");
		notice4.setHorizontalAlignment(SwingConstants.LEFT);
		notice4.setIcon(new ImageIcon(BaseDataDialog.class.getResource("/main/delete.png")));
		notice1.setBounds(12, 10, 410, 35);
		notice2.setBounds(12, 25, 410, 35);
		notice3.setBounds(12, 50, 410, 35);
		notice4.setBounds(12, 75, 410, 35);
		getContentPane().add(notice1);
		getContentPane().add(notice2);
		getContentPane().add(notice3);
		getContentPane().add(notice4);
		
		JButton btnFertig = new JButton("Fertig");
		btnFertig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// refresh table entries and close the dialog
				TimetableModel.refreshTable();
				dispose();
			}
		});
		btnFertig.setBounds(125, 354, 200, 35);
		getContentPane().add(btnFertig);
	    init();
	}
	
	private void init() {
		
		// configuring the dialog and adding elements
		this.setTitle("Stammdaten");
		
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
	}
}
