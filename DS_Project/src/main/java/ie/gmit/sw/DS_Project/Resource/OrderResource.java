package ie.gmit.sw.DS_Project.Resource;



import ie.gmit.sw.DS_Project.Car;
import ie.gmit.sw.DS_Project.CarOrder;
import ie.gmit.sw.DS_Project.Customer;
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
@Path("/orders")
public class OrderResource {

	/*
	 * In this example class marshalling functionality is handled by Jersey. Notice
	 * how the PurchaseOrder type may be used directly as a return type and input
	 * parameter for each RESTful method. This means that there is no need to write
	 * JAXB/Eclipselink Moxy code manually
	 */
	Registry registry = LocateRegistry.getRegistry();
	// Looking up the registry for the remote object
			Hello stub = (Hello) registry.lookup("howdayService");
	ArrayList<CarOrder> orders = new ArrayList<CarOrder>();
	// Getting the registry
			
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	@Path("/{value}")
	/*
	 * Note how this method has been annotated to produce both XML and JSON The
	 * response format which is sent will depend on the Accept: header field in the
	 * HTTP request from the client
	 */
	public CarOrder getOrder(@PathParam("value") String value) {
		System.out.println(value);
		for (CarOrder carOrder : orders) {
			System.out.println(carOrder.getOrderNumber());
		}
		CarOrder requested = null;
		for (CarOrder p : orders) {
			if (p.getOrderNumber().equals(value)) {
				requested = p;
			}
		}

		return requested;
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_XML)
	@Path("/{value}")
	public Response createOrder(@PathParam("value") String value, CarOrder toCreate) throws Exception {
		System.out.println("Post");
		
		CarOrder requested = null;
		
		for(CarOrder p : orders) {
			
			if(p.getOrderNumber().equals(value)) {
				requested = p;
			}
		}
		
		if(requested != null) {
			String msg = "The order number " + requested.getOrderNumber() + " already exists";
			System.out.println(msg);
			return Response.status(409).entity(msg).build();
		}
		else {
			orders.add(toCreate);
			stub.createOrder(toCreate);
			String msg = "Resource created!";
			System.out.println(msg);
			return Response.status(201).entity(msg).build(); // return 201 for resource created
		}
		
		
	}

	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_XML)
	@Path("/{value}")
	public Response updateOrder(@PathParam("value") String value, CarOrder updated) throws Exception {
		CarOrder requested = null;
		for (CarOrder p : orders) {
			if (p.getOrderNumber().equals(value)) {
				requested = p;
			}
		}

		if (requested != null) {
			String msg = "The order number " + value + " was updated!";
			orders.remove(requested);
			orders.add(updated);
			stub.updateOrder(updated);
			return Response.status(200).entity(msg).build();
		} else {
			String msg = "The order number " + value + " does not exist";
			
			return Response.status(404).entity(msg).build(); // return 404 for resource not found
		}
	}

	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{value}")
	public Response deleteOrder(@PathParam("value") String value) throws Exception {
		CarOrder requested = null;
		for (CarOrder p : orders) {
			if (p.getOrderNumber().equals(value)) {
				requested = p;
			}
		}

		if (requested != null) {
			String msg = "The order number " + value + " was deleted!";
			orders.remove(requested);
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


		ObjectFactory objFactory = new ObjectFactory();

		CarOrder carOrder = objFactory.createCarOrder();
		for(CarOrder c1:stub.getOrder()) {
			System.out.println(c1.getOrderNumber());
			c1.getBillTo().getName();
			carOrder = c1;
			
			orders.add(carOrder);
			
		}

			

	}

}
