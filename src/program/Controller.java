package program;

import java.util.ArrayList;

public class Controller {

	public Controller(){}
	
	/**
	 * 
	 * @param text
	 * @return the % of Risk
	 */
	public int checkEmailRisk(String text)
	{
		int risk = 0;
		
		ArrayList<String> foundEmails = findEmails(text);
		
		return risk;
	}

	public int checkLinkRisk(String text) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int checkPhraseRisk(String text) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public ArrayList<String> findEmails(String text)
	{
		ArrayList<String> emails = new ArrayList<>();
		int index = 0;
		int index2 = 0;
		int count = 0;
		String email = null;
		boolean loop = true;
		while((index = text.indexOf('@',index2)) != -1)
		{
			index2 = index;
			index--;//move 1 to left to avoid comparing @ symbol
			
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
				if(index <= 0)
					break;
			}
			count = 0;
			index++;//so it is pointing to a valid char and not stuck on invalid, from exit of while
			index2++;//move 1 to right to avoid comparing @ symbol
			while(count < 255)
			{
				//System.out.println(text.charAt(index2));
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
			//System.out.println("Index First = "+index+"  Index End = "+index2);
			email = text.substring(index,index2);
			System.out.println(email);
			if(isValidEmailAddress(email) && email != null && email.length() < 255)
				emails.add(email);
		}
		return emails;		
	}

	public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9._-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
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