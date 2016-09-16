package main;

import com.mongodb.DBCollection;

/**
 * class DatabaseCollection
 * 
 * Handles the collections
 * 
 * @author f.petruschke
 *
 */
public class DatabaseCollection {

	/**
	 * createCollection
	 * 
	 * Method for creating the mongoDB collection by given name if not existent yet
	 * 
	 * @param collectionName	String			Name of the collection to find or create if not existent create
	 * @return					DBCollection	Returns DBCollection object
	 * @throws Exception						Throws Exception on failure
	 */
	public static DBCollection createCollection(String collectionName) throws Exception {
		try {
			DBCollection coll = DatabaseConnector.db.getCollection(collectionName);
			System.out.println("Collection "+collectionName+" successfully created");
			return coll;
		} catch (Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    	throw new Exception();
		}
	}
	
	/**
	 * deleteCollection
	 * 
	 * Method for deleting a given DBCollection
	 * 
	 * @param collectionToDelete	DBCollection to delete
	 * @throws Exception			Throws exception on failure
	 */
	public static void deleteCollection(DBCollection collectionToDelete) throws Exception {
		try {
			collectionToDelete.drop();
			System.out.println("Collection "+collectionToDelete.getName()+" was dropped");
		} catch (Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    	throw new Exception();
		}
	}
	
}
