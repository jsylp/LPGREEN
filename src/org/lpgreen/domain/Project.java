package org.lpgreen.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import org.joda.time.DateTime;

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

	private static final long serialVersionUID = -3081390794547822711L; // ToDo: need to regenerate!!

	private int         id;                 // database generated id
	private String		projectCode;
	private String      name;
	private String      currentPhase;
	private int         projectManager1Id;
	private int         projectManager2Id;
	private int         customerAccount;
	private UUID        customerContact;
	private UUID        sponsor;
	private int         managingDeptId;
	private String      objectives;
	private String      description;
	private double      budget;
	private String      currencyCode;       // currency code: http://www.xe.com/iso4217.php
	private DateTime    startDate;
	private DateTime    endDate;
	private int         parentProjectId;
	private String      notes;
	private UUID        ownerId;
	private int 		ownerAccountId;
	private DateTime 	createdDate;
	private UUID        createdById;
	private DateTime 	lastModifiedDate;
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
			int customerAccount, UUID customerContact, 
			UUID sponsor, int managingDeptId, String objectives,
			String description, double budget, String currencyCode,
			DateTime startDate, DateTime endDate,
			int parentProjectId, String notes)
	{		
		//super(createdDate, createdById, lastModifiedDate, lastModifiedById, ownerId, ownerAccountId);
		this.projectCode = projectCode;
		this.name = name;
		this.currentPhase = currentPhase;
		this.projectManager1Id = projectManager1Id;
		this.projectManager2Id = projectManager2Id;
		this.customerAccount = customerAccount;
		this.customerContact = customerContact;
		this.sponsor = sponsor;
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

	public void update(
			DateTime lastModifiedDate, UUID lastModifiedById, UUID ownerId, 
			String projectCode, String name, String currentPhase,  
			int projectManager1Id, int projectManager2Id, 
			int customerAccount, UUID customerContact, 
			UUID sponsor, int managingDeptId, String objectives,
			String description, double budget, String currencyCode,
			DateTime startDate, DateTime endDate,
			int parentProjectId, String notes) {
		//update(lastModifiedDate, lastModifiedById, ownerId);
		this.projectCode = projectCode;
		this.name = name;
		this.currentPhase = currentPhase;
		this.projectManager1Id = projectManager1Id;
		this.projectManager2Id = projectManager2Id;
		this.customerAccount = customerAccount;
		this.customerContact = customerContact;
		this.sponsor = sponsor;
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
		clonedProject.setCustomerAccount(this.customerAccount);
		clonedProject.setCustomerContact(this.customerContact);
		clonedProject.setSponsor(this.sponsor);
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

	public int getProjectManager2Id() {
		return projectManager2Id;
	}
	public void setProjectManager2Id(int projectManager2Id) {
		this.projectManager2Id = projectManager2Id;
	}

	public int getCustomerAccount() {
		return customerAccount;
	}
	public void setCustomerAccount(int customerAccount) {
		this.customerAccount = customerAccount;
	}

	public UUID getCustomerContact() {
		return customerContact;
	}
	public void setCustomerContact(UUID customerContact) {
		this.customerContact = customerContact;
	}

	public UUID getSponsor() {
		return sponsor;
	}
	public void setSponsor(UUID sponsor) {
		this.sponsor = sponsor;
	}

	public int getManagingDeptId() {
		return managingDeptId;
	}
	public void setManagingDeptId(int managingDeptId) {
		this.managingDeptId = managingDeptId;
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
		//this.startDate = new DateTime(startDate);
		this.startDate = startDate;
	}

	public DateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(DateTime endDate) {
		//this.endDate = new DateTime(endDate);
		this.endDate = endDate;
	}

	public int getParentProjectId() {
		return parentProjectId;
	}
	public void setParentProjectId(int parentProjectId) {
		this.parentProjectId = parentProjectId;
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
