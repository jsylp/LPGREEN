package org.lpgreen.repository;

import java.util.List;

import org.lpgreen.domain.LoginUserRole;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * It is the LoginUserRole JDBC implementation testing class. 
 * 
 * Creation date: Feb. 18, 2013
 * Last modify date: Feb. 18, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

@SuppressWarnings("deprecation")
public class JdbcLoginUserRoleDaoTests  extends AbstractTransactionalDataSourceSpringContextTests  {

	private LoginUserRoleDao loginUserRoleDao;
	public void setLoginUserRoleDao(LoginUserRoleDao loginUserRoleDao) {
		this.loginUserRoleDao = loginUserRoleDao;
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
			List<LoginUserRole> userRoles = loginUserRoleDao.findAllSiteLoginUserRoles(1, 1);
			assertNotNull(userRoles);
			assertTrue(userRoles.size() >= 1);
			for (LoginUserRole r : userRoles) {
				System.out.println("LoginUserRole: userId=" + r.getLoginUserId() + "; roleId=" + r.getRoleId() + "; roleName=" + r.getRoleName());
			}

			System.out.println("    find one specific LoginUserRole (userId=2 and roleId=2)");
			LoginUserRole userRole = loginUserRoleDao.findLoginUserRoleByUserIdAndRoleId(1, 2, 2);
			assertNotNull(userRole);
			System.out.println("LoginUserRole (1, 2, 2: useId=" + userRole.getLoginUserId() + "; roleId=" + userRole.getRoleId() + "; roleName=" + userRole.getRoleName());
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcLoginUserRoleDaoTests.findAllSiteLoginUserRoles Exception: " + e.getMessage());
		}
	}

	public void testAddDeleteLoginUserRole() {
		// Create LoginUserRole
		System.out.println("Test --> add and delete LoginUserRole");
		System.out.println("    Add LoginUserRole [userId=3, roleId=3]");
		LoginUserRole userRole = new LoginUserRole(3, 3, 1);
		assertNotNull(userRole);
		LoginUserRole retUserRole = null;
		try {
			int retId = loginUserRoleDao.addLoginUserRole(userRole);
			assertTrue(retId > 0);
			retUserRole = loginUserRoleDao.findLoginUserRoleByUserIdAndRoleId(1, userRole.getLoginUserId(), userRole.getRoleId());
			assertNotNull(retUserRole);
			assertEquals(retUserRole.getLoginUserId(), 3);
			assertEquals(retUserRole.getRoleId(), 3);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		// Delete LoginUserRole
		System.out.println("    Delete LoginUserRole [userId=3, roleId=3]");
		try {
			int numRecDeleted = loginUserRoleDao.deleteLoginUserRole(1, retUserRole.getLoginUserId(), retUserRole.getRoleId());
			assertEquals(numRecDeleted, 1);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		System.out.println("     <-- Done.");
	}
}
