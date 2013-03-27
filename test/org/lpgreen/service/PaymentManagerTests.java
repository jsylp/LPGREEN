package org.lpgreen.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.lpgreen.domain.Payment;
import org.lpgreen.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * It is the PaymentManager junit testing class. 
 * 
 * Creation date: Mar. 26, 2013
 * Last modify date: Mar. 26, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class PaymentManagerTests extends AbstractTransactionalDataSourceSpringContextTests {
	private UUID currentUserId;
	private int  currentUserOwnerAccountId;

	private PaymentManager paymentManager;
	@Autowired
	public void setPaymentManager(PaymentManager paymentManager) {
		this.paymentManager = paymentManager;
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] {"classpath:test-context.xml"};
	}

	@Override
	protected void onSetUpInTransaction() throws Exception {
		currentUserId = UUID.fromString("86291951-969e-4b5b-ab37-29a5749544ed"); // sysadmin@logixpath.com, used as the object owner id
		currentUserOwnerAccountId = 1;
	}

	// get all Payments by TotalAmount range
	public void testFindPaymentsByTotalAmountRange() {
		try {
			double startAmount = 100.00;
			double endAmount = 500.00;
			System.out.println("Test --> testFindPaymentsByTotalAmountRange");
			System.out.println("    testFindPaymentsByTotalAmountRange [OwnerAccountId=1, startAmount=" +
					startAmount + ", endAmount=" + endAmount + ", currentCode=USD]");
			List<Payment> payments = paymentManager.findPaymentsByTotalAmountRange(
					currentUserOwnerAccountId, startAmount, endAmount, "USD");
			assertNotNull(payments);
			assertTrue(payments.size() == 2);
			for (Payment payment : payments) {
				System.out.println("Payment: id=" + payment.getId() +
					"; isReceivedPayment=" + payment.getIsReceivedPayment() +
					"; paymentType=" + payment.getPaymentType() +
					"; paymentCategory=" + payment.getPaymentCategory() +
					"; description=" + payment.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("PaymentManagerTests.testFindPaymentsByTotalAmountRange Exception: " + e.getMessage());
		}
	}

	public void testAddAndUpdatePayment() {
		System.out.println("Test --> testAddAndUpdatePayment");

		UUID ownerId = UUID.randomUUID();
		UUID payerContactId = UUID.fromString("07771AE4-236A-49d3-A49E-B1F9E1934D40");
		UUID payerBillingAddressId = UUID.randomUUID();
		UUID payeeContactId = UUID.fromString("07771AE4-236A-49d3-A49E-B1F9E1934D41");
		UUID payeeBillingAddressId = UUID.randomUUID();
		DateTime paymentDateTime = StringUtil.parseUTCDateTimeFromString_HHmmss("2013-01-15 06:08:08");
		
		Payment payment = new Payment();
		payment.setOwnerId(ownerId);
		payment.setOwnerAccountId(1);
		payment.setIsReceivedPayment(true);
		payment.setPaymentType("Capital");
		payment.setPaymentCategory("Software");
		payment.setDescription("License for 5 seat database");
		payment.setCurrencyCode("USD");
		payment.setTotalAmount(200000.00);
		payment.setPaymentMethodType("PO");
		payment.setPayerPaymentMethodId(2);
		payment.setPayeePaymentReceiveMethodId(1);
		payment.setCheckNumber("320");
		payment.setPaymentDateTime(paymentDateTime);
		payment.setPayerAccountId(2);
		payment.setPayerAccountName("Bobby Company");
		payment.setPayerContactId(payerContactId);
		payment.setPayerContactName("Cathy");
		payment.setPayerBillingAddressId(payerBillingAddressId);
		payment.setPayeeAccountId(5);
		payment.setPayerAccountName("Darren Company");
		payment.setPayerContactId(payeeContactId);
		payment.setPayerContactName("Carl");
		payment.setPayerBillingAddressId(payeeBillingAddressId);
		payment.setDepartmentId(7);
		payment.setCostCenterNumber("11206534");
		payment.setPrimaryPaymentReceiverEmpId(5);
		payment.setSecondaryPaymentReceiverEmpId(10);
		payment.setPrimaryPaymentPayerEmpId(3);
		payment.setSecondaryPaymentPayerEmpId(8);
		payment.setNotes("2014-2015 Lincense Fee");
	
		// Create the Payment
		Payment retPayment = null;
		try {
			retPayment = paymentManager.createPayment(
					currentUserId, currentUserOwnerAccountId, payment);
			assertNotNull(retPayment);
			assertEquals(retPayment.getPaymentType(), "Capital");
			assertEquals(retPayment.getPaymentCategory(), "Software");
			assertEquals(retPayment.getDescription(), "License for 5 seat database");
			assertEquals(retPayment.getCurrencyCode(), "USD");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	
		// Update the Payment
		try {
			retPayment.setCostCenterNumber("43560211");
			retPayment.setNotes("Make changes in notes");

			Payment retPaymentUpd = paymentManager.updatePayment(
					currentUserId, retPayment);
			assertNotNull(retPayment);
			assertEquals(retPaymentUpd.getCostCenterNumber(), "43560211");
			assertEquals(retPaymentUpd.getNotes(), "Make changes in notes");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		// Delete the Payment
		try {
			paymentManager.deletePayment(currentUserId, currentUserOwnerAccountId, retPayment.getId());
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
