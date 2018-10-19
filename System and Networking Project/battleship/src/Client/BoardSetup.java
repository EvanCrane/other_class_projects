package Client;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Color;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.border.MatteBorder;


public class BoardSetup extends JFrame {
	/**
	 * Generated serial ID
	 */
	private static final long serialVersionUID = -6922472213296529796L;
		
//Global Variables
	
	//Game Object has access to socket and able to sent messages
	public Game game;
	//ArrayList of Point objects holds the indexs of selected buttons
	ArrayList<Point> placed;
	//Totals number of ships
	private final int SHIPS = 5;
	//Number of ships placed
	private int numSelected;
	//Content Pane
	private JPanel contentPane;
	//JToggleButton array contains all buttons placed on GUI 
	private JToggleButton[][] buttons;
	//Ready Button Used to send ready message to Server through the Game object
	private JButton btnReady;
	//Panel containing JToggleButtons
	private JPanel pnlSetup;
	//Label containing number of ships placed
	private JLabel lblShips;
	private int ships = 5;
	
	public String rawInput;
	public static String playerName;
	
	private ImageIcon bButton = new ImageIcon("src/image/brushedMetalTexture.jpg");
	private ImageIcon bBackground = new ImageIcon("src/image/radarDisplay.jpg");
	
	
//
	/**
	 * Launch the application.
	 */
	//for testing only
	/*public static void main(String[] args) {
		BoardSetup frame = new BoardSetup();
		frame.setVisible(true);
	}
	*/
	
	
	/**
	 * Create the frame.
	 */

	
	public BoardSetup(Game g)
	{
		game = g;
		game.setInfoSetup(this);
		
		System.out.println("Started player setup.");
		
		setAlwaysOnTop(true);
		
		buttons = new JToggleButton[5][5];
		placed = new ArrayList<Point>();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 460, 605);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Makes the windows not resizeable and automatically center them
		setResizable(false);
		setLocationRelativeTo(null); 
		//this will hide the title bar to discourage the use of the exit button
		setUndecorated(true);
		
		pnlSetup = new JPanel();
		pnlSetup.setBounds(55, 165, 350, 350);
		pnlSetup.setLayout(new GridLayout(5, 5, 0, 0));
		
