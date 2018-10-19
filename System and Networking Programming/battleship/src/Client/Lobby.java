package Client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

/*
 * This it the client side Lobby window 
 * It displays the Names of the two clients that will player the game
 * The server will appoint a client that connects as player one or player two
 * That will determine which side the clients name will appear on
 * When two clients are connected a timer starts to countdown to game start
 * If both players click the ready button then the match will start immediately 
 * Once one of those two conditions are met the game moves to the match window.
 */





public class Lobby extends JFrame {

	private JPanel contentPane;

	private Game game;
	/**
	 * Launch the application.
	 * @throws Exception 
	 */
	/*
	//For testing only
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Lobby frame = new Lobby();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
	/*
	 * Might be a problem (Does disposing of a JFrame destroy a JFrame created within it?)
	*/
	//starts the battle window and starts the game
	public void startGame() throws Exception{
		Battleship battle = new Battleship(game);
		battle.setVisible(true);
		dispose();
	}
	
	//waits until a second player is in the lobby
	public void waitForOpponent(){
		
		
		
		
	}
	
	
	/**
	 * Create the frame.
	 */
	//For testing only

	public Lobby(Game g) {
		
		game = g;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 102, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 128));
		panel.setBounds(59, 55, 663, 281);
		contentPane.add(panel);
		panel.setLayout(null);

		
		JLabel lblP1Name = new JLabel("Waiting for opponent...");
		lblP1Name.setHorizontalAlignment(SwingConstants.CENTER);
		lblP1Name.setForeground(new Color(240, 255, 255));
		lblP1Name.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblP1Name.setBounds(0, 103, 654, 66);
		panel.add(lblP1Name);
	}
}
