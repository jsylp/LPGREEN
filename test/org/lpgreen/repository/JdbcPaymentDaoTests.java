package org.lpgreen.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
			List<Payment> payments = paymentDao.findPaymentsByOwnerAccountId(1);
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

	public void testFindAllSitePayments2() {
		try {
			// Test to find the LogixPath (OwnerAccountId=1) all Payments 
			System.out.println("Test --> testFindAllSitePayments2");
			System.out.println("    testFindAllSitePayments2 [OwnerAccountId=1]");
			List<Payment> payments = paymentDao.findPaymentsByOwnerAccountId2(1);
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
			System.out.println("    testFindPaymentById [OwnerAccountId=1, Id=2]");
			Payment payment = paymentDao.findPaymentById(1, 2);
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
			System.out.println("JdbcProjectDaoTests.testFindPaymentsByPaymentCategory Exception: " + e.getMessage());
		}
	}

	/*
	public void testFindProjectsByProjectManager2Id() {
		try {
			// Test to find the projects by manager2 id
			System.out.println("Test --> testFindProjectsByProjectManager2Id");
			System.out.println("    testFindProjectsByProjectManager2Id [OwnerAccountId=2, Manager2Id=5]");
			Set<String> currentPhases = new HashSet<String>();
			currentPhases.add("Construction");
			List<Project> projects = projectDao.findProjectsByProjectManager2Id(2, 5, currentPhases);
			assertNotNull(projects);
			assertTrue(projects.size() == 3);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; code=" + project.getProjectCode() +
						"; Phase=" + project.getCurrentPhase() + "; description=" + project.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindProjectsByProjectManager2Id Exception: " + e.getMessage());
		}
	}

	public void testFindProjectsByCustomerAccount() {
		try {
			// Test to find the projects by customer account
			System.out.println("Test --> testFindProjectsByCustomerAccount");
			System.out.println("    testFindProjectsByCustomerAccount [OwnerAccountId=2, CustomerAccount=3]");
			Set<String> currentPhases = new HashSet<String>();
			currentPhases.add("Construction");
			List<Project> projects = projectDao.findProjectsByCustomerAccount(2, 3, currentPhases);
			assertNotNull(projects);
			assertTrue(projects.size() == 2);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; code=" + project.getProjectCode() +
						"; Phase=" + project.getCurrentPhase() + "; description=" + project.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindProjectsByCustomerAccount Exception: " + e.getMessage());
		}
	}

	public void testFindProjectsByCustomerContact() {
		try {
			// Test to find the projects by customer contact
			System.out.println("Test --> testFindProjectsByCustomerContact");
			System.out.println("    testFindProjectsByCustomerContact [OwnerAccountId=2, CustomerContact=07771AE4-236A-49d3-A49E-B1F9E1934D31]");
			Set<String> currentPhases = new HashSet<String>();
			currentPhases.add("Construction");
			List<Project> projects = projectDao.findProjectsByCustomerContact(2,
					UUID.fromString("07771AE4-236A-49d3-A49E-B1F9E1934D31"), currentPhases);
			assertNotNull(projects);
			assertTrue(projects.size() == 1);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; code=" + project.getProjectCode() +
						"; Phase=" + project.getCurrentPhase() + "; description=" + project.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindProjectsByCustomerContact Exception: " + e.getMessage());
		}
	}

	public void testFindProjectsBySponsor() {
		try {
			// Test to find the projects by sponsor
			System.out.println("Test --> testFindProjectsBySponsor");
			System.out.println("    testFindProjectsBySponsor [OwnerAccountId=2, Sponsor=07771AE4-236A-49d3-A49E-B1F9E1934D20]");
			Set<String> currentPhases = new HashSet<String>();
			currentPhases.add("Construction");
			List<Project> projects = projectDao.findProjectsBySponsor(2,
					UUID.fromString("07771AE4-236A-49d3-A49E-B1F9E1934D20"), currentPhases);
			assertNotNull(projects);
			assertTrue(projects.size() == 2);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; code=" + project.getProjectCode() +
						"; Phase=" + project.getCurrentPhase() + "; description=" + project.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindProjectsBySponsor Exception: " + e.getMessage());
		}
	}

	public void testFindProjectsByManagingDeptId() {
		try {
			// Test to find the projects by managing dept id
			System.out.println("Test --> testFindProjectsByManagingDeptId");
			System.out.println("    testFindProjectsByManagingDeptId [OwnerAccountId=2, ManagingDeptId=5]");
			Set<String> currentPhases = new HashSet<String>();
			currentPhases.add("Construction");
			List<Project> projects = projectDao.findProjectsByManagingDeptId(2, 5, currentPhases);
			assertNotNull(projects);
			assertTrue(projects.size() == 2);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; code=" + project.getProjectCode() +
						"; Phase=" + project.getCurrentPhase() + "; description=" + project.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindProjectsByManagingDeptId Exception: " + e.getMessage());
		}
	}

	public void testFindProjectsByStartDateRange() {
		try {
			// Test to find the projects by managing dept id
			System.out.println("Test --> testFindProjectsByStartDateRange");
			System.out.println("    testFindProjectsByStartDateRange [OwnerAccountId=2, range=[fromDate, toDate]]");
			DateTime startDate = StringUtil.parseUTCDateTimeFromString_HHmmss("2011-06-02 06:08:08");
			DateTime endDate = StringUtil.parseUTCDateTimeFromString_HHmmss("2012-12-30 06:08:08");
			List<Project> projects = projectDao.findProjectsByStartDateRange(2, startDate, endDate);
			assertNotNull(projects);
			assertTrue(projects.size() == 3);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; code=" + project.getProjectCode() +
						"; Phase=" + project.getCurrentPhase() + "; description=" + project.getDescription());
			}
			startDate = StringUtil.parseUTCDateTimeFromString_HHmmss("2013-04-01 06:08:08");
			endDate = StringUtil.parseUTCDateTimeFromString_HHmmss("2013-06-30 06:08:08");
			projects = projectDao.findProjectsByStartDateRange(2, startDate, endDate);
			assertNotNull(projects);
			assertTrue(projects.size() == 1);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; code=" + project.getProjectCode() +
						"; Phase=" + project.getCurrentPhase() + "; description=" + project.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindProjectsByStartDateRange Exception: " + e.getMessage());
		}
	}

	public void testFindProjectsByEndDateRange() {
		try {
			// Test to find the projects by managing dept id
			System.out.println("Test --> testFindProjectsByEndDateRange");
			System.out.println("    testFindProjectsByEndDateRange [OwnerAccountId=2, range=[fromDate, toDate]]");
			DateTime startDate = StringUtil.parseUTCDateTimeFromString_HHmmss("2015-02-01 06:08:08");
			DateTime endDate = StringUtil.parseUTCDateTimeFromString_HHmmss("2016-12-30 06:08:08");
			List<Project> projects = projectDao.findProjectsByEndDateRange(2, startDate, endDate);
			assertNotNull(projects);
			assertTrue(projects.size() == 2);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; code=" + project.getProjectCode() +
						"; Phase=" + project.getCurrentPhase() + "; description=" + project.getDescription());
			}
			startDate = StringUtil.parseUTCDateTimeFromString_HHmmss("2013-02-16 06:08:08");
			endDate = StringUtil.parseUTCDateTimeFromString_HHmmss("2013-11-16 06:08:08");
			projects = projectDao.findProjectsByEndDateRange(2, startDate, endDate);
			assertNotNull(projects);
			assertTrue(projects.size() == 2);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; code=" + project.getProjectCode() +
						"; Phase=" + project.getCurrentPhase() + "; description=" + project.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindProjectsByEndDateRange Exception: " + e.getMessage());
		}
	}

	public void testFindProjectsByParentProjectId() {
		try {
			// Test to find the projects by managing dept id
			System.out.println("Test --> testFindProjectsByParentProjectId");
			System.out.println("    testFindProjectsByParentProjectId [OwnerAccountId=2, ManagingDeptId=5]");
			List<Project> projects = projectDao.findProjectsByParentProjectId(2, 4);
			assertNotNull(projects);
			assertTrue(projects.size() == 2);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; code=" + project.getProjectCode() +
						"; Phase=" + project.getCurrentPhase() + "; description=" + project.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindProjectsByParentProjectId Exception: " + e.getMessage());
		}
	}

	public void testAddUpdateDeleteProject() {
		// Create Project
		// Note: createdDate, createdById, lastModifiedDate and lastModifiedById
		// are not used when inserting values to the database. These are the
		// first four parameters to the constructor.
		UUID ownerId = UUID.randomUUID();
		UUID customerContact = UUID.fromString("07771AE4-236A-49d3-A49E-B1F9E1934D20");
		UUID sponsor = UUID.fromString("07771AE4-236A-49d3-A49E-B1F9E1934D10");
		DateTime startDate = StringUtil.parseUTCDateTimeFromString_HHmmss("2013-01-15 06:08:08");
		DateTime endDate = StringUtil.parseUTCDateTimeFromString_HHmmss("2015-12-15 06:08:08");
		Project project = new Project(null, null, null, null,
				ownerId, 1, "XingZhuang", "New Factory", "Study", 1, 2,
				2, customerContact, sponsor, 1, "Build more cars",
				"Factory can build 1200 cars/month", 120000000, "USD",
				startDate, endDate, 1, "Good project");
		assertNotNull(project);

		System.out.println("Test --> add, update and delete Project");
		System.out.println("    Add Project [OwnerAccountId=" + project.getOwnerAccountId() + "]");
		Project retProject = null;
		try {
			int retId = projectDao.addProject(project);
			assertTrue(retId > 0);
			retProject = projectDao.findProjectById(1, retId);
			assertNotNull(retProject);
			assertEquals(retProject.getName(), "New Factory");
			assertEquals(retProject.getDescription(), "Factory can build 1200 cars/month");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		// Update Project
		System.out.println("    Update Project");
		retProject.setDescription("The new test project");
		try {
			int numRecUpdated = projectDao.saveProject(retProject);
			assertEquals(numRecUpdated, 1);
			Project retProjectUpd = projectDao.findProjectById(1, retProject.getId());
			assertNotNull(retProjectUpd);
			assertEquals(retProjectUpd.getName(), "New Factory");
			assertEquals(retProjectUpd.getDescription(), "The new test project");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		// Delete Project
		System.out.println("    Delete Project");
		try {
			int numRecDeleted = projectDao.deleteProject(1, retProject.getId());
			assertEquals(numRecDeleted, 1);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		System.out.println("     <-- Done.");
	}
	*/
}
