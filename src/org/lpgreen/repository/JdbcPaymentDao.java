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
import org.lpgreen.domain.Payment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 * JdbcPaymentDao is the JDBC implementation of the PaymentDao for Payment related entity's persistence layer
 * 
 * Creation date: Mar. 20, 2013
 * Last modify date: Mar. 20, 2013
 * 
 * @author  Jiaxun Stephen Yu
 * @version 1.0
 */

@Repository
public class JdbcPaymentDao extends JdbcGeneric<Payment> implements PaymentDao {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertPayment;
	public void setDataSource(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		insertPayment = new SimpleJdbcInsert(dataSource).withTableName("Payment").usingGeneratedKeyColumns("id");
	}

	protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	// o: the main object: this Payment 
	protected final static String fieldSelectionForReadPayment =
			"o.Id,o.IsReceivedPayment,o.PaymentType,o.PaymentCategory,o.Description," +
			"o.CurrencyCode,o.TotalAmount,o.PaymentMethodType,o.PayerPaymentMethodId," +
			"o.PayeePaymentReceiveMethodId,o.CheckNumber,o.PaymentDateTime," +
			"o.PayerAccountId,o.PayerAccountName,o.PayerContactId,o.PayerContactName,o.PayerBillingAddressId," +
			"o.PayeeAccountId,o.PayeeAccountName,o.PayeeContactId,o.PayeeContactName,o.PayeeBillingAddressId," +
			"o.DepartmentId,o.CostCenterNumber,o.PrimaryPaymentReceiverEmpId,o.SecondaryPaymentReceiverEmpId," +
			"o.PrimaryPaymentPayerEmpId,o.SecondaryPaymentPayerEmpId,o.Notes,o.OwnerId,o.OwnerAccountId";

	// Override to return field selection for read
	protected String getFieldSelectionForRead() {
		return fieldSelectionForReadPayment;
	}

	protected final static String fieldSetForUpdatePayment = 
			"IsReceivedPayment=:IsReceivedPayment,PaymentType=:PaymentType,PaymentCategory=:PaymentCategory," +
			"Description=:Description,CurrencyCode=:CurrencyCode,TotalAmount=:TotalAmount," +
			"PaymentMethodType=:PaymentMethodType,PayerPaymentMethodId=:PayerPaymentMethodId," +
			"PayeePaymentReceiveMethodId=:PayeePaymentReceiveMethodId,CheckNumber=:CheckNumber," +
			"PaymentDateTime=:PaymentDateTime,PayerAccountId=:PayerAccountId,PayerAccountName=:PayerAccountName," +
			"PayerContactId=:PayerContactId,PayerContactName=:PayerContactName,PayerBillingAddressId=:PayerBillingAddressId," +
			"PayeeAccountId=:PayeeAccountId,PayeeAccountName=:PayeeAccountName,PayeeContactId=:PayeeContactId," +
			"PayeeContactName=:PayeeContactName,PayeeBillingAddressId=:PayeeBillingAddressId,DepartmentId=:DepartmentId," +
			"CostCenterNumber=:CostCenterNumber,PrimaryPaymentReceiverEmpId=:PrimaryPaymentReceiverEmpId," +
			"SecondaryPaymentReceiverEmpId=:SecondaryPaymentReceiverEmpId,PrimaryPaymentPayerEmpId=:PrimaryPaymentPayerEmpId," +
			"SecondaryPaymentPayerEmpId=:SecondaryPaymentPayerEmpId,Notes=:Notes,OwnerAccountId=:OwnerAccountId";

