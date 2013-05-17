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
import org.lpgreen.domain.Project;
import org.lpgreen.util.MustOverrideException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * JdbcProjectDao is the JDBC implementation of the ProjectDao for Project related entity's persistence layer
 * 
 * Creation date: Mar. 06, 2013
 * Last modify date: Apr. 14, 2013
 * 
 * @author  Jiaxun Stephen Yu
 * @version 1.0
 */

@Repository
public class JdbcProjectDao extends LPJdbcGeneric<Project> implements ProjectDao {

	public void setDataSource(DataSource dataSource)
			throws MustOverrideException {
		try {
			super.setDataSource(dataSource);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcProjectDao.setDataSource Exception: " + e.getMessage());
			throw e;
		}
	}

	// To do: uncomment
//	private EmployeeDao employeeDao;
//	public void setEmployeeDao(EmployeeDao employeeDao) {
//		this.employeeDao = employeeDao;
//	}

	// o: the main object: this Project
	// ac: the CustomerAccount
	// cc: the CustomerContact
	// cs: the Sponsor
	// dm: the ManagingDept
	// pp: the ParentProject
	protected final static String fieldSelectionForReadProject =
			"o.Id,o.ProjectCode,o.Name,o.CurrentPhase,o.ProjectManager1Id,o.ProjectManager2Id," +
			"o.CustomerAccountId,ac.Name as CustomerAccountName," +
			"o.CustomerContactId,cc.LastName||','||cc.FirstName as CustomerContactName," +
			"o.SponsorId,cs.LastName||','||cs.FirstName as SponsorContactName," +
			"o.ManagingDeptId,dm.Name as ManagingDeptName,o.Objectives," +
			"o.Description,o.Budget,o.CurrencyCode,o.StartDate,o.EndDate," +
			"o.ParentProjectId,pp.ProjectCode as ParentProjectCode,pp.Name as ParentProjectName," +
			"o.Notes,o.OwnerId,o.OwnerAccountId";

	protected final static String custAcctJoin = " LEFT OUTER JOIN Account as ac ON o.CustomerAccountId = ac.Id";
	protected final static String custContJoin = " LEFT OUTER JOIN Contact as cc ON o.CustomerContactId = cc.UniqueId";
	protected final static String sponsorJoin = " LEFT OUTER JOIN Contact as cs ON o.SponsorId = cs.UniqueId";
	protected final static String mngDeptJoin = " LEFT OUTER JOIN Department as dm ON o.ManagingDeptId = dm.Id";
	protected final static String parProjJoin = " LEFT OUTER JOIN Project as pp ON o.ParentProjectId = pp.Id";
	protected final static String outJoins = custAcctJoin + custContJoin + sponsorJoin + mngDeptJoin + parProjJoin;

	// field selection for update
	protected final static String fieldSetForUpdateProject = 
			"ProjectCode=:ProjectCode,Name=:Name,CurrentPhase=:CurrentPhase," +
			"ProjectManager1Id=:ProjectManager1Id,ProjectManager2Id=:ProjectManager1Id," +
			"CustomerAccountId=:CustomerAccountId,CustomerContactId=:CustomerContactId," +
			"SponsorId=:SponsorId,ManagingDeptId=:ManagingDeptId,Objectives=:Objectives," +
			"Description=:Description,Budget=:Budget,CurrencyCode=:CurrencyCode," +
			"StartDate=:StartDate,EndDate=:EndDate,ParentProjectId=:ParentProjectId," +
			"Notes=:Notes,OwnerAccountId=:OwnerAccountId";

	// RowMapper class
	private static class ProjectMapper implements RowMapper<Project> {

		public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
			Project project = new Project();

			project.setId(rs.getInt("Id"));
			project.setProjectCode(rs.getString("ProjectCode"));
			project.setName(rs.getString("Name"));
			project.setCurrentPhase(rs.getString("CurrentPhase"));
			project.setProjectManager1Id(rs.getInt("ProjectManager1Id"));
			project.setProjectManager2Id(rs.getInt("ProjectManager2Id"));

