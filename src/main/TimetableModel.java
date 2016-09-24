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
 * @author f.petruschke
 *
 */
public class TimetableModel extends AbstractTableModel{
	public static Entry[][] entry;
	String[] columnNames;
	
	@SuppressWarnings("unchecked")
	/**
	 * constructor TimetableModel
	 */
	public TimetableModel() {
		
		// defining columnName
		String[] columnNames = {"Zeit",
                "Montag",
                "Dienstag",
                "Mittwoch",
                "Donnerstag",
                "Freitag"};
		
		// defining the data for the jTable beginning with setting the rows and columns
		// get current number of timeslots for number of rows
		int rows = (int) MongoDbConnector.db.getCollectionFromString("timeslots").count();
		// get current number of possible weekdays for number of columns
		int columns = ((int) MongoDbConnector.db.getCollectionFromString("weekdays").count()) + 1;	// (+1 column for the timeslots!)
		entry = new Entry [rows][columns];		
		for(int row = 0; row < rows; row++) {			
			for(int column = 0; column < columns; column++) {
				
				// if first column: show time from and until (timeslots)
				if(column == 0) {
					// insert from time to until time
					DBObject hour = MongoDbQueries.findOneInCollection("timeslots", "hourId", Integer.toString(row+1));
					String from = (String) ((BasicBSONObject) hour).get("from");
					String until = (String) ((BasicBSONObject) hour).get("until");
					entry[row][column] = new Entry(from, until);
					
				// else get the corresponding data (data per timeslot and weekday)
				} else {
					
					// get the timetable column corresponding to weekday and course hour
					DBObject weekday = MongoDbQueries.findOneInCollection("weekdays", "nameId", Integer.toString(column));
					DBObject hourId = MongoDbQueries.findOneInCollection("timeslots", "hourId", Integer.toString(row+1));
					DBCursor timetableColumn = MongoDbQueries.findOneInCollectionAnd("timetable", "weekday", weekday, "timeslot", hourId);
					
					// check if data as available or if its an empty hour
					if(timetableColumn!=null && timetableColumn.count()>0) {
						while(timetableColumn.hasNext()) {
							DBObject hourObject = timetableColumn.next();
							
							// get all the teachers names
							Object teachers = hourObject.get("teachers");
							ArrayList<String> teacherNames = new ArrayList<>();
							if(teachers instanceof BasicDBList) {
								for (int i = 0; i < ((ArrayList<Object>) teachers).size(); i++) {
									Object listElement = ((BasicBSONList) teachers).get(i);
									Object teacher = ((BasicBSONObject) listElement).get("teacher");
									String teacherName = (String) ((BasicBSONObject) teacher).get("name"); 
									teacherNames.add(teacherName);
								}
							} else {
								String name = (String) ((BasicBSONObject) teachers).get("name");
								teacherNames.add(name);
							}
							
							// get the course name
							Object course = hourObject.get("course");
							String courseName = (String) ((BasicBSONObject) course).get("name");
							
							// get the room name
							Object room = hourObject.get("room");
							String roomName = (String) ((BasicBSONObject) room).get("name");
							
							// insert the result data
							entry[row][column] = new Entry((String) weekday.get("shortname"), (String) hourId.get("from"),teacherNames, roomName, courseName);
						}
					} else {
						// no data was found for that day at that time block
						entry[row][column] = new Entry(false);
					}
				}
			}
		}
		
		this.columnNames = columnNames;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int columnIndex) {
		return Entry.class;
	}
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return TimetableModel.entry[rowIndex][columnIndex];
	}

	public void setValueAt(ArrayList<String> teacherNames, String room, String subject, int rowIndex, int columnIndex) {
		Entry newLesson = entry[rowIndex][columnIndex];
		newLesson.setTeacher(teacherNames);
		newLesson.setRoom(room);
		newLesson.setSubject(subject);
		// refresh the table
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	@Override
	public int getRowCount() {
		return entry.length;
	}
	
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}
	
	public static void refreshTable() {
		TimetableModel table = new TimetableModel();
		table.fireTableDataChanged();
	}

}
