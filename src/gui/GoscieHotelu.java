package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
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

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GoscieHotelu extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;

	public static int id_goscia;
	
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
	
	public void odswiezTabele() 
	{
		String goscieKwerenda = "Select Goscie.IDGoscia, Goscie.Imie, Goscie.Nazwisko, Goscie.nrPokoju, Goscie.Adres, Pokoje.DataPrzyjazdu, Pokoje.DataOdjazdu FROM Goscie INNER JOIN Pokoje ON Goscie.IDGoscia = Pokoje.IDGoscia WHERE Goscie.czyGosc = true";
		PreparedStatement psgoscie;
		try {
			psgoscie = connection.prepareStatement(goscieKwerenda);
			ResultSet rsgoscie = psgoscie.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rsgoscie));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public boolean brakWyboru(JTable table, String akcja) {
		ListSelectionModel listSelectionModel = table.getSelectionModel();
		if(listSelectionModel.isSelectionEmpty()) {
			JOptionPane.showMessageDialog(null, "Zaznacz goscia, ktorego chcesz "+akcja, "Error", JOptionPane.ERROR_MESSAGE);
			return true;
		}
		return false;
	}
	
	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public GoscieHotelu() throws SQLException {
		connection = mySqlConnection.dbConnector();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1055, 483);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(16, 16, 32));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nasi goscie");
		lblNewLabel.setForeground(new Color(96, 96, 128));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 25));
		lblNewLabel.setBounds(338, 28, 236, 46);
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
		
		JButton btnUsunGoscia = new JButton("Usun goscia");
		btnUsunGoscia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				int ostrzezenie = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz usunac goscia?", "Usun", JOptionPane.YES_NO_OPTION);
				
				
				
				if(ostrzezenie == 0 && !brakWyboru(table, "usunac!") ) 		
				{
					int current_row = table.getSelectedRow();
					Object x = table.getValueAt(current_row, 0);
					
					int IDGoscia = Integer.parseInt(x.toString());
					
					String query = "DELETE FROM Goscie WHERE IDGoscia=?";
					PreparedStatement pst;
					try {
						pst = connection.prepareStatement(query);
						pst.setInt(1, IDGoscia);
						pst.execute();
						odswiezTabele();
						JOptionPane.showMessageDialog(null, "Gosc "+IDGoscia+" usuniety!");
						
						String usun_gosci_z_pokoju = "UPDATE Pokoje SET IDGoscia=NULL, DataPrzyjazdu=NULL, DataOdjazdu=NULL WHERE IDGoscia=?";
						PreparedStatement UsunGosciZPokoju = connection.prepareStatement(usun_gosci_z_pokoju);
						UsunGosciZPokoju.setInt(1, IDGoscia);
						UsunGosciZPokoju.executeUpdate();
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnUsunGoscia.setBounds(876, 98, 142, 37);
		contentPane.add(btnUsunGoscia);
		
		JButton btnEdytujGoscia = new JButton("Edytuj goscia");
		btnEdytujGoscia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!brakWyboru(table, "edytowac!"))
				{
					int current_row = table.getSelectedRow();
					Object x = table.getValueAt(current_row, 0);		
					id_goscia = Integer.parseInt(x.toString());
					FormularzEdycji formularz = new FormularzEdycji();
					formularz.setVisible(true);
				}
				
			}
		});
		btnEdytujGoscia.setBounds(876, 158, 142, 37);
		contentPane.add(btnEdytujGoscia);
		
		
	}
}
