package com.model;

/*import lombok.Getter;
import lombok.Setter;

@Getter
@Setter*/
public class AuthResponse {
	private String access_token;
	private String token_type; 
	private String expires_in; 
	private String scope;
	private String tenant_id;
	private String pod;
	private String strong_auth_supported;
	private String org;
	private String identity_id;
	private String user_name;
	private String strong_auth;
	private String jti;
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getTenant_id() {
		return tenant_id;
	}
	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}
	public String getPod() {
		return pod;
	}
	public void setPod(String pod) {
		this.pod = pod;
	}
	public String getStrong_auth_supported() {
		return strong_auth_supported;
	}
	public void setStrong_auth_supported(String strong_auth_supported) {
		this.strong_auth_supported = strong_auth_supported;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getIdentity_id() {
		return identity_id;
	}
	public void setIdentity_id(String identity_id) {
		this.identity_id = identity_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getStrong_auth() {
		return strong_auth;
	}
	public void setStrong_auth(String strong_auth) {
		this.strong_auth = strong_auth;
	}
	public String getJti() {
		return jti;
	}
	public void setJti(String jti) {
		this.jti = jti;
	}
	
	
}
