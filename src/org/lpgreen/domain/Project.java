package org.lpgreen.domain;

import java.io.Serializable;
import java.util.UUID;

import org.joda.time.DateTime;
import org.lpgreen.util.StringUtil;

/**
 * Project is a domain object.
 * 
 * Creation date: Mar. 5, 2013
 * Last modify date: Mar. 5, 2013
 * 
 * @author  Jiaxun Stephen Yu
 * @version 1.0
 */

public class Project implements Serializable {

	private static final long serialVersionUID = -4399220502314763280L;

	private int         id;                     // database generated id
	private String      projectCode;
	private String      name;
	private String      currentPhase;
	private int         projectManager1Id;
	private String      projectManager1Name;
	private int         projectManager2Id;
	private String      projectManager2Name;
	private int         customerAccountId;
	private String      customerAccountName;
	private UUID        customerContactId;
	private String      customerContactName;
	private UUID        sponsorId;
	private String      sponsorName;
	private int         managingDeptId;
	private String      managingDeptName;
	private String      objectives;
	private String      description;
	private double      budget;
	private String      currencyCode;           // currency code: http://www.xe.com/iso4217.php
	private DateTime    startDate;
	private String      startDateInputString;   // format in UTC timezone: YYYY-MM-dd HH:mm:ss, passed from client to server
	private DateTime    endDate;
	private String      endDateInputString;     // format in UTC timezone: YYYY-MM-dd HH:mm:ss, passed from client to server
	private int         parentProjectId;
	private String      parentProjectCode;
	private String      parentProjectName;
	private String      notes;
	private UUID        ownerId;
	private int         ownerAccountId;
	private DateTime    createdDate;
	private UUID        createdById;
	private DateTime    lastModifiedDate;
	private UUID        lastModifiedById;

	// Constructor
	public Project() {
	}

	// Constructor
	public Project(
			DateTime createdDate, UUID createdById, 
			DateTime lastModifiedDate, UUID lastModifiedById,
			UUID ownerId, int ownerAccountId,
			String projectCode, String name, String currentPhase,  
			int projectManager1Id, int projectManager2Id, 
			int customerAccountId, UUID customerContactId, 
			UUID sponsorId, int managingDeptId, String objectives,
			String description, double budget, String currencyCode,
			DateTime startDate, DateTime endDate,
			int parentProjectId, String notes)
	{
		//super(createdDate, createdById, lastModifiedDate, lastModifiedById, ownerId, ownerAccountId);
		this.ownerAccountId = ownerAccountId;
		this.projectCode = projectCode;
		this.name = name;
		this.currentPhase = currentPhase;
		this.projectManager1Id = projectManager1Id;
		this.projectManager2Id = projectManager2Id;
		this.customerAccountId = customerAccountId;
		this.customerContactId = customerContactId;
		this.sponsorId = sponsorId;
		this.managingDeptId = managingDeptId;
		this.objectives = objectives;
		this.description = description;
		this.budget = budget;
		this.currencyCode = currencyCode;
		this.startDate = startDate;
		this.endDate = endDate;
		this.parentProjectId = parentProjectId;
		this.notes = notes;
	}

	public void update(
			DateTime lastModifiedDate, UUID lastModifiedById, UUID ownerId, 
			String projectCode, String name, String currentPhase,  
			int projectManager1Id, int projectManager2Id, 
			int customerAccountId, UUID customerContactId, 
			UUID sponsorId, int managingDeptId, String objectives,
			String description, double budget, String currencyCode,
			DateTime startDate, DateTime endDate,
			int parentProjectId, String notes) {
		//update(lastModifiedDate, lastModifiedById, ownerId);
		this.projectCode = projectCode;
		this.name = name;
		this.currentPhase = currentPhase;
		this.projectManager1Id = projectManager1Id;
		this.projectManager2Id = projectManager2Id;
		this.customerAccountId = customerAccountId;
		this.customerContactId = customerContactId;
		this.sponsorId = sponsorId;
		this.managingDeptId = managingDeptId;
		this.objectives = objectives;
		this.description = description;
		this.budget = budget;
		this.currencyCode = currencyCode;
		this.startDate = new DateTime(startDate);
		this.endDate = new DateTime(endDate);
		this.parentProjectId = parentProjectId;
		this.notes = notes;
	}

