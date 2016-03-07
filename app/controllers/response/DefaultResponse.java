package controllers.response;

import controllers.exception.ApiException;
import rga.APICode;

public class DefaultResponse {

	private Result result;
	private int code;
	private Object data;
	private String reason;
	
	public DefaultResponse() {
		this.result = Result.ok;
		this.code = APICode.OK.getCode();
	}
	
	public DefaultResponse(Result result, Throwable ex) {
		this.result = result;
		this.reason = ex.getMessage();
		
		if (ex instanceof ApiException)
			this.code = ((ApiException) ex).getCode();
		else 
			this.code = APICode.Other.getCode();
	}
	
	public DefaultResponse setData(Object data) {
		this.data = data;
		return this;
	}
	
}
