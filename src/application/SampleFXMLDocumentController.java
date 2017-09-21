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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.util.Callback;
import program.Controller;
import program.Data;
import program.Database;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

//import program.ReadFile;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import Entities.Warning;

public class SampleFXMLDocumentController implements Initializable{
	private static Logger log = Logger.getLogger(SampleFXMLDocumentController.class);
	
	public SampleFXMLDocumentController()
	{
		log.setLevel(Level.INFO);
	}
	public static ArrayList<Warning> warnings = new ArrayList<>();
	
	@FXML
	private Button scanBtn, cancelBtn;
	
	@FXML
	private Label label1, label2;
	
	@FXML
	private TextArea textArea;
	
	@FXML
	private PieChart pieChart;
	
	@FXML
	public ListView<Warning> warnList;
	
	@FXML
	private void handleScanButtonAction(ActionEvent event)
	{
		warnings.clear();
        refreshList();
		Controller control = new Controller();
		String text = textArea.getText();
		//log.info("\n"+text+"\n");
		int linkRisk = control.checkLinkRisk(text);
		int emailRisk = control.checkEmailRisk(text);
		int phraseRisk = control.checkPhraseRisk(text);
		/*CALCULATE RISK*/
		int safe;
		double linkRiskRatio = 0, emailRiskRatio = 0, phraseRiskRatio = 0;
		int[] totals = {linkRisk,emailRisk,phraseRisk};
		int risk = 0;
		
		/*FIND BIGEST VALUE*/
		for(int i = 0; i < totals.length; i++)
		{
			if(totals[i]>risk)
				risk = totals[i];
		}
		
		int totalRisk = linkRisk + emailRisk + phraseRisk;
		safe = 100 - risk;
		if(totalRisk > 0)
		{
			linkRiskRatio = linkRisk/totalRisk;
			emailRiskRatio =  emailRisk/totalRisk;
			phraseRiskRatio =  phraseRisk/totalRisk;
		}
		
		ObservableList<PieChart.Data> pieChartData = 
                FXCollections.observableArrayList(
                    new PieChart.Data("Risky Links", linkRiskRatio),
                    new PieChart.Data("Risky Email Addresses", emailRiskRatio),
                    new PieChart.Data("Risky Phrases", phraseRiskRatio),
                    new PieChart.Data("Safe", safe));
		System.out.println(warnings);
		System.out.println(warnings.size());


		if(!textArea.getText().isEmpty())
       {
			
			refreshList();			
			System.out.println(warnList);
			label2.setText(totalRisk+"% Detected Risk");
    	    label2.setVisible(true);
    	    pieChart.setTitle("Warn Me");
	        pieChart.setLabelsVisible(true);
	        pieChart.setData(pieChartData);
       }
		
	}
	
	public void refreshList()
	{
		ObservableList<Warning> list = FXCollections.observableList(warnings);
		warnList.setItems(list);
		warnList.setCellFactory(new Callback<ListView<Warning>, ListCell<Warning>>() {
			@Override
			public ListCell<Warning> call(ListView<Warning> p) {

				ListCell<Warning> cell = new ListCell<Warning>() {
					@Override
					protected void updateItem(Warning t, boolean bln) {
						super.updateItem(t, bln);
						if (t != null) {
							setText(t.toString());
						} else {
							warnList.setPlaceholder(new Label("No Warnings"));
						}
					}
				};
				return cell;
			}
		});
	}
	/**
	 * Refreshes textArea and pie chart to blank
	 * @param event
	 */
	@FXML
	private void handleCancelButtonAction(ActionEvent event)
	{
		clear();
	}
	
	private void clear()
	{
		ObservableList<PieChart.Data> pieChartData = 
                FXCollections.observableArrayList();
		label2.setText("None");
	   	label2.setVisible(false);
        pieChart.setTitle("");
        pieChart.setData(pieChartData);       
        textArea.setText("");
        warnings.clear();
        refreshList();
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
			Data.populateDatabase();
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
