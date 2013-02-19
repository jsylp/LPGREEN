package org.lpgreen.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.lpgreen.domain.LoginUserRoles;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 * JdbcLoginUserRolesDao is the JDBC implementation of the LoginUserRolesDao for LoginUserRoles related entity's persistence layer
 * 
 * Creation date: Feb. 16, 2013
 * Last modify date: Feb. 16, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

@Repository
public class JdbcLoginUserRolesDao implements LoginUserRolesDao {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertLoginUserRoles;
	public void setDataSource(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		insertLoginUserRoles = new SimpleJdbcInsert(dataSource).withTableName("LoginUserRoles");
	}
	
	// o: the main object: this LoginUserRoles; 
	protected final static String fieldSelectionForReadLoginUserRoles =
			"o.LoginUserId,o.RoleId,o.OwnerAccountId";

	protected final static String fieldSetForUpdateLoginUserRoles = 
			"LoginUserId=:LoginUserId,RoleId=:RoleId,OwnerAccountId=:OwnerAccountId";

	// query LoginUserRoles using OwnerAccountId and LoginUserId
	protected final static String strLoginUserRolesQueryWithUserId = "select " + fieldSelectionForReadLoginUserRoles +
			" from LoginUserRoles as o where OwnerAccountId=:OwnerAccountId";

	// query LoginUserRoles using LoginUseId and RoleId
	protected final static String strLoginUserRolesQueryWithUserIdAndRoleId = "select " + fieldSelectionForReadLoginUserRoles +
			" from LoginUserRoles as o where OwnerAccountId=:OwnerAccountId and LoginUserId=:LoginUserId and RoleId=:RoleId";

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// LoginUserRoles related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private static class LoginUserRolesMapper implements RowMapper<LoginUserRoles> {

		public LoginUserRoles mapRow(ResultSet rs, int rowNum) throws SQLException {
			LoginUserRoles userRole = new LoginUserRoles();
			userRole.setLoginUserId(rs.getInt("LoginUserId"));
			userRole.setRoleId(rs.getInt("RoleId"));
			userRole.setOwnerAccountId(rs.getInt("OwnerAccountId"));
			return userRole;
		}
	}

	// get all LoginUserRoles owned by a specific account id
	@Override
	public List<LoginUserRoles> findAllSiteLoginUserRoles(int ownerAccountId, int userId) {
		try {
			List<LoginUserRoles> userRoles = namedParameterJdbcTemplate.query(
					strLoginUserRolesQueryWithUserId,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("LoginUserId", userId),
					new LoginUserRolesMapper());
			return userRoles;
		}
		catch (Exception e) {
			System.out.println("JdbcLoginUserRolesDao.findAllSiteLoginUserRoless Exception: " + e.getMessage());
			return null;
		}
	}

	// get a specific LoginUserRoles by given loginUserId and roleId
	@Override
	public LoginUserRoles findLoginUserRolesByUserIdAndRoleId(int ownerAccountId, int userId, int roleId) {
		try {
			LoginUserRoles userRole = namedParameterJdbcTemplate.queryForObject(
					strLoginUserRolesQueryWithUserIdAndRoleId,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).
							addValue("LoginUserId", userId).addValue("RoleId", roleId),
					new LoginUserRolesMapper());
			return userRole;
		} 
		catch (Exception e) {
			System.out.println("JdbcLoginUserRolesDao.findLoginUserRolesByUserIdAndRoleId Exception: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Set SQL Parameters used for creating LoginUserRoles
	 * @param userRole
	 * @param bNew
	 * @return
	 */
	private MapSqlParameterSource getLoginUserRolesMapSqlParameterSource(LoginUserRoles userRole) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("LoginUserId", userRole.getLoginUserId());
		parameters.addValue("RoleId", userRole.getRoleId());
		parameters.addValue("OwnerAccountId", userRole.getOwnerAccountId());
		return parameters;
	}

	// Add a LoginUserRoles. Return the number of records added
	@Override
	public int addLoginUserRoles(LoginUserRoles userRole) 
			throws DuplicateKeyException, Exception {
		if (userRole == null)
			throw new Exception("Missing input userRole");

		MapSqlParameterSource parameters = this.getLoginUserRolesMapSqlParameterSource(userRole);	
		try {
			// insert LoginUserRoles record
			int retRows = insertLoginUserRoles.execute(parameters);
			return retRows;
		}
		catch (DuplicateKeyException e1) {
			System.out.println("JdbcLoginUserRolesDao.addLoginUserRoles Exception: " + e1.getMessage());
			throw e1;
		}
		catch (Exception e2) {
			System.out.println("JdbcLoginUserRolesDao.addLoginUserRoles Exception: " + e2.getMessage());
			throw e2;
		}
	}

	// Delete a LoginUserRoles object. Return the # of record deleted
	@Override
	public int deleteLoginUserRoles(int ownerAccountId, int userId, int roleId)
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
