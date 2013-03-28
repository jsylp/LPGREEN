package org.lpgreen.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.lpgreen.domain.LoginUserRole;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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
public class JdbcLoginUserRoleDao implements LoginUserRoleDao {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertLoginUserRole;
	public void setDataSource(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		insertLoginUserRole = new SimpleJdbcInsert(dataSource).withTableName("LoginUserRoles");
	}

	// o: the main object: this LoginUserRole; 
	protected final static String fieldSelectionForReadLoginUserRole =
			"o.LoginUserId,o.RoleId,o.OwnerAccountId,r.RoleName";

	protected final static String fieldSetForUpdateLoginUserRole = 
			"LoginUserId=:LoginUserId,RoleId=:RoleId,OwnerAccountId=:OwnerAccountId";

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// LoginUserRole related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

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

	// query LoginUserRole using OwnerAccountId and LoginUserId
	protected final static String strLoginUserRoleQueryWithUserId = "select " + fieldSelectionForReadLoginUserRole +
			" from LoginUserRoles as o" +
			" LEFT OUTER JOIN Role as r ON o.RoleId=r.Id" +
			" where o.OwnerAccountId=:OwnerAccountId and LoginUserId=:LoginUserId";

	// get all LoginUserRoles owned by a specific account id
	@Override
	public List<LoginUserRole> findAllSiteLoginUserRoles(int ownerAccountId, int userId) {

		try {
			List<LoginUserRole> userRoles = namedParameterJdbcTemplate.query(
					strLoginUserRoleQueryWithUserId,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("LoginUserId", userId),
					new LoginUserRoleMapper());
			return userRoles;
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

	/**
	 * Set SQL Parameters used for creating LoginUserRole
	 * @param userRole
	 * @param bNew
	 * @return
	 */
	private MapSqlParameterSource getLoginUserRoleMapSqlParameterSource(LoginUserRole userRole) {
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

	// Add a LoginUserRole. Return the number of records added
	@Override
	public int addLoginUserRole(LoginUserRole userRole) 
			throws DuplicateKeyException, Exception {
		if (userRole == null)
			throw new Exception("Missing input userRole");

		MapSqlParameterSource parameters = this.getLoginUserRoleMapSqlParameterSource(userRole);	
		try {
			// insert LoginUserRole record
			int retRows = insertLoginUserRole.execute(parameters);
			return retRows;
		}
		catch (DuplicateKeyException e1) {
			System.out.println("JdbcLoginUserRoleDao.addLoginUserRole DuplicateKeyException: " + e1.getMessage());
			throw e1;
		}
		catch (Exception e2) {
			System.out.println("JdbcLoginUserRoleDao.addLoginUserRole Exception: " + e2.getMessage());
			throw e2;
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
