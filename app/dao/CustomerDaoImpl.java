package dao;

import java.util.List;

import com.mongodb.MongoException.DuplicateKey;

import controllers.api.APICode;
import controllers.exception.ApiException;
import models.Customer;
import utils.Util;

class CustomerDaoImpl extends BaseDao implements CustomerDao {

	@Override
	public Customer getCustomer(String customerId) throws ApiException
	{
		Customer customer = Customer.findById(customerId);
		if (null == customer)
			throw new ApiException(APICode.InvalidParameter, "invalid-customer-id");
		
		return customer;
	}
	
	@Override
	public Customer getCustomerByEmail(String email) throws ApiException 
	{
		Customer customer = Customer.find("email", email).get();
		if (null == customer)
			throw new ApiException(APICode.InvalidParameter, "invalid-customer-email");
		
		return customer;
	}

	@Override
	public List<Customer> getAllCustomers() 
	{
		return Customer.findAll();
	}

	@Override
	public void createCustomer(String name, String email, String pwd, String phone) throws ApiException 
	{
		try {
			Customer customer = new Customer();
			customer.setName(name);
			customer.setEmail(email);
			customer.setPassword(pwd);
			customer.setPhone(phone);
			customer.save();
		} catch (IllegalArgumentException e) {
			throw new ApiException(APICode.InvalidParameter, e.getMessage());
		} catch (DuplicateKey e) {
			throw new ApiException(APICode.UniqueParameter, "email-already-exist");
		}
	}

	@Override
	public void updateCustomer(String customerId, String name, String email, String pwd, String phone) throws ApiException 
	{
		Customer customer = this.getCustomer(customerId);
		
		try {
			if (!Util.isNullOrEmpty(name))
				customer.setName(name);
			if (!Util.isNullOrEmpty(email))
				customer.setEmail(email);
			if (!Util.isNullOrEmpty(pwd))
				customer.setPassword(pwd);
			if (!Util.isNullOrEmpty(phone));
				customer.setPhone(phone);
				
			customer.save();
		} catch (IllegalArgumentException e) {
			throw new ApiException(APICode.InvalidParameter, e.getMessage());
		} catch (DuplicateKey e) {
			throw new ApiException(APICode.UniqueParameter, "email-already-exist");
		}
	}

	@Override
	public void deleteCustomer(String customerId)
	{
		Customer customer = Customer.findById(customerId);
		if (null != customer)
			customer.delete();
	}
	
	
}
