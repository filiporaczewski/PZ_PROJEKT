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

	public String imie_goscia;
	public String nazwisko_goscia;
	
	/**
	 * Create the frame.
	 * @throws ParseException 
	 */
	public ReservationForm() throws ParseException {
		connection = mySqlConnection.dbConnector();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 649, 506);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(16, 16, 32));
		contentPane.setForeground(new Color(34, 139, 34));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Podaj swoje dane");
		lblNewLabel.setForeground(new Color(96, 96, 128));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 32));
		lblNewLabel.setBounds(135, 25, 352, 84);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Imie");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_1.setForeground(new Color(255, 255, 0));
		lblNewLabel_1.setBounds(135, 154, 70, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Nazwisko");
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_2.setForeground(new Color(255, 255, 0));
		lblNewLabel_2.setBounds(135, 231, 92, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblA = new JLabel("Adres");
		lblA.setFont(new Font("Dialog", Font.BOLD, 15));
		lblA.setForeground(new Color(255, 255, 0));
		lblA.setBounds(135, 308, 70, 15);
		contentPane.add(lblA);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(268, 141, 230, 40);
		contentPane.add(textPane);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(268, 219, 230, 40);
		contentPane.add(textPane_1);
		
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setBounds(268, 302, 230, 40);
		contentPane.add(textPane_2);
		
		JButton btnNewButton = new JButton("GOTOWE");
		
				
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textPane.getText().length() > 0 && textPane_1.getText().length() > 0 && textPane_2.getText().length() > 0) {
				
					HotelApp.id_goscia += 1;
					String dane = "INSERT INTO Goscie (IDGoscia, Imie, Nazwisko, nrPokoju, Adres) VALUES  (?, ?, ?, ?, ?)";
					try {
						
						PreparedStatement pst = connection.prepareStatement(dane);
						
						pst.setInt(1, HotelApp.id_goscia);
						pst.setString(2, textPane.getText());
						pst.setString(3, textPane_1.getText());
						pst.setString(4, HotelApp.nrPok);
						pst.setString(5, textPane_2.getText());
						pst.executeUpdate();
						
						
						String wszystko_z_pokoi = "SELECT * FROM Pokoje WHERE IDGoscia IS NOT NULL";
						PreparedStatement pokoje_wszystko = connection.prepareStatement(wszystko_z_pokoi);
						ResultSet wszystkie_pokoje = pokoje_wszystko.executeQuery();
						
						if(wszystkie_pokoje.next())
						{
							String dodaj_nowy_wiersz = "INSERT INTO Pokoje (nrPokoju, RodzajPokoju, Cena, IDGoscia, DataPrzyjazdu, DataOdjazdu) VALUES (?, ?, ?, ?, ?, ?)";
							PreparedStatement dodaj_wiersz = connection.prepareStatement(dodaj_nowy_wiersz);
							dodaj_wiersz.setString(1, wszystkie_pokoje.getString(1));
							dodaj_wiersz.setInt(2, wszystkie_pokoje.getInt(2));
							dodaj_wiersz.setInt(3, wszystkie_pokoje.getInt(3));
							dodaj_wiersz.setInt(4, HotelApp.id_goscia);
							dodaj_wiersz.setString(5, HotelApp.data_przyjazdu);
							dodaj_wiersz.setString(6, HotelApp.data_wyjazdu);
							dodaj_wiersz.execute();
							
						} else 
						{
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
						}
								
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					imie_goscia = textPane.getText();
					nazwisko_goscia = textPane_1.getText();
					JOptionPane.showMessageDialog(null, "Pokoj nr. "+HotelApp.nrPok+" zarezerwowany przez "+imie_goscia+" "+nazwisko_goscia);
				} else 
				{
					JOptionPane.showMessageDialog(null, "Prosze uzupelnic wszystkie pola", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton.setBounds(313, 392, 185, 35);
		contentPane.add(btnNewButton);
		
		
	}
}
