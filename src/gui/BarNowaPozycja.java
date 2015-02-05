package gui;

import java.awt.BorderLayout;
import java.sql.*;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BarNowaPozycja extends JFrame {

	private JPanel contentPane;
	Connection connection = null;
	
	/**
	 * Launch the application.
	 */
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BarNowaPozycja frame = new BarNowaPozycja();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BarNowaPozycja() {
		connection = mySqlConnection.dbConnector();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 483, 417);
		contentPane = new JPanel();
		//contentPane.setBackground(new Color(96, 96, 128));
		contentPane.setBackground(new Color(16, 16, 32));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBarDodaj = new JLabel("BAR - Dodaj pozycje");
		lblBarDodaj.setForeground(new Color(96, 96, 128));
		lblBarDodaj.setFont(new Font("Dialog", Font.BOLD, 30));
		lblBarDodaj.setBounds(67, 46, 436, 76);
		contentPane.add(lblBarDodaj);
		
		JLabel nazwa_produktu = new JLabel("Nazwa produktu");
		nazwa_produktu.setForeground(Color.YELLOW);
		nazwa_produktu.setFont(new Font("Dialog", Font.BOLD, 15));
		nazwa_produktu.setBounds(38, 149, 149, 45);
		contentPane.add(nazwa_produktu);
		
		JLabel lblCena = new JLabel("Cena (pln)");
		lblCena.setForeground(Color.YELLOW);
		lblCena.setFont(new Font("Dialog", Font.BOLD, 15));
		lblCena.setBounds(38, 227, 208, 45);
		contentPane.add(lblCena);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(245, 149, 195, 28);
		contentPane.add(textPane_1);
		
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setBounds(245, 237, 195, 28);
		contentPane.add(textPane_2);
		
		JButton btnDodajProdukt = new JButton("Dodaj produkt");
		btnDodajProdukt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nowy_produkt = "INSERT INTO Bar VALUES (?,?)";
				try {
					PreparedStatement dodaj_produkt = connection.prepareStatement(nowy_produkt);
					dodaj_produkt.setString(1, textPane_1.getText());
					dodaj_produkt.setInt(2, Integer.parseInt(textPane_2.getText()));
					dodaj_produkt.execute();
					JOptionPane.showMessageDialog(null, "Produkt dodany pomyslnie!");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnDodajProdukt.setBounds(291, 313, 149, 36);
		contentPane.add(btnDodajProdukt);
	}
}
