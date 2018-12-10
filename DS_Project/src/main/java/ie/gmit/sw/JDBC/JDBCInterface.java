package ie.gmit.sw.JDBC;

import java.rmi.Remote;
import java.sql.SQLException;
import java.util.List;

import ie.gmit.sw.DS_Project.CarOrder;
/*
 * Adapted from the lab: Java RMI Labs (weeks 5 & 6)
 * Available @ https://learnonline.gmit.ie/course/view.php?id=590
 * This file is not used for this application it is her for reference purpose only.
 */

// An interface to allow for remote method calls to the implementatino of this interface
public interface JDBCInterface extends Remote{
	//MEthod to retrieve and order form the database
	public List<CarOrder> getOrder() throws Exception;
	// To create a new row in the database
	public void createOrder(CarOrder co) throws  Exception;
	
	//To delete a row from the database
	public void deleteOrder(CarOrder co)throws  Exception;
	// To update a row in the database.
	public void updateOrder(CarOrder co) throws Exception;
}
