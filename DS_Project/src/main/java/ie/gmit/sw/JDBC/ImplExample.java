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

import ie.gmit.sw.DS_Project.Address;
import ie.gmit.sw.DS_Project.CarOrder;
import ie.gmit.sw.DS_Project.Cars;
import ie.gmit.sw.DS_Project.Cars.Car;






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
	         po.setOrderDate(date);
	         Address cust = new Address();
	         cust.setName(name);
	         cust.setCounty(country);
	         cust.setStreet(street);
	         cust.setCity(city);
	         
	         po.setBillTo(cust);
	         Cars items = new Cars();
	 		po.setCars(items);
	 		List<Cars.Car> col = items.getCar();
	 		Cars.Car i1 = new Cars.Car();
	 		i1.setCarName(model);
	 		i1.setPrice(price);
	 		i1.setQuantity(quant);
	 		i1.setBookingDate(date);
	 		col.add(i1);
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
	      String carName = "";
	      int quantity = 0;
	      BigDecimal price = new BigDecimal(0);
	      //Execute a query 
	      System.out.println("Creating statement..."+co.getOrderNumber()); 
	      for (Car p : co.getCars().getCar()) { 
			carName = p.getCarName();
			quantity = p.getQuantity();
			price = p.getPrice();
		}
	   Statement   stmt = conn.createStatement();  
	      String sql = "INSERT INTO customers (name, orderNumber, orderDate,country,street,city, model,quantity, price) VALUES ('"+co.getBillTo().getName()+"','"+co.getOrderNumber()+"','"+co.getOrderDate()+"','"+co.getBillTo().getCounty()+"','"+co.getBillTo().getStreet()+"','"+co.getBillTo().getCity()+"','"+ carName+"','"+quantity+"','"+price+"');"; 
	      stmt.executeUpdate(sql);
		
		
	}
/*
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
*/
	@Override
	public void updateOrder(CarOrder co) throws Exception {
		//Register JDBC driver 
	      Class.forName(JDBC_DRIVER); 
	      
	      //Open a connection
	      System.out.println("Connecting to a selected database...Update CarORder"); 
	    Connection  conn = DriverManager.getConnection(DB_URL, USER, PASS); 
	      System.out.println("Connected database successfully...");  
	      String carName = "";
	      int quantity = 0;
	      BigDecimal price = new BigDecimal(0);
	      //Execute a query 
	      System.out.println("Creating statement..."+co.getOrderNumber()); 
	      for (Car p : co.getCars().getCar()) { 
			carName = p.getCarName();
			quantity = p.getQuantity();
			price = p.getPrice();
		}
	      //Execute a query 
	      System.out.println("Creating statement..."); 
	     System.out.println(co.getBillTo().getStreet());
	     Statement stmt = conn.createStatement();  
	      String sql = "UPDATE customers SET name='"+co.getBillTo().getName()+"', orderDate='"+co.getOrderDate()+"', country='"+co.getBillTo().getCounty()+"', street='"+co.getBillTo().getStreet()+"', city='"+co.getBillTo().getCity()+"', model='"+carName+"', quantity='"+quantity+"', price='"+price+"'  WHERE  `orderNumber`='"+co.getOrderNumber()+"';"; 
	      stmt.executeUpdate(sql);
		
	}
	   
	   

}