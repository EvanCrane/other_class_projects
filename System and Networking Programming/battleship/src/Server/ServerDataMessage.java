package Server;
/*
 * DataMessage class
 */

public class ServerDataMessage
{
	private int type;
	private int bool;
	private String text;
	private String data; //Acts as carrier for client board and boolean hits or misses
	private final String FIELDDEL = "%";
	private String sender = "Unset";
	
	public ServerDataMessage()
	{
		this.type = 0;
		this.bool = 0;
		this.text = "";
		this.data = "Server";
	}
	
	public ServerDataMessage(int type, int bool, String data, String text)
	{
		this.type = type;
		this.bool = bool;
		this.text = text;
		this.data = data;
	}
	

	public int getType()
	{
		return this.type;
	}
	
	public void setType(int type)
	{
		this.type = type;
	}
	
	public int getBool()
	{
		return this.bool;
	}
	
	public void setBool(int bool)
	{
		this.bool = bool;
	}
	
	public String getText()
	{
		return this.text;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
		
	public String getData()
	{
		return this.data;
	}
	
	public void setData(String data)
	{
		this.data = data;
	}
	
	public String getSender()
	{
		return this.sender;
	}
	
	public void setSender(String sender)
	{
		this.sender = sender;
	}
	
	public String toString()
	{
		return this.type + FIELDDEL + this.bool + FIELDDEL + this.text + FIELDDEL + this.data;
	}
	
	public void toDataMessage(String msg)
	{
		String[] str = msg.split(FIELDDEL);
		this.type = Integer.parseInt(str[0]);
		this.bool = Integer.parseInt(str[1]);
		this.text = str[2];
		this.data = str[3];
	}
	
	
//Server Functions only ---------------------------
	public void setHelloMsg()
	{
		this.type = 0; //Indicates hello message type
		this.bool = 0; //Not used
		this.text = "Please send player info DataMessage.";
		this.data = "Server";
	}
	
	public void setGameStartMsg(String opponent)
	{
		this.type = 1; //Indicates game start message
		this.bool = 0; //Not used
		this.text = "" + opponent;
		this.data = "Server";
	}
	
	public void setTurnMsg()
	{
		this.type = 2; //Indicates turn message type
		this.bool = 0; //Not used
		this.text = "It's your turn. Please send attack"; 
		this.data = "Server";
	}
	
	public void setAttResultMsg(int bool, String cellNum)
	{
		this.type = 3; //Indicates result message type
		this.bool = bool; //Hit or miss boolean result
		this.text = cellNum; //Attack coordinate
		this.data = "Server";
	}
	
	public void setDefResultMsg(int bool, String cellNum)
	{
		this.type = 4; //Indicates result message type
		this.bool = bool; //Hit or miss boolean result
		this.text = cellNum; //Attack coordinate
		this.data = "Server";
	}
	
	public void setWinnerMsg(int win)
	{
		this.type = 5; //Indicates winner message type
		this.bool = win; 
		this.text = "";
		this.data = "Server";

	}
}