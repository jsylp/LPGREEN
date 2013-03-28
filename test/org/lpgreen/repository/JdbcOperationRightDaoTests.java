package org.lpgreen.repository;

import java.util.List;

import org.lpgreen.domain.OperationRight;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * It is the OperationRight JDBC implementation testing class. 
 * 
 * Creation date: Feb. 18, 2013
 * Last modify date: Feb. 18, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

@SuppressWarnings("deprecation")
public class JdbcOperationRightDaoTests  extends AbstractTransactionalDataSourceSpringContextTests  {

	private OperationRightDao opRightDao;
	public void setOperationRightDao(OperationRightDao opRightDao) {
		this.opRightDao = opRightDao;
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] {"classpath:test-context.xml"};
	}

	@Override
	protected void onSetUpInTransaction() throws Exception {
	}

	public void testFindAllSiteOperationRights() {
		try {
			// Test to find the LogixPath (OwnerAccountId=1) all OperationRights 
			System.out.println("Test --> findAllSiteOperationRights");
			System.out.println("    findAllSiteOperationRights [OwnerAccountId=1]");
			List<OperationRight> opRights = opRightDao.findAllSiteOperationRights(1);
			assertNotNull(opRights);
			assertTrue(opRights.size() >= 10);
			for (OperationRight right : opRights) {
				System.out.println("role: id=" + right.getId() + "; name=" + right.getOperationName() + "; description=" + right.getDescription());
			}

			System.out.println("    find one specific OperationRight (id=6)");
			OperationRight opRight = opRightDao.findOperationRightById(6);
			assertNotNull(opRight);
			assertTrue(opRight.getOperationName() != null && opRight.getOperationName().equals("Clone"));
			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("JdbcOperationRightDaoTests.testFindAllSiteOperationRights Exception: " + e.getMessage());
		}
	}

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
			retRight = opRightDao.findOperationRightById(retId);
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
			OperationRight retRightUpd = opRightDao.findOperationRightById(retRight.getId());
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
}
