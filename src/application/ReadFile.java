package application;
import java.io.*;

public class ReadFile {
	private String file_path;
	
	public ReadFile(String file_path)
	{
		this.file_path = file_path; 
	}
	
	/**
	 * Counts how many lines are in the ReadFile file
	 * @return
	 * @throws IOException
	 */
	public int readLines() throws IOException
	{
		FileReader fr = new FileReader(file_path);
		BufferedReader br = new BufferedReader(fr);
		int numberOfLines = 0;
		while(br.readLine() != null)
		{
			numberOfLines++;
		}
		br.close();
		fr.close();
		return numberOfLines;
	}
	
	/**
	 * Opens the file and reads it
	 * @return lines in the file
	 * @throws IOException
	 */
	public String[] openFile() throws IOException
	{
		FileReader fr = new FileReader(file_path);
		BufferedReader textReader = new BufferedReader(fr);
		int numberOfLines = readLines();
		String[] textData = new String[numberOfLines];
		for(int i = 0; i < numberOfLines; i++)
		{
			textData[i] = textReader.readLine();
		}
		textReader.close();
		fr.close();
		return textData;
	}

}
