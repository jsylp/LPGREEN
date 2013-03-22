package org.lpgreen.domain;

import java.io.Serializable;
import java.util.UUID;

import org.joda.time.DateTime;

/**
 * Payment is a domain object.
 * 
 * Creation date: Mar.18, 2013
 * Last modify date: Mar. 18, 2013
 * 
 * @author  Jiaxun Stephen Yu
 * @version 1.0
 */

public class Payment implements Serializable {

	private static final long serialVersionUID = 1580129015848831473L;

	private long        id;                 // database generated id
	private boolean	    isReceivedPayment;
	private String      paymentType;
	private String      paymentCategory;
	private String      description;
	private String      currencyCode;       // currency code: http://www.xe.com/iso4217.php
	private double      totalAmount;
	private String      paymentMethodType;
	private int         payerPaymentMethodId;
	private int         payeePaymentReceiveMethodId;
	private String      checkNumber;
	private DateTime 	paymentDateTime;
	private int         payerAccountId;
	private String      payerAccountName;
	private UUID        payerContactId;
	private String      payerContactName;
	private UUID        payerBillingAddressId;
	private int         payeeAccountId;
	private String      payeeAccountName;
	private UUID        payeeContactId;
	private String      payeeContactName;
	private UUID        payeeBillingAddressId;
	private int         departmentId;
	private String      costCenterNumber;
	private int         primaryPaymentReceiverEmpId;
	private int         secondaryPaymentReceiverEmpId;
	private int         primaryPaymentPayerEmpId;
	private int         secondaryPaymentPayerEmpId;
	private String      notes;
	private UUID        ownerId;
	private int 		ownerAccountId;
	private DateTime 	createdDate;
	private UUID        createdById;
	private DateTime 	lastModifiedDate;
	private UUID        lastModifiedById;

	// Constructor
	public Payment() {
	}

	// Constructor
	public Payment(
			DateTime createdDate, UUID createdById, 
			DateTime lastModifiedDate, UUID lastModifiedById,
			UUID ownerId, int ownerAccountId, boolean isReceivedPayment,
			String paymentType, String paymentCategory, String description,
			String currencyCode, double totalAmount, String paymentMethodType,
			int payerPaymentMethodId, int payeePaymentReceiveMethodId,
			String checkNumber, DateTime paymentDateTime,
			int payerAccountId, String payerAccountName, UUID payerContactId,
			String payerContactName, UUID payerBillingAddressId,
			int payeeAccountId, String payeeAccountName, UUID payeeContactId,
			String payeeContactName, UUID payeeBillingAddressId,
			int departmentId, String costCenterNumber,
			int primaryPaymentReceiverEmpId, int secondaryPaymentReceiverEmpId,
			int primaryPaymentPayerEmpId, int secondaryPaymentPayerEmpId,
			String notes)
	{		
		//super(createdDate, createdById, lastModifiedDate, lastModifiedById, ownerId, ownerAccountId);
		this.ownerAccountId = ownerAccountId;
		this.isReceivedPayment = isReceivedPayment;
		this.paymentType = paymentType;
		this.paymentCategory = paymentCategory;
		this.description = description;
		this.currencyCode = currencyCode;
		this.totalAmount = totalAmount;
		this.paymentMethodType = paymentMethodType;
		this.payerPaymentMethodId = payerPaymentMethodId;
		this.payeePaymentReceiveMethodId = payeePaymentReceiveMethodId;
		this.checkNumber = checkNumber;
		this.paymentDateTime = new DateTime(paymentDateTime);
		this.payerAccountId = payerAccountId;
		this.payerAccountName = payerAccountName;
		this.payerContactId = payerContactId;
		this.payerContactName = payerContactName;
		this.payerBillingAddressId = payerBillingAddressId;
		this.payeeAccountId = payeeAccountId;
		this.payeeAccountName = payeeAccountName;
		this.payeeContactId = payeeContactId;
		this.payeeContactName = payeeContactName;
		this.payeeBillingAddressId = payeeBillingAddressId;
		this.departmentId = departmentId;
		this.costCenterNumber = costCenterNumber;
		this.primaryPaymentReceiverEmpId = primaryPaymentReceiverEmpId;
		this.secondaryPaymentReceiverEmpId = secondaryPaymentReceiverEmpId;
		this.primaryPaymentPayerEmpId = primaryPaymentPayerEmpId;
		this.secondaryPaymentPayerEmpId = secondaryPaymentPayerEmpId;
		this.notes = notes;
	}

