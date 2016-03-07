package models;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Indexed;
import com.google.gson.annotations.Expose;

import play.modules.morphia.Model;
import rga.utils.Util;

@Entity
public class Customer extends Model {

	private String name;
	@Indexed(unique=true)
	private String email;
	@Expose(serialize=false)
	private String password;
	private String phone;
	private String creationTimestamp;
	
	public Customer() {
		this.creationTimestamp = DateTime.now(DateTimeZone.UTC).toString("dd:MM:yyyy");
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		if (Util.isNullOrEmpty(name))
			throw new IllegalArgumentException("invalid-name");
			
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		if (!Util.validateEmail(email))
			throw new IllegalArgumentException("invalid-email");
		
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		if (Util.isNullOrEmpty(password))
			throw new IllegalArgumentException("invalid-password");
		
		String salt = this.creationTimestamp;
		this.password = Util.sha256InBase64(password + salt);
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		if (Util.isNullOrEmpty(phone))
			throw new IllegalArgumentException("invalid-phone");
		
		this.phone = phone;
	}

	public String getCreationTimestamp() {
		return creationTimestamp;
	}
	
}
