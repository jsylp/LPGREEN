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
	UnitQuantityUMCode      varchar(20),
	RangeStartQuantity      int,
	RangeEndQuantity        int,
	StartWeekDayTime        char(8),
	Duration                char(5),
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
INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Description, UnitQuantity,
		UMCode, MinQuantity, MaxQuantity, StartTime, Duration, OwnerAccountId)
	VALUES('Exempt', 'PerMonth', 'Salaried Employee', 1.0,
	       'Month', 6, 12, '01-08:00', '08:00', 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Description, UnitQuantity,
		UMCode, MinQuantity, MaxQuantity, StartTime, Duration, OwnerAccountId)
	VALUES('NonExempt', 'PerHour', 'Consultant', 1.0,
	       'Month', 6, 12, '01-08:00', '08:00', 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Description, UnitQuantity,
		UMCode, MinQuantity, MaxQuantity, StartTime, Duration, OwnerAccountId)
	VALUES('Exempt', 'PerMonth', 'Salaried Employee', 1.0,
	       'Month', 6, 12, '01-08:00', '08:00', 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Description, UnitQuantity,
		UMCode, MinQuantity, MaxQuantity, StartTime, Duration, OwnerAccountId)
	VALUES('Exempt', 'PerMonth', 'Salaried Employee', 1.0,
	       'Month', 6, 12, '01-08:00', '08:00', 1);

INSERT INTO LaborCostMethod(CostMethodCode, CostMethodType, Description, UnitQuantity,
		UMCode, MinQuantity, MaxQuantity, StartTime, Duration, OwnerAccountId)
	VALUES('Exempt', 'PerMonth', 'Salaried Employee', 1.0,
	       'Month', 6, 12, '01-08:00', '08:00', 1);
