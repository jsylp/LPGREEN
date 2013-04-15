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

	// get a specific Project by a given database id
	public Project findProjectById(int id);

	// read detailed data of a project. The input project may have the header only.
	public Project readProjectDetail(Project project);

	// get all Projects owned by a specific owner account id
	public List<Project> findProjectsByOwnerAccountId(int ownerAccountId,
			Set<String> currentPhases, boolean headerOnly);

	// get all Projects by ProjectCode
	public List<Project> findProjectsByProjectCode(int ownerAccountId,
			String projectCode, Set<String> currentPhases, boolean headerOnly);

	// get all Projects owned by a given Name
	public List<Project> findProjectsByName(int ownerAccountId,
			String name, Set<String> currentPhases, boolean headerOnly);

	// get all Projects owned by a given ProjectManager1Id
	public List<Project> findProjectsByProjectManager1Id(int ownerAccountId,
			int projectMgr1Id, Set<String> currentPhases, boolean headerOnly);

	// get all Projects owned by a given ProjectManager2Id
	public List<Project> findProjectsByProjectManager2Id(int ownerAccountId,
			int projectMgr2Id, Set<String> currentPhases, boolean headerOnly);

	// get all Projects owned by a given CustomerAccountId
	public List<Project> findProjectsByCustomerAccountId(int ownerAccountId,
			int customerAccountId, Set<String> currentPhases, boolean headerOnly);

	// get all Projects owned by a given CustomerContactId
	public List<Project> findProjectsByCustomerContactId(int ownerAccountId,
			UUID customerContactId, Set<String> currentPhases, boolean headerOnly);

	// get all Projects owned by a given SponsorId
	public List<Project> findProjectsBySponsorId(int ownerAccountId,
			UUID sponsorId, Set<String> currentPhases, boolean headerOnly);

	// get all Projects owned by a given ManagingDeptId
	public List<Project> findProjectsByManagingDeptId(int ownerAccountId,
			int managingDeptId, Set<String> currentPhases, boolean headerOnly);

	// get all Projects by the StartDate range
	public List<Project> findProjectsByStartDateRange(int ownerAccountId,
			DateTime fromDate, DateTime toDate, Set<String> currentPhases, boolean headerOnly);

	// get all Projects by the EndDate range
	public List<Project> findProjectsByEndDateRange(int ownerAccountId,
			DateTime fromDate, DateTime toDate, Set<String> currentPhases, boolean headerOnly);

	// get all Projects owned by a given ParentProjectId
	public List<Project> findProjectsByParentProjectId(int ownerAccountId,
			int parentProjectId, Set<String> currentPhases, boolean headerOnly);

	// Add a Project. Return the generated database id
	public int addProject(Project project) 
			throws DuplicateKeyException, Exception;

	// Save changes of an existing Project object. Return the # of records updated
	public int saveProject(Project project) 
			throws DuplicateKeyException, Exception;

	// Delete a Project object. Return the # of record deleted
	public int deleteProject(int ownerAccountId, int id) throws Exception;

}
