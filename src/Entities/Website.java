package Entities;

public class Website {
	
	private String address;
	private String listType;
	
	public Website()
	{
		address = null;
		listType = "unknown";
	}
	public Website(String address)
	{
		this.address = address;
		this.listType = "unknown";
	}
	public Website(String address, String listType)
	{
		this.address = address;
		this.listType = listType;
	}
	
	public String getAddress()
	{
		return address;
	}

	public String getListType()
	{
		return listType;	
	}
	
	public boolean setListType(String listType)
	{
		if(!listType.equals("white") && !listType.equals("black") && !listType.equals("unknown"))//w - white, b - black, u - unknown
			return false;
		else this.listType = listType;

		return true;		
	}
	
	public String toString()
	{
		String status = listType;
		if(!listType.equals("white") && !listType.equals("black"))
			status = "neither black or white";
		return address + " is "+status+" listed"; 
	}
}
