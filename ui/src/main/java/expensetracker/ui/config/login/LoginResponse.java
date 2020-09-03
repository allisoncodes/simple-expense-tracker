package expensetracker.ui.config.login;

import java.util.List;

public class LoginResponse {

	String access_token;
	String id_token;
	String token_type;
	int expires_in;
	String scope;
	String refresh_token;
	String jti;

	public LoginResponse() {}

	public LoginResponse(String access_token, String id_token, String token_type, int expires_in, String scope, String refresh_token, String jti) {
		this.access_token = access_token;
		this.id_token = id_token;
		this.token_type = token_type;
		this.expires_in = expires_in;
		this.scope = scope;
		this.refresh_token = refresh_token;
		this.jti = jti;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getId_token() {
		return id_token;
	}

	public void setId_token(String id_token) {
		this.id_token = id_token;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getJti() {
		return jti;
	}

	public void setJti(String jti) {
		this.jti = jti;
	}
	//	private String jwt;
//
//	private String username;
//
//	private List<String> roles;
//
//	public LoginResponse() {
//	}
//
//	public LoginResponse(String jwt, String username, List<String> roles) {
//		this.jwt = jwt;
//		this.username = username;
//		this.roles = roles;
//	}
//
//	public String getJwt() {
//		return jwt;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public List<String> getRoles() {
//		return roles;
//	}

}
