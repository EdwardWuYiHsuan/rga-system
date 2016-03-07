package models;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Indexed;

import play.modules.morphia.Model;

@Entity
public class Token extends Model {

	@Indexed
	private String userId;
	private String userName;
	private String remoteIP;
	private String userAgent;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getRemoteIP() {
		return remoteIP;
	}
	
	public void setRemoteIP(String remoteIP) {
		this.remoteIP = remoteIP;
	}
	
	public String getUserAgent() {
		return userAgent;
	}
	
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
}
