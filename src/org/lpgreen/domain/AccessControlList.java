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

public class AccessControlList implements Serializable {

	private static final long serialVersionUID = 7697797138591781838L;
	private int             roleId;
	private String          objectName;
	private int             operationRightId;
	private int             ownerAccountId;
	private DateTime        createdDateTime;	// NOTE: we will learn the Joda datetime manipulation in the future
	private String          roleName;           // Implementation helper - retrieved from outer join
	private String          operationName;      // Implementation helper - retrieved from outer join

	/*
	RoleId                  int REFERENCES Role(Id) NOT NULL,
	ObjectName              varchar(255) NOT NULL,
	OperationRightId        int REFERENCES OperationRight(Id) NOT NULL,
	OwnerAccountId          int REFERENCES Account (Id),
	*/
	
	// Constructor
	public AccessControlList() {
	}
	
	// Constructor
	public AccessControlList(int roleId, String objectName, int operationRightId, int ownerAccountId) {
		this.roleId = roleId;
		this.objectName = objectName;
		this.operationRightId = operationRightId;
		this.ownerAccountId = ownerAccountId;
	}	
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public int getOperationRightId() {
		return operationRightId;
	}
	public void setOperationRightId(int operationRightId) {
		this.operationRightId = operationRightId;
	}
	public int getOwnerAccountId() {
		return ownerAccountId;
	}
	public void setOwnerAccountId(int ownerAccountId) {
		this.ownerAccountId = ownerAccountId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
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