	public void update(
			DateTime lastModifiedDate, UUID lastModifiedById, UUID ownerId, 
			boolean isReceivedPayment, String paymentType, String paymentCategory,  
			String description,	String currencyCode, double totalAmount,
			String paymentMethodType, int payerPaymentMethodId,
			int payeePaymentReceiveMethodId, String checkNumber, DateTime paymentDateTime,
			int payerAccountId, String payerAccountName, UUID payerContactId,
			String payerContactName, UUID payerBillingAddressId,
			int payeeAccountId, String payeeAccountName, UUID payeeContactId,
			String payeeContactName, UUID payeeBillingAddressId,
			int departmentId, String costCenterNumber,
			int primaryPaymentReceiverEmpId, int secondaryPaymentReceiverEmpId,
			int primaryPaymentPayerEmpId, int secondaryPaymentPayerEmpId,
			String notes) {
		//update(lastModifiedDate, lastModifiedById, ownerId);
		this.isReceivedPayment = isReceivedPayment;
		this.paymentType = paymentType;
		this.paymentCategory = paymentCategory;
		this.description = description;
		this.currencyCode = currencyCode;
		this.totalAmount = totalAmount;
		this.paymentMethodType = paymentMethodType;
		this.payerPaymentMethodId = payerPaymentMethodId;
		this.payeePaymentReceiveMethodId = payeePaymentReceiveMethodId;
		this.checkNumber = checkNumber;
		this.paymentDateTime = paymentDateTime;
		this.payerAccountId = payerAccountId;
		this.payerAccountName = payerAccountName;
		this.payerContactId = payerContactId;
		this.payerContactName = payerContactName;
		this.payerBillingAddressId = payerBillingAddressId;
		this.payeeAccountId = payeeAccountId;
		this.payeeAccountName = payeeAccountName;
		this.payeeContactId = payeeContactId;
		this.payeeContactName = payeeContactName;
		this.payeeBillingAddressId = payeeBillingAddressId;
		this.departmentId = departmentId;
		this.costCenterNumber = costCenterNumber;
		this.primaryPaymentReceiverEmpId = primaryPaymentReceiverEmpId;
		this.secondaryPaymentReceiverEmpId = secondaryPaymentReceiverEmpId;
		this.primaryPaymentPayerEmpId = primaryPaymentPayerEmpId;
		this.secondaryPaymentPayerEmpId = secondaryPaymentPayerEmpId;
		this.notes = notes;
	}

