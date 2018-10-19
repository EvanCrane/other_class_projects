package Server;
/*
 * Battle class
 */

import java.net.*;
import java.io.*;
import java.util.*;

public class Battle
{
	//Arrays to store pair of client Sockets and I/O Streams
	private Socket[] clientSocks = new Socket[2];
	private DataInputStream[] inStream = new DataInputStream[2];
	private DataOutputStream[] outStream = new DataOutputStream[2];
	
	//Arrays to store pair of player names and boards
	private String[] playerName = new String[2];
	private String[] playerShips = new String[2];
	private String[][][] playerBoard = new String[2][5][5];	
	//Array to log player attacks - not really needed server-side
	private String[][] playerAttacks = new String[2][25];
	//Array to log number of ships remaining for each player
	private int[] numShips = new int[2];
	
	//I/O DataMessages for Server
	private ServerDataMessage outMsg = new ServerDataMessage();
	private ServerDataMessage inMsg = new ServerDataMessage();
	
	private int turnNumber = 0; //Count current turn numbers
	private int winner = -1; //Winner name storage
	
	//Index players at zero for array access
	private int playerONE = 0;
	private int playerTWO = 1;
	
	//Battle Constructor stores randomly player data from PlayerSetupThreads passed as parameters
	public Battle(PlayerSetupThread thread1, PlayerSetupThread thread2) throws IOException
	{	
		//Randomly choose first player
		if(this.coinFlip() == 1) 	
		{
			//First client to connect is assigned to be first player
			this.setPlayerData(thread1, thread2);
			System.out.println(thread1.getPlayerName() + " won the coin toss and will play first");
		}
		else
		{
			//Second client to connect is assigned to be first player
			this.setPlayerData(thread2, thread1);
			System.out.println(thread2.getPlayerName() + " won the coin toss and will play first");
		}
	}

	//Initialize player data
	public void setPlayerData(PlayerSetupThread p1, PlayerSetupThread p2) throws IOException
	{
		//Client Sockets
		clientSocks[playerONE] = p1.getClientSocket();
		clientSocks[playerTWO] = p2.getClientSocket();
		
		//Data Input Streams
		inStream[playerONE] = new DataInputStream(clientSocks[playerONE].getInputStream());
		inStream[playerTWO] = new DataInputStream(clientSocks[playerTWO].getInputStream());
		
		//Data Output Streams
		outStream[playerONE] = new DataOutputStream(clientSocks[playerONE].getOutputStream());
		outStream[playerTWO] = new DataOutputStream(clientSocks[playerTWO].getOutputStream());
		
		//Player Names
		playerName[playerONE] = p1.getPlayerName();
		playerName[playerTWO] = p2.getPlayerName();
		
		//Store the string of player ships
		playerShips[playerONE] = p1.getPlayerShips();
		playerShips[playerTWO] = p2.getPlayerShips();
		
		//Extract ship coordinates for player one
		for(String[][] board : playerBoard) {
	        for (String[] line : board) {
	            Arrays.fill(line, "0");
	        }
	    }
		
		int index = 0;
		int xIndex = 0;
		int yIndex = 0;
		String str = playerShips[playerONE];
		System.out.println(str);
		while(index < 10)
		{
			System.out.println(index);
			xIndex = Integer.parseInt("" + str.charAt(index));
			index++;
			yIndex = Integer.parseInt("" + str.charAt(index));
			index++;
			
			System.out.println(xIndex + " " + yIndex);
			
			playerBoard[playerONE][xIndex][yIndex] = "1";
		}
		
		//Extract ship coordinates for player two
		index = 0;
		str = playerShips[playerTWO];
		System.out.println(str);
		while(index < 10)
		{
			xIndex = Integer.parseInt("" + str.charAt(index));
			index++;
			yIndex = Integer.parseInt("" + str.charAt(index));
			index++;
			
			playerBoard[playerTWO][xIndex][yIndex] = "1";
		}
		
		//Both players start game with 5 ships
		Arrays.fill(numShips, 5);
	}
	
	//Randomly returns either integer of 1 or 2
	public int coinFlip()
	{
		Random rand = new Random();
		return 1 + rand.nextInt(2);
	}
	
