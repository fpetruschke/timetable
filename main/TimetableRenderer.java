package main;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 * class TimetableRenderer
 * 
 * Renders the timetable with configured styles and adds the labels containing
 * the values to the table cells.
 * 
 * @author f.petruschke
 *
 */
public class TimetableRenderer implements TableCellRenderer{
	public JPanel panel;
	private JLabel lblteacher;
	private JLabel lblroom;
	private JLabel lblsubject;

	public TimetableRenderer() {
		super();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Entry entry = (Entry)value;
		
		panel = new JPanel();
		panel.setBounds(0, 0, 120, 60);
		panel.setLayout(null);
		
		ArrayList<String> teachers = entry.getTeacher();
		String teacherNames = "";
		for (int i = 0; i < teachers.size(); i++) {
			if(i == teachers.size()-1) {
				teacherNames += (teachers.get(i));
			} else {
				teacherNames += (teachers.get(i)) + ", ";
			}
		}
		
		lblteacher = new JLabel(teacherNames, SwingConstants.CENTER);
		lblteacher.setBounds(0, 0, panel.getWidth(), 20);
		panel.add(lblteacher);
		
		lblroom = new JLabel(entry.getRoom(), SwingConstants.CENTER);
		lblroom.setBounds(0, 20, panel.getWidth(), 20);
		panel.add(lblroom);
		
		lblsubject = new JLabel(entry.getCourse(), SwingConstants.CENTER);
		lblsubject.setBounds(0, 40, panel.getWidth(), 20);
		panel.add(lblsubject);
		
		if (entry.isColor()) {
			panel.setBackground(Color.LIGHT_GRAY);
		}
		if (isSelected) {
			panel.setBackground(table.getSelectionBackground());
		}

		return panel;
	}

}
