package org.lpgreen.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.lpgreen.domain.AccessControlList;
import org.lpgreen.domain.LoginUserRole;
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

	private LoginUserRoleDao loginUserRoleDao;
	@Autowired
	public void setLoginUserRoleDao(LoginUserRoleDao loginUserRoleDao) {
		this.loginUserRoleDao = loginUserRoleDao;
	}

	private OperationRightDao operationRightDao;
	@Autowired
	public void setOperationRightDao(OperationRightDao operationRightDao) {
		this.operationRightDao = operationRightDao;
	}

	private RoleAndHierarchyDao roleAndHierarchyDao;
	@Autowired
	public void setRoleAndHierarchyDao(RoleAndHierarchyDao roleAndHierarchyDao) {
		this.roleAndHierarchyDao = roleAndHierarchyDao;
	}

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertAccessControlList;
	public void setDataSource(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		insertAccessControlList = new SimpleJdbcInsert(dataSource).withTableName("AccessControlList");
	}

	// o: the main object: this AccessControlList; 
	protected final static String fieldSelectionForReadAccessControlList =
			"o.RoleId,o.ObjectName,o.OperationRightId,o.OwnerAccountId,r.RoleName,p.OperationName";

	protected final static String fieldSetForUpdateAccessControlList =
			"RoleId=:RoleId,ObjectName=:ObjectName,OperationRightId=:OperationRightId,OwnerAccountId=:OwnerAccountId";

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
			acList.setRoleName(rs.getString("RoleName"));
			acList.setOperationName(rs.getString("OperationName"));
			return acList;
		}
	}

	// query AccessControlList using OwnerAccountId
	protected final static String strAccessControlListQueryWithOwnerAccountId = "select " + fieldSelectionForReadAccessControlList +
			" from AccessControlList as o" +
			" LEFT OUTER JOIN Role as r ON o.RoleId=r.Id" +
			" LEFT OUTER JOIN OperationRight as p ON o.OperationRightId=p.Id" +
			" where o.OwnerAccountId=:OwnerAccountId";

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

	// query AccessControlList using RoleId
	protected final static String strAccessControlListQueryWithRoleId = "select " + fieldSelectionForReadAccessControlList +
			" from AccessControlList as o" +
			" LEFT OUTER JOIN Role as r ON o.RoleId=r.Id" +
			" LEFT OUTER JOIN OperationRight as p ON o.OperationRightId=p.Id" +
			" where o.OwnerAccountId=:OwnerAccountId and RoleId=:RoleId";

	// get a specific AccessControlList by a given roleId
	@Override
	public List<AccessControlList> findAccessControlListsByRoleId(int ownerAccountId, int roleId) {
		try {
			List<AccessControlList> acLists = namedParameterJdbcTemplate.query(
					strAccessControlListQueryWithRoleId,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("RoleId", roleId),
					new AccessControlListMapper());
			return acLists;
		}
		catch (Exception e) {
			System.out.println("JdbcAccessControlListDao.findAccessControlListByRoleId Exception: " + e.getMessage());
			return null;
		}
	}

	// query AccessControlList using OperationRightId
	protected final static String strAccessControlListQueryWithOperationRightId = "select " + fieldSelectionForReadAccessControlList +
			" from AccessControlList as o" +
			" LEFT OUTER JOIN Role as r ON o.RoleId=r.Id" +
			" LEFT OUTER JOIN OperationRight as p ON o.OperationRightId=p.Id" +
			" where o.OwnerAccountId=:OwnerAccountId and OperationRightId=:OperationRightId";

	// get a specific AccessControlList by a given opRightId
	public List<AccessControlList> findAccessControlListsByOperationRightId(int ownerAccountId, int opRightId) {
		try {
			List<AccessControlList> acLists = namedParameterJdbcTemplate.query(
					strAccessControlListQueryWithOperationRightId,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("OperationRightId", opRightId),
					new AccessControlListMapper());
			return acLists;
		}
		catch (Exception e) {
			System.out.println("JdbcAccessControlListDao.findAccessControlListByOperationRightId Exception: " + e.getMessage());
			return null;
		}
	}

	// query AccessControlList using ObjectName
	protected final static String strAccessControlListQueryWithObjectName = "select " + fieldSelectionForReadAccessControlList +
			" from AccessControlList as o" +
			" LEFT OUTER JOIN Role as r ON o.RoleId=r.Id" +
			" LEFT OUTER JOIN OperationRight as p ON o.OperationRightId=p.Id" +
			" where o.OwnerAccountId=:OwnerAccountId and ObjectName=:ObjectName";

	// get a specific AccessControlList by a given name
	@Override
	public List<AccessControlList> findAccessControlListsByObjectName(int ownerAccountId, String name) {
		try {
			List<AccessControlList> acLists = namedParameterJdbcTemplate.query(
					strAccessControlListQueryWithObjectName,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("ObjectName", name),
					new AccessControlListMapper());
			return acLists;
		} 
		catch (Exception e) {
			System.out.println("JdbcAccessControlListDao.findAccessControlListByName Exception: " + e.getMessage());
			return null;
		}
	}

	// query AccessControlList using RoleId, ObjectName and OperationRightId
	protected final static String strAccessControlListQueryWithAllColumns = "select " + fieldSelectionForReadAccessControlList +
			" from AccessControlList as o" +
			" LEFT OUTER JOIN Role as r ON o.RoleId=r.Id" +
			" LEFT OUTER JOIN OperationRight as p ON o.OperationRightId=p.Id" +
			" where o.OwnerAccountId=:OwnerAccountId and RoleId=:RoleId and" +
			" ObjectName=:ObjectName and OperationRightId=:OperationRightId";

	// get a specific AccessControlList by a given roleId, name and opRightId
	@Override
	public AccessControlList findAccessControlListByRoleIdObjNameOperationRight(int ownerAccountId, int roleId, String name, int opRightId) {
		try {
			AccessControlList acList = namedParameterJdbcTemplate.queryForObject(
					strAccessControlListQueryWithAllColumns,
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
		if (acList.getRoleId() > 0)
			parameters.addValue("RoleId", acList.getRoleId());
		else
			parameters.addValue("RoleId", null);
		parameters.addValue("ObjectName", acList.getObjectName());
		if (acList.getOperationRightId() > 0)
			parameters.addValue("OperationRightId", acList.getOperationRightId());
		else
			parameters.addValue("OperationRightId", null);
		if (acList.getOwnerAccountId() > 0)
			parameters.addValue("OwnerAccountId", acList.getOwnerAccountId());
		else
			parameters.addValue("OwnerAccountId", null);
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

	// Delete a AccessControlList object. Return the # of record deleted
	@Override
	public int deleteAccessControlList(AccessControlList acList)
			throws Exception {
		if (acList.getOwnerAccountId() < 0 || acList.getRoleId() <= 0 || acList.getOperationRightId() <= 0)
			return 0;
		try {
			int numRecDeleted = namedParameterJdbcTemplate.update(
					"delete from AccessControlList where RoleId=:RoleId and ObjectName=:ObjectName and " +
							"OperationRightId=:OperationRightId and OwnerAccountId=:OwnerAccountId", 
					new MapSqlParameterSource().addValue("RoleId", acList.getRoleId()).addValue("ObjectName", acList.getObjectName()).
							addValue("OperationRightId", acList.getOperationRightId()).addValue("OwnerAccountId", acList.getOwnerAccountId()));
			return numRecDeleted;
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	// query number of AccessControlLists using RoleId, ObjectName and OperationRightId
	protected final static String strAccessControlListQueryForInt = "select count(1) " +
			" from AccessControlList as o where OwnerAccountId=:OwnerAccountId and RoleId=:RoleId and" +
			" ObjectName=:ObjectName and OperationRightId=:OperationRightId";

	private int getNumberOfAccessControlLists(int ownerAccountId, int roleId, String name, int opRightId) {
		try {
			int numRecs = namedParameterJdbcTemplate.queryForInt(
					strAccessControlListQueryForInt,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("RoleId", roleId).
						addValue("ObjectName", name).addValue("OperationRightId", opRightId));
			return numRecs;
		}
		catch (Exception e) {
			System.out.println("JdbcAccessControlListDao.getNumberOfAccessControlLists Exception: " + e.getMessage());
			return -1;
		}
	}

	// Get the user's roles and included roles to check for permission
	// Since there is no LoginUser domain object here, use LoginUserId instead
	public boolean hasPermission(int userId, String objectName, String operation, int ownerAccountId)
			throws Exception {
		try {
			OperationRight opRight = operationRightDao.findOperationRightByName(ownerAccountId, operation);
			if (opRight == null)
				return false;
			List<LoginUserRole> userRoles = loginUserRoleDao.findAllSiteLoginUserRoles(ownerAccountId, userId);
			if (userRoles == null || userRoles.size() == 0)
				return false;

			boolean permit = false;
			for (LoginUserRole userRole : userRoles) {
				Role role = roleAndHierarchyDao.findRoleById(ownerAccountId, userRole.getRoleId());
				if (role == null)
					continue;

				List<Role> roles = roleAndHierarchyDao.getAllIncludedRoles(role);
				if (roles == null || roles.size() == 0)
					continue;

				for (Role r : roles) {
					int numRecs = getNumberOfAccessControlLists(ownerAccountId, r.getId(), objectName, opRight.getId());
					if (numRecs > 0) {
						permit = true;
						break;
					}
				}

				if (permit == true)
					break;
			}
			return permit;
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
