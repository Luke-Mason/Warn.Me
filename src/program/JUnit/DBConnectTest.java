package program.JUnit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Entities.Email;
import Entities.Website;
import program.DBConnect;

public class DBConnectTest {

	DBConnect connect = new DBConnect();
	
	@Before
	public void setUp()
	{
		
	}
	
	@Test
	public void getEmailTest()
	{
		Email email = connect.getEmail("lukeymason.15@gmail.com");
		System.out.println(email.toString());
	}
	
	@Test
	public void getWebsiteTest()
	{
		System.out.println(connect.getWebsite("www.youtube.com"));
	}
	
	@Test
	public void addWebsiteTest()
	{
		//connect.addWebsite("www.youtube.com","white");
	}
	
	@Test
	public void findWebsitesTest()
	{
		System.out.println(connect.findWebsites(".com"));
	}
	
	@Test
	public void getWebsitesOfListTypeTest()
	{
		System.out.println(connect.getWebsitesOfListType("white"));
	}
	
	
	@After
	public void tearDown()
	{
		
	}
	
	
	
	
}
