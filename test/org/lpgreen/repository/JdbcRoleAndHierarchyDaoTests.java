package org.lpgreen.repository;

import java.util.List;

import org.lpgreen.domain.Role;
import org.lpgreen.domain.RoleHierarchy;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * It is the RoleAndHierarchy JDBC implementation testing class. 
 * 
 * Creation date: Feb. 18, 2013
 * Last modify date: Feb. 18, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

@SuppressWarnings("deprecation")
public class JdbcRoleAndHierarchyDaoTests  extends AbstractTransactionalDataSourceSpringContextTests  {

	private RoleAndHierarchyDao roleAndHierarchyDao;
	public void setRoleAndHierarchyDao(RoleAndHierarchyDao roleAndHierarchyDao) {
		this.roleAndHierarchyDao = roleAndHierarchyDao;
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] {"classpath:test-context.xml"};
	}

	@Override
	protected void onSetUpInTransaction() throws Exception {
	}

	public void testGetAllIncludedRoles() {
		try {
			// Find the LogixPath (OwnerAccountId=1) superadmin 
			Role role = roleAndHierarchyDao.findRoleById(1, 1);
			assertNotNull(role);

			// Test find all included roles
			System.out.println("Test --> getAllIncludedRoles");
			System.out.println("    getAllIncludedRoles [name=ROLE_LP_SUPERADMIN, OwnerAccountId=1]");
			List<Role> allRoles = roleAndHierarchyDao.getAllIncludedRoles(role);
			assertNotNull(allRoles);
			assertTrue(allRoles.size() == 6);
			for (Role r : allRoles) {
				System.out.println("role: id=" + r.getId() + "; name=" + r.getRoleName() + "; description=" + r.getDescription());
			}

			System.out.println("    getAllIncludedRoles [name=ROLE_ALLEN_CO_SITE_USERADMIN, OwnerAccountId=2]");
			role = roleAndHierarchyDao.findRoleByName(2, "ROLE_ALLEN_CO_SITE_USERADMIN");
			allRoles = roleAndHierarchyDao.getAllIncludedRoles(role);
			assertNotNull(allRoles);
			assertTrue(allRoles.size() == 2);
			for (Role r : allRoles) {
				System.out.println("role: id=" + r.getId() + "; name=" + r.getRoleName() + "; description=" + r.getDescription());
			}

			System.out.println("    getAllIncludedRoles [name=ROLE_BOBBY_CO_SITE_SUPERVISOR, OwnerAccountId=3]");
			role = roleAndHierarchyDao.findRoleByName(3, "ROLE_BOBBY_CO_SITE_SUPERVISOR");
			allRoles = roleAndHierarchyDao.getAllIncludedRoles(role);
			assertNotNull(allRoles);
			assertTrue(allRoles.size() == 1);
			for (Role r : allRoles) {
				System.out.println("role: id " + r.getId() + "; name " + r.getRoleName() + "; description " + r.getDescription());
			}

			System.out.println("    getAllIncludedRoles [name=ROLE_CATHY_CO_SITE_USER, OwnerAccountId=4]");
			role = roleAndHierarchyDao.findRoleByName(4, "ROLE_CATHY_CO_SITE_USER");
			allRoles = roleAndHierarchyDao.getAllIncludedRoles(role);
			assertNotNull(allRoles);
			assertTrue(allRoles.size() == 0);
			for (Role r : allRoles) {
				System.out.println("role: id " + r.getId() + "; name " + r.getRoleName() + "; description " + r.getDescription());
			}
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcRoleAndHierarchyDaoTests.testGetAllIncludedRoles Exception: " + e.getMessage());
		}
	}

	public void testAddUpdateDeleteRole() {
		// Create Role
		System.out.println("Test --> add, update and delete Role");
		System.out.println("    Add ROLE_LP_TEST");
		Role role = new Role("ROLE_LP_TEST", "LogixPath testing role", 1);
		assertNotNull(role);
		Role retRole = null;
		try {
			int retId = roleAndHierarchyDao.addRole(role);
			assertTrue(retId > 0);
			retRole = roleAndHierarchyDao.findRoleById(1, retId);
			assertNotNull(retRole);
			assertEquals(retRole.getRoleName(), "ROLE_LP_TEST");
			assertEquals(retRole.getDescription(), "LogixPath testing role");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		// Update Role
		System.out.println("    Update ROLE_LP_TEST to ROLE_LP_TEST_NP");
		retRole.setRoleName("ROLE_LP_TEST_NP");
		retRole.setDescription("LogixPath testing role new product");
		try {
			int numRecUpdated = roleAndHierarchyDao.saveRole(retRole);
			assertEquals(numRecUpdated, 1);
			Role retRoleUpd = roleAndHierarchyDao.findRoleById(1, retRole.getId());
			assertNotNull(retRoleUpd);
			assertEquals(retRoleUpd.getRoleName(), "ROLE_LP_TEST_NP");
			assertEquals(retRoleUpd.getDescription(), "LogixPath testing role new product");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		// Delete Role
		System.out.println("    Delete ROLE_LP_TEST_NP");
		try {
			int numRecDeleted = roleAndHierarchyDao.deleteRole(1, retRole.getId());
			assertEquals(numRecDeleted, 1);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		System.out.println("     <-- Done.");
	}

	public void testRoleHierarchy() {
		try {
			// Find the LogixPath (OwnerAccountId=1) superadmin 
			Role role = roleAndHierarchyDao.findRoleById(1, 1);
			Role roleInc = roleAndHierarchyDao.findRoleById(1, 2);
			assertNotNull(role);
			assertNotNull(roleInc);

			// Test RoleHierarchy
			System.out.println("Test --> findRoleHierarchy");
			System.out.println("    findRoleHierarchy [role=ROLE_LP_SUPERADMIN, includedRole=ROLE_LP_SYSADMIN OwnerAccountId=1]");
			RoleHierarchy roleHiera = roleAndHierarchyDao.findRoleHierarchy(role, roleInc);
			assertNotNull(roleHiera);
			assertEquals(roleHiera.getRoleId(), 1);
			assertEquals(roleHiera.getIncludedRoleId(), 2);

			// Input Parameters -> 1st: OwnerAccoutId; 2nd: RoleId; 3rd: IncludedRoleId 
			roleHiera = roleAndHierarchyDao.findRoleHierarchyByRoleIds(1, 1, 2);
			assertNotNull(roleHiera);
			assertEquals(roleHiera.getRoleId(), 1);
			assertEquals(roleHiera.getIncludedRoleId(), 2);
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testAddDeleteRoleHierarchy() {
		// Create RoleHierarchy
		System.out.println("Test --> add and delete RoleHierarchy");
		System.out.println("    Add RoleHierarchy RoleId 2 IncludedRoleId 7");
		RoleHierarchy roleHiera = new RoleHierarchy(2, 7, 1);
		assertNotNull(roleHiera);
		RoleHierarchy retRoleHiera = null;
		try {
			int retId = roleAndHierarchyDao.addRoleHierarchy(roleHiera);
			assertTrue(retId > 0);
			retRoleHiera = roleAndHierarchyDao.findRoleHierarchyByRoleIds(1, 2, 7);
			assertNotNull(retRoleHiera);
			assertEquals(retRoleHiera.getRoleId(), 2);
			assertEquals(retRoleHiera.getIncludedRoleId(), 7);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		// Delete RoleHierarchy
		System.out.println("    Delete RoleHierarchy");
		try {
			int numRecDeleted = roleAndHierarchyDao.deleteRoleHierarchy(1, retRoleHiera.getRoleId(),
					retRoleHiera.getIncludedRoleId());
			assertEquals(numRecDeleted, 1);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		System.out.println("     <-- Done.");
	}
}
