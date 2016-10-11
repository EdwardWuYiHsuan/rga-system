package dao;

import java.util.List;

import controllers.exception.ApiException;
import models.Customer;

public interface CustomerDao {

	public Customer getCustomer(String customerId) throws ApiException;
	
	public Customer getCustomerByEmail(String email) throws ApiException;
	
	public List<Customer> getAllCustomers();
	
	public void createCustomer(String name, String email, String pwd, String phone)  throws ApiException;
	
	public void updateCustomer(String customerId, String name, String email, String pwd , String phone) throws ApiException;
	
	public void deleteCustomer(String customerId);
	
}
