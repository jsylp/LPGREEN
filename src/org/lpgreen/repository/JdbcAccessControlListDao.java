package org.lpgreen.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.lpgreen.domain.AccessControlList;
import org.lpgreen.domain.LoginUserRoles;
import org.lpgreen.domain.OperationRight;
import org.lpgreen.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 * JdbcAccessControlListDao is the JDBC implementation of the AccessControlListDao for AccessControlList related entity's persistence layer
 * 
 * Creation date: Feb. 16, 2013
 * Last modify date: Feb. 16, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

@Repository
public class JdbcAccessControlListDao implements AccessControlListDao {

	private LoginUserRolesDao userRoleDao;
	@Autowired
	public void setLoginUserRolesDao(LoginUserRolesDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	private OperationRightDao opRightDao;
	@Autowired
	public void setOperationRightDao(OperationRightDao opRightDao) {
		this.opRightDao = opRightDao;
	}

	private RoleAndHierarchyDao roleAndHiera;
	@Autowired
	public void setRoleAndHierarchyDao(RoleAndHierarchyDao roleAndHiera) {
		this.roleAndHiera = roleAndHiera;
	}

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertAccessControlList;
	public void setDataSource(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		insertAccessControlList = new SimpleJdbcInsert(dataSource).withTableName("AccessControlList");
	}

	// o: the main object: this AccessControlList; 
	protected final static String fieldSelectionForReadAccessControlList =
			"o.RoleId,o.ObjectName,o.OperationRightId,o.OwnerAccountId";

	protected final static String fieldSetForUpdateAccessControlList =
			"RoleId=:RoleId,ObjectName=:ObjectName,OperationRightId=:OperationRightId,OwnerAccountId=:OwnerAccountId";

	// query AccessControlList using OwnerAccountId
	protected final static String strAccessControlListQueryWithOwnerAccountId = "select " + fieldSelectionForReadAccessControlList +
			" from AccessControlList as o where OwnerAccountId=:OwnerAccountId";

	// query AccessControlList using RoleId
	protected final static String strAccessControlListQueryWithRoleId = "select " + fieldSelectionForReadAccessControlList +
			" from AccessControlList as o where OwnerAccountId=:OwnerAccountId and RoleId=:RoleId";

	// query AccessControlList using OperationRightId
	protected final static String strAccessControlListQueryWithOperationRightId = "select " + fieldSelectionForReadAccessControlList +
			" from AccessControlList as o where OwnerAccountId=:OwnerAccountId and OperationRightId=:OperationRightId";

	// query AccessControlList using ObjectName
	protected final static String strAccessControlListQueryWithObjectName = "select " + fieldSelectionForReadAccessControlList +
			" from AccessControlList as o where OwnerAccountId=:OwnerAccountId and ObjectName=:ObjectName";

	// query AccessControlList using RoleId, ObjectName and OperationRightId
	protected final static String strAccessControlListQueryWithAll = "select " + fieldSelectionForReadAccessControlList +
			" from AccessControlList as o where OwnerAccountId=:OwnerAccountId and RoleId=:RoleId and" +
			" ObjectName=:ObjectName and OperationRightId=:OperationRightId";

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// AccessControlList related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private static class AccessControlListMapper implements RowMapper<AccessControlList> {

		public AccessControlList mapRow(ResultSet rs, int rowNum) throws SQLException {
			AccessControlList acList = new AccessControlList();
			acList.setRoleId(rs.getInt("RoleId"));
			acList.setObjectName(rs.getString("ObjectName"));
			acList.setOperationRightId(rs.getInt("OperationRightId"));
			acList.setOwnerAccountId(rs.getInt("OwnerAccountId"));
			return acList;
		}
	}

