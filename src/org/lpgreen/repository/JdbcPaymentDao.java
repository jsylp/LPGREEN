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

import org.lpgreen.domain.Payment;
import org.lpgreen.util.MustOverrideException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
public class JdbcPaymentDao extends LPJdbcGeneric<Payment> implements PaymentDao {

	public void setDataSource(DataSource dataSource)
			throws MustOverrideException {
		try {
			super.setDataSource(dataSource);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.setDataSource Exception: " + e.getMessage());
			throw e;
		}
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

	// field selection for update
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

	// RowMapper class
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

	// Override to return the SQL table name
	protected String getSqlTable() {
		return "Payment";
	}

	// Override to return the field selection for read
	protected String getFieldSelectionForRead() {
		return fieldSelectionForReadPayment;
	}

	// Override to return the filed selection for update
	protected String getFieldSelectionForUpdate() {
		return fieldSetForUpdatePayment;
	}

	// Override to return the RowMapper
	protected RowMapper<Payment> getRowMapper() {
		return new PaymentMapper();
	}

	// Override to return MapSqlParameterSource for creating Payment
	protected MapSqlParameterSource getDomainObjectMapSqlParameterSource(Payment payment, boolean bNew) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		if (!bNew) {
			if (payment.getId() > 0)
				parameters.addValue("Id", payment.getId());	// auto generated when insert a Payment, use it as the primary key when update it
			else
				parameters.addValue("Id", null);
		}
		parameters.addValue("IsReceivedPayment", payment.getIsReceivedPayment() ? 1.0 : 0.0);
		parameters.addValue("PaymentType", payment.getPaymentType());
		parameters.addValue("PaymentCategory", payment.getPaymentCategory());
		parameters.addValue("Description", payment.getDescription());
		parameters.addValue("CurrencyCode", payment.getCurrencyCode());
		parameters.addValue("TotalAmount", payment.getTotalAmount());
		parameters.addValue("PaymentMethodType", payment.getPaymentMethodType());
		if (payment.getPayerPaymentMethodId() > 0) {
			parameters.addValue("PayerPaymentMethodId", payment.getPayerPaymentMethodId());
		}
		else {
			parameters.addValue("PayerPaymentMethodId", null);
		}
		if (payment.getPayeePaymentReceiveMethodId() > 0) {
			parameters.addValue("PayeePaymentReceiveMethodId", payment.getPayeePaymentReceiveMethodId());
		}
		else {
			parameters.addValue("PayeePaymentReceiveMethodId", null);
		}
		parameters.addValue("CheckNumber", payment.getCheckNumber());
		if (payment.getPaymentDateTime() != null) {
			parameters.addValue("PaymentDateTime", payment.getPaymentDateTime().toCalendar(null), Types.TIMESTAMP);
		}
		else {
			parameters.addValue("PaymentDateTime", null);
		}
		if (payment.getPayerAccountId() > 0) {
			parameters.addValue("PayerAccountId", payment.getPayerAccountId());
		}
		else {
			parameters.addValue("PayerAccountId", null);
		}
		parameters.addValue("PayerAccountName", payment.getPayerAccountName());
		parameters.addValue("PayerContactId", payment.getPayerContactId());
		parameters.addValue("PayerContactName", payment.getPayerContactName());
		parameters.addValue("PayerBillingAddressId", payment.getPayerBillingAddressId());
		if (payment.getPayeeAccountId() > 0) {
			parameters.addValue("PayeeAccountId", payment.getPayeeAccountId());
		}
		else {
			parameters.addValue("PayeeAccountId", null);
		}
		parameters.addValue("PayeeAccountName", payment.getPayeeAccountName());
		parameters.addValue("PayeeContactId", payment.getPayeeContactId());
		parameters.addValue("PayeeContactName", payment.getPayeeContactName());
		parameters.addValue("PayeeBillingAddressId", payment.getPayeeBillingAddressId());
		if (payment.getDepartmentId() > 0) {
			parameters.addValue("DepartmentId", payment.getDepartmentId());
		}
		else {
			parameters.addValue("DepartmentId", null);
		}
		parameters.addValue("CostCenterNumber", payment.getCostCenterNumber());
		if (payment.getPrimaryPaymentReceiverEmpId() > 0) {
			parameters.addValue("PrimaryPaymentReceiverEmpId", payment.getPrimaryPaymentReceiverEmpId());
		}
		else {
			parameters.addValue("PrimaryPaymentReceiverEmpId", null);
		}
		if (payment.getSecondaryPaymentReceiverEmpId() > 0) {
			parameters.addValue("SecondaryPaymentReceiverEmpId", payment.getSecondaryPaymentReceiverEmpId());
		}
		else {
			parameters.addValue("SecondaryPaymentReceiverEmpId", null);
		}
		if (payment.getPrimaryPaymentPayerEmpId() > 0) {
			parameters.addValue("PrimaryPaymentPayerEmpId", payment.getPrimaryPaymentPayerEmpId());
		}
		else {
			parameters.addValue("PrimaryPaymentPayerEmpId", null);
		}
		if (payment.getSecondaryPaymentPayerEmpId() > 0) {
			parameters.addValue("SecondaryPaymentPayerEmpId", payment.getSecondaryPaymentPayerEmpId());
		}
		else {
			parameters.addValue("SecondaryPaymentPayerEmpId", null);
		}
		parameters.addValue("Notes", payment.getNotes());
		parameters.addValue("OwnerId", payment.getOwnerId());
		if (payment.getOwnerAccountId() > 0)
			parameters.addValue("OwnerAccountId", payment.getOwnerAccountId());
		else
			parameters.addValue("OwnerAccountId", null);
		return parameters;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// Payment related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	// get all Payments owned by a specific owner account id
	@Override
	public List<Payment> findPaymentsByOwnerAccountId(int ownerAccountId, Set<String> currentStatuses) {
		try {
			return findDomainObjectsByOwnerAccountId(ownerAccountId, null, currentStatuses, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByOwerAccountId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByOwerAccountId Exception: " + e.getMessage());
			return null;
		}
	}

	// get a specific Payment by a given database id
	@Override
	public Payment findPaymentById(long id) {
		try {
			return findDomainObjectById(id, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentById MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentById Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by IsReceivedPayment
	@Override
	public List<Payment> findPaymentsByIsReceivedPayment(int ownerAccountId, boolean isReceivedPayment) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "IsReceivedPayment", isReceivedPayment, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByIsReceivedPayment MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByIsReceivedPayment Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by PaymentType
	@Override
	public List<Payment> findPaymentsByPaymentType(int ownerAccountId, String paymentType) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "PaymentType", paymentType, true, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPaymentType MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPaymentType Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by PaymentCategory
	@Override
	public List<Payment> findPaymentsByPaymentCategory(int ownerAccountId, String paymentCategory) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "PaymentCategory", paymentCategory, true, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPaymentCategory MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPaymentCategory Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by Description
	@Override
	public List<Payment> findPaymentsByDescription(int ownerAccountId, String description) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "Description", description, true, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByDescription MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByDescription Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by CurrencyCode
	@Override
	public List<Payment> findPaymentsByCurrencyCode(int ownerAccountId, String currencyCode) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "CurrencyCode", currencyCode, true, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByCurrencyCode MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByCurrencyCode Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by PaymentMethodType
	@Override
	public List<Payment> findPaymentsByPaymentMethodType(int ownerAccountId, String paymentMethodType) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "PaymentMethodType", paymentMethodType, true, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPaymentMethodType MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPaymentMethodType Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by PayerPaymentMethodId
	@Override
	public List<Payment> findPaymentsByPayerPaymentMethodId(int ownerAccountId, int payerPaymentMethodId) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "PayerPaymentMethodId", payerPaymentMethodId, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayerPaymentMethodId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayerPaymentMethodId Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by PayeePaymentReceiveMethodId
	@Override
	public List<Payment> findPaymentsByPayeePaymentReceiveMethodId(int ownerAccountId, int payeePaymentReceiveMethodId) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "PayeePaymentReceiveMethodId", payeePaymentReceiveMethodId, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayeePaymentReceiveMethodId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayeePaymentReceiveMethodId Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by CheckNumber range
	@Override
	public List<Payment> findPaymentsByCheckNumberRange(int ownerAccountId,
			String startCheckNumber, String endCheckNumber) {
		try {
			return findDomainObjectsByColumnValRange(ownerAccountId, null, "CheckNumber", startCheckNumber, endCheckNumber, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByCheckNumberRange MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByCheckNumberRange Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by PaymentDateTime range
	@Override
	public List<Payment> findPaymentsByPaymentDateTimeRange(int ownerAccountId,
			DateTime startDateTime, DateTime endDateTime) {
		try {
			return findDomainObjectsByDateTimeRange(ownerAccountId, null, "PaymentDateTime", startDateTime, endDateTime, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPaymentDateTimeRange MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPaymentDateTimeRange Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by PayerAccountId
	@Override
	public List<Payment> findPaymentsByPayerAccountId(int ownerAccountId, int payerAccountId) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "PayerAccountId", payerAccountId, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayerAccountId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayerAccountId Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by PayerAccountName
	@Override
	public List<Payment> findPaymentsByPayerAccountName(int ownerAccountId, String payerAccountName) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "PayerAccountName", payerAccountName, true, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayerAccountName MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayerAccountName Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by PayerContactId
	@Override
	public List<Payment> findPaymentsByPayerContactId(int ownerAccountId, UUID payerContactId) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "PayerContactId", payerContactId, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayerContactId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayerContactId Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by PayerContactName
	@Override
	public List<Payment> findPaymentsByPayerContactName(int ownerAccountId, String payerContactName) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "PayerContactName", payerContactName, true, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayerContactName MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayerContactName Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by PayerBillingAddressId
	@Override
	public List<Payment> findPaymentsByPayerBillingAddressId(int ownerAccountId, UUID payerBillingAddressId) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "PayerBillingAddressId", payerBillingAddressId, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayerBillingAddressId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayerBillingAddressId Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by PayeeAccountId
	@Override
	public List<Payment> findPaymentsByPayeeAccountId(int ownerAccountId, int payeeAccountId) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "PayeeAccountId", payeeAccountId, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayeeAccountId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayeeAccountId Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by PayeeAccountName
	@Override
	public List<Payment> findPaymentsByPayeeAccountName(int ownerAccountId, String payeeAccountName) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "PayeeAccountName", payeeAccountName, true, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayeeAccountName MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayeeAccountName Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by PayeeContactId
	@Override
	public List<Payment> findPaymentsByPayeeContactId(int ownerAccountId, UUID payeeContactId) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "PayeeContactId", payeeContactId, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayeeContactId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayeeContactId Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by PayeeContactName
	@Override
	public List<Payment> findPaymentsByPayeeContactName(int ownerAccountId, String payeeContactName) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "PayeeContactName", payeeContactName, true, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayeeContactName MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayeeContactName Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by PayeeBillingAddressId
	@Override
	public List<Payment> findPaymentsByPayeeBillingAddressId(int ownerAccountId, UUID payeeBillingAddressId) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "PayeeBillingAddressId", payeeBillingAddressId, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayeeBillingAddressId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPayeeBillingAddressId Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by DepartmentId
	@Override
	public List<Payment> findPaymentsByDepartmentId(int ownerAccountId, int departmentId) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "DepartmentId", departmentId, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByDepartmentId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByDepartmentId Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by CostCenterNumber
	@Override
	public List<Payment> findPaymentsByCostCenterNumber(int ownerAccountId, String costCenterNumber) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "CostCenterNumber", costCenterNumber, true, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByCostCenterNumber MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByCostCenterNumber Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by PrimaryPaymentReceiverEmpId
	@Override
	public List<Payment> findPaymentsByPrimaryPaymentReceiverEmpId(int ownerAccountId, int primaryPaymentReceiverEmpId) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "PrimaryPaymentReceiverEmpId", primaryPaymentReceiverEmpId, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPrimaryPaymentReceiverEmpId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPrimaryPaymentReceiverEmpId Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by SecondaryPaymentReceiverEmpId
	@Override
	public List<Payment> findPaymentsBySecondaryPaymentReceiverEmpId(int ownerAccountId, int secondaryPaymentReceiverEmpId) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "SecondaryPaymentReceiverEmpId", secondaryPaymentReceiverEmpId, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsBySecondaryPaymentReceiverEmpId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsBySecondaryPaymentReceiverEmpId Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by PrimaryPaymentPayerEmpId
	@Override
	public List<Payment> findPaymentsByPrimaryPaymentPayerEmpId(int ownerAccountId, int primaryPaymentPayerEmpId) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "PrimaryPaymentPayerEmpId", primaryPaymentPayerEmpId, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPrimaryPaymentPayerEmpId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByPrimaryPaymentPayerEmpId Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by SecondaryPaymentPayerEmpId
	@Override
	public List<Payment> findPaymentsBySecondaryPaymentPayerEmpId(int ownerAccountId, int secondaryPaymentPayerEmpId) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "SecondaryPaymentPayerEmpId", secondaryPaymentPayerEmpId, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsBySecondaryPaymentPayerEmpId MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsBySecondaryPaymentPayerEmpId Exception: " + e.getMessage());
			return null;
		}
	}

	// get all Payments by Notes
	@Override
	public List<Payment> findPaymentsByNotes(int ownerAccountId, String notes) {
		try {
			return findDomainObjectsByColumnVal(ownerAccountId, null, "Notes", notes, true, null, null);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.findPaymentsByNotes MustOverrideException: " + e.getMessage());
			return null;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.findPaymentsByNotes Exception: " + e.getMessage());
			return null;
		}
	}

	// Add a Payment. Return the generated database id
	@Override
	public int addPayment(Payment payment)
			throws DuplicateKeyException, Exception {
		try {
			// insert Project record
			int retId = addDomainObject(payment);
			payment.setId(retId);
			return retId;
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.addPayment MustOverrideException: " + e.getMessage());
			return -1;
		}
		catch (DuplicateKeyException e) {
			System.out.println("JdbcPaymentDao.addPayment DuplicateKeyException: " + e.getMessage());
			throw e;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.addPayment Exception: " + e.getMessage());
			throw e;
		}
	}

	// Save changes of an existing Payment object. Return the # of records updated
	@Override
	public int savePayment(Payment payment)
			throws DuplicateKeyException, Exception {
		try {
			return saveDomainObject(payment);
		}
		catch (MustOverrideException e) {
			System.out.println("JdbcPaymentDao.savePayment MustOverrideException: " + e.getMessage());
			return -1;
		}
		catch (DuplicateKeyException e) {
			System.out.println("JdbcPaymentDao.savePayment DuplicateKeyException: " + e.getMessage());
			throw e;
		}
		catch (Exception e) {
			System.out.println("JdbcPaymentDao.savePayment Exception: " + e.getMessage());
			throw e;
		}
	}

	// Delete a Payment object. Return the # of records deleted
	@Override
	public int deletePayment(int ownerAccountId, long id)
			throws Exception {
		try {
			return deleteDomainObject(ownerAccountId, id);
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
