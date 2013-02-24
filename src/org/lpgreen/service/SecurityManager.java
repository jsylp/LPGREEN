package org.lpgreen.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import org.lpgreen.util.InvalidDataValueException;
import org.lpgreen.util.MissingRequiredDataException;
import org.lpgreen.domain.Role;
import org.lpgreen.domain.RoleHierarchy;
import org.lpgreen.domain.OperationRight;
import org.lpgreen.domain.LoginUserRole;
import org.lpgreen.domain.AccessControlList;
import org.springframework.dao.DuplicateKeyException;

/**
 * SecurityManager is the interface for all Security related objects
 * 
 * Creation date: Feb. 20, 2013
 * Last modify date: Feb. 20, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */
public interface SecurityManager {

	////////////////////////////////////////////
	// Role management
	////////////////////////////////////////////

	// get hierarchical included Roles by a given role
	public List<Role> getAllIncludedRoles(Role role) throws Exception;

	// get all Roles owned by a specific account id
	public List<Role> findAllOwnerAccountRoles(int ownerAccountId) throws Exception;

	// get a specific Role by a given id
	public Role findRoleById(int ownerAccountId, int id) throws Exception;

	// get a specific Role by a given name
	public Role findRoleByName(int ownerAccountId, String roleName) throws Exception;

	// Create services
	public Role createRole(UUID userId, int ownerAccountId, Role role) 
			throws MissingRequiredDataException, InvalidDataValueException, DuplicateKeyException, Exception;

	// Update services
	public Role updateRole(UUID userId, Role role) 
			throws MissingRequiredDataException, InvalidDataValueException, DuplicateKeyException, Exception;

	// Delete services
	public int deleteRole(UUID userId, int ownerAccountId, int roleId)
			throws MissingRequiredDataException, Exception;

	// Export/import to/from CSV file
	public void exportRolesToCSV(List<Role> roles, OutputStream os)
			throws Exception;
	public List<Role> importRolesFromCSV(UUID userId, int ownerAccountId, InputStream is, boolean bOverrideDup)
			throws Exception;

	////////////////////////////////////////////
	// RoleHierarchy management
	////////////////////////////////////////////

	// get a specific RoleHierarchy by a given Role
	public RoleHierarchy findRoleHierarchy(Role role, Role roleInc) throws Exception;

	// get a specific RoleHierarchy by a given RoleId
	public RoleHierarchy findRoleHierarchyByRoleIds(int ownerAccountId, int roleId, int roleIdInc)
			throws Exception;

	// Create services
	public int createRoleHierarchy(UUID userId, RoleHierarchy roleHiera) 
			throws MissingRequiredDataException, InvalidDataValueException, DuplicateKeyException, Exception;

	// Delete services
	public int deleteRoleHierarchy(UUID userId, int ownerAccountId, int roleId, int roleIdInc)
			throws MissingRequiredDataException, Exception;

	// Export/import to/from CSV file
	public void exportRoleHierarchysToCSV(List<RoleHierarchy> roleHiera, OutputStream os)
			throws Exception;
	public List<RoleHierarchy> importRoleHierarchysFromCSV(UUID userId, int ownerAccountId, InputStream is, boolean bOverrideDup)
			throws Exception;

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// LoginUserRole management
	///////////////////////////////////////////////////////////////////////////////////////////////////

	// get all LoginUserRoles owned by a specific account id
	public List<LoginUserRole> findAllSiteLoginUserRoles(int ownerAccountId, int userId)
			throws Exception;

	// get a specific LoginUserRole by given loginUserId and roleId
	public LoginUserRole findLoginUserRoleByUserIdAndRoleId(int ownerAccountId, int userId, int roleId)
			throws Exception;

	// Create services
	public int createLoginUserRole(UUID userId, LoginUserRole userRole) 
			throws MissingRequiredDataException, InvalidDataValueException, DuplicateKeyException, Exception;

	// Delete services
	public int deleteLoginUserRole(UUID userId, int ownerAccountId, int loginUserId, int roleId)
			throws MissingRequiredDataException, Exception;

	// Export/import to/from CSV file
	public void exportLoginUserRoleToCSV(List<LoginUserRole> userRoles, OutputStream os)
			throws Exception;
	public List<LoginUserRole> importLoginUserRolesFromCSV(UUID userId, int ownerAccountId, InputStream is, boolean bOverrideDup)
			throws Exception;

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// OperationRight management
	///////////////////////////////////////////////////////////////////////////////////////////////////

	// get all OperationRight owned by a specific account id
	public List<OperationRight> findAllSiteOperationRights(int ownerAccountId)
			throws Exception;

	// get a specific OperationRight by a given id
	public OperationRight findOperationRightById(int ownerAccountId, int opRightId)
			throws Exception;

	// get a specific OperationRight by a given name
	public OperationRight findOperationRightByName(int ownerAccountId, String opName)
			throws Exception;

	// Create services
	public OperationRight createOperationRight(UUID userId, OperationRight opRight) 
			throws MissingRequiredDataException, InvalidDataValueException, DuplicateKeyException, Exception;

	// Update services
	public OperationRight updateOperationRight(UUID userId, OperationRight opRight) 
			throws MissingRequiredDataException, InvalidDataValueException, DuplicateKeyException, Exception;

	// Delete services
	public int deleteOperationRight(UUID userId, int ownerAccountId, int opRightId)
			throws MissingRequiredDataException, Exception;

	// Export/import to/from CSV file
	public void exportOperationRightsToCSV(List<OperationRight> opRights, OutputStream os)
			throws Exception;
	public List<OperationRight> importOperationRightsFromCSV(UUID userId, int ownerAccountId, InputStream is, boolean bOverrideDup)
			throws Exception;

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// AccessControlList management
	///////////////////////////////////////////////////////////////////////////////////////////////////

	// get all AccessControlLists owned by a specific account id
	public List<AccessControlList> findAllSiteAccessControlLists(int ownerAccountId)
			throws Exception;

	// get all AccessControlLists by a given roleId
	public List<AccessControlList> findAccessControlListsByRoleId(int ownerAccountId, int roleId)
			throws Exception;

	// get all AccessControlLists by a given opRightId
	public List<AccessControlList> findAccessControlListsByOperationRightId(int ownerAccountId, int opRightId)
			throws Exception;

	// get all AccessControlLists by a given objName
	public List<AccessControlList> findAccessControlListsByObjectName(int ownerAccountId, String objName)
			throws Exception;

	// get a specific AccessControlList by a given roleId, objName and opRightId
	public AccessControlList findAccessControlListByRoleIdObjNameOperationRight(int ownerAccountId, int roleId, String objName, int opRightId)
			throws Exception;

	// Create services
	public int createAccessControlList(UUID userId, AccessControlList acList)
			throws MissingRequiredDataException, InvalidDataValueException, DuplicateKeyException, Exception;

	// Delete services
	public int deleteAccessControlList(UUID userId, AccessControlList acList)
			throws MissingRequiredDataException, Exception;

	// Get the user's roles and included roles to check for permission
	// Since there is no LoginUser domain object here, use LoginUserId instead
	public boolean hasPermission(int userId, String objectName, String operation, int ownerAccountId)
			throws Exception;

	// Export/import to/from CSV file
	public void exportAccessControlListsToCSV(List<AccessControlList> acLists, OutputStream os)
			throws Exception;
	public List<AccessControlList> importAccessControlListsFromCSV(UUID userId, int ownerAccountId, InputStream is, boolean bOverrideDup)
			throws Exception;

}
