package ie.gmit.sw.JDBC;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ie.gmit.sw.DS_Project.CarOrder;



public class ImplExample extends UnicastRemoteObject implements Hello {  
	   
	   protected ImplExample() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Implementing the interface method 
	   public List<CarOrder> getName() throws Exception {  
	      List<CarOrder> list = new ArrayList<CarOrder>();   
	    
	      // JDBC driver name and database URL 
	      String JDBC_DRIVER = "com.mysql.jdbc.Driver";   
	      String DB_URL = "jdbc:mysql://localhost:3306/ordersdb";  
	      
	      // Database credentials 
	      String USER = "root"; 
	      String PASS = "";  
	      
	      Connection conn = null; 
	      Statement stmt = null;  
	      
	      //Register JDBC driver 
	      Class.forName(JDBC_DRIVER);   
	      
	      //Open a connection
	      System.out.println("Connecting to a selected database..."); 
	      conn = DriverManager.getConnection(DB_URL, USER, PASS); 
	      System.out.println("Connected database successfully...");  
	      
	      //Execute a query 
	      System.out.println("Creating statement..."); 
	      
	      stmt = conn.createStatement();  
	      String sql = "SELECT * FROM purchaseorder"; 
	      ResultSet rs = stmt.executeQuery(sql);  
	      
	      //Extract data from result set 
	      while(rs.next()) { 
	         // Retrieve by column name 
	    
	         
	         String name = rs.getString("OrderNumber"); 
	         
	         
	         // Setting the values 
	         CarOrder po = new CarOrder(); 
	         po.setOrderNumber(name); 
	         
	         list.add(po); 
	      } 
	      rs.close(); 
	      return list;     
	   }  

}
