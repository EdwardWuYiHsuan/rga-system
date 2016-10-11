package dao;

import controllers.exception.ApiException;
import models.Token;

public interface TokenDao {
	
	public Token getToken(String tokenId) throws ApiException;
	
	public Token createToken(String customerId, String customerName, String remoteIp, String userAgent) throws ApiException;
	
	public void deleteToken(String tokenId);
	
}
