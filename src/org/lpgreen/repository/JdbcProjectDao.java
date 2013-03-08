package org.lpgreen.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
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

	private final static String timeZone = "America/Los_Angeles";

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertProject;
	public void setDataSource(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		insertProject = new SimpleJdbcInsert(dataSource).withTableName("Project").usingGeneratedKeyColumns("Id");
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
			project.setBudget(rs.getBigDecimal("Budget"));
			project.setCurrencyCode(rs.getString("CurrencyCode"));
			if (rs.getTimestamp("StartDate") != null) {
				project.setStartDate(new DateTime(rs.getTimestamp("StartDate")));
			}
			if (rs.getTimestamp("EndDate") != null) {
				project.setEndDate(new DateTime(rs.getTimestamp("EndDate")));
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
	public List<Project> findProjectsByOwerAccountId(int ownerAccountId) {
		try {
			List<Project> projects = namedParameterJdbcTemplate.query(
					strProjectQueryWithOwnerAccountId,
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
	public List<Project> findProjectsByProjectManager1Id(int ownerAccountId, int projectMgr1Id) {
		try {
			List<Project> projects = namedParameterJdbcTemplate.query(
					strProjectQueryWithProjManager1Id,
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
	public List<Project> findProjectsByProjectManager2Id(int ownerAccountId, int projectMgr2Id) {
		try {
			List<Project> projects = namedParameterJdbcTemplate.query(
					strProjectQueryWithProjManager2Id,
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
	public List<Project> findProjectsByCustomerAccount(int ownerAccountId, int customerAccount) {
		try {
			List<Project> projects = namedParameterJdbcTemplate.query(
					strProjectQueryWithCustomerAccount,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("CustomerAccount", customerAccount),
					new ProjectMapper());
			return projects;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByCustomerAccount Exception: " + e.getMessage());
			return null;
		}
	}

	// query Project using ManagingDeptId
	protected final static String strProjectQueryWithManagingDeptId = "select " + fieldSelectionForReadProject +
			" from Project as o where OwnerAccountId=:OwnerAccountId and ManagingDeptId=:ManagingDeptId";

	// get all Projects owned by a given managing department id
	@Override
	public List<Project> findProjectsByManagingDeptId(int ownerAccountId, int managingDeptId) {
		try {
			List<Project> projects = namedParameterJdbcTemplate.query(
					strProjectQueryWithManagingDeptId,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("ManagingDeptId", managingDeptId),
					new ProjectMapper());
			return projects;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByManagingDeptId Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Projects by date range
	@Override
	public List<Project> findProjectsByDateRange(int ownerAccountId, DateTime startDate, DateTime endDate) {
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(fieldSelectionForReadProject);
			sbQuery.append(" from Project as o where OwnerAccountId=:OwnerAccountId");

			MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
			sqlParameters.addValue("OwnerAccountId", ownerAccountId);
			if (startDate != null) {
				sbQuery.append(" and to_char(o.StartDate, 'YYYY-MM-DD') >= :StartDate");
				String strStartDate = DateTimeFormat.forPattern("YYYY-MM-dd").withZone(DateTimeZone.forID(timeZone)).print(startDate);
				sqlParameters.addValue("StartDate", strStartDate);
			}
			if (endDate != null) {
				sbQuery.append(" and to_char(o.EndDate, 'YYYY-MM-DD') <= :EndDate");
				String strEndDate = DateTimeFormat.forPattern("YYYY-MM-dd").withZone(DateTimeZone.forID(timeZone)).print(endDate);
				sqlParameters.addValue("EndDate", strEndDate);
			}
			List<Project> projects = namedParameterJdbcTemplate.query(
					sbQuery.toString(),
					sqlParameters,
					new ProjectMapper());
			return projects;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByDateRange Exception: " + e.getMessage());
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
		// ToDo: how to add UUID
		// CustomerContact
		// Sponsor

		if (project.getManagingDeptId() > 0) {
			parameters.addValue("ManagingDeptId", project.getManagingDeptId());
		}
		else {
			parameters.addValue("ManagingDeptId", null);
		}
		parameters.addValue("Objectives", project.getObjectives());
		parameters.addValue("Description", project.getDescription());
		// Is Decimal special? 
		parameters.addValue("Budget", project.getBudget());
		parameters.addValue("CurrencyCode", project.getCurrencyCode());
		// ToDo: how to do DateTime
		if (project.getStartDate() != null) {
		
		}
		if (project.getEndDate() != null) {
			
		}
		if (project.getParentProjectId() > 0) {
			parameters.addValue("ParentProjectId", project.getParentProjectId());
		}
		else {
			parameters.addValue("ParentProjectId", null);
		}
		parameters.addValue("Notes", project.getNotes());
		// ToDo OnwerId UUID
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
					"update OperationRight set " + fieldSetForUpdateProject + " where Id=:Id;",
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
