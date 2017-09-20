package application;

import javafx.fxml.FXMLLoader;
//import java.io.File;
//import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
//import program.Database;
//import program.ReadFile;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application{
	private static Logger log = Logger.getLogger(Main.class);
	public Main(){log.setLevel(Level.INFO);}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
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

	public static void main(String[] args) 
	{
		BasicConfigurator.configure();//configuring log4j
		log.info("Program Launch");
		launch(args);
		log.info("Program Close");
		return;
	}	
}
;