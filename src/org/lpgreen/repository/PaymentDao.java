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

	// get all Payments owned by a specific owner account id
	public List<Payment> findPaymentsByOwnerAccountId(int ownerAccountId, Set<String> currentPhases);

	// get a specific Payment by a given database id
	public Payment findPaymentById(long id);

	// get all Payments by IsReceivedPayment
	public List<Payment> findPaymentsByIsReceivedPayment(int ownerAccountId, boolean isReceivedPayment);

	// get all Payments by PaymentType
	public List<Payment> findPaymentsByPaymentType(int ownerAccountId, String paymentType);

	// get all Payments by PaymentCategory
	public List<Payment> findPaymentsByPaymentCategory(int ownerAccountId, String paymentCategory);

	// get all Payments by Description
	public List<Payment> findPaymentsByDescription(int ownerAccountId, String description);

	// get all Payments by CurrencyCode
	public List<Payment> findPaymentsByCurrencyCode(int ownerAccountId, String currencyCode);

	// get all Payments by PaymentMethodType
	public List<Payment> findPaymentsByPaymentMethodType(int ownerAccountId, String paymentMethodType);

	// get all Payments by PayerPaymentMethodId
	public List<Payment> findPaymentsByPayerPaymentMethodId(int ownerAccountId, int payerPaymentMethodId);

	// get all Payments by PayeePaymentReceiveMethodId
	public List<Payment> findPaymentsByPayeePaymentReceiveMethodId(int ownerAccountId, int payeePaymentReceiveMethodId);

	// get all Payments by CheckNumber range
	public List<Payment> findPaymentsByCheckNumberRange(int ownerAccountId, String startCheckNumber, String endCheckNumber);

	// get all Payments by PaymentDateTime range
	public List<Payment> findPaymentsByPaymentDateTimeRange(int ownerAccountId, DateTime startDateTime, DateTime endDateTime);

	// get all Payments by PayerAccountId
	public List<Payment> findPaymentsByPayerAccountId(int ownerAccountId, int payerAccountId);

	// get all Payments by PayerAccountName
	public List<Payment> findPaymentsByPayerAccountName(int ownerAccountId, String payerAccountName);

	// get all Payments by PayerContactId
	public List<Payment> findPaymentsByPayerContactId(int ownerAccountId, UUID payerContactId);

	// get all Payments by PayerContactName
	public List<Payment> findPaymentsByPayerContactName(int ownerAccountId, String payerContactName);

	// get all Payments by PayerBillingAddressId
	public List<Payment> findPaymentsByPayerBillingAddressId(int ownerAccountId, UUID payerBillingAddressId);

	// get all Payments by PayeeAccountId
	public List<Payment> findPaymentsByPayeeAccountId(int ownerAccountId, int payeeAccountId);

	// get all Payments by PayeeAccountName
	public List<Payment> findPaymentsByPayeeAccountName(int ownerAccountId, String payeeAccountName);

	// get all Payments by PayeeContactId
	public List<Payment> findPaymentsByPayeeContactId(int ownerAccountId, UUID payeeContactId);

	// get all Payments by PayeeContactName
	public List<Payment> findPaymentsByPayeeContactName(int ownerAccountId, String payeeContactName);

	// get all Payments by PayeeBillingAddressId
	public List<Payment> findPaymentsByPayeeBillingAddressId(int ownerAccountId, UUID payeeBillingAddressId);

	// get all Payments by DepartmentId
	public List<Payment> findPaymentsByDepartmentId(int ownerAccountId, int departmentId);

	// get all Payments by CostCenterNumber
	public List<Payment> findPaymentsByCostCenterNumber(int ownerAccountId, String costCenterNumber);

	// get all Payments by PrimaryPaymentReceiverEmpId
	public List<Payment> findPaymentsByPrimaryPaymentReceiverEmpId(int ownerAccountId, int primaryPaymentReceiverEmpId);

	// get all Payments by SecondaryPaymentReceiverEmpId
	public List<Payment> findPaymentsBySecondaryPaymentReceiverEmpId(int ownerAccountId, int secondaryPaymentReceiverEmpId);

	// get all Payments by PrimaryPaymentPayerEmpId
	public List<Payment> findPaymentsByPrimaryPaymentPayerEmpId(int ownerAccountId, int primaryPaymentPayerEmpId);

	// get all Payments by SecondaryPaymentPayerEmpId
	public List<Payment> findPaymentsBySecondaryPaymentPayerEmpId(int ownerAccountId, int secondaryPaymentPayerEmpId);

	// get all Payments by Notes
	public List<Payment> findPaymentsByNotes(int ownerAccountId, String notes);

	// Add a Payment. Return the generated database id
	public int addPayment(Payment payment) throws DuplicateKeyException, Exception;

	// Save changes of an existing Payment object. Return the # of records updated
	public int savePayment(Payment payment)	throws DuplicateKeyException, Exception;

	// Delete a Payment object. Return the # of records deleted
	public int deletePayment(int ownerAccountId, long id) throws Exception;

}
