package org.lpgreen.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.lpgreen.domain.AccessControlList;
import org.lpgreen.domain.LoginUserRole;
import org.lpgreen.domain.OperationRight;
import org.lpgreen.domain.Role;
import org.lpgreen.util.MustOverrideException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
public class JdbcAccessControlListDao extends LPJdbcGeneric<AccessControlList> implements AccessControlListDao {

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

	public void setDataSource(DataSource dataSource)
			throws MustOverrideException {
		try {
			super.setDataSource(dataSource);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcAccessControlListDao.setDataSource Exception: " + e.getMessage());
			throw e;
		}
	}

	// o: the main object: this AccessControlList;
	protected final static String fieldSelectionForReadAccessControlList =
			"o.RoleId,o.ObjectName,o.OperationRightId,o.OwnerAccountId,r.RoleName,p.OperationName";

	// field selection for update
	protected final static String fieldSetForUpdateAccessControlList =
			"RoleId=:RoleId,ObjectName=:ObjectName,OperationRightId=:OperationRightId,OwnerAccountId=:OwnerAccountId";

	// RowMapper class
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

	// Override to return the SQL table name
	protected String getSqlTable() {
		return "AccessControlList";
	}

	// Override to return the field selection for read
	protected String getFieldSelectionForRead() {
		return fieldSelectionForReadAccessControlList;
	}

	// Override to return the filed selection for update
	protected String getFieldSelectionForUpdate() {
		return fieldSetForUpdateAccessControlList;
	}

	// Override to return the RowMapper
	protected RowMapper<AccessControlList> getRowMapper() {
		return new AccessControlListMapper();
	}

	// Override to return MapSqlParameterSource for creating AccessControlList
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

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// AccessControlList related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	// get all AccessControlList owned by a specific account id
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

	// get a specific AccessControlList by a given roleId
	@Override
	public List<AccessControlList> findAccessControlListsByRoleId(int ownerAccountId, int roleId) {
		try {
			String outJoins = "LEFT OUTER JOIN Role as r ON o.RoleId=r.Id LEFT OUTER JOIN OperationRight as p ON o.OperationRightId=p.Id";
			return findDomainObjectsByColumnVal(ownerAccountId, outJoins, "RoleId", roleId, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcAccessControlListDao.findAccessControlListsByRoleId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcAccessControlListDao.findAccessControlListsByRoleId Exception: " + e.getMessage());
			return null;
		}
	}

	// get a specific AccessControlList by a given opRightId
	public List<AccessControlList> findAccessControlListsByOperationRightId(int ownerAccountId, int opRightId) {
		try {
			String outJoins = "LEFT OUTER JOIN Role as r ON o.RoleId=r.Id LEFT OUTER JOIN OperationRight as p ON o.OperationRightId=p.Id";
			return findDomainObjectsByColumnVal(ownerAccountId, outJoins, "OperationRightId", opRightId, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcAccessControlListDao.findAccessControlListsByOperationRightId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcAccessControlListDao.findAccessControlListsByOperationRightId Exception: " + e.getMessage());
			return null;
		}
	}

	// get a specific AccessControlList by a given name
	@Override
	public List<AccessControlList> findAccessControlListsByObjectName(int ownerAccountId, String name) {
		try {
			String outJoins = "LEFT OUTER JOIN Role as r ON o.RoleId=r.Id LEFT OUTER JOIN OperationRight as p ON o.OperationRightId=p.Id";
			return findDomainObjectsByColumnVal(ownerAccountId, outJoins, "ObjectName", name, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcAccessControlListDao.findAccessControlListsByObjectName MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcAccessControlListDao.findAccessControlListsByObjectName Exception: " + e.getMessage());
			return null;
		}
	}

	// query AccessControlList using RoleId, ObjectName and OperationRightId
	protected final static String strAccessControlListQueryWithAllColumns = "select " + fieldSelectionForReadAccessControlList +
			" from AccessControlList as o" +
			" LEFT OUTER JOIN Role as r ON o.RoleId=r.Id" +
			" LEFT OUTER JOIN OperationRight as p ON o.OperationRightId=p.Id" +
			" where o.OwnerAccountId=:OwnerAccountId and o.RoleId=:RoleId and" +
			" o.ObjectName=:ObjectName and o.OperationRightId=:OperationRightId";

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

	// Add a AccessControlList. Return the generated id
	@Override
	public int addAccessControlList(AccessControlList acList) 
			throws DuplicateKeyException, Exception {
		if (acList == null)
			throw new Exception("Missing input acList");
		
		MapSqlParameterSource parameters = this.getAccessControlListMapSqlParameterSource(acList);	
		try {
			// insert AccessControlList record
			int retRows = insertDomainObject.execute(parameters);
			return retRows;
		}
		catch (DuplicateKeyException e) {
			System.out.println("JdbcAccessControlListDao.addAccessControlList DuplicateKeyException: " + e.getMessage());
			throw e;
		}
		catch (Exception e) {
			System.out.println("JdbcAccessControlListDao.addAccessControlList Exception: " + e.getMessage());
			throw e;
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
			List<OperationRight> opRights = operationRightDao.findOperationRightByName(ownerAccountId, operation);
			if (opRights == null || opRights.isEmpty() || opRights.size() > 1)
				return false;
			OperationRight opRight = opRights.get(0);
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
