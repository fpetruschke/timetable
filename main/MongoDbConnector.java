package main;

import com.mongodb.MongoClient;
import com.mongodb.DB;

/**
 * class MongoDbConnector
 * 
 * Creating and closing the database connection
 * 
 * @author f.petruschke
 *
 */
public class MongoDbConnector {

	// initialization and definition of connection params
	private static String host = "localhost";
	private static int port = 27017;
	private static String databaseName = "test";
	public static MongoClient mongoClient;
	public static DB db;
	
	MongoDbConnector() throws Exception {
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
		System.out.println("[NOTICE] Trying to connect to mongoDB ...");
		try{    	  
			// To connect to mongodb server
			mongoClient = new MongoClient(host, port);
			// Now connect to your databases
			@SuppressWarnings("deprecation")
			DB openedDb = mongoClient.getDB(databaseName);
			System.out.println("[SUCCESS] Successfully connected to the database.");
			db = openedDb;
			
      } catch(Exception e) {
    	  System.err.println( "[ERROR]" + e.getClass().getName() + ": " + e.getMessage() );
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
		System.out.println("[NOTICE] Trying to close the connection to mongoDB ...");
		try {
			mongoClient.close();
			System.out.println("[SUCCESS] Successfully closed the connection to the database.");
		} catch(Exception e) {
			System.err.println( "[ERROR]" + e.getClass().getName() + ": " + e.getMessage() );
			throw new Exception();
		}
	}

}