	//Call method after initializing Battle object with player data, returns winner name String
	public String playGame() throws IOException
	{
		//Server sends a start game message to both clients 
		outMsg.setGameStartMsg(playerName[playerTWO]);
		outStream[playerONE].writeUTF(outMsg.toString());
		outMsg.setGameStartMsg(playerName[playerONE]);
		outStream[playerTWO].writeUTF(outMsg.toString());
		
		System.out.println("The game has started! ");
		
		//Play 25 turns = most possible rounds
		while(turnNumber < 25)
		{
			System.out.println("Turn #" + turnNumber);
			
			//Handle playerONE's turn
			this.playTurn(playerONE);
			//Check if move ended game, declare playerONE winner if so
			if(this.isGameOver(playerTWO))
			{
				winner = playerONE;
				outMsg.setWinnerMsg(1);
				outStream[playerONE].writeUTF(outMsg.toString());
				outMsg.setWinnerMsg(0);
				outStream[playerTWO].writeUTF(outMsg.toString());
				break;
			}
			
			//Handle playerTWO's turn
			this.playTurn(playerTWO);
			//check if move ended game, declare playerTWO winner if so
			if(this.isGameOver(playerONE))
			{
				
				winner = playerTWO;
				outMsg.setWinnerMsg(1);
				outStream[playerTWO].writeUTF(outMsg.toString());
				outMsg.setWinnerMsg(0);
				outStream[playerONE].writeUTF(outMsg.toString());
				break;
			}
			
			turnNumber++; //Count turn number
		}
		System.out.println("Game Over! " + winner + " won!");
		
		//When game ends, send winner message to both clients and return winner to GameThread
		return playerName[winner];
	}
	
	//Handle player turn, pass which player is attacking as parameter
	public void playTurn(int attacker) throws IOException
	{
	//Start turn
		System.out.println("Player " + playerName[attacker] + "'s turn. Waiting for coordinate...");
		
		//Notify player that his turn is beginning, send request turn message
		outMsg.setTurnMsg();
		outStream[attacker].writeUTF(outMsg.toString());
		
		//Wait for client response and read in his attack coordinate
		String receiveStr = inStream[attacker].readUTF();
		inMsg.toDataMessage(receiveStr);
		String attackCell = inMsg.getText(); 
		playerAttacks[attacker][turnNumber] = attackCell; //Store attack
		System.out.print(playerName[attacker] + " attacked (" + attackCell.charAt(0) + ", " + attackCell.charAt(1) + ")... ");
		
		int defender;
		//Determine which client is defending
		if(attacker == 0)
			defender = 1;
		else
			defender = 0;
		
		//If attack coordinate matches defender ship location, send hit message to both clients
		if(this.isHit(defender, attackCell))
		{
			System.out.println("Hit!");
			
			outMsg.setAttResultMsg(1, attackCell);
			outStream[attacker].writeUTF(outMsg.toString());
			outMsg.setDefResultMsg(1, attackCell);
			outStream[defender].writeUTF(outMsg.toString());
			
			numShips[defender]--; //Record defender ship loss
		}
		//Else send miss message to both clients
		else
		{
			System.out.println("Miss.");
			
			outMsg.setAttResultMsg(0, attackCell);
			outStream[attacker].writeUTF(outMsg.toString());
			outMsg.setDefResultMsg(0, attackCell);
			outStream[defender].writeUTF(outMsg.toString());
		}
	}
	
	//Determines if defender has a ship at attack cell
	//Defender identifier and attackCell integers passed as parameters, returns hit or miss boolean
	public boolean isHit(int defender, String attackCell)
	{
		System.out.println(attackCell);
		int xIndex = Integer.parseInt("" + attackCell.charAt(0));
		int yIndex = Integer.parseInt("" + attackCell.charAt(1));

		boolean isHit = false; //Assume miss
		//If the defender board string has a ship at the attack cell
		if(playerBoard[defender][xIndex][yIndex].charAt(0) == '1') 
			isHit = true; //Hit!
		
		return isHit;
	}
	
	//Checks to see if game is over by checking the ship number of the defender passed as a parameter
	public boolean isGameOver(int defender)
	{
		boolean gameOver = false;
		if(numShips[defender] == 0)
			gameOver = true;
		
		return gameOver;
	}
}
