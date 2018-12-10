package ie.gmit.sw.DS_Project.Resource;

/*
 * The below file was adapted from the  REST JAX-RS/Jersey Labs (weeks 7, 8 & 9)
 * @ https://learnonline.gmit.ie/course/view.php?id=590
 */


import ie.gmit.sw.DS_Project.CarOrder;

import ie.gmit.sw.DS_Project.ObjectFactory;
import ie.gmit.sw.JDBC.Hello;

import java.math.BigDecimal;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.*;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


@Singleton
// The path that the below resources can be accessed at.
@Path("/orders")
public class OrderResource {

	// Looking up the registry for the remote object
	Registry registry = LocateRegistry.getRegistry();
	// Getting the registry
	Hello stub = (Hello) registry.lookup("howdayService");
	
	// A list to store the initial data from the database. Populated by the init method.
	ArrayList<CarOrder> orders = new ArrayList<CarOrder>();
	
	//Annotation for the type of http method that will be called to read data in this case
	@GET
	// Annotation for the type of media that is produced
	//Ordered by priority from left to right
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	//The parameter that is passed from the uri is used to get the car order in the below method.
	@Path("/{value}")
	
	public CarOrder getOrder(@PathParam("value") String value) {
		//System.out.println(value);
		//for (CarOrder carOrder : orders) {
			//System.out.println(carOrder.getOrderNumber());
		//}
		
		// A null variable to hold the results of the below for loop
		CarOrder requested = null;
		//Loop Over the orders array list 
		for (CarOrder p : orders) {
			// Add the order if the order number matches the uri vaule passed
			if (p.getOrderNumber().equals(value)) {
				// Add the data to the above object
				requested = p;
			}
		}
		//Return the object
		return requested;
	}
	
	//The post method is used to create a new object
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_XML)
	@Path("/{value}")
	public Response createOrder(@PathParam("value") String value, CarOrder toCreate) throws Exception {
		//System.out.println("Post");
		// Container for the new object
		CarOrder requested = null;
		//Check if the object is allready in the arrayList
		for(CarOrder p : orders) {
			
			if(p.getOrderNumber().equals(value)) {
				requested = p;
			}
		}
		// IF the object exists it cant be created return the response code to show this.
		if(requested != null) {
			String msg = "The order number " + requested.getOrderNumber() + " already exists";
			System.out.println(msg);
			return Response.status(409).entity(msg).build();
		}
		
		else {
			//Add the object to the array list
			orders.add(toCreate);
			//Add the object to the remote method for creating new data to the database.
			stub.createOrder(toCreate);
			String msg = "Resource created!";
			System.out.println(msg);
			return Response.status(201).entity(msg).build(); // return 201 for resource created
		}
		
		
	}
	// Used to update the database
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_XML)
	@Path("/{value}")
	public Response updateOrder(@PathParam("value") String value, CarOrder updated) throws Exception {
		System.out.println("Put");
		//Empty container to hold the results
		CarOrder requested = null;
		//Get the object from the arrayList
		for (CarOrder p : orders) {
			if (p.getOrderNumber().equals(value)) {
				//Assign the object to the empty carOrder
				requested = p;
			}
		}
		//Updating the object
		if (requested != null) {
			String msg = "The order number " + value + " was updated!";
			//REmove the old object from the orders ArrayList
			orders.remove(requested);
			// Add the new object to the orders ArrayLsit
			orders.add(updated);
			//Add the new object to the remote method for updating the database.
			stub.updateOrder(updated);
			return Response.status(200).entity(msg).build();
		} else {
			
			//If the object does not exist
			String msg = "The order number " + value + " does not exist";
			
			return Response.status(404).entity(msg).build(); // return 404 for resource not found
		}
	}
	//USed to delete from the database and the global orders arayList
	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{value}")
	public Response deleteOrder(@PathParam("value") String value) throws Exception {
		//Empty container to hold the results
		CarOrder requested = null;
		//Get the object from the arrayList
		for (CarOrder p : orders) {
			if (p.getOrderNumber().equals(value)) {
				//Assign the object to the empty carOrder
				requested = p;
			}
		}

		if (requested != null) {
			String msg = "The order number " + value + " was deleted!";
			//Remove the object from the ArrayList
			orders.remove(requested);
			//Call the remote method and pass the object to be deleted from the database.
			stub.deleteOrder(requested);
			return Response.status(200).entity(msg).build();
		} else {
			String msg = "The order number " + value + " does not exist";
			
			return Response.status(404).entity(msg).build(); // return 404 for resource not found
		}
	}

	public OrderResource() throws Exception {
		init();
	}

	private void init() throws Exception {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date(System.nanoTime()));
		XMLGregorianCalendar date = null;
		try {
			date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Create a new object factory
		ObjectFactory objFactory = new ObjectFactory();
		// assign a new car order from the object factory
		CarOrder carOrder = objFactory.createCarOrder();
		//Get the remote method getOrder() and store the details into the new carOrder.
		for(CarOrder c1:stub.getOrder()) {
			//System.out.println(c1.getOrderNumber());
			//Get remote object and store it in carOrder
			c1.getBillTo().getName();
			carOrder = c1;
			//Add the carOrder to the arrayList orders for initialization.
			orders.add(carOrder);
			
		}

			

	}
}
