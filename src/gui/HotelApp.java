package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTable;

import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

import javax.swing.*;

import net.proteanit.sql.DbUtils;

import com.toedter.calendar.JCalendar;
import com.toedter.components.JLocaleChooser;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.toedter.calendar.JDateChooser;

import java.awt.Color;

public class HotelApp extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	
	Connection connection = null;
	
	public static String nrPok;
	public static String data_przyjazdu;
	public static String data_wyjazdu;
	
	public static int id_goscia;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HotelApp frame = new HotelApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Boolean IsFreeInPeriod(String arival_date, String departure_date, String room_occupied_from, String room_occupied_to)
	{
		try 
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date ad = sdf.parse(arival_date);
			Date dd = sdf.parse(departure_date);
			Date rof = sdf.parse(room_occupied_from);
			Date rot = sdf.parse(room_occupied_to);
			
			if(ad.compareTo(rof) >= 0 && ad.compareTo(rot) <= 0)
			{
				return false;
			} else if(dd.compareTo(rof) >= 0 && dd.compareTo(rot) <= 0)
			{
				return false;
			} else if (ad.compareTo(rof) <= 0 && ad.compareTo(rot) <= 0 && dd.compareTo(rof) >= 0 && dd.compareTo(rot) >= 0)
			{
				return false;
			} else 
			{
				return true;
			}
			
		}catch(ParseException ex) 
		{
			ex.printStackTrace();
			return false;
		}	
	}
		



	
	
	public HotelApp() throws ParseException, SQLException {
		connection = mySqlConnection.dbConnector();
	
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 795, 589);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		
		
		scrollPane.setBounds(39, 223, 695, 196);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("REZERWACJA POKOJU");
		lblNewLabel.setForeground(new Color(255, 215, 0));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 28));
		lblNewLabel.setBounds(260, 36, 371, 39);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Sprawdz wolne pokoje");
		
		btnNewButton.setBounds(307, 177, 212, 25);
		contentPane.add(btnNewButton);
		
		JButton btnIdGoscia = new JButton("Dokonaj Rezerwacji");
		
		
		btnIdGoscia.setBounds(273, 458, 256, 54);
		contentPane.add(btnIdGoscia);
		
		JLabel lblNewLabel_1 = new JLabel("od");
		lblNewLabel_1.setForeground(new Color(34, 139, 34));
		lblNewLabel_1.setBounds(156, 112, 70, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("do");
		lblNewLabel_2.setForeground(new Color(34, 139, 34));
		lblNewLabel_2.setBounds(409, 112, 70, 15);
		contentPane.add(lblNewLabel_2);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setBounds(214, 104, 151, 23);
		contentPane.add(dateChooser);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setDateFormatString("yyyy-MM-dd");
		dateChooser_1.setBounds(473, 102, 201, 25);
		contentPane.add(dateChooser_1);
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					
					
					
					String query1 = "SELECT * FROM Pokoje WHERE IDGoscia IS NOT NULL";
					PreparedStatement pst1;
					try {
						pst1 = connection.prepareStatement(query1);
						ResultSet rs1 = pst1.executeQuery();
						while(rs1.next()) {
							String DataPrzyjazdu = rs1.getString("DataPrzyjazdu");
							String DataOdjazdu = rs1.getString("DataOdjazdu");
							String Pokoj = rs1.getString("nrPokoju");
							Date a_date = dateChooser.getDate();
							Date d_date = dateChooser_1.getDate();
							
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							
							data_przyjazdu = sdf.format(a_date);
							data_wyjazdu = sdf.format(d_date);					
							
							if(IsFreeInPeriod(data_przyjazdu, data_wyjazdu, DataPrzyjazdu, DataOdjazdu))
							{
								
								JOptionPane.showMessageDialog(null, "Pokoj "+ Pokoj + " wolny!");
								String query2 = "UPDATE Pokoje SET WolnyWTerminie = true WHERE nrPokoju = ?";
								PreparedStatement pst2 = connection.prepareStatement(query2);
								pst2.setString(1, Pokoj);
								pst2.executeUpdate();
							} else 
							{

								JOptionPane.showMessageDialog(null, "Pokoj "+ Pokoj + " zajety!");
								String queryfalse = "UPDATE Pokoje SET WolnyWTerminie = false WHERE nrPokoju = ?";
								PreparedStatement pstfalse = connection.prepareStatement(queryfalse);
								pstfalse.setString(1, Pokoj);
								pstfalse.executeUpdate();
								
							}
							
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					String queryupdate = "UPDATE Pokoje SET WolnyWTerminie = true WHERE IdGoscia IS NULL";
					PreparedStatement pstupdate;
					try {
						pstupdate = connection.prepareStatement(queryupdate);
						pstupdate.executeUpdate();
						
						Date a_date = dateChooser.getDate();
						Date d_date = dateChooser_1.getDate();
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						
						data_przyjazdu = sdf.format(a_date);
						data_wyjazdu = sdf.format(d_date);	
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					String query3 = "SELECT * FROM Pokoje WHERE WolnyWTerminie = true";
					PreparedStatement pst3;
					try {
						pst3 = connection.prepareStatement(query3);
						ResultSet rst3 = pst3.executeQuery();
						
						table.setModel(DbUtils.resultSetToTableModel(rst3));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
//					Object x = table.getValueAt(3, 0);
//					JOptionPane.showMessageDialog(null, x);
					
					
					
				
			}
		});
		
		btnIdGoscia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PreparedStatement pstat;
				try {
					pstat = connection.prepareStatement("SELECT MAX(IDGoscia) FROM Goscie");
					ResultSet rset = pstat.executeQuery();
					int id;
					try {
						if(rset.next()) {
							id = rset.getInt(1);
							id_goscia = id;
						} else {
							id_goscia = 0;
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				
				
				int current_row = table.getSelectedRow();
				Object x = table.getValueAt(current_row, 0);
				nrPok = x.toString();
				JOptionPane.showMessageDialog(null, "Zarezerwowano pokoj nr "+nrPok);
				ReservationForm form;
				try {
					form = new ReservationForm();
					form.setVisible(true);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		
		
		
	}
}
