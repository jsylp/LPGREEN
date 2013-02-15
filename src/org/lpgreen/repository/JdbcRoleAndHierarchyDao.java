package org.lpgreen.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.lpgreen.domain.Role;
import org.lpgreen.domain.RoleHierarchy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
public class JdbcRoleAndHierarchyDao implements RoleAndHierarchyDao {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertRole;
	public void setDataSource(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		insertRole = new SimpleJdbcInsert(dataSource).withTableName("Role").usingGeneratedKeyColumns("id");
	}

	// o: the main object: this Role
	protected final static String fieldSelectionForReadRole =
			"o.Id,o.RoleName,o.Description,o.OwnerAccountId";

	protected final static String fieldSetForUpdateRole = 
			"RoleName=:RoleName,Description=:Description,OwnerAccountId=:OwnerAccountId";
	
	// o: the main object: this RoleHierarchy
	protected final static String fieldSelectionForReadRoleHierarchy =
			"o.RoleId,o.IncludedRoleId,o.OwnerAccountId";

	protected final static String fieldSetForUpdateRoleHierarchy = 
			"RoleIde=:RoleId,IncludedRoleId=:IncludedRoleId,OwnerAccountId=:OwnerAccountId";
	
	// query RoleHierarchy using RoleId
	protected final static String strHieraQueryWithRoleId = "select " + fieldSelectionForReadRoleHierarchy + 
			" from RoleHierarchy as o where OwnerAccountId=:OwnerAccountId and RoleId=:RoleId"; 

	// query RoleHierarchy using IncludedRoleId
	protected final static String strHieraQueryWithIncludedRoleId = "select " + fieldSelectionForReadRoleHierarchy + 
			" from RoleHierarchy as o where OwnerAccountId=:OwnerAccountId and IncludedRoleId=:IncludedRoleId"; 

	// query Role using Id
	protected final static String strRoleQueryWithId = "select " + fieldSelectionForReadRole +
			" from Role as o where OwnerAccountId=:OwnerAccountId and Id=:Id";

	// query Role using RoleName
	protected final static String strRoleQueryWithRoleName = "select " + fieldSelectionForReadRole +
			" from Role as o where OwnerAccountId=:OwnerAccountId and RoleName=:RoleName";

	// query Role using OwnerAccountId
	protected final static String strRoleQueryWithOwnerAccountId = "select " + fieldSelectionForReadRole +
			" from Role as o where OwnerAccountId=:OwnerAccountId";

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// Role Mapper
	///////////////////////////////////////////////////////////////////////////////////////////////////

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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	// RoleHierarchy Mapper
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private static class RoleHierarchyMapper implements RowMapper<RoleHierarchy> {

		public RoleHierarchy mapRow(ResultSet rs, int rowNum) throws SQLException {
			RoleHierarchy roleHierarchy = new RoleHierarchy();
			roleHierarchy.setRoleId(rs.getInt("RoleId"));
			roleHierarchy.setIncludedRoleId(rs.getInt("IncludedRoleId"));
			roleHierarchy.setOwnerAccountId(rs.getInt("OwnerAccountId"));
			return roleHierarchy;
		}
	}

