package controllers.interceptor;
import controllers.api.APICode;
import controllers.api.APIController;
import controllers.exception.ApiException;
import models.Token;
import play.mvc.Before;
import utils.Util;

public class APIInterceptor extends APIController {

	@Before(priority = 1)
	static void validateToken() {
		try {
			String tokenId = params.get("tokenId");
			if (Util.isNullOrEmpty(tokenId)) {
				tokenId = session.get("tokenId");
			}
			
			Token token = Token.findById(tokenId);
			if (null == token)
				throw new ApiException(APICode.InvalidAccess, "invalid-access");
			
			session.put("tokenId", token.getIdAsStr());
		} catch (Exception e) {
			respondError(e);
		}
	}
}
