package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import java.io.File;
//import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import program.Controller;
import program.Database;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

//import program.ReadFile;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class SampleFXMLDocumentController implements Initializable{
	private static Logger log = Logger.getLogger(SampleFXMLDocumentController.class);
	
	public SampleFXMLDocumentController()
	{
		log.setLevel(Level.INFO);
	}
	
	@FXML
	private Button scanBtn, cancelBtn;
	
	@FXML
	private Label label;
	
	@FXML
	private TextArea textArea;
	
	@FXML
	private PieChart pieChart;
	
	
	@FXML
	private void handleScanButtonAction(ActionEvent event)
	{
		Controller control = new Controller();
		String text = textArea.getText();
		log.info("\n"+text+"\n");
		int linkRisk = control.checkLinkRisk(text);
		int emailRisk = control.checkEmailRisk(text);
		int phraseRisk = control.checkPhraseRisk(text);
		/*CALCULATE RISK*/
		int safe, linkRiskRatio, emailRiskRatio, phraseRiskRatio;
		int[] totals = {linkRisk,emailRisk,phraseRisk};
		int risk = 0;
		/*FIND BIGEST VALUE*/
		for(int i = 0; i< totals.length; i++)
		{
			if(totals[i]>risk)
				risk = totals[i];
		}
		
		int totalRisk = linkRisk + emailRisk + phraseRisk;
		safe = 100 - risk;
		linkRiskRatio = linkRisk/totalRisk;
		emailRiskRatio =  emailRisk/totalRisk;
		phraseRiskRatio =  phraseRisk/totalRisk;
		
		//riskAnimation = risk;
		
		ObservableList<PieChart.Data> pieChartData = 
                FXCollections.observableArrayList(
                    new PieChart.Data("Risky Links", linkRiskRatio),
                    new PieChart.Data("Risky Email Addresses", emailRiskRatio),
                    new PieChart.Data("Risky Phrases", phraseRiskRatio),
                    new PieChart.Data("Risky Links", safe));

        if(textArea.getText() != "")
        {
	        pieChart.setTitle("Monthly Record");
	        pieChart.setData(pieChartData);
        }
		
	}
	
	/**
	 * Refreshes textArea and pie chart to blank
	 * @param event
	 */
	@FXML
	private void handleCancelButtonAction(ActionEvent event)
	{
		ObservableList<PieChart.Data> pieChartData = 
                FXCollections.observableArrayList();
        pieChart.setTitle("");
        pieChart.setData(pieChartData);       
        textArea.setText("");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		checkAndCreateDatabase("phishing.db");
		
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
	
	
	//If we need to read a file
	/*File path = new File("Email/test.txt");
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
	}*/
}
