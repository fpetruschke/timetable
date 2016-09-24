package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;


/**
 * class MongoDbQueries
 * 
 * Holds all the database queries as methods
 * 
 * @author f.petruschke
 * 
 */
public class MongoDbQueries {
	
	/**
	 * method insertWeekday
	 * 
	 * Inserts a new weekday into the collection "weekdays"
	 * 
	 * @param nameId		id of the weekdayname
	 * @param name			name of the weekday (e.g. "Montag")
	 * @param shortname		shortname of the weekday (e.g. "Mo")
	 * @return				returns true on success
	 * @throws Exception	throws exception if inserting failed
	 */
	public static boolean insertWeekday(int nameId, String name, String shortname) throws Exception {
		
		DBCollection collection = MongoDbConnector.db.getCollection("weekdays");
		
		try {
			BasicDBObject document = new BasicDBObject();
			document.put("nameId", Integer.toString(nameId));
			document.put("name", name);
			document.put("shortname", shortname);
			collection.insert(document);
			return true;
			
		} catch (Exception e) {
			System.out.println("[ERROR] " + e.getMessage() + "STACKTRACE: " + e.getStackTrace());
			throw e;
		}
	}
	
	/**
	 * method insertTimeslot
	 * 
	 * inserts new timeslot into collection "timeslots"
	 * 
	 * @param hourId		id of the hour - e.g. 1 for a course from 7:45 - 8:30
	 * @param from			starttime - e.g. "7:45"
	 * @param until			endtime - e.g. "8:30"
	 * @return				returns true on success
	 * @throws Exception 	throws exception if insertion fails
	 */
	public static boolean insertTimeslot(int hourId, String from, String until) throws Exception {
		
		DBCollection collection = MongoDbConnector.db.getCollection("timeslots");
		
		try {
			BasicDBObject document = new BasicDBObject();
			document.put("hourId", Integer.toString(hourId));
			document.put("from", from);
			document.put("until", until);
			collection.insert(document);
			return true;
			
		} catch (Exception e) {
			System.out.println("[ERROR] " + e.getMessage() + "STACKTRACE: " + e.getStackTrace());
			throw e;
		}
	}
	
	/**
	 * method insertRoom
	 * 
	 * inserts new room into collection "rooms"
	 * 
	 * @param name			name of the room (e.g. "221")
	 * @return				returns true on success
	 * @throws Exception 	throws exception if insertion fails
	 */
	public static boolean insertRoom(String name) throws Exception {
		
		DBCollection collection = MongoDbConnector.db.getCollection("rooms");
		
		DBObject match = findOneInCollection("rooms", "name", name);
		// check if name is new, else there is no need for inserting
		if(null == match) {
			try {
				BasicDBObject document = new BasicDBObject();
				document.put("name", name);
				collection.insert(document);
				return true;
				
			} catch (Exception e) {
				System.out.println("[ERROR] " + e.getMessage() + "STACKTRACE: " + e.getStackTrace());
				throw e;
			}
		} else {
			return true;
		}
	}
	
	/**
	 * method insertCourse
	 * 
	 * inserts new course into collection "courses"
	 * 
	 * @param name			name of the course (e.g. "AE")
	 * @return				returns true on success
	 * @throws Exception 	throws exception if insertion fails
	 */
	public static boolean insertCourse(String name) throws Exception {
		
		DBCollection collection = MongoDbConnector.db.getCollection("courses");
		
		DBObject match = findOneInCollection("courses", "name", name);
		// check if name is new, else there is no need for inserting
		if(null == match) {
			try {
				BasicDBObject document = new BasicDBObject();
				document.put("name", name);
				collection.insert(document);
				return true;
				
			} catch (Exception e) {
				System.out.println("[ERROR] " + e.getMessage() + "STACKTRACE: " + e.getStackTrace());
				throw e;
			}
		} else {
			return true;
		}
	}
	
