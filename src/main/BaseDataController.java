package main;

import java.util.ArrayList;
import java.util.Arrays;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class BaseDataController {

	/**
	 * insertNewBaseData
	 * 
	 * Inserts new basedata to the matching collection.
	 * It is only allowed to insert into
	 * rooms, teachers and courses
	 * 
	 * @param collectionName	String		Name of the collection to insert into
	 * @param name				String		Value (name) to insert
	 * @throws Exception					Throws exception on failure
	 */
	public static void insertNewBaseData(String collectionName, String name) throws Exception {
		
		DBObject match = MongoDbQueries.findOneInCollection(collectionName, "name", name);
		// check if name is existent, else it can be ignored
		if(null == match) {
			switch(collectionName) {
				case("rooms"):
					MongoDbQueries.insertRoom(name);
					break;
				case("teachers"):
					MongoDbQueries.insertTeacher(name);
					break;
				case("courses"):
					MongoDbQueries.insertCourse(name);
					break;
				default:
					System.out.println("[NOTICE] Given collection " + collectionName + " not editable.");
					break;
			}
		} else {
			System.out.println("[NOTICE] The element you're trying to add is already existent.");
		}
	}
	
	/**
	 * deleteBaseDataDocument
	 * 
	 * Only allowed for rooms, courses and teachers.
	 * Calls the delete query.
	 * 
	 * @param collectionName	String		Name of the collection to delete from
	 * @param name				String		Value of the name
	 * @throws Exception					Throws Exception on failure
	 */
	public static void deleteBaseDataDocument(String collectionName, String name) throws Exception {
		if(collectionName != "rooms" && collectionName != "teachers" && collectionName != "courses") {
			System.out.println("[NOTICE] You cannot delete document from " + collectionName);
		} else {
			MongoDbQueries.deleteDocumentFromCollection(collectionName, name);
		}
	}
	
	/**
	 * getNamesOfCollectionAsArray
	 * 
	 * Takes a collection name as string and returns the name of each
	 * stored entry in that collection as string array.
	 * 
	 * @param collectionName	String		Name of an existing collection
	 * @return					Array		String array containing the names
	 */
	public static String[] getNamesOfCollectionAsArray(String collectionName) {
		// read out available rooms
		DBCursor collectionsCursor = MongoDbQueries.showAllFromCollection(collectionName);
		ArrayList<String> values = new ArrayList<String>();
		if(collectionsCursor!=null && collectionsCursor.count()>0) {
			while(collectionsCursor.hasNext()) {
				DBObject documentObject = collectionsCursor.next();
				String documentName = (String) documentObject.get("name");
				values.add(documentName);
			}
		}
		Object[] namesObjectArray = values.toArray();
		String[] namesStringArray = Arrays.copyOf(namesObjectArray, namesObjectArray.length, String[].class);
		return namesStringArray;
	}
	
	
	
}