	// This clone will copy characteristics data, but not the dynamic data, such as dates
	public Project clone() {
		Project clonedProject = new Project();
		clonedProject.setProjectCode(this.projectCode);
		clonedProject.setName(this.name);
		clonedProject.setCurrentPhase(this.currentPhase);
		clonedProject.setProjectManager1Id(this.projectManager1Id);
		clonedProject.setProjectManager2Id(this.projectManager2Id);
		clonedProject.setCustomerAccountId(this.customerAccountId);
		clonedProject.setCustomerContactId(this.customerContactId);
		clonedProject.setSponsorId(this.sponsorId);
		clonedProject.setManagingDeptId(this.managingDeptId);
		clonedProject.setObjectives(this.objectives);
		clonedProject.setDescription(this.description);
		clonedProject.setBudget(this.budget);
		clonedProject.setCurrencyCode(this.currencyCode);
		clonedProject.setParentProjectId(this.parentProjectId);
		clonedProject.setNotes(this.notes);
		return clonedProject;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getCurrentPhase() {
		return currentPhase;
	}
	public void setCurrentPhase(String currentPhase) {
		this.currentPhase = currentPhase;
	}

	public int getProjectManager1Id() {
		return projectManager1Id;
	}
	public void setProjectManager1Id(int projectManager1Id) {
		this.projectManager1Id = projectManager1Id;
	}
	public String getProjectManager1Name() {
		return projectManager1Name;
	}
	public void setProjectManager1Name(String projectManager1Name) {
		this.projectManager1Name = projectManager1Name;
	}

	public int getProjectManager2Id() {
		return projectManager2Id;
	}
	public void setProjectManager2Id(int projectManager2Id) {
		this.projectManager2Id = projectManager2Id;
	}
	public String getProjectManager2Name() {
		return projectManager2Name;
	}
	public void setProjectManager2Name(String projectManager2Name) {
		this.projectManager2Name = projectManager2Name;
	}

	public int getCustomerAccountId() {
		return customerAccountId;
	}
	public void setCustomerAccountId(int customerAccountId) {
		this.customerAccountId = customerAccountId;
	}
	public String getCustomerAccountName() {
		return customerAccountName;
	}
	public void setCustomerAccountName(String customerAccountName) {
		this.customerAccountName = customerAccountName;
	}

	public UUID getCustomerContactId() {
		return customerContactId;
	}
	public void setCustomerContactId(UUID customerContactId) {
		this.customerContactId = customerContactId;
	}
	public String getCustomerContactName() {
		return customerContactName;
	}
	public void setCustomerContactName(String customerContactName) {
		this.customerContactName = customerContactName;
	}

	public UUID getSponsorId() {
		return sponsorId;
	}
	public void setSponsorId(UUID sponsorId) {
		this.sponsorId = sponsorId;
	}
	public String getSponsorName() {
		return sponsorName;
	}
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public int getManagingDeptId() {
		return managingDeptId;
	}
	public void setManagingDeptId(int managingDeptId) {
		this.managingDeptId = managingDeptId;
	}
	public String getManagingDeptName() {
		return managingDeptName;
	}
	public void setManagingDeptName(String managingDeptName) {
		this.managingDeptName = managingDeptName;
	}

	public String getObjectives() {
		return objectives;
	}
	public void setObjectives(String objectives) {
		this.objectives = objectives;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public double getBudget() {
		return budget;
	}
	public void setBudget(double budget) {
		this.budget = budget;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public DateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}
	public String getStartDateInputString() {
		return startDateInputString;
	}
	public void setStartDateInputString(String startDateInputString) {
		this.startDateInputString = startDateInputString;
	}
	public String getStartDateString() {
		if (this.startDate == null)
			return null;
		else
			return StringUtil.getUTCDateTimeString_hhmma(this.startDate);
	}
	public String getStartDateStringLong() {
		if (this.startDate == null)
			return null;
		else
			return StringUtil.getUTCDateTimeString_hhmma_week(this.startDate);
	}

	public DateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}
	public String getEndDateInputString() {
		return endDateInputString;
	}
	public void setEndDateInputString(String endDateInputString) {
		this.endDateInputString = endDateInputString;
	}
	public String getEndDateString() {
		if (this.endDate == null)
			return null;
		else
			return StringUtil.getUTCDateTimeString_hhmma(this.endDate);
	}
	public String getEndDateStringLong() {
		if (this.endDate == null)
			return null;
		else
			return StringUtil.getUTCDateTimeString_hhmma_week(this.endDate);
	}

	public int getParentProjectId() {
		return parentProjectId;
	}
	public void setParentProjectId(int parentProjectId) {
		this.parentProjectId = parentProjectId;
	}
	public String getParentProjectCode() {
		return parentProjectCode;
	}
	public void setParentProjectCode(String parentProjectCode) {
		this.parentProjectCode = parentProjectCode;
	}
	public String getParentProjectName() {
		return parentProjectName;
	}
	public void setParentProjectName(String parentProjectName) {
		this.parentProjectName = parentProjectName;
	}

	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
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

}
