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
	Description             varchar(255),
	UnitQuantity            decimal(19,4),
	UnitQuantityCost        decimal(19,4),
	UnitQuantityUMCode      varchar(20),
	UserDefinedUMCode       varchar(20),
	Multiplier              decimal(10,4),
	RangeStartQuantity      int,
	RangeEndQuantity        int,
	OTStartWeekdayTime      char(8),
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
INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0001', 'PerDuration', 'Monthly full time employee salary',
	       1.0, null, 'Month', null,
	       null, null, null, null, null, 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0002', 'PerDuration', 'Bi monthly full time employee salary',
	       0.5, null, 'Month', null,
	       null, null, null, null, null, 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0003', 'PerDuration', 'Weekly full time employee salary',
	       1.0, null, 'Week', null,
	       null, null, null, null, null, 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0004', 'PerTask', 'Task for setup test lab',
	       1.0, 15000.00, null, 'task',
	       null, null, null, null, null, 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0005', 'PerTask', 'Task for setup computers',
	       1.0, 6750.00, null, 'task',
	       null, null, null, null, null, 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0006', 'PerQuantity', 'Assemble 100 notebook computers',
	       100.0, 25000.00, null, 'piece',
	       null, null, null, null, null, 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0007', 'PerQuantity', 'Make 10000 gallon gas',
	       10000.0, 15000.00, 'gallon', null,
	       null, null, null, null, null, 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Description,
	       UnitQuantity, UnitQuantityCost, UnitQuantityUMCode, UserDefinedUMCode,
	       Multiplier, RangeStartQuantity, RangeEndQuantity,
	       OTStartWeekDayTime, OTDuration, OwnerAccountId)
	VALUES('CMC-0008', 'PerQuantityWithTier', 'Make 10000 gallon gas',
	       10000.0, 15000.00, 'gallon', null,
	       null, null, null, null, null, 1);
