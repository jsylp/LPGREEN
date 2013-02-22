package org.lpgreen.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.lpgreen.domain.Role;
import org.lpgreen.domain.RoleHierarchy;
import org.lpgreen.domain.LoginUserRoles;
import org.lpgreen.domain.OperationRight;
import org.lpgreen.domain.AccessControlList;
import org.lpgreen.repository.RoleAndHierarchyDao;
import org.lpgreen.repository.LoginUserRolesDao;
import org.lpgreen.repository.OperationRightDao;
import org.lpgreen.repository.AccessControlListDao;
import org.lpgreen.util.InvalidDataValueException;
import org.lpgreen.util.MissingRequiredDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

/**
 * SecurityManagerImpl is the implementation of the interface SecurityManager for all role and access control related objects. 
 * It provides Request CRUD services.
 * CRUD: Create, Read, Update, Delete
 * 
 * Creation date: Feb. 21, 2013
 * Last modify date: Feb. 21, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */
public class SecurityManagerImpl implements SecurityManager {

	// Logger for this class and subclasses
	//protected final Log logger = LogFactory.getLog(getClass());

	private RoleAndHierarchyDao roleAndHierarchyDao;
	@Autowired
	public void setRoleAndHierarchyDao(RoleAndHierarchyDao roleAndHierarchyDao) {
		this.roleAndHierarchyDao = roleAndHierarchyDao;
	}

	private LoginUserRolesDao loginUserRolesDao;
	@Autowired
	public void setLoginUserRolesDao(LoginUserRolesDao loginUserRolesDao) {
		this.loginUserRolesDao = loginUserRolesDao;
	}

	private OperationRightDao operationRightDao;
	@Autowired
	public void setOperationRightDao(OperationRightDao operationRightDao) {
		this.operationRightDao = operationRightDao;
	}

	private AccessControlListDao accessControlListDao;
	@Autowired
	public void setAccessControlListDao(AccessControlListDao accessControlListDao) {
		this.accessControlListDao = accessControlListDao;
	}

	////////////////////////////////////////////
	// Role related management methods
	////////////////////////////////////////////

	// get hierarchical included Roles by a given role
	@Override
	public List<Role> getAllIncludedRoles(Role role) throws Exception {
		if (role == null) {
			throw new Exception("Invalud input role");
		}
		List<Role> roles = roleAndHierarchyDao.getAllIncludedRoles(role);
		if (roles != null && roles.size() == 0)
			roles = null;
		return roles;
	}

