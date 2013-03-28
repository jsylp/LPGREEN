package org.lpgreen.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.joda.time.DateTime;
import org.lpgreen.util.InvalidDataValueException;
import org.lpgreen.util.MissingRequiredDataException;
import org.lpgreen.domain.Project;
import org.springframework.dao.DuplicateKeyException;

/**
 * ProjectManager is the interface for all Project related objects
 * 
 * Creation date: Mar. 13, 2013
 * Last modify date: Mar. 13, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */
public interface ProjectManager {

	////////////////////////////////////////////
	// Project related methods
	////////////////////////////////////////////
	
	// get all Projects owned by a specific account id
	public List<Project> findAllSiteProjects(int ownerAccountId, Set<String> currentPhases)
			throws Exception;
	
	// get a specific Project by a given id
	public Project findProjectById(int ownerAccountId, int id)
			throws Exception;

	// get a specific Project by a given project code
	public List<Project> findProjectsByProjectCode(int ownerAccountId, String projectCode)
			throws Exception;

	// get a specific Project by a given name
	public List<Project> findProjectByName(int ownerAccountId, String name)
			throws Exception;

	// get all Projects owned by a given project manager1 id
	public List<Project> findProjectsByProjectManager1Id(int ownerAccountId,
			int projectMgr1Id, Set<String> currentPhases)
			throws Exception;

	// get all Projects owned by a given project manager2 id
	public List<Project> findProjectsByProjectManager2Id(int ownerAccountId,
			int projectMgr2Id, Set<String> currentPhases)
			throws Exception;

	// get all Projects owned by a given customer account
	public List<Project> findProjectsByCustomerAccount(int ownerAccountId,
			int customerAccount, Set<String> currentPhases)
			throws Exception;

	// get all Projects owned by a given customer contact
	public List<Project> findProjectsByCustomerContact(int ownerAccountId,
			UUID customerContact, Set<String> currentPhases)
			throws Exception;

	// get all Projects owned by a given sponsor
	public List<Project> findProjectsBySponsor(int ownerAccountId,
			UUID sponsor, Set<String> currentPhases)
			throws Exception;

	// get all Projects owned by a given managing department id
	public List<Project> findProjectsByManagingDeptId(int ownerAccountId,
			int managingDeptId,	Set<String> currentPhases)
			throws Exception;

	// get all Projects by budget range
	public List<Project> findProjectsByBudgetRange(int ownerAccountId,
			double fromAmount, double toAmount, String currencyCode)
			throws Exception;

	// get all Projects by start date range
	public List<Project> findProjectsByStartDateRange(int ownerAccountId,
			DateTime fromDate, DateTime toDate)
			throws Exception;

	// get all Projects by end date range
	public List<Project> findProjectsByEndDateRange(int ownerAccountId,
			DateTime fromDate, DateTime toDate)
			throws Exception;

	// get all Project owned by a specific account id and have the same parent project id
	public List<Project> findAllSiteProjectsByParentProject(int ownerAccountId,
			int parentProjectId)
			throws Exception;

	// Create services
	public Project createProject(UUID userId, int userOwnerAccountId, Project project) 
			throws MissingRequiredDataException, InvalidDataValueException, DuplicateKeyException, Exception;

	// Update services
	public Project updateProject(UUID userId, Project project) 
			throws MissingRequiredDataException, InvalidDataValueException, DuplicateKeyException, Exception;

	// Delete services
	public void deleteProject(UUID userId, int ownerAccountId, int id)
			throws MissingRequiredDataException, Exception;

	// Export/import to/from CSV file
	public void exportProjectsToCSV(List<Project> projects, OutputStream os)
			throws Exception;
	public List<Project> importProjectsFromCSV(UUID userId, int userOwnerAccountId, InputStream is, boolean bOverrideDup)
			throws Exception;
}
