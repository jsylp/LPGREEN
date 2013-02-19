package org.lpgreen.repository;

import java.util.List;

import org.lpgreen.domain.OperationRight;
import org.springframework.dao.DuplicateKeyException;

/**
 * OperationRightDao is the interface for OperationRight related entity's persistence layer
 * 
 * Creation date: Jan. 13, 2013
 * Last modify date: Feb. 15, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

public interface OperationRightDao {

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// OperationRight related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	// get all OperationRight owned by a specific account id
	public List<OperationRight> findAllSiteOperationRights(int ownerAccountId);

	// get a specific OperationRight by a given id
	public OperationRight findOperationRightById(int ownerAccountId, int id);

	// get a specific OperationRight by a given name
	public OperationRight findOperationRightByName(int ownerAccountId, String name);

	// Add an OperationRight. Return the generated id
	public int addOperationRight(OperationRight opRight) 
			throws DuplicateKeyException, Exception;

	// Save a the changes of an existing OperationRight object. Return the # of record updated
	public int saveOperationRight(OperationRight opRight) 
			throws DuplicateKeyException, Exception;

	// Delete an OperationRight object. Return the # of record deleted
	public int deleteOperationRight(int ownerAccountId, int id) throws Exception;
	
}
