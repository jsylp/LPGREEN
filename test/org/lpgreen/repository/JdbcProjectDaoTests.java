package org.lpgreen.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.lpgreen.util.StringUtil;

import org.joda.time.DateTime;
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
			// Test to find the LogixPath (OwnerAccountId=2) all Projects 
			System.out.println("Test --> testFindAllSiteProjects");
			System.out.println("    testFindAllSiteProjects [OwnerAccountId=2]");
			List<Project> projects = projectDao.findProjectsByOwnerAccountId(2, null);
			assertNotNull(projects);
			assertTrue(projects.size() == 5);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; code=" + project.getProjectCode() +
					"; Phase=" + project.getCurrentPhase() + "; description=" + project.getDescription());
			}
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
			System.out.println("    testFindProjectById [Id=2]");
			Project project = projectDao.findProjectById(2);
			assertNotNull(project);
			System.out.println("Project: id=" + project.getId() + "; code=" + project.getProjectCode() +
					"; Phase=" + project.getCurrentPhase() + "; description=" + project.getDescription());
			assertTrue(project.getId() == 2);
			assertTrue(project.getProjectCode() != null && project.getProjectCode().equals("London"));
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindProjectById Exception: " + e.getMessage());
		}
	}

	public void testFindProjectsByProjectCode() {
		try {
			// Test to find projects by ProjectCode
			System.out.println("Test --> testFindProjectsByProjectCode");
			System.out.println("    testFindProjectsByProjectCode [OwnerAccountId=2, ProjectCode=Shanghai]");
			List<Project> projects = projectDao.findProjectsByProjectCode(2, "Shanghai");
			assertNotNull(projects);
			assertTrue(projects.size() == 1);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; code=" + project.getProjectCode() +
						"; Phase=" + project.getCurrentPhase() + "; description=" + project.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDaoTests.testFindProjectsByProjectCode Exception: " + e.getMessage());
		}
	}

	public void testFindProjectsByName() {
		try {
			// Test to find the projects by Name
			System.out.println("Test --> testFindProjectsByName");
			System.out.println("    testFindProjectsByName [OwnerAccountId=1, Name=New Bridge]");
			List<Project> projects = projectDao.findProjectsByName(1, "New Bridge");
			assertNotNull(projects);
			assertTrue(projects.size() == 1);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; code=" + project.getProjectCode() +
						"; Phase=" + project.getCurrentPhase() + "; description=" + project.getDescription());
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
			System.out.println("    testFindProjectsByProjectManager1Id [OwnerAccountId=2, Manager1Id=4]");
			Set<String> currentPhases = new HashSet<String>();
			currentPhases.add("Construction");
			List<Project> projects = projectDao.findProjectsByProjectManager1Id(2, 4, currentPhases);
			assertNotNull(projects);
			assertTrue(projects.size() == 4);
			for (Project project : projects) {
				System.out.println("Project: id=" + project.getId() + "; code=" + project.getProjectCode() +
						"; Phase=" + project.getCurrentPhase() + "; description=" + project.getDescription());
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
			// Test to find the projects by StartDateRange
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
			// Test to find the projects by EndDateRange
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
			// Test to find the projects by ParentProjectId
			System.out.println("Test --> testFindProjectsByParentProjectId");
			System.out.println("    testFindProjectsByParentProjectId [OwnerAccountId=2, ParentProjectId=5]");
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
			retProject = projectDao.findProjectById(retId);
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
			Project retProjectUpd = projectDao.findProjectById(retProject.getId());
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
}
