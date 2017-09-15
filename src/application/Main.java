package application;
import java.io.File;
import java.io.IOException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;



public class Main extends Application {
	private static Logger log = Logger.getLogger(Main.class);
	public Main(){log.setLevel(Level.INFO);}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
			
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Warn Me - Email Risk Calculator");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		if(args.length != 1)//Checking for the argument e.g test.txt
		{
			log.warn("Invalid command line, exactly one argument required");
			System.exit(1);
		}
		
		File path = new File(args[0]);//Getting path
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
