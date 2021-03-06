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
 * Last modify date: May. 17, 2013
 * 
 * @author  Jiaxun Stephen Yu
 * @version 1.0
 */

public class LPJdbcGeneric<T, R extends Number> {

	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	protected SimpleJdbcInsert insertDomainObject;

	// Called by the derived class
	public void setDataSource(DataSource dataSource)
			throws MustOverrideException {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		String strSqlTable = getSqlTable();
		if (strSqlTable == null || strSqlTable.isEmpty()) {
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

	// Must be overridden by the derived class to return the name of the current status column
	protected String getCurrentStatusColumn() {
		return null;
	}

	// Must be overridden by the derived class to return the field order for read a list of objects
	protected String getFieldOrderForReadList() {
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

	// Parse the input current status and construct SQL query string part
	protected String getCurrentStatusQueryPart(Set<String> currentStatuses)
			throws MustOverrideException {
		if (currentStatuses != null && currentStatuses.size() > 0) {
			String statusName = getCurrentStatusColumn();
			if (statusName == null || statusName.isEmpty()) {
				throw new MustOverrideException("Missing derived class override getCurrentStatusQueryPart");
			}
			StringBuffer sbCurrentStatuses = new StringBuffer();
			sbCurrentStatuses.append(" AND LOWER(o.");
			sbCurrentStatuses.append(statusName);
			sbCurrentStatuses.append(") IN (");
			boolean bFirst = true;
			Iterator<String> it = currentStatuses.iterator();
			while (it.hasNext()) {
				String currentStatus = it.next();
				if (currentStatus.isEmpty() || currentStatus.toLowerCase().equals("all")) {
					sbCurrentStatuses.setLength(0);
					break;
				}
				else {
					if (!bFirst)
						sbCurrentStatuses.append(", ");
					else
						bFirst = false;
					sbCurrentStatuses.append("'" + currentStatus.toLowerCase() + "'");
				}
			}
			if (sbCurrentStatuses.length() > 0) {
				sbCurrentStatuses.append(") ");
				return sbCurrentStatuses.toString();
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
	public T findDomainObjectById(int id, String outJoins)
			throws MustOverrideException {
		String strReadFields = getFieldSelectionForRead();
		String strSqlTable  = getSqlTable();
		RowMapper<T> mapper = getRowMapper();
		if (strReadFields == null || strReadFields.isEmpty() ||
		    strSqlTable == null || strSqlTable.isEmpty() || mapper == null) {
			throw new MustOverrideException("Missing derived class override get functions");
		}
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(strReadFields);
			sbQuery.append(" from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" as o ");
			if (outJoins != null && !outJoins.isEmpty()) {
				sbQuery.append(outJoins);
			}
			sbQuery.append(" where o.Id=:Id;");
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

	// Get a specific domain object by a given database id. Because
	// this is a common operation, this method assumes the database column
	// used in the queried is "Id".
	public T findDomainObjectById(long id, String outJoins)
			throws MustOverrideException {
		String strReadFields = getFieldSelectionForRead();
		String strSqlTable  = getSqlTable();
		RowMapper<T> mapper = getRowMapper();
		if (strReadFields == null || strReadFields.isEmpty() ||
		    strSqlTable == null || strSqlTable.isEmpty() || mapper == null) {
			throw new MustOverrideException("Missing derived class override get functions");
		}
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(strReadFields);
			sbQuery.append(" from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" as o ");
			if (outJoins != null && !outJoins.isEmpty()) {
				sbQuery.append(outJoins);
			}
			sbQuery.append(" where o.Id=:Id;");
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

	// Get all domain objects owned by a specific owner account id and
	// the current statuses. Because this is a common operation, this method
	// assumes the database columns used in the queried is "OwnerAccountId"
	// and the return from getCurrentStatusColumn().
	public List<T> findDomainObjectsByOwnerAccountId(int ownerAccountId, String outJoins, Set<String> currentStatuses, String extraCondition)
			throws MustOverrideException {
		String strReadFields = getFieldSelectionForRead();
		String strSqlTable  = getSqlTable();
		RowMapper<T> mapper = getRowMapper();
		if (strReadFields == null || strReadFields.isEmpty() ||
		    strSqlTable == null || strSqlTable.isEmpty() || mapper == null) {
			throw new MustOverrideException("Missing derived class override get functions");
		}
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(strReadFields);
			sbQuery.append(" from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" as o ");
			if (outJoins != null && !outJoins.isEmpty()) {
				sbQuery.append(outJoins);
			}
			sbQuery.append(" where o.OwnerAccountId=:OwnerAccountId ");
			sbQuery.append(getCurrentStatusQueryPart(currentStatuses));
			if (extraCondition != null && !extraCondition.isEmpty())
				sbQuery.append(" AND ").append(extraCondition);
			if (getFieldOrderForReadList() != null && !getFieldOrderForReadList().isEmpty())
				sbQuery.append(" order by ").append(getFieldOrderForReadList());
			sbQuery.append(";");
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

	// Get domain objects by a boolean column value (database - decimal(1.0)).
	public List<T> findDomainObjectsByBooleanColumnVal(int ownerAccountId, String outJoins,
			String colName, boolean boolVal, Set<String> currentStatuses, String extraCondition)
			throws MustOverrideException, InvalidDataValueException {
		if (colName == null) {
			throw new InvalidDataValueException("Missing input colName");
		}
		String strReadFields = getFieldSelectionForRead();
		String strSqlTable  = getSqlTable();
		RowMapper<T> mapper = getRowMapper();
		if (strReadFields == null || strReadFields.isEmpty() ||
		    strSqlTable == null || strSqlTable.isEmpty() || mapper == null) {
			throw new MustOverrideException("Missing derived class override get functions");
		}
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(strReadFields);
			sbQuery.append(" from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" as o ");
			if (outJoins != null && !outJoins.isEmpty()) {
				sbQuery.append(outJoins);
			}
			sbQuery.append(" where o.OwnerAccountId=:OwnerAccountId and ");
			sbQuery.append(colName);
			sbQuery.append("=:");
			sbQuery.append(colName.substring(colName.indexOf('.') + 1));
			sbQuery.append(" ");
			sbQuery.append(getCurrentStatusQueryPart(currentStatuses));
			if (extraCondition != null && !extraCondition.isEmpty())
				sbQuery.append(" AND ").append(extraCondition);
			if (getFieldOrderForReadList() != null && !getFieldOrderForReadList().isEmpty())
				sbQuery.append(" order by ").append(getFieldOrderForReadList());
			sbQuery.append(";");
			List<T> domainObjs = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).
							addValue(colName.substring(colName.indexOf('.') + 1), boolVal ? 1 : 0),
					mapper);
			return domainObjs;
		}
		catch (Exception e) {
			System.out.println("LPJdbcGeneric.findDomainObjectsByBooleanColumnVal Exception: " + e.getMessage());
			return null;
		}
	}

	// Get domain objects by a generic column value (Integer, Long, Double, UUID).
	public <V> List<T> findDomainObjectsByGenericTypeColumnVal(int ownerAccountId, String outJoins,
			String colName, V genVal, Set<String> currentStatuses, String extraCondition)
			throws MustOverrideException, InvalidDataValueException {
		if (colName == null) {
			throw new InvalidDataValueException("Missing input colName");
		}
		String strReadFields = getFieldSelectionForRead();
		String strSqlTable  = getSqlTable();
		RowMapper<T> mapper = getRowMapper();
		if (strReadFields == null || strReadFields.isEmpty() ||
		    strSqlTable == null || strSqlTable.isEmpty() || mapper == null) {
			throw new MustOverrideException("Missing derived class override get functions");
		}
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(strReadFields);
			sbQuery.append(" from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" as o ");
			if (outJoins != null && !outJoins.isEmpty()) {
				sbQuery.append(outJoins);
			}
			sbQuery.append(" where o.OwnerAccountId=:OwnerAccountId and ");
			sbQuery.append(colName);
			sbQuery.append("=:");
			sbQuery.append(colName.substring(colName.indexOf('.') + 1));
			sbQuery.append(" ");
			sbQuery.append(getCurrentStatusQueryPart(currentStatuses));
			if (extraCondition != null && !extraCondition.isEmpty())
				sbQuery.append(" AND ").append(extraCondition);
			if (getFieldOrderForReadList() != null && !getFieldOrderForReadList().isEmpty())
				sbQuery.append(" order by ").append(getFieldOrderForReadList());
			sbQuery.append(";");
			List<T> domainObjs = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue(colName.substring(colName.indexOf('.') + 1), genVal),
					mapper);
			return domainObjs;
		}
		catch (Exception e) {
			System.out.println("LPJdbcGeneric.findDomainObjectsByGenericTypeColumnVal Exception: " + e.getMessage());
			return null;
		}
	}

	// Get domain objects by a string column value.
	public List<T> findDomainObjectsByStringColumnVal(int ownerAccountId, String outJoins,
			String colName, String strVal, boolean caseSensitive, Set<String> currentStatuses, String extraCondition)
			throws MustOverrideException, InvalidDataValueException {
		if (colName == null) {
			throw new InvalidDataValueException("Missing input colName");
		}
		String strReadFields = getFieldSelectionForRead();
		String strSqlTable  = getSqlTable();
		RowMapper<T> mapper = getRowMapper();
		if (strReadFields == null || strReadFields.isEmpty() ||
		    strSqlTable == null || strSqlTable.isEmpty() || mapper == null) {
			throw new MustOverrideException("Missing derived class override get functions");
		}
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(strReadFields);
			sbQuery.append(" from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" as o ");
			if (outJoins != null && !outJoins.isEmpty()) {
				sbQuery.append(outJoins);
			}
			sbQuery.append(" where o.OwnerAccountId=:OwnerAccountId and ");
			if (caseSensitive)
				sbQuery.append(colName);
			else
				sbQuery.append("LOWER(").append(colName).append(")");
			sbQuery.append("=:");
			sbQuery.append(colName.substring(colName.indexOf('.') + 1));
			sbQuery.append(" ");
			sbQuery.append(getCurrentStatusQueryPart(currentStatuses));
			if (extraCondition != null && !extraCondition.isEmpty())
				sbQuery.append(" AND ").append(extraCondition);
			if (getFieldOrderForReadList() != null && !getFieldOrderForReadList().isEmpty())
				sbQuery.append(" order by ").append(getFieldOrderForReadList());
			sbQuery.append(";");
			
			if (!caseSensitive)
				strVal = strVal.toLowerCase();
			
			List<T> domainObjs = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue(colName.substring(colName.indexOf('.') + 1), strVal),
					mapper);
			return domainObjs;
		}
		catch (Exception e) {
			System.out.println("LPJdbcGeneric.findDomainObjectsByStringColumnVal Exception: " + e.getMessage());
			return null;
		}
	}

	// Get domain objects by a column value of Number in the [start, end] range.
	public <V extends Comparable<V>> List<T> findDomainObjectsByNumberColumnRange(int ownerAccountId, String outJoins,
			String colName, V numStartVal, V numEndVal, Set<String> currentStatuses, String extraCondition)
			throws MustOverrideException, InvalidDataValueException {

		String strReadFields = getFieldSelectionForRead();
		String strSqlTable  = getSqlTable();
		RowMapper<T> mapper = getRowMapper();
		if (strReadFields == null || strReadFields.isEmpty() ||
		    strSqlTable == null || strSqlTable.isEmpty() || mapper == null) {
			throw new MustOverrideException("Missing derived class override get functions");
		}
		if (colName == null) {
			throw new InvalidDataValueException("Missing input colName");
		}
		if (numStartVal.compareTo(numEndVal) > 0) {
			throw new InvalidDataValueException("Start value is larger than the end value");
		}
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(strReadFields);
			sbQuery.append(" from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" as o ");
			if (outJoins != null && !outJoins.isEmpty()) {
				sbQuery.append(outJoins);
			}
			sbQuery.append(" where o.OwnerAccountId=:OwnerAccountId and ");
			sbQuery.append(colName);
			sbQuery.append(" >= :StartVal and ");
			sbQuery.append(colName);
			sbQuery.append(" <= :EndVal ");
			sbQuery.append(getCurrentStatusQueryPart(currentStatuses));
			if (extraCondition != null && !extraCondition.isEmpty())
				sbQuery.append(" AND ").append(extraCondition);
			if (getFieldOrderForReadList() != null && !getFieldOrderForReadList().isEmpty())
				sbQuery.append(" order by ").append(getFieldOrderForReadList());
			sbQuery.append(";");
			List<T> domainObjs = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).
						addValue("StartVal", numStartVal).addValue("EndVal", numEndVal),
					mapper);
			return domainObjs;
		}
		catch (Exception e) {
			System.out.println("LPJdbcGeneric.findDomainObjectsByNumberColumnRange Exception: " + e.getMessage());
			return null;
		}
	}

	// Get domain objects by a string value column in a [start, end] range.
	public List<T> findDomainObjectsByStringColumnRange(int ownerAccountId, String outJoins,
			String colName, String strStartVal, String strEndVal, Set<String> currentStatuses, String extraCondition)
			throws MustOverrideException, InvalidDataValueException {
		if (colName == null) {
			throw new InvalidDataValueException("Missing input colName");
		}
		if (strStartVal == null && strEndVal == null) {
			throw new InvalidDataValueException("Missing input strStartVal and strEndVal");
		}
		String strReadFields = getFieldSelectionForRead();
		String strSqlTable  = getSqlTable();
		RowMapper<T> mapper = getRowMapper();
		if (strReadFields == null || strReadFields.isEmpty() ||
		    strSqlTable == null || strSqlTable.isEmpty() || mapper == null) {
			throw new MustOverrideException("Missing derived class override get functions");
		}
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(strReadFields);
			sbQuery.append(" from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" as o ");
			if (outJoins != null && !outJoins.isEmpty()) {
				sbQuery.append(outJoins);
			}
			sbQuery.append(" where o.OwnerAccountId=:OwnerAccountId");

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
			sbQuery.append(" ");
			sbQuery.append(getCurrentStatusQueryPart(currentStatuses));
			if (extraCondition != null && !extraCondition.isEmpty())
				sbQuery.append(" AND ").append(extraCondition);
			if (getFieldOrderForReadList() != null && !getFieldOrderForReadList().isEmpty())
				sbQuery.append(" order by ").append(getFieldOrderForReadList());
			sbQuery.append(";");
			List<T> domainObjs = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					sqlParameters,
					mapper);
			return domainObjs;
		}
		catch (Exception e) {
			System.out.println("LPJdbcGeneric.findDomainObjectsByStringColumnRange Exception: " + e.getMessage());
			return null;
		}
	}

	// Get domain objects by a DateTime value column in a [start, end] range.
	public List<T> findDomainObjectsByDateTimeRange(int ownerAccountId, String outJoins,
			String colName, DateTime dtStartVal, DateTime dtEndVal, Set<String> currentStatuses, String extraCondition)
			throws MustOverrideException, InvalidDataValueException {
		if (colName == null) {
			throw new InvalidDataValueException("Missing input colName");
		}
		if (dtStartVal == null && dtEndVal == null) {
			throw new InvalidDataValueException("Missing input dtStartVal and dtEndVal");
		}
		String strReadFields = getFieldSelectionForRead();
		String strSqlTable  = getSqlTable();
		RowMapper<T> mapper = getRowMapper();
		if (strReadFields == null || strReadFields.isEmpty() ||
		    strSqlTable == null || strSqlTable.isEmpty() || mapper == null) {
			throw new MustOverrideException("Missing derived class override get functions");
		}
		if (dtStartVal != null && dtEndVal != null && dtStartVal.getMillis() > dtEndVal.getMillis()) {
			throw new InvalidDataValueException("Start DateTime is larger than the end DateTime");
		}
		
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(strReadFields);
			sbQuery.append(" from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" as o ");
			if (outJoins != null && !outJoins.isEmpty()) {
				sbQuery.append(outJoins);
			}
			sbQuery.append(" where o.OwnerAccountId=:OwnerAccountId");

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
			sbQuery.append(" ");
			sbQuery.append(getCurrentStatusQueryPart(currentStatuses));
			if (extraCondition != null && !extraCondition.isEmpty())
				sbQuery.append(" AND ").append(extraCondition);
			if (getFieldOrderForReadList() != null && !getFieldOrderForReadList().isEmpty())
				sbQuery.append(" order by ").append(getFieldOrderForReadList());
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

	// Get domain objects by a value that belongs the range [column start, column end].
	public <V extends Comparable<V>> List<T> findDomainObjectsByNumberInRangeColumns(int ownerAccountId, String outJoins,
			String colNameStart, String colNameEnd, V numVal, Set<String> currentStatuses, String extraCondition)
			throws MustOverrideException, InvalidDataValueException {

		String strReadFields = getFieldSelectionForRead();
		String strSqlTable  = getSqlTable();
		RowMapper<T> mapper = getRowMapper();
		if (strReadFields == null || strReadFields.isEmpty() ||
		    strSqlTable == null || strSqlTable.isEmpty() || mapper == null) {
			throw new MustOverrideException("Missing derived class override get functions");
		}
		if (colNameStart == null) {
			throw new InvalidDataValueException("Missing input colNameStart");
		}
		if (colNameEnd == null) {
			throw new InvalidDataValueException("Missing input colNameEnd");
		}
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(strReadFields);
			sbQuery.append(" from ");
			sbQuery.append(strSqlTable);
			sbQuery.append(" as o ");
			if (outJoins != null && !outJoins.isEmpty()) {
				sbQuery.append(outJoins);
			}
			sbQuery.append(" where o.OwnerAccountId=:OwnerAccountId and ");
			sbQuery.append(":NumVal >= ");
			sbQuery.append(colNameStart);
			sbQuery.append(" and ");
			sbQuery.append(":NumVal <= ");
			sbQuery.append(colNameEnd);
			sbQuery.append(" ");
			sbQuery.append(getCurrentStatusQueryPart(currentStatuses));
			if (extraCondition != null && !extraCondition.isEmpty())
				sbQuery.append(" AND ").append(extraCondition);
			if (getFieldOrderForReadList() != null && !getFieldOrderForReadList().isEmpty())
				sbQuery.append(" order by ").append(getFieldOrderForReadList());
			sbQuery.append(";");
			List<T> domainObjs = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).
						addValue("NumVal", numVal).addValue("NumVal", numVal),
					mapper);
			return domainObjs;
		}
		catch (Exception e) {
			System.out.println("LPJdbcGeneric.findDomainObjectsByNumberInRangeColumns Exception: " + e.getMessage());
			return null;
		}
	}

	// Add a domain object to the database and return the generated database id
	public R addDomainObject(T domainObj)
			throws MustOverrideException, DuplicateKeyException, Exception {
		if (domainObj == null) {
			throw new Exception("Missing input domainObj");
		}
		MapSqlParameterSource parameters = getDomainObjectMapSqlParameterSource(domainObj, true);
		if (parameters == null) {
			throw new MustOverrideException("Missing derived class override getDomainObjectMapSqlParameterSource");
		}
		try {
			// Insert the domain object to the database
			return (R)insertDomainObject.executeAndReturnKey(parameters);
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
		if (domainObj == null) {
			throw new Exception("Missing input domainObj");
		}
		String strUpdateFields = getFieldSelectionForUpdate();
		String strSqlTable = getSqlTable();
		if (strUpdateFields == null || strUpdateFields.isEmpty() ||
		    strSqlTable == null || strSqlTable.isEmpty()) {
			throw new MustOverrideException("Missing derived class override get functions");
		}
		MapSqlParameterSource parameters = getDomainObjectMapSqlParameterSource(domainObj, false);
		if (parameters == null) {
			throw new MustOverrideException("Missing derived class override getDomainObjectMapSqlParameterSource");
		}
		try {
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

	public int deleteDomainObject(int ownerAccountId, R id)
			throws MustOverrideException, Exception {
		// The derived class test if the id is less than or equal to zero
		if (ownerAccountId < 0) // || id <= 0)
			return 0;
		String strSqlTable = getSqlTable();
		if (strSqlTable == null) {
			throw new MustOverrideException("Missing derived class override getSqlTable");
		}
		try {
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
