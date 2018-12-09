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
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resourceBaseURL = "http://localhost:8080/DS_Project/webapi/orders/";
		String requestedOrder = request.getParameter("orderNumber");
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
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
					i1.setPrice(price);
					i1.setBookingDate(date);
					col.add(i1);
					carOrder.setCars(cars);
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

}