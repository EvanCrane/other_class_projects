package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class GameOver extends JFrame 
{
	private JPanel contentPane;
	private JTextField txtGameOver;
	private String serverIp;

	/**
	 * Create the frame.
	 */
	public GameOver(int win, String ip) 
	{
		//Save the ip to start a new client
		serverIp = ip;
		
		setTitle("GAME OVER");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 599, 449);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Game over text, changes depending on win/loss
		txtGameOver = new JTextField();
		txtGameOver.setHorizontalAlignment(SwingConstants.CENTER);
		txtGameOver.setBackground(SystemColor.control);
		if(win == 1)
			txtGameOver.setText("VICTORY");
		else
			txtGameOver.setText("DEFEAT");
		txtGameOver.setFont(new Font("Impact", Font.BOLD, 45));
		txtGameOver.setBounds(10, 11, 563, 83);
		contentPane.add(txtGameOver);
		txtGameOver.setColumns(10);
		
		//Return to title button
		JButton btnReturnToTitle = new JButton("Return to Title");
		btnReturnToTitle.addActionListener(new ActionListener() 
		{
			//Return to title action listener			
			public void actionPerformed(ActionEvent arg0) 			
			{
				//Once this selection is pressed reopen the title screen and close all other windows
				try {
					reset();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnReturnToTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnReturnToTitle.setBounds(219, 342, 149, 39);
		contentPane.add(btnReturnToTitle);
		
		//Image label
		JLabel lblNewLabel = new JLabel("New label");
		if(win == 1)
			lblNewLabel.setIcon(new ImageIcon(GameOver.class.getResource("/image/Win.jpg")));
		else
			lblNewLabel.setIcon(new ImageIcon(GameOver.class.getResource("/image/Loss.jpg")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 93, 563, 306);
		contentPane.add(lblNewLabel);
	}
	
	//Starts client over
	public void reset() throws Exception
	{
		new Game(serverIp);
		
		dispose();
	}
}
