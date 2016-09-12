package main;

import com.mongodb.DB;
import com.mongodb.DBCollection;

/**
 * class TimetableStart
 * 
 * This class starts the program
 * 
 * @author petrusp
 *
 */
public class TimetableStart {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		// connect to db
		DB db = DatabaseConntector.connectDb();
		
		// create a collection
		DBCollection newCollection = DatabaseQueries.createCollection(db, "test");
		
		// drop the collection
		DatabaseQueries.deleteCollection(newCollection);
		
		// close the db connection
		DatabaseConntector.closeConnection();
		
	}

}
