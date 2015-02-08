package gui;

import java.awt.BorderLayout;
import java.sql.*;
import java.text.ParseException;
import java.awt.EventQueue;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class BarHotelowy extends JFrame {

	private JPanel contentPane;
	Connection connection = null;
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BarHotelowy frame = new BarHotelowy();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	/**
	 * Create the frame.
	 */
	public BarHotelowy() {
		connection = mySqlConnection.dbConnector();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 485, 649);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(16, 16, 32));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		int wszystkie_produkty = 0;
		
		String ilosc_produktow = "SELECT COUNT(NazwaProduktu) AS Ilosc FROM Bar";
		try {
			PreparedStatement il = connection.prepareStatement(ilosc_produktow);
			ResultSet il_prod = il.executeQuery();
			if(il_prod.next())
			{
				wszystkie_produkty = il_prod.getInt("Ilosc");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String [] options = new String[wszystkie_produkty];
		String pobierz_produkty_z_baru = "SELECT NazwaProduktu FROM Bar";
		try {
			int index = 0;
			PreparedStatement produkty = connection.prepareStatement(pobierz_produkty_z_baru);
			ResultSet lista = produkty.executeQuery();
			while(lista.next()) 
			{
				options[index] = lista.getString("NazwaProduktu");
				index ++;			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JComboBox<String> comboBox = new JComboBox<>(options);
		comboBox.setBounds(250, 135, 180, 23);
		contentPane.add(comboBox);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("resources/obrazki/bar.jpg"));
		label.setBounds(33, 287, 412, 192);
		contentPane.add(label);
		
		JLabel lblBarDodaj = new JLabel("BAR - Dodaj zamowienie");
		lblBarDodaj.setForeground(new Color(96, 96, 128));
		lblBarDodaj.setFont(new Font("Dialog", Font.BOLD, 30));
		lblBarDodaj.setBounds(33, 38, 436, 76);
		contentPane.add(lblBarDodaj);
		
		JLabel wybierz_produkt = new JLabel("Wybierz produkt");
		wybierz_produkt.setForeground(Color.YELLOW);
		wybierz_produkt.setFont(new Font("Dialog", Font.BOLD, 15));
		wybierz_produkt.setBounds(38, 126, 151, 45);
		contentPane.add(wybierz_produkt);
		
		JLabel ilosc = new JLabel("Ilosc: ");
		ilosc.setForeground(Color.YELLOW);
		ilosc.setFont(new Font("Dialog", Font.BOLD, 15));
		ilosc.setBounds(124, 200, 49, 45);
		contentPane.add(ilosc);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(250, 210, 60, 23);
		contentPane.add(textPane);
		
		JButton btnNewButton = new JButton("Zloz zamowienie");
		btnNewButton.setBounds(270, 535, 162, 33);
		contentPane.add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dodaj_zamowienie = "INSERT INTO BarZamowienia VALUES (?, ?, ?, ?)";
				int cena_zamowienia = 0;
				if(textPane.getText() != null && isInteger(textPane.getText())) 
				{
					try {
						PreparedStatement zamow = connection.prepareStatement(dodaj_zamowienie);
						zamow.setString(1, (String) comboBox.getSelectedItem());
						PreparedStatement cena = connection.prepareStatement("SELECT Cena FROM Bar WHERE NazwaProduktu = ?");
						cena.setString(1, (String) comboBox.getSelectedItem());
						ResultSet rs = cena.executeQuery();
						if(rs.next()) 
						{
							cena_zamowienia = rs.getInt("Cena");
						}
						int ilosc = Integer.parseInt(textPane.getText());
						cena_zamowienia = cena_zamowienia * ilosc;
						
						zamow.setInt(2, cena_zamowienia);
						zamow.setInt(3, ilosc);
						
						Date aktualna_data = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String data = sdf.format(aktualna_data);
						
						zamow.setString(4, data);
						
						zamow.execute();
						JOptionPane.showMessageDialog(null, "Zamowienie dodane! Do zaplaty: " + cena_zamowienia + "pln.");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else 
				{
					JOptionPane.showMessageDialog(null, "Prosze poprawnie wypelnic dane.");
				}
			}
		});
		
		
	}
}
