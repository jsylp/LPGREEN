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

	private static final long serialVersionUID = 4211824501050120552L; // ToDo: find a new number
	private int             loginUserId;
	private int             roleId;
	private DateTime        createdDateTime;	// NOTE: we will learn the Joda datetime manipulation in the future
	
	/*
	LoginUserId             int REFERENCES LoginUser(Id) NOT NULL,
	RoleId                  int REFERENCES Role(Id) NOT NULL,
	*/
	
	// Constructor
	public LoginUserRoles() {
	}
	
	// Constructor
	public LoginUserRoles(int loginUserId, int roleId) {
		this.loginUserId = loginUserId;
		this.roleId = roleId;
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
	
	public DateTime getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(DateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	// TODO: generate getting and setting for the remaining fields.
	// TIP: right click the class name in the project explorer panel, invoke menu source -> generate getting and setting ...
}
