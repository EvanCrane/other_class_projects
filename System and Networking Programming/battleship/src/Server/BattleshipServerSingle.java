package Server;
import java.net.*;
import java.util.*;

public class BattleshipServerSingle 
{
	private ServerSocket servSock;
	
	private Socket[] clientSock = new Socket[2];
	private ArrayList<PlayerSetupThread> setupThreads = new ArrayList<PlayerSetupThread>(2);
	
	private Battle battle;
	
	public static void main(String[] args) throws Exception 
	{
		new BattleshipServerSingle();
	}

	public BattleshipServerSingle() throws Exception
	{
		servSock = new ServerSocket(30005);
		System.out.println("--- Server Online ---");
		
		while(setupThreads.size() < 2)
		{
			System.out.println(setupThreads.size());
			//Store incoming client sockets and pass both to PlayerSetupThreads 
			clientSock[setupThreads.size()] = this.servSock.accept();
			PlayerSetupThread setup = new PlayerSetupThread(clientSock[setupThreads.size()]);
			setupThreads.add(setup);
			setup.start();
		}
		//Wait for both client setup threads to finish
		PlayerSetupThread thread = setupThreads.get(0);
		thread.join();
		thread = setupThreads.get(1);
		thread.join();
		
		//Start the battle, passing setup threads containing player info.
		battle = new Battle(setupThreads.get(0), setupThreads.get(1));
		
		//Get the winner
		String winner = battle.playGame();
		
		System.out.println(winner + " won.");
		
		new BattleshipServerSingle();
	}
	
}
