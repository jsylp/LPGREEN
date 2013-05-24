package org.lpgreen.repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.joda.time.DateTime;
import org.lpgreen.domain.LaborCostMethod;
import org.springframework.dao.DuplicateKeyException;

/**
 * LaborCostMethodDao is the interface for LaborCostMethod related entity's persistence layer
 * 
 * Creation date: May. 07, 2013
 * Last modify date: May. 07, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

public interface LaborCostMethodDao {

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// LaborCostMethod related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	// get a specific LaborCostMethod by a given database id
	public LaborCostMethod findLaborCostMethodById(int id);

	// read detailed data of a project. The input project may have the header only.
	public LaborCostMethod readLaborCostMethodDetail(LaborCostMethod project);

	// get all LaborCostMethods owned by a specific owner account id
	public List<LaborCostMethod> findLaborCostMethodsByOwnerAccountId(int ownerAccountId,
			Set<String> currentStatuses, boolean headerOnly);

	// get a specific LaborCostMethod by CostMethodCode
	public LaborCostMethod findLaborCostMethodByCostMethodCode(int ownerAccountId,
			String costMethodCode, boolean caseSensitive, Set<String> currentStatuses, boolean headerOnly);

	// get all LaborCostMethods by a given CostMethodType
	public List<LaborCostMethod> findLaborCostMethodsByCostMethodType(int ownerAccountId,
			String costMethodType, boolean caseSensitive, Set<String> currentStatuses, boolean headerOnly);

	// get all LaborCostMethods by a given UnitQuantity range
	public List<LaborCostMethod> findLaborCostMethodsByUnitQuantityRange(int ownerAccountId,
			double startUnitQuantity, double endUnitQuantity, Set<String> currentStatuses, boolean headerOnly);

	// get all LaborCostMethods by a given UnitQuantityCost range
	public List<LaborCostMethod> findLaborCostMethodsByUnitQuantityCostRange(int ownerAccountId,
			double startUnitQuantityCost, double endUnitQuantityCost, Set<String> currentStatuses, boolean headerOnly);

	// get all LaborCostMethods by a given UnitQuantityUMCode
	public List<LaborCostMethod> findLaborCostMethodsByUnitQuantityUMCode(int ownerAccountId,
			String unitQuantityUMCode, boolean caseSensitive, Set<String> currentStatuses, boolean headerOnly);

	// get all LaborCostMethods by a given UserDefinedUMCode
	public List<LaborCostMethod> findLaborCostMethodsByUserDefinedUMCode(int ownerAccountId,
			String userDefinedUMCode, boolean caseSensitive, Set<String> currentStatuses, boolean headerOnly);

	// get all LaborCostMethods by a given Multiplier range
	public List<LaborCostMethod> findLaborCostMethodsByMultiplierRange(int ownerAccountId,
			double startMultiplier, double endMultiplier, Set<String> currentStatuses, boolean headerOnly);

	// get all LaborCostMethods where the input value is in the range [RangeStartQuantity, RangeEndQuantity]
	public List<LaborCostMethod> findLaborCostMethodsByValueInQuantityRange(int ownerAccountId,
			int valQuantity, Set<String> currentStatuses, boolean headerOnly);

	// Add a LaborCostMethod. Return the generated database id
	public int addLaborCostMethod(LaborCostMethod project) 
			throws DuplicateKeyException, Exception;

	// Save changes of an existing LaborCostMethod object. Return the # of records updated
	public int saveLaborCostMethod(LaborCostMethod project) 
			throws DuplicateKeyException, Exception;

	// Delete a LaborCostMethod object. Return the # of record deleted
	public int deleteLaborCostMethod(int ownerAccountId, int id) throws Exception;

}
