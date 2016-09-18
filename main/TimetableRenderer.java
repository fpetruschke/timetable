package main;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 * class TimetableRenderer
 * 
 * Renders the timetable with configured styles
 * 
 * @author d.lentz
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
		Lesson lesson = (Lesson)value;
		
		panel = new JPanel();
		panel.setBounds(0, 0, 120, 60);
		panel.setLayout(null);
		
		lblteacher = new JLabel(lesson.getTeacher(), SwingConstants.CENTER);
		lblteacher.setBounds(0, 0, panel.getWidth(), 20);
		panel.add(lblteacher);
		
		lblroom = new JLabel(lesson.getRoom(), SwingConstants.CENTER);
		lblroom.setBounds(0, 20, panel.getWidth(), 20);
		panel.add(lblroom);
		
		lblsubject = new JLabel(lesson.getCourse(), SwingConstants.CENTER);
		lblsubject.setBounds(0, 40, panel.getWidth(), 20);
		panel.add(lblsubject);
		
		if (lesson.isColor()) {
			panel.setBackground(Color.LIGHT_GRAY);
		}
		if (isSelected) {
			panel.setBackground(table.getSelectionBackground());
		}

		return panel;
	}

}
