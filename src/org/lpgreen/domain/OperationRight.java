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

public class OperationRight implements Serializable {

	private static final long serialVersionUID = 4211824501050120552L; // ToDo: find a new number
	private int             id; 	// database generated id
	private String          operationName;
	private String          description;
	private int             ownerAccountId;
	private DateTime        createdDateTime;	// NOTE: we will learn the Joda datetime manipulation in the future
	
	/*
	Id                      serial NOT NULL PRIMARY KEY UNIQUE,
	OperationName           varchar(255) NOT NULL,
	Description             varchar(512),
	OwnerAccountId          int REFERENCES Account (Id),
	*/
	
	// Constructor
	public OperationRight() {
	}
	
	// Constructor
	public OperationRight(String operationName, String description, int ownerAccountId) {
		this.operationName = operationName;
		this.description = description;
		this.ownerAccountId = ownerAccountId;
	}	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
