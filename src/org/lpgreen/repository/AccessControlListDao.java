package org.lpgreen.repository;

import java.util.List;

import org.lpgreen.domain.AccessControlList;
import org.springframework.dao.DuplicateKeyException;

/**
 * AccessControlListDao is the interface for AccessControlList related entity's persistence layer
 * 
 * Creation date: Feb. 16, 2013
 * Last modify date: Feb. 16, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

public interface AccessControlListDao {

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// AccessControlList related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	// get all AccessControlLists owned by a specific account id
	public List<AccessControlList> findAllSiteAccessControlLists(int ownerAccountId);

	// get a specific AccessControlList by a given roleId
	public AccessControlList findAccessControlListByRoleId(int ownerAccountId, int roleId);

	// get a specific AccessControlList by a given opRightId
	public AccessControlList findAccessControlListByOperationRightId(int ownerAccountId, int opRightId);

	// get a specific AccessControlList by a given name
	public AccessControlList findAccessControlListByName(int ownerAccountId, String name);

	// get a specific AccessControlList by a given roleId, name and opRightId
	public AccessControlList findAccessControlListByAll(int ownerAccountId, int roleId, String name, int opRightId);

	// Add a AccessControlList. Return the generated id
	public int addAccessControlList(AccessControlList acList) 
			throws DuplicateKeyException, Exception;

	// Save a the changes of an existing AccessControlList object. Return the # of record updated
	public int saveAccessControlList(AccessControlList acList)
			throws DuplicateKeyException, Exception;

	// Delete a AccessControlList object. Return the # of record deleted
	public int deleteAccessControlList(int ownerAccountId, int roleId) throws Exception;

	// Get the user's roles and included roles to check for permission
	// Since there is no LoginUser domain object here, use LoginUserId instead
	public boolean hasPermission(int userId, String objectName, String operation, int ownerAccountId)
			throws Exception;
}
