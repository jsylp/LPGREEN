package org.lpgreen.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.lpgreen.domain.LaborCostMethod;
import org.lpgreen.util.MustOverrideException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * JdbcLaborCostMethodDao is the JDBC implementation of the LaborCostMethodDao for LaborCostMethod related entity's persistence layer
 * 
 * Creation date: May. 07, 2013
 * Last modify date: May. 07, 2013
 * 
 * @author  Jiaxun Stephen Yu
 * @version 1.0
 */

@Repository
public class JdbcLaborCostMethodDao extends LPJdbcGeneric<LaborCostMethod, Integer> implements LaborCostMethodDao {

	public void setDataSource(DataSource dataSource)
			throws MustOverrideException {
		try {
			super.setDataSource(dataSource);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcLaborCostMethodDao.setDataSource Exception: " + e.getMessage());
			throw e;
		}
	}

	// o: the main object: this LaborCostMethod
	protected final static String fieldSelectionForReadLaborCostMethod =
			"o.Id,o.CostMethodCode,o.CostMethodType,o.Status,o.Description," +
			"o.UnitQuantity,o.UnitQuantityCost,o.UnitQuantityUMCode,o.UserDefinedUMCode," +
			"o.Multiplier,o.RangeStartQuantity,o.RangeEndQuantity," +
			"o.OTStartWeekdayTime,o.OTDuration,o.OwnerId,o.OwnerAccountId";

	// outer join is not used in this Dao
	protected final static String outJoins = null;

	// field selection for update
	protected final static String fieldSetForUpdateLaborCostMethod = 
			"CostMethodCode=:CostMethodCode,CostMethodType=:CostMethodType,Status=:Status," +
			"Description=:Description,UnitQuantity=:UnitQuantity,UnitQuantityCost=:UnitQuantityCost," +
			"UnitQuantityUMCode=:UnitQuantityUMCode,UserDefinedUMCode=:UserDefinedUMCode," +
			"Multiplier=:Multiplier,RangeStartQuantity=:RangeStartQuantity,RangeEndQuantity=:RangeEndQuantity," +
			"OTStartWeekdayTime=:OTStartWeekdayTime,OTDuration=:OTDuration,OwnerAccountId=:OwnerAccountId";

	// RowMapper class
	private static class LaborCostMethodMapper implements RowMapper<LaborCostMethod> {

		public LaborCostMethod mapRow(ResultSet rs, int rowNum) throws SQLException {
			LaborCostMethod laborCostMethod = new LaborCostMethod();

			laborCostMethod.setId(rs.getInt("Id"));
			laborCostMethod.setCostMethodCode(rs.getString("CostMethodCode"));
			laborCostMethod.setCostMethodType(rs.getString("CostMethodType"));
			laborCostMethod.setStatus(rs.getString("Status"));
			laborCostMethod.setDescription(rs.getString("Description"));

			double dblVal = rs.getDouble("UnitQuantity");
			if (dblVal > 0) {
				laborCostMethod.setUnitQuantity(dblVal);
			}
			else {
				laborCostMethod.setUnitQuantity(0);
			}
			dblVal = rs.getDouble("UnitQuantityCost");
			if (dblVal > 0) {
				laborCostMethod.setUnitQuantityCost(dblVal);
			}
			else {
				laborCostMethod.setUnitQuantityCost(0);
			}

			laborCostMethod.setUnitQuantityUMCode(rs.getString("UnitQuantityUMCode"));
			laborCostMethod.setUserDefinedUMCode(rs.getString("UserDefinedUMCode"));

			dblVal = rs.getDouble("Multiplier");
			if (dblVal > 0) {
				laborCostMethod.setMultiplier(dblVal);
			}
			else {
				laborCostMethod.setMultiplier(0);
			}

			int intVal = rs.getInt("RangeStartQuantity");
			if (intVal > 0) {
				laborCostMethod.setRangeStartQuantity(intVal);
			}
			else {
				laborCostMethod.setRangeStartQuantity(0);
			}
			intVal = rs.getInt("RangeEndQuantity");
			if (intVal > 0) {
				laborCostMethod.setRangeEndQuantity(intVal);
			}
			else {
				laborCostMethod.setRangeEndQuantity(0);
			}

			laborCostMethod.setOTStartWeekdayTime(rs.getString("OTStartWeekdayTime"));
			laborCostMethod.setOTDuration(rs.getString("OTDuration"));
			laborCostMethod.setOwnerAccountId(rs.getInt("OwnerAccountId"));
			return laborCostMethod;
		}
	}

