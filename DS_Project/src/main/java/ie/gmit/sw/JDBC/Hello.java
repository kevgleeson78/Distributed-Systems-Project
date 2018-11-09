package ie.gmit.sw.JDBC;

import java.rmi.Remote;
import java.util.List;

import ie.gmit.sw.DS_Project.CarOrder;


public interface Hello extends Remote{
	public List<CarOrder> getName() throws Exception;  
}
