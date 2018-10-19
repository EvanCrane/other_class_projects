package Client;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.Color;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JToggleButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


/*
 * This is the client match window
 * here we have two grids of JToggleButtons
 * at the start of a match each player will choose 4 buttons on the right side(The left side will be disabled until both sides finish)
 * The each player's board will be on the right while the opponent's is on the left
 * When both players have placed their ships the game begins
 * The server will notify which client goes first
 * they choose a single button on the grid and click fire
 * when fire is clicked a message is sent to the server containing the space selected
 * The server checks if the opponent has a ship at that location and returns a "HIT" or "MISS" message
 * The icon on the button chosen then changes to match a hit or miss 
 * Then the other player gets his turn
 * continue until the server issues a "GAMEOVER" message
 * After a Gameover return to lobby
 */

public class Battleship extends JFrame{

	private static final long serialVersionUID = 7499356178513135796L;
	//Global Variables
	private Game game; //Game Object
	private ArrayList<Point> attacked; //Holds the points that are attacked(Button positions)
	private JToggleButton[][] pButtons; //holds player's buttons(The one's they can click, not their board)
	private JToggleButton[][] oButtons; //holds opponent's buttons(Shows player's ships player cannot interact)
	private JButton btnFire; 	//Fire button used to attack and end turn
	private ImageIcon water;	//Default button image
	private ImageIcon waterSelected; //Image when button is selected
	private ImageIcon waterHit;
	private ImageIcon waterMiss;
	private Point target;	//current target (Point button point to attack when Fire is clicked)
	private boolean ready = false; //Boolean that is set true when a target is selected
	
	private String opponentName;
	private int opponentShips = 0;
	private String playerName;
	private int playerShips = 0;
	private JLabel lblOpponentShips;
	private JLabel lblPlayerShips;
	private JLabel lblTurns; 
	private JLabel lblPlayerName; 
	private JLabel lblOpponentName;

