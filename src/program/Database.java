package program;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Database
{
	private static Logger log = Logger.getLogger(Database.class);

	/*
	 * this class is to only be used once at the start Use DatabaseConneciton
	 * when accessing the database
	 */
	public Database(String fileName)
	{
		log.setLevel(Level.DEBUG);
		createNewDatabase(fileName);
	}

	/**
	 * create a database
	 */
	public void createNewDatabase(String fileName)
	{
		/**
		 * sets the url and name of the database if the database doesn't exist
		 * one will be created
		 */
		String url = "jdbc:sqlite:Database/" + fileName;
		try (Connection connect = DriverManager.getConnection(url))
		{
			if (connect != null)
			{
				DatabaseMetaData meta = connect.getMetaData();
				log.info("The driver is " + meta.getDriverName()+"\n");
				log.info("New database "+fileName+" has been created.\n");
			}
		} catch (SQLException sqle)
		{
			log.info(sqle.getMessage()+"\n");
		}
	}

	/**
	 * Function to create tables
	 */
	public void createTable(String filename)
	{
		String url = "jdbc:sqlite:Database/" + filename;

		String queryEmails = 
				"CREATE TABLE IF NOT EXISTS EMAILS "
				+ "("
					+ "address TEXT PRIMARY KEY,"
					+ "first_seen DATE,"
					+ "last_seen DATE NOT NULL,"
					+ "number_of_occurences INTEGER NOT NULL,"
					+ "white_black_listed VARCHAR(6) NOT NULL"
				+ ");";
		
		String queryBadPhrases = 
				"CREATE TABLE IF NOT EXISTS BAD_PHRASES "
				+ "("
					+ "phrase TEXT PRIMARY KEY,"
					+ "rating INTEGER NOT NULL"
				+ ");";
		
		String queryWebsites = 
				"CREATE TABLE IF NOT EXISTS WEBSITES "
				+ "("
					+ "domain TEXT PRIMARY KEY,"
					+ "white_black_listed VARCHAR(6) NOT NULL"
				+ ");";
		//https://blog.malwarebytes.com/101/2017/06/somethings-phishy-how-to-detect-phishing-attempts/
		/**
		 * Attempting to connect to the database so tables can be created
		 */
		try (Connection connect = DriverManager.getConnection(url); Statement sql = connect.createStatement())
		{
			//Creating Table 'EMAILS'
			sql.executeUpdate(queryEmails);
			log.debug("Table 'EMAILS' added");
			
			//Creating Table 'BAD_PHRASES'
			sql.executeUpdate(queryBadPhrases);
			log.debug("Table 'BAD_PHRASES' added");
			
			//Creating Table 'WEBSITES'
			sql.executeUpdate(queryWebsites);
			log.debug("Table 'WEBSITES' added");
			
		} catch (SQLException sqle)
		{
			// System.out.println("ERROR: couldn't add table:
			// "+sqle.getMessage());
			log.warn("ERROR: couldn't add table: " + sqle.getMessage());
		}
	}
	
	
	
	
}
