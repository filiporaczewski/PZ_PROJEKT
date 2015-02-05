package gui;

import java.awt.BorderLayout;
import java.sql.*;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextPane;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormularzEdycji extends JFrame {

	private JPanel contentPane;
	private String przyszli_czy_obecni;

	/**
	 * Launch the application.
	 */
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormularzEdycji frame = new FormularzEdycji("");
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
	public FormularzEdycji(String klasa) {
		przyszli_czy_obecni = klasa;
		connection = mySqlConnection.dbConnector();
		int id_goscia;
		
		if(przyszli_czy_obecni == "obecni")
		{
			id_goscia = GoscieHotelu.id_goscia;
		} else
		{
			id_goscia = PrzyszliGoscie.id_goscia;
		}
		
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 598, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(new Color(16, 16, 32));
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Imie");
		lblNewLabel.setForeground(Color.YELLOW);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel.setBounds(67, 189, 70, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nazwisko");
		lblNewLabel_1.setForeground(Color.YELLOW);
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_1.setBounds(67, 251, 153, 43);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Numer Pokoju");
		lblNewLabel_2.setForeground(Color.YELLOW);
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_2.setBounds(67, 326, 170, 43);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("ID Goscia");
		lblNewLabel_3.setForeground(Color.YELLOW);
		lblNewLabel_3.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_3.setBounds(67, 101, 114, 28);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Adres");
		lblNewLabel_4.setForeground(Color.YELLOW);
		lblNewLabel_4.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_4.setBounds(67, 414, 70, 15);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Formularz edycji goscia");
		lblNewLabel_5.setForeground(new Color(96, 96, 128));
		lblNewLabel_5.setFont(new Font("Dialog", Font.BOLD, 25));
		lblNewLabel_5.setBounds(100, 12, 437, 63);
		contentPane.add(lblNewLabel_5);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(277, 101, 227, 28);
		contentPane.add(textPane);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(277, 176, 227, 28);
		contentPane.add(textPane_1);
		
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setBounds(277, 251, 227, 28);
		contentPane.add(textPane_2);
		
		JTextPane textPane_3 = new JTextPane();
		textPane_3.setBounds(277, 326, 228, 28);
		contentPane.add(textPane_3);
		
		JTextPane textPane_4 = new JTextPane();
		textPane_4.setBounds(277, 401, 227, 28);
		contentPane.add(textPane_4);
		
		
		
		String dane_goscia = "SELECT IDGoscia, Imie, Nazwisko, NrPokoju, Adres FROM Goscie WHERE IDGoscia = ?";
		PreparedStatement pobierz_dane;
		try {
			pobierz_dane = connection.prepareStatement(dane_goscia);
			pobierz_dane.setInt(1, id_goscia);
			ResultSet dane = pobierz_dane.executeQuery();
			if(dane.next()) 
			{
				textPane.setText(Integer.toString(id_goscia));
				textPane_1.setText(dane.getString("Imie"));
				textPane_2.setText(dane.getString("Nazwisko"));
				textPane_3.setText(dane.getString("NrPokoju"));
				textPane_4.setText(dane.getString("Adres"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JButton btnEdytuj = new JButton("Edytuj");
		btnEdytuj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String edytuj_goscia = "UPDATE Goscie SET IDGoscia=?, Imie=?, Nazwisko=?, NrPokoju=?, Adres=? WHERE IDGoscia = ?";
				try {
					PreparedStatement edycja = connection.prepareStatement(edytuj_goscia);
					edycja.setInt(1, Integer.parseInt(textPane.getText()));
					edycja.setString(2, textPane_1.getText());
					edycja.setString(3, textPane_2.getText());
					edycja.setString(4, textPane_3.getText());
					edycja.setString(5, textPane_4.getText());
					edycja.setInt(6, id_goscia);
					edycja.executeUpdate();
					JOptionPane.showMessageDialog(null, "Gosc "+id_goscia+" edytowany pomyslnie!");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnEdytuj.setBounds(371, 478, 133, 36);
		contentPane.add(btnEdytuj);
	}
}