	// get hierarchical included Roles by a given role
	private List<Role> getAllIncludedRoles(Role role, List<Role> rolesIncluded)
			throws Exception {
		try {
			if (role == null)
				throw new Exception("Missing input role");

			int ownerAccountId = role.getOwnerAccountId(); 
			List<RoleHierarchy> roleHierarchys = namedParameterJdbcTemplate.query(
					strHieraQueryWithRoleId,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("RoleId", role.getId()),
					new RoleHierarchyMapper());

			for (RoleHierarchy roleHiera : roleHierarchys) {
				Role roleInc = namedParameterJdbcTemplate.queryForObject(
						strRoleQueryWithId,
						new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("Id", roleHiera.getIncludedRoleId()),
						new RoleMapper());
				
				// check if the incRole has been added already
				// note: if performance is a concern, we can create an ArrayList
				// of just RoleId and use it to check if the role was added already.
				if (!rolesIncluded.contains(roleInc)) {
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
	public List<Role> findAllOwnerAccountRoles(int ownerAccountId) {
		try {
			List<Role> roles = namedParameterJdbcTemplate.query(
					strRoleQueryWithOwnerAccountId,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId),
					new RoleMapper());
			return roles;
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
			Role role = namedParameterJdbcTemplate.queryForObject(
					strRoleQueryWithId,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("Id", id),
					new RoleMapper());
			return role;
		} 
		catch (Exception e) {
			System.out.println("JdbcRoleAndHierarchyDao.findRoleById Exception: " + e.getMessage());
			return null;
		}
	}

	// get a specific Role by a given name
	@Override
	public Role findRoleByName(int ownerAccountId, String roleName) {
		try {
			Role role = namedParameterJdbcTemplate.queryForObject(
					strRoleQueryWithRoleName,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("RoleName", roleName),
					new RoleMapper());
			return role;
		}
		catch (Exception e) {
			System.out.println("JdbcRoleAndHierarchyDao.findRoleByName Exception: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Set SQL Parameters used for creating Role
	 * @param role
	 * @param bNew
	 * @return
	 */
	private MapSqlParameterSource getRoleMapSqlParameterSource(Role role, boolean bNew) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		if (!bNew)
			parameters.addValue("Id", role.getId());	// auto generated when insert a Role, use it as the primary key when update it
		parameters.addValue("RoleName", role.getRoleName());
		parameters.addValue("Description", role.getDescription());
		parameters.addValue("OwnerAccountId", role.getOwnerAccountId());
		return parameters;
	}

	// Add a Role. Return the generated id
	@Override
	public int addRole(Role role) 
			throws DuplicateKeyException, Exception {
		if (role == null)
			throw new Exception("Missing input role");

		MapSqlParameterSource parameters = this.getRoleMapSqlParameterSource(role, true);	
		try {
			// insert Role record
			int retId = insertRole.executeAndReturnKey(parameters).intValue();
			role.setId(retId);
			return retId;
		}
		catch (DuplicateKeyException e1) {
			System.out.println("JdbcRoleAndHierarchyDao.addRole Exception: " + e1.getMessage());
			throw e1;
		}
		catch (Exception e2) {
			System.out.println("JdbcRoleAndHierarchyDao.addRole Exception: " + e2.getMessage());
			throw e2;
		}
	}

	// Save the changes of an existing Role object. Return the # of record updated
	@Override
	public int saveRole(Role role) 
			throws DuplicateKeyException, Exception {
		if (role == null)
			throw new Exception("Missing input role");
		try {
			int numRecUpdated = namedParameterJdbcTemplate.update(
					"update Role set " + fieldSetForUpdateRole + " where Id=:Id;",
					getRoleMapSqlParameterSource(role, false));
			return numRecUpdated;
		}
		catch (DuplicateKeyException e1) {
			System.out.println("JdbcRoleAndHierarchyDao.saveRole Exception: " + e1.getMessage());
			throw e1;
		}
		catch (Exception e2) {
			System.out.println("JdbcRoleAndHierarchyDao.saveRole Exception: " + e2.getMessage());
			throw e2;
		}
	}

	// Delete a Role object. Return the # of record deleted
	@Override
	public int deleteRole(int ownerAccountId, int id)	
			throws Exception {
		if (ownerAccountId < 0 || id <= 0)
			return 0;
		try {
			int numRecDeleted = namedParameterJdbcTemplate.update(
					"delete from Role where Id=:Id and OwnerAccountId=:OwnerAccountId", 
					new MapSqlParameterSource().addValue("Id", id).addValue("OwnerAccountId", ownerAccountId));
			return numRecDeleted;
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	// get a specific RoleHierarchy by a given Role
	@Override
	public RoleHierarchy findRoleHierarchy(Role role) 
			throws Exception {
		if (role == null)
			throw new Exception("Missing input role");
		try {
			RoleHierarchy roleHiera = namedParameterJdbcTemplate.queryForObject(
					strHieraQueryWithRoleId,
					new MapSqlParameterSource().addValue("OwnerAccountId", role.getOwnerAccountId()).addValue("RoleId", role.getId()),
					new RoleHierarchyMapper());
			return roleHiera;
		} 
		catch (Exception e) {
			System.out.println("JdbcRoleAndHierarchyDao.findRoleHierarchy Exception: " + e.getMessage());
			return null;
		}
	}

	// get a specific RoleHierarchy by a given RoleId
	@Override
	public RoleHierarchy findRoleHierarchyByRoleId(int ownerAccountId, int roleId) {
		try {
			RoleHierarchy roleHiera = namedParameterJdbcTemplate.queryForObject(
					strHieraQueryWithRoleId,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("RoleId", roleId),
					new RoleHierarchyMapper());
			return roleHiera;
		} 
		catch (Exception e) {
			System.out.println("JdbcRoleAndHierarchyDao.findRoleHierarchy Exception: " + e.getMessage());
			return null;
		}
	}

	// get a specific RoleHierarchy by a given IncludedRole
	@Override
	public RoleHierarchy findRoleHierarchyByIncludedRole(Role role)
			throws Exception {
		if (role == null)
			throw new Exception("Missing input included role");
		try {
			RoleHierarchy roleHiera = namedParameterJdbcTemplate.queryForObject(
					strHieraQueryWithIncludedRoleId,
					new MapSqlParameterSource().addValue("OwnerAccountId", role.getOwnerAccountId()).addValue("IncludedRoleId", role.getId()),
					new RoleHierarchyMapper());
			return roleHiera;
		} 
		catch (Exception e) {
			System.out.println("JdbcRoleAndHierarchyDao.findRoleHierarchyByIncludedRole Exception: " + e.getMessage());
			return null;
		}
	}

	// get a specific RoleHierarchy by a given IncludedRoleId
	@Override
	public RoleHierarchy findRoleHierarchyByIncludedRoleId(int ownerAccountId, int roleId) {
		try {
			RoleHierarchy roleHiera = namedParameterJdbcTemplate.queryForObject(
					strHieraQueryWithIncludedRoleId,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("IncludedRoleId", roleId),
					new RoleHierarchyMapper());
			return roleHiera;
		} 
		catch (Exception e) {
			System.out.println("JdbcRoleAndHierarchyDao.findRoleHierarchyByIncludedRoleId Exception: " + e.getMessage());
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
		parameters.addValue("RoleId", roleHiera.getRoleId());
		parameters.addValue("IncludedRoleId", roleHiera.getIncludedRoleId());
		parameters.addValue("OwnerAccountId", roleHiera.getOwnerAccountId());
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
			int retId = insertRole.executeAndReturnKey(parameters).intValue();
			return retId;
		}
		catch (DuplicateKeyException e1) {
			System.out.println("JdbcRoleAndHierarchyDao.addRoleHierarchy Exception: " + e1.getMessage());
			throw e1;
		}
		catch (Exception e2) {
			System.out.println("JdbcRoleAndHierarchyDao.addRoleHierarchy Exception: " + e2.getMessage());
			throw e2;
		}
	}

	// Save the changes of an existing RoleHierarchy object. Return the # of record updated
	@Override
	public int saveRoleHierarchy(RoleHierarchy roleHiera) 
			throws DuplicateKeyException, Exception {
		if (roleHiera == null)
			throw new Exception("Missing input roleHierarchy");
		try {
			int numRecUpdated = namedParameterJdbcTemplate.update(
					"update RoleHierarchy set " + fieldSetForUpdateRoleHierarchy + " where RoleId=:RoleId and " +
							"IncludedRoleId=:IncludedRoleId and OwnerAccountId=:OwnerAccountId;",
					getRoleHierarchyMapSqlParameterSource(roleHiera));
			return numRecUpdated;
		}
		catch (DuplicateKeyException e1) {
			System.out.println("JdbcRoleAndHierarchyDao.saveRoleHierarchy Exception: " + e1.getMessage());
			throw e1;
		}
		catch (Exception e2) {
			System.out.println("JdbcRoleAndHierarchyDao.saveRoleHierarchy Exception: " + e2.getMessage());
			throw e2;
		}
	}

	// Delete a RoleHierarchy object. Return the # of record deleted
	@Override
	public int deleteRoleHierarchy(int ownerAccountId, int roleId)
			throws Exception {
		if (ownerAccountId < 0 || roleId <= 0)
			return 0;
		try {
			int numRecDeleted = namedParameterJdbcTemplate.update(
					"delete from RoleHierarchy where RoleId=:RoleId and OwnerAccountId=:OwnerAccountId", 
					new MapSqlParameterSource().addValue("RoleId", roleId).addValue("OwnerAccountId", ownerAccountId));
			return numRecDeleted;
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
