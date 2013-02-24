package org.lpgreen.repository;

import java.util.List;

import org.lpgreen.domain.LoginUserRole;
import org.springframework.dao.DuplicateKeyException;

/**
 * LoginUserRoleDao is the interface for LoginUserRole related entity's persistence layer
 * 
 * Creation date: Feb. 16, 2013
 * Last modify date: Feb. 16, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

public interface LoginUserRoleDao {

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// LoginUserRole related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	// get all LoginUserRoles owned by a specific account id
	public List<LoginUserRole> findAllSiteLoginUserRoles(int ownerAccountId, int userId);

	// get a specific LoginUserRole by given loginUserId and roleId
	public LoginUserRole findLoginUserRoleByUserIdAndRoleId(int ownerAccountId, int userId, int roleId);

	// Add a LoginUserRole. Return the number of records added
	public int addLoginUserRole(LoginUserRole userRole) 
			throws DuplicateKeyException, Exception;

	// Delete a LoginUserRole object. Return the # of record deleted
	public int deleteLoginUserRole(int ownerAccountId, int userId, int roleId) throws Exception;

}
