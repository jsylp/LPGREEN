package org.lpgreen.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.lpgreen.domain.LoginUserRole;
import org.lpgreen.util.MustOverrideException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * JdbcLoginUserRoleDao is the JDBC implementation of the LoginUserRoleDao for LoginUserRole related entity's persistence layer
 * 
 * Creation date: Feb. 16, 2013
 * Last modify date: Feb. 16, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

@Repository
public class JdbcLoginUserRoleDao extends LPJdbcGeneric<LoginUserRole> implements LoginUserRoleDao {

	public void setDataSource(DataSource dataSource)
			throws MustOverrideException {
		try {
			super.setDataSource(dataSource);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcLoginUserRoleDao.setDataSource Exception: " + e.getMessage());
			throw e;
		}
	}

	// o: the main object: this LoginUserRole;
	protected final static String fieldSelectionForReadLoginUserRole =
			"o.LoginUserId,o.RoleId,o.OwnerAccountId,r.RoleName";

	// field selection for update
	protected final static String fieldSetForUpdateLoginUserRole = 
			"LoginUserId=:LoginUserId,RoleId=:RoleId,OwnerAccountId=:OwnerAccountId";

	// RowMapper class
	private static class LoginUserRoleMapper implements RowMapper<LoginUserRole> {

		public LoginUserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
			LoginUserRole userRole = new LoginUserRole();
			userRole.setLoginUserId(rs.getInt("LoginUserId"));
			userRole.setRoleId(rs.getInt("RoleId"));
			userRole.setOwnerAccountId(rs.getInt("OwnerAccountId"));
			userRole.setRoleName(rs.getString("RoleName"));
			return userRole;
		}
	}

	// Override to return the SQL table name
	protected String getSqlTable() {
		return "LoginUserRoles";
	}

	// Override to return the field selection for read
	protected String getFieldSelectionForRead() {
		return fieldSelectionForReadLoginUserRole;
	}

	// Override to return the filed selection for update
	protected String getFieldSelectionForUpdate() {
		return fieldSetForUpdateLoginUserRole;
	}

	// Override to return the RowMapper
	protected RowMapper<LoginUserRole> getRowMapper() {
		return new LoginUserRoleMapper();
	}

	// Override to return MapSqlParameterSource for creating LoginUserRole
	protected MapSqlParameterSource getLoginUserRoleMapSqlParameterSource(LoginUserRole userRole) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		if (userRole.getLoginUserId() > 0)
			parameters.addValue("LoginUserId", userRole.getLoginUserId());
		else
			parameters.addValue("LoginUserId", null);
		if (userRole.getRoleId() > 0)
			parameters.addValue("RoleId", userRole.getRoleId());
		else
			parameters.addValue("RoleId", null);
		if (userRole.getOwnerAccountId() > 0)
			parameters.addValue("OwnerAccountId", userRole.getOwnerAccountId());
		else
			parameters.addValue("OwnerAccountId", null);
		return parameters;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// LoginUserRole related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	// get all LoginUserRoles owned by a specific account id
	@Override
	public List<LoginUserRole> findAllSiteLoginUserRoles(int ownerAccountId, int userId) {
		try {
			String outJoins = "LEFT OUTER JOIN Role as r ON o.RoleId=r.Id";
			return findDomainObjectsByColumnVal(ownerAccountId, outJoins, "LoginUserId", userId, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcLoginUserRoleDao.findAllSiteLoginUserRoles MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcLoginUserRoleDao.findAllSiteLoginUserRoles Exception: " + e.getMessage());
			return null;
		}
	}

	// query LoginUserRole using LoginUseId and RoleId
	protected final static String strLoginUserRoleQueryWithUserIdAndRoleId = "select " + fieldSelectionForReadLoginUserRole +
			" from LoginUserRoles as o" +
			" LEFT OUTER JOIN Role as r ON o.RoleId=r.Id" +
			" where o.OwnerAccountId=:OwnerAccountId and LoginUserId=:LoginUserId and RoleId=:RoleId";

	// get a specific LoginUserRole by given loginUserId and roleId
	@Override
	public LoginUserRole findLoginUserRoleByUserIdAndRoleId(int ownerAccountId, int userId, int roleId) {
		try {
			LoginUserRole userRole = namedParameterJdbcTemplate.queryForObject(
					strLoginUserRoleQueryWithUserIdAndRoleId,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).
							addValue("LoginUserId", userId).addValue("RoleId", roleId),
					new LoginUserRoleMapper());
			return userRole;
		} 
		catch (Exception e) {
			System.out.println("JdbcLoginUserRoleDao.findLoginUserRoleByUserIdAndRoleId Exception: " + e.getMessage());
			return null;
		}
	}

	// Add a LoginUserRole. Return the number of records added
	@Override
	public int addLoginUserRole(LoginUserRole userRole) 
			throws DuplicateKeyException, Exception {
		if (userRole == null)
			throw new Exception("Missing input userRole");

		MapSqlParameterSource parameters = this.getLoginUserRoleMapSqlParameterSource(userRole);	
		try {
			// insert LoginUserRole record
			int retRows = insertDomainObject.execute(parameters);
			return retRows;
		}
		catch (DuplicateKeyException e) {
			System.out.println("JdbcLoginUserRoleDao.addLoginUserRole DuplicateKeyException: " + e.getMessage());
			throw e;
		}
		catch (Exception e) {
			System.out.println("JdbcLoginUserRoleDao.addLoginUserRole Exception: " + e.getMessage());
			throw e;
		}
	}

	// Delete a LoginUserRole object. Return the # of record deleted
	@Override
	public int deleteLoginUserRole(int ownerAccountId, int userId, int roleId)
			throws Exception {
		if (ownerAccountId < 0 || userId <= 0 || roleId <= 0)
			return 0;
		try {
			int numRecDeleted = namedParameterJdbcTemplate.update(
					"delete from LoginUserRoles where LoginUserId=:LoginUserId and RoleId=:RoleId and OwnerAccountId=:OwnerAccountId", 
					new MapSqlParameterSource().addValue("LoginUserId", userId).
						addValue("RoleId", roleId).addValue("OwnerAccountId", ownerAccountId));
			return numRecDeleted;
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
