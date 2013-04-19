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
	UMCode                  varchar(20),
	MinQuantity             int,
	MaxQuantity             int,
	FromTime                char(5),
	ToTime                  char(5),
	IsWeekend               decimal(1,0) default 0,
	OwnerAccountId          int
);
CREATE UNIQUE INDEX LCM_CODE_indx ON LaborCostMethod(OwnerAccountId, upper(CostMethodCode));
