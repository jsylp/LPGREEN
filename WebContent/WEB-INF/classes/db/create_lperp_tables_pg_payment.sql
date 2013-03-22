------------------------------------------------------------------------------
-- DROP TABLEs
------------------------------------------------------------------------------
DROP TABLE IF EXISTS Payment;

------------------------------------------------------------------------------
-- Create Table: Payment
------------------------------------------------------------------------------
CREATE TABLE Payment (
	Id                              bigserial NOT NULL PRIMARY KEY UNIQUE,
	IsReceivedPayment               decimal(1,0) NOT NULL,
	PaymentType                     varchar(50),
	PaymentCategory                 varchar(50) NOT NULL,
	Description                     varchar(512) NOT NULL,
	CurrencyCode                    varchar(3) NOT NULL,
	TotalAmount                     decimal(14,2) NOT NULL,
	PaymentMethodType               varchar(30),
	PayerPaymentMethodId            int,
	PayeePaymentReceiveMethodId     int,
	CheckNumber                     varchar(32),
	PaymentDateTime                 timestamp without time zone,
	PayerAccountId                  int REFERENCES Account(Id),
	PayerAccountName                varchar(255),
	PayerContactId                  uuid REFERENCES Contact(UniqueId),
	PayerContactName                varchar(255),
	PayerBillingAddressId           uuid,
	PayeeAccountId                  int REFERENCES Account(Id),
	PayeeAccountName                varchar(255),
	PayeeContactId                  uuid REFERENCES Contact(UniqueId),
	PayeeContactName                varchar(255),
	PayeeBillingAddressId           uuid,
	DepartmentId                    int REFERENCES Department(Id),
	CostCenterNumber                varchar(32),
	PrimaryPaymentReceiverEmpId     int REFERENCES Employee(Id),
	SecondaryPaymentReceiverEmpId   int REFERENCES Employee(Id),
	PrimaryPaymentPayerEmpId        int REFERENCES Employee(Id),
	SecondaryPaymentPayerEmpId      int REFERENCES Employee(Id),
	Notes                           varchar(1000),
	OwnerId                         uuid,
	OwnerAccountId                  int REFERENCES Account(Id),
	CreatedDate                     timestamp without time zone DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'UTC'),
	CreatedById                     uuid,
	LastModifiedDate                timestamp without time zone DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'UTC'),
	LastModifiedById                uuid
);

------------------------------------------------------------------------------
-- Load Payment
------------------------------------------------------------------------------
INSERT INTO Payment(IsReceivedPayment, PaymentType, PaymentCategory, Description,
		CurrencyCode, TotalAmount, PaymentMethodType, CheckNumber, PaymentDateTime,
		PayerAccountId, PayerAccountName, PayerContactId, PayerContactName, PayerBillingAddressId,
		PayeeAccountId, PayeeAccountName, PayeeContactId, PayeeContactName, PayeeBillingAddressId,
		DepartmentId, CostCenterNumber, PrimaryPaymentReceiverEmpId, SecondaryPaymentReceiverEmpId,
		PrimaryPaymentPayerEmpId, SecondaryPaymentPayerEmpId, Notes, OwnerAccountId)
	VALUES('1', 'Corporate', 'Purchase', 'Computer',
	       'USD', '1500.00', 'Check', '101', '2013-01-05 08:08:08',
	       '2', 'Allen Company', '07771AE4-236A-49d3-A49E-B1F9E1934D20', '', '07771AE4-236A-49d3-A49F-B1F9E1934D20',
	       '2', 'Allen Company', '07771AE4-236A-49d3-A49E-B1F9E1934D23', '', '07771AE4-236A-49d3-A49F-B1F9E1934D23',
	       '5', '056-112-001', '7', '6', '4', '4', 'Computer Purchase', '1');

