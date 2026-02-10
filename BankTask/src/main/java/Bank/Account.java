package Bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/account")
public class Account extends HttpServlet {

	
	@Override
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException {
		
		// html page datas vangi kiran 
		String pin        = req.getParameter("pinno");
		String holderName = req.getParameter("accountHolder");
		
		// dbConnection  from details
		Double fromBalance = 0.0;
		try {
			
			Connection con = Db.connect();
			PreparedStatement pst =con.prepareStatement("select bankBalance,accountHolderName,accountNo from Bank where pinNo = ? and accountHolderName = ?");
			pst.setString(1, pin);
			pst.setString(2, holderName);
			ResultSet rst =pst.executeQuery();
			
			if(rst.next()) {
				System.out.println("from details");
				System.out.println("accountNumber :"+ rst.getLong("accountNo"));
				System.out.println("accountHolderName :"+ rst.getString("accountHolderName"));
				System.out.println("accountBankBalance:"+ rst.getDouble("bankBalance"));
				
				fromBalance = rst.getDouble("bankBalance");
				// server details vaikanum 
				// from oda details
				// json format
//				res.setContentType("application/json");
//				PrintWriter out = res.getWriter();
//				
//				out.println("from details.....");
//				out.println(" {");
//				out.println("\"accountNumber\" :\""+rst.getLong("accountNo")+"\",");
//				out.println("\"accountHolderName\" :\""+rst.getString("accountHolderName")+"\",");
//				out.println("\"accountBankBalance\" :"+rst.getDouble("bankBalance"));
//				out.println("}");
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// dbConnection To details
		String toPin = req.getParameter("topinno");
		String toAccountHolder = req.getParameter("toaccountHolder");
		
		double toBalance = 0.0;
		
				try {
					Connection con = Db.connect();
					PreparedStatement pst =con.prepareStatement("select bankBalance,accountHolderName,accountNo from Bank where pinNo = ? and accountHolderName = ?");
					pst.setString(1, toPin);
					pst.setString(2, toAccountHolder);
					ResultSet rst =pst.executeQuery();
					
					if(rst.next()) {
						System.out.println("To details");
						System.out.println("accountNumber :"+ rst.getLong("accountNo"));
						System.out.println("accountHolderName :"+ rst.getString("accountHolderName"));
						System.out.println("accountBankBalance:"+ rst.getDouble("bankBalance"));
						
						toBalance = rst.getDouble("bankBalance");
						
//						res.setContentType("application/json");
//						PrintWriter out = res.getWriter();
//						out.println("To details.....");
//						out.println(" {");
//						out.println("\"accountNumber\" :\""+rst.getLong("accountNo")+"\",");
//						out.println("\"accountHolderName\" :\""+rst.getString("accountHolderName")+"\",");
//						out.println("\"accountBankBalance\" :"+rst.getDouble("bankBalance"));
//						out.println("}");
//						
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				// datas vera server kudukura 
				
				// from amnt
//				req.setAttribute("fromAmnt", fromBalance);
//				
//				// to amnt 
//				req.setAttribute("toAmnt", toBalance);
//				
//				// datas vera server run so include ana datas share pannum  and full mudichitu inga run agum 
//				
//				req.getRequestDispatcher("money").forward(req, res);
				// res.sendRedirect("money");
//				res.sendRedirect("");
//				
			res.sendRedirect("money.html");
				
	
	}
}
