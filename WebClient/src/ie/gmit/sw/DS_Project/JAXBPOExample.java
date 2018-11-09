package ie.gmit.sw.DS_Project;
import java.io.*;
import java.util.*;
import java.math.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamSource;

public class JAXBPOExample {
	public static void main(String[] args) throws Exception{
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date(System.nanoTime()));
		XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);


		JAXBContext jc = JAXBContext.newInstance("ie.gmit.sw.DS_Project");
		ObjectFactory objFactory = new ObjectFactory();

		CarOrder po = objFactory.createCarOrder();
		po.setOrderNumber("55522-BABA");
		po.setOrderDate(date);

		Customer shipTo = new Customer();
		shipTo.setName("John Doe");
		shipTo.setStreet("123 Castle Road");
		shipTo.setCity("Oranmore");
		
		shipTo.setCountry(Country.IRELAND);
		
		po.setBillTo(shipTo);

		Car items = new Car();
		po.setCar(items);
		List<Car.Item> col = items.getItem();
		Car.Item i1 = new Car.Item();
		i1.setCarModel("123ABC");
		
		i1.setQuantity(1);
		i1.setPrice(new BigDecimal("250.00"));
		i1.setOrderDate(date);
		col.add(i1);



		// Marshal the PurchaseOrder into XML
		System.out.println("\n\n######### XML Format #########");
		Marshaller m1 = jc.createMarshaller();
		m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m1.marshal(po, new FileWriter("order.xml"));
		m1.marshal(po, System.out);

		// Marshal the PurchaseOrder in JSON
	/*	System.out.println("\n\n######### JSON Format #########");
		Marshaller m2 = jc.createMarshaller();
		m2.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m2.setProperty("eclipselink.media-type", "application/json");
		m2.setProperty("eclipselink.json.include-root", false);
		m2.marshal(po, new FileWriter("order.json"));
		m2.marshal(po, System.out);
*/
		//Unmarshal the XML into a PurchaseOrder Object
		File file1 = new File("order.xml");
		Unmarshaller um1 = jc.createUnmarshaller();
		StreamSource source1 = new StreamSource(file1);
		JAXBElement<CarOrder> poElement1 = um1.unmarshal(source1, CarOrder.class);
		CarOrder poFromXml = (CarOrder) poElement1.getValue();
		System.out.println("\n\n######### XML Unmarshalling #########\n" + poFromXml.getOrderNumber());
/*
		//Unmarshal the JSON into a PurchaseOrder Object
		File file2 = new File("order.json");
		Unmarshaller um2 = jc.createUnmarshaller();
		um2.setProperty("eclipselink.media-type", "application/json");
		um2.setProperty("eclipselink.json.include-root", false);
		StreamSource source2 = new StreamSource(file2);
		JAXBElement<CarOrder> poElement2 = um2.unmarshal(source2, CarOrder.class);
		CarOrder poFromJson = (CarOrder) poElement2.getValue();
		System.out.println("\n\n######### JSON Unmarshalling #########\n" + poFromJson.getOrderNumber());
*/
	}
}