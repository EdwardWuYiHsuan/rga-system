package controllers.exception;

import controllers.api.APICode;

public class ApiException extends Exception {

	private APICode code;
	
	public ApiException(APICode code) {
		this.code = code;
	}
	
	public ApiException(APICode code, String message) {
		super(message);
		this.code = code;
	}
	
	public ApiException(APICode code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
	
	public ApiException(APICode code, Throwable cause) {
		super(cause);
		this.code = code;
	}
	
	public int getCode() {
		return this.code.getCode();
	}
	
}
