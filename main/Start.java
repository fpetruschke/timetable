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
		new MongoDbConnector();
		
		// creating the collection weekdays (deleting it first for janitoring)
		MongoDbCollection.deleteCollection(MongoDbConnector.db.getCollection("weekdays"));
		MongoDbCollection.createCollection("weekdays");
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
			MongoDbQueries.insertWeekday(nameId, weekday.getKey(), weekday.getValue());
	    }
		
		// creating the collection timeslots (deleting it first for janitoring)
		MongoDbCollection.deleteCollection(MongoDbConnector.db.getCollection("timeslots"));
		MongoDbCollection.createCollection("timeslots");
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
			MongoDbQueries.insertTimeslot(hourId, timeslot.getKey(), timeslot.getValue());
	    }
		
		// creating the collection rooms (deleting it first for janitoring)
		MongoDbCollection.deleteCollection(MongoDbConnector.db.getCollection("rooms"));
		MongoDbCollection.createCollection("rooms");
		String[] rooms = new String[] {"", "221", "208", "H1.1"};
		for(String room : rooms){
			MongoDbQueries.insertRoom(room);
	    }
		
		// creating the collection courses (deleting it first for janitoring)
		MongoDbCollection.deleteCollection(MongoDbConnector.db.getCollection("courses"));
		MongoDbCollection.createCollection("courses");
		String[] courses = new String[] {"", "AE", "IT", "SKIL", "GUS", "SUK", "OGP", "WUG", "IT-WS"};
		for(String course : courses){
			MongoDbQueries.insertCourse(course);
	    }
		
		// creating the collection teachers (deleting it first for janitoring)
		MongoDbCollection.deleteCollection(MongoDbConnector.db.getCollection("teachers"));
		MongoDbCollection.createCollection("teachers");
		String[] teachers = new String[] {"", "Wm", "Hr", "Zi", "l1", "Al", "Rt", "Hu"};
		for(String teacher : teachers){
			MongoDbQueries.insertTeacher(teacher);
	    }
		
		// creating the collection timetable (deleting it first for janitoring)
		MongoDbCollection.deleteCollection(MongoDbConnector.db.getCollection("timetable"));
		MongoDbCollection.createCollection("timetable");
		MongoDbQueries.insertIntoTimetable("Mo", "7:45", "", "", new String[] {});
		MongoDbQueries.insertIntoTimetable("Mo", "8:30", "", "", new String[] {});
		MongoDbQueries.insertIntoTimetable("Mo", "9:30", "221", "AE", new String[] {"Wm"});
		MongoDbQueries.insertIntoTimetable("Mo", "10:15", "221", "AE", new String[] {"Wm"});
		MongoDbQueries.insertIntoTimetable("Mo", "11:30", "221", "AE", new String[] {"Wm"});
		MongoDbQueries.insertIntoTimetable("Mo", "12:15", "221", "AE", new String[] {"Wm"});
		MongoDbQueries.insertIntoTimetable("Mo", "13:30", "221", "AE", new String[] {"Wm"});
		MongoDbQueries.insertIntoTimetable("Mo", "14:15", "221", "AE", new String[] {"Wm"});
		MongoDbQueries.insertIntoTimetable("Mo", "15:15", "", "", new String[] {});
		MongoDbQueries.insertIntoTimetable("Mo", "16:00", "", "", new String[] {});
		
		MongoDbQueries.insertIntoTimetable("Di", "7:45", "", "", new String[] {});
		MongoDbQueries.insertIntoTimetable("Di", "8:30", "208", "IT", new String[] {"Hr"});
		MongoDbQueries.insertIntoTimetable("Di", "9:30", "208", "IT", new String[] {"Hr", "Zi"});
		MongoDbQueries.insertIntoTimetable("Di", "10:15", "208", "IT", new String[] {"Hr", "Zi"});
		MongoDbQueries.insertIntoTimetable("Di", "11:30", "208", "IT", new String[] {"l1"});
		MongoDbQueries.insertIntoTimetable("Di", "12:15", "208", "IT", new String[] {"l1"});
		MongoDbQueries.insertIntoTimetable("Di", "13:30", "208", "IT", new String[] {"Zi"});
		MongoDbQueries.insertIntoTimetable("Di", "14:15", "208", "IT", new String[] {"Zi"});
		MongoDbQueries.insertIntoTimetable("Di", "15:15", "208", "IT", new String[] {"Zi"});
		MongoDbQueries.insertIntoTimetable("Di", "16:00", "", "", new String[] {});
		
		MongoDbQueries.insertIntoTimetable("Mi", "7:45", "", "", new String[] {});
		MongoDbQueries.insertIntoTimetable("Mi", "8:30", "", "", new String[] {});
		MongoDbQueries.insertIntoTimetable("Mi", "9:30", "H1.1", "GUS", new String[] {"Zi"});
		MongoDbQueries.insertIntoTimetable("Mi", "10:15", "H1.1", "GUS", new String[] {"Zi"});
		MongoDbQueries.insertIntoTimetable("Mi", "11:30", "221", "SUK", new String[] {"Al"});
		MongoDbQueries.insertIntoTimetable("Mi", "12:15", "", "", new String[] {});
		MongoDbQueries.insertIntoTimetable("Mi", "13:30", "", "", new String[] {});
		MongoDbQueries.insertIntoTimetable("Mi", "14:15", "", "", new String[] {});
		MongoDbQueries.insertIntoTimetable("Mi", "15:15", "", "", new String[] {});
		MongoDbQueries.insertIntoTimetable("Mi", "16:00", "", "", new String[] {});
		
		MongoDbQueries.insertIntoTimetable("Do", "7:45", "", "", new String[] {});
		MongoDbQueries.insertIntoTimetable("Do", "8:30", "", "", new String[] {});
		MongoDbQueries.insertIntoTimetable("Do", "9:30", "221", "OGP", new String[] {"Rt", "Hr"});
		MongoDbQueries.insertIntoTimetable("Do", "10:15", "221", "OGP", new String[] {"Rt", "Hr"});
		MongoDbQueries.insertIntoTimetable("Do", "11:30", "221", "OGP", new String[] {"Rt"});
		MongoDbQueries.insertIntoTimetable("Do", "12:15", "221", "OGP", new String[] {"Rt"});
		MongoDbQueries.insertIntoTimetable("Do", "13:30", "221", "WUG", new String[] {"Hu"});
		MongoDbQueries.insertIntoTimetable("Do", "14:15", "221", "WUG", new String[] {"Hu"});
		MongoDbQueries.insertIntoTimetable("Do", "15:15", "221", "WUG", new String[] {"Hu"});
		MongoDbQueries.insertIntoTimetable("Do", "16:00", "221", "WUG", new String[] {"Hu"});
		
		MongoDbQueries.insertIntoTimetable("Fr", "7:45", "", "", new String[] {});
		MongoDbQueries.insertIntoTimetable("Fr", "8:30", "", "", new String[] {});
		MongoDbQueries.insertIntoTimetable("Fr", "9:30", "", "", new String[] {});
		MongoDbQueries.insertIntoTimetable("Fr", "10:15", "221", "GUS", new String[] {"Zi"});
		MongoDbQueries.insertIntoTimetable("Fr", "11:30", "221", "IT-WS", new String[] {"Hr"});
		MongoDbQueries.insertIntoTimetable("Fr", "12:15", "221", "IT-WS", new String[] {"Hr"});
		MongoDbQueries.insertIntoTimetable("Fr", "13:30", "221", "IT-WS", new String[] {"Hr"});
		MongoDbQueries.insertIntoTimetable("Fr", "14:15", "221", "IT-WS", new String[] {"Hr"});
		MongoDbQueries.insertIntoTimetable("Fr", "15:15", "", "", new String[] {});
		MongoDbQueries.insertIntoTimetable("Fr", "16:00", "", "", new String[] {});
		
		// debugging output - decomment if necessary - IMPORTANT: Import DBCursor!
		//DBCursor cursor = MongoDbQueries.showAllFromCollection("timetable");
		//while (cursor.hasNext()) { 
		//	System.out.println(cursor.next());
		//}
		
		// show the gui
		MainFrame mainFrame = new MainFrame("Stundenplan");
		mainFrame.setSize(750,700);
		mainFrame.setVisible(true);
		
	}

}