	// get all AccessControlLists owned by a specific account id
	@Override
	public List<AccessControlList> findAllSiteAccessControlLists(int ownerAccountId) {
		try {
			List<AccessControlList> acLists = namedParameterJdbcTemplate.query(
					strAccessControlListQueryWithOwnerAccountId,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId),
					new AccessControlListMapper());
			return acLists;
		}
		catch (Exception e) {
			System.out.println("JdbcAccessControlListDao.findAllSiteAccessControlLists Exception: " + e.getMessage());
			return null;
		}
	}

	// get a specific AccessControlList by a given roleId
	@Override
	public AccessControlList findAccessControlListByRoleId(int ownerAccountId, int roleId) {
		try {
			AccessControlList acList = namedParameterJdbcTemplate.queryForObject(
					strAccessControlListQueryWithRoleId,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("RoleId", roleId),
					new AccessControlListMapper());
			return acList;
		}
		catch (Exception e) {
			System.out.println("JdbcAccessControlListDao.findAccessControlListByRoleId Exception: " + e.getMessage());
			return null;
		}
	}

	// get a specific AccessControlList by a given opRightId
	public AccessControlList findAccessControlListByOperationRightId(int ownerAccountId, int opRightId) {
		try {
			AccessControlList acList = namedParameterJdbcTemplate.queryForObject(
					strAccessControlListQueryWithOperationRightId,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("OperationRightId", opRightId),
					new AccessControlListMapper());
			return acList;
		}
		catch (Exception e) {
			System.out.println("JdbcAccessControlListDao.findAccessControlListByOperationRightId Exception: " + e.getMessage());
			return null;
		}
	}

	// get a specific AccessControlList by a given name
	@Override
	public AccessControlList findAccessControlListByName(int ownerAccountId, String name) {
		try {
			AccessControlList acList = namedParameterJdbcTemplate.queryForObject(
					strAccessControlListQueryWithObjectName,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("ObjectName", name),
					new AccessControlListMapper());
			return acList;
		} 
		catch (Exception e) {
			System.out.println("JdbcAccessControlListDao.findAccessControlListByName Exception: " + e.getMessage());
			return null;
		}
	}

	// get a specific AccessControlList by a given roleId, name and opRightId
	@Override
	public AccessControlList findAccessControlListByAll(int ownerAccountId, int roleId, String name, int opRightId) {
		try {
			AccessControlList acList = namedParameterJdbcTemplate.queryForObject(
					strAccessControlListQueryWithAll,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("RoleId", roleId).
						addValue("ObjectName", name).addValue("OperationRightId", opRightId),
					new AccessControlListMapper());
			return acList;
		} 
		catch (Exception e) {
			System.out.println("JdbcAccessControlListDao.findAccessControlListByAll Exception: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Set SQL Parameters used for creating AccessControlList
	 * @param acList
	 * @param bNew
	 * @return
	 */
	private MapSqlParameterSource getAccessControlListMapSqlParameterSource(AccessControlList acList) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("RoleId", acList.getRoleId());
		parameters.addValue("ObjectName", acList.getObjectName());
		parameters.addValue("OperationRightId", acList.getOperationRightId());
		parameters.addValue("OwnerAccountId", acList.getOwnerAccountId());
		return parameters;
	}

	// Add a AccessControlList. Return the generated id
	@Override
	public int addAccessControlList(AccessControlList acList) 
			throws DuplicateKeyException, Exception {
		if (acList == null)
			throw new Exception("Missing input acList");
		
		MapSqlParameterSource parameters = this.getAccessControlListMapSqlParameterSource(acList);	
		try {
			// insert AccessControlList record
			int retRows = insertAccessControlList.execute(parameters);
			return retRows;
		}
		catch (DuplicateKeyException e1) {
			System.out.println("JdbcAccessControlListDao.addAccessControlList Exception: " + e1.getMessage());
			throw e1;
		}
		catch (Exception e2) {
			System.out.println("JdbcAccessControlListDao.addAccessControlList Exception: " + e2.getMessage());
			throw e2;
		}
	}

	// Save a the changes of an existing AccessControlList object. Return the # of record updated
	@Override
	public int saveAccessControlList(AccessControlList acList)
			throws DuplicateKeyException, Exception {
		if (acList == null)
			throw new Exception("Missing input acList");
		try {
			int numRecUpdated = namedParameterJdbcTemplate.update(
					"update AccessControlList set " + fieldSetForUpdateAccessControlList + " where RoleId=:RoleId;",
					getAccessControlListMapSqlParameterSource(acList));
			return numRecUpdated;
		}
		catch (DuplicateKeyException e1) {
			System.out.println("JdbcAccessControlListDao.saveAccessControlList Exception: " + e1.getMessage());
			throw e1;
		}
		catch (Exception e2) {
			System.out.println("JdbcAccessControlListDao.saveAccessControlList Exception: " + e2.getMessage());
			throw e2;
		}
	}

	// Delete a AccessControlList object. Return the # of record deleted
	@Override
	public int deleteAccessControlList(int ownerAccountId, int roleId)
			throws Exception {
		if (ownerAccountId < 0 || roleId <= 0)
			return 0;
		try {
			int numRecDeleted = namedParameterJdbcTemplate.update(
					"delete from AccessControlList where RoleId=:RoleId and OwnerAccountId=:OwnerAccountId", 
					new MapSqlParameterSource().addValue("RoleId", roleId).addValue("OwnerAccountId", ownerAccountId));
			return numRecDeleted;
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	// Get the user's roles and included roles to check for permission
	// Since there is no LoginUser domain object here, use LoginUserId instead
	public boolean hasPermission(int userId, String objectName, String operation, int ownerAccountId)
			throws Exception {
		try {
			OperationRight opRight = opRightDao.findOperationRightByName(ownerAccountId, operation);
			if (opRight == null)
				return false;
			List<LoginUserRoles> userRoles = userRoleDao.findAllSiteLoginUserRoles(ownerAccountId, userId);
			if (userRoles == null || userRoles.size() == 0)
				return false;

			boolean permit = false;
			for (LoginUserRoles userRole : userRoles) {
				Role role = roleAndHiera.findRoleById(ownerAccountId, userRole.getRoleId());
				if (role == null)
					continue;

				List<Role> roles = roleAndHiera.getAllIncludedRoles(role);
				if (roles == null || roles.size() == 0)
					continue;

				for (Role r : roles) {
					AccessControlList acList = findAccessControlListByAll(ownerAccountId, r.getId(),
						objectName,	opRight.getId());
					if (acList != null) {
						permit = true;
						break;
					}
				}
			}
			return permit;
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
