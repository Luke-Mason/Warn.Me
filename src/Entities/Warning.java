package Entities;

public class Warning {

		private String warningMsg;
		
		public Warning(String msg)
		{
			warningMsg = msg;
		}
		
		public void setMsg(String msg)
		{
			warningMsg = msg;
		}
		
		public String toString()
		{
			return warningMsg;
		}
}
