package Client;

public class ClientDataMessage 
{
	private int type;
	private int bool;
	private String text;
	private String sender = "";
	
	private final String FIELDDEL = "%";
	
	public ClientDataMessage()
	{
		this.type = 0;
		this.bool = 0;
		this.text = "";
		this.sender = "Unset";
	}
	
	public ClientDataMessage(String sender)
	{
		this.type = 0;
		this.bool = 0;
		this.text = "";
		this.sender = sender;
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
		return this.type + FIELDDEL + this.bool + FIELDDEL + this.text + FIELDDEL + this.sender;
	}
	
	public void toDataMessage(String msg)
	{
		String[] str = msg.split(FIELDDEL);
		this.type = Integer.parseInt(str[0]);
		this.bool = Integer.parseInt(str[1]);
		this.text = str[2];
		this.sender = str[3];
	}
	
	//Methods to format client message frames
	public void setInfoMsg(String board)
	{
		this.type = 0;
		this.text = board;
	}
	
	public void setReadyMsg()
	{
		this.type = 1;
		this.text = "Ready";
	}
	
	public void setAttackMsg(String attack)
	{
		this.type = 2;
		this.text = attack;
	}
	
	public void setQuitMsg()
	{
		this.type = 4;
		this.text = "Player quit the game";
	}

}
