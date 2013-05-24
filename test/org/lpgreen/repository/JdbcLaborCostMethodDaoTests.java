package org.lpgreen.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.lpgreen.util.StringUtil;

import org.joda.time.DateTime;
import org.lpgreen.domain.LaborCostMethod;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * It is the LaborCostMethod JDBC implementation testing class. 
 * 
 * Creation date: May. 08, 2013
 * Last modify date: May. 08, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

@SuppressWarnings("deprecation")
public class JdbcLaborCostMethodDaoTests  extends AbstractTransactionalDataSourceSpringContextTests  {

	private LaborCostMethodDao laborCostMethodDao;
	public void setLaborCostMethodDao(LaborCostMethodDao laborCostMethodDao) {
		this.laborCostMethodDao = laborCostMethodDao;
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] {"classpath:test-context.xml"};
	}

	@Override
	protected void onSetUpInTransaction() throws Exception {
	}

	public void testFindAllSiteLaborCostMethods() {
		try {
			// Test to find the LogixPath (OwnerAccountId=1) all LaborCostMethods 
			System.out.println("Test --> testFindAllSiteLaborCostMethods");
			System.out.println("    testFindAllSiteLaborCostMethods [OwnerAccountId=1]");
			List<LaborCostMethod> laborCostMethods = laborCostMethodDao.findLaborCostMethodsByOwnerAccountId(1, null, false);
			assertNotNull(laborCostMethods);
			assertTrue(laborCostMethods.size() == 16);
			for (LaborCostMethod laborCostMethod : laborCostMethods) {
				System.out.println("LaborCostMethod: id=" + laborCostMethod.getId() + "; costCode=" + laborCostMethod.getCostMethodCode() +
					"; Status=" + laborCostMethod.getStatus() + "; description=" + laborCostMethod.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindAllSiteLaborCostMethods Exception: " + e.getMessage());
		}
	}

	public void testFindAllSiteActiveLaborCostMethods() {
		try {
			// Test to find the LogixPath (OwnerAccountId=1) all active LaborCostMethods 
			System.out.println("Test --> testFindAllSiteActiveLaborCostMethods");
			System.out.println("    testFindAllSiteActiveLaborCostMethods [OwnerAccountId=1]");
			Set<String> statuses = new HashSet<String>();
			statuses.add("Active");
			List<LaborCostMethod> laborCostMethods = laborCostMethodDao.findLaborCostMethodsByOwnerAccountId(1, statuses, false);
			assertNotNull(laborCostMethods);
			assertTrue(laborCostMethods.size() == 13);
			for (LaborCostMethod laborCostMethod : laborCostMethods) {
				System.out.println("LaborCostMethod: id=" + laborCostMethod.getId() + "; costCode=" + laborCostMethod.getCostMethodCode() +
					"; Status=" + laborCostMethod.getStatus() + "; description=" + laborCostMethod.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindAllSiteActiveLaborCostMethods Exception: " + e.getMessage());
		}
	}

	public void testFindAllSiteInactiveLaborCostMethods() {
		try {
			// Test to find the LogixPath (OwnerAccountId=1) all inactive LaborCostMethods 
			System.out.println("Test --> testFindAllSiteInactiveLaborCostMethods");
			System.out.println("    testFindAllSiteInactiveLaborCostMethods [OwnerAccountId=1]");
			Set<String> statuses = new HashSet<String>();
			statuses.add("InActive");
			List<LaborCostMethod> laborCostMethods = laborCostMethodDao.findLaborCostMethodsByOwnerAccountId(1, statuses, false);
			assertNotNull(laborCostMethods);
			assertTrue(laborCostMethods.size() == 3);
			for (LaborCostMethod laborCostMethod : laborCostMethods) {
				System.out.println("LaborCostMethod: id=" + laborCostMethod.getId() + "; costCode=" + laborCostMethod.getCostMethodCode() +
					"; Status=" + laborCostMethod.getStatus() + "; description=" + laborCostMethod.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindAllSiteInactiveLaborCostMethods Exception: " + e.getMessage());
		}
	}

	public void testFindLaborCostMethodById() {
		try {
			// Test to find LaborCostMethod by Id 
			System.out.println("Test --> testFindLaborCostMethodById");
			System.out.println("    testFindLaborCostMethodById [Id=2]");
			LaborCostMethod laborCostMethod = laborCostMethodDao.findLaborCostMethodById(2);
			assertNotNull(laborCostMethod);
			System.out.println("LaborCostMethod: id=" + laborCostMethod.getId() + "; costCode=" + laborCostMethod.getCostMethodCode() +
					"; Status=" + laborCostMethod.getStatus() + "; description=" + laborCostMethod.getDescription());
			assertTrue(laborCostMethod.getId() == 2);
			assertTrue(laborCostMethod.getCostMethodCode() != null && laborCostMethod.getCostMethodCode().equals("CMC-0002"));
			System.out.println("    testFindLaborCostMethodById [Id=12]");
			laborCostMethod = laborCostMethodDao.findLaborCostMethodById(12);
			assertNotNull(laborCostMethod);
			System.out.println("LaborCostMethod: id=" + laborCostMethod.getId() + "; costCode=" + laborCostMethod.getCostMethodCode() +
					"; Status=" + laborCostMethod.getStatus() + "; description=" + laborCostMethod.getDescription());
			assertTrue(laborCostMethod.getId() == 12);
			assertTrue(laborCostMethod.getCostMethodCode() != null && laborCostMethod.getCostMethodCode().equals("CMC-0012"));
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindLaborCostMethodById Exception: " + e.getMessage());
		}
	}

	public void testFindLaborCostMethodByCostMethodCode() {
		try {
			// Test to find laborCostMethod by CostMethodCode
			System.out.println("Test --> testFindLaborCostMethodByCostMethodCode");
			System.out.println("    testFindLaborCostMethodByCostMethodCode [OwnerAccountId=1, CostMethodCode=CMC-0003]");
			LaborCostMethod laborCostMethod = laborCostMethodDao.findLaborCostMethodByCostMethodCode(1, "CMC-0003", true, null, false);
			assertNotNull(laborCostMethod);
			System.out.println("LaborCostMethod: id=" + laborCostMethod.getId() + "; costCode=" + laborCostMethod.getCostMethodCode() +
					"; Status=" + laborCostMethod.getStatus() + "; description=" + laborCostMethod.getDescription());
			System.out.println("    testFindLaborCostMethodByCostMethodCode [OwnerAccountId=1, CostMethodCode=CMC-0013]");
			laborCostMethod = laborCostMethodDao.findLaborCostMethodByCostMethodCode(1, "CMC-0013", true, null, false);
			assertNotNull(laborCostMethod);
			System.out.println("LaborCostMethod: id=" + laborCostMethod.getId() + "; costCode=" + laborCostMethod.getCostMethodCode() +
					"; Status=" + laborCostMethod.getStatus() + "; description=" + laborCostMethod.getDescription());
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindLaborCostMethodByCostMethodCode Exception: " + e.getMessage());
		}
	}

	public void testFindLaborCostMethodsByCostMethodType() {
		try {
			// Test to find the laborCostMethods by CostMethodType
			System.out.println("Test --> testFindLaborCostMethodsByCostMethodType");
			System.out.println("    testFindLaborCostMethodsByCostMethodType [OwnerAccountId=1, CostMethodType=PerDuration]");
			List<LaborCostMethod> laborCostMethods = laborCostMethodDao.findLaborCostMethodsByCostMethodType(1, "PerDuration", true, null, false);
			assertNotNull(laborCostMethods);
			assertTrue(laborCostMethods.size() == 3);
			for (LaborCostMethod laborCostMethod : laborCostMethods) {
				System.out.println("LaborCostMethod: id=" + laborCostMethod.getId() + "; costCode=" + laborCostMethod.getCostMethodCode() +
					"; Status=" + laborCostMethod.getStatus() + "; description=" + laborCostMethod.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindLaborCostMethodsByCostMethodType Exception: " + e.getMessage());
		}
	}

	public void testFindLaborCostMethodsByUnitQuantityRange() {
		try {
			// Test to find the laborCostMethods by UnitQuantity range
			System.out.println("Test --> testFindLaborCostMethodsByUnitQuantityRange");
			System.out.println("    testFindLaborCostMethodsByUnitQuantityRange [OwnerAccountId=1, StartUnitQuantity=1.0, EndUnitQuantity=1.0]");
			List<LaborCostMethod> laborCostMethods = laborCostMethodDao.findLaborCostMethodsByUnitQuantityRange(1, 1.0, 1.0, null, false);
			assertNotNull(laborCostMethods);
			assertTrue(laborCostMethods.size() == 7);
			for (LaborCostMethod laborCostMethod : laborCostMethods) {
				System.out.println("LaborCostMethod: id=" + laborCostMethod.getId() + "; costCode=" + laborCostMethod.getCostMethodCode() +
					"; Status=" + laborCostMethod.getStatus() + "; description=" + laborCostMethod.getDescription());
			}
			Set<String> statuses = new HashSet<String>();
			statuses.add("Active");
			System.out.println("    testFindLaborCostMethodsByUnitQuantityRange [OwnerAccountId=1, StartUnitQuantity=1.0, EndUnitQuantity=1.0, Status=Active]");
			laborCostMethods = laborCostMethodDao.findLaborCostMethodsByUnitQuantityRange(1, 1.0, 1.0, statuses, false);
			assertNotNull(laborCostMethods);
			assertTrue(laborCostMethods.size() == 5);
			for (LaborCostMethod laborCostMethod : laborCostMethods) {
				System.out.println("LaborCostMethod: id=" + laborCostMethod.getId() + "; costCode=" + laborCostMethod.getCostMethodCode() +
					"; Status=" + laborCostMethod.getStatus() + "; description=" + laborCostMethod.getDescription());
			}
			statuses.clear();
			statuses.add("Inactive");
			System.out.println("    testFindLaborCostMethodsByUnitQuantityRange [OwnerAccountId=1, StartUnitQuantity=1.0, EndUnitQuantity=1.0, Status=Inactive]");
			laborCostMethods = laborCostMethodDao.findLaborCostMethodsByUnitQuantityRange(1, 1.0, 1.0, statuses, false);
			assertNotNull(laborCostMethods);
			assertTrue(laborCostMethods.size() == 2);
			for (LaborCostMethod laborCostMethod : laborCostMethods) {
				System.out.println("LaborCostMethod: id=" + laborCostMethod.getId() + "; costCode=" + laborCostMethod.getCostMethodCode() +
					"; Status=" + laborCostMethod.getStatus() + "; description=" + laborCostMethod.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindLaborCostMethodsByUnitQuantityRange Exception: " + e.getMessage());
		}
	}

	public void testFindLaborCostMethodsByUnitQuantityCostRange() {
		try {
			// Test to find the laborCostMethods by UnitQuantityCost
			System.out.println("Test --> testFindLaborCostMethodsByUnitQuantityCostRange");
			System.out.println("    testFindLaborCostMethodsByUnitQuantityCost [OwnerAccountId=1, startUnitQuantityCost=16000.00, endUnitQuantityCost=15000.00, Status=Inactive]");
			Set<String> statuses = new HashSet<String>();
			statuses.add("Inactive");
			List<LaborCostMethod> laborCostMethods = laborCostMethodDao.findLaborCostMethodsByUnitQuantityCostRange(1, 15000.00, 15000.00, statuses, false);
			assertNotNull(laborCostMethods);
			assertTrue(laborCostMethods.size() == 2);
			for (LaborCostMethod laborCostMethod : laborCostMethods) {
				System.out.println("LaborCostMethod: id=" + laborCostMethod.getId() + "; costCode=" + laborCostMethod.getCostMethodCode() +
					"; Status=" + laborCostMethod.getStatus() + "; description=" + laborCostMethod.getDescription());
			}
			statuses.clear();
			statuses.add("Active");
			System.out.println("    testFindLaborCostMethodsByUnitQuantityCostRange [OwnerAccountId=1, startUnitQuantityCost=1.00, endUnitQuantityCost=100000.00, Status=Active]");
			laborCostMethods = laborCostMethodDao.findLaborCostMethodsByUnitQuantityCostRange(1, 1.00, 100000.00, statuses, false);
			assertNotNull(laborCostMethods);
			assertTrue(laborCostMethods.size() == 4);
			for (LaborCostMethod laborCostMethod : laborCostMethods) {
				System.out.println("LaborCostMethod: id=" + laborCostMethod.getId() + "; costCode=" + laborCostMethod.getCostMethodCode() +
					"; Status=" + laborCostMethod.getStatus() + "; description=" + laborCostMethod.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindLaborCostMethodsByUnitQuantityCostRange Exception: " + e.getMessage());
		}
	}

	public void testFindLaborCostMethodsByUnitQuantityUMCode() {
		try {
			// Test to find laborCostMethod by UnitQuantityUMCode
			System.out.println("Test --> testFindLaborCostMethodsByUnitQuantityUMCode");
			System.out.println("    testFindLaborCostMethodsByUnitQuantityUMCode [OwnerAccountId=1, UnitQuantityUMCode=gallon, Status=Active]");
			Set<String> statuses = new HashSet<String>();
			statuses.add("Active");
			List<LaborCostMethod> laborCostMethods = laborCostMethodDao.findLaborCostMethodsByUnitQuantityUMCode(1, "gallon", true, statuses, false);
			assertNotNull(laborCostMethods);
			assertTrue(laborCostMethods.size() == 3);
			for (LaborCostMethod laborCostMethod : laborCostMethods) {
				System.out.println("LaborCostMethod: id=" + laborCostMethod.getId() + "; costCode=" + laborCostMethod.getCostMethodCode() +
					"; Status=" + laborCostMethod.getStatus() + "; description=" + laborCostMethod.getDescription());
			}
			System.out.println("    testFindLaborCostMethodsByUnitQuantityUMCode [OwnerAccountId=1, UnitQuantityUMCode=gallon, Status=Inactive]");
			statuses.clear();
			statuses.add("Inactive");
			laborCostMethods = laborCostMethodDao.findLaborCostMethodsByUnitQuantityUMCode(1, "gallon", true, statuses, false);
			assertNotNull(laborCostMethods);
			assertTrue(laborCostMethods.size() == 1);
			for (LaborCostMethod laborCostMethod : laborCostMethods) {
				System.out.println("LaborCostMethod: id=" + laborCostMethod.getId() + "; costCode=" + laborCostMethod.getCostMethodCode() +
					"; Status=" + laborCostMethod.getStatus() + "; description=" + laborCostMethod.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindLaborCostMethodsByUnitQuantityUMCode Exception: " + e.getMessage());
		}
	}

	public void testFindLaborCostMethodsByUserDefinedUMCode() {
		try {
			// Test to find laborCostMethod by UserDefinedUMCode
			System.out.println("Test --> testFindLaborCostMethodsByUserDefinedUMCode");
			System.out.println("    testFindLaborCostMethodsByUserDefinedUMCode [OwnerAccountId=1, UserDefinedUMCode=task, Status=Active]");
			Set<String> statuses = new HashSet<String>();
			statuses.add("Active");
			List<LaborCostMethod> laborCostMethods = laborCostMethodDao.findLaborCostMethodsByUserDefinedUMCode(1, "task", true, statuses, false);
			assertNotNull(laborCostMethods);
			assertTrue(laborCostMethods.size() == 1);
			for (LaborCostMethod laborCostMethod : laborCostMethods) {
				System.out.println("LaborCostMethod: id=" + laborCostMethod.getId() + "; costCode=" + laborCostMethod.getCostMethodCode() +
					"; Status=" + laborCostMethod.getStatus() + "; description=" + laborCostMethod.getDescription());
			}
			System.out.println("    testFindLaborCostMethodsByUserDefinedUMCode [OwnerAccountId=1, UserDefinedUMCode=task, Status=Inactive]");
			statuses.clear();
			statuses.add("Inactive");
			laborCostMethods = laborCostMethodDao.findLaborCostMethodsByUserDefinedUMCode(1, "task", true, statuses, false);
			assertNotNull(laborCostMethods);
			assertTrue(laborCostMethods.size() == 1);
			for (LaborCostMethod laborCostMethod : laborCostMethods) {
				System.out.println("LaborCostMethod: id=" + laborCostMethod.getId() + "; costCode=" + laborCostMethod.getCostMethodCode() +
					"; Status=" + laborCostMethod.getStatus() + "; description=" + laborCostMethod.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindLaborCostMethodsByUserDefinedUMCode Exception: " + e.getMessage());
		}
	}

	public void testFindLaborCostMethodsByMultiplierRange() {
		try {
			// Test to find the laborCostMethods by MultiplierRange
			System.out.println("Test --> testFindLaborCostMethodsByMultiplierRange");
			System.out.println("    testFindLaborCostMethodsByMultiplierRange [OwnerAccountId=1, startMultiplier=0.05, endMultiplier=5.00]");
			List<LaborCostMethod> laborCostMethods = laborCostMethodDao.findLaborCostMethodsByMultiplierRange(1, 0.05, 5.00, null, false);
			assertNotNull(laborCostMethods);
			assertTrue(laborCostMethods.size() == 6);
			for (LaborCostMethod laborCostMethod : laborCostMethods) {
				System.out.println("LaborCostMethod: id=" + laborCostMethod.getId() + "; costCode=" + laborCostMethod.getCostMethodCode() +
					"; Status=" + laborCostMethod.getStatus() + "; description=" + laborCostMethod.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindLaborCostMethodsByMultiplierRange Exception: " + e.getMessage());
		}
	}

	public void testFindLaborCostMethodsByValueInQuantityRange() {
		try {
			// Test to find the laborCostMethods by EndDateRange
			System.out.println("Test --> testFindLaborCostMethodsByValueInQuantityRange");
			System.out.println("    testFindLaborCostMethodsByValueInQuantityRange [OwnerAccountId=1, valueQuantity=250]");
			List<LaborCostMethod> laborCostMethods = laborCostMethodDao.findLaborCostMethodsByValueInQuantityRange(1, 250, null, false);
			assertNotNull(laborCostMethods);
			assertTrue(laborCostMethods.size() == 1);
			for (LaborCostMethod laborCostMethod : laborCostMethods) {
				System.out.println("LaborCostMethod: id=" + laborCostMethod.getId() + "; costCode=" + laborCostMethod.getCostMethodCode() +
					"; Status=" + laborCostMethod.getStatus() + "; description=" + laborCostMethod.getDescription());
			}
			System.out.println("    testFindLaborCostMethodsByValueInQuantityRange [OwnerAccountId=1, valueQuantity=600]");
			laborCostMethods = laborCostMethodDao.findLaborCostMethodsByValueInQuantityRange(1, 600, null, false);
			assertNotNull(laborCostMethods);
			assertTrue(laborCostMethods.size() == 1);
			for (LaborCostMethod laborCostMethod : laborCostMethods) {
				System.out.println("LaborCostMethod: id=" + laborCostMethod.getId() + "; costCode=" + laborCostMethod.getCostMethodCode() +
					"; Status=" + laborCostMethod.getStatus() + "; description=" + laborCostMethod.getDescription());
			}
			System.out.println("    testFindLaborCostMethodsByValueInQuantityRange [OwnerAccountId=1, valueQuantity=900]");
			laborCostMethods = laborCostMethodDao.findLaborCostMethodsByValueInQuantityRange(1, 900, null, false);
			assertNotNull(laborCostMethods);
			assertTrue(laborCostMethods.size() == 1);
			for (LaborCostMethod laborCostMethod : laborCostMethods) {
				System.out.println("LaborCostMethod: id=" + laborCostMethod.getId() + "; costCode=" + laborCostMethod.getCostMethodCode() +
					"; Status=" + laborCostMethod.getStatus() + "; description=" + laborCostMethod.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindLaborCostMethodsByValueInQuantityRange Exception: " + e.getMessage());
		}
	}

	/*
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
			int retId = laborCostMethodDao.addProject(project);
			assertTrue(retId > 0);
			retProject = laborCostMethodDao.findProjectById(retId);
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
			int numRecUpdated = laborCostMethodDao.saveProject(retProject);
			assertEquals(numRecUpdated, 1);
			Project retProjectUpd = laborCostMethodDao.findProjectById(retProject.getId());
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
			int numRecDeleted = laborCostMethodDao.deleteProject(1, retProject.getId());
			assertEquals(numRecDeleted, 1);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		System.out.println("     <-- Done.");
	}
	*/
}
