package gui;

import java.awt.BorderLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import net.proteanit.sql.DbUtils;

public class ArchiwumGosci extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	Connection connection = null;
	String miesiac_cyfra;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ArchiwumGosci frame = new ArchiwumGosci();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void wczytajGosci(JTable table)
	{
		connection = mySqlConnection.dbConnector();
		String query = "SELECT * FROM GoscieArchiwum";
		try {
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void wczytajGosciPoMiesiacu(String miesiac, JTable table)
	{
		
		switch(miesiac)
		{
			case "Styczen": miesiac_cyfra = "01";
				break;
			case "Luty": miesiac_cyfra = "02";
				break;
			case "Marzec": miesiac_cyfra = "03";
				break;
			case "Kwiecien": miesiac_cyfra = "04";
				break;
			case "Maj": miesiac_cyfra = "05";
				break;
			case "Czerwiec": miesiac_cyfra = "06";
				break;
			case "Lipiec": miesiac_cyfra = "07";
				break;
			case "Sierpien": miesiac_cyfra = "08";
				break;
			case "Wrzesien": miesiac_cyfra = "09";
				break;
			case "Pazdziernik": miesiac_cyfra = "10";
				break;
			case "Listopad": miesiac_cyfra = "11";
				break;
			case "Grudzien": miesiac_cyfra = "12";
				break;
			case "-": miesiac_cyfra = "__";
				break;
		}
		
		connection = mySqlConnection.dbConnector();
		String query = "SELECT * FROM GoscieArchiwum WHERE DataPrzyjazdu LIKE ?";
		try {
			String regular_expression = "%-" + miesiac_cyfra + "-%";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, regular_expression);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	/**
	 * Create the frame.
	 */
	public ArchiwumGosci() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 933, 474);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(16, 16, 32));
		
		JLabel lblWybierzMiesiac = new JLabel("Wybierz miesiac");
		lblWybierzMiesiac.setForeground(new Color(150, 96, 128));
		lblWybierzMiesiac.setFont(new Font("Dialog", Font.BOLD, 15));
		lblWybierzMiesiac.setBounds(700, 82, 320, 76);
		contentPane.add(lblWybierzMiesiac);
		
		String [] options = {"-", "Styczen","Luty","Marzec","Kwiecien","Maj","Czerwiec", "Lipiec", "Sierpien", "Wrzesien", "Pazdziernik", "Listopad", "Grudzien"};
		JComboBox<String> comboBox = new JComboBox<>(options);
		comboBox.setBounds(700, 142, 151, 23);
		contentPane.add(comboBox);
		
		JLabel lblBarDodaj = new JLabel("Archiwum gosci");
		lblBarDodaj.setForeground(new Color(150, 96, 128));
		lblBarDodaj.setFont(new Font("Dialog", Font.BOLD, 30));
		lblBarDodaj.setBounds(262, 12, 320, 76);
		contentPane.add(lblBarDodaj);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 107, 632, 312);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		wczytajGosci(table);
		
		JButton btnFiltrujWyniki = new JButton("Pokaz gosci");
		btnFiltrujWyniki.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wczytajGosciPoMiesiacu((String) comboBox.getSelectedItem(), table);
			}
		});
		btnFiltrujWyniki.setBounds(700, 202, 140, 27);
		contentPane.add(btnFiltrujWyniki);
	}
}
