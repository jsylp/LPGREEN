package org.lpgreen.service;

import java.util.UUID;

import org.lpgreen.domain.Role;
import org.lpgreen.domain.RoleHierarchy;
import org.lpgreen.domain.LoginUserRole;
import org.lpgreen.domain.OperationRight;
import org.lpgreen.domain.AccessControlList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * It is the SecurityManager junit testing class. 
 * 
 * Creation date: Feb. 23, 2013
 * Last modify date: Feb. 23, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class SecurityManagerTests extends AbstractTransactionalDataSourceSpringContextTests {
	private UUID currentUserId;
	private int  currentUserOwnerAccountId;

	private SecurityManager securityManager;
	@Autowired
	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] {"classpath:test-context.xml"};
	}

	@Override
	protected void onSetUpInTransaction() throws Exception {
		currentUserId = UUID.fromString("86291951-969e-4b5b-ab37-29a5749544ed"); // sysadmin@logixpath.com, used as the object owner id
		currentUserOwnerAccountId = 1; 	// LogixPath Company, used as the object owner account id
	}

	public void testAddUpdateDeleteRole() {
		System.out.println("Test --> testAddUpdateDeleteRole");
		Role role = new Role("ROLE_LP_TEST", "LogixPath Test Role", currentUserOwnerAccountId);

		// Create the Role
		Role retRole = null;
		try {
			retRole = securityManager.createRole(
					currentUserId, currentUserOwnerAccountId, role);
			assertNotNull(retRole);
			assertEquals(retRole.getRoleName(), "ROLE_LP_TEST");
			assertEquals(retRole.getDescription(), "LogixPath Test Role");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		// Update the Role
		try {
			retRole.setDescription("Testing LPGREEN schedule change");

			Role retDeptUpd = securityManager.updateRole(
					currentUserId, retRole);
			assertNotNull(retDeptUpd);
			assertEquals(retDeptUpd.getDescription(), "Testing LPGREEN schedule change");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		// Delete the Role
		try {
			int numRecsDeleted = securityManager.deleteRole(currentUserId,
					currentUserOwnerAccountId, retRole.getId());
			assertTrue(numRecsDeleted > 0);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		System.out.println("     <-- Done.");
	}

	public void testAddUpdateRoleHierarchy() {
		System.out.println("Test --> testAddUpdateRoleHierarchy");

		// RoleId=1 - ROLE_LP_SUPERADMIN, IncludedRoleId=7 - ROLE_LP_SITE_PARTNER 
		RoleHierarchy roleHiera = new RoleHierarchy(1, 7, currentUserOwnerAccountId);

		// Create the RoleHierarchy
		// Note: RoleHierarchy is relationship, the create method returns the
		// number of records added, which should be 1.
		try {
			int numRecsAdded = securityManager.createRoleHierarchy(
					currentUserId, roleHiera);
			assertTrue(numRecsAdded > 0);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		RoleHierarchy roleHieraAdded = null;
		try {
			roleHieraAdded = securityManager.findRoleHierarchyByRoleIds(currentUserOwnerAccountId, 1, 7);
			assertNotNull(roleHieraAdded);
			assertEquals(roleHieraAdded.getRoleId(), 1);
			assertEquals(roleHieraAdded.getIncludedRoleId(), 7);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		// Delete the RoleHierarchy
		try {
			int numRecsDeleted = securityManager.deleteRoleHierarchy(currentUserId,
					currentUserOwnerAccountId, roleHieraAdded.getRoleId(), roleHieraAdded.getIncludedRoleId());
			assertTrue(numRecsDeleted > 0);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		System.out.println("     <-- Done.");
	}

	public void testAddUpdateLoginUserRole() {
		System.out.println("Test --> testAddUpdateLoginUserRole");

		// LoginUserId=1 - root, RoleId=2 - ROLE_LP_SYSADMIN 
		LoginUserRole userRole = new LoginUserRole(1, 2, currentUserOwnerAccountId);

		// Create the LoginUserRole
		// Note: LoginUserRole is relationship, the create method returns the
		// number of records added, which should be 1.
		try {
			int numRecsAdded = securityManager.createLoginUserRole(
					currentUserId, userRole);
			assertTrue(numRecsAdded > 0);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		LoginUserRole userRoleAdded = null;
		try {
			userRoleAdded = securityManager.findLoginUserRoleByUserIdAndRoleId(
					currentUserOwnerAccountId, 1, 2);
			assertNotNull(userRoleAdded);
			assertEquals(userRoleAdded.getLoginUserId(), 1);
			assertEquals(userRoleAdded.getRoleId(), 2);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		// Delete the LoginUserRole
		try {
			int numRecsDeleted = securityManager.deleteLoginUserRole(currentUserId,
					currentUserOwnerAccountId, userRoleAdded.getLoginUserId(), userRoleAdded.getRoleId());
			assertTrue(numRecsDeleted > 0);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		System.out.println("     <-- Done.");
	}

	public void testAddUpdateDeleteOperationRight() {
		System.out.println("Test --> testAddUpdateDeleteOperationRight");
		OperationRight opRight = new OperationRight("Save", "LogixPath Save Right", currentUserOwnerAccountId);

		// Create the OperationRight
		OperationRight retOpRight = null;
		try {
			retOpRight = securityManager.createOperationRight(currentUserId, opRight);
			assertNotNull(retOpRight);
			assertEquals(retOpRight.getOperationName(), "Save");
			assertEquals(retOpRight.getDescription(), "LogixPath Save Right");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		// Update the OperationRight
		try {
			retOpRight.setDescription("LogixPath LPGREEN Save change");

			OperationRight retOpRightUpd = securityManager.updateOperationRight(
					currentUserId, retOpRight);
			assertNotNull(retOpRightUpd);
			assertEquals(retOpRightUpd.getDescription(), "LogixPath LPGREEN Save change");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		// Delete the OperationRight
		try {
			int numRecsDeleted = securityManager.deleteOperationRight(currentUserId,
					currentUserOwnerAccountId, retOpRight.getId());
			assertTrue(numRecsDeleted > 0);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		System.out.println("     <-- Done.");
	}

	public void testAddUpdateAccessControlList() {
		System.out.println("Test --> testAddUpdateAccessControlList");

		// RoleId=3 - ROLE_LP_SITE_ADMIN, ObjectName="Database", OperationRightId=7 - Export, 
		AccessControlList acList = new AccessControlList(3, "Database", 7, currentUserOwnerAccountId);

		// Create the AccessControlList
		// Note: AccessControlList is relationship, the create method returns the
		// number of records added, which should be 1.
		try {
			int numRecsAdded = securityManager.createAccessControlList(
					currentUserId, acList);
			assertTrue(numRecsAdded > 0);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		AccessControlList acListAdded = null;
		try {
			acListAdded = securityManager.findAccessControlListByRoleIdObjNameOperationRight(
					currentUserOwnerAccountId, 3, "Database", 7);
			assertNotNull(acListAdded);
			assertEquals(acListAdded.getRoleId(), 3);
			assertEquals(acListAdded.getObjectName(), "Database");
			assertEquals(acListAdded.getOperationRightId(), 7);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		// Delete the AccessControlList
		try {
			int numRecsDeleted = securityManager.deleteAccessControlList(currentUserId, acListAdded);
			assertTrue(numRecsDeleted > 0);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		System.out.println("     <-- Done.");
	}

}
