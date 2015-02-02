package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.awt.Color;
import java.awt.Font;

public class MENU extends JFrame {

	private JPanel contentPane;

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
	 */
	public MENU() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 729, 515);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Opcje");
		mnNewMenu.setFont(new Font("Dialog", Font.BOLD, 20));
		mnNewMenu.setForeground(new Color(204, 204, 0));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmS = new JMenuItem("Rezerwacja");
		mntmS.setForeground(new Color(204, 204, 0));
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
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Goscie");
		mntmNewMenuItem.setForeground(new Color(204, 204, 0));
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
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmDodajPokoj = new JMenuItem("Dodaj Pokoj");
		mntmDodajPokoj.setForeground(new Color(204, 204, 0));
		mntmDodajPokoj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DodajPokoj dodaj_pokoj = new DodajPokoj();
				dodaj_pokoj.setVisible(true);
			}
		});
		mnNewMenu.add(mntmDodajPokoj);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Przyszli Goscie");
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
		mntmNewMenuItem_1.setForeground(new Color(204, 204, 0));
		mnNewMenu.add(mntmNewMenuItem_1);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
