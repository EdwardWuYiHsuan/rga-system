package dao;

import controllers.api.APICode;
import controllers.exception.ApiException;
import models.Token;
import play.modules.morphia.Model;

class TokenDaoImpl extends BaseDao implements TokenDao {

	@Override
	public Token getToken(String tokenId) throws ApiException 
	{
		Token token = Token.findById(tokenId);
		if (null == token)
			throw new ApiException(APICode.InvalidParameter, "invalid-token-id");
		
		return token;
	}

	@Override
	public Token createToken(String customerId, String customerName, String remoteIp, String userAgent) throws ApiException 
	{
		Token token = new Token();
		token.setUserId(customerId);
		token.setUserName(customerName);
		token.setRemoteIP(remoteIp);
		token.setUserAgent(userAgent);
		token.save();

		return token;
	}
	
	@Override
	public void deleteToken(String tokenId) 
	{
		Token token = Token.findById(tokenId);
		if (null != token)
			token.delete();
	}
	

}
