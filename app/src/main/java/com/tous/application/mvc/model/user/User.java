package com.tous.application.mvc.model.user;


import com.moka.framework.database.DataObject;


public class User extends DataObject {

	private long id;
	private String email;
	private String password;
	private String name;
	private String authenticationToken;
	private String syncAt;
	private long createdAt;
	private long updatedAt;

	public long getId() {

		return id;
	}

	public void setId( long id ) {

		this.id = id;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail( String email ) {

		this.email = email;
	}

	public String getPassword() {

		return password;
	}

	public void setPassword( String password ) {

		this.password = password;
	}

	public String getName() {

		return name;
	}

	public void setName( String name ) {

		this.name = name;
	}

	public String getAuthenticationToken() {

		return authenticationToken;
	}

	public void setAuthenticationToken( String authenticationToken ) {

		this.authenticationToken = authenticationToken;
	}

	public String getSyncAt() {

		return syncAt;
	}

	public void setSyncAt( String syncAt ) {

		this.syncAt = syncAt;
	}

	public long getCreatedAt() {

		return createdAt;
	}

	public void setCreatedAt( long createdAt ) {

		this.createdAt = createdAt;
	}

	public long getUpdatedAt() {

		return updatedAt;
	}

	public void setUpdatedAt( long updatedAt ) {

		this.updatedAt = updatedAt;
	}

}
