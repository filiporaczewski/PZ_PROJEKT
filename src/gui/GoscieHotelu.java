package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTable;
import javax.swing.JScrollPane;

import net.proteanit.sql.DbUtils;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Color;

public class GoscieHotelu extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GoscieHotelu frame = new GoscieHotelu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection connection = null;

	public Boolean AktualniGoscie(String ad, String room_occupied_from, String room_occupied_to)
	{
		try 
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Date rof = sdf.parse(room_occupied_from);
			Date rot = sdf.parse(room_occupied_to);
			Date aktualna_data = sdf.parse(ad);
			
			if(aktualna_data.compareTo(rof) >= 0 && aktualna_data.compareTo(rot) <= 0)
			{
				return true;
			}
			else 
			{
				return false;
			}
			
		}catch(ParseException ex) 
		{
			ex.printStackTrace();
			return false;
		}	
	}
	
	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public GoscieHotelu() throws SQLException {
		connection = mySqlConnection.dbConnector();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 890, 469);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nasi goscie");
		lblNewLabel.setForeground(new Color(255, 215, 0));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 25));
		lblNewLabel.setBounds(349, 12, 236, 46);
		contentPane.add(lblNewLabel);
		
		String query = "SELECT * FROM Goscie";
		PreparedStatement pst = connection.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
				
				while(rs.next()) 
				{
					
					PreparedStatement pst2 = connection.prepareStatement("SELECT DataPrzyjazdu, DataOdjazdu FROM Pokoje WHERE IDGoscia=?");
					
					int id_goscia = rs.getInt("IDGoscia");
					pst2.setInt(1, id_goscia);
					ResultSet rs2 = pst2.executeQuery();
					
					if(rs2.next()) 
					{
						String data_od = rs2.getString("DataPrzyjazdu");
						String data_do = rs2.getString("DataOdjazdu");
						
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date ad = new Date();
						String aktualna_data = dateFormat.format(ad);
						
						if(AktualniGoscie(aktualna_data, data_od, data_do))
						{
							String czy_gosc = "UPDATE Goscie SET czyGosc = true WHERE idGoscia=?";
							PreparedStatement psczygosc = connection.prepareStatement(czy_gosc);
							psczygosc.setInt(1, id_goscia);
							psczygosc.executeUpdate();
						}
						else 
						{
							String nie_gosc = "UPDATE Goscie SET czyGosc = false WHERE idGoscia=?";
							PreparedStatement niepsczygosc = connection.prepareStatement(nie_gosc);
							niepsczygosc.setInt(1, id_goscia);
							niepsczygosc.executeUpdate();
						}
					}
					
					
				}
		
		String goscieKwerenda = "Select Goscie.IDGoscia, Goscie.Imie, Goscie.Nazwisko, Goscie.nrPokoju, Goscie.Adres, Pokoje.DataPrzyjazdu, Pokoje.DataOdjazdu FROM Goscie INNER JOIN Pokoje ON Goscie.IDGoscia = Pokoje.IDGoscia WHERE Goscie.czyGosc = true";
		PreparedStatement psgoscie = connection.prepareStatement(goscieKwerenda);
		ResultSet rsgoscie = psgoscie.executeQuery();
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 86, 813, 340);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(DbUtils.resultSetToTableModel(rsgoscie));
		
		
	}
}