	// Read detailed data of a LaborCostMetod. The input LaborCostMethod may have the header only.
	@Override
	public LaborCostMethod readLaborCostMethodDetail(LaborCostMethod laborCostMethod) {
		if (laborCostMethod == null)
			return null;

		// To do: whatever information in addition to the header

		return laborCostMethod;
	}

	// Override to return the SQL table name
	protected String getSqlTable() {
		return "LaborCostMethod";
	}

	// Override to return the field selection for read
	protected String getFieldSelectionForRead() {
		return fieldSelectionForReadLaborCostMethod;
	}

	// Override to return the filed selection for update
	protected String getFieldSelectionForUpdate() {
		return fieldSetForUpdateLaborCostMethod;
	}

	// Overridden to return the name of the current status column
	protected String getCurrentStatusColumn() {
		return "Status";
	}

	// Override to return the field order for read a list of objects
	protected String getFieldOrderForReadList() {
		return "o.OwnerAccountId, o.CostMethodCode ASC";
	}

	// Override to return the RowMapper
	protected RowMapper<LaborCostMethod> getRowMapper() {
		return new LaborCostMethodMapper();
	}

	// Override to return MapSqlParameterSource for creating LaborCostMethod
	protected MapSqlParameterSource getDomainObjectMapSqlParameterSource(LaborCostMethod laborCostMethod, boolean bNew) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		// Persist data defined in UserObject
		parameters.addValue("OwnerId", laborCostMethod.getOwnerId());
		parameters.addValue("OwnerAccountId", laborCostMethod.getOwnerAccountId());

		if (!bNew) {
			if (laborCostMethod.getId() > 0)
				parameters.addValue("Id", laborCostMethod.getId());	// auto generated when insert a LaborCostMethod, use it as the primary key when update it
			else
				parameters.addValue("Id", null);
		}
		parameters.addValue("CostMethodCode", laborCostMethod.getCostMethodCode());
		parameters.addValue("CostMethodType", laborCostMethod.getCostMethodType());
		parameters.addValue("Status", laborCostMethod.getStatus());

		if (laborCostMethod.getDescription() != null && !laborCostMethod.getDescription().isEmpty())
			parameters.addValue("Description", laborCostMethod.getDescription());
		else
			parameters.addValue("Description", null);

		if (laborCostMethod.getUnitQuantity() > 0)
			parameters.addValue("UnitQuantity", laborCostMethod.getUnitQuantity());
		else
			parameters.addValue("UnitQuantity", null);
		if (laborCostMethod.getUnitQuantityCost() > 0)
			parameters.addValue("UnitQuantityCost", laborCostMethod.getUnitQuantityCost());
		else
			parameters.addValue("UnitQuantityCost", null);

		if (laborCostMethod.getUnitQuantityUMCode() != null && !laborCostMethod.getUnitQuantityUMCode().isEmpty())
			parameters.addValue("UnitQuantityUMCode", laborCostMethod.getUnitQuantityUMCode());
		else
			parameters.addValue("UnitQuantityUMCode", null);
		if (laborCostMethod.getUserDefinedUMCode() != null && !laborCostMethod.getUserDefinedUMCode().isEmpty())
			parameters.addValue("UserDefinedUMCode", laborCostMethod.getUserDefinedUMCode());
		else
			parameters.addValue("UserDefinedUMCode", null);

		if (laborCostMethod.getMultiplier() > 0)
			parameters.addValue("Multiplier", laborCostMethod.getMultiplier());
		else
			parameters.addValue("Multiplier", null);

