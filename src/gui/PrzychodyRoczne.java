package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JMonthChooser;

public class PrzychodyRoczne extends JFrame {

	private JPanel contentPane;
	Connection connection = null;
	int miesiac_liczba = 0;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrzychodyRoczne frame = new PrzychodyRoczne();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void miesiac_jako_liczba(String miesiac)
	{
		switch (miesiac)
		{
			case "Styczen": miesiac_liczba = 0;
				break;
			case "Luty": miesiac_liczba = 1;
				break;
			case "Marzec": miesiac_liczba = 2;
				break;
			case "Kwiecien": miesiac_liczba = 3;
				break;
			case "Maj": miesiac_liczba = 4;
				break;
			case "Czerwiec": miesiac_liczba = 5;
				break;
			case "Lipiec": miesiac_liczba = 6;
				break;
			case "Sierpien": miesiac_liczba = 7;
				break;
			case "Wrzesien": miesiac_liczba = 8;
				break;
			case "Pazdziernik": miesiac_liczba = 9;
				break;
			case "Listopad": miesiac_liczba = 10;
				break;
			case "Grudzien": miesiac_liczba = 11;
				break;
		}
		
	}

	public void przychodMiesieczny(int miesiac, String miesiac_string) throws ParseException
	{		
		int przychod = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		connection = mySqlConnection.dbConnector();
		String query = "SELECT Cena, DataZakupy FROM BarZamowienia";
		try {
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) 
			{
				String data_string = rs.getString("DataZakupy");
				Date data = sdf.parse(data_string);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(data);
				
				int miesiac_z_bazy = calendar.get(Calendar.MONTH);
				if(miesiac_z_bazy == miesiac)
				{
					przychod = przychod + rs.getInt("Cena");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query_1 = "SELECT DataPrzyjazdu, DataOdjazdu, Rachunek FROM GoscieArchiwum";
		try {
			PreparedStatement pst_1 = connection.prepareStatement(query_1);
			ResultSet rs_1 = pst_1.executeQuery();
			while(rs_1.next()) 
			{
				String data_string_1 = rs_1.getString("DataPrzyjazdu");
				Date data_1 = sdf.parse(data_string_1);
				Calendar calendar_1 = Calendar.getInstance();
				calendar_1.setTime(data_1);
				int miesiac_z_bazy_1 = calendar_1.get(Calendar.MONTH);				
				if(miesiac_z_bazy_1 == miesiac)
				{
					przychod = przychod + rs_1.getInt("Rachunek");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(null, "Przychod w miesiacu " + miesiac_string + " wyniosl " + przychod + "pln.");
		przychod = 0;
	}
	
	
	/**
	 * Create the frame.
	 */
	public PrzychodyRoczne() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 431, 292);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(16, 16, 32));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String [] options = {"Styczen","Luty","Marzec","Kwiecien","Maj","Czerwiec", "Lipiec", "Sierpien", "Wrzesien", "Pazdziernik", "Listopad", "Grudzien"};
		JComboBox<String> comboBox = new JComboBox<>(options);
		comboBox.setBounds(200, 142, 151, 23);
		contentPane.add(comboBox);
		
		JLabel lblBarDodaj = new JLabel("Przychody miesieczne");
		lblBarDodaj.setForeground(new Color(96, 96, 128));
		lblBarDodaj.setFont(new Font("Dialog", Font.BOLD, 25));
		lblBarDodaj.setBounds(41, 55, 436, 76);
		contentPane.add(lblBarDodaj);
		
		JLabel miesiac = new JLabel("Miesiac");
		miesiac.setForeground(Color.YELLOW);
		miesiac.setFont(new Font("Dialog", Font.BOLD, 15));
		miesiac.setBounds(51, 133, 149, 45);
		contentPane.add(miesiac);
		
		JButton btnObliczPrzychody = new JButton("Oblicz przychody");
		btnObliczPrzychody.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String miesiac_string = (String) comboBox.getSelectedItem();
				miesiac_jako_liczba(miesiac_string);
				try {
					przychodMiesieczny(miesiac_liczba, miesiac_string);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnObliczPrzychody.setBounds(128, 222, 179, 30);
		contentPane.add(btnObliczPrzychody);
	}
}
