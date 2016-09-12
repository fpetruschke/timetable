package main;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

import com.mongodb.ServerAddress;
import java.util.Arrays;

/**
 * class DatabaseConnector
 * 
 * Creating and closing the database connection
 * 
 * @author petrusp
 *
 */
public class DatabaseConntector {

	public static String host = "localhost";
	public static int port = 27017;
	public static String databaseName = "test";
	public static MongoClient mongoClient;
	
	/**
	 * method connectDb
	 * 
	 * connects to the running mongodb server and returns the database object
	 * 
	 * @return
	 * @throws Exception
	 */
	public static DB connectDb() throws Exception {
		try{    	  
			// To connect to mongodb server
			mongoClient = new MongoClient(host, port);
			// Now connect to your databases
			DB db = mongoClient.getDB(databaseName);
			System.out.println("Connection to database successfully");
 
			return db;
			
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
