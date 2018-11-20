package ie.gmit.sw.DS_Project.Resource;


import ie.gmit.sw.DS_Project.Car;
import ie.gmit.sw.DS_Project.CarOrder;
import ie.gmit.sw.DS_Project.Country;
import ie.gmit.sw.DS_Project.Customer;
import ie.gmit.sw.DS_Project.ObjectFactory;
import ie.gmit.sw.JDBC.Hello;

import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBElement;
import javax.xml.transform.stream.StreamSource;

@Singleton
@Path("/orders")
public class OrderResource {

	/*
	 * In this example class marshalling functionality is handled by Jersey. Notice
	 * how the PurchaseOrder type may be used directly as a return type and input
	 * parameter for each RESTful method. This means that there is no need to write
	 * JAXB/Eclipselink Moxy code manually
	 */

	ArrayList<CarOrder> orders = new ArrayList<CarOrder>();
	// Getting the registry
			Registry registry = LocateRegistry.getRegistry();

			// Looking up the registry for the remote object
			Hello stub = (Hello) registry.lookup("howdayService");

			// Calling the remote method using the obtained object
			List<CarOrder> list = (List) stub.getName();
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	@Path("/{value}")
	/*
	 * Note how this method has been annotated to produce both XML and JSON The
	 * response format which is sent will depend on the Accept: header field in the
	 * HTTP request from the client
	 */
	public CarOrder getOrder(@PathParam("value") String value) {
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
	public Response createOrder(@PathParam("value") String value, CarOrder toCreate) {
		CarOrder requested = null;
		for (CarOrder p : orders) {
			if (p.getOrderNumber().equals(value)) {
				requested = p;
			}
		}

		if (requested != null) {
			String msg = "The order number " + value + " already exists";
			return Response.status(409).entity(msg).build();
		} else {
			orders.add(toCreate);
			String msg = "Resource created!";
			return Response.status(201).entity(msg).build(); // return 201 for resource created
		}
	}

	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_XML)
	@Path("/{value}")
	public Response updateOrder(@PathParam("value") String value, CarOrder updated) {
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
			return Response.status(200).entity(msg).build();
		} else {
			String msg = "The order number " + value + " does not exist";
			;
			return Response.status(404).entity(msg).build(); // return 404 for resource not found
		}
	}

	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{value}")
	public Response deleteOrder(@PathParam("value") String value) {
		CarOrder requested = null;
		for (CarOrder p : orders) {
			if (p.getOrderNumber().equals(value)) {
				requested = p;
			}
		}

		if (requested != null) {
			String msg = "The order number " + value + " was deleted!";
			orders.remove(requested);
			return Response.status(200).entity(msg).build();
		} else {
			String msg = "The order number " + value + " does not exist";
			;
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


		JAXBContext jc = JAXBContext.newInstance("ie.gmit.sw.DS_Project");
		ObjectFactory objFactory = new ObjectFactory();
		
		CarOrder co = objFactory.createCarOrder();
			// System.out.println("bc "+s.getBranch());
			//System.out.println("ID: " +list.get(0).getOrderNumber());
			co.setOrderNumber("Kevin");
			co.setOrderDate(date);

			Customer shipTo = new Customer();
			shipTo.setName("John Doe");
			shipTo.setStreet("123 Castle Road");
			shipTo.setCity("Oranmore");
			shipTo.setCountry(Country.IRELAND);
			
			co.setBillTo(shipTo);
			
			Car items = new Car();
			
			List<Car.Item> col = items.getItem();
			Car.Item i1 = new Car.Item();
			i1.setCarModel("Tyota");
			i1.setOrderDate(date);
			i1.setPrice(new BigDecimal("100.99"));
			
			i1.setQuantity(1);
			col.add(i1);

			
			orders.add(co);

			System.out.println("\n\n######### XML Format #########");
			Marshaller m1 = jc.createMarshaller();
			m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m1.marshal(co, new FileWriter("order.xml"));
			m1.marshal(co, System.out);

	}

}
