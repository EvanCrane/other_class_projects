package Client;


import java.awt.EventQueue;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.*;

import java.awt.Canvas;

public class ConnectingDialog extends JDialog
	{

	private ImageIcon loadingGif = new ImageIcon("src/image/targetLoading.gif");
	private Game game;
	
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectingDialog dialog = new ConnectingDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
	/**
	 * Create the dialog.
	 */
	public ConnectingDialog(Game g) 
	{
		game = g;
		setUndecorated(true);
		setAlwaysOnTop(true);
		getContentPane().setBackground(Color.BLACK);
		setBounds(100, 100, 800, 500);
		getContentPane().setLayout(null);
		
		JLabel lblLoadingGif = new JLabel(loadingGif);
		lblLoadingGif.setBounds(329, 183, 142, 133);
		getContentPane().add(lblLoadingGif);
		
		JLabel lblConnecting = new JLabel("CONNECTING TO OTHER PLAYER...");
		lblConnecting.setBounds(10, 60, 780, 50);
		lblConnecting.setForeground(Color.RED);
		lblConnecting.setBackground(Color.WHITE);
		lblConnecting.setFont(new Font("DIN Condensed", Font.BOLD | Font.ITALIC, 40));
		lblConnecting.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblConnecting);
		
		
		//Makes the windows not resizeable and automatically center them
		setResizable(false);
		setLocationRelativeTo(null); 
		//this will hide the title bar to discourage the use of the exit button
		//setUndecorated(true);
		
		

	}
}
