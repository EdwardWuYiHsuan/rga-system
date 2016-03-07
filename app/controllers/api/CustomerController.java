package controllers.api;

import java.util.List;

import com.mongodb.MongoException.DuplicateKey;

import controllers.exception.ApiException;
import controllers.interceptor.APIInterceptor;
import controllers.response.DefaultResponse;
import controllers.response.Result;
import models.Customer;
import play.mvc.With;
import rga.APICode;
import rga.utils.Util;

@With(APIInterceptor.class)
public class CustomerController extends APIController {

	public static void createCustomer() 
	{
		try {
			String name = readApiParameter("name", true);
			String email = readApiParameter("email", true);
			String password = readApiParameter("password", true);
			String phone = readApiParameter("phone", true);
			
			try {
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(email);
				customer.setPassword(password);
				customer.setPhone(phone);
				customer.save();
			} catch (IllegalArgumentException e) {
				throw new ApiException(APICode.InvalidParameter, e.getMessage());
			} catch (DuplicateKey e) {
				throw new ApiException(APICode.UniqueParameter, "email-already-exist");
			}
			
			renderJSON(new DefaultResponse());
		} catch (Exception e) {
			respondError(e);
		}
	}
	
	public static void updateCustomer() 
	{
		try {
			String userId = readApiParameter("userId", true);
			String name = readApiParameter("name", false);
			String email = readApiParameter("email", false);
			String password = readApiParameter("password", false);
			String phone = readApiParameter("phone", false);
			
			Customer customer = Customer.findById(userId);
			if (null == customer) {
				throw new ApiException(APICode.InvalidParameter, "invalid-userId");
			}
			
			try {
				if (!Util.isNullOrEmpty(name))
					customer.setName(name);
				if (!Util.isNullOrEmpty(email))
					customer.setEmail(email);
				if (!Util.isNullOrEmpty(password))
					customer.setPassword(password);
				if (!Util.isNullOrEmpty(phone));
					customer.setPhone(phone);
					
				customer.save();
			} catch (IllegalArgumentException e) {
				throw new ApiException(APICode.InvalidParameter, e.getMessage());
			} catch (DuplicateKey e) {
				throw new ApiException(APICode.UniqueParameter, "email-already-exist");
			}
			
			renderJSON(new DefaultResponse());
		} catch (Exception e) {
			respondError(e);
		}
	}
	
	public static void deleteCustomer() 
	{
		try {
			String userId = readApiParameter("userId", true);
			
			Customer customer = Customer.findById(userId);
			if (null == customer) {
				throw new ApiException(APICode.InvalidParameter, "invalid-userId");
			}
			
			customer.delete();
			renderJSON(new DefaultResponse());
		} catch (Exception e) {
			respondError(e);
		}
	}
	
	public static void getCustomer() 
	{
		try {
			String userId = readApiParameter("userId", true);
			
			Customer customer = Customer.findById(userId);
			if (null == customer) {
				throw new ApiException(APICode.InvalidParameter, "invalid-userId");
			}
			
			renderJSON(new DefaultResponse().setData(customer));
		} catch (Exception e) {
			respondError(e);
		}
	}
	
	public static void getAllCustomer() 
	{
		try {
			List<Customer> customerList = Customer.findAll();
			
			renderJSON(new DefaultResponse().setData(customerList));
		} catch (Exception e) {
			respondError(e);
		}
	}
	
	
}
