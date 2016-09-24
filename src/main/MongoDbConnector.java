package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
	private static String host;
	private static String port;
	private static String databaseName;
	public static MongoClient mongoClient;
	public static DB db;
	
	MongoDbConnector() throws Exception {
		System.out.println("[NOTICE] Loading configuration ...");
		loadConfig();
		connectDb();
	}
	
	/**
	 * loadConfig
	 * 
	 * Method for loading the configuration parameters for connection
	 * to the mongoDB from the config file "MongoDbConfig.properties"
	 * 
	 * @throws IOException 		Throws Exception if file not found
	 */
	public void loadConfig() throws IOException {
		
		Properties prop = new Properties();
		String propFileName = "MongoDbConfig.properties";
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

		// check if properties file is existent
		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("[ERROR] Property file '" + propFileName + "' not found in the classpath");
		}

		// get the property values and set attributes
		host = prop.getProperty("host");
		port = prop.getProperty("port");
		databaseName = prop.getProperty("databaseName");
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
			mongoClient = new MongoClient(host, Integer.parseInt(port));
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
