package program;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import Entities.*;
import application.Main;


public class DBConnect {
	private static Logger log = Logger.getLogger(Main.class);
	public DBConnect()
	{
		log.setLevel(Level.DEBUG);
	}
	
	/**
	 * creates a connection to the database, to be called multiple times in the class by get and set methods
	 * @return
	 */
	private Connection connect()
	{

		String url = "jdbc:sqlite:Database/phishing.db";
        Connection connect = null;
        try { 
            connect = DriverManager.getConnection(url);
        } catch (SQLException sqle) {
            log.warn(sqle.getMessage());
        }
        return connect;
	}
	/*************************************************************************************/

	public void addWebsite(String address, String listType)
	{
		String query = "INSERT INTO WEBSITES VALUES (?,?);";
		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			inject.setString(1,address);
			inject.setString(2,listType);
			inject.execute();
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
	}
	
	public void addEmail(String domain, Date firstSeen, Date lastSeen, int numberOfOccurences, String listType)
	{
		String query = "INSERT INTO EMAILS VALUES (?,?,?,?,?);";
		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			inject.setString(1,domain);
			inject.setDate(2,firstSeen);
			inject.setDate(3,lastSeen);
			inject.setInt(4,numberOfOccurences);
			inject.setString(5,listType);
			inject.execute();
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
	}
	
	public ArrayList<Website> findWebsites(String keyword)
	{
		log.debug("IN findWebsites\n");
		ArrayList<Website> ws = new ArrayList<>();
		String address;
		String listType; 
		
		String query = 	  "SELECT * "
						+ "FROM WEBSITES "
						+ "WHERE domain LIKE ?";

		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//crates a user from the found information
			inject.setString(1,"%"+keyword+"%");
			ResultSet output = inject.executeQuery();
			while (output.next()){
				address = output.getString(1);
				listType = output.getString(2);
				ws.add(new Website(address, listType));
			}
			output.close();
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		log.debug("OUT findWebsites\n");
		return ws;
	}
	
	public Website getWebsite(String address)
	{
		log.debug("IN getWebsites\n");
		Website ws = null;		
		String query = 	  "SELECT * FROM WEBSITES WHERE domain = ?";

		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//crates a user from the found information
			inject.setString(1,address);
			ResultSet output = inject.executeQuery();
			if(!output.first())
				ws = new Website(address);
			else		
				ws = new Website(output.getString(1), output.getString(2));
			output.close();
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		log.debug("OUT getWebsite\n");
		return ws;
	}
	
	public ArrayList<Website> getWebsitesOfListType(String listType)
	{
		log.debug("IN getWebsitesOfListType\n");
		ArrayList<Website> ws = new ArrayList<>();		
		String query = 	  "SELECT * "
						+ "FROM WEBSITES "
						+ "WHERE white_black_listed = ?";

		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//crates a user from the found information
			inject.setString(1,listType);
			ResultSet output = inject.executeQuery();
			while (output.next()){
				String domain = output.getString(1);
				String list = output.getString(2);
				ws.add(new Website(domain,list));
			}
			output.close();
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		log.debug("OUT getWebsitesOfListType\n");
		return ws;
	}
	
	public Email getEmail(String domain)
	{
		log.debug("IN getEmail\n");
		Email email = new Email(domain);	
		String newAddress = "";
		Date firstSeen = null;
		Date lastSeen = null;
		int numberOfOccurences = 0;
		String listType = "";
		String query = 	  "SELECT * FROM EMAILS WHERE address = ?";

		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//crates a user from the found information
			inject.setString(1,domain);
			ResultSet output = inject.executeQuery();	
			while(output.next())
			{
				newAddress = output.getString(1);
				firstSeen = output.getDate(2);
				lastSeen = output.getDate(3);
				numberOfOccurences = output.getInt(4);
				listType = output.getString(5);
			}
			email = new Email(domain,firstSeen,lastSeen,numberOfOccurences,listType);
			output.close();
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		log.debug("OUT getEmail\n");
		return email;
	}
}
