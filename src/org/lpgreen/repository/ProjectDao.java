package org.lpgreen.repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.joda.time.DateTime;
import org.lpgreen.domain.Project;
import org.springframework.dao.DuplicateKeyException;

/**
 * ProjectDao is the interface for Project related entity's persistence layer
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

	// get all Project owned by a specific account id and currentPhases
	public List<Project> findProjectsByOwnerAccountId(int ownerAccountId, Set<String> currentPhases);

	// get a specific Project by a given id
	public Project findProjectById(int ownerAccountId, int id);

	// get a specific Project by a given ProjectCode
	public Project findProjectByProjectCode(int ownerAccountId, String projectCode);

	// get all Projects owned by a given name
	public List<Project> findProjectsByName(int ownerAccountId, String name);

	// get all Projects owned by a given project manager1 id
	public List<Project> findProjectsByProjectManager1Id(int ownerAccountId,
			int projectMgr1Id, Set<String> currentPhases);

	// get all Projects owned by a given project manager2 id
	public List<Project> findProjectsByProjectManager2Id(int ownerAccountId,
			int projectMgr2Id, Set<String> currentPhases);

	// get all Projects owned by a given customer account
	public List<Project> findProjectsByCustomerAccount(int ownerAccountId,
			int customerAccount, Set<String> currentPhases);

	// get all Projects owned by a given customer contact
	public List<Project> findProjectsByCustomerContact(int ownerAccountId,
			UUID customerContact, Set<String> currentPhases);

	// get all Projects owned by a given sponsor
	public List<Project> findProjectsBySponsor(int ownerAccountId,
			UUID sponsor, Set<String> currentPhases);

	// get all Projects owned by a given managing department id
	public List<Project> findProjectsByManagingDeptId(int ownerAccountId, int managingDeptId,
			Set<String> currentPhases);

	// get all Projects by start date range
	public List<Project> findProjectsByStartDateRange(int ownerAccountId, DateTime fromDate, DateTime toDate)
			throws Exception;

	// get all Projects by end date range
	public List<Project> findProjectsByEndDateRange(int ownerAccountId, DateTime fromDate, DateTime toDate)
			throws Exception;

	// get all Projects by a given parent project id
	public List<Project> findProjectsByParentProjectId(int ownerAccountId, int parentProjectId);

	// Add a Project. Return the generated id
	public int addProject(Project project) 
			throws DuplicateKeyException, Exception;

	// Save a the changes of an existing Project object. Return the # of record updated
	public int saveProject(Project project) 
			throws DuplicateKeyException, Exception;

	// Delete a Project object. Return the # of record deleted
	public int deleteProject(int ownerAccountId, int id) throws Exception;

}
