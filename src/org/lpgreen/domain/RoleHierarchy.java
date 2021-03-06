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

public class RoleHierarchy implements Serializable {

	private static final long serialVersionUID = -9207571163059812615L;
	private int             roleId;
	private String          roleName;
	private int             includedRoleId;
	private String          includedRoleName;
	private int             ownerAccountId;
	private DateTime        createdDateTime;	// NOTE: we will learn the Joda datetime manipulation in the future
	
	/*
	RoleId                  int REFERENCES Role(Id) NOT NULL,
	IncludedRoleId          int REFERENCES Role(Id) NOT NULL,
	OwnerAccountId          int REFERENCES Account (Id),
	*/
	
	// Constructor
	public RoleHierarchy() {
	}
	
	// Constructor
	public RoleHierarchy(int roleId, int includedRoleId, int ownerAccountId) {
		this.roleId = roleId;
		this.includedRoleId = includedRoleId;
		this.ownerAccountId = ownerAccountId;
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

	public int getIncludedRoleId() {
		return includedRoleId;
	}
	public void setIncludedRoleId(int includedRoleId) {
		this.includedRoleId = includedRoleId;
	}
	public String getIncludedRoleName() {
		return includedRoleName;
	}
	public void setIncludedRoleName(String includedRoleName) {
		this.includedRoleName = includedRoleName;
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
