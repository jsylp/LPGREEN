package org.lpgreen.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.joda.time.DateTime;
import org.lpgreen.domain.Payment;
import org.lpgreen.repository.PaymentDao;
import org.lpgreen.util.InvalidDataValueException;
import org.lpgreen.util.MissingRequiredDataException;
import org.lpgreen.util.UnitsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

/**
 * PaymentManagerImpl is the implementation of the interface PaymentManager for all Payment related objects. 
 * It provides Request CRUD services.
 * CRUD: Create, Read, Update, Delete
 * 
 * Creation date: Mar. 25, 2013
 * Last modify date: Mar. 25, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */
public class PaymentManagerImpl implements PaymentManager {

	// Logger for this class and subclasses
	//protected final Log logger = LogFactory.getLog(getClass());

	private PaymentDao paymentDao;
	@Autowired
	public void setPaymentDao(PaymentDao paymentDao) {
		this.paymentDao = paymentDao;
	}

	////////////////////////////////////////////
	// Payment related methods
	////////////////////////////////////////////
	
	// get all Payments owned by a specific owner account id
	@Override
	public List<Payment> findAllSitePayments(int ownerAccountId, Set<String> currentStatuses)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		List<Payment> payments = paymentDao.findPaymentsByOwnerAccountId(ownerAccountId, currentStatuses);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get a specific Payment by a given database id
	@Override
	public Payment findPaymentById(long id)
			throws Exception {
		if (id <= 0) {
			throw new Exception("Invalud input id");
		}
		return paymentDao.findPaymentById(id);
	}