	/**
	 * method insertTeacher
	 * 
	 * inserts new course into collection "teachers"
	 * 
	 * @param name			name of the teacher (e.g. "AE")
	 * @return				returns true on success
	 * @throws Exception 	throws exception if insertion fails
	 */
	public static boolean insertTeacher(String name) throws Exception {
		
		DBCollection collection = MongoDbConnector.db.getCollection("teachers");
		
		DBObject match = findOneInCollection("teachers", "name", name);
		// check if name is new, else there is no need for inserting
		if(null == match) {
			try {
				BasicDBObject document = new BasicDBObject();
				document.put("name", name);
				collection.insert(document);
				return true;
				
			} catch (Exception e) {
				System.out.println("[ERROR] " + e.getMessage() + "STACKTRACE: " + e.getStackTrace());
				throw e;
			}
		} else {
			return true;
		}
	}
	
	/**
	 * method findOneInCollection
	 * 
	 * Returns one result from collection for the given key/value pair
	 * 
	 * @param collectionName	String		Name of the collection
	 * @param key				String		Name of the key to search for
	 * @param value				String		Name of the value the key has
	 * @return					DBObject	Returns the matching DBObject
	 */
	public static DBObject findOneInCollection(String collectionName, String key, String value) {
		DBCollection collection = MongoDbConnector.db.getCollection(collectionName);
		DBObject query = new BasicDBObject(key, value);
		DBObject result = collection.findOne(query);
		return result;
	}
	
	/**
	 * method findOneInCollection
	 * 
	 * Returns result from given collection
	 * 
	 * @param collectionName
	 * @param key
	 * @param value
	 * @return
	 */
	public static DBCursor findObjectsInCollection(String collectionName, String key, DBObject value) {
		DBCollection collection = MongoDbConnector.db.getCollection(collectionName);
		DBObject query = new BasicDBObject(key, value);
		DBCursor result = collection.find(query);
		return result;
	}
	
	/**
	 * method findOneInCollectionAnd
	 * 
	 * Find the results for the given parameters
	 * 
	 * @param collectionName	String		name of the collection to use
	 * @param key				String		name of the key to search for
	 * @param value				DBObject	Object for desired result
	 * @param andKey			String		name of the second key to search for
	 * @param andValue			DBObject	second Object for desired result
	 * @return					DBCursor	returns the cursort with the result
	 */
	public static DBCursor findOneInCollectionAnd(String collectionName, String key, DBObject value, String andKey, DBObject andValue) {
		DBCollection collection = MongoDbConnector.db.getCollection(collectionName);
		BasicDBObject andQuery = new BasicDBObject();
	    List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
	    obj.add(new BasicDBObject(key, value));
	    obj.add(new BasicDBObject(andKey, andValue));
	    andQuery.put("$and", obj);
	    DBCursor cursor = collection.find(andQuery);
		
		return cursor;
	}
	
	/**
	 * 
	 * method findOneInCollectionAnd
	 * 
	 * Find the results for the given parameters (Strings only!)
	 * 
	 * @param collectionName	String		name of the collection
	 * @param key				String		keyname to search for
	 * @param value				String		value of the desired result
	 * @param andKey			String		second keyname to search for
	 * @param andValue			String		second value of the desired result
	 * @return					DBCursor	returns the DBCursor of the resultset
	 */
	public static DBCursor findOneInCollectionAnd(String collectionName, String key, String value, String andKey, String andValue) {
		
		DBCollection collection = MongoDbConnector.db.getCollection(collectionName);
		BasicDBObject andQuery = new BasicDBObject();
	    List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
	    obj.add(new BasicDBObject(key, value));
	    obj.add(new BasicDBObject(andKey, andValue));
	    andQuery.put("$and", obj);
	    DBCursor cursor = collection.find(andQuery);
		
		return cursor;
	}
	
