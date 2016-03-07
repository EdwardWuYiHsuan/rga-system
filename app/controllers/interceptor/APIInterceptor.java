package controllers.interceptor;
import controllers.api.APIController;
import controllers.exception.ApiException;
import models.Token;
import play.mvc.Before;
import rga.APICode;
import rga.utils.Util;

public class APIInterceptor extends APIController {

	@Before(priority = 1)
	static void validateToken() {
		try {
			String key = params.get("tokenId");
			if (Util.isNullOrEmpty(key)) {
				params.put("tokenId", session.get("tokenId"));
			}
			
			String tokenId = readApiParameter("tokenId", true);
			
			Token token = Token.findById(tokenId);
			if (null == token)
				throw new ApiException(APICode.InvalidAccess, "invalid-access");
			
			session.put("tokenId", token.getIdAsStr());
		} catch (Exception e) {
			respondError(e);
		}
	}
}