		btnReady = new JButton("Ready");
		btnReady.setIcon(bButton);
		btnReady.setFocusable(false);
		btnReady.setHorizontalTextPosition(JButton.CENTER);
		btnReady.setVerticalTextPosition(JButton.CENTER);
		btnReady.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnReady.setFocusable(false);
		btnReady.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
//Ready button action listener		
		btnReady.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent a) 
			{
				//if there is a bad name input
				rawInput = txtNameEntry.getText();
				String badString = "%";
				String badString2 = "";
				if(rawInput.contains(badString)||rawInput.equals(badString2))
				{
					NameEntryErrorDialog errorDialog = new NameEntryErrorDialog(game);
					errorDialog.setVisible(true);
					
				}
				//if there is a good name input and board selection is ready
				else
				{
					playerName = rawInput; //To pass the raw input. playerName is the object that need to be passed to the server
					////////////////////////////////////////// SERVER COMS NEEDED
					String board = "";
					
					for(Point p : placed)
					{
						board += ("" + ((int)p.getX()) + "" + ((int)p.getY()));
					}
					
					System.out.println(playerName + " " + board);
					try {
						game.setInfo(playerName, board);
						game.prepGame();
					} 
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		
		btnReady.setEnabled(false);
		btnReady.setBounds(260, 527, 145, 50);
		contentPane.add(btnReady);
		
		//create buttons
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.fill = GridBagConstraints.BOTH;
		for(int row = 0; row < 5; row++){
			for(int col = 0; col < 5; col++){
				buttons[row][col] = new JToggleButton();
				buttons[row][col].addItemListener(itemListener);
				gbc.gridx = col;
				gbc.gridy = row;
				pnlSetup.add(buttons[row][col], gbc);
			}						
		}
		
		panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 204));
		panel_2.setForeground(new Color(255, 255, 255));
		panel_2.setBounds(105, 134, 264, 25);
		contentPane.add(panel_2);
		
		JLabel lblRemainingShips = new JLabel("Remaining Ships: ");
		panel_2.add(lblRemainingShips);
		lblRemainingShips.setForeground(new Color(102, 0, 0));
		
		lblShips = new JLabel("" + ships);
		panel_2.add(lblShips);
		lblShips.setForeground(new Color(102, 0, 0));
		contentPane.add(pnlSetup);
		
		txtNameEntry = new JTextField();
		txtNameEntry.setHorizontalAlignment(SwingConstants.CENTER);
		txtNameEntry.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtNameEntry.setColumns(10);
		txtNameEntry.setBounds(12, 52, 431, 35);
		contentPane.add(txtNameEntry);
		
		JButton btnBack = new JButton("Back");
		btnBack.setIcon(bButton);
		btnBack.setHorizontalTextPosition(JButton.CENTER);
		btnBack.setVerticalTextPosition(JButton.CENTER);
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnBack.setFocusable(false);
		btnBack.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBack.setBounds(55, 527, 145, 50);
		btnBack.setEnabled(true);
		contentPane.add(btnBack);
		
		panel = new JPanel();
		panel.setForeground(new Color(255, 255, 255));
		panel.setBackground(new Color(255, 255, 204));
		panel.setBounds(144, 19, 171, 28);
		contentPane.add(panel);
		
		JLabel lblEnterYourName = new JLabel("Enter your name");
		panel.add(lblEnterYourName);
		lblEnterYourName.setForeground(new Color(102, 0, 0));
		lblEnterYourName.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterYourName.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 204));
		panel_1.setBounds(55, 96, 350, 35);
		contentPane.add(panel_1);
		
		JLabel lblPlaceYourShips = new JLabel("Place your ships on the board");
		panel_1.add(lblPlaceYourShips);
		lblPlaceYourShips.setForeground(new Color(102, 0, 0));
		lblPlaceYourShips.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlaceYourShips.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon(BoardSetup.class.getResource("/image/midwayIsland.jpg")));
		lblBackground.setHorizontalAlignment(SwingConstants.CENTER);
		lblBackground.setBounds(6, 6, 448, 593);
		contentPane.add(lblBackground);
		
		btnBack.addActionListener(new ActionListener() 
		{
			//back button action listener
			public void actionPerformed(ActionEvent e) 
			{
				Title titleScreen = new Title(game);
				game.setTitle(titleScreen);
				titleScreen.setVisible(true);
				dispose();//goes back to the title screen				
			}
		});
		
		
		
		
		
		
	};
	
	//used by the item listener to determine the index of the pressed button
	private Point getPressedButton(Object source){
		
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				if(buttons[i][j] == source){
					return new Point(i,j);
				}
			}
		}
		return null;
	}
	
	//ItemListener 
	ItemListener itemListener = new ItemListener() {
		public void itemStateChanged(ItemEvent itemEvent) {
	        int state = itemEvent.getStateChange();
	        Object source = itemEvent.getSource();
	        Point p = getPressedButton(source);	//gets index of button in array as a Point
	        
	        //If the JToggleButton has not been selected yet(Its current state is selected after being clicked)
	        //Increment the number of ships placed by one, also add the index of the button to placed ArrayList
	        //else If (The ToggleButton was just deselected) Decrement the number of ships placed
	        if (state == ItemEvent.SELECTED) {
	          numSelected++;
	          placed.add(p);
	          ships--;
	          lblShips.setText("" + ships);
	        } else {
	          numSelected--;
	          placed.remove(p);
	          ships++;
	          lblShips.setText("" + ships);
	        }
	        if(numSelected < SHIPS){
	        	for(int row = 0; row < 5; row++){
	    			for(int col = 0; col < 5; col++){
	    				if(!buttons[row][col].isSelected()){
	    					buttons[row][col].setEnabled(true);
	    					
	    				}
	    			}
	        	}
	        	btnReady.setEnabled(false);
	        }
	        if(numSelected == SHIPS){
	        	//disable other buttons
	        	for(int row = 0; row < 5; row++){
	    			for(int col = 0; col < 5; col++){
	    				if(!buttons[row][col].isSelected()){
	    					buttons[row][col].setEnabled(false);
	    				}
	    			}
	        	}
	        	btnReady.setEnabled(true);
	        }
	      }	
	    };
	private JTextField txtNameEntry;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
}
