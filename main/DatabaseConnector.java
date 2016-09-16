package main;

import com.mongodb.MongoClient;
import com.mongodb.DB;

/**
 * class DatabaseConnector
 * 
 * Creating and closing the database connection
 * 
 * @author f.petruschke
 *
 */
public class DatabaseConnector {

	// initialization and definition of connection params
	private static String host = "localhost";
	private static int port = 27017;
	private static String databaseName = "test";
	public static MongoClient mongoClient;
	public static DB db;
	
	DatabaseConnector() throws Exception {
		connectDb();
	}
	
	/**
	 * method connectDb
	 * 
	 * connects to the running mongodb server and returns the database object
	 * 
	 * @throws Exception
	 */
	private static void connectDb() throws Exception {
		try{    	  
			// To connect to mongodb server
			mongoClient = new MongoClient(host, port);
			// Now connect to your databases
			@SuppressWarnings("deprecation")
			DB openedDb = mongoClient.getDB(databaseName);
			System.out.println("Connection to database successfully");
 
			db = openedDb;
			
      } catch(Exception e) {
    	  System.err.println( e.getClass().getName() + ": " + e.getMessage() );
    	  throw new Exception();
      }
	}
	
	/**
	 * method closeConnection
	 * 
	 * closes the existing connection of a client
	 * 
	 * @param mongoClient
	 * @throws Exception
	 */
	public static void closeConnection() throws Exception {
		try {
			mongoClient.close();
			System.out.println("Connection to database closed");
		} catch(Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			throw new Exception();
		}
	}

}
