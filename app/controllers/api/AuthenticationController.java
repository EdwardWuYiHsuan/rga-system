package controllers.api;

import controllers.exception.ApiException;
import controllers.response.DefaultResponse;
import dao.CustomerDao;
import dao.DaoManager;
import dao.TokenDao;
import models.Customer;
import models.Token;
import play.mvc.Http;
import utils.Util;

public class AuthenticationController extends APIController {

	
	public static void login() 
	{
		try {
			String email = readApiParameter("email", true);
			String password = readApiParameter("password", true);
			
			CustomerDao customerDao = DaoManager.getDao(DaoManager.CUSTOMER);
			Customer customer = customerDao.getCustomerByEmail(email);
			
			if (!customer.login(password))
				throw new ApiException(APICode.LoginFailed, "login-failed");
			
			String remoteIp = request.remoteAddress; 
			String userAgent = Http.Request.current().headers.get("user-agent").toString().toLowerCase();
			
			TokenDao tokenDao = DaoManager.getDao(DaoManager.TOKEN);
			Token token = tokenDao.createToken(customer.getIdAsStr(), customer.getName(), remoteIp, userAgent);
			session.put("tokenId", token.getIdAsStr());
			
			renderJSON(new DefaultResponse().setData(token));
		} catch (Exception e) {
			respondError(e);
		}
	}
	
	public static void logout() 
	{
		try {
			String id = params.get("tokenId");
			if (Util.isNullOrEmpty(id)) {
				params.put("tokenId", session.get("tokenId"));
			}
			
			String tokenId = readApiParameter("tokenId", true);
			
			TokenDao tokenDao = DaoManager.getDao(DaoManager.TOKEN);
			tokenDao.deleteToken(tokenId);
			session.remove("tokenId");
			
			renderJSON(new DefaultResponse());
		} catch (Exception e) {
			respondError(e);
		}
	}
	
}