	// get all Roles owned by a specific account id
	@Override
	public List<Role> findAllOwnerAccountRoles(int ownerAccountId) throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		return roleAndHierarchyDao.findAllOwnerAccountRoles(ownerAccountId);
	}

	// get a specific Role by a given id
	@Override
	public Role findRoleById(int ownerAccountId, int id) throws Exception {
		if (ownerAccountId <= 0 || id <= 0) {
			throw new Exception("Invalud input ownerAccountId or id");
		}
		return roleAndHierarchyDao.findRoleById(ownerAccountId, id);
	}

	// get a specific Role by a given name
	@Override
	public Role findRoleByName(int ownerAccountId, String roleName)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (roleName == null || roleName.isEmpty()) {
			throw new Exception("Invalud input roleName");
		}
		return roleAndHierarchyDao.findRoleByName(ownerAccountId, roleName);
	}

	// Create services

	private void validateRoleData(Role role)
			throws MissingRequiredDataException, InvalidDataValueException,
					DuplicateKeyException, Exception {
		if (role.getRoleName() == null || role.getRoleName().isEmpty())
			throw new Exception("Missing required RoleName");
	}

	@Override
	public Role createRole(UUID userId, int ownerAccountId, Role role) 
			throws MissingRequiredDataException, InvalidDataValueException,
					DuplicateKeyException, Exception {
		//if (userId == null)
		//	throw new MissingRequiredDataException("Missing userId");
		if (ownerAccountId <= 0)
			throw new MissingRequiredDataException("Missing ownerAccountId");

		this.validateRoleData(role);

		// Good to go
		//DateTime currentDateTime = DateTime.now();		

		// Create the Role
		//reqCategoryAreaData.setCreatedDate(currentDateTime);
		//reqCategoryAreaData.setCreatedById(userId);
		//reqCategoryAreaData.setLastModifiedDate(currentDateTime);
		//reqCategoryAreaData.setLastModifiedById(userId);
		//reqCategoryAreaData.setOwnerId(userId);

		role.setOwnerAccountId(ownerAccountId);
		
		// Persist the Role object
		try {
			int retId = roleAndHierarchyDao.addRole(role);
			role.setId(retId);

			// retrieve the new data back
			Role retRole = this.findRoleById(ownerAccountId, retId);
			return retRole;
		}
		catch (Exception e) {
			//logger.info("SecurityManagerImpl.createRole: fail to create Role. Exception: " + e.getMessage());
			throw e;
		}
	}

	// Update services

	@Override
	public Role updateRole(UUID userId, Role role) 
			throws MissingRequiredDataException, InvalidDataValueException,
				DuplicateKeyException, Exception {
		//if (userId == null)
		//	throw new MissingRequiredDataException("Missing userId");
		
		this.validateRoleData(role);

		// Good to go
		//DateTime currentDateTime = DateTime.now();		
		try {
			// Persist the Role changes
			//reqCategoryAreaData.setLastModifiedDate(currentDateTime);
			//reqCategoryAreaData.setLastModifiedById(userId);

			@SuppressWarnings("unused")
			int numRecordUpdated = roleAndHierarchyDao.saveRole(role);

			// retrieve the new data back
			Role retRole = this.findRoleById(role.getOwnerAccountId(), role.getId());
			return retRole;
		}
		catch (Exception e) {
			//logger.info("SecurityManagerImpl.updateRole: fail to update Role. Exception: " + e.getMessage());
			throw e;
		}
	}

	// Delete services

	@Override
	public int deleteRole(UUID userId, int ownerAccountId, int roleId)
			throws MissingRequiredDataException, Exception {
		//if (userId == null)
		//	throw new MissingRequiredDataException("Missing userId");
		if (ownerAccountId <= 0) {
			throw new MissingRequiredDataException("Missing ownerAccountId");
		}
		if (roleId <= 0) {
			throw new MissingRequiredDataException("Missing roleId");
		}

		int numRecordDeleted = roleAndHierarchyDao.deleteRole(ownerAccountId, roleId);
		if (numRecordDeleted == 0) {
			throw new Exception("Fail to delete Role: " + roleId);
		}
		return numRecordDeleted;
	}

	// Export/import to/from CSV file

	@Override
	public void exportRolesToCSV(List<Role> roles, OutputStream os)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Role> importRolesFromCSV(UUID userId,
			int ownerAccountId, InputStream is, boolean bOverrideDup)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	////////////////////////////////////////////
	// RoleHierarchy related management methods
	////////////////////////////////////////////

	// get a specific RoleHierarchy by a given Role
	@Override
	public RoleHierarchy findRoleHierarchy(Role role, Role roleInc) throws Exception {
		if (role == null) {
			throw new Exception("Missing input role");
		}
		if (roleInc == null) {
			throw new Exception("Missing input roleInc");
		}
		return roleAndHierarchyDao.findRoleHierarchy(role, roleInc);
	}

	// get a specific RoleHierarchy by a given RoleId
	@Override
	public RoleHierarchy findRoleHierarchyByRoleIds(int ownerAccountId, int roleId, int roleIdInc)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Missing input ownerAccountId");
		}
		if (roleId <= 0 || roleIdInc <= 0) {
			throw new Exception("Missing input roleId or roleIdInc");
		}
		return roleAndHierarchyDao.findRoleHierarchyByRoleIds(ownerAccountId, roleId, roleIdInc);
	}

	// Create services

	private void validateRoleHierarchyData(RoleHierarchy roleHiera)
			throws MissingRequiredDataException, InvalidDataValueException,
					DuplicateKeyException, Exception {
		if (roleHiera.getRoleId() <= 0 || roleHiera.getIncludedRoleId() <= 0)
			throw new Exception("Invalid input roleHierarchy");
	}

	@Override
	public int createRoleHierarchy(UUID userId, RoleHierarchy roleHiera) 
			throws MissingRequiredDataException, InvalidDataValueException,
					DuplicateKeyException, Exception {
		//if (userId == null)
		//	throw new MissingRequiredDataException("Missing userId");

		this.validateRoleHierarchyData(roleHiera);

		// Good to go
		//DateTime currentDateTime = DateTime.now();		

		// Create the RoleHierarchy
		//reqCategoryAreaData.setCreatedDate(currentDateTime);
		//reqCategoryAreaData.setCreatedById(userId);
		//reqCategoryAreaData.setLastModifiedDate(currentDateTime);
		//reqCategoryAreaData.setLastModifiedById(userId);
		//reqCategoryAreaData.setOwnerId(userId);

		// Persist the RoleHierarchy object
		try {
			int numRecAdded = roleAndHierarchyDao.addRoleHierarchy(roleHiera);
			if (numRecAdded == 0) {
				throw new Exception("Fail to add RoleHierarchy");
			}
			return numRecAdded;
		}
		catch (Exception e) {
			//logger.info("SecurityManagerImpl.createRoleHierarchy: fail to create RoleHierarchy. Exception: " + e.getMessage());
			throw e;
		}
	}

	// Delete services

	@Override
	public int deleteRoleHierarchy(UUID userId, int ownerAccountId, int roleId, int roleIdInc)
			throws MissingRequiredDataException, Exception {
		//if (userId == null)
		//	throw new MissingRequiredDataException("Missing userId");
		if (ownerAccountId <= 0) {
			throw new MissingRequiredDataException("Missing ownerAccountId");
		}
		if (roleId <= 0 || roleIdInc <= 0) {
			throw new MissingRequiredDataException("Invalid roleId or roleIdInc");
		}

		int numRecordDeleted = roleAndHierarchyDao.deleteRoleHierarchy(ownerAccountId, roleId, roleIdInc);
		if (numRecordDeleted == 0) {
			throw new Exception("Fail to delete RoleHierarchy: (" + roleId + ", " + roleIdInc + ")");
		}
		return numRecordDeleted;
	}

	// Export/import to/from CSV file

	@Override
	public void exportRoleHierarchysToCSV(List<RoleHierarchy> roleHiera, OutputStream os)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public List<RoleHierarchy> importRoleHierarchysFromCSV(UUID userId,
			int ownerAccountId, InputStream is, boolean bOverrideDup)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	////////////////////////////////////////////
	// LoginUserRoles related management methods
	////////////////////////////////////////////

	// get all LoginUserRoles owned by a specific account id
	@Override
	public List<LoginUserRoles> findAllSiteLoginUserRoles(int ownerAccountId, int userId)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Missing input ownerAccountId");
		}
		if (userId <= 0) {
			throw new Exception("Missing input loginUserId");
		}
		List<LoginUserRoles> userRoles = loginUserRolesDao.findAllSiteLoginUserRoles(ownerAccountId, userId);
		if (userRoles != null && userRoles.size() == 0)
			userRoles = null;
		return userRoles;
	}

	// get a specific LoginUserRoles by given loginUserId and roleId
	@Override
	public LoginUserRoles findLoginUserRolesByUserIdAndRoleId(int ownerAccountId, int userId, int roleId)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Missing input ownerAccountId");
		}
		if (userId <= 0) {
			throw new Exception("Missing input loginUserId");
		}
		if (roleId <= 0) {
			throw new Exception("Missing input roleId");
		}
		return loginUserRolesDao.findLoginUserRolesByUserIdAndRoleId(ownerAccountId, userId, roleId);
	}

	// Create services

	private void validateLoginUserRolesData(LoginUserRoles userRole)
			throws MissingRequiredDataException, InvalidDataValueException,
					DuplicateKeyException, Exception {
		if (userRole.getLoginUserId() <= 0 || userRole.getRoleId() <= 0)
			throw new Exception("Invalid input roleHierarchy");
	}

	@Override
	public int createLoginUserRoles(UUID userId, LoginUserRoles userRole) 
			throws MissingRequiredDataException, InvalidDataValueException,
					DuplicateKeyException, Exception {
		//if (userId == null)
		//	throw new MissingRequiredDataException("Missing userId");
		if (userRole == null) {
			throw new Exception("Missing input userRole");
		}

		this.validateLoginUserRolesData(userRole);

		// Good to go
		//DateTime currentDateTime = DateTime.now();		

		// Create the LoginUserRoles
		//reqCategoryAreaData.setCreatedDate(currentDateTime);
		//reqCategoryAreaData.setCreatedById(userId);
		//reqCategoryAreaData.setLastModifiedDate(currentDateTime);
		//reqCategoryAreaData.setLastModifiedById(userId);
		//reqCategoryAreaData.setOwnerId(userId);

		// Persist the LoginUserRoles object
		try {
			int numRecAdded = loginUserRolesDao.addLoginUserRoles(userRole);
			if (numRecAdded == 0) {
				throw new Exception("Fail to add LoginUserRoles");
			}
			return numRecAdded;
		}
		catch (Exception e) {
			//logger.info("SecurityManagerImpl.createLoginUserRoles: fail to create LoginUserRoles. Exception: " + e.getMessage());
			throw e;
		}
	}

	// Delete services

	@Override
	public int deleteLoginUserRoles(UUID userId, int ownerAccountId, int loginUserId, int roleId)
			throws MissingRequiredDataException, Exception {
		//if (userId == null)
		//	throw new MissingRequiredDataException("Missing userId");
		if (ownerAccountId <= 0) {
			throw new MissingRequiredDataException("Missing input ownerAccountId");
		}
		if (loginUserId <= 0) {
			throw new MissingRequiredDataException("Missing input loginUserId");
		}
		if (roleId <= 0) {
			throw new MissingRequiredDataException("Missing input roleId");
		}

		int numRecordDeleted = loginUserRolesDao.deleteLoginUserRoles(ownerAccountId, loginUserId, roleId);
		if (numRecordDeleted == 0) {
			throw new Exception("Fail to delete LoginUserRoles: (" + loginUserId + ", " + roleId + ")");
		}
		return numRecordDeleted;
	}

	// Export/import to/from CSV file

	@Override
	public void exportLoginUserRolesToCSV(List<LoginUserRoles> userRoles, OutputStream os)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public List<LoginUserRoles> importLoginUserRolesFromCSV(UUID userId,
			int ownerAccountId, InputStream is, boolean bOverrideDup)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	////////////////////////////////////////////
	// OperationRight related management methods
	////////////////////////////////////////////

	// get all OperationRight owned by a specific account id
	@Override
	public List<OperationRight> findAllSiteOperationRights(int ownerAccountId)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Missing input ownerAccountId");
		}
		List<OperationRight> opRights = operationRightDao.findAllSiteOperationRights(ownerAccountId);
		if (opRights != null && opRights.size() == 0)
			opRights = null;
		return opRights;
	}

	// get a specific OperationRight by a given id
	@Override
	public OperationRight findOperationRightById(int ownerAccountId, int opRightId)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Missing input ownerAccountId");
		}
		if (opRightId <= 0) {
			throw new Exception("Missing input operationRightId");
		}
		return operationRightDao.findOperationRightById(ownerAccountId, opRightId);
	}

	// get a specific OperationRight by a given id
	@Override
	public OperationRight findOperationRightByName(int ownerAccountId, String opName)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Missing input ownerAccountId");
		}
		if (opName == null || opName.isEmpty()) {
			throw new Exception("Missing input operationName");
		}
		return operationRightDao.findOperationRightByName(ownerAccountId, opName);
	}

	// Create services

	private void validateOperationRightData(OperationRight opRight)
			throws MissingRequiredDataException, InvalidDataValueException,
					DuplicateKeyException, Exception {
		if (opRight.getOperationName() == null || opRight.getOperationName().isEmpty())
			throw new Exception("Invalid input opRight");
	}

	@Override
	public OperationRight createOperationRight(UUID userId, OperationRight opRight) 
			throws MissingRequiredDataException, InvalidDataValueException,
					DuplicateKeyException, Exception {
		//if (userId == null)
		//	throw new MissingRequiredDataException("Missing userId");
		if (opRight == null) {
			throw new Exception("Missing input opRight");
		}

		this.validateOperationRightData(opRight);

		// Good to go
		//DateTime currentDateTime = DateTime.now();		

		// Create the OperationRight
		//reqCategoryAreaData.setCreatedDate(currentDateTime);
		//reqCategoryAreaData.setCreatedById(userId);
		//reqCategoryAreaData.setLastModifiedDate(currentDateTime);
		//reqCategoryAreaData.setLastModifiedById(userId);
		//reqCategoryAreaData.setOwnerId(userId);

		opRight.setOwnerAccountId(opRight.getOwnerAccountId());
		
		// Persist the OperationRight object
		try {
			int retId = operationRightDao.addOperationRight(opRight);
			opRight.setId(retId);

			// retrieve the new data back
			OperationRight retOpRight = this.findOperationRightById(opRight.getOwnerAccountId(), retId);
			return retOpRight;
		}
		catch (Exception e) {
			//logger.info("SecurityManagerImpl.createOperationRight: fail to create OperationRight. Exception: " + e.getMessage());
			throw e;
		}
	}

	// Update services

	@Override
	public OperationRight updateOperationRight(UUID userId, OperationRight opRight) 
			throws MissingRequiredDataException, InvalidDataValueException,
				DuplicateKeyException, Exception {
		//if (userId == null)
		//	throw new MissingRequiredDataException("Missing userId");
		
		this.validateOperationRightData(opRight);

		// Good to go
		//DateTime currentDateTime = DateTime.now();		
		try {
			// Persist the Role changes
			//reqCategoryAreaData.setLastModifiedDate(currentDateTime);
			//reqCategoryAreaData.setLastModifiedById(userId);

			@SuppressWarnings("unused")
			int numRecordUpdated = operationRightDao.saveOperationRight(opRight);

			// retrieve the new data back
			OperationRight retOpRight = this.findOperationRightById(opRight.getOwnerAccountId(), opRight.getId());
			return retOpRight;
		}
		catch (Exception e) {
			//logger.info("SecurityManagerImpl.updateOperationRight: fail to update OperationRight. Exception: " + e.getMessage());
			throw e;
		}
	}

	// Delete services

	@Override
	public int deleteOperationRight(UUID userId, int ownerAccountId, int opRightId)
			throws MissingRequiredDataException, Exception {
		//if (userId == null)
		//	throw new MissingRequiredDataException("Missing userId");
		if (ownerAccountId <= 0) {
			throw new MissingRequiredDataException("Missing ownerAccountId");
		}
		if (opRightId <= 0) {
			throw new MissingRequiredDataException("Missing opRightId");
		}

		int numRecordDeleted = operationRightDao.deleteOperationRight(ownerAccountId, opRightId);
		if (numRecordDeleted == 0) {
			throw new Exception("Fail to delete OperationRight: " + opRightId);
		}
		return numRecordDeleted;
	}

	// Export/import to/from CSV file

	@Override
	public void exportOperationRightsToCSV(List<OperationRight> opRights, OutputStream os)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public List<OperationRight> importOperationRightsFromCSV(UUID userId,
			int ownerAccountId, InputStream is, boolean bOverrideDup)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	////////////////////////////////////////////
	// AccessControlList related management methods
	////////////////////////////////////////////

	// get all AccessControlLists owned by a specific account id
	@Override
	public List<AccessControlList> findAllSiteAccessControlLists(int ownerAccountId)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		List<AccessControlList> acLists = accessControlListDao.findAllSiteAccessControlLists(ownerAccountId);
		if (acLists != null && acLists.size() == 0)
			acLists = null;
		return acLists;
	}

	// get all AccessControlLists by a given roleId
	@Override
	public List<AccessControlList> findAccessControlListsByRoleId(int ownerAccountId, int roleId)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (roleId <= 0) {
			throw new Exception("Invalud input roleId");
		}
		List<AccessControlList> acLists = accessControlListDao.findAccessControlListsByRoleId(ownerAccountId, roleId);
		if (acLists != null && acLists.size() == 0)
			acLists = null;
		return acLists;
	}

	// get all AccessControlLists by a given opRightId
	@Override
	public List<AccessControlList> findAccessControlListsByOperationRightId(int ownerAccountId, int opRightId)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (opRightId <= 0) {
			throw new Exception("Invalud input opRightId");
		}
		List<AccessControlList> acLists = accessControlListDao.findAccessControlListsByOperationRightId(ownerAccountId, opRightId);
		if (acLists != null && acLists.size() == 0)
			acLists = null;
		return acLists;
	}

	// get all AccessControlLists by a given objName
	@Override
	public List<AccessControlList> findAccessControlListsByObjectName(int ownerAccountId, String objName)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (objName == null || objName.isEmpty()) {
			throw new Exception("Invalud input objName");
		}
		List<AccessControlList> acLists = accessControlListDao.findAccessControlListsByObjectName(ownerAccountId, objName);
		if (acLists != null && acLists.size() == 0)
			acLists = null;
		return acLists;
	}

	// get a specific AccessControlList by a given roleId, objName and opRightId
	@Override
	public AccessControlList findAccessControlListByAll(int ownerAccountId, int roleId, String objName, int opRightId)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (roleId <= 0) {
			throw new Exception("Invalud input roleId");
		}
		if (objName == null || objName.isEmpty()) {
			throw new Exception("Invalud input objName");
		}
		if (opRightId <= 0) {
			throw new Exception("Invalud input opRightId");
		}
		return accessControlListDao.findAccessControlListByAll(ownerAccountId, roleId, objName, opRightId);
	}

	// Create services

	private void validateAccessControlListData(AccessControlList acList)
			throws MissingRequiredDataException, InvalidDataValueException,
					DuplicateKeyException, Exception {
		if (acList.getRoleId() <= 0 || acList.getOperationRightId() <= 0 ||
		    acList.getObjectName() == null || acList.getObjectName().isEmpty())
			throw new Exception("Invalid input acList");
	}

	@Override
	public int createAccessControlList(UUID userId, AccessControlList acList) 
			throws MissingRequiredDataException, InvalidDataValueException,
					DuplicateKeyException, Exception {
		//if (userId == null)
		//	throw new MissingRequiredDataException("Missing userId");
		if (acList == null) {
			throw new Exception("Missing input acList");
		}

		this.validateAccessControlListData(acList);

		// Good to go
		//DateTime currentDateTime = DateTime.now();		

		// Create the AccessControlList
		//reqCategoryAreaData.setCreatedDate(currentDateTime);
		//reqCategoryAreaData.setCreatedById(userId);
		//reqCategoryAreaData.setLastModifiedDate(currentDateTime);
		//reqCategoryAreaData.setLastModifiedById(userId);
		//reqCategoryAreaData.setOwnerId(userId);

		// Persist the AccessControlList object
		try {
			int numRecAdded = accessControlListDao.addAccessControlList(acList);
			if (numRecAdded == 0) {
				throw new Exception("Fail to add RoleHierarchy");
			}
			return numRecAdded;
		}
		catch (Exception e) {
			//logger.info("SecurityManagerImpl.createAccessControlList: fail to create AccessControlList. Exception: " + e.getMessage());
			throw e;
		}
	}

	// Delete services

	@Override
	public int deleteAccessControlList(UUID userId, AccessControlList acList)
			throws MissingRequiredDataException, Exception {
		//if (userId == null)
		//	throw new MissingRequiredDataException("Missing userId");
		if (acList == null) {
			throw new MissingRequiredDataException("Missing acList");
		}

		int numRecordDeleted = accessControlListDao.deleteAccessControlList(acList);
		if (numRecordDeleted == 0) {
			throw new Exception("Fail to delete AccessControlList: (" + acList.getRoleId() +
					", " + acList.getObjectName() + ", " + acList.getOperationRightId() + ")");
		}
		return numRecordDeleted;
	}

	// Get the user's roles and included roles to check for permission
	// Since there is no LoginUser domain object here, use LoginUserId instead
	public boolean hasPermission(int userId, String objectName, String operation, int ownerAccountId)
			throws Exception {
		if (userId <= 0) {
			throw new Exception("Missing input userId");
		}
		if (objectName == null || objectName.isEmpty()) {
			throw new Exception("Missing input objectName");
		}
		if (operation == null || operation.isEmpty()) {
			throw new Exception("Missing input operation");
		}
		if (ownerAccountId <= 0) {
			throw new Exception("Missing input ownerAccountId");
		}
		return accessControlListDao.hasPermission(userId, objectName, operation, ownerAccountId);
	}

	// Export/import to/from CSV file

	@Override
	public void exportAccessControlListsToCSV(List<AccessControlList> acLists, OutputStream os)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public List<AccessControlList> importAccessControlListsFromCSV(UUID userId,
			int ownerAccountId, InputStream is, boolean bOverrideDup)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
