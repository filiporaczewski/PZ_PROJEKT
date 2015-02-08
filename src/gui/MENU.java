package gui;

import java.awt.BorderLayout;
import java.sql.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.ImageIcon;



public class MENU extends JFrame {
	
	private JPanel contentPane;
	Connection connection = null;
	
	public boolean goscieDoWymeldowania()
	{
		Date aktualna_data = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String data = sdf.format(aktualna_data);
		connection = mySqlConnection.dbConnector();
		String query = "SELECT DataOdjazdu FROM Pokoje";
		try {
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) 
			{
				
				if(data.equals(rs.getString("DataOdjazdu")))
				{
					return true;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MENU frame = new MENU();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	
	public static JMenuItem mntmNewMenuItem;
	
	public MENU() throws IOException {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1185, 681);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu mnNewMenu = new JMenu("Opcje");
		mnNewMenu.setFont(new Font("Dialog", Font.BOLD, 20));
		mnNewMenu.setForeground(new Color(96, 96, 128));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmS = new JMenuItem("Rezerwacja");
		mntmS.setForeground(new Color(96, 96, 128));
		mntmS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					HotelApp app = new HotelApp();
					app.setVisible(true);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		mnNewMenu.add(mntmS);
		
		JMenu mnGoscie = new JMenu("Goscie");
		mnGoscie.setForeground(new Color(96, 96, 128));
		mnNewMenu.add(mnGoscie);
		
		mntmNewMenuItem = new JMenuItem("Goscie");
		if(goscieDoWymeldowania())
		{
			mntmNewMenuItem.setForeground(new Color(255, 0, 0));
		} else
		{
			mntmNewMenuItem.setForeground(new Color(96, 96, 128));
		}
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					GoscieHotelu goscie = new GoscieHotelu();
					goscie.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
				
			}
		});
		mnGoscie.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Przyszli goscie");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrzyszliGoscie goscie;
				try {
					goscie = new PrzyszliGoscie();
					goscie.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		mntmNewMenuItem_1.setForeground(new Color(96, 96, 128));
		mnGoscie.add(mntmNewMenuItem_1);
		
		JMenuItem mntmArchiwumGosci = new JMenuItem("Archiwum gosci");
		mntmArchiwumGosci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArchiwumGosci archiwum;
				archiwum = new ArchiwumGosci();
				archiwum.setVisible(true);
			}
		});
		mntmArchiwumGosci.setForeground(new Color(96, 96, 128));
		mnGoscie.add(mntmArchiwumGosci);
		
		JMenuItem mntmDodajPokoj = new JMenuItem("Dodaj Pokoj");
		mntmDodajPokoj.setForeground(new Color(96, 96, 128));
		mntmDodajPokoj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DodajPokoj dodaj_pokoj = new DodajPokoj();
				dodaj_pokoj.setVisible(true);
			}
		});
		mnNewMenu.add(mntmDodajPokoj);
		
		JMenu mnBar = new JMenu("Bar");
		mnBar.setForeground(new Color(96, 96, 128));
		mnNewMenu.add(mnBar);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Nowa pozycja");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BarNowaPozycja bar_dodaj = new BarNowaPozycja();
				bar_dodaj.setVisible(true);
			}
		});
		mntmNewMenuItem_2.setForeground(new Color(96, 96, 128));
		mnBar.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Nowe zamowienie");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BarHotelowy bar_zamowienie = new BarHotelowy();
				bar_zamowienie.setVisible(true);
			}
		});
		mntmNewMenuItem_3.setForeground(new Color(96, 96, 128));
		mnBar.add(mntmNewMenuItem_3);
		
		
		JMenu mnPrzychody = new JMenu("Przychody");
		mnPrzychody.setForeground(new Color(96, 96, 128));
		mnNewMenu.add(mnPrzychody);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Przychody dzienne");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Przychody przychody = new Przychody();
				przychody.setVisible(true);
			}
		});
		mntmNewMenuItem_4.setForeground(new Color(96, 96, 128));
		mnPrzychody.add(mntmNewMenuItem_4);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Przychody miesięczne");
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrzychodyRoczne przychody_roczne = new PrzychodyRoczne();
				przychody_roczne.setVisible(true);
			}
		});
		mntmNewMenuItem_5.setForeground(new Color(96, 96, 128));
		mnPrzychody.add(mntmNewMenuItem_5);
		
		contentPane = new JPanel();
		
		contentPane.setBackground(new Color(16, 16, 32));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setBounds(0, 0, 1185, 661);
		ImageIcon image = new ImageIcon("resources/obrazki/hti_1448061504.jpg");
		label.setIcon(image);
		contentPane.add(label);
	}
	
}



