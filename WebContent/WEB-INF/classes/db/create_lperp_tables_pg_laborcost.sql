------------------------------------------------------------------------------
-- DROP TABLEs
------------------------------------------------------------------------------
DROP TABLE IF EXISTS LaborCostMethod;

------------------------------------------------------------------------------
-- Create TABLEs
------------------------------------------------------------------------------
-- Create Table: LaborCostMethod
CREATE TABLE LaborCostMethod (
	Id                      serial NOT NULL PRIMARY KEY UNIQUE,
	CostMethodCode          varchar(30) NOT NULL,
	CostMethodType          varchar(30) NOT NULL,
	Status                  varchar(30) NOT NULL,
	Description             varchar(255),
	UnitQuantity            decimal(19,4),
	UnitQuantityCost        decimal(19,4),
	UnitQuantityUMCode      varchar(20),
	UserDefinedUMCode       varchar(20),
	Multiplier              decimal(10,4),
	RangeStartQuantity      int,
	RangeEndQuantity        int,
	OTStartWeekdayTime      char(5),
	OTDuration              char(5),
	OwnerId                 uuid,
	OwnerAccountId          int REFERENCES Account(Id),
	CreatedDate             timestamp without time zone DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'UTC'),
	CreatedById             uuid,
	LastModifiedDate        timestamp without time zone DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'UTC'),
	LastModifiedById        uuid
);
CREATE UNIQUE INDEX LCM_CODE_indx ON LaborCostMethod(OwnerAccountId, upper(CostMethodCode));

------------------------------------------------------------------------------
-- Load LaborCostMethod
------------------------------------------------------------------------------
INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Status, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0001', 'PerDuration', 'Active', 'Monthly full time employee salary',
	       1.0, null, 'Month', null,
	       null, null, null, null, null, 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Status, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0002', 'PerDuration', 'Active', 'Bi monthly full time employee salary',
	       0.5, null, 'Month', null,
	       null, null, null, null, null, 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Status, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0003', 'PerDuration', 'Inactive', 'Weekly full time employee salary',
	       1.0, null, 'Week', null,
	       null, null, null, null, null, 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Status, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0004', 'PerTask', 'Inactive', 'Task for setup test lab',
	       1.0, 15000.00, null, 'task',
	       null, null, null, null, null, 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Status, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0005', 'PerTask', 'Active', 'Task for setup computers',
	       1.0, 6750.00, null, 'task',
	       null, null, null, null, null, 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Status, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0006', 'PerQuantity', 'Active', 'Assemble 100 notebook computers',
	       100.0, 25000.00, null, 'piece',
	       null, null, null, null, null, 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Status, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0007', 'PerQuantity', 'Inactive', 'Make 10000 gallon gas',
	       10000.0, 15000.00, 'gallon', null,
	       null, null, null, null, null, 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Status, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0008', 'PerQuantityWithTier', 'Active', 'Make first 500 of 1000 gallon milk',
	       1, 1.25, 'gallon', null,
	       null, 1, 500, null, null, 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Status, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0009', 'PerQuantityWithTier', 'Active', 'Make next 250 gallon milk',
	       1, 1.00, 'gallon', null,
	       null, 501, 750, null, null, 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Status, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0010', 'PerQuantityWithTier', 'Active', 'Make last 250 gallon milk',
	       1, 0.75, 'gallon', null,
	       null, 751, 1000, null, null, 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Status, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0011', 'PerPercentValue', 'Active', 'Real Estate Transaction',
	       null, null, null, null,
	       0.06, null, null, null, null, 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Status, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0012', 'PerPercentProfit', 'Active', 'Used Car Sales Transaction',
	       null, null, null, null,
	       0.10, null, null, null, null, 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Status, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0013', 'PerHoliday', 'Active', 'Work during the holiday',
	       null, null, null, null,
	       2.0, null, null, null, null, 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Status, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0014', 'PerOvertime', 'Active', 'First shift overtime',
	       null, null, null, null,
	       1.5, null, null, '16:00', '16:00', 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Status, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0015', 'PerOvertime', 'Active', 'Second shift overtime',
	       null, null, null, null,
	       1.75, null, null, '00:00', '16:00', 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Status, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0016', 'PerOvertime', 'Active', 'Third shift overtime',
	       null, null, null, null,
	       2.00, null, null, '08:00', '16:00', 1);
