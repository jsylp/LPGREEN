package org.lpgreen.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.lpgreen.domain.OperationRight;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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
public class JdbcOperationRightDao implements OperationRightDao {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertOperationRight;
	public void setDataSource(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		insertOperationRight = new SimpleJdbcInsert(dataSource).withTableName("OperationRight").usingGeneratedKeyColumns("id");
	}

	// o: the main object: this OperationRight; 
	protected final static String fieldSelectionForReadOperationRight =
			"o.Id,o.OperationName,o.Description,o.OwnerAccountId";

	protected final static String fieldSetForUpdateOperationRight = 
			"OperationName=:OperationName,Description=:Description,OwnerAccountId=:OwnerAccountId";

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// OperationRight related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

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

	// query OperationRight using OwnerAccountId
	protected final static String strOperationRightQueryWithOwnerAccountId = "select " + fieldSelectionForReadOperationRight +
			" from OperationRight as o where OwnerAccountId=:OwnerAccountId";

	// get all OperationRight owned by a specific account id
	@Override
	public List<OperationRight> findAllSiteOperationRights(int ownerAccountId) {
		try {
			List<OperationRight> opRights = namedParameterJdbcTemplate.query(
					strOperationRightQueryWithOwnerAccountId,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId),
					new OperationRightMapper());
			return opRights;
		}
		catch (Exception e) {
			System.out.println("JdbcOperationRightDao.findAllSiteOperationRights Exception: " + e.getMessage());
			return null;
		}
	}

	// query OperationRight using Id
	protected final static String strOperationRightQueryWithId = "select " + fieldSelectionForReadOperationRight +
			" from OperationRight as o where OwnerAccountId=:OwnerAccountId and Id=:Id";

	// get a specific OperationRight by a given id
	@Override
	public OperationRight findOperationRightById(int ownerAccountId, int id) {
		try {
			OperationRight opRight = namedParameterJdbcTemplate.queryForObject(
					strOperationRightQueryWithId,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("Id", id),
					new OperationRightMapper());
			return opRight;
		} 
		catch (Exception e) {
			System.out.println("JdbcOperationRightDao.findOperationRightById Exception: " + e.getMessage());
			return null;
		}
	}

	// query OperationRight using OperationName
	protected final static String strOperationRightQueryWithOperationName = "select " + fieldSelectionForReadOperationRight +
			" from OperationRight as o where OwnerAccountId=:OwnerAccountId and OperationName=:OperationName";

	// get a specific OperationRight by a given name
	@Override
	public OperationRight findOperationRightByName(int ownerAccountId, String opName) {
		try {
			OperationRight opRight = namedParameterJdbcTemplate.queryForObject(
					strOperationRightQueryWithOperationName,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("OperationName", opName),
					new OperationRightMapper());
			
			return opRight;
		} 
		catch (Exception e) {
			System.out.println("JdbcOperationRightDao.findOperationRightByName Exception: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Set SQL Parameters used for creating OperationRight
	 * @param opRight
	 * @param bNew
	 * @return
	 */
	private MapSqlParameterSource getOperationRightMapSqlParameterSource(OperationRight opRight, boolean bNew) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		if (!bNew) {
			if (opRight.getId() > 0)
				parameters.addValue("Id", opRight.getId());	// auto generated when insert a OperationRight, use it as the primary key when update it
			else
				parameters.addValue("Id", null);
		}
		parameters.addValue("OperationName", opRight.getOperationName());
		parameters.addValue("Description", opRight.getDescription());
		if (opRight.getOwnerAccountId() > 0)
			parameters.addValue("OwnerAccountId", opRight.getOwnerAccountId());
		else
			parameters.addValue("OwnerAccountId", null);
		return parameters;
	}

	// Add an OperationRight. Return the generated id
	@Override
	public int addOperationRight(OperationRight opRight) 
			throws DuplicateKeyException, Exception {
		if (opRight == null)
			throw new Exception("Missing input opRight");

		MapSqlParameterSource parameters = this.getOperationRightMapSqlParameterSource(opRight, true);	
		try {
			// insert OperationRight record
			int retId = insertOperationRight.executeAndReturnKey(parameters).intValue();
			opRight.setId(retId);
			return retId;
		}
		catch (DuplicateKeyException e1) {
			System.out.println("JdbcOperationRightDao.addOperationRight Exception: " + e1.getMessage());
			throw e1;
		}
		catch (Exception e2) {
			System.out.println("JdbcOperationRightDao.addOperationRight Exception: " + e2.getMessage());
			throw e2;
		}
	}

	// Save a the changes of an existing OperationRight object. Return the # of record updated
	@Override
	public int saveOperationRight(OperationRight opRight) 
			throws DuplicateKeyException, Exception {
		if (opRight == null)
			throw new Exception("Missing input opRight");
		try {
			int numRecUpdated = namedParameterJdbcTemplate.update(
					"update OperationRight set " + fieldSetForUpdateOperationRight + " where Id=:Id;",
					getOperationRightMapSqlParameterSource(opRight, false));
			return numRecUpdated;
		}
		catch (DuplicateKeyException e1) {
			System.out.println("JdbcOperationRightDao.saveOperationRight Exception: " + e1.getMessage());
			throw e1;
		}
		catch (Exception e2) {
			System.out.println("JdbcOperationRightDao.saveOperationRight Exception: " + e2.getMessage());
			throw e2;
		}
	}

	// Delete an OperationRight object. Return the # of record deleted
	@Override
	public int deleteOperationRight(int ownerAccountId, int id)
			throws Exception {
		if (ownerAccountId < 0 || id <= 0)
			return 0;
		try {
			int numRecDeleted = namedParameterJdbcTemplate.update(
					"delete from OperationRight where Id=:Id and OwnerAccountId=:OwnerAccountId", 
					new MapSqlParameterSource().addValue("Id", id).addValue("OwnerAccountId", ownerAccountId));
			return numRecDeleted;
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
