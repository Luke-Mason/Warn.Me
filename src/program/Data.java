package program;

import java.sql.Date;
import java.util.Calendar;

public class Data {
	
	public static void populateDatabase()
	{
		DBConnect connect = new DBConnect();
		Date firstSeen = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
		Date lastSeen = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
		connect.addEmail("lukeymason.15@gmail.com", firstSeen, lastSeen, 10, "white");
		connect.addEmail("lukey_mason@hotmail.com", firstSeen, lastSeen, 10, "white");
		connect.addEmail("olaspokes@nabruuea.com", firstSeen, lastSeen, 1, "black");
	}

}
