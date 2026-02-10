package Bank;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {

	//   utility class static method helper cls
	
	static {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// console
			System.out.println("driver load....");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection connect() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/logindb";
		String username = "root";
		String pass = "root";
		
		return DriverManager.getConnection(url, username, pass);
	}
}

