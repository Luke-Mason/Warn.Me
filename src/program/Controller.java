package program;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import Entities.Email;
import Entities.Warning;
import application.Main;
import application.SampleFXMLDocumentController;

public class Controller {
	private static Logger log = Logger.getLogger(Main.class);
	public Controller(){log.setLevel(Level.INFO);}
	DBConnect connect;
	/**
	 * Finds email address with highest risk score and returns that /100
	 * @param text
	 * @return the % of Risk
	 */
	public int checkEmailRisk(String text)
	{
		SampleFXMLDocumentController sampleControl = new SampleFXMLDocumentController();
		log.info("IN checkEmailRisk\n");
		ArrayList<Integer> risk = new ArrayList<>();
		connect = new DBConnect();
		int highestRisk = 0;
		ArrayList<String> foundEmails = findEmailsInText(text);

		/*REMOVING DUPLICATES*/
		Set<String> hs = new HashSet<>();
		hs.addAll(foundEmails);
		foundEmails.clear();
		foundEmails.addAll(hs);
		
		log.debug("Emails found in text: "+foundEmails);
		
		for(String fe: foundEmails)
		{
			Email email = connect.getEmail(fe);
			System.out.println(email.toString());
			if(email.getNumberOfOccurences() == 0)
			{
				sampleControl.warnings.add(new Warning("WARNING: Foreign Email <"+email.getAddress()+">"));
			}
			if(email.getListType().equals("black"))
			{
				risk.add(100);
				sampleControl.warnings.add(new Warning("RISK: Blacklisted Email <"+email.getAddress()+">"));
			}
		}
		for(int r: risk)
		{
			if(r > highestRisk)
				highestRisk = r;
		}
		log.info("OUT checkEmailRisk\n");
		return highestRisk;
	}

	public int checkLinkRisk(String text) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int checkPhraseRisk(String text) {
		// TODO Auto-generated method stub
		return 0;
	}

	public ArrayList<String> findEmailsInText(String text)
	{
		log.info("IN findEmailsInText\n");
		ArrayList<String> emails = new ArrayList<>();
		int index = 0;
		int index2 = 0;
		int count = 0;
		String email = null;
		while((index = text.indexOf('@',index2)) != -1)
		{
			index2 = index;
			if(index > 0)
				index--;//move 1 to left to avoid comparing @ symbol
			else
				count = 65;
			
			while(count < 65)
			{
				boolean isCharBetween97And123 = (int)text.charAt(index) >= 97 && (int)text.charAt(index) <= 122;
				boolean isChar95or45or46 = (int)text.charAt(index) == 95 || (int)text.charAt(index) == 45 || (int)text.charAt(index) == 46;
				boolean isCharBetween64And91 = (int)text.charAt(index) >= 65 && (int)text.charAt(index) <= 90;
				boolean isCharBetween47And58 = (int)text.charAt(index) >= 48 && (int)text.charAt(index) <= 57;
				
				if(isCharBetween97And123 || isChar95or45or46 || isCharBetween64And91 || isCharBetween47And58)
				{
					index--;
					count++;
				}
				else
					break;
				if(index < 0)
					break;
			}
			count = 0;
			index++;//so it is pointing to a valid char and not stuck on invalid, from exit of while
			if(index2 < text.length()-1)
				index2++;//move 1 to right to avoid comparing @ symbol
			else
			{	
				index2 = text.length();
				System.out.println("continue");
				continue;
			}
			while(count < 255)
			{
				boolean isCharBetween97And123 = (int)text.charAt(index2) >= 97 && (int)text.charAt(index2) <= 122;
				boolean isChar95or45or46 = (int)text.charAt(index2) == 95 || (int)text.charAt(index2) == 45 || (int)text.charAt(index2) == 46;
				boolean isCharBetween64And91 = (int)text.charAt(index2) >= 65 && (int)text.charAt(index2) <= 90;
				boolean isCharBetween47And58 = (int)text.charAt(index2) >= 48 && (int)text.charAt(index2) <= 57;
				
				if(isCharBetween97And123 || isChar95or45or46 || isCharBetween64And91 || isCharBetween47And58)
				{
					index2++;
					count++;
				}
				else
					break;
				if(index2 >= text.length())
				{
					break;
				}
			}
			log.debug("index = "+index+" index2 = "+index2+"\n");
			if(index <= index2 && index >= 0 && index2 <= text.length())
			{
				email = text.substring(index,index2);
				System.out.println("Checking Email: "+email);
				if(isValidEmailAddress(email) /*&& email != null && email.length() < 255*/)
				{
					emails.add(email);
					System.out.println(email+" IS VALID");
				}
			}			
		}
		log.info("OUT findEmailsInText\n");
		return emails;		
	}

	public static boolean isValidEmailAddress(String email) {
		log.info("IN isValidEmailAddress\n");
        String ePattern = "^[a-zA-Z0-9._-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        log.info("OUT isValidEmailAddress\n");
        return m.matches();
		}
	
}


/*
 
From: Bing <bing@e.microsoft.com>
To: <lukey_mason@hotmail.com>
Message-ID: <em8.10bc5db8.20170919123502.2.-.365.3552803.1996802@e.microsoft.com>
Subject: Ace Bing's Footy Finals Quiz: You could WIN a Surface Book
Content-Type: multipart/alternative; boundary="-----15e9bd34dec_184dd"
X-Mailer: 10.1.0.4.39; msn
X-rpcampaign: msft411371
Return-Path: bing@e.microsoft.com

*/