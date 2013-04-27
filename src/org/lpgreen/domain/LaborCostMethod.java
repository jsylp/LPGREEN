package org.lpgreen.domain;

import java.io.Serializable;
import java.util.UUID;

import org.joda.time.DateTime;

/**
 * LaborCostMethod is a domain object
 * 
 * Creation date: Apr. 24, 2013 
 * Last modify date: Apr. 24, 2013
 * 
 * @author Jiaxun Stephen Yu
 * @version 1.0
 */

public class LaborCostMethod implements Serializable {

	private static final long serialVersionUID = 609156840288246724L;
	private int             id;                 // database generated id
	private String          costMethodCode;
	private String          costMethodType;
	private String          description;
	private double          unitQuantity;
	private String          uMCode;
	private int             minQuantity;
	private int             maxQuantity;
	private String          startTime;          // WD-HH:MM
	private String          duration;           // HH:MM
	private int             ownerAccountId;
	private DateTime        createdDate;
	private UUID            createdById;
	private DateTime        lastModifiedDate;
	private UUID            lastModifiedById;

	/*
	Id                      serial NOT NULL PRIMARY KEY UNIQUE,
	CostMethodCode          varchar(30) NOT NULL,
	CostMethodType          varchar(30) NOT NULL,
	Description             varchar(255),
	UnitQuantity            decimal(19,4),
	UMCode                  varchar(20),
	MinQuantity             int,
	MaxQuantity             int,
	StartTime               char(8),            // WD: weekday (00 - Sunday, 06 - Saturday)
	Duration                char(5),            // HH:MM  hour:minutes
	OwnerId                 uuid,
	OwnerAccountId          int REFERENCES Account(Id),
	*/

	// Constructor
	public LaborCostMethod() {
	}
	
	// Constructor
	public LaborCostMethod(String costMethodCode, String costMethodType,
			String description,	double unitQuantity, String uMCode,	int minQuantity,
			int maxQuantity, String startTime, String duration, int ownerAccountId) {
		this.costMethodCode = costMethodCode;
		this.costMethodType = costMethodType;
		this.description = description;
		this.unitQuantity = unitQuantity;
		this.uMCode = uMCode;
		this.minQuantity = minQuantity;
		this.maxQuantity = maxQuantity;
		this.startTime = startTime;
		this.duration = duration;
		this.ownerAccountId = ownerAccountId;
	}	

	/*
	 * property gets and sets
	 */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCostMethodCode() {
		return costMethodCode;
	}
	public void setCostMethodCode(String costMethodCode) {
		this.costMethodCode = costMethodCode;
	}
	public String getCostMethodType() {
		return costMethodType;
	}
	public void setCostMethodType(String costMethodType) {
		this.costMethodType = costMethodType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getUnitQuantity() {
		return unitQuantity;
	}
	public void setUnitQuantity(double unitQuantity) {
		this.unitQuantity = unitQuantity;
	}
	public String getUMCode() {
		return uMCode;
	}
	public void setUMCode(String uMCode) {
		this.uMCode = uMCode;
	}
	public int getMinQuantity() {
		return minQuantity;
	}
	public void setMinQuantity(int minQuantity) {
		this.minQuantity = minQuantity;
	}
	public int getMaxQuantity() {
		return maxQuantity;
	}
	public void setMaxQuantity(int maxQuantity) {
		this.maxQuantity = maxQuantity;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
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
