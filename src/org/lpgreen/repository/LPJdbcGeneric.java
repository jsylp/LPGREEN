package org.lpgreen.repository;

import java.sql.Types;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.sql.DataSource;
import org.joda.time.DateTime;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import org.lpgreen.util.MustOverrideException;
import org.lpgreen.util.InvalidDataValueException;

/**
 * JdbcGeneric is the helper class to implement generic JDBC functions
 * 
 * Creation date: Mar. 21, 2013
 * Last modify date: Mar. 21, 2013
 * 
 * @author  Jiaxun Stephen Yu
 * @version 1.0
 */

public class LPJdbcGeneric<T> {

	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	protected SimpleJdbcInsert insertDomainObject;

	// Called by the derived class
	public void setDataSource(DataSource dataSource)
			throws MustOverrideException {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		String strSqlTable = getSqlTable();
		if (strSqlTable == null) {
			throw new MustOverrideException("JdbcGeneric.setDataSource: must override getSqlTable()");
		}
		insertDomainObject = new SimpleJdbcInsert(dataSource).withTableName(strSqlTable).usingGeneratedKeyColumns("id");
	}

	// Must be overridden by the derived class to return the SQL table name
	protected String getSqlTable() {
		return null;
	}

	// Must be overridden by the derived class to return the field selection for read
	protected String getFieldSelectionForRead() {
		return null;
	}

	// Must be overridden by the derived class to return the field selection for update
	protected String getFieldSelectionForUpdate() {
		return null;
	}

	// Must be overridden by the derived class to return the record RowMapper
	protected RowMapper<T> getRowMapper() {
		return null;
	}

	// Must be overridden by the derived class to return the MapSqlParameterSource object
	protected MapSqlParameterSource getDomainObjectMapSqlParameterSource(T domainObj, boolean bNew) {
		return null;
	}

	// Parse the input current phases and construct SQL query string part
	private String getCurrentPhaseQueryPart(Set<String> currentPhases) {
		if (currentPhases != null && currentPhases.size() > 0) {
			StringBuffer sbCurrentPhases = new StringBuffer();
			sbCurrentPhases.append(" AND LOWER(o.CurrentPhase) IN (");
			boolean bFirst = true;
			Iterator<String> it = currentPhases.iterator();
			while (it.hasNext()) {
				String currentPhase = it.next();
				if (currentPhase.isEmpty() || currentPhase.toLowerCase().equals("all")) {
					sbCurrentPhases.setLength(0);
					break;
				}
				else {
					if (!bFirst)
						sbCurrentPhases.append(", ");
					else
						bFirst = false;
					sbCurrentPhases.append("'" + currentPhase.toLowerCase() + "'");
				}
			}
			if (sbCurrentPhases.length() > 0) {
				sbCurrentPhases.append(") ");
				return sbCurrentPhases.toString();
			}
			else
				return "";
		}
		else
			return "";
	}

	// Get a specific domain object by a given database id. Because
	// this is a common operation, this method assumes the database column
	// used in the queried is "Id".
	public T findDomainObjectById(int id)
			throws MustOverrideException {
		try {
			String strReadFields = getFieldSelectionForRead();
			String strSqlTable  = getSqlTable();
			RowMapper<T> mapper = getRowMapper();
			if (strReadFields == null || strSqlTable == null || mapper == null) {
				throw new MustOverrideException("Missing derived class override get functions");
			}
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(strReadFields);
			sbQuery.append(" from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" as o where Id=:Id");
			T domanObj = namedParameterJdbcTemplate.queryForObject(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("Id", id),
					mapper);
			return domanObj;
		}
		catch (Exception e) {
			System.out.println("LPJdbcGeneric.findDomainObjectById Exception: " + e.getMessage());
			return null;
		}
	}

	// Get all domain objects owned by a specific owner account id. Because
	// this is a common operation, this method assumes the database column
	// used in the queried is "OwnerAccountId".
	public List<T> findDomainObjectsByOwnerAccountId(int ownerAccountId)
			throws MustOverrideException {
		try {
			String strReadFields = getFieldSelectionForRead();
			String strSqlTable  = getSqlTable();
			RowMapper<T> mapper = getRowMapper();
			if (strReadFields == null || strSqlTable == null || mapper == null) {
				throw new MustOverrideException("Missing derived class override get functions");
			}
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(strReadFields);
			sbQuery.append(" from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" as o where OwnerAccountId=:OwnerAccountId");
			List<T> domainObjs = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId),
					mapper);
			return domainObjs;
		}
		catch (Exception e) {
			System.out.println("LPJdbcGeneric.findDomainObjectsByOwnerAccountId Exception: " + e.getMessage());
			return null;
		}
	}

