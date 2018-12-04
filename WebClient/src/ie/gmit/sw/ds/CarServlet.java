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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
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

import ie.gmit.sw.DS_Project.Car;
import ie.gmit.sw.DS_Project.CarOrder;
import ie.gmit.sw.DS_Project.Customer;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resourceBaseURL = "http://localhost:8080/DS_Project/webapi/orders/";
		String requestedOrder =  request.getParameter("param1");
		URL url;
		HttpURLConnection con;
		String resultInXml = "";
			
		// try to create a connection and request XML format
		try {

			url = new URL(resourceBaseURL + requestedOrder);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept", "application/xml");
			InputStream in = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			resultInXml = br.lines().collect(Collectors.joining());
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			StringReader reader = new StringReader(resultInXml);
			CarOrder requested = (CarOrder) unmarshaller.unmarshal(reader);	

			request.setAttribute("requested", requested);
			
			String nextJSP = "/ViewBookings.jsp";
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		
		//System.out.println(request.getParameter("name"));
		String resourceBaseURL = "http://localhost:8080/DS_Project/webapi/orders/";
		String requestedOrder = request.getParameter("orderNumber");
		String custName = request.getParameter("name");
		String date = request.getParameter("date");
		String country = request.getParameter("country");
		String street = request.getParameter("street");
		String city = request.getParameter("city");
		String model = request.getParameter("carModel");
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		BigDecimal price = new BigDecimal(request.getParameter("price"));
		URL url;		
		HttpURLConnection con;
		String resultInXml = "";
		

		// try to create a connection and request XML format
		try {
			
			ObjectFactory objFactory = new ObjectFactory();
			
			CarOrder carOrder = objFactory.createCarOrder();
			carOrder.setOrderNumber(requestedOrder);
			Customer cust = new Customer();
			cust.setName(custName);
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
			
			
			url = new URL(resourceBaseURL + requestedOrder);
			con = (HttpURLConnection) url.openConnection();
			System.out.println(url);
			
			
			con.setDoOutput(true);
			con.setInstanceFollowRedirects(false);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/xml");
			JAXBContext jc = JAXBContext.newInstance(CarOrder.class);
			Marshaller m1 = jc.createMarshaller();
			
			m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m1.marshal(carOrder, new FileWriter("test.xml"));
			//m1.marshal(car, System.out);
			OutputStream os = con.getOutputStream();

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			FileReader fileReader = new FileReader("test.xml");
			StreamSource source = new StreamSource(fileReader);
			StreamResult result = new StreamResult(os);
			transformer.transform(source, result);

			os.flush();
			con.getResponseCode();
			request.setAttribute("requested", carOrder);
			String nextJSP = "/ViewBookings.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			dispatcher.forward(request, response);
			con.disconnect();
			
		} catch (IOException | TransformerException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println(request.getParameter("name"));
				BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));

				String requestedOrder = br.readLine();
				br.close();
				String resourceBaseURL = "http://localhost:8080/DS_Project/webapi/orders/";
				
				URL url;		
				HttpURLConnection con;
				String resultInXml = "";
				

				// try to create a connection and request XML format
				try {
					
					ObjectFactory objFactory = new ObjectFactory();

					CarOrder carOrder = objFactory.createCarOrder();
					carOrder.setOrderNumber(requestedOrder);
					url = new URL(resourceBaseURL + requestedOrder);
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
					//m1.marshal(car, System.out);
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

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));

		String requestedOrder = br.readLine();
		br.close();
		String resourceBaseURL = "http://localhost:8080/DS_Project/webapi/orders/";
		
		URL url;		
		HttpURLConnection con;
		String resultInXml = "";
		

		// try to create a connection and request XML format
		try {
			
			ObjectFactory objFactory = new ObjectFactory();

			CarOrder carOrder = objFactory.createCarOrder();
			carOrder.setOrderNumber(requestedOrder);
			url = new URL(resourceBaseURL + requestedOrder);
			con = (HttpURLConnection) url.openConnection();
			System.out.println("DoDelete");
			
			
			con.setDoOutput(true);
			con.setInstanceFollowRedirects(false);
			con.setRequestMethod("DELETE");
			con.setRequestProperty("Content-Type", "application/xml");
			JAXBContext jc = JAXBContext.newInstance(CarOrder.class);
			Marshaller m1 = jc.createMarshaller();
			
			m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m1.marshal(carOrder, new FileWriter("test.xml"));
			//m1.marshal(car, System.out);
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

}
