package org.lpgreen.repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.joda.time.DateTime;
import org.lpgreen.domain.Payment;
import org.springframework.dao.DuplicateKeyException;

/**
 * PaymentDao is the interface for Payment related entity's persistence layer
 * 
 * Creation date: Mar. 19, 2013
 * Last modify date: Mar. 19, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

public interface PaymentDao {

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// Payment related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	// get all Payments owned by a specific account id
	public List<Payment> findPaymentsByOwnerAccountId(int ownerAccountId);

	// get all Payments owned by a specific account id
	public List<Payment> findPaymentsByOwnerAccountId2(int ownerAccountId);

	// get a specific Payment by a given id
	public Payment findPaymentById(int ownerAccountId, int id);

	// get all Payments by IsReceived
	public List<Payment> findPaymentsByIsReceivedPayment(int ownerAccountId, boolean isReceivedPayment);

	// get all Payments by PaymentType
	public List<Payment> findPaymentsByPaymentType(int ownerAccountId, String paymentType);

	// get all Payments by PaymentCategory
	public List<Payment> findPaymentsByPaymentCategory(int ownerAccountId, String paymentCategory);

	/*
	// get all Payments owned by a given Payment manager1 id
	public List<Payment> findPaymentsByPaymentManager1Id(int ownerAccountId,
			int PaymentMgr1Id, Set<String> currentPhases);

	// get all Payments owned by a given Payment manager2 id
	public List<Payment> findPaymentsByPaymentManager2Id(int ownerAccountId,
			int PaymentMgr2Id, Set<String> currentPhases);

	// get all Payments owned by a given customer account
	public List<Payment> findPaymentsByCustomerAccount(int ownerAccountId,
			int customerAccount, Set<String> currentPhases);

	// get all Payments owned by a given customer contact
	public List<Payment> findPaymentsByCustomerContact(int ownerAccountId,
			UUID customerContact, Set<String> currentPhases);

	// get all Payments owned by a given sponsor
	public List<Payment> findPaymentsBySponsor(int ownerAccountId,
			UUID sponsor, Set<String> currentPhases);

	// get all Payments owned by a given managing department id
	public List<Payment> findPaymentsByManagingDeptId(int ownerAccountId, int managingDeptId,
			Set<String> currentPhases);

	// get all Payments by start date range
	public List<Payment> findPaymentsByStartDateRange(int ownerAccountId, DateTime fromDate, DateTime toDate)
			throws Exception;

	// get all Payments by end date range
	public List<Payment> findPaymentsByEndDateRange(int ownerAccountId, DateTime fromDate, DateTime toDate)
			throws Exception;

	// get all Payments by a given parent Payment id
	public List<Payment> findPaymentsByParentPaymentId(int ownerAccountId, int parentPaymentId);

	// Add a Payment. Return the generated id
	public int addPayment(Payment Payment) 
			throws DuplicateKeyException, Exception;

	// Save a the changes of an existing Payment object. Return the # of record updated
	public int savePayment(Payment Payment) 
			throws DuplicateKeyException, Exception;

	// Delete a Payment object. Return the # of record deleted
	public int deletePayment(int ownerAccountId, int id) throws Exception;
	*/

}
