package org.lpgreen.repository;

import java.util.List;
import java.util.UUID;

import org.lpgreen.util.StringUtil;

import org.joda.time.DateTime;
import org.lpgreen.domain.Payment;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * It is the Payment JDBC implementation testing class. 
 * 
 * Creation date: Mar. 19, 2013
 * Last modify date: Mar. 19, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

@SuppressWarnings("deprecation")
public class JdbcPaymentDaoTests  extends AbstractTransactionalDataSourceSpringContextTests  {

	private PaymentDao paymentDao;
	public void setPaymentDao(PaymentDao paymentDao) {
		this.paymentDao = paymentDao;
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] {"classpath:test-context.xml"};
	}

	@Override
	protected void onSetUpInTransaction() throws Exception {
	}

	public void testFindAllSitePayments() {
		try {
			// Test to find the LogixPath (OwnerAccountId=1) all Payments 
			System.out.println("Test --> testFindAllSitePayments");
			System.out.println("    testFindAllSitePayments [OwnerAccountId=1]");
			List<Payment> payments = paymentDao.findPaymentsByOwnerAccountId(1, null);
			assertNotNull(payments);
			assertTrue(payments.size() == 4);
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
			System.out.println("JdbcPaymentDaoTests.testFindAllSitePayments Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentById() {
		try {
			// Test to find Payment by Payment Id
			System.out.println("Test --> testFindPaymentById");
			System.out.println("    testFindPaymentById [Id=2]");
			Payment payment = paymentDao.findPaymentById(2);
			assertNotNull(payment);
			System.out.println("Payment: id=" + payment.getId() +
					"; isReceivedPayment=" + payment.getIsReceivedPayment() +
					"; paymentType=" + payment.getPaymentType() +
					"; paymentCategory=" + payment.getPaymentCategory() +
					"; description=" + payment.getDescription());
			assertTrue(payment.getId() == 2);
			assertTrue(payment.getPaymentType() != null && payment.getPaymentType().equals("Reimburse"));
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDaoTests.testFindPaymentById Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByIsReceivedPayment() {
		try {
			// Test to find Payments by IsReceivedPayment 
			System.out.println("Test --> testFindPaymentsByIsReceivedPayment");
			System.out.println("    testFindPaymentsByIsReceivedPayment [OwnerAccountId=1, IsReceivedPayment=true]");
			List<Payment> payments = paymentDao.findPaymentsByIsReceivedPayment(1, true);
			assertNotNull(payments);
			assertTrue(payments.size() == 3);
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByIsReceivedPayment Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByPaymentType() {
		try {
			// Test to find the Payments by PaymentType
			System.out.println("Test --> testFindPaymentsByPaymentType");
			System.out.println("    testFindPaymentsByPaymentType [OwnerAccountId=1, PaymentType=Corporate]");
			List<Payment> payments = paymentDao.findPaymentsByPaymentType(1, "Corporate");
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByPaymentType Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByPaymentCategory() {
		try {
			// Test to find the Payments by PaymentCategory
			System.out.println("Test --> testFindPaymentsByPaymentCategory");
			System.out.println("    testFindPaymentsByPaymentCategory [OwnerAccountId=1, PaymentCategory=Travel]");
			List<Payment> payments = paymentDao.findPaymentsByPaymentCategory(1, "Travel");
			assertNotNull(payments);
			assertTrue(payments.size() == 1);
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByPaymentCategory Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByDescription() {
		try {
			// Test to find the Payments by Description
			System.out.println("Test --> testFindPaymentsByDescription");
			System.out.println("    testFindPaymentsByDescription [OwnerAccountId=1, Description=Online class]");
			List<Payment> payments = paymentDao.findPaymentsByDescription(1, "Online class");
			assertNotNull(payments);
			assertTrue(payments.size() == 1);
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByDescription Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByCurrencyCode() {
		try {
			// Test to find the Payments by CurrencyCode
			System.out.println("Test --> testFindPaymentsByCurrencyCode");
			System.out.println("    testFindPaymentsByCurrencyCode [OwnerAccountId=1, CurrencyCode=USD]");
			List<Payment> payments = paymentDao.findPaymentsByCurrencyCode(1, "USD");
			assertNotNull(payments);
			assertTrue(payments.size() == 4);
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByCurrencyCode Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByPaymentMethodType() {
		try {
			// Test to find the Payments by PaymentMethodType
			System.out.println("Test --> testFindPaymentsByPaymentMethodType");
			System.out.println("    testFindPaymentsByPaymentMethodType [OwnerAccountId=1, PaymentMethodType=Check]");
			List<Payment> payments = paymentDao.findPaymentsByPaymentMethodType(1, "Check");
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByPaymentMethodType Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByPayerPaymentMethodId() {
		try {
			// Test to find the Payments by PayerPaymentMethodId
			System.out.println("Test --> testFindPaymentsByPayerPaymentMethodId");
			System.out.println("    testFindPaymentsByPayerPaymentMethodId [OwnerAccountId=1, PayerPaymentMethodId=2]");
			List<Payment> payments = paymentDao.findPaymentsByPayerPaymentMethodId(1, 2);
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByPayerPaymentMethodId Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByPayeePaymentReceiveMethodId() {
		try {
			// Test to find the Payments by PayeePaymentReceiveMethodId
			System.out.println("Test --> testFindPaymentsByPayeePaymentReceiveMethodId");
			System.out.println("    testFindPaymentsByPayeePaymentReceiveMethodId [OwnerAccountId=1, PayeePaymentReceiveMethodId=16]");
			List<Payment> payments = paymentDao.findPaymentsByPayeePaymentReceiveMethodId(1, 16);
			assertNotNull(payments);
			assertTrue(payments.size() == 1);
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByPayeePaymentReceiveMethodId Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByCheckNumberRange() {
		try {
			// Test to find the Payments by CheckNumberRange
			System.out.println("Test --> testFindPaymentsByCheckNumberRange");
			System.out.println("    testFindPaymentsByCheckNumberRange [OwnerAccountId=1, startCheckNumber=100, endCheckNumber=105]");
			List<Payment> payments = paymentDao.findPaymentsByCheckNumberRange(1, "100", "105");
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByCheckNumberRange Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByPaymentDateTimeRange() {
		try {
			// Test to find the Payments by PaymentDateTimeRange
			System.out.println("Test --> testFindPaymentsByPaymentDateTimeRange");
			System.out.println("    testFindPaymentsByPaymentDateTimeRange [OwnerAccountId=1, range=[startDateTime, endDateTime]]");
			DateTime startDateTime = StringUtil.parseUTCDateTimeFromString_HHmmss("2013-01-10 06:08:08");
			DateTime endDateTime = StringUtil.parseUTCDateTimeFromString_HHmmss("2013-02-15 06:08:08");
			List<Payment> payments = paymentDao.findPaymentsByPaymentDateTimeRange(1, startDateTime, endDateTime);
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
			System.out.println("JdbcProjectDaoTests.testFindPaymentsByPaymentDateTimeRange Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByPayerAccountId() {
		try {
			// Test to find the Payments by PayerAccountId
			System.out.println("Test --> testFindPaymentsByPayerAccountId");
			System.out.println("    testFindPaymentsByPayerAccountId [OwnerAccountId=1, PayerAccountId=2]");
			List<Payment> payments = paymentDao.findPaymentsByPayerAccountId(1, 2);
			assertNotNull(payments);
			assertTrue(payments.size() == 3);
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByPayerAccountId Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByPayerAccountName() {
		try {
			// Test to find the Payments by PayerAccountName
			System.out.println("Test --> testFindPaymentsByPayerAccountName");
			System.out.println("    testFindPaymentsByPayerAccountName [OwnerAccountId=1, PayerAccountName=Bobby Company]");
			List<Payment> payments = paymentDao.findPaymentsByPayerAccountName(1, "Bobby Company");
			assertNotNull(payments);
			assertTrue(payments.size() == 1);
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByPayerAccountName Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByPayerContactId() {
		try {
			// Test to find the projects by PayerContactId
			System.out.println("Test --> testFindPaymentsByPayerContactId");
			System.out.println("    testFindPaymentsByPayerContactId [OwnerAccountId=1, PayerContactId=07771AE4-236A-49d3-A49E-B1F9E1934D20]");
			List<Payment> payments = paymentDao.findPaymentsByPayerContactId(1,
					UUID.fromString("07771AE4-236A-49d3-A49E-B1F9E1934D20"));
			assertNotNull(payments);
			assertTrue(payments.size() == 3);
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByPayerContactId Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByPayerContactName() {
		try {
			// Test to find the Payments by PayerContactName
			System.out.println("Test --> testFindPaymentsByPayerContactName");
			System.out.println("    testFindPaymentsByPayerContactName [OwnerAccountId=1, PayerContactName=Allen]");
			List<Payment> payments = paymentDao.findPaymentsByPayerContactName(1, "Allen");
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByPayerContactName Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByPayerBillingAddressId() {
		try {
			// Test to find the projects by PayerBillingAddressId
			System.out.println("Test --> testFindPaymentsByPayerBillingAddressId");
			System.out.println("    testFindPaymentsByPayerBillingAddressId [OwnerAccountId=1, PayerBillingAddressId=07771AE4-236A-49d3-A49E-B1F9E1934D20]");
			List<Payment> payments = paymentDao.findPaymentsByPayerBillingAddressId(1,
					UUID.fromString("07771AE4-236A-49d3-A49F-B1F9E1934D20"));
			assertNotNull(payments);
			assertTrue(payments.size() == 3);
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByPayerBillingAddressId Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByPayeeAccountId() {
		try {
			// Test to find the Payments by PayeeAccountId
			System.out.println("Test --> testFindPaymentsByPayeeAccountId");
			System.out.println("    testFindPaymentsByPayeeAccountId [OwnerAccountId=1, PayeeAccountId=2]");
			List<Payment> payments = paymentDao.findPaymentsByPayeeAccountId(1, 2);
			assertNotNull(payments);
			assertTrue(payments.size() == 3);
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByPayeeAccountId Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByPayeeAccountName() {
		try {
			// Test to find the Payments by PayeeAccountName
			System.out.println("Test --> testFindPaymentsByPayeeAccountName");
			System.out.println("    testFindPaymentsByPayeeAccountName [OwnerAccountId=1, PayeeAccountName=Bobby Company]");
			List<Payment> payments = paymentDao.findPaymentsByPayeeAccountName(1, "Bobby Company");
			assertNotNull(payments);
			assertTrue(payments.size() == 1);
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByPayeeAccountName Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByPayeeContactId() {
		try {
			// Test to find the projects by PayeeContactId
			System.out.println("Test --> testFindPaymentsByPayeeContactId");
			System.out.println("    testFindPaymentsByPayeeContactId [OwnerAccountId=1, PayeeContactId=07771AE4-236A-49d3-A49E-B1F9E1934D31]");
			List<Payment> payments = paymentDao.findPaymentsByPayeeContactId(1,
					UUID.fromString("07771AE4-236A-49d3-A49E-B1F9E1934D31"));
			assertNotNull(payments);
			assertTrue(payments.size() == 1);
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByPayeeContactId Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByPayeeContactName() {
		try {
			// Test to find the Payments by PayeeContactName
			System.out.println("Test --> testFindPaymentsByPayeeContactName");
			System.out.println("    testFindPaymentsByPayeeContactName [OwnerAccountId=1, PayeeContactName=Allen]");
			List<Payment> payments = paymentDao.findPaymentsByPayeeContactName(1, "Alex");
			assertNotNull(payments);
			assertTrue(payments.size() == 1);
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByPayeeContactName Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByPayeeBillingAddressId() {
		try {
			// Test to find the projects by PayeeBillingAddressId
			System.out.println("Test --> testFindPaymentsByPayeeBillingAddressId");
			System.out.println("    testFindPaymentsByPayeeBillingAddressId [OwnerAccountId=1, PayeeBillingAddressId=07771AE4-236A-49d3-A49F-B1F9E1934D22]");
			List<Payment> payments = paymentDao.findPaymentsByPayeeBillingAddressId(1,
					UUID.fromString("07771AE4-236A-49d3-A49F-B1F9E1934D22"));
			assertNotNull(payments);
			assertTrue(payments.size() == 1);
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByPayeeBillingAddressId Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByDepartmentId() {
		try {
			// Test to find the projects by DepartmentId
			System.out.println("Test --> testFindPaymentsByDepartmentId");
			System.out.println("    testFindPaymentsByDepartmentId [OwnerAccountId=1, DepartmentId=7]");
			List<Payment> payments = paymentDao.findPaymentsByDepartmentId(1, 7);
			assertNotNull(payments);
			assertTrue(payments.size() == 1);
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByDepartmentId Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByCostCenterNumber() {
		try {
			// Test to find the projects by CostCenterNumber
			System.out.println("Test --> testFindPaymentsByCostCenterNumber");
			System.out.println("    testFindPaymentsByCostCenterNumber [OwnerAccountId=1, CostCenterNumber=056-112-105]");
			List<Payment> payments = paymentDao.findPaymentsByCostCenterNumber(1, "056-112-105");
			assertNotNull(payments);
			assertTrue(payments.size() == 1);
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByCostCenterNumber Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByPrimaryPaymentReceiverEmpId() {
		try {
			// Test to find the projects by PrimaryPaymentReceiverEmpId
			System.out.println("Test --> testFindPaymentsByPrimaryPaymentReceiverEmpId");
			System.out.println("    testFindPaymentsByPrimaryPaymentReceiverEmpId [OwnerAccountId=1, PrimaryPaymentReceiverEmpId=11]");
			List<Payment> payments = paymentDao.findPaymentsByPrimaryPaymentReceiverEmpId(1, 11);
			assertNotNull(payments);
			assertTrue(payments.size() == 1);
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByPrimaryPaymentReceiverEmpId Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsBySecondaryPaymentReceiverEmpId() {
		try {
			// Test to find the projects by SecondaryPaymentReceiverEmpId
			System.out.println("Test --> testFindPaymentsBySecondaryPaymentReceiverEmpId");
			System.out.println("    testFindPaymentsBySecondaryPaymentReceiverEmpId [OwnerAccountId=1, SecondaryPaymentReceiverEmpId=11]");
			List<Payment> payments = paymentDao.findPaymentsBySecondaryPaymentReceiverEmpId(1, 5);
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsBySecondaryPaymentReceiverEmpId Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByPrimaryPaymentPayerEmpId() {
		try {
			// Test to find the projects by PrimaryPaymentPayerEmpId
			System.out.println("Test --> testFindPaymentsByPrimaryPaymentPayerEmpId");
			System.out.println("    testFindPaymentsByPrimaryPaymentPayerEmpId [OwnerAccountId=1, PrimaryPaymentPayerEmpId=4]");
			List<Payment> payments = paymentDao.findPaymentsByPrimaryPaymentPayerEmpId(1, 4);
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByPrimaryPaymentPayerEmpId Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsBySecondaryPaymentPayerEmpId() {
		try {
			// Test to find the projects by SecondaryPaymentPayerEmpId
			System.out.println("Test --> testFindPaymentsBySecondaryPaymentPayerEmpId");
			System.out.println("    testFindPaymentsBySecondaryPaymentPayerEmpId [OwnerAccountId=1, SecondaryPaymentPayerEmpId=4]");
			List<Payment> payments = paymentDao.findPaymentsBySecondaryPaymentPayerEmpId(1, 3);
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsBySecondaryPaymentPayerEmpId Exception: " + e.getMessage());
		}
	}

	public void testFindPaymentsByNotes() {
		try {
			// Test to find the projects by Notes
			System.out.println("Test --> testFindPaymentsByNotes");
			System.out.println("    testFindPaymentsByNotes [OwnerAccountId=1, Notes=Semester grade B+]");
			List<Payment> payments = paymentDao.findPaymentsByNotes(1, "Semester grade B+");
			assertNotNull(payments);
			assertTrue(payments.size() == 1);
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
			System.out.println("JdbcPaymentDaoTests.testFindPaymentsByNotes Exception: " + e.getMessage());
		}
	}

	public void testAddUpdateDeletePayment() {
		// Create Payment
		// Note: createdDate, createdById, lastModifiedDate and lastModifiedById
		// are not used when inserting values to the database. These are the
		// first four parameters to the constructor.
		UUID ownerId = UUID.randomUUID();
		UUID payerContactId = UUID.fromString("07771AE4-236A-49d3-A49E-B1F9E1934D40");
		UUID payerBillingAddressId = UUID.randomUUID();
		UUID payeeContactId = UUID.fromString("07771AE4-236A-49d3-A49E-B1F9E1934D41");
		UUID payeeBillingAddressId = UUID.randomUUID();
		DateTime paymentDateTime = StringUtil.parseUTCDateTimeFromString_HHmmss("2013-01-15 06:08:08");
		Payment payment = new Payment(null, null, null, null,
				ownerId, 1, true, "Reimburse", "Meals", "Team lunch", "USD", 121.0,
				"Check", 1, 2, "156", paymentDateTime,
				2, "New Company", payerContactId, "Cathy", payerBillingAddressId,
				5, "New Company", payeeContactId, "Carl", payeeBillingAddressId,
				6, "101-326-512", 6, 5, 4, 3, "Finsihed milestone 2");
		assertNotNull(payment);

		System.out.println("Test --> add, update and delete Payment");
		System.out.println("    Add Payment [OwnerAccountId=" + payment.getOwnerAccountId() + "]");
		Payment retPayment = null;
		try {
			long retId = paymentDao.addPayment(payment);
			assertTrue(retId > 0);
			retPayment = paymentDao.findPaymentById(retId);
			assertNotNull(retPayment);
			assertEquals(retPayment.getPaymentType(), "Reimburse");
			assertEquals(retPayment.getPaymentCategory(), "Meals");
			assertEquals(retPayment.getDescription(), "Team lunch");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		// Update Payment
		System.out.println("    Update Payment");
		retPayment.setDescription("The new test payment");
		try {
			int numRecUpdated = paymentDao.savePayment(retPayment);
			assertEquals(numRecUpdated, 1);
			Payment retPaymentUpd = paymentDao.findPaymentById(retPayment.getId());
			assertNotNull(retPaymentUpd);
			assertEquals(retPaymentUpd.getPaymentType(), "Reimburse");
			assertEquals(retPaymentUpd.getPaymentCategory(), "Meals");
			assertEquals(retPaymentUpd.getDescription(), "The new test payment");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		// Delete Project
		System.out.println("    Delete Payment");
		try {
			int numRecDeleted = paymentDao.deletePayment(1, retPayment.getId());
			assertEquals(numRecDeleted, 1);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		System.out.println("     <-- Done.");
	}
}
