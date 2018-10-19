package Client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NameEntryErrorDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField txtTheInputYou;
	private Game game;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		try {
			NameEntryErrorDialog dialog = new NameEntryErrorDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/

	/**
	 * Create the dialog.
	 */
	public NameEntryErrorDialog(Game g) 
	{
		game = g;
		setFont(new Font("Lucida Grande", Font.BOLD, 13));
		setTitle("INPUT ERROR");
		setType(Type.POPUP);
		setAlwaysOnTop(true);
		setBounds(100, 100, 628, 150);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		txtTheInputYou = new JTextField();
		txtTheInputYou.setBackground(Color.LIGHT_GRAY);
		txtTheInputYou.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		txtTheInputYou.setHorizontalAlignment(SwingConstants.CENTER);
		txtTheInputYou.setText("The input you entered is invalid. The input may not contain '%' or be blank. Please try again.");
		txtTheInputYou.setBounds(6, 14, 616, 61);
		contentPanel.add(txtTheInputYou);
		txtTheInputYou.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
//okay button action listener		
				//NameEntryDialog name = new NameEntryDialog();
				okButton.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						//name.revalidate();
						dispose();
						
					}
				});
				okButton.setFont(new Font("Tahoma", Font.BOLD, 12));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
