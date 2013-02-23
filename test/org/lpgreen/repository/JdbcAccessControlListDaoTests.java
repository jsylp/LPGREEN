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

	public void testfindAllSiteAccessControlLists() {
		try {
			// Test find all site AccessControlList (OwnerAccountId=1)
			System.out.println("Test --> findAllSiteAccessControlLists");
			System.out.println("    findAllSiteAccessControlLists [OwnerAccountId=1]");
			List<AccessControlList> acLists = AccessControlListDao.findAllSiteAccessControlLists(1);
			assertNotNull(acLists);
			assertTrue(acLists.size() >= 4);
			for (AccessControlList acl : acLists) {
				System.out.println("AccessControlList: roleId=" + acl.getRoleId() + "; objectName=" + acl.getObjectName() +
						"; opRightId=" + acl.getOperationRightId() + "; ownerAccountId=" + acl.getOwnerAccountId());
			}

			System.out.println("    findAllSiteAccessControlLists [OwnerAccountId=1, RoleId=2]");
			acLists = AccessControlListDao.findAccessControlListsByRoleId(1, 2);
			assertNotNull(acLists);
			assertTrue(acLists.size() >= 3);
			for (AccessControlList acl : acLists) {
				System.out.println("AccessControlList: roleId=" + acl.getRoleId() + "; objectName=" + acl.getObjectName() +
						"; opRightId=" + acl.getOperationRightId() + "; ownerAccountId=" + acl.getOwnerAccountId());
			}

			System.out.println("    findAccessControlListsByOperationRightId [OwnerAccountId=1, OperationRight=6]");
			acLists = AccessControlListDao.findAccessControlListsByOperationRightId(1, 6);
			assertNotNull(acLists);
			assertTrue(acLists.size() >= 3);
			for (AccessControlList acl : acLists) {
				System.out.println("AccessControlList: roleId=" + acl.getRoleId() + "; objectName=" + acl.getObjectName() +
						"; opRightId=" + acl.getOperationRightId() + "; ownerAccountId=" + acl.getOwnerAccountId());
			}

			System.out.println("    findAccessControlListsByObjectName [OwnerAccountId=1, ObjectName=Everything]");
			acLists = AccessControlListDao.findAccessControlListsByObjectName(1, "Everything");
			assertNotNull(acLists);
			assertTrue(acLists.size() >= 3);
			for (AccessControlList acl : acLists) {
				System.out.println("AccessControlList: roleId=" + acl.getRoleId() + "; objectName=" + acl.getObjectName() +
						"; opRightId=" + acl.getOperationRightId() + "; ownerAccountId=" + acl.getOwnerAccountId());
			}

			System.out.println("    findAccessControlListByAll [OwnerAccountId=1, RoleId=2, ObjectName=Everything], OperationRightId=6");
			AccessControlList acList = AccessControlListDao.findAccessControlListByRoleIdObjNameOperationRight(1, 2, "Everything", 6);
			assertNotNull(acList);
			System.out.println("AccessControlList: roleId=" + acList.getRoleId() + "; objectName=" + acList.getObjectName() +
						"; opRightId=" + acList.getOperationRightId() + "; ownerAccountId=" + acList.getOwnerAccountId());

			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcAccessControlListDaoTests.testGetAllIncludedRoles Exception: " + e.getMessage());
		}
	}

	public void testAddDeleteAccessControlList() {
		// Create AccessControlList
		System.out.println("Test --> add and delete AccessControlList");
		System.out.println("    Add AccessControlList [roleId=2, objectName=TestObject, opRightId=4, ownerAccountId=1]");
		AccessControlList acList = new AccessControlList(2, "TestObject", 4, 1);
		assertNotNull(acList);
		AccessControlList retAcList = null;
		try {
			int retRec = AccessControlListDao.addAccessControlList(acList);
			assertTrue(retRec > 0);
			retAcList = AccessControlListDao.findAccessControlListByRoleIdObjNameOperationRight(1, acList.getRoleId(), acList.getObjectName(), acList.getOperationRightId());
			assertNotNull(retAcList);
			assertEquals(retAcList.getRoleId(), 2);
			assertEquals(retAcList.getObjectName(), "TestObject");
			assertEquals(retAcList.getOperationRightId(), 4);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		// Delete AccessControlList
		System.out.println("    Delete AccessControlList [roleId=2, objectName=TestObject, opRightId=4, ownerAccountId=1]");
		try {
			int numRecDeleted = AccessControlListDao.deleteAccessControlList(retAcList);
			assertEquals(numRecDeleted, 1);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		System.out.println("     <-- Done.");
	}

	public void testHasPermissionAccessControlList() {
		System.out.println("Test --> hasPermission AccessControlList");
		System.out.println("    hasPermission [userId=2, objectName=UserAccounts, operation=Delete, ownerAccountId=1]");
		try {
			boolean bPerm = AccessControlListDao.hasPermission(2, "UserAccounts", "Delete", 1);
			assertTrue(bPerm);
			System.out.println("    hasPermission = TRUE");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		System.out.println("    hasPermission [userId=2, objectName=UserAccounts, operation=Export, ownerAccountId=1]");
		try {
			boolean bPerm = AccessControlListDao.hasPermission(2, "UserAccounts", "Export", 1);
			assertFalse(bPerm);
			System.out.println("    hasPermission = FALSE");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		System.out.println("     <-- Done.");
	}
}
