package Entities;

import java.util.Calendar;
import java.util.Date;

public class Email {

	private String address, listType;
	private Date firstSeen, lastSeen;
	private int numberOfOccurences = 0;
	
	public Email()
	{
		listType = "unknown";
		address = null;
		firstSeen = null;
		lastSeen = null;
	}
	
	public Email(String address)
	{
		this.address = address;
		this.listType = "unknown";
		Calendar cal = Calendar.getInstance();
		this.firstSeen = cal.getTime();
		this.lastSeen = cal.getTime();
		this.numberOfOccurences = 0;
	}
	
	public Email(String address,Date firstSeen, Date lastSeen, int numberOfOccurences, String listType)
	{
		this.listType = listType;
		this.address = address;
		this.firstSeen = firstSeen;
		this.lastSeen = lastSeen;
		this.numberOfOccurences = numberOfOccurences;
	}
	/*GET METHODS*/
	public String getAddress()
	{
		return address;
	}
	
	public Date getFirstSeen()
	{
		return firstSeen;
	}
	
	public Date getLastSeen()
	{
		return lastSeen;
	}
	public String getListType()
	{
		return listType;
	}
	public int getNumberOfOccurences()
	{
		return numberOfOccurences;
	}

	
	/*SET METHODS*/
	public void setAddress(String add)
	{
		address = add;
	}
	
	public void setFirstSeen(Date date)
	{
		firstSeen = date;
	}
	
	public void setLastSeen(Date date)
	{
		lastSeen = date;
	}
	public void setListType(String type)
	{
		listType = type;
	}
	public void setNumberOfOccurences(int i)
	{
		numberOfOccurences = i;
	}
	
	/*OTHER METHODS*/
	public void addOccurence()
	{
		numberOfOccurences++;
	}
	
}