	/**
	 * insertIntoTimetable
	 * 
	 * Method for inserting entries into timetable
	 * 
	 * @param weekdayShortname	String		german shortname of the weekday
	 * @param timeslotFrom		String		begin time of the course - e.g. "7:45"
	 * @param roomName			String		name of the room
	 * @param courseName		String		name of the course
	 * @param teachers_id		Array		teachers
	 * @return					boolean		true of successful
	 * @throws Exception
	 */
	public static boolean insertIntoTimetable(String weekdayShortname, String timeslotFrom, String roomName, String courseName, String[] teachers_id) throws Exception {
		
		DBCollection collection = MongoDbConnector.db.getCollection("timetable");

		try {
			BasicDBObject document = new BasicDBObject();
			
			DBObject weekday = findOneInCollection("weekdays", "shortname", weekdayShortname);
			document.put("weekday", weekday);
			
			DBObject timeslot = findOneInCollection("timeslots", "from", timeslotFrom);
			document.put("timeslot", timeslot);
			
			DBObject room = findOneInCollection("rooms", "name", roomName);
			document.put("room", room);
			
			DBObject course = findOneInCollection("courses", "name", courseName);
			document.put("course", course);
			
			BasicDBList dbl = new BasicDBList();
			for (String teacher: teachers_id) {
				DBObject teacherObj = findOneInCollection("teachers", "name", teacher);
				dbl.add(new BasicDBObject("teacher",teacherObj));
			}
			document.append("teachers", dbl);
			collection.insert(document);
			return true;
			
		} catch (Exception e) {
			System.out.println("[ERROR] " + e.getMessage() + "STACKTRACE: " + e.getStackTrace());
			throw e;
		}
	}
	
	/**
	 * method deleteDocumentFromCollection
	 * 
	 * Deletes the document according to the given name.
	 * Important: Is only meant for usage with collections
	 * rooms, courses and teachers
	 * 
	 * @param 	collection		String		name of the DBCollection to delete from
	 * @param 	name			String		name of the document to delete
	 * @return					boolean		Returns true on success
	 * @throws Exception					Throws exception on failure
	 */
	@SuppressWarnings("unchecked")
	public static boolean deleteDocumentFromCollection(String collectionName, String name) throws Exception {
		
		if (collectionName != "teachers" && collectionName != "rooms" && collectionName != "courses") {
			System.out.println("[ERROR] You are not allowed to delete documents from " + collectionName);
			return false;
		} else {
			DBCollection collection = MongoDbConnector.db.getCollection(collectionName);
			DBObject match = findOneInCollection(collectionName, "name", name);
			// check if name is existent, else it can be ignored
			if(null != match) {
				System.out.println("[NOTICE] Trying to delete " + name + " from " + collectionName);
				try {
					
					// update the timetable and set the "notSet" object instead of the deleted one
					String column = "";
					switch(collectionName) {
						case("rooms"):
							column = "room";
							break;
						case("teachers"):
							column = "teachers";
							break;
						case("courses"):
							column = "course";
							break;
						default:
							break;
					}
					
					DBObject notSet = findOneInCollection(collectionName, "name", "n.a.");
					DBObject entryToDelete = findOneInCollection(collectionName, "name", name);
					
					DBCursor occurences = findObjectsInCollection("timetable", column, entryToDelete);
					if(occurences.size()<=0) {
						System.out.println("[WARNING] For some reason you don't get a result here...");
					}
					while(occurences.hasNext()) {
						DBObject entry = occurences.next();
						
						DBObject weekday = (DBObject) entry.get("weekday");
						String weekdayShortname = (String) weekday.get("shortname");
						
						DBObject timeslot = (DBObject) entry.get("timeslot");
						String timeslotFrom = (String) timeslot.get("from");
						
						DBObject room = (DBObject) entry.get("room");
						String roomName = (String) room.get("name");
						
						DBObject course = (DBObject) entry.get("course");
						String courseName = (String) course.get("name");
						
						ArrayList<String> teachersArray = new ArrayList<String>();
						Object teachers = entry.get("teachers");
						if(teachers instanceof BasicDBList) {
							for (int i = 0; i < ((ArrayList<Object>) teachers).size(); i++) {
								Object listElement = ((BasicBSONList) teachers).get(i);
								Object teacher = ((BasicBSONObject) listElement).get("teacher");
								String teacherName = (String) ((BasicBSONObject) teacher).get("name"); 
								teachersArray.add(teacherName);
							}
						} else {
							String teachersName = (String) ((BasicBSONObject) teachers).get("name");
							teachersArray.add(teachersName);
						}
												
						if(column == "course") {
							courseName = (String) notSet.get("name");
						} else if (column == "room") {
							roomName = (String) notSet.get("name");
						} else if (column == "teachers") {
							teachersArray.clear();
							teachersArray.add((String) notSet.get("name"));
						}
						
						MongoDbQueries.updateTimetableEntry(
								weekdayShortname, 
								timeslotFrom, 
								roomName, 
								courseName, 
								teachersArray);						
					}
					System.out.println("[NOTICE] Updated all occurences in timetable collection");
					
					// actual delete the entry
					BasicDBObject query = new BasicDBObject();
				    query.put("name", name);
				    DBObject dbObj = MongoDbConnector.db.getCollection(collectionName).findOne(query);
					collection.remove(dbObj);
					System.out.println("[SUCCESS] Deleted " + name + " from " + collectionName);
					return true;
					
				} catch (Exception e) {
					System.out.println("[ERROR] " + e.getMessage() + "STACKTRACE: " + e.getStackTrace());
					throw e;
				}
			} else {
				System.out.println("[NOTICE] No document found for name = " + name);
				return true;
			}
		}
	}

