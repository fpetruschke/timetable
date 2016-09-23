package main;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class Start
 * 
 * This class starts the program.
 * 
 * Important: An acitve mongoDB setup is necessary for this program to run!
 * 
 * When starting the program, the mongoDB collections will be initialized/filled.
 * Before that happens, all existing data from the set collections will be deleted.
 * This is done for maintining and developing purposes but also for presenting the
 * functionality of the project.
 * 
 * @author f.petruschke
 *
 */
public class Start {

	/**
	 * main
	 * 
	 * Creates the db connection for the current client, creates the collections
	 * and documents if not existent yet and displays the gui.
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		// turning of the unnessecary loggin into console
		Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
		mongoLogger.setLevel(Level.SEVERE); 
		
		// initializing the db connection
		new MongoDbConnector();
		
		// fill the db if collections do not exist yet
		MongoDbInit datafill = new MongoDbInit();
		if(!datafill.collectionExists("weekdays") && 
			!datafill.collectionExists("timeslots") &&
			!datafill.collectionExists("courses") &&
			!datafill.collectionExists("rooms") && 
			!datafill.collectionExists("teachers")
		) {
			System.out.println("[NOTICE] Not all necessary collections available. Creating ...");
			datafill.insertData();
		} else {
			System.out.println("[SUCCESS] All necessary collections are available.");
		}
		
		// show the gui
		System.out.println("[NOTICE] Showing GUI ...");
		MainFrame mainFrame = new MainFrame("Stundenplan");
		mainFrame.setSize(750,700);
		mainFrame.setVisible(true);
		
	}

}
