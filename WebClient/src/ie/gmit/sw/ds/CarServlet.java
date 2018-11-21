package ie.gmit.sw.ds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
import javax.xml.bind.Unmarshaller;

import ie.gmit.sw.DS_Project.CarOrder;

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
			System.out.println(requestedOrder);
		// try to create a connection and request XML format
		try {

			url = new URL(resourceBaseURL + requestedOrder);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept", "application/xml");
			InputStream in = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			resultInXml = br.lines().collect(Collectors.joining());
			JAXBContext jaxbContext = JAXBContext.newInstance(CarOrder.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			StringReader reader = new StringReader(resultInXml);
			CarOrder requested = (CarOrder) unmarshaller.unmarshal(reader);	

			request.setAttribute("requested", requested);
			
			String nextJSP = "/DeleteBooking.jsp";
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
