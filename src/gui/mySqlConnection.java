package gui;

import java.sql.*;
import javax.swing.*;

import javax.swing.JOptionPane;

public class mySqlConnection {
		Connection con = null;
		public static Connection dbConnector() 
		{
			try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "subaru23");
			
			return con;
			} catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
				return null;
			}
		}
}

