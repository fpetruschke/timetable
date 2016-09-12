package main;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * class DatabaseQueries
 * 
 * Holds all the database queries as methods
 * 
 * @author petrusp
 *
 */
public class DatabaseQueries {

	public static DBCollection createCollection(DB db, String collectionName) throws Exception {
		try {
			DBCollection coll = db.getCollection(collectionName);
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
