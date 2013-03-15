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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 * JdbcProjectDao is the JDBC implementation of the ProjectDao for Project related entity's persistence layer
 * 
 * Creation date: Mar. 06, 2013
 * Last modify date: Mar. 06, 2013
 * 
 * @author  Jiaxun Stephen Yu
 * @version 1.0
 */

@Repository
public class JdbcProjectDao implements ProjectDao {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertProject;
	public void setDataSource(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		insertProject = new SimpleJdbcInsert(dataSource).withTableName("Project").usingGeneratedKeyColumns("id");
	}

	// o: the main object: this Project 
	protected final static String fieldSelectionForReadProject =
			"o.Id,o.ProjectCode,o.Name,o.CurrentPhase,o.ProjectManager1Id,o.ProjectManager2Id," +
			"o.CustomerAccount,o.CustomerContact,o.Sponsor,o.ManagingDeptId,o.Objectives," +
			"o.Description,o.Budget,o.CurrencyCode,o.StartDate,o.EndDate,o.ParentProjectId," +
			"o.Notes,o.OwnerId,o.OwnerAccountId";

	protected final static String fieldSetForUpdateProject = 
			"ProjectCode=:ProjectCode,Name=:Name,CurrentPhase=:CurrentPhase," +
			"ProjectManager1Id=:ProjectManager1Id,ProjectManager2Id=:ProjectManager1Id," +
			"CustomerAccount=:CustomerAccount,CustomerContact=:CustomerContact," +
			"Sponsor=:Sponsor,ManagingDeptId=:ManagingDeptId,Objectives=:Objectives," +
			"Description=:Description,Budget=:Budget,CurrencyCode=:CurrencyCode," +
			"StartDate=:StartDate,EndDate=:EndDate,ParentProjectId=:ParentProjectId," +
			"Notes=:Notes,OwnerAccountId=:OwnerAccountId";

