package org.lpgreen.repository;

import java.util.List;

import org.lpgreen.domain.LoginUserRoles;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * It is the LoginUserRoles JDBC implementation testing class. 
 * 
 * Creation date: Feb. 18, 2013
 * Last modify date: Feb. 18, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

@SuppressWarnings("deprecation")
public class JdbcLoginUserRolesDaoTests  extends AbstractTransactionalDataSourceSpringContextTests  {

	private LoginUserRolesDao loginUserRolesDao;
	public void setLoginUserRolesDao(LoginUserRolesDao loginUserRolesDao) {
		this.loginUserRolesDao = loginUserRolesDao;
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] {"classpath:test-context.xml"};
	}

	@Override
	protected void onSetUpInTransaction() throws Exception {
	}

	public void testfindAllSiteLoginUserRoles() {
		try {
			// Test find all site LoginUserRoles (OwnerAccountId=1)
			System.out.println("Test --> findAllSiteLoginUserRoles");
			System.out.println("    findAllSiteLoginUserRoles [OwnerAccountId=1, LoginUserId=1]");
			List<LoginUserRoles> userRoles = loginUserRolesDao.findAllSiteLoginUserRoles(1, 1);
			assertNotNull(userRoles);
			assertTrue(userRoles.size() >= 3);
			for (LoginUserRoles r : userRoles) {
				System.out.println("LoginUserRoles: userId=" + r.getLoginUserId() + "; roleId=" + r.getRoleId());
			}

			System.out.println("    find one specific LoginUserRoles (userId=2 and roleId=2)");
			LoginUserRoles role = loginUserRolesDao.findLoginUserRolesByUserIdAndRoleId(1, 2, 2);
			assertNotNull(role);
			System.out.println("LoginUserRoles (1, 2, 2: useId=" + role.getLoginUserId() + "; roleId=" + role.getRoleId());
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcLoginUserRolesDaoTests.testGetAllIncludedRoles Exception: " + e.getMessage());
		}
	}

	public void testAddDeleteLoginUserRoles() {
		// Create LoginUserRoles
		System.out.println("Test --> add and delete LoginUserRoles");
		System.out.println("    Add LoginUserRoles [userId=3, roleId=3]");
		LoginUserRoles userRole = new LoginUserRoles(3, 3, 1);
		assertNotNull(userRole);
		LoginUserRoles retUserRole = null;
		try {
			int retId = loginUserRolesDao.addLoginUserRoles(userRole);
			assertTrue(retId > 0);
			retUserRole = loginUserRolesDao.findLoginUserRolesByUserIdAndRoleId(1, userRole.getLoginUserId(), userRole.getRoleId());
			assertNotNull(retUserRole);
			assertEquals(retUserRole.getLoginUserId(), 3);
			assertEquals(retUserRole.getRoleId(), 3);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		// Delete LoginUserRoles
		System.out.println("    Delete LoginUserRoles [userId=3, roleId=3]");
		try {
			int numRecDeleted = loginUserRolesDao.deleteLoginUserRoles(1, retUserRole.getLoginUserId(), retUserRole.getRoleId());
			assertEquals(numRecDeleted, 1);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		System.out.println("     <-- Done.");
	}
}
