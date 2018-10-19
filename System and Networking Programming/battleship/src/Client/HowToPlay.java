package Client;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import java.awt.Window;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JScrollBar;
import javax.swing.ImageIcon;

public class HowToPlay extends JFrame {

	private JPanel contentPane;
	private JTextField txtField1;
	private JTextField txtField2;
	private JTextField txtField3;
	private JTextField txtField4;
	private JTextField txtField5;
	private JTextField txtTheBattleMay;
	
	private Image backgroundImage;
	
	private ImageIcon bBackgroundSecondary = new ImageIcon("src/image/s.jpg");

	/**
	 * Launch the application.
	 */
	
	/*
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HowToPlay frame = new HowToPlay();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
	/**
	 * Create the frame.
	 */
	public HowToPlay() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle(" ");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Makes the windows not resizeable and automatically center them
				setResizable(false);
				setLocationRelativeTo(null);
		
		txtField1 = new JTextField();
		txtField1.setEditable(false);
		txtField1.setForeground(new Color(255, 255, 255));
		txtField1.setBackground(new Color(204, 0, 0));
		txtField1.setHorizontalAlignment(SwingConstants.CENTER);
		txtField1.setFont(new Font("Impact", Font.PLAIN, 37));
		txtField1.setText("BATTLESHIP");
		txtField1.setBounds(7, 11, 986, 40);
		contentPane.add(txtField1);
		txtField1.setColumns(10);
		
		JTextArea txtArea1 = new JTextArea();
		txtArea1.setBackground(SystemColor.info);
		txtArea1.setLineWrap(true);
		txtArea1.setText("The object of the game is to be the first person to sink all five of your opponents ships.");
		txtArea1.setBounds(10, 93, 980, 22);
		contentPane.add(txtArea1);
		
		txtField2 = new JTextField();
		txtField2.setEditable(false);
		txtField2.setFont(new Font("Lucida Console", Font.BOLD, 14));
		txtField2.setBackground(SystemColor.inactiveCaption);
		txtField2.setHorizontalAlignment(SwingConstants.CENTER);
		txtField2.setText("OBJECT OF THE GAME");
		txtField2.setBounds(7, 62, 986, 20);
		contentPane.add(txtField2);
		txtField2.setColumns(10);
		
		txtField3 = new JTextField();
		txtField3.setEditable(false);
		txtField3.setText("ENTER YOUR NAME");
		txtField3.setHorizontalAlignment(SwingConstants.CENTER);
		txtField3.setFont(new Font("Lucida Console", Font.BOLD, 14));
		txtField3.setColumns(10);
		txtField3.setBackground(SystemColor.inactiveCaption);
		txtField3.setBounds(7, 126, 986, 20);
		contentPane.add(txtField3);
		
		JTextArea txtArea3 = new JTextArea();
		txtArea3.setWrapStyleWord(true);
		txtArea3.setBackground(SystemColor.info);
		txtArea3.setText("After pressing start on the main title screen a dialog box will ask you to enter your name. Once you enter your name and press okay the game will then take you to a ship selection screen. ");
		txtArea3.setLineWrap(true);
		txtArea3.setBounds(10, 157, 980, 40);
		contentPane.add(txtArea3);
		
		txtField4 = new JTextField();
		txtField4.setEditable(false);
		txtField4.setText("PREPARE FOR BATTLE");
		txtField4.setHorizontalAlignment(SwingConstants.CENTER);
		txtField4.setFont(new Font("Lucida Console", Font.BOLD, 14));
		txtField4.setColumns(10);
		txtField4.setBackground(SystemColor.inactiveCaption);
		txtField4.setBounds(7, 208, 986, 20);
		contentPane.add(txtField4);
		
		JTextArea txtArea4 = new JTextArea();
		txtArea4.setWrapStyleWord(true);
		txtArea4.setBackground(SystemColor.info);
		txtArea4.setText("The ship selection screen will allow you to place your 5 ships in the 5x5 grid board. You may secretly place your fleet in any of the tiles. You may also deselect your choices in case you dont think your ships are in the  right place. Once you think your ships are in the most strategic positions press the ready button to connect with your opponent. You will be taken to a lobby screen with your opponent and when you both press ready the game will begin!");
		txtArea4.setLineWrap(true);
		txtArea4.setBounds(10, 239, 980, 55);
		contentPane.add(txtArea4);
		