	// Get all domain objects owned by a specific owner account id and
	// the current phases. Because this is a common operation, this method
	// assumes the database columns used in the queried is "OwnerAccountId"
	// and the "CurrentPhase".
	public List<T> findDomainObjectsByOwnerAccountId(int ownerAccountId, Set<String> currentPhases)
			throws MustOverrideException {
		try {
			String strReadFields = getFieldSelectionForRead();
			String strSqlTable  = getSqlTable();
			RowMapper<T> mapper = getRowMapper();
			if (strReadFields == null || strSqlTable == null || mapper == null) {
				throw new MustOverrideException("Missing derived class override get functions");
			}
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(strReadFields);
			sbQuery.append(" from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" as o where OwnerAccountId=:OwnerAccountId");
			sbQuery.append(getCurrentPhaseQueryPart(currentPhases));
			List<T> domainObjs = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId),
					mapper);
			return domainObjs;
		}
		catch (Exception e) {
			System.out.println("LPJdbcGeneric.findDomainObjectsByOwnerAccountId Exception: " + e.getMessage());
			return null;
		}
	}

	// Get domain objects by a boolean value column (database - decimal(1.0)).
	public List<T> findDomainObjectsByBoolVal(int ownerAccountId, String colName, boolean boolVal)
			throws MustOverrideException, InvalidDataValueException {
		try {
			if (colName == null) {
				throw new InvalidDataValueException("Missing input colName");
			}
			String strReadFields = getFieldSelectionForRead();
			String strSqlTable  = getSqlTable();
			RowMapper<T> mapper = getRowMapper();
			if (strReadFields == null || strSqlTable == null || mapper == null) {
				throw new MustOverrideException("Missing derived class override get functions");
			}
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(strReadFields);
			sbQuery.append(" from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" as o where OwnerAccountId=:OwnerAccountId and ");
			sbQuery.append(colName);
			sbQuery.append("=:");
			sbQuery.append(colName);
			List<T> domainObjs = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).
							addValue(colName, boolVal ? 1.0 : 0.0),
					mapper);
			return domainObjs;
		}
		catch (Exception e) {
			System.out.println("LPJdbcGeneric.findDomainObjectsByBoolVal Exception: " + e.getMessage());
			return null;
		}
	}

	// Get domain objects by an integer value column.
	public List<T> findDomainObjectsByIntVal(int ownerAccountId, String colName, int intVal)
			throws MustOverrideException, InvalidDataValueException {
		try {
			if (colName == null) {
				throw new InvalidDataValueException("Missing input colName");
			}
			String strReadFields = getFieldSelectionForRead();
			String strSqlTable  = getSqlTable();
			RowMapper<T> mapper = getRowMapper();
			if (strReadFields == null || strSqlTable == null || mapper == null) {
				throw new MustOverrideException("Missing derived class override get functions");
			}
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(strReadFields);
			sbQuery.append(" from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" as o where OwnerAccountId=:OwnerAccountId and ");
			sbQuery.append(colName);
			sbQuery.append("=:");
			sbQuery.append(colName);
			List<T> domainObjs = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue(colName, intVal),
					mapper);
			return domainObjs;
		}
		catch (Exception e) {
			System.out.println("LPJdbcGeneric.findDomainObjectsByIntVal Exception: " + e.getMessage());
			return null;
		}
	}

	// Get domain objects by a long integer value column.
	public List<T> findDomainObjectsByLongVal(int ownerAccountId, String colName, long longVal)
			throws MustOverrideException, InvalidDataValueException {
		try {
			if (colName == null) {
				throw new InvalidDataValueException("Missing input colName");
			}
			String strReadFields = getFieldSelectionForRead();
			String strSqlTable  = getSqlTable();
			RowMapper<T> mapper = getRowMapper();
			if (strReadFields == null || strSqlTable == null || mapper == null) {
				throw new MustOverrideException("Missing derived class override get functions");
			}
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(strReadFields);
			sbQuery.append(" from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" as o where OwnerAccountId=:OwnerAccountId and ");
			sbQuery.append(colName);
			sbQuery.append("=:");
			sbQuery.append(colName);
			List<T> domainObjs = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue(colName, longVal),
					mapper);
			return domainObjs;
		}
		catch (Exception e) {
			System.out.println("LPJdbcGeneric.findDomainObjectsByLongVal Exception: " + e.getMessage());
			return null;
		}
	}

	// Get domain objects by a string value column.
	public List<T> findDomainObjectsByStringVal(int ownerAccountId, String colName, String strVal)
			throws MustOverrideException, InvalidDataValueException {
		try {
			if (colName == null) {
				throw new InvalidDataValueException("Missing input colName");
			}
			String strReadFields = getFieldSelectionForRead();
			String strSqlTable  = getSqlTable();
			RowMapper<T> mapper = getRowMapper();
			if (strReadFields == null || strSqlTable == null || mapper == null) {
				throw new MustOverrideException("Missing derived class override get functions");
			}
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(strReadFields);
			sbQuery.append(" from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" as o where OwnerAccountId=:OwnerAccountId and ");
			sbQuery.append(colName);
			sbQuery.append("=:");
			sbQuery.append(colName);
			List<T> domainObjs = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue(colName, strVal),
					mapper);
			return domainObjs;
		}
		catch (Exception e) {
			System.out.println("LPJdbcGeneric.findDomainObjectsByStringVal Exception: " + e.getMessage());
			return null;
		}
	}

	// Get domain objects by a uuid value column.
	public List<T> findDomainObjectsByUUIDVal(int ownerAccountId, String colName, UUID uuidVal)
			throws MustOverrideException, InvalidDataValueException {
		try {
			if (colName == null) {
				throw new InvalidDataValueException("Missing input colName");
			}
			String strReadFields = getFieldSelectionForRead();
			String strSqlTable  = getSqlTable();
			RowMapper<T> mapper = getRowMapper();
			if (strReadFields == null || strSqlTable == null || mapper == null) {
				throw new MustOverrideException("Missing derived class override get functions");
			}
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(strReadFields);
			sbQuery.append(" from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" as o where OwnerAccountId=:OwnerAccountId and ");
			sbQuery.append(colName);
			sbQuery.append("=:");
			sbQuery.append(colName);
			List<T> domainObjs = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue(colName, uuidVal),
					mapper);
			return domainObjs;
		}
		catch (Exception e) {
			System.out.println("LPJdbcGeneric.findDomainObjectsByUUIDVal Exception: " + e.getMessage());
			return null;
		}
	}

	// Get domain objects by an integer value column in a [start, end] range.
	public List<T> findDomainObjectsByIntValRange(int ownerAccountId, String colName,
			int intStartVal, int intEndVal)
			throws MustOverrideException, InvalidDataValueException {
		try {
			if (colName == null) {
				throw new InvalidDataValueException("Missing input colName");
			}
			String strReadFields = getFieldSelectionForRead();
			String strSqlTable  = getSqlTable();
			RowMapper<T> mapper = getRowMapper();
			if (strReadFields == null || strSqlTable == null || mapper == null) {
				throw new MustOverrideException("Missing derived class override get functions");
			}
			if (intStartVal > intEndVal) {
				int intTemp = intStartVal;
				intStartVal = intEndVal;
				intEndVal   = intTemp;
			}
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(strReadFields);
			sbQuery.append(" from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" as o where OwnerAccountId=:OwnerAccountId and ");
			sbQuery.append(colName);
			sbQuery.append(" >= :StartVal and ");
			sbQuery.append(colName);
			sbQuery.append(" <= :EndVal;");
			List<T> domainObjs = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).
						addValue("StartVal", intStartVal).addValue("EndVal", intEndVal),
					mapper);
			return domainObjs;
		}
		catch (Exception e) {
			System.out.println("LPJdbcGeneric.findDomainObjectsByIntValRange Exception: " + e.getMessage());
			return null;
		}
	}

	// Get domain objects by a long value column in a [start, end] range.
	public List<T> findDomainObjectsByLongValRange(int ownerAccountId, String colName,
			long longStartVal, long longEndVal)
			throws MustOverrideException, InvalidDataValueException {
		try {
			if (colName == null) {
				throw new InvalidDataValueException("Missing input colName");
			}
			String strReadFields = getFieldSelectionForRead();
			String strSqlTable  = getSqlTable();
			RowMapper<T> mapper = getRowMapper();
			if (strReadFields == null || strSqlTable == null || mapper == null) {
				throw new MustOverrideException("Missing derived class override get functions");
			}
			if (longStartVal > longEndVal) {
				long longTemp = longStartVal;
				longStartVal = longEndVal;
				longEndVal   = longTemp;
			}
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(strReadFields);
			sbQuery.append(" from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" as o where OwnerAccountId=:OwnerAccountId and ");
			sbQuery.append(colName);
			sbQuery.append(" >= :StartVal and ");
			sbQuery.append(colName);
			sbQuery.append(" <= :EndVal;");
			List<T> domainObjs = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).
						addValue("StartVal", longStartVal).addValue("EndVal", longEndVal),
					mapper);
			return domainObjs;
		}
		catch (Exception e) {
			System.out.println("LPJdbcGeneric.findDomainObjectsByLongValRange Exception: " + e.getMessage());
			return null;
		}
	}

	// Get domain objects by a string value column in a [start, end] range.
	public List<T> findDomainObjectsByStringValRange(int ownerAccountId, String colName,
			String strStartVal, String strEndVal)
			throws MustOverrideException, InvalidDataValueException {
		try {
			if (colName == null) {
				throw new InvalidDataValueException("Missing input colName");
			}
			if (strStartVal == null && strEndVal == null) {
				throw new InvalidDataValueException("Missing input strStartVal and strEndVal");
			}
			String strReadFields = getFieldSelectionForRead();
			String strSqlTable  = getSqlTable();
			RowMapper<T> mapper = getRowMapper();
			if (strReadFields == null || strSqlTable == null || mapper == null) {
				throw new MustOverrideException("Missing derived class override get functions");
			}
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(strReadFields);
			sbQuery.append(" from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" as o where OwnerAccountId=:OwnerAccountId");

			MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
			sqlParameters.addValue("OwnerAccountId", ownerAccountId);
			if (strStartVal != null) {
				sbQuery.append(" and ");
				sbQuery.append(colName);
				sbQuery.append(" >= :StartVal");
				sqlParameters.addValue("StartVal", strStartVal);
			}
			if (strEndVal != null) {
				sbQuery.append(" and ");
				sbQuery.append(colName);
				sbQuery.append(" <= :EndVal");
				sqlParameters.addValue("EndVal", strEndVal);
			}
			sbQuery.append(";");
			List<T> domainObjs = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					sqlParameters,
					mapper);
			return domainObjs;
		}
		catch (Exception e) {
			System.out.println("LPJdbcGeneric.findDomainObjectsByStringValRange Exception: " + e.getMessage());
			return null;
		}
	}

	// Get domain objects by a DateTime value column in a [start, end] range.
	public List<T> findDomainObjectsByDateTimeRange(int ownerAccountId, String colName,
			DateTime dtStartVal, DateTime dtEndVal)
			throws MustOverrideException, InvalidDataValueException {
		try {
			if (colName == null) {
				throw new InvalidDataValueException("Missing input colName");
			}
			if (dtStartVal == null && dtEndVal == null) {
				throw new InvalidDataValueException("Missing input dtStartVal and dtEndVal");
			}
			String strReadFields = getFieldSelectionForRead();
			String strSqlTable  = getSqlTable();
			RowMapper<T> mapper = getRowMapper();
			if (strReadFields == null || strSqlTable == null || mapper == null) {
				throw new MustOverrideException("Missing derived class override get functions");
			}
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(strReadFields);
			sbQuery.append(" from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" as o where OwnerAccountId=:OwnerAccountId");

			MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
			sqlParameters.addValue("OwnerAccountId", ownerAccountId);
			if (dtStartVal != null) {
				sbQuery.append(" and ");
				sbQuery.append(colName);
				sbQuery.append(" >= :StartDate");
				sqlParameters.addValue("StartDate", dtStartVal.toCalendar(null), Types.TIMESTAMP);
			}
			if (dtEndVal != null) {
				sbQuery.append(" and ");
				sbQuery.append(colName);
				sbQuery.append(" <= :EndDate");
				sqlParameters.addValue("EndDate", dtEndVal.toCalendar(null), Types.TIMESTAMP);
			}
			sbQuery.append(";");
			List<T> domainObjs = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					sqlParameters,
					mapper);
			return domainObjs;
		}
		catch (Exception e) {
			System.out.println("LPJdbcGeneric.findDomainObjectsByDateTimeRange Exception: " + e.getMessage());
			return null;
		}
	}

	// Add a domain object to the database and return the generated database id
	public int addDomainObject(T domainObj)
			throws MustOverrideException, DuplicateKeyException, Exception {
		try {
			if (domainObj == null) {
				throw new Exception("Missing input domainObj");
			}
			MapSqlParameterSource parameters = getDomainObjectMapSqlParameterSource(domainObj, true);
			if (parameters == null) {
				throw new MustOverrideException("Missing derived class override getDomainObjectMapSqlParameterSource");
			}
			// Insert the domain object to the database
			return insertDomainObject.executeAndReturnKey(parameters).intValue();
		}
		catch (DuplicateKeyException e) {
			System.out.println("LPJdbcGeneric.addDomainObject Exception: " + e.getMessage());
			throw e;
		}
		catch (Exception e) {
			System.out.println("LPJdbcGeneric.addDomainObject Exception: " + e.getMessage());
			throw e;
		}
	}

	// Save changes of an existing domain object to the database. Return the # of records updated
	public int saveDomainObject(T domainObj)
			throws DuplicateKeyException, Exception {
		try {
			if (domainObj == null) {
				throw new Exception("Missing input domainObj");
			}
			String strUpdateFields = getFieldSelectionForUpdate();
			String strSqlTable = getSqlTable();
			if (strUpdateFields == null || strSqlTable == null) {
				throw new MustOverrideException("Missing derived class override get functions");
			}
			MapSqlParameterSource parameters = getDomainObjectMapSqlParameterSource(domainObj, false);
			if (parameters == null) {
				throw new MustOverrideException("Missing derived class override getDomainObjectMapSqlParameterSource");
			}
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("update ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" set ");
			sbQuery.append(strUpdateFields);
			sbQuery.append(" where Id=:Id;");
			int numRecUpdated = namedParameterJdbcTemplate.update(
					sbQuery.toString(),
					parameters);
			return numRecUpdated;
		}
		catch (DuplicateKeyException e) {
			System.out.println("LPJdbcGeneric.saveDomainObject Exception: " + e.getMessage());
			throw e;
		}
		catch (Exception e) {
			System.out.println("LPJdbcGeneric.saveDomainObject Exception: " + e.getMessage());
			throw e;
		}
	}

	// Delete a domain object from the database. Return the # of records deleted
	public int deleteDomainObject(int ownerAccountId, int id)
		throws MustOverrideException, Exception {
		if (ownerAccountId < 0 || id <= 0)
			return 0;
		try {
			String strSqlTable = getSqlTable();
			if (strSqlTable == null) {
				throw new MustOverrideException("Missing derived class override getSqlTable");
			}
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("delete from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" where Id=:Id and OwnerAccountId=:OwnerAccountId;");
			int numRecDeleted = namedParameterJdbcTemplate.update(
					sbQuery.toString(), 
					new MapSqlParameterSource().addValue("Id", id).addValue("OwnerAccountId", ownerAccountId));
			return numRecDeleted;
		}
		catch (Exception e) {
			System.out.println("LPJdbcGeneric.deleteDomainObject Exception: " + e.getMessage());
			throw e;
		}
	}

}
