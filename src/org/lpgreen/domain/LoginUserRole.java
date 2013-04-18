package org.lpgreen.domain;

import java.io.Serializable;
import java.util.UUID;

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

public class LoginUserRole implements Serializable {

	private static final long serialVersionUID = 664406184352863096L;
	private int             loginUserId;
	private String          loginUserName;
	private int             roleId;
	private String          roleName;
	private int             ownerAccountId;
	private DateTime        createdDate;	// NOTE: we will learn the Joda datetime manipulation in the future
	private UUID            createdById;
	private DateTime        lastModifiedDate;
	private UUID            lastModifiedById;

	/*
	LoginUserId             int REFERENCES LoginUser(Id) NOT NULL,
	RoleId                  int REFERENCES Role(Id) NOT NULL,
	*/
	
	// Constructor
	public LoginUserRole() {
	}
	
	// Constructor
	public LoginUserRole(int loginUserId, int roleId, int ownerAccountId) {
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
	public String getLoginUserName() {
		return loginUserName;
	}
	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getOwnerAccountId() {
		return ownerAccountId;
	}
	public void setOwnerAccountId(int ownerAccountId) {
		this.ownerAccountId = ownerAccountId;
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}

	// TODO: generate getting and setting for the remaining fields.
	// TIP: right click the class name in the project explorer panel, invoke menu source -> generate getting and setting ...
}
