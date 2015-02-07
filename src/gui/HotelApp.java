package gui;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
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
import java.beans.PropertyChangeListener;

import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;

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
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1013, 691);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(16, 16, 32));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String [] options = {"-","1","2","3","4","5", "6"};
		JComboBox<String> comboBox = new JComboBox<>(options);
		comboBox.setBounds(533, 222, 151, 23);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("REZERWACJA POKOJU");
		lblNewLabel.setForeground(new Color(96, 96, 128));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 30));
		lblNewLabel.setBounds(336, 35, 371, 39);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Sprawdz wolne pokoje");
		
		btnNewButton.setBounds(407, 279, 214, 39);
		contentPane.add(btnNewButton);
		
		JButton btnIdGoscia = new JButton("Dokonaj Rezerwacji");
		btnIdGoscia.setEnabled(false);
		
		btnIdGoscia.setBounds(407, 597, 256, 54);
		contentPane.add(btnIdGoscia);
		
		JLabel lblNewLabel_1 = new JLabel("od");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_1.setForeground(new Color(255, 255, 0));
		lblNewLabel_1.setBounds(338, 110, 70, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("do");
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_2.setForeground(new Color(255, 255, 0));
		lblNewLabel_2.setBounds(338, 171, 70, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("<html>Rodzaj pokoju <br>(iluosobowy)</html>");
		lblNewLabel_3.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_3.setForeground(new Color(255, 255, 0));
		lblNewLabel_3.setBounds(336, 219, 151, 31);
		contentPane.add(lblNewLabel_3);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setBounds(533, 110, 151, 23);
		dateChooser.setMinSelectableDate(new Date());
		String max_date = "2015-12-29";
		Date MD = sdf.parse(max_date);
		dateChooser.setMaxSelectableDate(MD);
		
		dateChooser.getDateEditor().setEnabled(false);
		contentPane.add(dateChooser);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setDateFormatString("yyyy-MM-dd");
		dateChooser_1.setBounds(533, 161, 151, 25);
		dateChooser_1.setMinSelectableDate(new Date());
		String max_date_1 = "2015-12-30";
		Date MD_1 = sdf.parse(max_date_1);
		dateChooser_1.setMaxSelectableDate(MD_1);
		dateChooser_1.getDateEditor().setEnabled(false);
		contentPane.add(dateChooser_1);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("/home/filip-linux/workspace/gui/obrazki/pokoj.jpg"));
		label.setBounds(0, 0, 1043, 699);
		contentPane.add(label);
		
		table = new JTable();
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(240, 360, 597, 200);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				if(dateChooser.getDate() != null && dateChooser_1.getDate() != null)
				{
					btnIdGoscia.setEnabled(true);
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
								String query2 = "UPDATE Pokoje SET WolnyWTerminie = true WHERE nrPokoju = ?";
								PreparedStatement pst2 = connection.prepareStatement(query2);
								pst2.setString(1, Pokoj);
								pst2.executeUpdate();
							} else 
							{

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
					String query3;
					if(comboBox.getSelectedItem() == "-")
					{
						query3 = "SELECT nrPokoju, RodzajPokoju, Cena, IDGoscia, DataPrzyjazdu, DataOdjazdu FROM Pokoje WHERE WolnyWTerminie = true ORDER BY NrPokoju";
					}
					else
					{
						query3 = "SELECT nrPokoju, RodzajPokoju, Cena, IDGoscia, DataPrzyjazdu, DataOdjazdu FROM Pokoje WHERE WolnyWTerminie = true AND RodzajPokoju=? ORDER BY NrPokoju";
					}
					PreparedStatement pst3;
					try {
						pst3 = connection.prepareStatement(query3);
						if(comboBox.getSelectedItem() != "-")
							pst3.setInt(1, Integer.parseInt((String) comboBox.getSelectedItem()));
						
						
						ResultSet rst3 = pst3.executeQuery();
						
						table.setModel(DbUtils.resultSetToTableModel(rst3));
						DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

						for(int x=0;x<table.getColumnCount();x++){
					         table.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
					        }
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}		
					
				} else 
				{
					JOptionPane.showMessageDialog(null, "Wybierz odpowiednia date!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
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
				
				if(current_row >= 0) 
				{
					Object x = table.getValueAt(current_row, 0);
					nrPok = x.toString();
					ReservationForm form;
					try {
						
						form = new ReservationForm();
						form.setVisible(true);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else 
				{
					JOptionPane.showMessageDialog(null, "Prosze wybrac pokoj", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				
				
			}
		});
		
		
		
		
	}
}
