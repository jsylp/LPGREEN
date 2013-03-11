package org.lpgreen.repository;

import java.util.List;
import java.util.UUID;

import org.lpgreen.domain.Project;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * It is the Project JDBC implementation testing class. 
 * 
 * Creation date: Mar. 09, 2013
 * Last modify date: Mar. 09, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

@SuppressWarnings("deprecation")
public class JdbcProjectDaoTests  extends AbstractTransactionalDataSourceSpringContextTests  {

	private ProjectDao projectDao;
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] {"classpath:test-context.xml"};
	}

	@Override
	protected void onSetUpInTransaction() throws Exception {
	}

	public void testFindAllSiteProjects() {
		try {
			// Test to find the LogixPath (OwnerAccountId=1) all Projects 
			System.out.println("Test --> testFindAllSiteProjects");
			System.out.println("    testFindAllSiteProjects [OwnerAccountId=1]");
			List<Project> projects = projectDao.findProjectsByOwerAccountId(1, null);
			assertNotNull(projects);
			assertTrue(projects.size() >= 3);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; name=" + project.getName() + "; description=" + project.getDescription());
			}

			System.out.println("    find one specific Project (id=1)");
			Project project = projectDao.findProjectById(1, 1);
			assertNotNull(project);
			assertTrue(project.getName() != null && project.getName().equals("New Bridge"));
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindAllSiteProjects Exception: " + e.getMessage());
		}
	}

	public void testFindProjectById() {
		try {
			// Test to find project by project code 
			System.out.println("Test --> testFindProjectById");
			System.out.println("    testFindProjectById [OwnerAccountId=1, Id=2]");
			Project project = projectDao.findProjectById(2, 4);
			assertNotNull(project);
			System.out.println("Project: id=" + project.getId() + "; name=" + project.getName() + "; description=" + project.getDescription());
			assertTrue(project.getId() == 4);
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindProjectById Exception: " + e.getMessage());
		}
	}

	public void testFindProjectByProjectCode() {
		try {
			// Test to find project by project code 
			System.out.println("Test --> testFindProjectByProjectCode");
			System.out.println("    testFindProjectByProjectCode [OwnerAccountId=1, ProjectCode=Shanghai]");
			Project project = projectDao.findProjectByProjectCode(2, "Shanghai");
			assertNotNull(project);
			System.out.println("Project: id=" + project.getId() + "; name=" + project.getName() + "; description=" + project.getDescription());
			assertTrue(project.getProjectCode() != null && project.getProjectCode().equals("Shanghai"));
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindProjectByProjectCode Exception: " + e.getMessage());
		}
	}

	public void testFindProjectsByName() {
		try {
			// Test to find the projects by name
			System.out.println("Test --> testFindProjectsByName");
			System.out.println("    testFindProjectsByName [OwnerAccountId=1, Name=New Bridge]");
			List<Project> projects = projectDao.findProjectsByName(1, "New Bridge");
			assertNotNull(projects);
			assertTrue(projects.size() >= 3);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; name=" + project.getName() + "; description=" + project.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindProjectsByName Exception: " + e.getMessage());
		}
	}

	public void testFindProjectsByProjectManager1Id() {
		try {
			// Test to find the projects by manager1 id
			System.out.println("Test --> testFindProjectsByProjectManager1Id");
			System.out.println("    testFindProjectsByProjectManager1Id [OwnerAccountId=1, Manager1Id=4]");
			List<Project> projects = projectDao.findProjectsByProjectManager1Id(2, 4, null);
			assertNotNull(projects);
			assertTrue(projects.size() >= 3);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; name=" + project.getName() + "; description=" + project.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindProjectsByProjectManager1Id Exception: " + e.getMessage());
		}
	}

	public void testFindProjectsByProjectManager2Id() {
		try {
			// Test to find the projects by manager2 id
			System.out.println("Test --> testFindProjectsByProjectManager2Id");
			System.out.println("    testFindProjectsByProjectManager2Id [OwnerAccountId=1, Manager2Id=5]");
			List<Project> projects = projectDao.findProjectsByProjectManager2Id(2, 5, null);
			assertNotNull(projects);
			assertTrue(projects.size() >= 3);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; name=" + project.getName() + "; description=" + project.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindProjectsByProjectManager2Id Exception: " + e.getMessage());
		}
	}

	/*
	public void testFindProjectsByCustomerAccount() {
		try {
			// Test to find the projects by customer account
			System.out.println("Test --> testFindProjectsByCustomerAccount");
			System.out.println("    testFindProjectsByCustomerAccount [OwnerAccountId=1, CustomerAccount=5]");
			List<Project> projects = projectDao.findProjectsByCustomerAccount(2, 2);
			assertNotNull(projects);
			assertTrue(projects.size() >= 3);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; name=" + project.getName() + "; description=" + project.getDescription());
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
			System.out.println("    testFindProjectsByCustomerContact [OwnerAccountId=1, CustomerContact=07771AE4-236A-49d3-A49E-B1F9E1934D21]");
			List<Project> projects = projectDao.findProjectsByCustomerContact(2, UUID.fromString("07771AE4-236A-49d3-A49E-B1F9E1934D21"));
			assertNotNull(projects);
			assertTrue(projects.size() >= 3);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; name=" + project.getName() + "; description=" + project.getDescription());
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
			System.out.println("    testFindProjectsBySponsor [OwnerAccountId=1, Sponsor=07771AE4-236A-49d3-A49E-B1F9E1934D10]");
			List<Project> projects = projectDao.findProjectsBySponsor(1, UUID.fromString("07771AE4-236A-49d3-A49E-B1F9E1934D10"));
			assertNotNull(projects);
			assertTrue(projects.size() >= 3);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; name=" + project.getName() + "; description=" + project.getDescription());
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
			System.out.println("    testFindProjectsByManagingDeptId [OwnerAccountId=1, ManagingDeptId=5]");
			List<Project> projects = projectDao.findProjectsByManagingDeptId(2, 5);
			assertNotNull(projects);
			assertTrue(projects.size() >= 3);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; name=" + project.getName() + "; description=" + project.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindProjectsByManagingDeptId Exception: " + e.getMessage());
		}
	}

	public void testFindProjectsByBudgetRange() {
		try {
			// Test to find the projects by managing dept id
			System.out.println("Test --> testFindProjectsByBudgetRange");
			System.out.println("    testFindProjectsByBudgetRange [OwnerAccountId=1, lowAmount=]");
			List<Project> projects = projectDao.findProjectsByBudgetRange(2, 5);
			assertNotNull(projects);
			assertTrue(projects.size() >= 3);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; name=" + project.getName() + "; description=" + project.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindProjectsByBudgetRange Exception: " + e.getMessage());
		}
	}
	*/

	/*
	public void testAddUpdateDeleteOperationRight() {
		// Create OperationRight
		OperationRight right = new OperationRight("TestRight", "The test right", 1);
		assertNotNull(right);
		OperationRight retRight = null;

		System.out.println("Test --> add, update and delete OperationRight");
		System.out.println("    Add TestRight");
		try {
			int retId = opRightDao.addOperationRight(right);
			assertTrue(retId > 0);
			retRight = opRightDao.findOperationRightById(1, retId);
			assertNotNull(retRight);
			assertEquals(retRight.getOperationName(), "TestRight");
			assertEquals(retRight.getDescription(), "The test right");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		// Update OperationRight
		System.out.println("    Update TestRight");
		retRight.setDescription("The new test right");
		try {
			int numRecUpdated = opRightDao.saveOperationRight(retRight);
			assertEquals(numRecUpdated, 1);
			OperationRight retRightUpd = opRightDao.findOperationRightById(1, retRight.getId());
			assertNotNull(retRightUpd);
			assertEquals(retRightUpd.getOperationName(), "TestRight");
			assertEquals(retRightUpd.getDescription(), "The new test right");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		// Delete OperationRight
		System.out.println("    Delete TestRight");
		try {
			int numRecDeleted = opRightDao.deleteOperationRight(1, retRight.getId());
			assertEquals(numRecDeleted, 1);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		System.out.println("     <-- Done.");
	}
	*/
}
