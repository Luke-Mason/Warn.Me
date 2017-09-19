package application;

import javafx.fxml.FXMLLoader;
import java.io.File;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import program.Database;
import program.ReadFile;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	private static Logger log = Logger.getLogger(Main.class);
	public Main(){log.setLevel(Level.INFO);}
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
	        Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
	        primaryStage.setTitle("Warn Me - Email Risk Calculator");
	        primaryStage.setScene(new Scene(root));
	        primaryStage.setResizable(true);
	        primaryStage.show();		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void checkAndCreateDatabase(String file)
	{
		File db = new File("Database/"+file);
		if (!db.exists()) {
			log.debug("Creating Database "+file);
			Database data = new Database(file);
			data.createTable(file);
		}
	}

	public static void main(String[] args) {
		BasicConfigurator.configure();//configuring log4j
		checkAndCreateDatabase("phishing.db");
		
		launch(args);
		File path = new File("Email/test.txt");
		String fileName = path.getAbsolutePath();
		
		String[] fileLines = null;
		try//Opening the file with ReadFile() and reading its contents with openFile()
		{
			ReadFile file = new ReadFile(fileName);
			fileLines = file.openFile();
		}
		catch(IOException e)
		{
			log.info(e.getMessage());
			System.exit(1);
		}
		//Printing out the contents to screen as test
		for(int i = 0; i < fileLines.length; i++)
		{
			log.info(fileLines[i]);
		}

		//TODO Read file format
		//TODO return % RISK
		return;
	}	
}
