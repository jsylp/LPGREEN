package org.lpgreen.domain;

import java.io.Serializable;
import org.joda.time.DateTime;

/**
 * Department is a domain object
 * 
 * Creation date: Feb. 8, 2013 
 * Last modify date: Feb. 8, 2013
 * 
 * @author Jiaxun Stephen Yu
 * @version 1.0
 */

public class LoginUserRoles implements Serializable {

	private static final long serialVersionUID = 664406184352863096L;
	private int             loginUserId;
	private int             roleId;
	private int             ownerAccountId;
	private DateTime        createdDateTime;	// NOTE: we will learn the Joda datetime manipulation in the future

	/*
	LoginUserId             int REFERENCES LoginUser(Id) NOT NULL,
	RoleId                  int REFERENCES Role(Id) NOT NULL,
	*/
	
	// Constructor
	public LoginUserRoles() {
	}
	
	// Constructor
	public LoginUserRoles(int loginUserId, int roleId, int ownerAccountId) {
		this.loginUserId = loginUserId;
		this.roleId = roleId;
		this.ownerAccountId = ownerAccountId;
	}	
	
	public int getLoginUserId() {
		return loginUserId;
	}
	public void setLoginUserId(int loginUserId) {
		this.loginUserId = loginUserId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getOwnerAccountId() {
		return ownerAccountId;
	}
	public void setOwnerAccountId(int ownerAccountId) {
		this.ownerAccountId = ownerAccountId;
	}

	public DateTime getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(DateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	// TODO: generate getting and setting for the remaining fields.
	// TIP: right click the class name in the project explorer panel, invoke menu source -> generate getting and setting ...
}
