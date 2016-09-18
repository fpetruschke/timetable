package main;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class Start
 * 
 * This class starts the program.
 * 
 * Important: An acitve mongoDB setup is necessary for this program to run!
 * 
 * When starting the program, the mongoDB collections will be initialized/filled.
 * Before that happens, all existing data from the set collections will be deleted.
 * This is done for maintining and developing purposes but also for presenting the
 * functionality of the project.
 * 
 * @author f.petruschke
 * @author d.lentz
 *
 */
public class Start {

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
			int nameId = 0;
			switch(weekday.getValue()) {
				case("Mo"):
					nameId = 1;
					break;
				case("Di"):
					nameId = 2;
					break;
				case("Mi"):
					nameId = 3;
					break;
				case("Do"):
					nameId = 4;
					break;
				case("Fr"):
					nameId = 5;
					break;
			}
			DatabaseQueries.insertWeekday(nameId, weekday.getKey(), weekday.getValue());
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
			int hourId = 0;
			switch(timeslot.getKey()) {
				case("7:45"):
					hourId = 1;
					break;
				case("8:30"):
					hourId = 2;
					break;
				case("9:30"):
					hourId = 3;
					break;
				case("10:15"):
					hourId = 4;
					break;
				case("11:30"):
					hourId = 5;
					break;
				case("12:15"):
					hourId = 6;
					break;
				case("13:30"):
					hourId = 7;
					break;
				case("14:15"):
					hourId = 8;
					break;
				case("15:15"):
					hourId = 9;
					break;
				case("16:00"):
					hourId = 10;
					break;
			}
			DatabaseQueries.insertTimeslot(hourId, timeslot.getKey(), timeslot.getValue());
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
		
		// debugging output - decomment if necessary - IMPORTANT: Import DBCursor!
		//DBCursor cursor = DatabaseQueries.showAllFromCollection("timetable");
		//while (cursor.hasNext()) { 
		//	System.out.println(cursor.next());
		//}
		
		// show the gui
		TimetableWindow timetableWindow = new TimetableWindow("Stundenplan");
		timetableWindow.setSize(750,700);
		timetableWindow.setVisible(true);
		
	}

}