	private String getCurrentPhaseQueryPart(Set<String> currentPhases) {
		if (currentPhases != null && currentPhases.size() > 0) {
			StringBuffer sbCurrentPhases = new StringBuffer();
			sbCurrentPhases.append(" AND LOWER(o.CurrentPhase) IN (");
			boolean bFirst = true;
			Iterator<String> it = currentPhases.iterator();
			while (it.hasNext()) {
				String currentPhase = it.next();
				if (currentPhase.isEmpty() || currentPhase.toLowerCase().equals("all")) {
					sbCurrentPhases.setLength(0);
					break;
				}
				else {
					if (!bFirst)
						sbCurrentPhases.append(", ");
					else
						bFirst = false;
					sbCurrentPhases.append("'" + currentPhase.toLowerCase() + "'");
				}
			}
			if (sbCurrentPhases.length() > 0) {
				sbCurrentPhases.append(") ");
				return sbCurrentPhases.toString();
			}
			else
				return "";
		}
		else
			return "";
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// Project related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private static class ProjectMapper implements RowMapper<Project> {
		
		public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
			Project project = new Project();
			project.setId(rs.getInt("Id"));
			project.setProjectCode(rs.getString("ProjectCode"));
			project.setName(rs.getString("Name"));
			project.setCurrentPhase(rs.getString("CurrentPhase"));
			project.setProjectManager1Id(rs.getInt("ProjectManager1Id"));
			project.setProjectManager2Id(rs.getInt("ProjectManager2Id"));
			project.setCustomerAccount(rs.getInt("CustomerAccount"));
			if (rs.getString("CustomerContact") != null) {
				project.setCustomerContact(UUID.fromString(rs.getString("CustomerContact")));
			}
			if (rs.getString("Sponsor") != null) {
				project.setSponsor(UUID.fromString(rs.getString("Sponsor")));
			}
			project.setManagingDeptId(rs.getInt("ManagingDeptId"));
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
			project.setParentProjectId(rs.getInt("ParentProjectId"));
			project.setNotes(rs.getString("Notes"));
			project.setOwnerAccountId(rs.getInt("OwnerAccountId"));
			return project;
		}
	}

	// query Project using OwnerAccountId
	protected final static String strProjectQueryWithOwnerAccountId = "select " + fieldSelectionForReadProject +
			" from Project as o where OwnerAccountId=:OwnerAccountId";

	// get all Project owned by a specific account id
	@Override
	public List<Project> findProjectsByOwerAccountId(int ownerAccountId, Set<String> currentPhases) {
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(strProjectQueryWithOwnerAccountId);
			sbQuery.append(this.getCurrentPhaseQueryPart(currentPhases));
			List<Project> projects = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId),
					new ProjectMapper());
			return projects;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByOwerAccountId Exception: " + e.getMessage());
			return null;
		}
	}

	// query Project using Id
	protected final static String strProjectQueryWithId = "select " + fieldSelectionForReadProject +
			" from Project as o where OwnerAccountId=:OwnerAccountId and Id=:Id";

	// get a specific Project by a given id
	@Override
	public Project findProjectById(int ownerAccountId, int id) {
		try {
			Project project = namedParameterJdbcTemplate.queryForObject(
					strProjectQueryWithId,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("Id", id),
					new ProjectMapper());
			return project;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectById Exception: " + e.getMessage());
			return null;
		}
	}

	// query Project using ProjectCode
	protected final static String strProjectQueryWithProjectCode = "select " + fieldSelectionForReadProject +
			" from Project as o where OwnerAccountId=:OwnerAccountId and ProjectCode=:ProjectCode";

	// get a specific Project by a given ProjectCode
	@Override
	public Project findProjectByProjectCode(int ownerAccountId, String projectCode) {
		try {
			Project project = namedParameterJdbcTemplate.queryForObject(
					strProjectQueryWithProjectCode,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("ProjectCode", projectCode),
					new ProjectMapper());
			return project;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectByProjectCode Exception: " + e.getMessage());
			return null;
		}
	}

	// query Project using Name
	protected final static String strProjectQueryWithName = "select " + fieldSelectionForReadProject +
			" from Project as o where OwnerAccountId=:OwnerAccountId and Name=:Name";

	// get all Projects owned by a given name
	@Override
	public List<Project> findProjectsByName(int ownerAccountId, String name) {
		try {
			List<Project> projects = namedParameterJdbcTemplate.query(
					strProjectQueryWithName,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("Name", name),
					new ProjectMapper());
			return projects;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByName Exception: " + e.getMessage());
			return null;
		}
	}

	// query Project using ProjectManager1Id
	protected final static String strProjectQueryWithProjManager1Id = "select " + fieldSelectionForReadProject +
			" from Project as o where OwnerAccountId=:OwnerAccountId and ProjectManager1Id=:ProjectManager1Id";

	// get all Projects owned by a given project manager1 id
	@Override
	public List<Project> findProjectsByProjectManager1Id(int ownerAccountId, int projectMgr1Id,
			Set<String> currentPhases) {
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(strProjectQueryWithProjManager1Id);
			sbQuery.append(this.getCurrentPhaseQueryPart(currentPhases));
			List<Project> projects = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("ProjectManager1Id", projectMgr1Id),
					new ProjectMapper());
			return projects;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByProjectManager1Id Exception: " + e.getMessage());
			return null;
		}
	}

	// query Project using ProjectManager2Id
	protected final static String strProjectQueryWithProjManager2Id = "select " + fieldSelectionForReadProject +
			" from Project as o where OwnerAccountId=:OwnerAccountId and ProjectManager2Id=:ProjectManager2Id";

	// get all Projects owned by a given project manager2 id
	@Override
	public List<Project> findProjectsByProjectManager2Id(int ownerAccountId, int projectMgr2Id,
			Set<String> currentPhases) {
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(strProjectQueryWithProjManager2Id);
			sbQuery.append(this.getCurrentPhaseQueryPart(currentPhases));
			List<Project> projects = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("ProjectManager2Id", projectMgr2Id),
					new ProjectMapper());
			return projects;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByProjectManager2Id Exception: " + e.getMessage());
			return null;
		}
	}

	// query Project using CustomerAccount
	protected final static String strProjectQueryWithCustomerAccount = "select " + fieldSelectionForReadProject +
			" from Project as o where OwnerAccountId=:OwnerAccountId and CustomerAccount=:CustomerAccount";

	// get all Projects owned by a given customer account
	@Override
	public List<Project> findProjectsByCustomerAccount(int ownerAccountId, int customerAccount,
			Set<String> currentPhases) {
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(strProjectQueryWithCustomerAccount);
			sbQuery.append(this.getCurrentPhaseQueryPart(currentPhases));
			List<Project> projects = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("CustomerAccount", customerAccount),
					new ProjectMapper());
			return projects;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByCustomerAccount Exception: " + e.getMessage());
			return null;
		}
	}

	// query Project using CustomerContact
	protected final static String strProjectQueryWithCustomerContact = "select " + fieldSelectionForReadProject +
			" from Project as o where OwnerAccountId=:OwnerAccountId and CustomerContact=:CustomerContact";

	// get all Projects owned by a given customer contact
	@Override
	public List<Project> findProjectsByCustomerContact(int ownerAccountId, UUID customerContact,
			Set<String> currentPhases) {
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(strProjectQueryWithCustomerContact);
			sbQuery.append(this.getCurrentPhaseQueryPart(currentPhases));
			List<Project> projects = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("CustomerContact", customerContact),
					new ProjectMapper());
			return projects;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByCustomerContact Exception: " + e.getMessage());
			return null;
		}
	}

	// query Project using Sponsor
	protected final static String strProjectQueryWithSponsor = "select " + fieldSelectionForReadProject +
			" from Project as o where OwnerAccountId=:OwnerAccountId and Sponsor=:Sponsor";

	// get all Projects owned by a given sponsor
	@Override
	public List<Project> findProjectsBySponsor(int ownerAccountId, UUID sponsor,
			Set<String> currentPhases) {
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(strProjectQueryWithSponsor);
			sbQuery.append(this.getCurrentPhaseQueryPart(currentPhases));
			List<Project> projects = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("Sponsor", sponsor),
					new ProjectMapper());
			return projects;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsBySponsor Exception: " + e.getMessage());
			return null;
		}
	}

	// query Project using ManagingDeptId
	protected final static String strProjectQueryWithManagingDeptId = "select " + fieldSelectionForReadProject +
			" from Project as o where OwnerAccountId=:OwnerAccountId and ManagingDeptId=:ManagingDeptId";

	// get all Projects owned by a given managing department id
	@Override
	public List<Project> findProjectsByManagingDeptId(int ownerAccountId, int managingDeptId,
			Set<String> currentPhases) {
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(strProjectQueryWithManagingDeptId);
			sbQuery.append(this.getCurrentPhaseQueryPart(currentPhases));
			List<Project> projects = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("ManagingDeptId", managingDeptId),
					new ProjectMapper());
			return projects;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByManagingDeptId Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Projects by start date range
	@Override
	public List<Project> findProjectsByStartDateRange(int ownerAccountId, DateTime fromDate, DateTime toDate)
			throws Exception {
		try {
			if (fromDate == null && toDate == null) {
				throw new Exception("Neither fromDate nor toDate is specified");
			}
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(fieldSelectionForReadProject);
			sbQuery.append(" from Project as o where OwnerAccountId=:OwnerAccountId");

			MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
			sqlParameters.addValue("OwnerAccountId", ownerAccountId);
			if (fromDate != null) {
				sbQuery.append(" and StartDate >= :FromDate");
				sqlParameters.addValue("FromDate", fromDate.toCalendar(null), Types.TIMESTAMP);
			}
			if (toDate != null) {
				sbQuery.append(" and StartDate <= :ToDate");
				sqlParameters.addValue("ToDate", toDate.toCalendar(null), Types.TIMESTAMP);
			}
			List<Project> projects = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					sqlParameters,
					new ProjectMapper());
			return projects;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByEndDateRange Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Projects by end date range
	@Override
	public List<Project> findProjectsByEndDateRange(int ownerAccountId, DateTime fromDate, DateTime toDate)
			throws Exception {
		try {
			if (fromDate == null && toDate == null) {
				throw new Exception("Neither fromDate nor toDate is specified");
			}
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(fieldSelectionForReadProject);
			sbQuery.append(" from Project as o where OwnerAccountId=:OwnerAccountId");

			MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
			sqlParameters.addValue("OwnerAccountId", ownerAccountId);
			if (fromDate != null) {
				sbQuery.append(" and EndDate >= :FromDate");
				sqlParameters.addValue("FromDate", fromDate.toCalendar(null), Types.TIMESTAMP);
			}
			if (toDate != null) {
				sbQuery.append(" and EndDate <= :ToDate");
				sqlParameters.addValue("ToDate", toDate.toCalendar(null), Types.TIMESTAMP);
			}
			List<Project> projects = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					sqlParameters,
					new ProjectMapper());
			return projects;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByEndDateRange Exception: " + e.getMessage());
			return null;
		}
	}

	// query Project using ParentProjectId
	protected final static String strProjectQueryWithParentProjectId = "select " + fieldSelectionForReadProject +
			" from Project as o where OwnerAccountId=:OwnerAccountId and ParentProjectId=:ParentProjectId";

	// get all Projects by a given parent project id
	@Override
	public List<Project> findProjectsByParentProjectId(int ownerAccountId, int parentProjectId) {
		try {
			List<Project> projects = namedParameterJdbcTemplate.query(
					strProjectQueryWithParentProjectId,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("ParentProjectId", parentProjectId),
					new ProjectMapper());
			return projects;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByParentProjectId Exception: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Set SQL Parameters used for creating Project
	 * @param project
	 * @param bNew
	 * @return
	 */
	private MapSqlParameterSource getProjectMapSqlParameterSource(Project project, boolean bNew) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		if (!bNew) {
			if (project.getId() > 0)
				parameters.addValue("Id", project.getId());	// auto generated when insert a Project, use it as the primary key when update it
			else
				parameters.addValue("Id", null);
		}
		parameters.addValue("ProjectCode", project.getProjectCode());
		parameters.addValue("Name", project.getName());
		parameters.addValue("CurrentPhase", project.getCurrentPhase());
		if (project.getProjectManager1Id() > 0) {
			parameters.addValue("ProjectManager1Id", project.getProjectManager1Id());
		}
		else {
			parameters.addValue("ProjectManager1Id", null);
		}
		if (project.getProjectManager2Id() > 0) {
			parameters.addValue("ProjectManager2Id", project.getProjectManager2Id());
		}
		else {
			parameters.addValue("ProjectManager2Id", null);
		}
		if (project.getCustomerAccount() > 0) {
			parameters.addValue("CustomerAccount", project.getCustomerAccount());
		}
		else {
			parameters.addValue("CustomerAccount", null);
		}
		parameters.addValue("CustomerContact", project.getCustomerContact());
		parameters.addValue("Sponsor", project.getSponsor());
		if (project.getManagingDeptId() > 0) {
			parameters.addValue("ManagingDeptId", project.getManagingDeptId());
		}
		else {
			parameters.addValue("ManagingDeptId", null);
		}
		parameters.addValue("Objectives", project.getObjectives());
		parameters.addValue("Description", project.getDescription());
		parameters.addValue("Budget", project.getBudget());
		parameters.addValue("CurrencyCode", project.getCurrencyCode());
		if (project.getStartDate() != null) {
			parameters.addValue("StartDate", project.getStartDate().toCalendar(null), Types.TIMESTAMP);
		}
		else {
			parameters.addValue("StartDate", null);
		}
		if (project.getEndDate() != null) {
			parameters.addValue("EndDate", project.getEndDate().toCalendar(null), Types.TIMESTAMP);
		}
		else {
			parameters.addValue("EndDate", null);
		}
		if (project.getParentProjectId() > 0) {
			parameters.addValue("ParentProjectId", project.getParentProjectId());
		}
		else {
			parameters.addValue("ParentProjectId", null);
		}
		parameters.addValue("Notes", project.getNotes());
		parameters.addValue("OwnerId", project.getOwnerId());
		if (project.getOwnerAccountId() > 0)
			parameters.addValue("OwnerAccountId", project.getOwnerAccountId());
		else
			parameters.addValue("OwnerAccountId", null);
		return parameters;
	}

	// Add a Project. Return the generated id
	// Add an OperationRight. Return the generated id
	@Override
	public int addProject(Project project) 
			throws DuplicateKeyException, Exception {
		if (project == null)
			throw new Exception("Missing input project");

		MapSqlParameterSource parameters = this.getProjectMapSqlParameterSource(project, true);	
		try {
			// insert Project record
			int retId = insertProject.executeAndReturnKey(parameters).intValue();
			project.setId(retId);
			return retId;
		}
		catch (DuplicateKeyException e1) {
			System.out.println("JdbcProjectDao.addProject Exception: " + e1.getMessage());
			throw e1;
		}
		catch (Exception e2) {
			System.out.println("JdbcProjectDao.addProject Exception: " + e2.getMessage());
			throw e2;
		}
	}

	// Save a the changes of an existing Project object. Return the # of record updated
	@Override
	public int saveProject(Project project) 
			throws DuplicateKeyException, Exception {
		if (project == null)
			throw new Exception("Missing input project");
		try {
			int numRecUpdated = namedParameterJdbcTemplate.update(
					"update Project set " + fieldSetForUpdateProject + " where Id=:Id;",
					getProjectMapSqlParameterSource(project, false));
			return numRecUpdated;
		}
		catch (DuplicateKeyException e1) {
			System.out.println("JdbcProjectDao.saveProject Exception: " + e1.getMessage());
			throw e1;
		}
		catch (Exception e2) {
			System.out.println("JdbcProjectDao.saveProject Exception: " + e2.getMessage());
			throw e2;
		}
	}

	// Delete a Project object. Return the # of record deleted
	@Override
	public int deleteProject(int ownerAccountId, int id)
			throws Exception {
		if (ownerAccountId < 0 || id <= 0)
			return 0;
		try {
			int numRecDeleted = namedParameterJdbcTemplate.update(
					"delete from Project where Id=:Id and OwnerAccountId=:OwnerAccountId", 
					new MapSqlParameterSource().addValue("Id", id).addValue("OwnerAccountId", ownerAccountId));
			return numRecDeleted;
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
