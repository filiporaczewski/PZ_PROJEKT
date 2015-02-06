package gui;

import java.awt.BorderLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.sql.*;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Przychody extends JFrame {

	private JPanel contentPane;
	int przychod = 0;

	/**
	 * Launch the application.
	 */
	
	Connection connection = null;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Przychody frame = new Przychody();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void policzDziennyPrzychod(String data) throws ParseException
	{
		connection = mySqlConnection.dbConnector();
		String query = "SELECT Cena FROM BarZamowienia WHERE DataZakupy=?";
		try {
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, data);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				przychod = przychod + rs.getInt("Cena");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query_1 = "SELECT Cena, DataPrzyjazdu, DataOdjazdu FROM Pokoje WHERE DataPrzyjazdu=?";
		try {
			PreparedStatement pst_1 = connection.prepareStatement(query_1);
			pst_1.setString(1, data);
			ResultSet rs_1 = pst_1.executeQuery();
			while(rs_1.next())
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date1 = sdf.parse(data);
				Date date2 = sdf.parse(rs_1.getString("DataOdjazdu"));
				long diff = date2.getTime() - date1.getTime();
				int days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				przychod = przychod + rs_1.getInt("Cena") * days;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(null, "Calkowity przychod dnia " + data + ": " + przychod + " pln");
	}

	/**
	 * Create the frame.
	 */
	public Przychody() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 362, 279);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(16, 16, 32));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPrzychody = new JLabel("Przychody");
		lblPrzychody.setForeground(new Color(96, 96, 128));
		lblPrzychody.setBackground(new Color(0, 0, 51));
		lblPrzychody.setFont(new Font("Dialog", Font.BOLD, 32));
		lblPrzychody.setBounds(86, 61, 215, 38);
		contentPane.add(lblPrzychody);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(171, 122, 144, 27);
		contentPane.add(dateChooser);
		
		JLabel lblNewLabel_2 = new JLabel("Wybierz date");
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_2.setForeground(new Color(255, 255, 0));
		lblNewLabel_2.setBounds(32, 122, 137, 27);
		contentPane.add(lblNewLabel_2);
		
		JButton btnPoliczPrzychod = new JButton("Policz przychod");
		btnPoliczPrzychod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date data = dateChooser.getDate();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String wybrana_data = sdf.format(data);
				try {
					policzDziennyPrzychod(wybrana_data);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnPoliczPrzychod.setBounds(86, 184, 173, 27);
		contentPane.add(btnPoliczPrzychod);
	}
}
