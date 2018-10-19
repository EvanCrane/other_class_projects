package Server;
/*
 * BattleshipServer class
 */

import java.net.*;
import java.util.*;

public class BattleshipServerMulti
{	
	//ArrayList to store active game threads
	private static ArrayList<GameThread> games = new ArrayList<GameThread>();
	
	public static void main(String[] args) throws Exception
	{
		//Call the server start method
		new BattleshipServerMulti();
	}
	
	//Runs forever, continually pairing clients for games in new threads
	public BattleshipServerMulti() throws Exception
	{	    
		//Start server
		ServerSocket servSock = new ServerSocket(30005);
		System.out.println("--- Server Online ---");

		//Run forever
		while(true)
		{
			//Start a new game thread
			GameThread thread = new GameThread(servSock);
			if(thread != null)
			{
				//If thread is initialized, add it to the active games array and start it
				games.add(thread);
				thread.start();
			}
			//Implement a way to remove games that have ended clients that disconnect
			//Implement a way for server to exit
		}
	}
}