	// get all Payments by IsReceivedPayment
	@Override
	public List<Payment> findPaymentsByIsReceivedPayment(int ownerAccountId, boolean isReceivedPayment)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		return paymentDao.findPaymentsByIsReceivedPayment(ownerAccountId, isReceivedPayment);
	}

	// get all Payments by PaymentType
	@Override
	public List<Payment> findPaymentsByPaymentType(int ownerAccountId, String paymentType)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (paymentType == null) {
			throw new Exception("Invalud input paymentType");
		}
		List<Payment> payments = paymentDao.findPaymentsByPaymentType(ownerAccountId, paymentType);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by PaymentCategory
	@Override
	public List<Payment> findPaymentsByPaymentCategory(int ownerAccountId, String paymentCategory)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (paymentCategory == null || paymentCategory.isEmpty()) {
			throw new Exception("Invalud input paymentCategory");
		}
		List<Payment> payments = paymentDao.findPaymentsByPaymentCategory(ownerAccountId,
				paymentCategory);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by Description
	@Override
	public List<Payment> findPaymentsByDescription(int ownerAccountId, String description)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (description == null || description.isEmpty()) {
			throw new Exception("Invalud input description");
		}
		List<Payment> payments = paymentDao.findPaymentsByDescription(ownerAccountId,
				description);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by CurrencyCode
	@Override
	public List<Payment> findPaymentsByCurrencyCode(int ownerAccountId, String currencyCode)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (currencyCode == null || currencyCode.isEmpty()) {
			throw new Exception("Invalud input currencyCode");
		}
		List<Payment> payments = paymentDao.findPaymentsByCurrencyCode(ownerAccountId,
				currencyCode);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by TotalAmount range
	@Override
	public List<Payment> findPaymentsByTotalAmountRange(int ownerAccountId,
			double startAmount, double endAmount, String currencyCode)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (startAmount <= 0.0 || endAmount <= 0.0) {
			throw new Exception("startAmount and endAmount must be positive");
		}
		if (currencyCode == null) {
			throw new Exception("currencyCode is not specified");
		}
		if (startAmount > endAmount) {
			double tmp  = startAmount;
			startAmount = endAmount;
			endAmount   = tmp;
		}
		try {
			UnitsUtil unitsUtil = new UnitsUtil();
			List<Payment> retPayments = new ArrayList<Payment>();
			List<Payment> payments = paymentDao.findPaymentsByOwnerAccountId(ownerAccountId, null);
			for (Payment payment : payments) {
				double convTotal = unitsUtil.convertCurrencyUnits(payment.getCurrencyCode(),
						currencyCode, payment.getTotalAmount());
				if (convTotal >= startAmount && convTotal <= endAmount) {
					retPayments.add(payment);
				}
			}
			return retPayments;
		}
		catch (Exception e) {
			System.out.println("PaymentManagerImpl.findPaymentsByTotalAmountRange Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by PaymentMethodType
	@Override
	public List<Payment> findPaymentsByPaymentMethodType(int ownerAccountId, String paymentMethodType)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (paymentMethodType == null || paymentMethodType.isEmpty()) {
			throw new Exception("Invalud input paymentMethodType");
		}
		List<Payment> payments = paymentDao.findPaymentsByPaymentMethodType(ownerAccountId,
				paymentMethodType);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by PayerPaymentMethodId
	@Override
	public List<Payment> findPaymentsByPayerPaymentMethodId(int ownerAccountId, int payerPaymentMethodId)
			throws Exception {
		if (ownerAccountId <= 0 || payerPaymentMethodId <= 0) {
			throw new Exception("Invalud input ownerAccountId or payerPaymentMethodId");
		}
		List<Payment> payments = paymentDao.findPaymentsByPayerPaymentMethodId(ownerAccountId,
				payerPaymentMethodId);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by PayeePaymentReceiveMethodId
	@Override
	public List<Payment> findPaymentsByPayeePaymentReceiveMethodId(int ownerAccountId, int payeePaymentReceiveMethodId)
			throws Exception {
		if (ownerAccountId <= 0 || payeePaymentReceiveMethodId <= 0) {
			throw new Exception("Invalud input ownerAccountId or payerPaymentMethodId");
		}
		List<Payment> payments = paymentDao.findPaymentsByPayeePaymentReceiveMethodId(ownerAccountId,
				payeePaymentReceiveMethodId);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by CheckNumber range
	@Override
	public List<Payment> findPaymentsByCheckNumberRange(int ownerAccountId,
			String startCheckNumber, String endCheckNumber)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if ((startCheckNumber == null || startCheckNumber.isEmpty()) &&
		    (endCheckNumber == null || endCheckNumber.isEmpty())) {
			throw new Exception("Invalud input startCheckNumber or endCheckNumber");
		}
		List<Payment> payments = paymentDao.findPaymentsByCheckNumberRange(ownerAccountId,
				startCheckNumber, endCheckNumber);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by PaymentDateTime range
	@Override
	public List<Payment> findPaymentsByPaymentDateTimeRange(int ownerAccountId,
			DateTime startDateTime, DateTime endDateTime)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (startDateTime == null && endDateTime == null) {
			throw new Exception("Both startDateTime and endDateTime are null");
		}
		List<Payment> payments = paymentDao.findPaymentsByPaymentDateTimeRange(ownerAccountId,
				startDateTime, endDateTime);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by PayerAccountId
	@Override
	public List<Payment> findPaymentsByPayerAccountId(int ownerAccountId, int payerAccountId)
			throws Exception {
		if (ownerAccountId <= 0 || payerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId or payerAccountId");
		}
		List<Payment> payments = paymentDao.findPaymentsByPayerAccountId(ownerAccountId,
				payerAccountId);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by PayerAccountName
	@Override
	public List<Payment> findPaymentsByPayerAccountName(int ownerAccountId, String payerAccountName)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (payerAccountName == null || payerAccountName.isEmpty()) {
			throw new Exception("Invalud input payerAccountName");
		}
		List<Payment> payments = paymentDao.findPaymentsByPayerAccountName(ownerAccountId,
				payerAccountName);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by PayerContactId
	@Override
	public List<Payment> findPaymentsByPayerContactId(int ownerAccountId, UUID payerContactId)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (payerContactId == null || payerContactId.toString() == null ||
		    payerContactId.toString().isEmpty()) {
			throw new Exception("Invalud input payerContactId");
		}
		List<Payment> payments = paymentDao.findPaymentsByPayerContactId(ownerAccountId,
				payerContactId);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by PayerContactName
	@Override
	public List<Payment> findPaymentsByPayerContactName(int ownerAccountId, String payerContactName)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (payerContactName == null || payerContactName.isEmpty()) {
			throw new Exception("Invalud input payerContactName");
		}
		List<Payment> payments = paymentDao.findPaymentsByPayerContactName(ownerAccountId,
				payerContactName);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by PayerBillingAddressId
	@Override
	public List<Payment> findPaymentsByPayerBillingAddressId(int ownerAccountId, UUID payerBillingAddressId)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (payerBillingAddressId == null || payerBillingAddressId.toString() == null ||
		    payerBillingAddressId.toString().isEmpty()) {
			throw new Exception("Invalud input payerBillingAddressId");
		}
		List<Payment> payments = paymentDao.findPaymentsByPayerBillingAddressId(ownerAccountId,
				payerBillingAddressId);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by PayeeAccountId
	@Override
	public List<Payment> findPaymentsByPayeeAccountId(int ownerAccountId, int payeeAccountId)
			throws Exception {
		if (ownerAccountId <= 0 || payeeAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId or payeeAccountId");
		}
		List<Payment> payments = paymentDao.findPaymentsByPayeeAccountId(ownerAccountId,
				payeeAccountId);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by PayeeAccountName
	@Override
	public List<Payment> findPaymentsByPayeeAccountName(int ownerAccountId, String payeeAccountName)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (payeeAccountName == null || payeeAccountName.isEmpty()) {
			throw new Exception("Invalud input payeeAccountName");
		}
		List<Payment> payments = paymentDao.findPaymentsByPayeeAccountName(ownerAccountId,
				payeeAccountName);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by PayeeContactId
	@Override
	public List<Payment> findPaymentsByPayeeContactId(int ownerAccountId, UUID payeeContactId)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (payeeContactId == null || payeeContactId.toString() == null ||
		    payeeContactId.toString().isEmpty()) {
			throw new Exception("Invalud input payeeContactId");
		}
		List<Payment> payments = paymentDao.findPaymentsByPayeeContactId(ownerAccountId,
				payeeContactId);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by PayeeContactName
	@Override
	public List<Payment> findPaymentsByPayeeContactName(int ownerAccountId, String payeeContactName)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (payeeContactName == null || payeeContactName.isEmpty()) {
			throw new Exception("Invalud input payeeContactName");
		}
		List<Payment> payments = paymentDao.findPaymentsByPayeeContactName(ownerAccountId,
				payeeContactName);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by PayeeBillingAddressId
	@Override
	public List<Payment> findPaymentsByPayeeBillingAddressId(int ownerAccountId, UUID payeeBillingAddressId)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (payeeBillingAddressId == null || payeeBillingAddressId.toString() == null ||
		    payeeBillingAddressId.toString().isEmpty()) {
			throw new Exception("Invalud input payeeBillingAddressId");
		}
		List<Payment> payments = paymentDao.findPaymentsByPayeeBillingAddressId(ownerAccountId,
				payeeBillingAddressId);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by DepartmentId
	@Override
	public List<Payment> findPaymentsByDepartmentId(int ownerAccountId, int departmentId)
			throws Exception {
		if (ownerAccountId <= 0 || departmentId <= 0) {
			throw new Exception("Invalud input ownerAccountId or departmentId");
		}
		List<Payment> payments = paymentDao.findPaymentsByDepartmentId(ownerAccountId,
				departmentId);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by CostCenterNumber
	@Override
	public List<Payment> findPaymentsByCostCenterNumber(int ownerAccountId, String costCenterNumber)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (costCenterNumber == null || costCenterNumber.isEmpty()) {
			throw new Exception("Invalud input costCenterNumber");
		}
		List<Payment> payments = paymentDao.findPaymentsByCostCenterNumber(ownerAccountId,
				costCenterNumber);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by PrimaryPaymentReceiverEmpId
	@Override
	public List<Payment> findPaymentsByPrimaryPaymentReceiverEmpId(int ownerAccountId, int primaryPaymentReceiverEmpId)
			throws Exception {
		if (ownerAccountId <= 0 || primaryPaymentReceiverEmpId <= 0) {
			throw new Exception("Invalud input ownerAccountId or primaryPaymentReceiverEmpId");
		}
		List<Payment> payments = paymentDao.findPaymentsByPrimaryPaymentReceiverEmpId(ownerAccountId,
				primaryPaymentReceiverEmpId);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by SecondaryPaymentReceiverEmpId
	@Override
	public List<Payment> findPaymentsBySecondaryPaymentReceiverEmpId(int ownerAccountId, int secondaryPaymentReceiverEmpId)
			throws Exception {
		if (ownerAccountId <= 0 || secondaryPaymentReceiverEmpId <= 0) {
			throw new Exception("Invalud input ownerAccountId or secondaryPaymentReceiverEmpId");
		}
		List<Payment> payments = paymentDao.findPaymentsBySecondaryPaymentReceiverEmpId(ownerAccountId,
				secondaryPaymentReceiverEmpId);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by PrimaryPaymentPayerEmpId
	@Override
	public List<Payment> findPaymentsByPrimaryPaymentPayerEmpId(int ownerAccountId, int primaryPaymentPayerEmpId)
			throws Exception {
		if (ownerAccountId <= 0 || primaryPaymentPayerEmpId <= 0) {
			throw new Exception("Invalud input ownerAccountId or primaryPaymentPayerEmpId");
		}
		List<Payment> payments = paymentDao.findPaymentsByPrimaryPaymentPayerEmpId(ownerAccountId,
				primaryPaymentPayerEmpId);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by SecondaryPaymentPayerEmpId
	@Override
	public List<Payment> findPaymentsBySecondaryPaymentPayerEmpId(int ownerAccountId, int secondaryPaymentPayerEmpId)
			throws Exception {
		if (ownerAccountId <= 0 || secondaryPaymentPayerEmpId <= 0) {
			throw new Exception("Invalud input ownerAccountId or secondaryPaymentPayerEmpId");
		}
		List<Payment> payments = paymentDao.findPaymentsBySecondaryPaymentPayerEmpId(ownerAccountId,
				secondaryPaymentPayerEmpId);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// get all Payments by Notes
	@Override
	public List<Payment> findPaymentsByNotes(int ownerAccountId, String notes)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (notes == null || notes.isEmpty()) {
			throw new Exception("Invalud input notes");
		}
		List<Payment> payments = paymentDao.findPaymentsByNotes(ownerAccountId,
				notes);
		if (payments != null && payments.size() == 0)
			payments = null;
		return payments;
	}

	// Create services

	private void validatePaymentData(Payment payment)
			throws MissingRequiredDataException, InvalidDataValueException,
					DuplicateKeyException, Exception {
		if (payment == null)
			throw new Exception("Missing payment");
		if (payment.getPaymentCategory() == null || payment.getPaymentCategory().isEmpty())
			throw new Exception("Missing required PaymentCategory");
		if (payment.getDescription() == null || payment.getDescription().isEmpty())
			throw new Exception("Missing required Description");
		if (payment.getCurrencyCode() == null || payment.getCurrencyCode().isEmpty())
			throw new Exception("Missing required CurrencyCode");
		if (payment.getTotalAmount() <= 0.0)
			throw new Exception("Invalid TotalAmount");
		// PaymentMethodType or payerPaymentMethodId must not be null
		if ((payment.getPaymentMethodType() == null || payment.getPaymentMethodType().isEmpty()) &&
		     payment.getPayerPaymentMethodId() <= 0)
			throw new Exception("Both PaymentMethodType and payerPaymentMethodId are missing");
		// One of the PayerAccountId, PayerAccountName, PayerContactId,
		// or PayerContactName must not be null
		if ((payment.getPayerAccountId() <= 0) &&
		    (payment.getPayerAccountName() == null || payment.getPayerAccountName().isEmpty()) &&
		    (payment.getPayerContactId() == null || payment.getPayerContactId().toString() == null ||
		     payment.getPayerContactId().toString().isEmpty())                                 &&
		    (payment.getPayerContactName() == null || payment.getPayerContactName().isEmpty()))
			throw new Exception("None of PayerAccountId, PayerAccountName, PayerContactId and PayerContactName is available");
		// One of the PayeeAccountId, PayeeAccountName, PayeeContactId,
		// or PayeeContactName must not be null
		if ((payment.getPayeeAccountId() <= 0) &&
		    (payment.getPayeeAccountName() == null || payment.getPayeeAccountName().isEmpty()) &&
		    (payment.getPayeeContactId() == null || payment.getPayeeContactId().toString() == null ||
		     payment.getPayeeContactId().toString().isEmpty())                                 &&
		    (payment.getPayeeContactName() == null || payment.getPayeeContactName().isEmpty()))
			throw new Exception("None of PayeeAccountId, PayeeAccountName, PayeeContactId and PayeeContactName is available");
	}

	@Override
	public Payment createPayment(UUID userId, int userOwnerAccountId, Payment payment) 
			throws MissingRequiredDataException, InvalidDataValueException,
					DuplicateKeyException, Exception {
		//if (userId == null)
		//	throw new MissingRequiredDataException("Missing userId");
		if (userOwnerAccountId <= 0)
			throw new MissingRequiredDataException("Missing userOwnerAccountId");

		this.validatePaymentData(payment);

		// Good to go
		//DateTime currentDateTime = DateTime.now();		

		// Create the Payment
		//payment.setCreatedDate(currentDateTime);
		//payment.setCreatedById(userId);
		//payment.setLastModifiedDate(currentDateTime);
		//payment.setLastModifiedById(userId);
		//payment.setOwnerId(userId);
		payment.setOwnerAccountId(userOwnerAccountId);

		// Persist the Payment object
		try {
			long retId = paymentDao.addPayment(payment);
			payment.setId(retId);

			// retrieve the new data back
			Payment retPayment = this.findPaymentById(retId);
			return retPayment;
		}
		catch (Exception e) {
			//logger.info("PaymentManagerImpl.createPayment: fail to create Payment. Exception: " + e.getMessage());
			throw e;
		}
	}

	// Update services

	@Override
	public Payment updatePayment(UUID userId, Payment payment) 
			throws MissingRequiredDataException, InvalidDataValueException,
				DuplicateKeyException, Exception {
		//if (userId == null)
		//	throw new MissingRequiredDataException("Missing userId");

		this.validatePaymentData(payment);
		
		// Good to go
		//DateTime currentDateTime = DateTime.now();		
		try {
			// Persist the Payment changes
			//payment.setLastModifiedDate(currentDateTime);
			//payment.setLastModifiedById(userId);

			@SuppressWarnings("unused")
			int numRecordUpdated = paymentDao.savePayment(payment);

			// retrieve the new data back
			Payment retPayment = this.findPaymentById(payment.getId());
			return retPayment;
		}
		catch (Exception e) {
			//logger.info("PaymentManagerImpl.updatePayment: fail to update Payment. Exception: " + e.getMessage());
			throw e;
		}
		
	}

	// Delete services

	@Override
	public void deletePayment(UUID userId, int ownerAccountId, long id)
			throws MissingRequiredDataException, Exception {
		//if (userId == null)
		//	throw new MissingRequiredDataException("Missing userId");
		if (ownerAccountId <= 0)
			throw new MissingRequiredDataException("Missing ownerAccountId");
		if (id <= 0)
			throw new MissingRequiredDataException("Missing id");

		int numRecordDeleted = paymentDao.deletePayment(ownerAccountId, id);
		if (numRecordDeleted == 0)
			throw new Exception("Fail to delete Payment: " + id);
	}

	// Export/import to/from CSV file

	@Override
	public void exportPaymentsToCSV(List<Payment> payments, OutputStream os)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Payment> importPaymentsFromCSV(UUID userId,
			int userOwnerAccountId, InputStream is, boolean bOverrideDup)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
