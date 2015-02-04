package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JPasswordField;



import java.awt.Font;
import java.sql.*;
import java.awt.Color;

public class gui1 {

	private JFrame frame;
	private JPasswordField passwordField;
	Connection connection = null; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui1 window = new gui1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public gui1() {
		initialize();
		connection = mySqlConnection.dbConnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("DialogInput", Font.PLAIN, 12));
		frame.getContentPane().setBackground(new Color(16, 16, 32));
		
		frame.setBounds(100, 100, 539, 340);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nazwa użytkownika");
		lblNewLabel.setForeground(new Color(255, 255, 0));
		lblNewLabel.setFont(new Font("Nimbus Mono L", Font.BOLD, 15));
		lblNewLabel.setBounds(47, 141, 175, 30);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblH = new JLabel("Hasło");
		lblH.setForeground(new Color(255, 255, 0));
		lblH.setFont(new Font("Nimbus Mono L", Font.BOLD, 15));
		lblH.setBounds(141, 183, 52, 30);
		frame.getContentPane().add(lblH);
		
		JTextPane textPane = new JTextPane();
		textPane.setForeground(new Color(192, 192, 192));
		textPane.setBackground(Color.WHITE);
		textPane.setBounds(240, 141, 206, 30);
		frame.getContentPane().add(textPane);
		
		passwordField = new JPasswordField();
		passwordField.setForeground(new Color(192, 192, 192));
		passwordField.setBackground(Color.WHITE);
		passwordField.setBounds(240, 187, 206, 30);
		frame.getContentPane().add(passwordField);
		
		JLabel lblNewLabel_1 = new JLabel("ZALOGUJ SIĘ");
		lblNewLabel_1.setForeground(new Color(96, 96, 128));
		lblNewLabel_1.setFont(new Font("Nimbus Mono L", Font.BOLD, 40));
		lblNewLabel_1.setBounds(141, 44, 279, 59);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnLogin = new JButton("Zaloguj");
		btnLogin.setFont(new Font("Arial", Font.BOLD, 13));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
						String query = "SELECT * FROM users WHERE username =? AND password =? ";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, textPane.getText());
						pst.setString(2, passwordField.getText());
						ResultSet rs = pst.executeQuery();
						int count = 0;
						while(rs.next()) 
						{
							count++;
							
						}
						if(count==1) 
						{
							JOptionPane.showMessageDialog(null, "Zalogowano pomyślnie");
							frame.dispose();
							MENU app = new MENU();
							app.setVisible(true);
							
							
						}
						else if(count > 1)
						{
							JOptionPane.showMessageDialog(null, "Użytkownik zdublowany");
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Błędna kombinacja użytkownik/hasło");
						}
						
						rs.close();
						pst.close();
					} catch(Exception ex) {
						JOptionPane.showMessageDialog(null, ex);
					}
			}
		});
		btnLogin.setBounds(313, 252, 133, 30);
		frame.getContentPane().add(btnLogin);
	}
}
