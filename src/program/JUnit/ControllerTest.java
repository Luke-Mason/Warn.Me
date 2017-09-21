package program.JUnit;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import program.Controller;
import program.ReadFile;

public class ControllerTest {
	
	Controller control;
	
	@Before
	public void setUp()
	{
		control = new Controller();
	}
	
	@Test
	public void isValidEmailAddressTest1()
	{
		assertTrue(Controller.isValidEmailAddress("E1duMAC-0003Ph-6W@smtp08.steampowered.com"));
		assertTrue(Controller.isValidEmailAddress("lukeymason.15@gmail.com"));
		assertTrue(Controller.isValidEmailAddress("noreply@steampowered.com"));
	}
	
	@Test
	public void isValidEmailAddressTest2()
	{
		assertFalse(Controller.isValidEmailAddress("E1d<uMAC-0003Ph-6W@smtp08.steampowered.com"));
		assertFalse(Controller.isValidEmailAddress("<>lukeymason.15@gmail.com>"));
		assertFalse(Controller.isValidEmailAddress("smtp.mailfrom=noreply@steampowered.com"));
		assertFalse(Controller.isValidEmailAddress("header.i=@steampowered.com"));
		assertFalse(Controller.isValidEmailAddress("header.i^@steampowered.com"));
		assertFalse(Controller.isValidEmailAddress("header.i]@steampowered.com"));
		assertFalse(Controller.isValidEmailAddress("header.i[@steampowered.com"));
		assertFalse(Controller.isValidEmailAddress("header.i@steamp=owered.com"));
		assertFalse(Controller.isValidEmailAddress("header.i@steampo^wered.com"));
		assertFalse(Controller.isValidEmailAddress("header.i@steampower]ed.com"));
		assertFalse(Controller.isValidEmailAddress("@"));
	}
	
	@Test
	public void findEmailTest()
	{
		ArrayList<String> list = new ArrayList<>();
		File path = new File("Test_Data/original_msg.txt");
		String fileName = path.getAbsolutePath();
		
		String[] fileLines = null;
		try//Opening the file with ReadFile() and reading its contents with openFile()
		{
			ReadFile file = new ReadFile(fileName);
			fileLines = file.openFile();
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			System.exit(1);
		}
		//Printing out the contents to screen as test
		for(int i = 0; i < fileLines.length; i++)
		{
			control.findEmailsInText(fileLines[i]);
		}
	}
	
	@After
	public void tearDown()
	{
		
	}
}
