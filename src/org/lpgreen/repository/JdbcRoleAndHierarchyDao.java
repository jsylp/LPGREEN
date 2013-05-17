package org.lpgreen.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.lpgreen.domain.Role;
import org.lpgreen.domain.RoleHierarchy;
import org.lpgreen.util.MustOverrideException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 * JdbcRoleAndHierarchyDao is the JDBC implementation of the RoleAndHierarchyDao for Role and RoleHierarchy related entity's persistence layer
 * 
 * Creation date: Feb. 11, 2013
 * Last modify date: Feb. 11, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

@Repository
public class JdbcRoleAndHierarchyDao extends LPJdbcGeneric<Role> implements RoleAndHierarchyDao {

	private SimpleJdbcInsert insertRoleHiera;
	public void setDataSource(DataSource dataSource)
			throws MustOverrideException {
		try {
			super.setDataSource(dataSource);
			insertRoleHiera = new SimpleJdbcInsert(dataSource).withTableName("RoleHierarchy");
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcRoleAndHierarchyDao.setDataSource Exception: " + e.getMessage());
			throw e;
		}
	}

	// o: the main object: this Role
	protected final static String fieldSelectionForReadRole =
			"o.Id,o.RoleName,o.Description,o.OwnerAccountId";

	protected final static String fieldSetForUpdateRole = 
			"RoleName=:RoleName,Description=:Description,OwnerAccountId=:OwnerAccountId";

	// o: the main object: this RoleHierarchy
	protected final static String fieldSelectionForReadRoleHierarchy =
			"o.RoleId,r1.RoleName as RoleName,o.IncludedRoleId,r2.RoleName as IncludedRoleName,o.OwnerAccountId";

	protected final static String fieldSetForUpdateRoleHierarchy = 
			"RoleId=:RoleId,IncludedRoleId=:IncludedRoleId,OwnerAccountId=:OwnerAccountId";

	// RowMapper class for Role
	private static class RoleMapper implements RowMapper<Role> {

