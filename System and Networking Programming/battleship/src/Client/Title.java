package Client;


import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.border.BevelBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JLayeredPane;
import javax.swing.border.MatteBorder;


/*
 * This it the client side Title Screen 
 * It displays the title of the game and the buttons Connect and How to play
 * When Connect is clicked a dialog box appears and asks for a user name
 * the game then connects to the server and the client moves to the Lobby Screen.
 */



public class Title extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private final JPanel contentPanel = new JPanel();

	
	private JButton btnPlay;
	private JButton btnHowToPlay;
	private Game game;
	private ImageIcon bLogo = new ImageIcon("src/image/battleshipLogo.jpg");
	private ImageIcon bButton = new ImageIcon("src/image/brushedMetalTexture.jpg");
	private ImageIcon bBackground = new ImageIcon("src/image/battleShipSunset.jpg");
	
	/**
	 * Launch the application.
	 */
	/*
	//For testing only
	public static void main(String[] args) 
	{
		try 
		{
			Title frame = new Title();
			frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			frame.setVisible(true);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	 */

	
	
	public Title(Game g)
	{
		game = g;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		setBounds(100, 100, 600, 350);
		getContentPane().setLayout(new BorderLayout());	
		contentPanel.setBackground(new Color(0, 0, 51));
		
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		
		//Makes the windows not resizeable and automatically center them
				setResizable(false);
				setLocationRelativeTo(null);
			
		
		btnPlay = new JButton();	
		btnPlay.setIcon(bButton);
		btnPlay.setText("Play");
		btnPlay.setHorizontalTextPosition(JButton.CENTER);
		btnPlay.setVerticalTextPosition(JButton.CENTER);
		btnPlay.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnPlay.setFocusable(false);
		btnPlay.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnPlay.setBounds(105, 239, 150, 50);
		btnPlay.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Client clicked play");
				
				game.setup();
			}
		});
		
		contentPanel.add(btnPlay);		
		btnHowToPlay = new JButton();
		btnHowToPlay.setIcon(bButton);
		btnHowToPlay.setText("How To Play");
		btnHowToPlay.setHorizontalTextPosition(JButton.CENTER);
		btnHowToPlay.setVerticalTextPosition(JButton.CENTER);
		btnHowToPlay.addActionListener(new ActionListener() 
		//How to play button action listener
		{
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println("Client clicked instructions.");
				HowToPlay howTo = new HowToPlay();
				
				howTo.setVisible(true);
				
				
			}
		});
		
		btnHowToPlay.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnHowToPlay.setFocusable(false);
		btnHowToPlay.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnHowToPlay.setBounds(346, 239, 150, 50);
		contentPanel.add(btnHowToPlay);
		
		JLabel lblAuthor = new JLabel("Created by Charles Faber, Evan Crane, and Michael New");
		lblAuthor.setForeground(new Color(255, 255, 255));
		lblAuthor.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblAuthor.setHorizontalAlignment(SwingConstants.CENTER);
		lblAuthor.setBounds(76, 175, 445, 22);
		contentPanel.add(lblAuthor);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setForeground(new Color(204, 0, 0));
		layeredPane.setBorder(new MatteBorder(10, 10, 10, 10, (Color) new Color(153, 153, 153)));
		layeredPane.setBackground(new Color(204, 0, 0));
		layeredPane.setBounds(73, 24, 453, 115);
		contentPanel.add(layeredPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(204, 0, 0));
		panel.setBounds(6, 7, 441, 100);
		layeredPane.add(panel);
		
		JLabel lblBattleship = new JLabel(bLogo);
		lblBattleship.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblBattleship);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 0, 51));
		panel_1.setBounds(76, 171, 445, 32);
		contentPanel.add(panel_1);
		
		JLabel lblBackground = new JLabel("New label");
		lblBackground.setIcon(new ImageIcon(Title.class.getResource("/image/navyCamo.png")));
		lblBackground.setBounds(0, 0, 600, 328);
		contentPanel.add(lblBackground);
	}


	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