			int customerAccountId = rs.getInt("CustomerAccountId");
			if (customerAccountId > 0) {
				project.setCustomerAccountId(customerAccountId);
				project.setCustomerAccountName(rs.getString("CustomerAccountName"));
			}
			else {
				project.setCustomerAccountId(0);
				project.setCustomerAccountName(null);
			}

			if (rs.getString("CustomerContactId") != null) {
				project.setCustomerContactId(UUID.fromString(rs.getString("CustomerContactId")));
				project.setCustomerContactName(rs.getString("CustomerContactName"));
			}
			else {
				project.setCustomerContactName(null);
			}

			if (rs.getString("SponsorId") != null) {
				project.setSponsorId(UUID.fromString(rs.getString("SponsorId")));
				project.setSponsorName(rs.getString("SponsorContactName"));
			}
			else {
				project.setCustomerContactName(null);
			}

			int managingDeptId = rs.getInt("ManagingDeptId");
			if (managingDeptId > 0) {
				project.setManagingDeptId(managingDeptId);
				project.setManagingDeptName(rs.getString("ManagingDeptName"));
			}
			else {
				project.setManagingDeptId(0);
				project.setManagingDeptName(null);
			}

			project.setObjectives(rs.getString("Objectives"));
			project.setDescription(rs.getString("Description"));
			project.setBudget(rs.getDouble("Budget"));
			project.setCurrencyCode(rs.getString("CurrencyCode"));

			// StartDate and EndDate
			java.util.Calendar cal = Calendar.getInstance(); 
			cal.setTimeZone(TimeZone.getTimeZone("UTC")); 
			if (rs.getTimestamp("StartDate") != null) {
				project.setStartDate(new DateTime(rs.getTimestamp("StartDate", cal), DateTimeZone.UTC));
			}
			if (rs.getTimestamp("EndDate") != null) {
				project.setEndDate(new DateTime(rs.getTimestamp("EndDate", cal), DateTimeZone.UTC));
			}

			int parentProjectId = rs.getInt("ParentProjectId");
			if (parentProjectId > 0) {
				project.setParentProjectId(parentProjectId);
				project.setParentProjectCode(rs.getString("ParentProjectCode"));
				project.setParentProjectName(rs.getString("ParentProjectName"));
			}
			else {
				project.setParentProjectId(0);
				project.setParentProjectCode(null);
				project.setParentProjectName(null);
			}

			String notes = rs.getString("Notes");
			if (notes != null && !notes.isEmpty()) {
				notes = notes.replaceAll("(\\r\\n|\\n)", "<br/>");
				project.setNotes(notes);
			}