		txtField5 = new JTextField();
		txtField5.setEditable(false);
		txtField5.setText("CALL YOUR SHOT AND FIRE");
		txtField5.setHorizontalAlignment(SwingConstants.CENTER);
		txtField5.setFont(new Font("Lucida Console", Font.BOLD, 14));
		txtField5.setColumns(10);
		txtField5.setBackground(SystemColor.inactiveCaption);
		txtField5.setBounds(7, 306, 986, 20);
		contentPane.add(txtField5);
		
		JTextArea txtArea5 = new JTextArea();
		txtArea5.setWrapStyleWord(true);
		txtArea5.setBackground(SystemColor.info);
		txtArea5.setText("In the game screen there are two boards. Your board and a radar board in which your opponents fleet is secretly located. There is also a text area that will show all moves in the game as they happen. During your turn select a tile on your radar board and press the fire button afterwards to confirm the shot being fired on that coodinate.\r\nITS A HIT!\r\nIf it's a hit the game will show in text that a hit was confirmed and the radar board will show a hit marker on tile confirming a ship was sunk. \r\nITS A MISS!\r\nIf it's a miss the game will show that there was a miss in text and by a miss marker on the radar board where you fired. \r\nINCOMING FIRE!\r\nIf an opponent hits or missed one of your ships the game will indicate in text and by a hit or miss marker on your board.");
		txtArea5.setLineWrap(true);
		txtArea5.setBounds(10, 338, 980, 150);
		contentPane.add(txtArea5);
		
		txtTheBattleMay = new JTextField();
		txtTheBattleMay.setEditable(false);
		txtTheBattleMay.setText("THE BATTLE MAY BE OVER BUT NOT THE WAR");
		txtTheBattleMay.setHorizontalAlignment(SwingConstants.CENTER);
		txtTheBattleMay.setFont(new Font("Lucida Console", Font.BOLD, 14));
		txtTheBattleMay.setColumns(10);
		txtTheBattleMay.setBackground(SystemColor.inactiveCaption);
		txtTheBattleMay.setBounds(7, 500, 986, 20);
		contentPane.add(txtTheBattleMay);
		
		JTextArea txtrAfterAPlayer = new JTextArea();
		txtrAfterAPlayer.setWrapStyleWord(true);
		txtrAfterAPlayer.setBackground(SystemColor.info);
		txtrAfterAPlayer.setText("After a player has all 5 of their ships sunk a winner will be declared. You will then be given the option  to return to the title screen or to  play again in a rematch.  ");
		txtrAfterAPlayer.setLineWrap(true);
		txtrAfterAPlayer.setBounds(10, 532, 980, 40);
		contentPane.add(txtrAfterAPlayer);
		
		
		
		JButton btnReturnToTitle = new JButton("Return to title\r\n");
		btnReturnToTitle.setIcon(new ImageIcon(HowToPlay.class.getResource("/image/brushedMetalTexture.jpg")));
		btnReturnToTitle.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnReturnToTitle.setHorizontalTextPosition(JButton.CENTER);
		btnReturnToTitle.setVerticalTextPosition(JButton.CENTER);
		btnReturnToTitle.addActionListener(new ActionListener() {
//Action listener for the return to title button			
			public void actionPerformed(ActionEvent arg0) 
			{
				dispose();
			}
		});
		btnReturnToTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnReturnToTitle.setBounds(425, 605, 150, 50);
		contentPane.add(btnReturnToTitle);
		
		
	}



	public void JPanelWithBackground(String fileName) throws IOException 
	{
		backgroundImage = ImageIO.read(new File("battleshipSillhoutte.jpg"));
	}

	public void paintComponent(Graphics g) 
	{
		super.paintComponents(g);

    // Draw the background image.
    g.drawImage(backgroundImage, 0, 0, this);
	}
}