	// This clone will copy characteristics data, but not the dynamic data, such as dates
	public Payment clone() {
		Payment clonedPayment = new Payment();
		clonedPayment.setIsReceivedPayment(this.isReceivedPayment);
		clonedPayment.setPaymentType(this.paymentType);
		clonedPayment.setPaymentCategory(this.paymentCategory);
		clonedPayment.setDescription(this.description);
		clonedPayment.setCurrencyCode(this.currencyCode);
		clonedPayment.setTotalAmount(this.totalAmount);
		clonedPayment.setPaymentMethodType(this.paymentMethodType);
		clonedPayment.setPayerPaymentMethodId(this.payerPaymentMethodId);
		clonedPayment.setPayeePaymentReceiveMethodId(this.payeePaymentReceiveMethodId);
		clonedPayment.setCheckNumber(this.checkNumber);
		clonedPayment.setPayerAccountId(this.payerAccountId);
		clonedPayment.setPayerAccountName(this.payerAccountName);
		clonedPayment.setPayerContactId(this.payerContactId);
		clonedPayment.setPayerContactName(this.payerContactName);
		clonedPayment.setPayerBillingAddressId(this.payerBillingAddressId);
		clonedPayment.setPayeeAccountId(this.payeeAccountId);
		clonedPayment.setPayeeAccountName(this.payeeAccountName);
		clonedPayment.setPayeeContactId(this.payeeContactId);
		clonedPayment.setPayeeContactName(this.payeeContactName);
		clonedPayment.setPayeeBillingAddressId(this.payeeBillingAddressId);
		clonedPayment.setDepartmentId(this.departmentId);
		clonedPayment.setCostCenterNumber(this.costCenterNumber);
		clonedPayment.setPrimaryPaymentReceiverEmpId(this.primaryPaymentReceiverEmpId);
		clonedPayment.setSecondaryPaymentReceiverEmpId(this.secondaryPaymentReceiverEmpId);
		clonedPayment.setPrimaryPaymentPayerEmpId(this.primaryPaymentPayerEmpId);
		clonedPayment.setSecondaryPaymentPayerEmpId(this.secondaryPaymentPayerEmpId);
		clonedPayment.setNotes(this.notes);
		return clonedPayment;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public boolean getIsReceivedPayment() {
		return isReceivedPayment;
	}
	public void setIsReceivedPayment(boolean isReceivedPayment) {
		this.isReceivedPayment = isReceivedPayment;
	}

	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentCategory() {
		return paymentCategory;
	}
	public void setPaymentCategory(String paymentCategory) {
		this.paymentCategory = paymentCategory;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaymentMethodType() {
		return paymentMethodType;
	}
	public void setPaymentMethodType(String paymentMethodType) {
		this.paymentMethodType = paymentMethodType;
	}

	public int getPayerPaymentMethodId() {
		return payerPaymentMethodId;
	}
	public void setPayerPaymentMethodId(int payerPaymentMethodId) {
		this.payerPaymentMethodId = payerPaymentMethodId;
	}

	public int getPayeePaymentReceiveMethodId() {
		return payeePaymentReceiveMethodId;
	}
	public void setPayeePaymentReceiveMethodId(int payeePaymentReceiveMethodId) {
		this.payeePaymentReceiveMethodId = payeePaymentReceiveMethodId;
	}

	public String getCheckNumber() {
		return checkNumber;
	}
	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	public DateTime getPaymentDateTime() {
		return paymentDateTime;
	}
	public void setPaymentDateTime(DateTime paymentDateTime) {
		//this.paymentDateTime = new DateTime(paymentDateTime);
		this.paymentDateTime = paymentDateTime;
	}

	public int getPayerAccountId() {
		return payerAccountId;
	}
	public void setPayerAccountId(int payerAccountId) {
		this.payerAccountId = payerAccountId;
	}

	public String getPayerAccountName() {
		return payerAccountName;
	}
	public void setPayerAccountName(String payerAccountName) {
		this.payerAccountName = payerAccountName;
	}

	public UUID getPayerContactId() {
		return payerContactId;
	}
	public void setPayerContactId(UUID payerContactId) {
		this.payerContactId = payerContactId;
	}

	public String getPayerContactName() {
		return payerContactName;
	}
	public void setPayerContactName(String payerContactName) {
		this.payerContactName = payerContactName;
	}

	public UUID getPayerBillingAddressId() {
		return payerBillingAddressId;
	}
	public void setPayerBillingAddressId(UUID payerBillingAddressId) {
		this.payerBillingAddressId = payerBillingAddressId;
	}

	public int getPayeeAccountId() {
		return payeeAccountId;
	}
	public void setPayeeAccountId(int payeeAccountId) {
		this.payeeAccountId = payeeAccountId;
	}

	public String getPayeeAccountName() {
		return payeeAccountName;
	}
	public void setPayeeAccountName(String payeeAccountName) {
		this.payeeAccountName = payeeAccountName;
	}

	public UUID getPayeeContactId() {
		return payeeContactId;
	}
	public void setPayeeContactId(UUID payeeContactId) {
		this.payeeContactId = payeeContactId;
	}

	public String getPayeeContactName() {
		return payeeContactName;
	}
	public void setPayeeContactName(String payeeContactName) {
		this.payeeContactName = payeeContactName;
	}

	public UUID getPayeeBillingAddressId() {
		return payeeBillingAddressId;
	}
	public void setPayeeBillingAddressId(UUID payeeBillingAddressId) {
		this.payeeBillingAddressId = payeeBillingAddressId;
	}

	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getCostCenterNumber() {
		return costCenterNumber;
	}
	public void setCostCenterNumber(String costCenterNumber) {
		this.costCenterNumber = costCenterNumber;
	}

	public int getPrimaryPaymentReceiverEmpId() {
		return primaryPaymentReceiverEmpId;
	}
	public void setPrimaryPaymentReceiverEmpId(int primaryPaymentReceiverEmpId) {
		this.primaryPaymentReceiverEmpId = primaryPaymentReceiverEmpId;
	}

	public int getSecondaryPaymentReceiverEmpId() {
		return secondaryPaymentReceiverEmpId;
	}
	public void setSecondaryPaymentReceiverEmpId(int secondaryPaymentReceiverEmpId) {
		this.secondaryPaymentReceiverEmpId = secondaryPaymentReceiverEmpId;
	}

	public int getPrimaryPaymentPayerEmpId() {
		return primaryPaymentPayerEmpId;
	}
	public void setPrimaryPaymentPayerEmpId(int primaryPaymentPayerEmpId) {
		this.primaryPaymentPayerEmpId = primaryPaymentPayerEmpId;
	}

	public int getSecondaryPaymentPayerEmpId() {
		return secondaryPaymentPayerEmpId;
	}
	public void setSecondaryPaymentPayerEmpId(int secondaryPaymentPayerEmpId) {
		this.secondaryPaymentPayerEmpId = secondaryPaymentPayerEmpId;
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
