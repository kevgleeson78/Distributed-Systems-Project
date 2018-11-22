package ie.gmit.sw.JDBC;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ie.gmit.sw.DS_Project.Car;
import ie.gmit.sw.DS_Project.CarOrder;
import ie.gmit.sw.DS_Project.Customer;





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
	      String DB_URL = "jdbc:mysql://localhost:3306/carorder";  
	      
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
	      String sql = "SELECT * FROM customers;"; 
	      ResultSet rs = stmt.executeQuery(sql);  
	      
	      //Extract data from result set 
	      while(rs.next()) { 
	         // Retrieve by column name 
	    
	         
	         String orderNumber = rs.getString("orderNumber"); 
	         String name = rs.getString("name");
	         String country = rs.getString("country");
	         String street = rs.getString("street");
	         String city = rs.getString("city");
	         String model = rs.getString("model");
	         BigDecimal price = rs.getBigDecimal("price");
	         int quant = rs.getInt("quantity");
	         
	         
	         // Setting the values 
	         CarOrder po = new CarOrder(); 
	         po.setOrderNumber(orderNumber);
	         Customer cust = new Customer();
	         cust.setName(name);
	         cust.setCountry(country);
	         cust.setStreet(street);
	         cust.setCity(city);
	         
	         po.setBillTo(cust);
	         Car car = new Car();
	         car.setCarModel(model);
	         car.setPrice(price);
	         car.setQuantity(quant);
	         po.setCar(car);
	         list.add(po); 
	         
	          
	      } 
	      rs.close(); 
	      return list;     
	   }  

}
