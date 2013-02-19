package org.lpgreen.repository;

import java.util.List;

import org.lpgreen.domain.LoginUserRoles;
import org.springframework.dao.DuplicateKeyException;

/**
 * LoginUserRolesDao is the interface for LoginUserRoles related entity's persistence layer
 * 
 * Creation date: Feb. 16, 2013
 * Last modify date: Feb. 16, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

public interface LoginUserRolesDao {

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// LoginUserRoles related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	// get all LoginUserRoles owned by a specific account id
	public List<LoginUserRoles> findAllSiteLoginUserRoles(int ownerAccountId, int userId);

	// get a specific LoginUserRoles by given loginUserId and roleId
	public LoginUserRoles findLoginUserRolesByUserIdAndRoleId(int ownerAccountId, int userId, int roleId);

	// Add a LoginUserRoles. Return the number of records added
	public int addLoginUserRoles(LoginUserRoles userRole) 
			throws DuplicateKeyException, Exception;

	// Delete a LoginUserRoles object. Return the # of record deleted
	public int deleteLoginUserRoles(int ownerAccountId, int userId, int roleId) throws Exception;

}