		public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
			Role role = new Role();
			role.setId(rs.getInt("Id"));
			role.setRoleName(rs.getString("RoleName"));
			role.setDescription(rs.getString("Description"));
			role.setOwnerAccountId(rs.getInt("OwnerAccountId"));
			return role;
		}
	}

	// RowMapper class for RoleHierarchy with RoleName and IncludedRoleName 
	private static class RoleHierarchyNoRoleNamesMapper implements RowMapper<RoleHierarchy> {

		public RoleHierarchy mapRow(ResultSet rs, int rowNum) throws SQLException {
			RoleHierarchy roleHierarchy = new RoleHierarchy();
			roleHierarchy.setRoleId(rs.getInt("RoleId"));
			roleHierarchy.setIncludedRoleId(rs.getInt("IncludedRoleId"));
			roleHierarchy.setOwnerAccountId(rs.getInt("OwnerAccountId"));
			return roleHierarchy;
		}
	}

	// RowMapper class for RoleHierarchy
	private static class RoleHierarchyMapper implements RowMapper<RoleHierarchy> {

		public RoleHierarchy mapRow(ResultSet rs, int rowNum) throws SQLException {
			RoleHierarchy roleHierarchy = new RoleHierarchy();
			roleHierarchy.setRoleId(rs.getInt("RoleId"));
			String roleName = rs.getString("RoleName");
			if (roleName != null && !roleName.isEmpty())
				roleHierarchy.setRoleName(roleName);
			else 
				roleHierarchy.setRoleName(null);
			roleHierarchy.setIncludedRoleId(rs.getInt("IncludedRoleId"));
			String includedRoleName = rs.getString("IncludedRoleName");
			if (includedRoleName != null && !includedRoleName.isEmpty())
				roleHierarchy.setIncludedRoleName(includedRoleName);
			else
				roleHierarchy.setIncludedRoleName(null);
			roleHierarchy.setOwnerAccountId(rs.getInt("OwnerAccountId"));
			return roleHierarchy;
		}
	}

	// Override to return the SQL table name
	protected String getSqlTable() {
		return "Role";
	}

	// Override to return the field selection for read
	protected String getFieldSelectionForRead() {
		return fieldSelectionForReadRole;
	}

	// Override to return the filed selection for update
	protected String getFieldSelectionForUpdate() {
		return fieldSetForUpdateRole;
	}

	// Override to return the field order for read a list of objects
	protected String getFieldOrderForReadList() {
		return "o.OwnerAccountId, o.RoleName ASC";
	}

	// Override to return the RowMapper
	protected RowMapper<Role> getRowMapper() {
		return new RoleMapper();
	}

	// Override to return MapSqlParameterSource for creating Role
	protected MapSqlParameterSource getDomainObjectMapSqlParameterSource(Role role, boolean bNew) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		// Persist data defined in UserObject
		parameters.addValue("OwnerAccountId", role.getOwnerAccountId());
		if (!bNew) {
			if (role.getId() > 0)
				parameters.addValue("Id", role.getId());	// auto generated when insert a Role, use it as the primary key when update it
			else
				parameters.addValue("Id", null);
		}
		parameters.addValue("RoleName", role.getRoleName());
		if (role.getDescription() != null && !role.getDescription().isEmpty())
			parameters.addValue("Description", role.getDescription());
		else
			parameters.addValue("Description", null);
		return parameters;
	}

	// get hierarchical included Roles by a given role
	private List<Role> getAllIncludedRoles(Role role, List<Role> rolesIncluded)
			throws Exception {
		if (role == null)
			throw new Exception("Missing input role");
		try {
			int ownerAccountId = role.getOwnerAccountId(); 
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(" o.RoleId,o.IncludedRoleId,o.OwnerAccountId");
			sbQuery.append(" from RoleHierarchy as o");
			sbQuery.append(" where o.OwnerAccountId=:OwnerAccountId and o.RoleId=:RoleId");
			sbQuery.append(" order by o.RoleId, o.IncludedRoleId ASC;");
			List<RoleHierarchy> roleHierarchys = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("RoleId", role.getId()),
					new RoleHierarchyNoRoleNamesMapper());

			for (RoleHierarchy roleHiera : roleHierarchys) {
				if (roleHiera.getIncludedRoleId() <= 0) {
					throw new Exception("IncludedRole has negative Id");
				}
				Role roleInc = findRoleById(ownerAccountId, roleHiera.getIncludedRoleId());

				// check if the incRole has been added already
				// note: if performance is a concern, we can create an ArrayList
				// of just RoleId and use it to check if the role was added already.
				if (roleInc != null && !rolesIncluded.contains(roleInc)) {
					rolesIncluded.add(roleInc);

					// recurse into the included role's included roles
					getAllIncludedRoles(roleInc, rolesIncluded);
				}
			}
			return rolesIncluded;
		}
		catch (Exception e) {
			System.out.println("JdbcRoleAndHierarchyDao.findAllIncludedRoles Exception: " + e.getMessage());
			return null;
		}
	}

	// get hierarchical included Roles by a given role
	@Override
	public List<Role> getAllIncludedRoles(Role role)
			throws Exception {
		try {
			List<Role> rolesIncluded = new ArrayList<Role>();
			return getAllIncludedRoles(role, rolesIncluded);
		}
		catch (Exception e) {
			System.out.println("JdbcRoleAndHierarchyDao.findAllIncludedRoles Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Roles owned by a specific account id
	@Override
	public List<Role> findRolesByOwnerAccountId(int ownerAccountId) {
		if (ownerAccountId <= 0)
			return null;
		try {
			List<Role> roles = super.findDomainObjectsByOwnerAccountId(ownerAccountId, null,
					null, null);
			return roles;
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcRoleAndHierarchyDao.findRolesByOwnerAccountId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcRoleAndHierarchyDao.findAllOwnerAccountRoles Exception: " + e.getMessage());
			return null;
		}
	}

	// get a specific Role by a given id
	@Override
	public Role findRoleById(int ownerAccountId, int id) {
		try {
			Role role = super.findDomainObjectById(id, null);
			return role;
		} 
		catch (MustOverrideException e) {
			System.out.println("JdbcRoleAndHierarchyDao.findRoleById MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcRoleAndHierarchyDao.findRoleById Exception: " + e.getMessage());
			return null;
		}
	}

	// get a specific Role by a given name
	@Override
	public List<Role> findRoleByName(int ownerAccountId, String roleName) {
		try {
			List<Role> roles = super.findDomainObjectsByStringColumnVal(ownerAccountId, null,
					"o.RoleName", roleName, true, null, null);
			return roles;
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcRoleAndHierarchyDao.findRoleByName MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcRoleAndHierarchyDao.findRoleByName Exception: " + e.getMessage());
			return null;
		}
	}

	// Add a Role. Return the generated database id
	@Override
	public int addRole(Role role)
			throws DuplicateKeyException, Exception {
		if (role == null)
			throw new Exception("Missing input role");
		try {
			// insert Role record
			int retId = addDomainObject(role);
			role.setId(retId);
			return retId;
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcRoleAndHierarchyDao.addRole MustOverrideException: " + e.getMessage());
			return -1;
		}
		catch (DuplicateKeyException e) {
			System.out.println("JdbcRoleAndHierarchyDao.addRole DuplicateKeyException: " + e.getMessage());
			throw e;
		}
		catch (Exception e) {
			System.out.println("JdbcRoleAndHierarchyDao.addRole Exception: " + e.getMessage());
			throw e;
		}
	}

	// Save the changes of an existing Role object. Return the # of record updated
	@Override
	public int saveRole(Role role) 
			throws DuplicateKeyException, Exception {
		try {
			int numObjectUpdated = saveDomainObject(role);
			if (numObjectUpdated == 0) {
				throw new Exception("Fail to update the role obejct");
			}
			return numObjectUpdated;
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcRoleAndHierarchyDao.saveRole MustOverrideException: " + e.getMessage());
			return -1;
		}
		catch (DuplicateKeyException e) {
			System.out.println("JdbcRoleAndHierarchyDao.saveRole DuplicateKeyException: " + e.getMessage());
			throw e;
		}
		catch (Exception e) {
			System.out.println("JdbcRoleAndHierarchyDao.saveRole Exception: " + e.getMessage());
			throw e;
		}
	}

	// Delete a Role object. Return the # of record deleted
	@Override
	public int deleteRole(int ownerAccountId, int id)	
			throws Exception {
		if (ownerAccountId < 0 || id <= 0)
			return 0;
		try {
			return deleteDomainObject(ownerAccountId, id);
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// RoleHierarchy related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	// get a specific RoleHierarchy by a given Role and included Role
	@Override
	public RoleHierarchy findRoleHierarchy(int ownerAccountId, Role role, Role roleInc) 
			throws Exception {
		if (role == null || roleInc == null)
			throw new Exception("Missing input role or roleInc");
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(fieldSelectionForReadRoleHierarchy);
			sbQuery.append(" from RoleHierarchy as o");
			sbQuery.append(" LEFT OUTER JOIN Role as r1 ON o.RoleId=r1.Id");
			sbQuery.append(" LEFT OUTER JOIN Role as r2 ON o.IncludedRoleId=r2.Id");
			sbQuery.append(" where o.OwnerAccountId=:OwnerAccountId");
			sbQuery.append(" and o.RoleId=:RoleId and o.IncludedRoleId=:IncludedRoleId");
			sbQuery.append(" order by o.RoleId, o.IncludedRoleId ASC;");
			RoleHierarchy roleHiera = namedParameterJdbcTemplate.queryForObject(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).
							addValue("RoleId", role.getId()).addValue("IncludedRoleId", roleInc.getId()),
					new RoleHierarchyMapper());
			return roleHiera;
		}
		catch (Exception e) {
			System.out.println("JdbcRoleAndHierarchyDao.findRoleHierarchy Exception: " + e.getMessage());
			return null;
		}
	}

	// get a specific RoleHierarchy by a given RoleId and include RoleId
	@Override
	public RoleHierarchy findRoleHierarchyByRoleIds(int ownerAccountId, int roleId, int roleIdInc)
			throws Exception {
		if (roleId <= 0 || roleIdInc <= 0)
			throw new Exception("Invalid roleId or includeRoleId");
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(fieldSelectionForReadRoleHierarchy);
			sbQuery.append(" from RoleHierarchy as o");
			sbQuery.append(" LEFT OUTER JOIN Role as r1 ON o.RoleId=r1.Id");
			sbQuery.append(" LEFT OUTER JOIN Role as r2 ON o.IncludedRoleId=r2.Id");
			sbQuery.append(" where o.OwnerAccountId=:OwnerAccountId");
			sbQuery.append(" and o.RoleId=:RoleId and o.IncludedRoleId=:IncludedRoleId");
			sbQuery.append(" order by o.RoleId, o.IncludedRoleId ASC;");
			RoleHierarchy roleHiera = namedParameterJdbcTemplate.queryForObject(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).
							addValue("RoleId", roleId).addValue("IncludedRoleId", roleIdInc),
					new RoleHierarchyMapper());
			return roleHiera;
		} 
		catch (Exception e) {
			System.out.println("JdbcRoleAndHierarchyDao.findRoleHierarchy Exception: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Set SQL Parameters used for creating RoleHierarchy
	 * @param roleHiera
	 * @param bNew
	 * @return
	 */
	private MapSqlParameterSource getRoleHierarchyMapSqlParameterSource(RoleHierarchy roleHiera) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		// Persist data defined in UserObject
		parameters.addValue("OwnerAccountId", roleHiera.getOwnerAccountId());
		if (roleHiera.getRoleId() > 0)
			parameters.addValue("RoleId", roleHiera.getRoleId());
		else
			parameters.addValue("RoleId", null);
		if (roleHiera.getIncludedRoleId() > 0)
			parameters.addValue("IncludedRoleId", roleHiera.getIncludedRoleId());
		else
			parameters.addValue("IncludedRoleId", null);
		return parameters;
	}

	// Add a RoleHierarchy.
	@Override
	public int addRoleHierarchy(RoleHierarchy roleHiera) 
			throws DuplicateKeyException, Exception {
		if (roleHiera == null)
			throw new Exception("Missing input roleHierarchy");

		MapSqlParameterSource parameters = this.getRoleHierarchyMapSqlParameterSource(roleHiera);	
		try {
			// insert RoleHierarchy record
			int retRows = insertRoleHiera.execute(parameters);
			return retRows;
		}
		catch (DuplicateKeyException e) {
			System.out.println("JdbcRoleAndHierarchyDao.addRoleHierarchy DuplicateKeyException: " + e.getMessage());
			throw e;
		}
		catch (Exception e) {
			System.out.println("JdbcRoleAndHierarchyDao.addRoleHierarchy Exception: " + e.getMessage());
			throw e;
		}
	}

	// Delete a RoleHierarchy object. Return the # of record deleted
	@Override
	public int deleteRoleHierarchy(int ownerAccountId, int roleId, int roleIdInc)
			throws Exception {
		if (ownerAccountId < 0 || roleId <= 0 || roleIdInc <= 0)
			return 0;
		try {
			int numRecDeleted = namedParameterJdbcTemplate.update(
					"delete from RoleHierarchy where RoleId=:RoleId and IncludedRoleId=:IncludedRoleId and OwnerAccountId=:OwnerAccountId", 
					new MapSqlParameterSource().addValue("RoleId", roleId).
							addValue("IncludedRoleId", roleIdInc).addValue("OwnerAccountId", ownerAccountId));
			return numRecDeleted;
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
