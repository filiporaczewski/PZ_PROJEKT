package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.awt.Color;

public class ReservationForm extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReservationForm frame = new ReservationForm();
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
	 * @throws ParseException 
	 */
	public ReservationForm() throws ParseException {
		connection = mySqlConnection.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 606, 488);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 51));
		contentPane.setForeground(new Color(34, 139, 34));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Podaj swoje dane");
		lblNewLabel.setForeground(new Color(255, 215, 0));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 32));
		lblNewLabel.setBounds(135, 25, 352, 84);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Imie");
		lblNewLabel_1.setForeground(new Color(34, 139, 34));
		lblNewLabel_1.setBounds(87, 154, 70, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Nazwisko");
		lblNewLabel_2.setForeground(new Color(34, 139, 34));
		lblNewLabel_2.setBounds(87, 230, 70, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblA = new JLabel("Adres");
		lblA.setForeground(new Color(34, 139, 34));
		lblA.setBounds(87, 321, 70, 15);
		contentPane.add(lblA);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(268, 141, 219, 35);
		contentPane.add(textPane);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(268, 210, 219, 35);
		contentPane.add(textPane_1);
		
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setBounds(270, 301, 221, 35);
		contentPane.add(textPane_2);
		
		JButton btnNewButton = new JButton("GOTOWE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HotelApp.id_goscia += 1;
				JOptionPane.showMessageDialog(null, HotelApp.id_goscia);
				JOptionPane.showMessageDialog(null, HotelApp.nrPok);
				String dane = "INSERT INTO Goscie VALUES (?, ?, ?, ?, ?)";
				try {
					
					PreparedStatement pst = connection.prepareStatement(dane);
					
					pst.setInt(1, HotelApp.id_goscia);
					pst.setString(2, textPane.getText());
					pst.setString(3, textPane_1.getText());
					pst.setString(4, HotelApp.nrPok);
					pst.setString(5, textPane_2.getText());
					pst.executeUpdate();
					
					String uzupelnienie_tabeli_pokoje = "UPDATE Pokoje SET IDGoscia = ?, DataPrzyjazdu = ?, DataOdjazdu = ? WHERE nrPokoju = ?";
					try {
						PreparedStatement pst2 = connection.prepareStatement(uzupelnienie_tabeli_pokoje);
						pst2.setInt(1, HotelApp.id_goscia);
						pst2.setString(2, HotelApp.data_przyjazdu);
						pst2.setString(3, HotelApp.data_wyjazdu);
						pst2.setString(4, HotelApp.nrPok);
						pst2.executeUpdate();
						
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
				JOptionPane.showMessageDialog(null, "Reservation made");
				 
			}
		});
		btnNewButton.setBounds(197, 393, 185, 35);
		contentPane.add(btnNewButton);
		
		
	}
}