	/**
	 * method showAllFromCollection
	 * 
	 * @param collection 	Collection where to select from
	 * @return				Returns the DBCursor
	 */
	public static DBCursor showAllFromCollection(String collectionName) {
		DBCollection collection = MongoDbConnector.db.getCollection(collectionName);
		DBCursor cursor = collection.find();
		return cursor;
	}
	
	/**
	 * updateTimetableEntry
	 * 
	 * Method for updating the current timetable.
	 * 
	 * @param weekdayShortname	String			german shortname of the weekday - e.g. "Mo"
	 * @param timeslotFrom		String			time when the course starts - e.g. "7:45"
	 * @param roomName			String			name of the room
	 * @param courseName		String			name of the course
	 * @param teachersArray		ArrayList		ArrayList containing the teachers who lead the course
	 * @return					boolean			True if successful.
	 * @throws Exception						Throws exception if update could not be executed
	 */
	public static boolean updateTimetableEntry(String weekdayShortname, String timeslotFrom, String roomName, String courseName, ArrayList<String> teachersArray) throws Exception {
		
		DBCollection collection = MongoDbConnector.db.getCollection("timetable");
		
		DBObject weekday = MongoDbQueries.findOneInCollection("weekdays", "shortname", weekdayShortname);
		DBObject timeslot = MongoDbQueries.findOneInCollection("timeslots", "from", timeslotFrom);
		DBObject room = findOneInCollection("rooms", "name", roomName);
		DBObject course = findOneInCollection("courses", "name", courseName);
		
		DBCursor currentEntryCursor = findOneInCollectionAnd("timetable", "weekday", weekday, "timeslot", timeslot);
		
		if(currentEntryCursor!=null && currentEntryCursor.count()>0) {
			while(currentEntryCursor.hasNext()) {
				DBObject currentEntry = (BasicDBObject) currentEntryCursor.next();
				BasicDBObject editedEntry = new BasicDBObject();
				editedEntry.put("weekday", weekday);
				editedEntry.put("timeslot", timeslot);
				editedEntry.put("room", room);
				editedEntry.put("course", course);
				
				BasicDBList dbl = new BasicDBList();
				for (String teacher: teachersArray) {
					DBObject teacherObj = findOneInCollection("teachers", "name", teacher);
					dbl.add(new BasicDBObject("teacher",teacherObj));
				}
				editedEntry.append("teachers", dbl);
				
				collection.update(currentEntry, editedEntry);
				return true;
			}
		} else {
			System.out.println("[ERROR] Could not find an entry for that day.");
			return false;
		}
		return false;
			
	}
	
	/**
	 * getTeacherNamesAsArray
	 * 
	 * Method for iterating over the teachers collection and converting the
	 * resultset into a String array.
	 * 
	 * @return		Array		Returns a String Array
	 */
	public static String[] getTeacherNamesAsArray() {
		// read out available rooms
		DBCursor teachersCursor = MongoDbQueries.showAllFromCollection("teachers");
		ArrayList<String> values = new ArrayList<String>();
		if(teachersCursor!=null && teachersCursor.count()>0) {
			while(teachersCursor.hasNext()) {
				DBObject teacherObject = teachersCursor.next();
				String teacher = (String) teacherObject.get("name");
				values.add(teacher);
			}
		}
		Object[] teacherNamesObjectArray = values.toArray();
		String[] teacherNamesStringArray = Arrays.copyOf(teacherNamesObjectArray, teacherNamesObjectArray.length, String[].class);
		return teacherNamesStringArray;
	}
}