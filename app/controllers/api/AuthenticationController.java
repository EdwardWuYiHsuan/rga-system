package controllers.api;

import controllers.exception.ApiException;
import controllers.response.DefaultResponse;
import models.Customer;
import models.Token;
import play.mvc.Http;
import rga.APICode;
import rga.utils.Util;

public class AuthenticationController extends APIController {

	
	public static void login() 
	{
		try {
			String email = readApiParameter("email", true);
			String password = readApiParameter("password", true);
			
			Customer customer = Customer.find("email", email).get();
			if (null == customer)
				throw new ApiException(APICode.LoginFailed, "login-failed");
			
			String salt = customer.getCreationTimestamp();
			password = Util.sha256InBase64(password + salt);
			if (!customer.getPassword().equals(password))
				throw new ApiException(APICode.LoginFailed, "login-failed");
			
			String remoteIp = request.remoteAddress; 
			String userAgent = Http.Request.current().headers.get("user-agent").toString().toLowerCase();
			
			Token token = new Token();
			token.setUserId(customer.getIdAsStr());
			token.setUserName(customer.getName());
			token.setRemoteIP(remoteIp);
			token.setUserAgent(userAgent);
			token.save();
			
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
			
			Token token = Token.findById(tokenId);
			if (null != token)
				token.delete();
			
			session.remove("tokenId");
			
			renderJSON(new DefaultResponse());
		} catch (Exception e) {
			respondError(e);
		}
	}
	
}
