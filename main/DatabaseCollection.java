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
