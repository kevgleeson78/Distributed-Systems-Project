package ie.gmit.sw.ds;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import ie.gmit.sw.DS_Project.Address;
import ie.gmit.sw.DS_Project.CarOrder;
import ie.gmit.sw.DS_Project.Cars;
import ie.gmit.sw.DS_Project.ObjectFactory;

/**
 * Servlet implementation class CarServlet
 */
@WebServlet("/CarServlet")
public class CarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CarServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// Marshalling and unmarshalling in the below methods were adapted from the file(JAXBPOExample)
	// Available at https://learnonline.gmit.ie/course/view.php?id=590
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//The url to the resource
		String resourceBaseURL = "http://localhost:8080/DS_Project/webapi/orders/";
		//The parameter passed to be added to the end of the above url
		String requestedOrder = request.getParameter("param1");
		URL url;
		HttpURLConnection con;
		String resultInXml = "";

		// try to create a connection and request XML format
		try {
			//The full URL for the resource
			url = new URL(resourceBaseURL + requestedOrder);
			// Open a connection to the resource
			con = (HttpURLConnection) url.openConnection();
			// The http method that will be used in the resource.
			con.setRequestMethod("GET");
			// Set the request type
			con.setRequestProperty("Accept", "application/xml");
			// Get the data input stream
			InputStream in = con.getInputStream();
			// Buffered reader to read the xml
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			//Store the xml in the global variable resultInXml as a continious string
			resultInXml = br.lines().collect(Collectors.joining());
			// Get a new instance of the carOrder
			JAXBContext jaxbContext = JAXBContext.newInstance(CarOrder.class);
			// Unmarshalling the xml
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			// Read the xml string
			StringReader reader = new StringReader(resultInXml);
			// Bind the xml data to the carOrder object
			CarOrder requested = (CarOrder) unmarshaller.unmarshal(reader);
			// Set the attribute as requeted can be used to access data on the jsp page.
			request.setAttribute("requested", requested);
			// Used to forward to a new page once the request has been completed
			String nextJSP = "/ViewBooking.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			//Forward to the new page.
			dispatcher.forward(request, response);
			// Close the connection
			con.disconnect();

		} catch (IOException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// print out the results of the requests for testing
		System.out.println("****** Result in XML *****");
		System.out.println(resultInXml);
		System.out.println();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {

		// System.out.println(request.getParameter("name"));
		// THe url address to the web service resource 
		String resourceBaseURL = "http://localhost:8080/DS_Project/webapi/orders/";
		// Get the parameters from the form that has been posted
		String requestedOrder = request.getParameter("orderNumber");
		String custName = request.getParameter("name");
		String date = request.getParameter("date");
		String country = request.getParameter("country");
		String street = request.getParameter("street");
		String city = request.getParameter("city");
		String model = request.getParameter("carModel");
		BigDecimal price = new BigDecimal(request.getParameter("price"));
		BigDecimal quantityBD = new BigDecimal(request.getParameter("quantity"));
		int quantity = quantityBD.intValue();
		// Multiply the price by the quantity to get teh final price
		BigDecimal finalPrice = price.multiply(quantityBD);
		
		URL url;
		HttpURLConnection con;
		String resultInXml = "";

		// try to create a connection and request XML format
		try {

			ObjectFactory objFactory = new ObjectFactory();
			// A new car order to be sent to the post method in the web service
			CarOrder carOrder = objFactory.createCarOrder();
			// Set all of the values to the new object
			carOrder.setOrderNumber(requestedOrder);
			Address cust = new Address();
			cust.setName(custName);
			cust.setCounty(country);
			cust.setCity(city);
			cust.setStreet(street);
			carOrder.setBillTo(cust);
			Cars cars = new Cars();
			carOrder.setCars(cars);
			List<Cars.Car> col = cars.getCar();
			Cars.Car i1 = new Cars.Car();
			i1.setCarName(model);
			i1.setQuantity(quantity);
			i1.setPrice(finalPrice);
			i1.setBookingDate(date);
			col.add(i1);
			carOrder.setCars(cars);
			carOrder.setOrderDate(date);
			// The full path to the resourse
			url = new URL(resourceBaseURL + requestedOrder);
			// Open a new connection to the web service
			con = (HttpURLConnection) url.openConnection();
			System.out.println(url);
			// Needed for the post method
			con.setDoOutput(true);
			// Used for redirects allow
			con.setInstanceFollowRedirects(false);
			//Set he http method type
			con.setRequestMethod("POST");
			// Xml payload
			con.setRequestProperty("Content-Type", "application/xml");
			JAXBContext jc = JAXBContext.newInstance(CarOrder.class);
			//Marshall the data to xml
			Marshaller m1 = jc.createMarshaller();

			m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			//write to a new file overwrite if exists
			m1.marshal(carOrder, new FileWriter("test.xml"));
			// m1.marshal(car, System.out);
			OutputStream os = con.getOutputStream();
			//Transformer factory use to read the xml file
			// Adpated from: https://stackoverflow.com/questions/20143405/how-to-read-a-big-xml-file-in-java-and-spilt-it-into-small-xml-files-based-on-ta
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			FileReader fileReader = new FileReader("test.xml");
			StreamSource source = new StreamSource(fileReader);
			StreamResult result = new StreamResult(os);
			transformer.transform(source, result);

			os.flush();
			// get the response code for the request
			con.getResponseCode();
			// Set the sttributes for access on jsp page
			request.setAttribute("requested", carOrder);
			// Forward to the view boooking page
			String nextJSP = "/ViewBooking.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			dispatcher.forward(request, response);
			//Close the connection
			con.disconnect();

		} catch (IOException | TransformerException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	/*
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// System.out.println(request.getParameter("name"));
		ArrayList<String> paramList = new ArrayList<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));

		String data = br.readLine();
		br.close();
		//This is the only way I could figure out how to get multiple from inputs to variables.
		String[] params = data.split("&");
		Map<String, String> map = new HashMap<String, String>();
		for (String param : params) {
			String name = param.split("=")[0];
			String value = param.split("=")[1];
			map.put(name, value);
			paramList.add(value);

		}
		//String.relace is used to remove the + sign from the put request parameters
		//Adapted from: https://stackoverflow.com/questions/18717557/remove-plus-sign-in-url-query-string
		String orderNumber = paramList.get(0);
		String name = paramList.get(1).replace("+", " ");
		String date = paramList.get(2);
		String country = paramList.get(3).replace("+", " ");
		String street = paramList.get(4).replace("+", " ");
		String city = paramList.get(5).replace("+", " ");
		String model = paramList.get(6).replace("+", " ");
		int quantity = Integer.parseInt(paramList.get(7));
		BigDecimal price = new BigDecimal(paramList.get(8));
		String resourceBaseURL = "http://localhost:8080/DS_Project/webapi/orders/";
		
		URL url;
		HttpURLConnection con;
		String resultInXml = "";

		// try to create a connection and request XML format
		try {

			ObjectFactory objFactory = new ObjectFactory();

			CarOrder carOrder = objFactory.createCarOrder();
			carOrder.setOrderNumber(orderNumber);
			Customer cust = new Customer();
			cust.setName(name);
			cust.setCountry(country);
			cust.setCity(city);
			cust.setStreet(street);
			carOrder.setBillTo(cust);
			Car car = new Car();
			car.setCarModel(model);
			car.setQuantity(quantity);
			car.setPrice(price);
			car.setOrderDate(date);
			carOrder.setCar(car);
			carOrder.setOrderDate(date);
			
			url = new URL(resourceBaseURL + orderNumber);
			
			con = (HttpURLConnection) url.openConnection();
			System.out.println(url);

			con.setDoOutput(true);
			con.setInstanceFollowRedirects(false);
			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type", "application/xml");
			JAXBContext jc = JAXBContext.newInstance(CarOrder.class);
			Marshaller m1 = jc.createMarshaller();

			m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m1.marshal(carOrder, new FileWriter("test.xml"));
			// m1.marshal(carOrder, System.out);
			OutputStream os = con.getOutputStream();

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			FileReader fileReader = new FileReader("test.xml");
			StreamSource source = new StreamSource(fileReader);
			StreamResult result = new StreamResult(os);
			transformer.transform(source, result);

			os.flush();

			con.getResponseCode();

			con.disconnect();

		} catch (IOException | TransformerException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Needed for deleting by http
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		// read the url value there is only one value so no need to split the query.
		String requestedOrder = br.readLine();
		br.close();
		//The url to the web service
		String resourceBaseURL = "http://localhost:8080/DS_Project/webapi/orders/";

		URL url;
		HttpURLConnection con;
		String resultInXml = "";

		// try to create a connection and request XML format
		try {

			ObjectFactory objFactory = new ObjectFactory();
			// Create a new carORder object
			CarOrder carOrder = objFactory.createCarOrder();
			// Ste the order number to delete 
			carOrder.setOrderNumber(requestedOrder);
			// The full url to the web service
			url = new URL(resourceBaseURL + requestedOrder);
			// Open the connection
			con = (HttpURLConnection) url.openConnection();
			// For testing
			System.out.println("DoDelete");
			// Needed for doDelete in servlet
			con.setDoOutput(true);
			con.setInstanceFollowRedirects(false);
			//Set the request method to be accessed in the web service
			con.setRequestMethod("DELETE");
			// Xml payload
			con.setRequestProperty("Content-Type", "application/xml");
			JAXBContext jc = JAXBContext.newInstance(CarOrder.class);
			// MArshall the data to xml
			Marshaller m1 = jc.createMarshaller();

			m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m1.marshal(carOrder, new FileWriter("test.xml"));
			// m1.marshal(car, System.out);
			OutputStream os = con.getOutputStream();
			//Transformer factory use to read the xml file
			// Adpated from: https://stackoverflow.com/questions/20143405/how-to-read-a-big-xml-file-in-java-and-spilt-it-into-small-xml-files-based-on-ta
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			FileReader fileReader = new FileReader("test.xml");
			StreamSource source = new StreamSource(fileReader);
			StreamResult result = new StreamResult(os);
			transformer.transform(source, result);

			os.flush();

			con.getResponseCode();

			con.disconnect();

		} catch (IOException | TransformerException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
