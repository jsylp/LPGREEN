package org.lpgreen.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.lpgreen.domain.OperationRight;
import org.lpgreen.util.MustOverrideException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * JdbcOperationRightDao is the JDBC implementation of the OperationRightDao for OperationRight related entity's persistence layer
 * 
 * Creation date: Jan. 13, 2013
 * Last modify date: Feb. 15, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

@Repository
public class JdbcOperationRightDao extends LPJdbcGeneric<OperationRight> implements OperationRightDao {

	public void setDataSource(DataSource dataSource)
			throws MustOverrideException {
		try {
			super.setDataSource(dataSource);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcOperationRightDao.setDataSource Exception: " + e.getMessage());
			throw e;
		}
	}

	// o: the main object: this OperationRight; 
	protected final static String fieldSelectionForReadOperationRight =
			"o.Id,o.OperationName,o.Description,o.OwnerAccountId";

	// field selection for update
	protected final static String fieldSetForUpdateOperationRight = 
			"OperationName=:OperationName,Description=:Description,OwnerAccountId=:OwnerAccountId";

	// RowMapper class
	private static class OperationRightMapper implements RowMapper<OperationRight> {
		
		public OperationRight mapRow(ResultSet rs, int rowNum) throws SQLException {
			OperationRight opRight = new OperationRight();
			opRight.setId(rs.getInt("Id"));
			opRight.setOperationName(rs.getString("OperationName"));
			opRight.setDescription(rs.getString("Description"));
			opRight.setOwnerAccountId(rs.getInt("OwnerAccountId"));
			return opRight;
		}
	}

	// Override to return the SQL table name
	protected String getSqlTable() {
		return "OperationRight";
	}

	// Override to return the field selection for read
	protected String getFieldSelectionForRead() {
		return fieldSelectionForReadOperationRight;
	}

	// Override to return the filed selection for update
	protected String getFieldSelectionForUpdate() {
		return fieldSetForUpdateOperationRight;
	}

	// Override to return the field order for read a list of objects
	protected String getFieldOrderForReadList() {
		return "o.OwnerAccountId, o.OperationName ASC";
	}

	// Override to return the RowMapper
	protected RowMapper<OperationRight> getRowMapper() {
		return new OperationRightMapper();
	}

	// Override to return MapSqlParameterSource for creating OperationRight
	protected MapSqlParameterSource getDomainObjectMapSqlParameterSource(OperationRight opRight, boolean bNew) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		if (!bNew) {
			if (opRight.getId() > 0)
				parameters.addValue("Id", opRight.getId());	// auto generated when insert a OperationRight, use it as the primary key when update it
			else
				parameters.addValue("Id", null);
		}
		parameters.addValue("OperationName", opRight.getOperationName());
		if (opRight.getDescription() != null && !opRight.getDescription().isEmpty())
			parameters.addValue("Description", opRight.getDescription());
		else
			parameters.addValue("Description",null);
		if (opRight.getOwnerAccountId() > 0)
			parameters.addValue("OwnerAccountId", opRight.getOwnerAccountId());
		else
			parameters.addValue("OwnerAccountId", null);
		return parameters;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// OperationRight related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	// get all OperationRights owned by a specific owner account id
	@Override
	public List<OperationRight> findAllSiteOperationRights(int ownerAccountId) {
		try {
			return findDomainObjectsByOwnerAccountId(ownerAccountId, null,
					null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcOperationRightDao.findAllSiteOperationRights MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcOperationRightDao.findAllSiteOperationRights Exception: " + e.getMessage());
			return null;
		}
	}

	// get a specific OperationRight by a given database id
	@Override
	public OperationRight findOperationRightById(int id) {
		try {
			return findDomainObjectById(id, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcOperationRightDao.findOperationRightById MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcOperationRightDao.findOperationRightById Exception: " + e.getMessage());
			return null;
		}
	}

	// get all OperationRights by OperationName
	@Override
	public List<OperationRight> findOperationRightByName(int ownerAccountId, String opName) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null,
					"o.OperationName", opName, true, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcOperationRightDao.findOperationRightByName MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcOperationRightDao.findOperationRightByName Exception: " + e.getMessage());
			return null;
		}
	}

	// Add an OperationRight. Return the generated database id
	@Override
	public int addOperationRight(OperationRight opRight) 
			throws DuplicateKeyException, Exception {
		try {
			// insert OperationRight record
			int retId = addDomainObject(opRight);
			opRight.setId(retId);
			return retId;
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcOperationRightDao.addOperationRight MustOverrideException: " + e.getMessage());
			return -1;
		}
		catch (DuplicateKeyException e) {
			System.out.println("JdbcOperationRightDao.addOperationRight DuplicateKeyException: " + e.getMessage());
			throw e;
		}
		catch (Exception e) {
			System.out.println("JdbcOperationRightDao.addOperationRight Exception: " + e.getMessage());
			throw e;
		}
	}

	// Save changes of an existing OperationRight object. Return the # of records updated
	@Override
	public int saveOperationRight(OperationRight opRight) 
			throws DuplicateKeyException, Exception {
		try {
			int numObjectUpdated = saveDomainObject(opRight);
			if (numObjectUpdated == 0) {
				throw new Exception("Fail to update the OperationRight obejct");
			}
			return numObjectUpdated;
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcOperationRightDao.saveOperationRight MustOverrideException: " + e.getMessage());
			return -1;
		}
		catch (DuplicateKeyException e) {
			System.out.println("JdbcOperationRightDao.saveOperationRight DuplicateKeyException: " + e.getMessage());
			throw e;
		}
		catch (Exception e) {
			System.out.println("JdbcOperationRightDao.saveOperationRight Exception: " + e.getMessage());
			throw e;
		}
	}

	// Delete an OperationRight object. Return the # of record deleted
	@Override
	public int deleteOperationRight(int ownerAccountId, int id)
			throws Exception {
		if (ownerAccountId <= 0 || id <= 0)
			return 0;
		try {
			return deleteDomainObject(ownerAccountId, id);
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
