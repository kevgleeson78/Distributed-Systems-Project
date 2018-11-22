package ie.gmit.sw.JDBC;

import java.net.MalformedURLException;
import java.rmi.Naming;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;



public class Server extends ImplExample{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Server() throws RemoteException{} 
	   public static void main(String args[]) throws RemoteException, MalformedURLException { 
		 //A string, representing the message we want to associate with our Message object
			
			
			
			
			//Create an instance of a MessageService. As MessageServiceImpl implements the MessageService
			//interface, it can be referred to as a MessageService type.
			Hello ms = new ImplExample();
			
			//Start the RMI regstry on port 1099
			LocateRegistry.createRegistry(1099);
			
			//Bind our remote object to the registry with the human-readable name "howdayService"
			Naming.rebind("howdayService", ms);
			
			//Print a nice message to standard output
			System.out.println("Server ready.");
}
}
