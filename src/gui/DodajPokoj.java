package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JTextPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.awt.Color;

public class DodajPokoj extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DodajPokoj frame = new DodajPokoj();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection connection = null;

	/**
	 * Create the frame.
	 */
	public DodajPokoj() {
		
		connection = mySqlConnection.dbConnector();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 683, 399);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(16, 16, 32));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDsada = new JLabel("Dodaj Pokoj");
		lblDsada.setForeground(new Color(96, 96, 128));
		lblDsada.setBackground(new Color(0, 0, 51));
		lblDsada.setFont(new Font("Dialog", Font.BOLD, 32));
		lblDsada.setBounds(221, 22, 255, 64);
		contentPane.add(lblDsada);
		
		JLabel lblNewLabel = new JLabel("Numer Pokoju");
		lblNewLabel.setForeground(new Color(255, 255, 0));
		lblNewLabel.setBounds(93, 106, 117, 37);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Rodzaj Pokoju (iluusobowy?)");
		lblNewLabel_1.setForeground(new Color(255, 255, 0));
		lblNewLabel_1.setBounds(92, 172, 232, 37);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Cena");
		lblNewLabel_2.setForeground(new Color(255, 255, 0));
		lblNewLabel_2.setBounds(93, 249, 117, 22);
		contentPane.add(lblNewLabel_2);
		
		JButton btnDodajPokoj = new JButton("Dodaj Pokoj");
		
		btnDodajPokoj.setBounds(235, 308, 157, 30);
		contentPane.add(btnDodajPokoj);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(319, 106, 192, 38);
		contentPane.add(textPane);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(319, 172, 192, 37);
		contentPane.add(textPane_1);
		
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setBounds(319, 234, 192, 37);
		contentPane.add(textPane_2);
		
		btnDodajPokoj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query = "INSERT INTO Pokoje (nrPokoju, RodzajPokoju, Cena) VALUES (?, ?, ?)";
				try {
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textPane.getText());
					pst.setInt(2, Integer.parseInt(textPane_1.getText()));
					pst.setInt(3, Integer.parseInt(textPane_2.getText()));
					pst.execute();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
}
