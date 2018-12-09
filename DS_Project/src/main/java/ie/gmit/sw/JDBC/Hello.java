package ie.gmit.sw.JDBC;

import java.rmi.Remote;
import java.sql.SQLException;
import java.util.List;

import ie.gmit.sw.DS_Project.CarOrder;


public interface Hello extends Remote{
	public List<CarOrder> getOrder() throws Exception;
	
	public void createOrder(CarOrder co) throws  Exception;
//	public void deleteOrder(CarOrder co)throws  Exception;
	public void updateOrder(CarOrder co) throws Exception;
}
