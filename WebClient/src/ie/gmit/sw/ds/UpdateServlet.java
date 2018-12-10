package ie.gmit.sw.ds;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
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
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    // Marshalling and unmarshalling in the below methods were adapted from the file(JAXBPOExample)
 	// Available at https://learnonline.gmit.ie/course/view.php?id=590
    
    // THis method is used to populate data fron an existing order to the form for updating
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//The url to the resource
		String resourceBaseURL = "http://localhost:8080/DS_Project/webapi/orders/";
		//The parameter passed to be added to the end of the above url
		String requestedOrder = request.getParameter("orderNumber");
		URL url;
		HttpURLConnection con;
		String resultInXml = "";

		// try to create a connection and request XML format
		try {
			//The full URL for the resource of the web service
			url = new URL(resourceBaseURL + requestedOrder);
			// Open a connection to the resource
			con = (HttpURLConnection) url.openConnection();
			// The http method that will be used in the resource.
			con.setRequestMethod("GET");
			//Set the request type
			con.setRequestProperty("Accept", "application/xml");
			// Get the data input stream
			InputStream in = con.getInputStream();
			// Buffered reader to read the xml
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			//Store the xml in the global variable resultInXml as a continious string
			resultInXml = br.lines().collect(Collectors.joining());
			// Get a new instance of the ObjectFactory
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			// Unmarshall the xml data
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			// read the xml data
			StringReader reader = new StringReader(resultInXml);
			//CAst the data to a new carorder
			CarOrder requested = (CarOrder) unmarshaller.unmarshal(reader);
			// Set attributes for access in the jsp page or on the web service
			request.setAttribute("requested", requested);
			// Set the forward to update booking page once the request has been completed.
			String nextJSP = "/UpdateBooking.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			dispatcher.forward(request, response);
			con.disconnect();

		} catch (IOException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// print out the results of the requests
		System.out.println("****** Result in XML *****");
		System.out.println(resultInXml);
		System.out.println();
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	// For updating a order first to the web service then to the database
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// System.out.println(request.getParameter("name"));
		//PAram list is used to hold all of the values that have been passed through the url
				ArrayList<String> paramList = new ArrayList<>();
				// Buffered reader needed to rean the url parameters.
				BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
				// Store the url in a continuous string
				String data = br.readLine();
				// Close the buffered reader.
				br.close();
				//This is the only way I could figure out how to get multiple from inputs to variables.
				// Adapted from : https://stackoverflow.com/questions/855835/servlet-parameters-and-doput
				// Split the string at '&' and store it in an array
				String[] params = data.split("&");
				// Create a new map for the data to be stored
				Map<String, String> map = new HashMap<String, String>();
				// Loop over the params array ansd split again at '=' sign
				for (String param : params) {
					String name = param.split("=")[0];
					String value = param.split("=")[1];
					//put the values into the map
					map.put(name, value);
					// Add the value to the array
					paramList.add(value);
					//For testing
					//System.out.println(paramList);
				}
				//String.relace is used to remove the + sign from the put request parameters
				//Adapted from: https://stackoverflow.com/questions/18717557/remove-plus-sign-in-url-query-string
				
				// Each of the parameters are noe in order and can be accessed by their position in the array
				String orderNumber = paramList.get(0);
				// Strin greplace is suse dto remove the '+' sign that is placed by the url for spaces in a string from a form
				String name = paramList.get(1).replace("+", " ");
				String date = paramList.get(2);
				String country = paramList.get(3).replace("+", " ");
				String street = paramList.get(4).replace("+", " ");
				String city = paramList.get(5).replace("+", " ");
				String model = paramList.get(6).replace("+", " ");
				// Multiply the cost of the car by the amount of cars ordered to get the final price
				BigDecimal price = new BigDecimal(paramList.get(7));
				BigDecimal quantityBD = new BigDecimal(paramList.get(8));
				int quantity = quantityBD.intValue();
				BigDecimal finalPrice = price.multiply(quantityBD);
				// The url to the web service
				String resourceBaseURL = "http://localhost:8080/DS_Project/webapi/orders/";
				
				URL url;
				HttpURLConnection con;
				String resultInXml = "";

				// try to create a connection and request XML format
				try {
					// Set all of the values for the updated order
					ObjectFactory objFactory = new ObjectFactory();

					CarOrder carOrder = objFactory.createCarOrder();
					carOrder.setOrderNumber(orderNumber);
					Address cust = new Address();
					cust.setName(name);
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
					// The full url to the web service
					url = new URL(resourceBaseURL + orderNumber);
					// Open a new connection
					con = (HttpURLConnection) url.openConnection();
					System.out.println(url);
					// Needed fror put 
					con.setDoOutput(true);
					con.setInstanceFollowRedirects(false);
					// set the request method
					con.setRequestMethod("PUT");
					// Xml payload
					con.setRequestProperty("Content-Type", "application/xml");
					JAXBContext jc = JAXBContext.newInstance(CarOrder.class);
					// Marshal the data
					Marshaller m1 = jc.createMarshaller();

					m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
					// Marshal the object to the xnl file
					m1.marshal(carOrder, new FileWriter("test.xml"));
					// m1.marshal(carOrder, System.out);
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
