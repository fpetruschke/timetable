package main;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;

import com.mongodb.BasicDBList;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@SuppressWarnings("serial")
/**
 * class TimetableModel
 * 
 * Holds the model of the timetable including the data collection from the database
 * 
 * @author d.lentz
 * @author f.petruschke
 *
 */
public class TimetableModel extends AbstractTableModel{
	Lesson[][] lesson;
	String[] columnNames;
	
	@SuppressWarnings("unchecked")
	public TimetableModel() {
		
		// defining columnName
		String[] columnNames = {"Zeit",
                "Montag",
                "Dienstag",
                "Mittwoch",
                "Donnerstag",
                "Freitag"};
		
		// defining the data for the jTable
		int rows = 10;
		int columns = 6;
		Lesson[][] data = new Lesson [rows][columns];
		
		for(int row = 0; row < rows; row++) {			
			for(int column = 0; column < columns; column++) {
				
				// if first column: show time from and until
				if(column == 0) {
					// insert from time to until time
					DBObject hour = DatabaseQueries.findOneInCollection("timeslots", "hourId", Integer.toString(row+1));
					String from = (String) ((BasicBSONObject) hour).get("from");
					String until = (String) ((BasicBSONObject) hour).get("until");
					data[row][column] = new Lesson(from,"-",until);
					
				// else get the corresponding data
				} else {
					
					// get the timetable column corresponding to weekday and course hour
					DBObject weekday = DatabaseQueries.findOneInCollection("weekdays", "nameId", Integer.toString(column));
					DBObject hourId = DatabaseQueries.findOneInCollection("timeslots", "hourId", Integer.toString(row+1));
					DBCursor timetableColumn = DatabaseQueries.findOneInCollectionAnd("timetable", "weekday", weekday, "timeslot", hourId);
					
					// check if data as available or if its an empty hour
					if(timetableColumn!=null && timetableColumn.count()>0) {
						while(timetableColumn.hasNext()) {
							DBObject hourObject = timetableColumn.next();
							
							// get all the teachers names and concatinate them to one string
							Object teachers = hourObject.get("teachers");
							String teacherNames = "";
							if(teachers instanceof BasicDBList) {
								for (int i = 0; i < ((ArrayList<Object>) teachers).size(); i++) {
									Object listElement = ((BasicBSONList) teachers).get(i);
									Object teacher = ((BasicBSONObject) listElement).get("teacher");
									String teacherName = (String) ((BasicBSONObject) teacher).get("name"); 
									teacherNames += teacherName + " ";
								}
							} else {
								String name = (String) ((BasicBSONObject) teachers).get("name");
								teacherNames += name + ", ";
							}
							
							// get the course name
							Object course = hourObject.get("course");
							String courseName = (String) ((BasicBSONObject) course).get("name");
							
							// get the room name
							Object room = hourObject.get("room");
							String roomName = (String) ((BasicBSONObject) room).get("name");
							
							// insert the result data
							data[row][column] = new Lesson(teacherNames, roomName, courseName);
						}
					} else {
						// no data was found for that day at that time block
						data[row][column] = new Lesson(false);
					}
				}
			}
		}
		
		this.lesson = data;
		this.columnNames = columnNames;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int columnIndex) {
		return Lesson.class;
	}
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return lesson[rowIndex][columnIndex];
	}

	public void setValueAt(String teacher, String room, String subject, int rowIndex, int columnIndex) {
		Lesson newLesson = lesson[rowIndex][columnIndex];
		newLesson.setTeacher(teacher);
		newLesson.setRoom(room);
		newLesson.setSubject(subject);
		// refresh the table
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	@Override
	public int getRowCount() {
		return lesson.length;
	}
	
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

}