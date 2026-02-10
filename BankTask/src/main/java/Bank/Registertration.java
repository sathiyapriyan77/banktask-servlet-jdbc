package Bank;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register2")
public class Registertration extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException {
		
		// account no generate
		long accountNo = (long)(Math.random() * 9000000000000L) + 1000000000000L;
		
		// register vara datas vangikonum 
		String name  = req.getParameter("AHolderName");
		String phnno = req.getParameter("pinNo");
		String email = req.getParameter("email");
		Double initialAmnt =Double.parseDouble(req.getParameter("bankBalance"));
		
		// datas db store aganum // db coonection
		try {
			
			Connection con = Db.connect();
			PreparedStatement pst =con.prepareStatement("insert into Bank values(?,?,?,?,?)");
			pst.setLong(1, accountNo);
			pst.setString(2, name);
			pst.setString(3, phnno);
			pst.setString(4, email);
			pst.setDouble(5, initialAmnt);
			
			// console oru log
			int row = pst.executeUpdate();
			System.out.println(row);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		res.sendRedirect("RegistrationSucess.html");
		
		
	}
}
