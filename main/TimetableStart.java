package main;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

/**
 * class TimetableStart
 * 
 * This class starts the program
 * 
 * @author f.petruschke
 *
 */
public class TimetableStart {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		
		// turning of the unnessecary loggin into console
		Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
		mongoLogger.setLevel(Level.SEVERE); 
		
		// initializing the db connection
		new DatabaseConnector();
		
		// creating the collection weekdays (deleting it first for janitoring)
		DatabaseCollection.deleteCollection(DatabaseConnector.db.getCollection("weekdays"));
		DatabaseCollection.createCollection("weekdays");
		Map<String, String> weekdays = new HashMap<>();
		weekdays.put("Montag", "Mo");
		weekdays.put("Dienstag", "Di");
		weekdays.put("Mittwoch", "Mi");
		weekdays.put("Donnerstag", "Do");
		weekdays.put("Freitag", "Fr");
		for(Entry<String,String> weekday : weekdays.entrySet()){
			DatabaseQueries.insertWeekday(weekday.getKey(), weekday.getValue());
	    }
		
		// creating the collection timeslots (deleting it first for janitoring)
		DatabaseCollection.deleteCollection(DatabaseConnector.db.getCollection("timeslots"));
		DatabaseCollection.createCollection("timeslots");
		Map<String, String> timeslots = new HashMap<>();
		timeslots.put("7:45", "8:30");
		timeslots.put("8:30", "9:15");
		timeslots.put("9:30", "10:15");
		timeslots.put("10:15", "11:00");
		timeslots.put("11:30", "12:15");
		timeslots.put("12:15", "13:00");
		timeslots.put("13:30", "14:15");
		timeslots.put("14:15", "15:00");
		timeslots.put("15:15", "16:00");
		timeslots.put("16:00", "16:45");
		for(Entry<String,String> timeslot : timeslots.entrySet()){
			DatabaseQueries.insertTimeslot(timeslot.getKey(), timeslot.getValue());
	    }
		
		// creating the collection rooms (deleting it first for janitoring)
		DatabaseCollection.deleteCollection(DatabaseConnector.db.getCollection("rooms"));
		DatabaseCollection.createCollection("rooms");
		String[] rooms = new String[] {"221", "208", "H1.1"};
		for(String room : rooms){
			DatabaseQueries.insertRoom(room);
	    }
		
		// creating the collection courses (deleting it first for janitoring)
		DatabaseCollection.deleteCollection(DatabaseConnector.db.getCollection("courses"));
		DatabaseCollection.createCollection("courses");
		String[] courses = new String[] {"AE", "IT", "SKIL", "GUS", "SUK", "OGP", "WUG", "IT-WS"};
		for(String course : courses){
			DatabaseQueries.insertCourse(course);
	    }
		
		// creating the collection teachers (deleting it first for janitoring)
		DatabaseCollection.deleteCollection(DatabaseConnector.db.getCollection("teachers"));
		DatabaseCollection.createCollection("teachers");
		String[] teachers = new String[] {"Wm", "Hr", "Zi", "l1", "Al", "Rt", "Hu"};
		for(String teacher : teachers){
			DatabaseQueries.insertTeacher(teacher);
	    }
		
		// creating the collection timetable (deleting it first for janitoring)
		DatabaseCollection.deleteCollection(DatabaseConnector.db.getCollection("timetable"));
		DatabaseCollection.createCollection("timetable");
		DatabaseQueries.insertIntoTimetable("Mo", "9:30", "221", "AE", new String[] {"Wm"});
		DatabaseQueries.insertIntoTimetable("Mo", "10:15", "221", "AE", new String[] {"Wm"});
		DatabaseQueries.insertIntoTimetable("Mo", "11:30", "221", "AE", new String[] {"Wm"});
		DatabaseQueries.insertIntoTimetable("Mo", "12:15", "221", "AE", new String[] {"Wm"});
		DatabaseQueries.insertIntoTimetable("Mo", "13:30", "221", "AE", new String[] {"Wm"});
		DatabaseQueries.insertIntoTimetable("Mo", "14:15", "221", "AE", new String[] {"Wm"});
		
