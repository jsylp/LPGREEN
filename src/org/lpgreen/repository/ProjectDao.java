package org.lpgreen.repository;

import java.util.List;
import org.joda.time.DateTime;

import org.lpgreen.domain.Project;
import org.springframework.dao.DuplicateKeyException;

/**
 * ProjecttDao is the interface for Project related entity's persistence layer
 * 
 * Creation date: Mar. 06, 2013
 * Last modify date: Mar. 06, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

public interface ProjectDao {

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// Project related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	// get all Project owned by a specific account id
	public List<Project> findProjectsByOwerAccountId(int ownerAccountId);

	// get a specific Project by a given id
	public Project findProjectById(int ownerAccountId, int id);

	// get a specific Project by a given ProjectCode
	public Project findProjectByProjectCode(int ownerAccountId, String projectCode);

	// get all Projects owned by a given name
	public List<Project> findProjectsByName(int ownerAccountId, String name);

	// get all Projects owned by a given project manager1 id
	public List<Project> findProjectsByProjectManager1Id(int ownerAccountId, int projectMgr1Id);

	// get all Projects owned by a given project manager2 id
	public List<Project> findProjectsByProjectManager2Id(int ownerAccountId, int projectMgr2Id);

	// get all Projects owned by a given customer account
	public List<Project> findProjectsByCustomerAccount(int ownerAccountId, int customerAccount);

	// get all Projects owned by a given managing department id
	public List<Project> findProjectsByManagingDeptId(int ownerAccountId, int managingDeptId);

	// get all Projects by date range
	public List<Project> findProjectsByDateRange(int ownerAccountId, DateTime startDate, DateTime endDate);

	// Add a Project. Return the generated id
	public int addProject(Project project) 
			throws DuplicateKeyException, Exception;

	// Save a the changes of an existing Project object. Return the # of record updated
	public int saveProject(Project project) 
			throws DuplicateKeyException, Exception;

	// Delete a Project object. Return the # of record deleted
	public int deleteProject(int ownerAccountId, int id) throws Exception;

}
