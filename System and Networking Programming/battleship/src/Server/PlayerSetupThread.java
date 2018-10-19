package Server;
import java.net.*;
/*
 * PlayerSetupThread class
 */

import java.io.*;

public class PlayerSetupThread extends Thread
{
	//Variables to hold client info
	private Socket clientSock;
	private String playerName = "Guest";
	private String playerShips = "";	
	
	//I/O Streams
	private DataInputStream inStream;;;;
	private DataOutputStream outStream;
	
	//I/O DataMessages
	private ServerDataMessage inMsg = new ServerDataMessage();
	private ServerDataMessage outMsg = new ServerDataMessage();
	
	//Stores passed client socket and initializes Data I/O Streams
	public PlayerSetupThread(Socket clientSock) throws IOException
	{
		this.clientSock = clientSock;
		inStream = new DataInputStream(clientSock.getInputStream());
		outStream = new DataOutputStream(clientSock.getOutputStream());
	}
	
	//Thread operations
	public void run()
	{
		//Store and print socket address
		SocketAddress clientAddress = clientSock.getRemoteSocketAddress();
		System.out.println("Handling client at " + clientAddress);
		
		try {

			//Server sends client a name request message
			outMsg.setHelloMsg();
			outStream.writeUTF(outMsg.toString());
			
			//Server then waits for client response and converts the received string to a DataMessage
			String receiveStr = inStream.readUTF();
			inMsg.toDataMessage(receiveStr);
			this.playerShips = inMsg.getText();
			this.playerName = inMsg.getData();
			

			
			
			//Extracts the player board and name from the message text, stores, and prints
			
			System.out.println(playerName + " is ready to play.");
		}
		//Catch any errors
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		//End of PlayerSetupThread
	}
	
	//Returns private clientSock Socket
	public Socket getClientSocket()
	{
		return this.clientSock;
	}
	
	//Returns private playerName String
	public String getPlayerName()
	{
		return this.playerName;
	}
	
	//Returns private playerBoard String
	public String getPlayerShips()
	{
		return "" + this.playerShips;
	}
	
	
}