		DatabaseQueries.insertIntoTimetable("Di", "8:30", "208", "IT", new String[] {"Hr"});
		DatabaseQueries.insertIntoTimetable("Di", "9:30", "208", "IT", new String[] {"Hr", "Zi"});
		DatabaseQueries.insertIntoTimetable("Di", "10:15", "208", "IT", new String[] {"Hr", "Zi"});
		DatabaseQueries.insertIntoTimetable("Di", "11:30", "208", "IT", new String[] {"l1"});
		DatabaseQueries.insertIntoTimetable("Di", "12:15", "208", "IT", new String[] {"l1"});
		DatabaseQueries.insertIntoTimetable("Di", "13:30", "208", "IT", new String[] {"Zi"});
		DatabaseQueries.insertIntoTimetable("Di", "14:15", "208", "IT", new String[] {"Zi"});
		DatabaseQueries.insertIntoTimetable("Di", "15:15", "208", "IT", new String[] {"Zi"});
		
		DatabaseQueries.insertIntoTimetable("Mi", "9:30", "H1.1", "GUS", new String[] {"Zi"});
		DatabaseQueries.insertIntoTimetable("Mi", "10:15", "H1.1", "GUS", new String[] {"Zi"});
		DatabaseQueries.insertIntoTimetable("Mi", "11:30", "221", "SUK", new String[] {"Al"});
		
		DatabaseQueries.insertIntoTimetable("Do", "9:30", "221", "OGP", new String[] {"Rt", "Hr"});
		DatabaseQueries.insertIntoTimetable("Do", "10:15", "221", "OGP", new String[] {"Rt", "Hr"});
		DatabaseQueries.insertIntoTimetable("Do", "11:30", "221", "OGP", new String[] {"Rt"});
		DatabaseQueries.insertIntoTimetable("Do", "12:15", "221", "OGP", new String[] {"Rt"});
		DatabaseQueries.insertIntoTimetable("Do", "13:30", "221", "WUG", new String[] {"Hu"});
		DatabaseQueries.insertIntoTimetable("Do", "14:15", "221", "WUG", new String[] {"Hu"});
		DatabaseQueries.insertIntoTimetable("Do", "15:15", "221", "WUG", new String[] {"Hu"});
		DatabaseQueries.insertIntoTimetable("Do", "16:00", "221", "WUG", new String[] {"Hu"});
		
		DatabaseQueries.insertIntoTimetable("Fr", "10:15", "221", "GUS", new String[] {"Zi"});
		DatabaseQueries.insertIntoTimetable("Fr", "11:30", "221", "IT-WS", new String[] {"Hr"});
		DatabaseQueries.insertIntoTimetable("Fr", "12:15", "221", "IT-WS", new String[] {"Hr"});
		DatabaseQueries.insertIntoTimetable("Fr", "13:30", "221", "IT-WS", new String[] {"Hr"});
		DatabaseQueries.insertIntoTimetable("Fr", "14:15", "221", "IT-WS", new String[] {"Hr"});
		
		DBCursor cursor = DatabaseQueries.showAllFromCollection("timetable");
		while (cursor.hasNext()) { 
			   System.out.println(cursor.next());
			   /*String firstname = cursor.next().get("firstname");
			   lab1.setText(firstname);*/
				//BasicDBObject obj = (BasicDBObject) cursor.next();
				//lab1.setText(obj.getString("firstname"));
			    //result.add(obj.getString("HomeTown"));
			}
		
		// show the gui
		TimetableWindow timetableWindow = new TimetableWindow("Stundenplan");
		timetableWindow.setSize(800,400);
		timetableWindow.setVisible(true);
		
		/**
		 * Example: Create db connection, create collection, delete collection and drop db connection
		 */
		// connect to db
		//DB db = DatabaseConntector.connectDb();
		
		// create a collection
		//DBCollection newCollection = DatabaseQueries.createCollection(db, "test");
		
		// drop the collection
		//DatabaseQueries.deleteCollection(newCollection);
		
		// close the db connection
		//DatabaseConntector.closeConnection();
		
	}

}
