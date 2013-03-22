package org.lpgreen.repository;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * JdbcGeneric is the helper class to implement generic JDBC functions
 * 
 * Creation date: Mar. 21, 2013
 * Last modify date: Mar. 21, 2013
 * 
 * @author  Jiaxun Stephen Yu
 * @version 1.0
 */

public class JdbcGeneric<T> {

/*
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertProject;
	public void setDataSource(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		insertProject = new SimpleJdbcInsert(dataSource).withTableName("Project").usingGeneratedKeyColumns("id");
	}

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

*/
	// Override by the derived class to return NamedParameterJdbcTemplate
	protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return null;
	}

	// Override by the derived class to return field selection for read
	protected String getFieldSelectionForRead() {
		return null;
	}

	// Override by the derived class to return field selection for update
	protected String getFieldSelectionForUpdate() {
		return null;
	}

	// Override by the derived class to return RowMapper
	protected RowMapper<T> getRowMapper() {
		return null;
	}

	// Override by the derived class to return Sql table name
	protected String getSqlTable() {
		return null;
	}

	// get all Project owned by a specific account id
	public List<T> findRowsByOwnerAccountId(int ownerAccountId) {
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(getFieldSelectionForRead());
			sbQuery.append(" from " + getSqlTable() + " as o where OwnerAccountId=:OwnerAccountId");
			List<T> domainObjs = getNamedParameterJdbcTemplate().query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId),
					getRowMapper());
			return domainObjs;
		}
		catch (Exception e) {
			System.out.println("JdbcGeneric.findRowsByOwnerAccountId Exception: " + e.getMessage());
			return null;
		}
	}

}
