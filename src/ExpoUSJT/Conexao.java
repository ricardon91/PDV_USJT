package ExpoUSJT;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexao {

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/projeto?useTimezone=true&serverTimezone=UTC", "root", "141626");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel estabelecer conexão com o banco");
		}
		return null;
	}

}
