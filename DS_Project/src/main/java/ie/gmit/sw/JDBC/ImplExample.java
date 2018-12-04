package ie.gmit.sw.JDBC;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	// JDBC driver name and database URL 
    String JDBC_DRIVER = "com.mysql.jdbc.Driver";   
    String DB_URL = "jdbc:mysql://localhost:3306/carorder";  
    
    // Database credentials 
    String USER = "root"; 
    String PASS = "";  
    
     
	// Implementing the interface method 
	   public List<CarOrder> getOrder() throws Exception {  
	      List<CarOrder> list = new ArrayList<CarOrder>();   
	    
	      
	      
	      //Register JDBC driver 
	      Class.forName(JDBC_DRIVER);   
	      
	      //Open a connection
	      System.out.println("Connecting to a selected database..."); 
	      Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); 
	      System.out.println("Connected database successfully...");  
	      
	      //Execute a query 
	      System.out.println("Creating statement..."); 
	      
	     Statement stmt = conn.createStatement();  
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
	         String date = rs.getString("orderDate");
	         
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
	         car.setOrderDate(date);
	         po.setCar(car);
	         list.add(po); 
	         
	          
	      } 
	      rs.close(); 
	      return list;     
	   }

	@Override
	public void createOrder(CarOrder co) throws Exception{
		//Register JDBC driver 
	      Class.forName(JDBC_DRIVER); 
	      
	      //Open a connection
	      System.out.println("Connecting to a selected database...Add Car"); 
	     Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); 
	      System.out.println("Connected database successfully...");  
	      
	      //Execute a query 
	      System.out.println("Creating statement..."+co.getOrderNumber()); 
	     
	   Statement   stmt = conn.createStatement();  
	      String sql = "INSERT INTO customers (name, orderNumber, orderDate,country,street,city, model,quantity, price) VALUES ('"+co.getBillTo().getName()+"','"+co.getOrderNumber()+"','"+co.getCar().getOrderDate()+"','"+co.getBillTo().getCountry()+"','"+co.getBillTo().getStreet()+"','"+co.getBillTo().getCity()+"','"+co.getCar().getCarModel()+"','"+co.getCar().getQuantity()+"','"+co.getCar().getPrice()+"');"; 
	      stmt.executeUpdate(sql);
		
		
	}

	@Override
	public void deleteOrder(CarOrder co) throws Exception {
		
		//Register JDBC driver 
	      Class.forName(JDBC_DRIVER); 
	      
	      //Open a connection
	      System.out.println("Connecting to a selected database...Delete Car"); 
	     Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); 
	      System.out.println("Connected database successfully...");  
	      
	      //Execute a query 
	      System.out.println("Creating statement..."+co.getOrderNumber()); 
	     
	   Statement   stmt = conn.createStatement();
	   String sql = "Delete from customers WHERE orderNumber = '"+co.getOrderNumber()+"';";
	   System.out.println(sql);
	      stmt.executeUpdate(sql);
	}
	   
	   

}
