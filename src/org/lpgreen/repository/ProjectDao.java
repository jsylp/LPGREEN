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

	// get all Projects owned by a specific owner account id and current phases
	public List<Project> findProjectsByOwnerAccountId(int ownerAccountId, Set<String> currentStatuses);

	// get a specific Project by a given database id
	public Project findProjectById(int id);

	// get all Projects by ProjectCode
	public List<Project> findProjectsByProjectCode(int ownerAccountId, String projectCode);

	// get all Projects owned by a given Name
	public List<Project> findProjectsByName(int ownerAccountId, String name);

	// get all Projects owned by a given ProjectManager1Id
	public List<Project> findProjectsByProjectManager1Id(int ownerAccountId,
			int projectMgr1Id, Set<String> currentStatuses);

	// get all Projects owned by a given ProjectManager2Id
	public List<Project> findProjectsByProjectManager2Id(int ownerAccountId,
			int projectMgr2Id, Set<String> currentStatuses);

	// get all Projects owned by a given CustomerAccount
	public List<Project> findProjectsByCustomerAccount(int ownerAccountId,
			int customerAccount, Set<String> currentStatuses);

	// get all Projects owned by a given CustomerContact
	public List<Project> findProjectsByCustomerContact(int ownerAccountId,
			UUID customerContact, Set<String> currentStatuses);

	// get all Projects owned by a given Sponsor
	public List<Project> findProjectsBySponsor(int ownerAccountId,
			UUID sponsor, Set<String> currentStatuses);

	// get all Projects owned by a given ManagingDeptId
	public List<Project> findProjectsByManagingDeptId(int ownerAccountId, int managingDeptId,
			Set<String> currentStatuses);

	// get all Projects by the StartDate range
	public List<Project> findProjectsByStartDateRange(int ownerAccountId, DateTime fromDate, DateTime toDate);

	// get all Projects by the EndDate range
	public List<Project> findProjectsByEndDateRange(int ownerAccountId, DateTime fromDate, DateTime toDate);

	// get all Projects owned by a given ParentProjectId
	public List<Project> findProjectsByParentProjectId(int ownerAccountId, int parentProjectId);

	// Add a Project. Return the generated database id
	public int addProject(Project project) 
			throws DuplicateKeyException, Exception;

	// Save changes of an existing Project object. Return the # of records updated
	public int saveProject(Project project) 
			throws DuplicateKeyException, Exception;

	// Delete a Project object. Return the # of record deleted
	public int deleteProject(int ownerAccountId, int id) throws Exception;

}
