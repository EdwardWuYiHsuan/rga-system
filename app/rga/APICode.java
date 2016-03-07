package rga;

public enum APICode {

	OK(00),
	MissingParameter(101),
	UniqueParameter(102),
	InvalidParameter(103),
	LoginFailed(104),
	InvalidAccess(105),
	Other(999);
	
	private int code;
	private APICode(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
}
