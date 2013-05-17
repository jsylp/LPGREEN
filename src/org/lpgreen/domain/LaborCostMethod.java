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
	private String          status;
	private String          description;
	private double          unitQuantity;
	private double          unitQuantityCost;
	private String          unitQuantityUMCode;
	private String          userDefinedUMCode;
	private double          multiplier;
	private int             rangeStartQuantity;
	private int             rangeEndQuantity;
	private String          OTStartWeekdayTime;     // HH:MM
	private String          OTDuration;             // HH:MM
	private UUID            ownerId;
	private int             ownerAccountId;
	private DateTime        createdDate;
	private UUID            createdById;
	private DateTime        lastModifiedDate;
	private UUID            lastModifiedById;

	/*
	Id                      serial NOT NULL PRIMARY KEY UNIQUE,
	CostMethodCode          varchar(30) NOT NULL,
	CostMethodType          varchar(30) NOT NULL,
	Status                  varchar(30) NOT NULL,
	Description             varchar(255),
	UnitQuantity            decimal(19,4),
	UnitQuantityCost        decimal(19,4),
	UnitQuantityUMCode      varchar(20),
	UserDefinedUMCode       varchar(20),
	Multiplier              decimal(10,4),
	RangeStartQuantity      int,
	RangeEndQuantity        int,
	OTStartWeekdayTime      char(5),
	OTDuration              char(5),
	OwnerId                 uuid,
	OwnerAccountId          int REFERENCES Account(Id),
	*/

	// Constructor
	public LaborCostMethod() {
	}
	
	// Constructor
	public LaborCostMethod(String costMethodCode, String costMethodType, String status,
			String description,	double unitQuantity, double unitQuantityCost,
			String unitQuantityUMCode, String userDefinedUMCode, double multiplier,
			int rangeStartQuantity, int rangeEndQuantity,
			String OTStartWeekdayTime, String OTDuration, int ownerAccountId) {
		this.costMethodCode = costMethodCode;
		this.costMethodType = costMethodType;
		this.status = status;
		this.description = description;
		this.unitQuantity = unitQuantity;
		this.unitQuantityCost = unitQuantityCost;
		this.unitQuantityUMCode = unitQuantityUMCode;
		this.userDefinedUMCode = userDefinedUMCode;
		this.multiplier = multiplier;
		this.rangeStartQuantity = rangeStartQuantity;
		this.rangeEndQuantity = rangeEndQuantity;
		this.OTStartWeekdayTime = OTStartWeekdayTime;
		this.OTDuration = OTDuration;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public double getUnitQuantityCost() {
		return unitQuantityCost;
	}
	public void setUnitQuantityCost(double unitQuantityCost) {
		this.unitQuantityCost = unitQuantityCost;
	}
	public String getUnitQuantityUMCode() {
		return unitQuantityUMCode;
	}
	public void setUnitQuantityUMCode(String unitQuantityUMCode) {
		this.unitQuantityUMCode = unitQuantityUMCode;
	}
	public String getUserDefinedUMCode() {
		return userDefinedUMCode;
	}
	public void setUserDefinedUMCode(String userDefinedUMCode) {
		this.userDefinedUMCode = userDefinedUMCode;
	}
	public double getMultiplier() {
		return multiplier;
	}
	public void setMultiplier(double multiplier) {
		this.multiplier = multiplier;
	}
	public int getRangeStartQuantity() {
		return rangeStartQuantity;
	}
	public void setRangeStartQuantity(int rangeStartQuantity) {
		this.rangeStartQuantity = rangeStartQuantity;
	}
	public int getRangeEndQuantity() {
		return rangeEndQuantity;
	}
	public void setRangeEndQuantity(int rangeEndQuantity) {
		this.rangeEndQuantity = rangeEndQuantity;
	}
	public String getOTStartWeekdayTime() {
		return OTStartWeekdayTime;
	}
	public void setOTStartWeekdayTime(String OTStartWeekdayTime) {
		this.OTStartWeekdayTime = OTStartWeekdayTime;
	}
	public String getOTDuration() {
		return OTDuration;
	}
	public void setOTDuration(String OTDuration) {
		this.OTDuration = OTDuration;
	}
	public UUID getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
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