	//Constructor creates GUI
	public Battleship(Game g) throws Exception {
		//Initialize Game object
		game = g;
		//Button Icons images
		try{
			getContentPane().setBackground(new Color(238, 232, 170));
			water = new ImageIcon("water.png");
			waterSelected = new ImageIcon("water2.png");
			waterHit = new ImageIcon("waterHit.png");
			waterMiss = new ImageIcon("waterMiss.png");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		//Setup GUI

		initialize();

	}
	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	//Handles the GUI creation
	private void initialize() throws IOException {
		
		attacked = new ArrayList<Point>();

		pButtons = new JToggleButton[5][5];
		oButtons = new JToggleButton[5][5];
		
		setBackground(new Color(0,0,51));
		setResizable(false);
		setTitle("BattleShip");
		setBounds(100, 100, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);		
		
		JPanel pnlLeft = new JPanel();
		pnlLeft.setBackground(new Color(189, 183, 107));
		pnlLeft.setBorder(null);
		pnlLeft.setForeground(new Color(0,0,51));
		pnlLeft.setBounds(0, 0, 393, 500);
		pnlLeft.setLayout(null);
		
		JPanel pnlOpponentBoard = new JPanel();
		pnlOpponentBoard.setBorder(null);
		pnlOpponentBoard.setBackground(new Color(238, 232, 170));
		pnlOpponentBoard.setMinimumSize(new Dimension(70, 70));
		pnlOpponentBoard.setMaximumSize(new Dimension(70, 70));
		pnlOpponentBoard.setBounds(15, 80, 350, 350);
		GridBagLayout gbl_pnlOpponentBoard = new GridBagLayout();
		gbl_pnlOpponentBoard.columnWidths = new int[] {70, 70, 70, 70, 70, 0};
		gbl_pnlOpponentBoard.rowHeights = new int[]{70, 70, 70, 70, 70, 0};
		gbl_pnlOpponentBoard.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlOpponentBoard.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlOpponentBoard.setLayout(gbl_pnlOpponentBoard);
		
		lblOpponentShips = new JLabel("" + opponentShips);
		lblOpponentShips.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblOpponentShips.setHorizontalAlignment(JLabel.CENTER);
		lblOpponentShips.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		lblOpponentShips.setBounds(273, 26, 92, 35);

		lblOpponentShips.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblOpponentShips.setHorizontalAlignment(JLabel.CENTER);
		lblOpponentShips.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		lblOpponentShips.setBounds(273, 26, 92, 35);
		
		//Left side buttons(Can Interact)
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.fill = GridBagConstraints.BOTH;
		for(int row = 0; row < 5; row++){
			for(int col = 0; col < 5; col++){
				pButtons[row][col] = new JToggleButton();
				pButtons[row][col].setBorder(null);
				pButtons[row][col].setIcon(water);
				pButtons[row][col].setSelectedIcon(waterSelected);
				pButtons[row][col].setBackground(Color.BLACK);
				pButtons[row][col].setFocusable(false);
				pButtons[row][col].putClientProperty("xIndex", new Integer(col));
				pButtons[row][col].putClientProperty("yIndex", new Integer(row));
				pButtons[row][col].addItemListener(itemListener);
				gbc.gridx = col;
				gbc.gridy = row;
				pnlOpponentBoard.add(pButtons[row][col], gbc);
			}						
		}
		
		JPanel pnlRight = new JPanel();
		pnlRight.setLayout(null);
		pnlRight.setForeground(Color.WHITE);
		pnlRight.setBorder(null);
		pnlRight.setBackground(new Color(189, 183, 107));
		pnlRight.setBounds(396, 0, 428, 500);
		
		
		JPanel pnlPlayerBoard = new JPanel();
		pnlPlayerBoard.setBorder(null);
		pnlPlayerBoard.setMinimumSize(new Dimension(70, 70));
		pnlPlayerBoard.setMaximumSize(new Dimension(70, 70));
		pnlPlayerBoard.setBackground(new Color(238, 232, 170));
		pnlPlayerBoard.setBounds(27, 80, 350, 350);
		GridBagLayout gbl_pnlPlayerBoard = new GridBagLayout();
		gbl_pnlPlayerBoard.columnWidths = new int[]{70, 70, 70, 70, 70, 0};
		gbl_pnlPlayerBoard.rowHeights = new int[]{70, 70, 70, 70, 70, 0};
		gbl_pnlPlayerBoard.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlPlayerBoard.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlPlayerBoard.setLayout(gbl_pnlPlayerBoard);
		
		lblPlayerShips = new JLabel("" + playerShips);
		lblPlayerShips.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPlayerShips.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		lblPlayerShips.setBounds(285, 26, 92, 35);
		lblPlayerShips.setHorizontalAlignment(JLabel.CENTER);
		
		lblTurns = new JLabel("Waiting for opponent to play...");
		lblTurns.setHorizontalAlignment(SwingConstants.CENTER);
		lblTurns.setForeground(Color.BLACK);
		lblTurns.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTurns.setBounds(10, 511, 774, 49);
			
		//right side buttons(Does not interact)(Shows player's board)(Changes Icon to reflect hit or miss)
		for(int row = 0; row < 5; row++){
			for(int col = 0; col < 5; col++){
				oButtons[row][col] = new JToggleButton();
				oButtons[row][col].setBorder(null);
				oButtons[row][col].setIcon(water);
				oButtons[row][col].setBackground(Color.BLACK);
				oButtons[row][col].setFocusable(false);
				oButtons[row][col].setEnabled(false);
				oButtons[row][col].setDisabledIcon(waterSelected);
				gbc.gridx = col;
				gbc.gridy = row;
				pnlPlayerBoard.add(oButtons[row][col], gbc);
			}						
		}
		pnlLeft.add(pnlOpponentBoard);
		pnlLeft.add(lblOpponentShips);
		
		pnlRight.add(pnlPlayerBoard);
		pnlRight.add(lblPlayerShips);

		getContentPane().add(pnlLeft);
		
		btnFire = new JButton("Fire");
		btnFire.setBounds(129, 441, 115, 45);
		pnlLeft.add(btnFire);
		btnFire.setForeground(Color.WHITE);
		btnFire.setFocusable(false);
		btnFire.setBackground(Color.RED);
		btnFire.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnFire.setFont(new Font("Century", Font.PLAIN, 18));
		btnFire.setEnabled(false);
		
		lblPlayerName = new JLabel("Player Name");
		lblPlayerName.setBounds(15, 26, 153, 35);
		pnlLeft.add(lblPlayerName);
		lblPlayerName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPlayerName.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayerName.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		btnFire.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					String str = "" + (int)target.getX() + "" + (int)target.getY();
					//send target to attack
					try {
						game.getAttack(str);
						
						btnFire.setEnabled(false);
						btnFire.setBackground(Color.LIGHT_GRAY);
						lblTurns.setForeground(Color.BLACK);
						lblTurns.setText("Waiting for opponent to play...");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						System.out.println("Error");
					}
			}
			
		});
		getContentPane().add(pnlRight);
		
		lblOpponentName = new JLabel("Opponent Name");
		lblOpponentName.setBounds(27, 26, 153, 35);
		pnlRight.add(lblOpponentName);
		lblOpponentName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblOpponentName.setHorizontalAlignment(SwingConstants.CENTER);
		lblOpponentName.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(lblTurns);
		
		setLocationRelativeTo(null);
	}

	
	//used by the item listener to determine the index of the pressed button
		public Point getPressedButton(Object source){
			
			for(int i = 0; i < 5; i++){
				for(int j = 0; j < 5; j++){
					if(pButtons[i][j] == source){
						return new Point(i,j);
					}
				}
			}
			return null;
		}

	ItemListener itemListener = new ItemListener() {
	      public void itemStateChanged(ItemEvent itemEvent) {
			 int state = itemEvent.getStateChange();
		     Object source = itemEvent.getSource();
		     Point p = getPressedButton(source);
		     if (state == ItemEvent.SELECTED){
		    	 target = p;
		    	 //enable fire
		    	 //btnFire.setEnabled(true);
		    	 //btnFire.updateUI();
		    		 //disable all other buttons
		    	 for(int row = 0; row < 5; row++){
		    			for(int col = 0; col < 5; col++){
		    				if(!pButtons[row][col].isSelected() && !attacked.contains(target)){
		    					pButtons[row][col].setEnabled(false);
		    				}
		    			}
		        	}
		     }
		     else if((state == ItemEvent.DESELECTED)){
		    	 target = null;
		    	 //disable fire
		    	 //btnFire.setEnabled(false);
		    	 //btnFire.updateUI();
		    	 //enable all other buttons (Except those already targeted)
		    	 for(int row = 0; row < 5; row++){
		    			for(int col = 0; col < 5; col++){
		    				if(!attacked.contains(new Point(row, col))){
		    					pButtons[row][col].setEnabled(true);
		    				}
		    			}
		        	}
		     }  
		}
	};

	public void updateOppenentResult(String str, int bool)
	{
		int xIndex = Integer.parseInt("" + str.charAt(0));
		int yIndex = Integer.parseInt("" + str.charAt(1));

		if(bool == 0)
			oButtons[xIndex][yIndex].setDisabledIcon(waterMiss);
		else
		{
			oButtons[xIndex][yIndex].setDisabledIcon(waterHit);
			playerShips++;
			lblPlayerShips.setText("" + playerShips);
		}
		repaint();
	}
	
	public void setPlayerNames(String player, String opponent)
	{
		lblPlayerName.setText(player);
		lblOpponentName.setText(opponent);
		setTitle("Battleship - " + player);
	}
	
	public void updatePlayerResult(String str, int bool)
	{

		
		int xIndex = Integer.parseInt("" + str.charAt(0));
		int yIndex = Integer.parseInt("" + str.charAt(1));
		
		attacked.add(new Point(xIndex, yIndex));
		pButtons[xIndex][yIndex].setSelected(false);
		pButtons[xIndex][yIndex].setEnabled(false);


		if(bool == 0)
			pButtons[xIndex][yIndex].setDisabledIcon(waterMiss);
		else
		{
			pButtons[xIndex][yIndex].setDisabledIcon(waterHit);
			opponentShips++;
			lblOpponentShips.setText("" + opponentShips);
		}
		
		repaint();
		
		for(int row = 0; row < 5; row++){
			for(int col = 0; col < 5; col++){
				if(!attacked.contains(new Point(row, col))){
					pButtons[row][col].setEnabled(true);
				}
			}
		}
		repaint();
	}


	public void updateButton(boolean b)
	{
		if(b)
		{
			btnFire.setBackground(Color.RED);
			btnFire.setEnabled(true);
		}
		else
		{
			btnFire.setBackground(Color.LIGHT_GRAY);
			btnFire.setEnabled(false);
		}
		lblTurns.setForeground(Color.RED);
		lblTurns.setText("It's your turn!");
		
		btnFire.updateUI();
		System.out.println("Updated button");
		
	}
}
