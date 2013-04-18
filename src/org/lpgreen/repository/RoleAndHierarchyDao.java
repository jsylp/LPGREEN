package org.lpgreen.repository;

import java.util.List;

import org.lpgreen.domain.Role;
import org.lpgreen.domain.RoleHierarchy;
import org.springframework.dao.DuplicateKeyException;

/**
 * RoleAndHierarchyDao is the interface for Role and RoleHierarchy related entity's persistence layer
 * 
 * Creation date: Feb. 11, 2013
 * Last modify date: Feb. 11, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

public interface RoleAndHierarchyDao {

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// Role related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	// get hierarchical included Roles by a given role
	public List<Role> getAllIncludedRoles(Role role) throws Exception;

	// get all Roles owned by a specific account id
	public List<Role> findRolesByOwnerAccountId(int ownerAccountId);

	// get a specific Role by a given id
	public Role findRoleById(int ownerAccountId, int id);

	// get a specific Role by a given name
	public List<Role> findRoleByName(int ownerAccountId, String roleName);

	// Add a Role. Return the generated id
	public int addRole(Role role) 
			throws DuplicateKeyException, Exception;

	// Save the changes of an existing Role object. Return the # of record updated
	public int saveRole(Role role) 
			throws DuplicateKeyException, Exception;

	// Delete a Role object. Return the # of record deleted
	public int deleteRole(int ownerAccountId, int id) throws Exception;

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// RoleHierarchy related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	// get a specific RoleHierarchy by a given Role
	public RoleHierarchy findRoleHierarchy(int ownerAccountId, Role role, Role roleInc)
			throws Exception;

	// get a specific RoleHierarchy by a given RoleId
	public RoleHierarchy findRoleHierarchyByRoleIds(int ownerAccountId, int roleId, int roleIdInc)
			throws Exception;

	// Add a RoleHierarchy.
	public int addRoleHierarchy(RoleHierarchy roleHiera) 
			throws DuplicateKeyException, Exception;

	// Delete a RoleHierarchy object. Return the # of record deleted
	public int deleteRoleHierarchy(int ownerAccountId, int roleId, int roleIdInc) throws Exception;
}
