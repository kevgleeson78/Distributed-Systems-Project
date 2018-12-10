package ie.gmit.sw.JDBC;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import ie.gmit.sw.DS_Project.CarOrder;
/*
 * Adapted from the lab: Java RMI Labs (weeks 5 & 6)
 * Available @ https://learnonline.gmit.ie/course/view.php?id=590
 * This file is not used for this application it is her for reference purpose only.
 */

public class Client {
	 private Client() {}  
	   public static void main(String[] args)throws Exception {  
	      try { 
	         // Getting the registry 
	         Registry registry = LocateRegistry.getRegistry(); 
	    
	         // Looking up the registry for the remote object 
	         JDBCInterface stub = (JDBCInterface) registry.lookup("jdbcService"); 
	    
	         // Calling the remote method using the obtained object 
	         List<CarOrder> list = (List)stub.getOrder(); 
	         for (CarOrder s:list){ 
	            
	            // System.out.println("bc "+s.getBranch()); 
	            System.out.println("ID: " + s.getOrderNumber()); 
	           
	         }  
	      // System.out.println(list); 
	      } catch (Exception e) { 
	         System.err.println("Client exception: " + e.toString()); 
	         e.printStackTrace(); 
	      } 
	   } 
}
