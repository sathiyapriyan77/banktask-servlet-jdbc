package Bank;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/money")
public class MoneyTransfer extends HttpServlet{

	
	@Override
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException {
//		
//		fromBalance = (Double)req.getAttribute("fromAmnt");
//		toBalance = (Double)req.getAttribute("toAmnt");
//		
		
		res.sendRedirect("money.html");
	}
	
	@Override
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException {
		

		Double fromBalance = 0.0;
		Double toBalance = 0.0;
		Double fromamnt = 0.0;
		Long fromAccNo = 0l;
		Long toAccNo = 0l;
		Connection con = null;
		
		// html page iruthu vangurathu
		 fromamnt = Double.parseDouble(req.getParameter("fromamnt"));
		
		// from details
		 fromAccNo = Long.parseLong(req.getParameter("fromAccountNo"));
	
		
		// to details
		 toAccNo	 = Long.parseLong(req.getParameter("toAccountNo"));
				
				
				
			
		
		// dbConnection  from details
				
				try {
					
					 con = Db.connect();
					PreparedStatement pst =con.prepareStatement("select bankBalance from Bank where accountNo = ?");
					pst.setLong(1, fromAccNo);
					
					
					ResultSet rst = pst.executeQuery();
					
					if(rst.next()) {
						fromBalance = rst.getDouble("bankBalance");
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
               // dbConnection  to details
				
				try {
					
					 con = Db.connect();
					PreparedStatement pst =con.prepareStatement("select bankBalance from Bank where accountNo = ?");
					pst.setLong(1, toAccNo);
					
					
					ResultSet rst = pst.executeQuery();
					
					if(rst.next()) {
						toBalance = rst.getDouble("bankBalance");
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				Double frombalance = fromBalance - fromamnt;
				Double tobal = toBalance + fromamnt;
				System.out.println(tobal); //
				

				
				
				if(fromBalance >= fromamnt) {
					
					
					
					// db connection ana bankbalnace update panna venum 
						try{
							
						    con = Db.connect();
						    con.setAutoCommit(false);
						    
						    //sender debit
							PreparedStatement pst = con.prepareStatement("update Bank set bankBalance = ? where accountNo = ?");
							pst.setDouble(1, frombalance);
							pst.setLong(2, fromAccNo);
							
							int row = pst.executeUpdate();
							
							System.out.println("1. rows affected: "+row);
							
							//receiver credit
							PreparedStatement pst2 = con.prepareStatement("update Bank set bankBalance = ? where accountNo = ?");
							pst2.setDouble(1, tobal);
							pst2.setLong(2, toAccNo);
							
							int row2 = pst2.executeUpdate();
							
							System.out.println("2. rows affected: "+row2);
							
							con.commit();
							
							// console log 
							res.getWriter().println("money transmited.......");
							
						}
						catch(Exception e) {
							
							try {
								con.rollback();
							} catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							
						}
				}
				
				else {
					// console oru log 
					System.out.println("insuffient bank balance.....you can again payment pls wait 2 mins");
					
					// server oru log
					res.getWriter().println("insuffient bank balance..... you can again payment pls wait 2 mins");
					
					try {
						
						Thread.sleep(5000);
						
					} catch (InterruptedException e) {
						
						res.sendRedirect("money");
					}
					
				}
				
			
		
	}
}
