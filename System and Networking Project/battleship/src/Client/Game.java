package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class Game {
//Global Variables
	//Server info
	private final int SERVPORT = 30005;
	private String serverIp = "localhost";
	private Socket socket;
	
	private ThreadRead thread;
	//Client setup info
	private String myName = "Guest";
	private String myBoard = "";
	//I/O objects
	private DataInputStream inStream;
	private DataOutputStream outStream;
	
	//DataMessage object
	private ClientDataMessage outMsg = new ClientDataMessage();
	private ClientDataMessage inMsg = new ClientDataMessage();

	private String msgStr = "";
	private int msgType = -1;	
	
	private Title title;
	private BoardSetup infoSetup;
	private ConnectingDialog cd;
	private Battleship battle;
	private GameOver gameOver;
	
	//constructor create default data message
	public Game(String ip) throws Exception 
	{
		title = new Title(this);
		title.setVisible(true);
		
		serverIp = ip;
	}
	
	public void setup()
	{
		title.setVisible(false);
		
		infoSetup = new BoardSetup(this);
		infoSetup.setVisible(true);
	}

	public void prepGame() throws Exception
	{
		infoSetup.dispose();
		
		battle = new Battleship(this);
		cd = new ConnectingDialog(this);
		cd.setVisible(true);
		
		Socket servSock = new Socket(serverIp, SERVPORT);
		thread = new ThreadRead(this, servSock);
		thread.start();
		
		outStream = new DataOutputStream(servSock.getOutputStream());

		
	}
	
	public void handleMsg(String msg) throws Exception
	{
		inMsg.toDataMessage(msg);
		int type = inMsg.getType();
		if(type == 0)
		{
			sendInfo();
		}
		else if(type == 1)
		{
			battle.setPlayerNames(myName, inMsg.getText());
			cd.dispose();

			battle.setVisible(true);
		}
		else if(type == 2)
		{
			battle.updateButton(true);
		}
		else if(type == 3)
		{
			battle.updatePlayerResult(inMsg.getText(), inMsg.getBool());
		}
		else if(type == 4)
		{
			battle.updateOppenentResult(inMsg.getText(), inMsg.getBool());
		}
		else if(type == 5)
		{
			battle.setVisible(false);
			battle.dispose();
			gameOver = new GameOver(inMsg.getBool(), serverIp);
			gameOver.setVisible(true);
			System.out.println("Game over");
		}
		
	}
	
	//Uses a socket to connect to server creates I/O objects


	//sends ready message to server / server places client into lobby
	public void sendInfo() throws IOException
	{		
		outMsg.setSender(this.myName);
		outMsg.setInfoMsg(this.myBoard);
		outStream.writeUTF(outMsg.toString());
	}
	
	public void setInfo(String name, String board)
	{
		this.myName = name;
		this.myBoard = board;
	}

	public void getAttack(String attack) throws Exception
	{	
		outMsg.setAttackMsg(attack);
		outStream.writeUTF(outMsg.toString());
	}
	
	public void setName(String name)
	{
		this.myName = name;
	}
	
	public String getName()
	{
		return this.myName;
	}
	
	public void setBoard(String board)
	{
		this.myBoard = board;
	}
	
	public String getBoard()
	{
		return this.myBoard;
	}
	

	
	public Title getTitle()
	{
		return this.title;
	}
	
	public void setTitle(Title t)
	{
		this.title = t;
	}

	public BoardSetup getInfoSetup()
	{
		return this.infoSetup;
	}
	
	public void setInfoSetup(BoardSetup b)
	{
		this.infoSetup = b;
	}


	public Battleship getBattle()
	{
		return this.battle;
	}
	
	public void setBattle(Battleship b)
	{
		this.battle = b;
	}
}
