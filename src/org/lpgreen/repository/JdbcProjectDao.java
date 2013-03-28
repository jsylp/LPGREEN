package org.lpgreen.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
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
 * Last modify date: Mar. 06, 2013
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

	// o: the main object: this Project 
	protected final static String fieldSelectionForReadProject =
			"o.Id,o.ProjectCode,o.Name,o.CurrentPhase,o.ProjectManager1Id,o.ProjectManager2Id," +
			"o.CustomerAccount,o.CustomerContact,o.Sponsor,o.ManagingDeptId,o.Objectives," +
			"o.Description,o.Budget,o.CurrencyCode,o.StartDate,o.EndDate,o.ParentProjectId," +
			"o.Notes,o.OwnerId,o.OwnerAccountId";

	// field selection for update
	protected final static String fieldSetForUpdateProject = 
			"ProjectCode=:ProjectCode,Name=:Name,CurrentPhase=:CurrentPhase," +
			"ProjectManager1Id=:ProjectManager1Id,ProjectManager2Id=:ProjectManager1Id," +
			"CustomerAccount=:CustomerAccount,CustomerContact=:CustomerContact," +
			"Sponsor=:Sponsor,ManagingDeptId=:ManagingDeptId,Objectives=:Objectives," +
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

	// Override to return the RowMapper
	protected RowMapper<Project> getRowMapper() {
		return new ProjectMapper();
	}

	// Override to return MapSqlParameterSource for creating Project
	protected MapSqlParameterSource getDomainObjectMapSqlParameterSource(Project project, boolean bNew) {
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

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// Project related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	// get all Projects owned by a specific owner account id and current phases
	@Override
	public List<Project> findProjectsByOwnerAccountId(int ownerAccountId, Set<String> currentStatuses) {
		try {
			return findDomainObjectsByOwnerAccountId(ownerAccountId, currentStatuses);
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

	// get a specific Project by a given database id
	@Override
	public Project findProjectById(int id) {
		try {
			return findDomainObjectById(id);
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

	// get all Projects by ProjectCode
	@Override
	public List<Project> findProjectsByProjectCode(int ownerAccountId, String projectCode) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, "ProjectCode", projectCode, null);
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
	public List<Project> findProjectsByName(int ownerAccountId, String name) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, "Name", name, null);
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
	public List<Project> findProjectsByProjectManager1Id(int ownerAccountId, int projectMgr1Id,
			Set<String> currentStatuses) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, "ProjectManager1Id", projectMgr1Id, currentStatuses);
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
	public List<Project> findProjectsByProjectManager2Id(int ownerAccountId, int projectMgr2Id,
			Set<String> currentStatuses) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, "ProjectManager2Id", projectMgr2Id, currentStatuses);
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

	// get all Projects owned by a given CustomerAccount
	@Override
	public List<Project> findProjectsByCustomerAccount(int ownerAccountId, int customerAccount,
			Set<String> currentStatuses) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, "CustomerAccount", customerAccount, currentStatuses);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcProjectDao.findProjectsByCustomerAccount MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByCustomerAccount Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Projects owned by a given CustomerContact
	@Override
	public List<Project> findProjectsByCustomerContact(int ownerAccountId, UUID customerContact,
			Set<String> currentStatuses) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, "CustomerContact", customerContact, currentStatuses);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcProjectDao.findProjectsByCustomerContact MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsByCustomerContact Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Projects owned by a given Sponsor
	@Override
	public List<Project> findProjectsBySponsor(int ownerAccountId, UUID sponsor,
			Set<String> currentStatuses) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, "Sponsor", sponsor, currentStatuses);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcProjectDao.findProjectsBySponsor MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcProjectDao.findProjectsBySponsor Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Projects owned by a given ManagingDeptId
	@Override
	public List<Project> findProjectsByManagingDeptId(int ownerAccountId, int managingDeptId,
			Set<String> currentStatuses) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, "ManagingDeptId", managingDeptId, currentStatuses);
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
	public List<Project> findProjectsByStartDateRange(int ownerAccountId, DateTime fromDate, DateTime toDate) {
		try {
			return findDomainObjectsByDateTimeRange(ownerAccountId, "StartDate", fromDate, toDate, null);
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
	public List<Project> findProjectsByEndDateRange(int ownerAccountId, DateTime fromDate, DateTime toDate) {
		try {
			return findDomainObjectsByDateTimeRange(ownerAccountId, "EndDate", fromDate, toDate, null);
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
	public List<Project> findProjectsByParentProjectId(int ownerAccountId, int parentProjectId) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, "ParentProjectId", parentProjectId, null);
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
			System.out.println("JdbcProjectDao.addProject Exception: " + e.getMessage());
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
			return saveDomainObject(project);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcProjectDao.saveProject MustOverrideException: " + e.getMessage());
			return -1;
		}
		catch (DuplicateKeyException e) {
			System.out.println("JdbcProjectDao.saveProject Exception: " + e.getMessage());
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
		try {
			return deleteDomainObject(ownerAccountId, id);
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
