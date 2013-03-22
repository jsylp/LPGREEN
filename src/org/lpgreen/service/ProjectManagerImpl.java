package org.lpgreen.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.joda.time.DateTime;
import org.lpgreen.domain.Project;
import org.lpgreen.repository.ProjectDao;
import org.lpgreen.util.InvalidDataValueException;
import org.lpgreen.util.MissingRequiredDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

/**
 * ProjectManagerImpl is the implementation of the interface ProjectManager for all Department related objects. 
 * It provides Request CRUD services.
 * CRUD: Create, Read, Update, Delete
 * 
 * Creation date: Mar. 13, 2013
 * Last modify date: Mar. 13, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */
public class ProjectManagerImpl implements ProjectManager {

	// Logger for this class and subclasses
	//protected final Log logger = LogFactory.getLog(getClass());

	private ProjectDao projectDao;
	@Autowired
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}
	
	////////////////////////////////////////////
	// Department related methods
	////////////////////////////////////////////
	
	// get all Projects owned by a specific account id
	@Override
	public List<Project> findAllSiteProjects(int ownerAccountId, Set<String> currentPhases)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (currentPhases == null || currentPhases.isEmpty()) {
			throw new Exception("Invalud input currentPhases");
		}
		List<Project> projects = projectDao.findProjectsByOwnerAccountId(ownerAccountId, currentPhases);
		if (projects != null && projects.size() == 0)
			projects = null;
		return projects;
	}

	// get a specific Project by a given id
	@Override
	public Project findProjectById(int ownerAccountId, int id)
			throws Exception {
		if (ownerAccountId <= 0 || id <= 0) {
			throw new Exception("Invalud input ownerAccountId or id");
		}
		return projectDao.findProjectById(ownerAccountId, id);
	}

	// get a specific Project by a given project code
	@Override
	public Project findProjectByProjectCode(int ownerAccountId, String projectCode)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (projectCode == null) {
			throw new Exception("Invalud input projectCode");
		}
		return projectDao.findProjectByProjectCode(ownerAccountId, projectCode);
	}

	// get a specific Project by a given name
	@Override
	public List<Project> findProjectByName(int ownerAccountId, String name)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (name == null) {
			throw new Exception("Invalud input name");
		}
		List<Project> projects = projectDao.findProjectsByName(ownerAccountId, name);
		if (projects != null && projects.size() == 0)
			projects = null;
		return projects;
	}

	// get all Projects owned by a given project manager1 id
	@Override
	public List<Project> findProjectsByProjectManager1Id(int ownerAccountId,
			int projectMgr1Id, Set<String> currentPhases)
			throws Exception {
		if (ownerAccountId <= 0 || projectMgr1Id <= 0) {
			throw new Exception("Invalud input ownerAccountId or projectMgr1Id");
		}
		if (currentPhases == null || currentPhases.isEmpty()) {
			throw new Exception("Invalud input currentPhases");
		}
		List<Project> projects = projectDao.findProjectsByProjectManager1Id(ownerAccountId,
				projectMgr1Id, currentPhases);
		if (projects != null && projects.size() == 0)
			projects = null;
		return projects;
	}

	// get all Projects owned by a given project manager2 id
	@Override
	public List<Project> findProjectsByProjectManager2Id(int ownerAccountId,
			int projectMgr2Id, Set<String> currentPhases)
			throws Exception {
		if (ownerAccountId <= 0 || projectMgr2Id <= 0) {
			throw new Exception("Invalud input ownerAccountId or projectMgr2Id");
		}
		if (currentPhases == null || currentPhases.isEmpty()) {
			throw new Exception("Invalud input currentPhases");
		}
		List<Project> projects = projectDao.findProjectsByProjectManager2Id(ownerAccountId,
				projectMgr2Id, currentPhases);
		if (projects != null && projects.size() == 0)
			projects = null;
		return projects;
	}

	// get all Projects owned by a given customer account
	@Override
	public List<Project> findProjectsByCustomerAccount(int ownerAccountId,
			int customerAccount, Set<String> currentPhases)
			throws Exception {
		if (ownerAccountId <= 0 || customerAccount <= 0) {
			throw new Exception("Invalud input ownerAccountId or customerAccount");
		}
		if (currentPhases == null || currentPhases.isEmpty()) {
			throw new Exception("Invalud input currentPhases");
		}
		List<Project> projects = projectDao.findProjectsByCustomerAccount(ownerAccountId,
				customerAccount, currentPhases);
		if (projects != null && projects.size() == 0)
			projects = null;
		return projects;
	}

	// get all Projects owned by a given customer contact
	@Override
	public List<Project> findProjectsByCustomerContact(int ownerAccountId,
			UUID customerContact, Set<String> currentPhases)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (customerContact == null) {
			throw new Exception("Invalud input customerContact");
		}
		if (currentPhases == null || currentPhases.isEmpty()) {
			throw new Exception("Invalud input currentPhases");
		}
		List<Project> projects = projectDao.findProjectsByCustomerContact(ownerAccountId,
				customerContact, currentPhases);
		if (projects != null && projects.size() == 0)
			projects = null;
		return projects;
	}

	// get all Projects owned by a given sponsor
	@Override
	public List<Project> findProjectsBySponsor(int ownerAccountId,
			UUID sponsor, Set<String> currentPhases)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (sponsor == null) {
			throw new Exception("Invalud input sponsor");
		}
		if (currentPhases == null || currentPhases.isEmpty()) {
			throw new Exception("Invalud input currentPhases");
		}
		List<Project> projects = projectDao.findProjectsBySponsor(ownerAccountId,
				sponsor, currentPhases);
		if (projects != null && projects.size() == 0)
			projects = null;
		return projects;
	}

	// get all Projects owned by a given managing department id
	@Override
	public List<Project> findProjectsByManagingDeptId(int ownerAccountId, int managingDeptId,
			Set<String> currentPhases)
			throws Exception {
		if (ownerAccountId <= 0 || managingDeptId <= 0) {
			throw new Exception("Invalud input ownerAccountId or managingDeptId");
		}
		if (currentPhases == null || currentPhases.isEmpty()) {
			throw new Exception("Invalud input currentPhases");
		}
		List<Project> projects = projectDao.findProjectsByManagingDeptId(ownerAccountId,
				managingDeptId, currentPhases);
		if (projects != null && projects.size() == 0)
			projects = null;
		return projects;
	}

	// get all Projects by budget range
	@Override
	public List<Project> findProjectsByBudgetRange(int ownerAccountId, double fromAmount, double toAmount)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (fromAmount <= 0.0 || toAmount <= 0.0) {
			throw new Exception("fromAmount and toAmount must be positive");
		}
		if (fromAmount > toAmount) {
			double tmp = fromAmount;
			fromAmount = toAmount;
			toAmount   = tmp;
		}
		return null;
	}

	// get all Projects by start date range
	@Override
	public List<Project> findProjectsByStartDateRange(int ownerAccountId, DateTime fromDate, DateTime toDate)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (fromDate == null && toDate == null) {
			throw new Exception("Both fromDate and toDate are null");
		}
		List<Project> projects = projectDao.findProjectsByStartDateRange(ownerAccountId,
				fromDate, toDate);
		if (projects != null && projects.size() == 0)
			projects = null;
		return projects;
	}

	// get all Projects by end date range
	@Override
	public List<Project> findProjectsByEndDateRange(int ownerAccountId, DateTime fromDate, DateTime toDate)
			throws Exception {
		if (ownerAccountId <= 0) {
			throw new Exception("Invalud input ownerAccountId");
		}
		if (fromDate == null && toDate == null) {
			throw new Exception("Both fromDate and toDate are null");
		}
		List<Project> projects = projectDao.findProjectsByEndDateRange(ownerAccountId,
				fromDate, toDate);
		if (projects != null && projects.size() == 0)
			projects = null;
		return projects;
	}

	// get all Project owned by a specific account id and have the same parent project id
	@Override
	public List<Project> findAllSiteProjectsByParentProject(int ownerAccountId, int parentProjectId)
			throws Exception {
		if (ownerAccountId <= 0 || parentProjectId <= 0) {
			throw new Exception("Invalud input ownerAccountId or parentProjectId");
		}
		List<Project> projects = projectDao.findProjectsByParentProjectId(ownerAccountId, parentProjectId);
		if (projects != null && projects.size() == 0)
			projects = null;
		return projects;
	}

	// Create services

	private void validateProjectData(Project project)
			throws MissingRequiredDataException, InvalidDataValueException,
					DuplicateKeyException, Exception {
		if (project == null)
			throw new Exception("Missing project");
		if (project.getProjectCode() == null || project.getProjectCode().isEmpty())
			throw new Exception("Missing required Name");
		if (project.getName() == null || project.getName().isEmpty())
			throw new Exception("Missing required Name");
		if (project.getCurrentPhase() == null || project.getCurrentPhase().isEmpty())
			throw new Exception("Missing required Name");
		if (project.getProjectManager1Id() <= 0)
			throw new Exception("Invalid projectManager1Id");
		if (project.getProjectManager2Id() <= 0)
			throw new Exception("Invalid projectManager2Id");
		if (project.getCustomerAccount() <= 0)
			throw new Exception("Invalid customerAccount");
		if (project.getCustomerContact() == null)
			throw new Exception("Missing required customerContact");
		if (project.getSponsor() == null)
			throw new Exception("Missing required sponsor");
		if (project.getManagingDeptId() <= 0)
			throw new Exception("Invalid managingDeptId");
	}

	@Override
	public Project createProject(UUID userId, int userOwnerAccountId, Project project) 
			throws MissingRequiredDataException, InvalidDataValueException,
					DuplicateKeyException, Exception {
		//if (userId == null)
		//	throw new MissingRequiredDataException("Missing userId");
		if (userOwnerAccountId <= 0)
			throw new MissingRequiredDataException("Missing userOwnerAccountId");

		this.validateProjectData(project);

		// Good to go
		//DateTime currentDateTime = DateTime.now();		

		// Create the Project
		//project.setCreatedDate(currentDateTime);
		//project.setCreatedById(userId);
		//project.setLastModifiedDate(currentDateTime);
		//project.setLastModifiedById(userId);
		//project.setOwnerId(userId);
		project.setOwnerAccountId(userOwnerAccountId);

		// Persist the Project object
		try {
			int retId = projectDao.addProject(project);
			project.setId(retId);

			// retrieve the new data back
			Project retProject = this.findProjectById(userOwnerAccountId, retId);
			return retProject;
		}
		catch (Exception e) {
			//logger.info("ProjectManagerImpl.createProject: fail to create Project. Exception: " + e.getMessage());
			throw e;
		}
	}

	// Update services

	@Override
	public Project updateProject(UUID userId, Project project) 
			throws MissingRequiredDataException, InvalidDataValueException,
				DuplicateKeyException, Exception {
		//if (userId == null)
		//	throw new MissingRequiredDataException("Missing userId");

		this.validateProjectData(project);
		
		// Good to go
		//DateTime currentDateTime = DateTime.now();		
		try {
			// Persist the Project changes
			//project.setLastModifiedDate(currentDateTime);
			//project.setLastModifiedById(userId);

			@SuppressWarnings("unused")
			int numRecordUpdated = projectDao.saveProject(project);

			// retrieve the new data back
			Project retProject = this.findProjectById(project.getOwnerAccountId(), project.getId());
			return retProject;
		}
		catch (Exception e) {
			//logger.info("ProjectManagerImpl.updateProject: fail to update Project. Exception: " + e.getMessage());
			throw e;
		}
		
	}

	// Delete services

	@Override
	public void deleteProject(UUID userId, int ownerAccountId, int id)
			throws MissingRequiredDataException, Exception {
		//if (userId == null)
		//	throw new MissingRequiredDataException("Missing userId");
		if (ownerAccountId <= 0)
			throw new MissingRequiredDataException("Missing ownerAccountId");
		if (id <= 0)
			throw new MissingRequiredDataException("Missing id");

		int numRecordDeleted = projectDao.deleteProject(ownerAccountId, id);
		if (numRecordDeleted == 0)
			throw new Exception("Fail to delete Project: " + id);
	}

	// Export/import to/from CSV file

	@Override
	public void exportProjectsToCSV(List<Project> projects, OutputStream os)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Project> importProjectsFromCSV(UUID userId,
			int userOwnerAccountId, InputStream is, boolean bOverrideDup)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
