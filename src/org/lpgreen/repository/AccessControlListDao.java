package org.lpgreen.repository;

import java.util.List;

import org.lpgreen.domain.AccessControlList;
import org.lpgreen.util.InvalidDataValueException;
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
	public List<AccessControlList> findAccessControlListsByOwnerAccountId(int ownerAccountId);

	// get all AccessControlLists by a given roleId
	public List<AccessControlList> findAccessControlListsByRoleId(int ownerAccountId, int roleId)
			throws InvalidDataValueException;

	// get all AccessControlLists by a given opRightId
	public List<AccessControlList> findAccessControlListsByOperationRightId(int ownerAccountId, int opRightId)
			throws InvalidDataValueException;

	// get all AccessControlLists by a given objName
	public List<AccessControlList> findAccessControlListsByObjectName(int ownerAccountId, String objName)
			throws InvalidDataValueException;

	// get a specific AccessControlList by a given roleId, objName and opRightId
	public AccessControlList findAccessControlListByRoleIdObjNameOperationRight(int ownerAccountId, int roleId,
			String objName, int opRightId) throws InvalidDataValueException;

	// Add a AccessControlList. Return the generated id
	public int addAccessControlList(AccessControlList acList) throws DuplicateKeyException, Exception;

	// Delete a AccessControlList object. Return the # of record deleted
	public int deleteAccessControlList(AccessControlList acList) throws Exception;

	// Get the user's roles and included roles to check for permission
	// Since there is no LoginUser domain object here, use LoginUserId instead
	public boolean hasPermission(int userId, String objectName, String operation, int ownerAccountId)
			throws Exception;
}
