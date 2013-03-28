package org.lpgreen.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.joda.time.DateTime;
import org.lpgreen.util.InvalidDataValueException;
import org.lpgreen.util.MissingRequiredDataException;
import org.lpgreen.domain.Payment;
import org.springframework.dao.DuplicateKeyException;

/**
 * PaymentManager is the interface for all Payment related objects
 * 
 * Creation date: Mar. 25, 2013
 * Last modify date: Mar. 25, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */
public interface PaymentManager {

	////////////////////////////////////////////
	// Payment related methods
	////////////////////////////////////////////

	// get all Payments owned by a specific owner account id
	public List<Payment> findAllSitePayments(int ownerAccountId, Set<String> currentStatuses)
			throws Exception;

	// get a specific Payment by a given database id
	public Payment findPaymentById(long id)
			throws Exception;

	// get all Payments by IsReceivedPayment
	public List<Payment> findPaymentsByIsReceivedPayment(int ownerAccountId, boolean isReceivedPayment)
			throws Exception;

	// get all Payments by PaymentType
	public List<Payment> findPaymentsByPaymentType(int ownerAccountId, String paymentType)
			throws Exception;

	// get all Payments by PaymentCategory
	public List<Payment> findPaymentsByPaymentCategory(int ownerAccountId, String paymentCategory)
			throws Exception;

	// get all Payments by Description
	public List<Payment> findPaymentsByDescription(int ownerAccountId, String description)
			throws Exception;

	// get all Payments by CurrencyCode
	public List<Payment> findPaymentsByCurrencyCode(int ownerAccountId, String currencyCode)
			throws Exception;

	// get all Payments by TotalAmount range
	public List<Payment> findPaymentsByTotalAmountRange(int ownerAccountId,
			double startAmount, double endAmount, String currencyCode)
			throws Exception;

	// get all Payments by PaymentMethodType
	public List<Payment> findPaymentsByPaymentMethodType(int ownerAccountId, String paymentMethodType)
			throws Exception;

	// get all Payments by PayerPaymentMethodId
	public List<Payment> findPaymentsByPayerPaymentMethodId(int ownerAccountId, int payerPaymentMethodId)
			throws Exception;

	// get all Payments by PayeePaymentReceiveMethodId
	public List<Payment> findPaymentsByPayeePaymentReceiveMethodId(int ownerAccountId, int payeePaymentReceiveMethodId)
			throws Exception;

	// get all Payments by CheckNumber range
	public List<Payment> findPaymentsByCheckNumberRange(int ownerAccountId,
			String startCheckNumber, String endCheckNumber)
			throws Exception;

	// get all Payments by PaymentDateTime range
	public List<Payment> findPaymentsByPaymentDateTimeRange(int ownerAccountId,
			DateTime startDateTime, DateTime endDateTime)
			throws Exception;

	// get all Payments by PayerAccountId
	public List<Payment> findPaymentsByPayerAccountId(int ownerAccountId, int payerAccountId)
			throws Exception;

	// get all Payments by PayerAccountName
	public List<Payment> findPaymentsByPayerAccountName(int ownerAccountId, String payerAccountName)
			throws Exception;

	// get all Payments by PayerContactId
	public List<Payment> findPaymentsByPayerContactId(int ownerAccountId, UUID payerContactId)
			throws Exception;

	// get all Payments by PayerContactName
	public List<Payment> findPaymentsByPayerContactName(int ownerAccountId, String payerContactName)
			throws Exception;

	// get all Payments by PayerBillingAddressId
	public List<Payment> findPaymentsByPayerBillingAddressId(int ownerAccountId, UUID payerBillingAddressId)
			throws Exception;

	// get all Payments by PayeeAccountId
	public List<Payment> findPaymentsByPayeeAccountId(int ownerAccountId, int payeeAccountId)
			throws Exception;

	// get all Payments by PayeeAccountName
	public List<Payment> findPaymentsByPayeeAccountName(int ownerAccountId, String payeeAccountName)
			throws Exception;

	// get all Payments by PayeeContactId
	public List<Payment> findPaymentsByPayeeContactId(int ownerAccountId, UUID payeeContactId)
			throws Exception;

	// get all Payments by PayeeContactName
	public List<Payment> findPaymentsByPayeeContactName(int ownerAccountId, String payeeContactName)
			throws Exception;

	// get all Payments by PayeeBillingAddressId
	public List<Payment> findPaymentsByPayeeBillingAddressId(int ownerAccountId, UUID payeeBillingAddressId)
			throws Exception;

	// get all Payments by DepartmentId
	public List<Payment> findPaymentsByDepartmentId(int ownerAccountId, int departmentId)
			throws Exception;

	// get all Payments by CostCenterNumber
	public List<Payment> findPaymentsByCostCenterNumber(int ownerAccountId, String costCenterNumber)
			throws Exception;

	// get all Payments by PrimaryPaymentReceiverEmpId
	public List<Payment> findPaymentsByPrimaryPaymentReceiverEmpId(int ownerAccountId, int primaryPaymentReceiverEmpId)
			throws Exception;

	// get all Payments by SecondaryPaymentReceiverEmpId
	public List<Payment> findPaymentsBySecondaryPaymentReceiverEmpId(int ownerAccountId, int secondaryPaymentReceiverEmpId)
			throws Exception;

	// get all Payments by PrimaryPaymentPayerEmpId
	public List<Payment> findPaymentsByPrimaryPaymentPayerEmpId(int ownerAccountId, int primaryPaymentPayerEmpId)
			throws Exception;

	// get all Payments by SecondaryPaymentPayerEmpId
	public List<Payment> findPaymentsBySecondaryPaymentPayerEmpId(int ownerAccountId, int secondaryPaymentPayerEmpId)
			throws Exception;

	// get all Payments by Notes
	public List<Payment> findPaymentsByNotes(int ownerAccountId, String notes)
			throws Exception;

	// Create services
	public Payment createPayment(UUID userId, int userOwnerAccountId, Payment payment) 
			throws MissingRequiredDataException, InvalidDataValueException, DuplicateKeyException, Exception;

	// Update services
	public Payment updatePayment(UUID userId, Payment payment) 
			throws MissingRequiredDataException, InvalidDataValueException, DuplicateKeyException, Exception;

	// Delete services
	public void deletePayment(UUID userId, int ownerAccountId, long id)
			throws MissingRequiredDataException, Exception;

	// Export/import to/from CSV file
	public void exportPaymentsToCSV(List<Payment> payments, OutputStream os)
			throws Exception;
	public List<Payment> importPaymentsFromCSV(UUID userId, int userOwnerAccountId, InputStream is, boolean bOverrideDup)
			throws Exception;
}
