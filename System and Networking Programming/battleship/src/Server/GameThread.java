package Server;
/* 
 * GameThread class
 * Thread that handles client setup and game play for each pair of connections
 */

import java.net.*;
import java.util.*;

public class GameThread extends Thread
{
	//Store server and client sockets
	private ServerSocket servSock;
	private Socket clientSock[] = new Socket[2];
	
	//Array to hold both clients' PlayerSetupThreads
	private ArrayList<PlayerSetupThread> setupThreads = new ArrayList<PlayerSetupThread>(2);
	private PlayerSetupThread setup;
	
	//Create battle object for game play and set the winner to null
	private Battle battle;
	private String winner = null;
	
	//Store server socket from passed value
	public GameThread(ServerSocket servSock) 
	{
		this.servSock = servSock;
	}
	
	//Thread operations
	public void run()
	{
		try {
		//While two clients have not connected
		while(setupThreads.size() < 2)
		{
			//Store incoming client sockets and pass both to PlayerSetupThreads 
			clientSock[setupThreads.size()-1] = this.servSock.accept();
			setup = new PlayerSetupThread(clientSock[setupThreads.size() - 1]);
			setupThreads.add(setup);
			setup.start();
		}

		//Wait until both player setup threads have finished
		for(int i = 0; i < 2; i++)
		{
			setup = setupThreads.get(i);
			setup.join();
		}
				
		//Start the battle with the data from PlayerSetupThreads
		battle = new Battle(setupThreads.get(0), setupThreads.get(1));
		
		//Get the winner
		winner = battle.playGame();
		System.out.println(winner + " won.");
		}
		//Catch anything that could go wrong
		catch(Exception e) {}
	}	
}
