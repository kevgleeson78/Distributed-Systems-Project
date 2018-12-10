package ie.gmit.sw.JDBC;

import java.net.MalformedURLException;
import java.rmi.Naming;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;



public class Server extends ImplExample{
	/*
	 * Adapted from the lab: Java RMI Labs (weeks 5 & 6)
	 * Available @ https://learnonline.gmit.ie/course/view.php?id=590
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Server() throws RemoteException{} 
	   public static void main(String args[]) throws RemoteException, MalformedURLException { 
		   	// A new instance of the interface Implementation
			JDBCInterface ms = new ImplExample();
			
			// Start the registery on port 1099 by default
			LocateRegistry.createRegistry(1099);
			
			//Bind a name to the to the service used in the lookup method 
			Naming.rebind("jdbcService", ms);
			
			// Message to show the server is running 
			System.out.println("Server ready.");
}
}