INSERT INTO Payment(IsReceivedPayment, PaymentType, PaymentCategory, Description,
		CurrencyCode, TotalAmount, PaymentMethodType, CheckNumber, PaymentDateTime,
		PayerAccountId, PayerAccountName, PayerContactId, PayerContactName, PayerBillingAddressId,
		PayeeAccountId, PayeeAccountName, PayeeContactId, PayeeContactName, PayeeBillingAddressId,
		DepartmentId, CostCenterNumber, PrimaryPaymentReceiverEmpId, SecondaryPaymentReceiverEmpId,
		PrimaryPaymentPayerEmpId, SecondaryPaymentPayerEmpId, Notes, OwnerAccountId)
	VALUES('0', 'Reimburse', 'Travel', 'Hotel',
	       'USD', '150.00', 'Check', '102', '2013-01-12 03:08:08',
	       '2', 'Allen Company', '07771AE4-236A-49d3-A49E-B1F9E1934D20', '', '07771AE4-236A-49d3-A49F-B1F9E1934D20',
	       '2', 'Allen Company', '07771AE4-236A-49d3-A49E-B1F9E1934D22', '', '07771AE4-236A-49d3-A49F-B1F9E1934D22',
	       '6', '056-112-105', '6', '5', '4', '3', 'Offsite training', '1');

INSERT INTO Payment(IsReceivedPayment, PaymentType, PaymentCategory, Description,
		CurrencyCode, TotalAmount, PaymentMethodType, CheckNumber, PaymentDateTime,
		PayerAccountId, PayerAccountName, PayerContactId, PayerContactName, PayerBillingAddressId,
		PayeeAccountId, PayeeAccountName, PayeeContactId, PayeeContactName, PayeeBillingAddressId,
		DepartmentId, CostCenterNumber, PrimaryPaymentReceiverEmpId, SecondaryPaymentReceiverEmpId,
		PrimaryPaymentPayerEmpId, SecondaryPaymentPayerEmpId, Notes, OwnerAccountId)
	VALUES('1', 'Corporate', 'Sales', 'Conference',
	       'USD', '587.00', 'Card', '2300-1100-2312-8888', '2013-02-13 10:08:08',
	       '2', 'Allen Company', '07771AE4-236A-49d3-A49E-B1F9E1934D20', '', '07771AE4-236A-49d3-A49F-B1F9E1934D20',
	       '2', 'Allen Company', '07771AE4-236A-49d3-A49E-B1F9E1934D21', '', '07771AE4-236A-49d3-A49F-B1F9E1934D21',
	       '7', '056-111-011', '5', '5', '3', '3', 'Marketing sales', '1');

INSERT INTO Payment(IsReceivedPayment, PaymentType, PaymentCategory, Description,
		CurrencyCode, TotalAmount, PaymentMethodType, CheckNumber, PaymentDateTime,
		PayerAccountId, PayerAccountName, PayerContactId, PayerContactName, PayerBillingAddressId,
		PayeeAccountId, PayeeAccountName, PayeeContactId, PayeeContactName, PayeeBillingAddressId,
		DepartmentId, CostCenterNumber, PrimaryPaymentReceiverEmpId, SecondaryPaymentReceiverEmpId,
		PrimaryPaymentPayerEmpId, SecondaryPaymentPayerEmpId, Notes, OwnerAccountId)
	VALUES('1', 'Personal', 'Tuition', 'Online class',
	       'USD', '350.00', 'Card', '2300-1100-2312-8887', '2013-03-13 11:08:08',
	       '3', 'Bobby Company', '07771AE4-236A-49d3-A49E-B1F9E1934D30', '', '07771AE4-236A-49d3-A49F-B1F9E1934D30',
	       '3', 'Bobby Company', '07771AE4-236A-49d3-A49E-B1F9E1934D31', '', '07771AE4-236A-49d3-A49F-B1F9E1934D31',
	       '8', '712-565-321', '11', '11', '8', '8', 'Semester grade B+', '1');