			project.setOwnerAccountId(rs.getInt("OwnerAccountId"));
			return project;
		}
	}

	// Read detailed data of a project. The input project may have the header only.
	@Override
	public Project readProjectDetail(Project project) {
		if (project == null)
			return null;

		// To do: uncomment
		if (project.getProjectManager1Id() > 0)
			;
			//project.setProjectManager1Name(employeeDao.getEmployeeNameById(project.getProjectManager1Id()));
		if (project.getProjectManager2Id() > 0)
			;
			//project.setProjectManager2Name(employeeDao.getEmployeeNameById(project.getProjectManager2Id()));

		return project;
	}

	// Override to return the SQL table name
	protected String getSqlTable() {
		return "Project";
	}

	// Override to return the field selection for read
	protected String getFieldSelectionForRead() {
		return fieldSelectionForReadProject;
	}

	// Override to return the filed selection for update
	protected String getFieldSelectionForUpdate() {
		return fieldSetForUpdateProject;
	}

	// Overridden to return the name of the current status column
	protected String getCurrentStatusColumn() {
		return "CurrentPhase";
	}

	// Override to return the field order for read a list of objects
	protected String getFieldOrderForReadList() {
		return "o.OwnerAccountId, o.ProjectCode ASC";
	}

	// Override to return the RowMapper
	protected RowMapper<Project> getRowMapper() {
		return new ProjectMapper();
	}

	// Override to return MapSqlParameterSource for creating Project
	protected MapSqlParameterSource getDomainObjectMapSqlParameterSource(Project project, boolean bNew) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		// Persist data defined in UserObject
		parameters.addValue("OwnerId", project.getOwnerId());
		parameters.addValue("OwnerAccountId", project.getOwnerAccountId());

		if (!bNew) {
			if (project.getId() > 0)
				parameters.addValue("Id", project.getId());	// auto generated when insert a Project, use it as the primary key when update it
			else
				parameters.addValue("Id", null);
		}

		parameters.addValue("ProjectCode", project.getProjectCode());
		parameters.addValue("Name", project.getName());
		parameters.addValue("CurrentPhase", project.getCurrentPhase());

		if (project.getProjectManager1Id() > 0)
			parameters.addValue("ProjectManager1Id", project.getProjectManager1Id());
		else
			parameters.addValue("ProjectManager1Id", null);
		if (project.getProjectManager2Id() > 0)
			parameters.addValue("ProjectManager2Id", project.getProjectManager2Id());
		else
			parameters.addValue("ProjectManager2Id", null);

		if (project.getCustomerAccountId() > 0)
			parameters.addValue("CustomerAccountId", project.getCustomerAccountId());
		else
			parameters.addValue("CustomerAccountId", null);

		parameters.addValue("CustomerContactId", project.getCustomerContactId());
		parameters.addValue("SponsorId", project.getSponsorId());

		if (project.getManagingDeptId() > 0)
			parameters.addValue("ManagingDeptId", project.getManagingDeptId());
		else
			parameters.addValue("ManagingDeptId", null);

		if (project.getObjectives() != null && !project.getObjectives().isEmpty())
			parameters.addValue("Objectives", project.getObjectives());
		else
			parameters.addValue("Objectives", null);

		if (project.getDescription() != null && !project.getDescription().isEmpty())
			parameters.addValue("Description", project.getDescription());
		else
			parameters.addValue("Description", null);

		if (project.getBudget() > 0)
			parameters.addValue("Budget", project.getBudget());
		else
			parameters.addValue("Budget", null);

		if (project.getCurrencyCode() != null && !project.getCurrencyCode().isEmpty())
			parameters.addValue("CurrencyCode", project.getCurrencyCode());
		else
			parameters.addValue("CurrencyCode", null);

		if (project.getStartDate() != null)
			parameters.addValue("StartDate", project.getStartDate().toCalendar(null), Types.TIMESTAMP);
		else
			parameters.addValue("StartDate", null);

		if (project.getEndDate() != null)
			parameters.addValue("EndDate", project.getEndDate().toCalendar(null), Types.TIMESTAMP);
		else
			parameters.addValue("EndDate", null);

		if (project.getParentProjectId() > 0)
			parameters.addValue("ParentProjectId", project.getParentProjectId());
		else
			parameters.addValue("ParentProjectId", null);

		if (project.getNotes() != null && !project.getNotes().isEmpty())
			parameters.addValue("Notes", project.getNotes());
		else
			parameters.addValue("Notes", null);
		return parameters;
	}

	protected List<Project> getQueryDetail(List<Project> projects, boolean headerOnly) {
		if (projects != null && projects.size() > 0 && !headerOnly) {
			Iterator<Project> it = projects.iterator();
			while (it.hasNext()) {
				Project project = it.next();
				this.readProjectDetail(project);
			}
		}
		return projects;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// Project related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	// get a specific Project by a given database id
	@Override
	public Project findProjectById(int id) {
		try {
			Project project = super.findDomainObjectById(id, outJoins);
			if (project != null) {
				project = this.readProjectDetail(project);
			}
			return project;
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcProjectDao.findProjectById MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectById Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Projects owned by a specific owner account id
	@Override
	public List<Project> findProjectsByOwnerAccountId(int ownerAccountId,
			Set<String> currentPhases, boolean headerOnly) {
		if (ownerAccountId <= 0)
			return null;
		try {
			List<Project> projects = super.findDomainObjectsByOwnerAccountId(ownerAccountId, outJoins,
					currentPhases, null);
			return getQueryDetail(projects, headerOnly);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcProjectDao.findProjectsByOwnerAccountId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByOwnerAccountId Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Projects by ProjectCode
	@Override
	public List<Project> findProjectsByProjectCode(int ownerAccountId,
			String projectCode, Set<String> currentPhases, boolean headerOnly) {
		try {
			List<Project> projects = super.findDomainObjectsByStringColumnVal(ownerAccountId, outJoins,
					"o.ProjectCode", projectCode, true, currentPhases, null);
			return getQueryDetail(projects, headerOnly);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcProjectDao.findProjectsByProjectCode MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByProjectCode Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Projects owned by a given Name
	@Override
	public List<Project> findProjectsByName(int ownerAccountId,
			String name, Set<String> currentPhases, boolean headerOnly) {
		try {
			List<Project> projects = super.findDomainObjectsByStringColumnVal(ownerAccountId, outJoins,
					"o.Name", name, true, currentPhases, null);
			return getQueryDetail(projects, headerOnly);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcProjectDao.findProjectsByName MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByName Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Projects owned by a given ProjectManager1Id
	@Override
	public List<Project> findProjectsByProjectManager1Id(int ownerAccountId,
			int projectMgr1Id, Set<String> currentPhases, boolean headerOnly) {
		try {
			List<Project> projects = super.<Integer>findDomainObjectsByGenericTypeColumnVal(ownerAccountId, outJoins,
					"o.ProjectManager1Id", projectMgr1Id, currentPhases, null);
			return getQueryDetail(projects, headerOnly);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcProjectDao.findProjectsByProjectManager1Id MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByProjectManager1Id Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Projects owned by a given ProjectManager2Id
	@Override
	public List<Project> findProjectsByProjectManager2Id(int ownerAccountId,
			int projectMgr2Id, Set<String> currentPhases, boolean headerOnly) {
		try {
			List<Project> projects = super.<Integer>findDomainObjectsByGenericTypeColumnVal(ownerAccountId, outJoins,
					"o.ProjectManager2Id", projectMgr2Id, currentPhases, null);
			return getQueryDetail(projects, headerOnly);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcProjectDao.findProjectsByProjectManager2Id MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByProjectManager2Id Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Projects owned by a given CustomerAccountId
	@Override
	public List<Project> findProjectsByCustomerAccountId(int ownerAccountId,
			int customerAccountId, Set<String> currentPhases, boolean headerOnly) {
		try {
			List<Project> projects = super.<Integer>findDomainObjectsByGenericTypeColumnVal(ownerAccountId, outJoins,
					"o.CustomerAccountId", customerAccountId, currentPhases, null);
			return getQueryDetail(projects, headerOnly);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcProjectDao.findProjectsByCustomerAccountId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByCustomerAccountId Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Projects owned by a given CustomerContactId
	@Override
	public List<Project> findProjectsByCustomerContactId(int ownerAccountId,
			UUID customerContactId, Set<String> currentPhases, boolean headerOnly) {
		try {
			List<Project> projects = super.<UUID>findDomainObjectsByGenericTypeColumnVal(ownerAccountId, outJoins,
					"o.CustomerContactId", customerContactId, currentPhases, null);
			return getQueryDetail(projects, headerOnly);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcProjectDao.findProjectsByCustomerContactId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByCustomerContactId Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Projects owned by a given SponsorId
	@Override
	public List<Project> findProjectsBySponsorId(int ownerAccountId,
			UUID sponsorId, Set<String> currentPhases, boolean headerOnly) {
		try {
			List<Project> projects = super.<UUID>findDomainObjectsByGenericTypeColumnVal(ownerAccountId, outJoins,
					"o.SponsorId", sponsorId, currentPhases, null);
			return getQueryDetail(projects, headerOnly);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcProjectDao.findProjectsBySponsorId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsBySponsorId Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Projects owned by a given ManagingDeptId
	@Override
	public List<Project> findProjectsByManagingDeptId(int ownerAccountId,
			int managingDeptId, Set<String> currentPhases, boolean headerOnly) {
		try {
			List<Project> projects = super.<Integer>findDomainObjectsByGenericTypeColumnVal(ownerAccountId, outJoins,
					"o.ManagingDeptId", managingDeptId, currentPhases, null);
			return getQueryDetail(projects, headerOnly);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcProjectDao.findProjectsByManagingDeptId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByManagingDeptId Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Projects by the StartDate range
	@Override
	public List<Project> findProjectsByStartDateRange(int ownerAccountId,
			DateTime fromDate, DateTime toDate, Set<String> currentPhases, boolean headerOnly) {
		try {
			List<Project> projects = super.findDomainObjectsByDateTimeRange(ownerAccountId, outJoins,
					"o.StartDate", fromDate, toDate, currentPhases, null);
			return getQueryDetail(projects, headerOnly);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcProjectDao.findProjectsByStartDateRange MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByStartDateRange Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Projects by the EndDate range
	@Override
	public List<Project> findProjectsByEndDateRange(int ownerAccountId,
			DateTime fromDate, DateTime toDate, Set<String> currentPhases, boolean headerOnly) {
		try {
			List<Project> projects = super.findDomainObjectsByDateTimeRange(ownerAccountId, outJoins,
					"o.EndDate", fromDate, toDate, currentPhases, null);
			return getQueryDetail(projects, headerOnly);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcProjectDao.findProjectsByEndDateRange MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByEndDateRange Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Projects owned by a given ParentProjectId
	@Override
	public List<Project> findProjectsByParentProjectId(int ownerAccountId,
			int parentProjectId, Set<String> currentPhases, boolean headerOnly) {
		try {
			List<Project> projects = super.<Integer>findDomainObjectsByGenericTypeColumnVal(ownerAccountId, outJoins,
					"o.ParentProjectId", parentProjectId, currentPhases, null);
			return getQueryDetail(projects, headerOnly);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcProjectDao.findProjectsByParentProjectId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByParentProjectId Exception: " + e.getMessage());
			return null;
		}
	}

	// Add a Project. Return the generated database id
	@Override
	public int addProject(Project project) 
			throws DuplicateKeyException, Exception {
		if (project == null)
			throw new Exception("Missing input project");
		try {
			// insert Project record
			int retId = addDomainObject(project);
			project.setId(retId);
			return retId;
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcProjectDao.addProject MustOverrideException: " + e.getMessage());
			return -1;
		}
		catch (DuplicateKeyException e) {
			System.out.println("JdbcProjectDao.addProject DuplicateKeyException: " + e.getMessage());
			throw e;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.addProject Exception: " + e.getMessage());
			throw e;
		}
	}

	// Save changes of an existing Project object. Return the # of records updated
	@Override
	public int saveProject(Project project) 
			throws DuplicateKeyException, Exception {
		try {
			int numObjectUpdated = saveDomainObject(project);
			if (numObjectUpdated == 0) {
				throw new Exception("Fail to update the Project obejct");
			}
			return numObjectUpdated;
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcProjectDao.saveProject MustOverrideException: " + e.getMessage());
			return -1;
		}
		catch (DuplicateKeyException e) {
			System.out.println("JdbcProjectDao.saveProject DuplicateKeyException: " + e.getMessage());
			throw e;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.saveProject Exception: " + e.getMessage());
			throw e;
		}
	}

	// Delete a Project object. Return the # of record deleted
	@Override
	public int deleteProject(int ownerAccountId, int id)
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