		if (laborCostMethod.getRangeStartQuantity() > 0)
			parameters.addValue("RangeStartQuantity", laborCostMethod.getRangeStartQuantity());
		else
			parameters.addValue("RangeStartQuantity", null);
		if (laborCostMethod.getRangeEndQuantity() > 0)
			parameters.addValue("RangeEndQuantity", laborCostMethod.getRangeEndQuantity());
		else
			parameters.addValue("RangeEndQuantity", null);

		if (laborCostMethod.getOTStartWeekdayTime() != null && !laborCostMethod.getOTStartWeekdayTime().isEmpty())
			parameters.addValue("OTStartWeekdayTime", laborCostMethod.getOTStartWeekdayTime());
		else
			parameters.addValue("OTStartWeekdayTime", null);
		if (laborCostMethod.getOTDuration() != null && !laborCostMethod.getOTDuration().isEmpty())
			parameters.addValue("OTDuration", laborCostMethod.getOTDuration());
		else
			parameters.addValue("OTDuration", null);
		return parameters;
	}

	protected List<LaborCostMethod> getQueryDetail(List<LaborCostMethod> laborCostMethods, boolean headerOnly) {
		if (laborCostMethods != null && laborCostMethods.size() > 0 && !headerOnly) {
			Iterator<LaborCostMethod> it = laborCostMethods.iterator();
			while (it.hasNext()) {
				LaborCostMethod laborCostMethod = it.next();
				this.readLaborCostMethodDetail(laborCostMethod);
			}
		}
		return laborCostMethods;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// LaborCostMethod related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	// get a specific LaborCostMethod by a given database id
	@Override
	public LaborCostMethod findLaborCostMethodById(int id) {
		try {
			LaborCostMethod laborCostMethod = super.findDomainObjectById(id, outJoins);
			if (laborCostMethod != null) {
				laborCostMethod = this.readLaborCostMethodDetail(laborCostMethod);
			}
			return laborCostMethod;
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcLaborCostMethodDao.findLaborCostMethodById MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcLaborCostMethodDao.findLaborCostMethodById Exception: " + e.getMessage());
			return null;
		}
	}

	// get all LaborCostMethods owned by a specific owner account id
	@Override
	public List<LaborCostMethod> findLaborCostMethodsByOwnerAccountId(int ownerAccountId,
			Set<String> currentStatuses, boolean headerOnly) {
		if (ownerAccountId <= 0)
			return null;
		try {
			List<LaborCostMethod> laborCostMethods = super.findDomainObjectsByOwnerAccountId(ownerAccountId, outJoins,
					currentStatuses, null);
			return getQueryDetail(laborCostMethods, headerOnly);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcLaborCostMethodDao.findLaborCostMethodsByOwnerAccountId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcLaborCostMethodDao.findLaborCostMethodsByOwnerAccountId Exception: " + e.getMessage());
			return null;
		}
	}

	// get a specific LaborCostMethod by CostMethodCode
	@Override
	public LaborCostMethod findLaborCostMethodByCostMethodCode(int ownerAccountId,
			String costMethodCode, boolean caseSensitive, Set<String> currentStatuses, boolean headerOnly) {
		try {
			List<LaborCostMethod> laborCostMethods = super.findDomainObjectsByStringColumnVal(ownerAccountId, outJoins,
					"o.CostMethodCode", costMethodCode, caseSensitive, currentStatuses, null);
			if (laborCostMethods.isEmpty())
				return null;
			if (laborCostMethods.size() > 1) {
				System.out.println("JdbcLaborCostMethodDao.findLaborCostMethodByCostMethodCode (" + costMethodCode +
						") error: more than one laborCostMethods returned.");
				return null;
			}
			getQueryDetail(laborCostMethods, headerOnly);
			return laborCostMethods.get(0);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcLaborCostMethodDao.findLaborCostMethodByCostMethodCode MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcLaborCostMethodDao.findLaborCostMethodByCostMethodCode Exception: " + e.getMessage());
			return null;
		}
	}

	// get all LaborCostMethods by a given CostMethodType
	@Override
	public List<LaborCostMethod> findLaborCostMethodsByCostMethodType(int ownerAccountId,
			String costMethodType, boolean caseSensitive, Set<String> currentStatuses, boolean headerOnly) {
		try {
			List<LaborCostMethod> laborCostMethods = super.findDomainObjectsByStringColumnVal(ownerAccountId, outJoins,
					"o.CostMethodType", costMethodType, caseSensitive, currentStatuses, null);
			return getQueryDetail(laborCostMethods, headerOnly);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcLaborCostMethodDao.findLaborCostMethodsByCostMethodType MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcLaborCostMethodDao.findLaborCostMethodsByCostMethodType Exception: " + e.getMessage());
			return null;
		}
	}

	// get all LaborCostMethods by a given UnitQuantity range
	@Override
	public List<LaborCostMethod> findLaborCostMethodsByUnitQuantityRange(int ownerAccountId,
			double startUnitQuantity, double endUnitQuantity, Set<String> currentStatuses, boolean headerOnly) {
		try {
			List<LaborCostMethod> laborCostMethods = super.<Double>findDomainObjectsByNumberColumnRange(ownerAccountId,
					outJoins, "o.UnitQuantity", startUnitQuantity, endUnitQuantity, currentStatuses, null);
			return getQueryDetail(laborCostMethods, headerOnly);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcLaborCostMethodDao.findLaborCostMethodsByUnitQuantityRange MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcLaborCostMethodDao.findLaborCostMethodsByUnitQuantityRange Exception: " + e.getMessage());
			return null;
		}
	}

	// get all LaborCostMethods by a given UnitQuantityCost range
	@Override
	public List<LaborCostMethod> findLaborCostMethodsByUnitQuantityCostRange(int ownerAccountId,
			double startUnitQuantityCost, double endUnitQuantityCost, Set<String> currentStatuses, boolean headerOnly) {
		try {
			List<LaborCostMethod> laborCostMethods = super.<Double>findDomainObjectsByNumberColumnRange(ownerAccountId,
					outJoins, "o.UnitQuantityCost", startUnitQuantityCost, endUnitQuantityCost, currentStatuses, null);
			return getQueryDetail(laborCostMethods, headerOnly);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcLaborCostMethodDao.findLaborCostMethodsByUnitQuantityCostRange MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcLaborCostMethodDao.findLaborCostMethodsByUnitQuantityCostRange Exception: " + e.getMessage());
			return null;
		}
	}

	// get all LaborCostMethods by a given UnitQuantityUMCode
	@Override
	public List<LaborCostMethod> findLaborCostMethodsByUnitQuantityUMCode(int ownerAccountId,
			String unitQuantityUMCode, boolean caseSensitive, Set<String> currentStatuses, boolean headerOnly) {
		try {
			List<LaborCostMethod> laborCostMethods = super.findDomainObjectsByStringColumnVal(ownerAccountId, outJoins,
					"o.UnitQuantityUMCode", unitQuantityUMCode, caseSensitive, currentStatuses, null);
			return getQueryDetail(laborCostMethods, headerOnly);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcLaborCostMethodDao.findLaborCostMethodsByUnitQuantityUMCode MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcLaborCostMethodDao.findLaborCostMethodsByUnitQuantityUMCode Exception: " + e.getMessage());
			return null;
		}
	}

	// get all LaborCostMethods by a given UserDefinedUMCode
	@Override
	public List<LaborCostMethod> findLaborCostMethodsByUserDefinedUMCode(int ownerAccountId,
			String userDefinedUMCode, boolean caseSensitive, Set<String> currentStatuses, boolean headerOnly) {
		try {
			List<LaborCostMethod> laborCostMethods = super.findDomainObjectsByStringColumnVal(ownerAccountId, outJoins,
					"o.UserDefinedUMCode", userDefinedUMCode, caseSensitive, currentStatuses, null);
			return getQueryDetail(laborCostMethods, headerOnly);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcLaborCostMethodDao.findLaborCostMethodsByUserDefinedUMCode MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcLaborCostMethodDao.findLaborCostMethodsByUserDefinedUMCode Exception: " + e.getMessage());
			return null;
		}
	}

	// get all LaborCostMethods by a given Multiplier range
	@Override
	public List<LaborCostMethod> findLaborCostMethodsByMultiplierRange(int ownerAccountId,
			double startMultiplier, double endMultiplier, Set<String> currentStatuses, boolean headerOnly) {
		try {
			List<LaborCostMethod> laborCostMethods = super.<Double>findDomainObjectsByNumberColumnRange(ownerAccountId,
					outJoins, "o.Multiplier", startMultiplier, endMultiplier, currentStatuses, null);
			return getQueryDetail(laborCostMethods, headerOnly);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcLaborCostMethodDao.findLaborCostMethodsByMultiplierRange MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcLaborCostMethodDao.findLaborCostMethodsByMultiplierRange Exception: " + e.getMessage());
			return null;
		}
	}

	// get all LaborCostMethods where the input value is in the range [RangeStartQuantity, RangeEndQuantity]
	@Override
	public List<LaborCostMethod> findLaborCostMethodsByValueInQuantityRange(int ownerAccountId,
			int valQuantity, Set<String> currentStatuses, boolean headerOnly) {
		try {
			List<LaborCostMethod> laborCostMethods = super.<Integer>findDomainObjectsByNumberInRangeColumns(ownerAccountId, outJoins,
					"o.RangeStartQuantity", "o.RangeEndQuantity", valQuantity, currentStatuses, null);
			return getQueryDetail(laborCostMethods, headerOnly);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcLaborCostMethodDao.findLaborCostMethodsByValueInQuantityRange MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcLaborCostMethodDao.findLaborCostMethodsByValueInQuantityRange Exception: " + e.getMessage());
			return null;
		}
	}

	// Add a LaborCostMethod. Return the generated database id
	@Override
	public int addLaborCostMethod(LaborCostMethod laborCostMethod) 
			throws DuplicateKeyException, Exception {
		if (laborCostMethod == null)
			throw new Exception("Missing input laborCostMethod");
		try {
			// insert LaborCostMethod record
			int retId = addDomainObject(laborCostMethod);
			laborCostMethod.setId(retId);
			return retId;
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcLaborCostMethodDao.addLaborCostMethod MustOverrideException: " + e.getMessage());
			return -1;
		}
		catch (DuplicateKeyException e) {
			System.out.println("JdbcLaborCostMethodDao.addLaborCostMethod DuplicateKeyException: " + e.getMessage());
			throw e;
		}
		catch (Exception e) {
			System.out.println("JdbcLaborCostMethodDao.addLaborCostMethod Exception: " + e.getMessage());
			throw e;
		}
	}

	// Save changes of an existing LaborCostMethod object. Return the # of records updated
	@Override
	public int saveLaborCostMethod(LaborCostMethod laborCostMethod) 
			throws DuplicateKeyException, Exception {
		try {
			int numObjectUpdated = saveDomainObject(laborCostMethod);
			if (numObjectUpdated == 0) {
				throw new Exception("Fail to update the LaborCostMethod obejct");
			}
			return numObjectUpdated;
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcLaborCostMethodDao.saveLaborCostMethod MustOverrideException: " + e.getMessage());
			return -1;
		}
		catch (DuplicateKeyException e) {
			System.out.println("JdbcLaborCostMethodDao.saveLaborCostMethod DuplicateKeyException: " + e.getMessage());
			throw e;
		}
		catch (Exception e) {
			System.out.println("JdbcLaborCostMethodDao.saveLaborCostMethod Exception: " + e.getMessage());
			throw e;
		}
	}

	// Delete a LaborCostMethod object. Return the # of record deleted
	@Override
	public int deleteLaborCostMethod(int ownerAccountId, int id)
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
