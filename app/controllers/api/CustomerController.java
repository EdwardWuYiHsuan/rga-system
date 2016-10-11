package controllers.api;

import java.util.List;

import com.mongodb.MongoException.DuplicateKey;

import controllers.exception.ApiException;
import controllers.interceptor.APIInterceptor;
import controllers.response.DefaultResponse;
import controllers.response.Result;
import dao.CustomerDao;
import dao.DaoManager;
import models.Customer;
import play.mvc.With;
import utils.Util;

@With(APIInterceptor.class)
public class CustomerController extends APIController {

	public static void createCustomer() 
	{
		try {
			String name = readApiParameter("name", true);
			String email = readApiParameter("email", true);
			String password = readApiParameter("password", true);
			String phone = readApiParameter("phone", true);
			
			CustomerDao customerDao = DaoManager.getDao(DaoManager.CUSTOMER);
			customerDao.createCustomer(name, email, password, phone);
			
			renderJSON(new DefaultResponse());
		} catch (Exception e) {
			respondError(e);
		}
	}
	
	public static void updateCustomer() 
	{
		try {
			String customerId = readApiParameter("customerId", true);
			String name = readApiParameter("name", false);
			String email = readApiParameter("email", false);
			String password = readApiParameter("password", false);
			String phone = readApiParameter("phone", false);
			
			CustomerDao customerDao = DaoManager.getDao(DaoManager.CUSTOMER);
			customerDao.updateCustomer(customerId, name, email, password, phone);
			
			renderJSON(new DefaultResponse());
		} catch (Exception e) {
			respondError(e);
		}
	}
	
	public static void deleteCustomer() 
	{
		try {
			String customerId = readApiParameter("customerId", true);
			
			CustomerDao customerDao = DaoManager.getDao(DaoManager.CUSTOMER);
			customerDao.deleteCustomer(customerId);
			
			renderJSON(new DefaultResponse());
		} catch (Exception e) {
			respondError(e);
		}
	}
	
	public static void getCustomer() 
	{
		try {
			String customerId = readApiParameter("customerId", true);
			
			CustomerDao customerDao = DaoManager.getDao(DaoManager.CUSTOMER);
			Customer customer = customerDao.getCustomer(customerId);
			
			renderJSON(new DefaultResponse().setData(customer));
		} catch (Exception e) {
			respondError(e);
		}
	}
	
	public static void getAllCustomer() 
	{
		try {
			CustomerDao customerDao = DaoManager.getDao(DaoManager.CUSTOMER);
			List<Customer> customerList = customerDao.getAllCustomers();
			
			renderJSON(new DefaultResponse().setData(customerList));
		} catch (Exception e) {
			respondError(e);
		}
	}
	
	
}
