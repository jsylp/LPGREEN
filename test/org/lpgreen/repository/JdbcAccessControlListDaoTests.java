package org.lpgreen.repository;

import java.util.List;

import org.lpgreen.domain.AccessControlList;
import org.lpgreen.domain.RoleHierarchy;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * It is the AccessControlList JDBC implementation testing class. 
 * 
 * Creation date: Feb. 18, 2013
 * Last modify date: Feb. 18, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

@SuppressWarnings("deprecation")
public class JdbcAccessControlListDaoTests  extends AbstractTransactionalDataSourceSpringContextTests  {

	private AccessControlListDao AccessControlListDao;
	public void setAccessControlListDao(AccessControlListDao AccessControlListDao) {
		this.AccessControlListDao = AccessControlListDao;
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] {"classpath:test-context.xml"};
	}

	@Override
	protected void onSetUpInTransaction() throws Exception {
	}

	public void testfindAllSiteAccessControlList() {
		try {
			// Test find all site AccessControlList (OwnerAccountId=1)
			System.out.println("Test --> findAllSiteAccessControlList");
			System.out.println("    findAllSiteAccessControlList [OwnerAccountId=1, LoginUserId=1]");
			List<AccessControlList> userRoles = AccessControlListDao.findAllSiteAccessControlList(1, 1);
			assertNotNull(userRoles);
			assertTrue(userRoles.size() >= 3);
			for (AccessControlList r : userRoles) {
				System.out.println("AccessControlList: userId=" + r.getLoginUserId() + "; roleId=" + r.getRoleId());
			}

			System.out.println("    find one specific AccessControlList (userId=2 and roleId=2)");
			AccessControlList role = AccessControlListDao.findAccessControlListByUserIdAndRoleId(1, 2, 2);
			assertNotNull(role);
			System.out.println("AccessControlList (1, 2, 2: useId=" + role.getLoginUserId() + "; roleId=" + role.getRoleId());
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcAccessControlListDaoTests.testGetAllIncludedRoles Exception: " + e.getMessage());
		}
	}

	public void testAddDeleteAccessControlList() {
		// Create AccessControlList
		System.out.println("Test --> add and delete AccessControlList");
		System.out.println("    Add AccessControlList [userId=3, roleId=3]");
		AccessControlList userRole = new AccessControlList(3, 3, 1);
		assertNotNull(userRole);
		AccessControlList retUserRole = null;
		try {
			int retId = AccessControlListDao.addAccessControlList(userRole);
			assertTrue(retId > 0);
			retUserRole = AccessControlListDao.findAccessControlListByUserIdAndRoleId(1, userRole.getLoginUserId(), userRole.getRoleId());
			assertNotNull(retUserRole);
			assertEquals(retUserRole.getLoginUserId(), 3);
			assertEquals(retUserRole.getRoleId(), 3);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		// Delete AccessControlList
		System.out.println("    Delete AccessControlList [userId=3, roleId=3]");
		try {
			int numRecDeleted = AccessControlListDao.deleteAccessControlList(1, retUserRole.getLoginUserId(), retUserRole.getRoleId());
			assertEquals(numRecDeleted, 1);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		System.out.println("     <-- Done.");
	}
}