	protected String getFieldSelectionForUpdate() {
		return fieldSetForUpdatePayment;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// Payment related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private static class PaymentMapper implements RowMapper<Payment> {

		public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
			java.util.Calendar cal = Calendar.getInstance(); 
			Payment payment = new Payment();
			payment.setId(rs.getLong("Id"));
			payment.setIsReceivedPayment(rs.getDouble("IsReceivedPayment") > 0.0 ? true : false);
			payment.setPaymentType(rs.getString("PaymentType"));
			payment.setPaymentCategory(rs.getString("PaymentCategory"));
			payment.setDescription(rs.getString("Description"));
			payment.setCurrencyCode(rs.getString("CurrencyCode"));
			payment.setTotalAmount(rs.getDouble("TotalAmount"));
			payment.setPaymentMethodType(rs.getString("PaymentMethodType"));
			payment.setPayerPaymentMethodId(rs.getInt("PayerPaymentMethodId"));
			payment.setPayeePaymentReceiveMethodId(rs.getInt("PayeePaymentReceiveMethodId"));
			payment.setCheckNumber(rs.getString("CheckNumber"));
			cal.setTimeZone(TimeZone.getTimeZone("UTC")); 
			if (rs.getTimestamp("PaymentDateTime") != null) {
				payment.setPaymentDateTime(new DateTime(rs.getTimestamp("PaymentDateTime", cal), DateTimeZone.UTC));
			}
			payment.setPayerAccountId(rs.getInt("PayerAccountId"));
			payment.setPayerAccountName(rs.getString("PayerAccountName"));
			if (rs.getString("PayerContactId") != null) {
				payment.setPayerContactId(UUID.fromString(rs.getString("PayerContactId")));
			}
			payment.setPayerContactName(rs.getString("PayerContactName"));
			if (rs.getString("PayerBillingAddressId") != null) {
				payment.setPayerBillingAddressId(UUID.fromString(rs.getString("PayerBillingAddressId")));
			}
			payment.setPayeeAccountId(rs.getInt("PayeeAccountId"));
			payment.setPayeeAccountName(rs.getString("PayeeAccountName"));
			if (rs.getString("PayeeContactId") != null) {
				payment.setPayeeContactId(UUID.fromString(rs.getString("PayeeContactId")));
			}
			payment.setPayeeContactName(rs.getString("PayeeContactName"));
			if (rs.getString("PayeeBillingAddressId") != null) {
				payment.setPayeeBillingAddressId(UUID.fromString(rs.getString("PayeeBillingAddressId")));
			}
			payment.setDepartmentId(rs.getInt("DepartmentId"));
			payment.setCostCenterNumber(rs.getString("CostCenterNumber"));
			payment.setPrimaryPaymentReceiverEmpId(rs.getInt("PrimaryPaymentReceiverEmpId"));
			payment.setSecondaryPaymentReceiverEmpId(rs.getInt("SecondaryPaymentReceiverEmpId"));
			payment.setPrimaryPaymentPayerEmpId(rs.getInt("PrimaryPaymentPayerEmpId"));
			payment.setSecondaryPaymentPayerEmpId(rs.getInt("SecondaryPaymentPayerEmpId"));
			payment.setNotes(rs.getString("Notes"));
			payment.setOwnerAccountId(rs.getInt("OwnerAccountId"));
			return payment;
		}
	}

	protected RowMapper getRowMapper() {
		return new PaymentMapper();
	}

	protected String getSqlTable() {
		return "Payment";
	}

	// query Payment using OwnerAccountId
	protected final static String strPaymentQueryWithOwnerAccountId = "select " + fieldSelectionForReadPayment +
			" from Payment as o where OwnerAccountId=:OwnerAccountId";

	// get all Payments owned by a specific account id
	@Override
	public List<Payment> findPaymentsByOwnerAccountId(int ownerAccountId) {
		try {
			List<Payment> payments = namedParameterJdbcTemplate.query(
					strPaymentQueryWithOwnerAccountId,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId),
					new PaymentMapper());
			return payments;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByOwerAccountId Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments owned by a specific account id
	@Override
	public List<Payment> findPaymentsByOwnerAccountId2(int ownerAccountId) {
		return findRowsByOwnerAccountId(ownerAccountId);
	}

	// query Payment using Id
	protected final static String strPaymentQueryWithId = "select " + fieldSelectionForReadPayment +
			" from Payment as o where OwnerAccountId=:OwnerAccountId and Id=:Id";

	// get a specific Payment by a given id
	@Override
	public Payment findPaymentById(int ownerAccountId, int id) {
		try {
			Payment payment = namedParameterJdbcTemplate.queryForObject(
					strPaymentQueryWithId,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("Id", id),
					new PaymentMapper());
			return payment;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentById Exception: " + e.getMessage());
			return null;
		}
	}

	// query Payment using IsReceivedPayment
	protected final static String strPaymentQueryWithIsReceivedPayment = "select " + fieldSelectionForReadPayment +
			" from Payment as o where OwnerAccountId=:OwnerAccountId and IsReceivedPayment=:IsReceivedPayment";

	// get all Payments by IsReceived
	@Override
	public List<Payment> findPaymentsByIsReceivedPayment(int ownerAccountId, boolean isReceivedPayment) {
		try {
			List<Payment> payments = namedParameterJdbcTemplate.query(
					strPaymentQueryWithIsReceivedPayment,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).
							addValue("IsReceivedPayment", isReceivedPayment ? 1.0 : 0.0),
					new PaymentMapper());
			return payments;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByIsReceivedPayment Exception: " + e.getMessage());
			return null;
		}
	}

	// query Payment using PaymentType
	protected final static String strPaymentQueryWithPaymentType = "select " + fieldSelectionForReadPayment +
			" from Payment as o where OwnerAccountId=:OwnerAccountId and PaymentType=:PaymentType";

	// get all Payments by PaymentType
	@Override
	public List<Payment> findPaymentsByPaymentType(int ownerAccountId, String paymentType) {
		try {
			List<Payment> payments = namedParameterJdbcTemplate.query(
					strPaymentQueryWithPaymentType,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).
							addValue("PaymentType", paymentType),
					new PaymentMapper());
			return payments;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPaymentType Exception: " + e.getMessage());
			return null;
		}
	}

	// query Payment using PaymentCategory
	protected final static String strPaymentQueryWithPaymentCategory = "select " + fieldSelectionForReadPayment +
			" from Payment as o where OwnerAccountId=:OwnerAccountId and PaymentCategory=:PaymentCategory";

	// get all Payments by PaymentCategory
	@Override
	public List<Payment> findPaymentsByPaymentCategory(int ownerAccountId, String paymentCategory) {
		try {
			List<Payment> payments = namedParameterJdbcTemplate.query(
					strPaymentQueryWithPaymentCategory,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).
							addValue("PaymentCategory", paymentCategory),
					new PaymentMapper());
			return payments;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPaymentCategory Exception: " + e.getMessage());
			return null;
		}
	}

	/*
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
	*/

	/**
	 * Set SQL Parameters used for creating Project
	 * @param project
	 * @param bNew
	 * @return
	 */
	/*
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
			int retId = insertPayment.executeAndReturnKey(parameters).intValue();
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
	*/

}
