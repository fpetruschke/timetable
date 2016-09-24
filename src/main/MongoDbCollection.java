package main;

import com.mongodb.DBCollection;

/**
 * class MongoDbCollection
 * 
 * Handles the collections
 * 
 * @author f.petruschke
 *
 */
public class MongoDbCollection {

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
		System.out.println("[NOTICE] Trying to create collection " + collectionName + " ...");
		try {
			DBCollection coll = MongoDbConnector.db.getCollection(collectionName);
			System.out.println("[SUCCESS] Successfully created collection "+collectionName);
			return coll;
		} catch (Exception e) {
			System.err.println( "[ERROR] " + e.getClass().getName() + ": " + e.getMessage() );
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
		System.out.println("[NOTICE] Trying to delete collection " + collectionToDelete + " ...");
		try {
			collectionToDelete.drop();
			System.out.println("[SUCCESS] Dropped collection "+collectionToDelete.getName());
		} catch (Exception e) {
			System.err.println( "[ERROR] " + e.getClass().getName() + ": " + e.getMessage() );
	    	throw new Exception();
		}
	}
	
}
