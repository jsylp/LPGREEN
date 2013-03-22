package org.lpgreen.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.lpgreen.domain.Project;
import org.lpgreen.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * It is the DepartmentManager junit testing class. 
 * 
 * Creation date: Jan. 18, 2013
 * Last modify date: Jan. 18, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class ProjectManagerTests extends AbstractTransactionalDataSourceSpringContextTests {
	private UUID currentUserId;
	private int  currentUserOwnerAccountId;

	private ProjectManager projectManager;
	@Autowired
	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] {"classpath:test-context.xml"};
	}

	@Override
	protected void onSetUpInTransaction() throws Exception {
		currentUserId = UUID.fromString("86291951-969e-4b5b-ab37-29a5749544ed"); // sysadmin@logixpath.com, used as the object owner id
		currentUserOwnerAccountId = 2;  // Allen Company
	}

	// get all Projects by budget range
	public void testFindProjectsByBudgetRange() {
		try {
			double fromAmount = 20000000.00;
			double toAmount = 55000000.00;
			System.out.println("Test --> testFindProjectsByBudgetRange");
			System.out.println("    testFindProjectsByBudgetRange [OwnerAccountId=2, fromAmount=" +
					fromAmount + ", toAmount=" + toAmount + ", currentCode=USD]");
			List<Project> projects = projectManager.findProjectsByBudgetRange(
					currentUserOwnerAccountId, fromAmount, toAmount, "USD");
			assertNotNull(projects);
			assertTrue(projects.size() == 1);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; code=" + project.getProjectCode() +
					"; Phase=" + project.getCurrentPhase() + "; description=" + project.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("ProjectManagerTests.testFindProjectsByBudgetRange Exception: " + e.getMessage());
		}
	}

	public void testAddAndUpdateProject() {
		System.out.println("Test --> testAddAndUpdateProject");

		UUID customerContact = UUID.fromString("07771AE4-236A-49d3-A49E-B1F9E1934D20");
		UUID sponsor = UUID.fromString("07771AE4-236A-49d3-A49E-B1F9E1934D10");
		DateTime startDate = StringUtil.parseUTCDateTimeFromString_HHmmss("2013-01-02 08:08:08");
		DateTime endDate = StringUtil.parseUTCDateTimeFromString_HHmmss("2016-12-31 08:08:08");
		UUID ownerId = UUID.randomUUID();

		Project project = new Project();
		project.setOwnerAccountId(1);
		project.setProjectCode("Pudong");
		project.setName("New Bank");
		project.setCurrentPhase("Study");
		project.setProjectManager1Id(1);
		project.setProjectManager2Id(2);
		project.setCustomerAccount(2);
		project.setCustomerContact(customerContact);
		project.setSponsor(sponsor);
		project.setManagingDeptId(1);
		project.setObjectives("Landing to small business");
		project.setDescription("Establish seed funding for 100 startup");
		project.setBudget(500000000.00);
		project.setCurrencyCode("CNY");
		project.setStartDate(startDate);
		project.setEndDate(endDate);
		project.setParentProjectId(1);
		project.setOwnerId(ownerId);
	
		// Create the Project
		Project retProject = null;
		try {
			retProject = projectManager.createProject(
					currentUserId, currentUserOwnerAccountId, project);
			assertNotNull(retProject);
			assertEquals(retProject.getName(), "New Bank");
			assertEquals(retProject.getCurrentPhase(), "Study");
			assertEquals(retProject.getProjectManager1Id(), 1);
			assertEquals(retProject.getProjectManager2Id(), 2);
			assertEquals(retProject.getCustomerAccount(), 2);
			assertEquals(retProject.getDescription(), "Establish seed funding for 100 startup");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		
		// Update the Project
		try {
			retProject.setCurrentPhase("Construction");
			retProject.setNotes("Approved and start construction");

			Project retProjectUpd = projectManager.updateProject(
					currentUserId, retProject);
			assertNotNull(retProject);
			assertEquals(retProjectUpd.getCurrentPhase(), "Construction");
			assertEquals(retProjectUpd.getNotes(), "Approved and start construction");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		// Delete the Project
		try {
			projectManager.deleteProject(currentUserId, currentUserOwnerAccountId, retProject.getId());
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
