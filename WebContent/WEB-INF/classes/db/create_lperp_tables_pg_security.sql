------------------------------------------------------------------------------
-- DROP TABLEs
------------------------------------------------------------------------------
DROP TABLE IF EXISTS AccessControlList;
DROP TABLE IF EXISTS OperationRight;
DROP TABLE IF EXISTS LoginUserRoles;
DROP TABLE IF EXISTS RoleHierarchy;
DROP TABLE IF EXISTS Role;
DROP TABLE IF EXISTS LoginUser;

------------------------------------------------------------------------------
-- Create TABLEs
------------------------------------------------------------------------------
-- Create Table: LoginUser
CREATE TABLE LoginUser (
	Id                              serial NOT NULL PRIMARY KEY UNIQUE,
	UserName                        varchar(255) NOT NULL UNIQUE,
	Password                        varchar(512) NOT NULL,
	SysGenedPwd                     decimal(1) default 1,
	ContactUuid                     uuid REFERENCES Contact(UniqueId) NOT NULL UNIQUE,
	Enabled                         decimal(1) default 1,
	SecurityQuestion1               varchar(255),					
	SecurityAnswer1                 varchar(255),					
	SecurityQuestion2               varchar(255),					
	SecurityAnswer2                 varchar(255),					
	SecurityQuestion3               varchar(255),					
	SecurityAnswer3                 varchar(255),					
	CreatedDate                     timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
	CreatedById                     uuid REFERENCES Contact(UniqueId), 
	LastModifiedDate                timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
	LastModifiedById                uuid REFERENCES Contact(UniqueId)
);
CREATE INDEX LU_UN_indx ON LoginUser(UserName);
CREATE INDEX LU_CID_indx ON LoginUser(ContactUuid);

-- Create Table: Role
CREATE TABLE Role (
	Id                              serial NOT NULL PRIMARY KEY UNIQUE,
	RoleName                        varchar(255) NOT NULL UNIQUE,
	Description                     varchar(512),
	OwnerAccountId                  int REFERENCES Account (Id),
	UNIQUE (OwnerAccountId, RoleName)
);
CREATE INDEX ROLE_OAID_indx ON Role(OwnerAccountId NULLS FIRST);

-- Create Table: RoleHierarchy
CREATE TABLE RoleHierarchy (
	RoleId                          int REFERENCES Role(Id) NOT NULL,
	IncludedRoleId                  int REFERENCES Role(Id) NOT NULL,
	OwnerAccountId                  int REFERENCES Account (Id),
	UNIQUE (RoleId, IncludedRoleId)
);

-- Create Table: LoginUserRoles
CREATE TABLE LoginUserRoles (
	LoginUserId                     int REFERENCES LoginUser(Id) NOT NULL,
	RoleId                          int REFERENCES Role(Id) NOT NULL,
	OwnerAccountId                  int REFERENCES Account (Id),
	UNIQUE (LoginUserId, RoleId)
);

-- Create Table: OperationRight
CREATE TABLE OperationRight (
	Id                              serial NOT NULL PRIMARY KEY UNIQUE,
	OperationName                   varchar(255) NOT NULL,
	Description                     varchar(512),
	OwnerAccountId                  int REFERENCES Account (Id),
	UNIQUE (OwnerAccountId, OperationName)
);
CREATE INDEX RIGHT_OAID_indx ON OperationRight(OwnerAccountId NULLS FIRST);

-- Create Table: AccessControlList
CREATE TABLE AccessControlList (
	RoleId                          int REFERENCES Role(Id) NOT NULL,
	ObjectName                      varchar(255) NOT NULL,
	OperationRightId                int REFERENCES OperationRight(Id) NOT NULL,
	OwnerAccountId                  int REFERENCES Account (Id),
	UNIQUE (OwnerAccountId, RoleId, ObjectName, OperationRightId)
);
CREATE INDEX ACL_OAID_indx ON AccessControlList(OwnerAccountId NULLS FIRST);

------------------------------------------------------------------------------
-- Load testing data
------------------------------------------------------------------------------

------------------------------------------------------------------------------
-- Load LoginUser
------------------------------------------------------------------------------
INSERT INTO LoginUser(UserName, Password, ContactUuid)
	VALUES('root',    'Rootpassword',  '07771AE4-236A-49d3-A49E-B1F9E1934D10');
INSERT INTO LoginUser(UserName, Password, ContactUuid)
	VALUES('lpgreen', 'Greenassword',  '07771AE4-236A-49d3-A49E-B1F9E1934D11');
INSERT INTO LoginUser(UserName, Password, ContactUuid)
	VALUES('lpsec',   'Lpsecpassword', '07771AE4-236A-49d3-A49E-B1F9E1934D12');

INSERT INTO LoginUser(UserName, Password, ContactUuid)
	VALUES('allen',   'Allenpassword', '07771AE4-236A-49d3-A49E-B1F9E1934D20');
INSERT INTO LoginUser(UserName, Password, ContactUuid)
	VALUES('adam',    'Adampassword',  '07771AE4-236A-49d3-A49E-B1F9E1934D21');
INSERT INTO LoginUser(UserName, Password, ContactUuid)
	VALUES('alex',    'Alexpassword',  '07771AE4-236A-49d3-A49E-B1F9E1934D22');
INSERT INTO LoginUser(UserName, Password, ContactUuid)
	VALUES('alan',    'Alanpassword',  '07771AE4-236A-49d3-A49E-B1F9E1934D23');

INSERT INTO LoginUser(UserName, Password, ContactUuid)
	VALUES('bobby',   'BobbyPassword', '07771AE4-236A-49d3-A49E-B1F9E1934D30');
INSERT INTO LoginUser(UserName, Password, ContactUuid)
	VALUES('bill',    'Billpassword',  '07771AE4-236A-49d3-A49E-B1F9E1934D31');
INSERT INTO LoginUser(UserName, Password, ContactUuid)
	VALUES('beth',    'Bethpassword',  '07771AE4-236A-49d3-A49E-B1F9E1934D32');
INSERT INTO LoginUser(UserName, Password, ContactUuid)
	VALUES('bart',    'Bartpassword',  '07771AE4-236A-49d3-A49E-B1F9E1934D33');

INSERT INTO LoginUser(UserName, Password, ContactUuid)
	VALUES('cathy',   'Cathypassword', '07771AE4-236A-49d3-A49E-B1F9E1934D40');
INSERT INTO LoginUser(UserName, Password, ContactUuid)
	VALUES('carl',    'Carlpassword',  '07771AE4-236A-49d3-A49E-B1F9E1934D41');
INSERT INTO LoginUser(UserName, Password, ContactUuid)
	VALUES('cate',    'Catepassword',  '07771AE4-236A-49d3-A49E-B1F9E1934D42');
INSERT INTO LoginUser(UserName, Password, ContactUuid)
	VALUES('carol',   'Carolpassword', '07771AE4-236A-49d3-A49E-B1F9E1934D43');

------------------------------------------------------------------------------
-- Load Role for LogixPath (OwnerAccountId = 1)
------------------------------------------------------------------------------
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_LP_SUPERADMIN',
	       'LP super system admin role',
	       1);
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_LP_SYSADMIN',
	       'LP system admin role',
	       1);
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_LP_SITE_ADMIN',
	       'LP system site admin role',
	       1);
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_LP_SITE_USERADMIN',
	       'LP system site user admin role',
	       1);
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_LP_SITE_SUPERVISOR',
	       'LP system site supervisor role',
	       1);
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_LP_SITE_USER',
	       'LP system site user role',
	       1);
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_LP_SITE_PARTNER',
	       'LP system site partnet role',
	       1);

------------------------------------------------------------------------------
-- Load Role for Allen Company
------------------------------------------------------------------------------
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_ALLEN_CO_SUPERADMIN',
	       'Allen company super system admin role',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_ALLEN_CO_SYSADMIN',
	       'Allen company system admin role',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_ALLEN_CO_SITE_ADMIN',
	       'Allen company system site admin role',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_ALLEN_CO_SITE_USERADMIN',
	       'Allen company system site user admin role',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_ALLEN_CO_SITE_SUPERVISOR',
	       'Allen company system site supervisor role',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_ALLEN_CO_SITE_USER',
	       'Allen company system site user role',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_ALLEN_CO_SITE_PARTNER',
	       'Allen company system site partnet role',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));

------------------------------------------------------------------------------
-- Load Role for Bobby Company
------------------------------------------------------------------------------
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_BOBBY_CO_SUPERADMIN',
	       'Bobby company super system admin role',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_BOBBY_CO_SYSADMIN',
	       'Bobby company system admin role',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_BOBBY_CO_SITE_ADMIN',
	       'Bobby company system site admin role',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_BOBBY_CO_SITE_USERADMIN',
	       'Bobby company system site user admin role',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_BOBBY_CO_SITE_SUPERVISOR',
	       'Bobby company system site supervisor role',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_BOBBY_CO_SITE_USER',
	       'Bobby company system site user role',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_BOBBY_CO_SITE_PARTNER',
	       'Bobby company system site partnet role',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));

------------------------------------------------------------------------------
-- Load Role for Cathy Company
------------------------------------------------------------------------------
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_CATHY_CO_SUPERADMIN',
	       'Cathy company super system admin role',
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_CATHY_CO_SYSADMIN',
	       'Cathy company system admin role',
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_CATHY_CO_SITE_ADMIN',
	       'Cathy company system site admin role',
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_CATHY_CO_SITE_USERADMIN',
	       'Cathy company system site user admin role',
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_CATHY_CO_SITE_SUPERVISOR',
	       'Cathy company system site supervisor role',
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_CATHY_CO_SITE_USER',
	       'Cathy company system site user role',
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_CATHY_CO_SITE_PARTNER',
	       'Cathy company system site partnet role',
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));

------------------------------------------------------------------------------
-- Load LogixPath RoleHierarchy
------------------------------------------------------------------------------
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_LP_SUPERADMIN'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_LP_SYSADMIN'),
	       1);
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_LP_SYSADMIN'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_LP_SITE_ADMIN'),
	       1);
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_LP_SITE_ADMIN'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_LP_SITE_USERADMIN'),
	       1);
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_LP_SITE_ADMIN'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_LP_SITE_PARTNER'),
	       1);
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_LP_SITE_USERADMIN'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_LP_SITE_SUPERVISOR'),
	       1);
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_LP_SITE_SUPERVISOR'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_LP_SITE_USER'),
	       1);

------------------------------------------------------------------------------
-- Load Allen company RoleHierarchy
------------------------------------------------------------------------------
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_ALLEN_CO_SUPERADMIN'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_ALLEN_CO_SYSADMIN'),
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_ALLEN_CO_SYSADMIN'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_ALLEN_CO_SITE_ADMIN'),
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_ALLEN_CO_SITE_ADMIN'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_ALLEN_CO_SITE_USERADMIN'),
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_ALLEN_CO_SITE_ADMIN'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_ALLEN_CO_SITE_PARTNER'),
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_ALLEN_CO_SITE_USERADMIN'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_ALLEN_CO_SITE_SUPERVISOR'),
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_ALLEN_CO_SITE_SUPERVISOR'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_ALLEN_CO_SITE_USER'),
	       (SELECT Id FROM Account WHERE Name='Allen Company'));

------------------------------------------------------------------------------
-- Load Bobby company RoleHierarchy
------------------------------------------------------------------------------
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_BOBBY_CO_SUPERADMIN'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_BOBBY_CO_SYSADMIN'),
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_BOBBY_CO_SYSADMIN'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_BOBBY_CO_SITE_ADMIN'),
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_BOBBY_CO_SITE_ADMIN'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_BOBBY_CO_SITE_USERADMIN'),
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_BOBBY_CO_SITE_ADMIN'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_BOBBY_CO_SITE_PARTNER'),
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_BOBBY_CO_SITE_USERADMIN'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_BOBBY_CO_SITE_SUPERVISOR'),
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_BOBBY_CO_SITE_SUPERVISOR'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_BOBBY_CO_SITE_USER'),
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));

------------------------------------------------------------------------------
-- Load Cathy company RoleHierarchy
------------------------------------------------------------------------------
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_CATHY_CO_SUPERADMIN'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_CATHY_CO_SYSADMIN'),
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_CATHY_CO_SYSADMIN'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_CATHY_CO_SITE_ADMIN'),
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_CATHY_CO_SITE_ADMIN'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_CATHY_CO_SITE_USERADMIN'),
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_CATHY_CO_SITE_ADMIN'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_CATHY_CO_SITE_PARTNER'),
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_CATHY_CO_SITE_USERADMIN'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_CATHY_CO_SITE_SUPERVISOR'),
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO RoleHierarchy(RoleId, IncludedRoleId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_CATHY_CO_SITE_SUPERVISOR'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_CATHY_CO_SITE_USER'),
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));

------------------------------------------------------------------------------
-- Load LogixPath LoginUserRoles (OwnerAccountId = 1)
------------------------------------------------------------------------------
INSERT INTO LoginUserRoles(LoginUserId, RoleId, OwnerAccountId)
	VALUES((SELECT Id FROM LoginUser WHERE UserName='root'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_LP_SUPERADMIN'),
	       1);
INSERT INTO LoginUserRoles(LoginUserId, RoleId, OwnerAccountId)
	VALUES((SELECT Id FROM LoginUser WHERE UserName='lpgreen'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_LP_SYSADMIN'),
	       1);
INSERT INTO LoginUserRoles(LoginUserId, RoleId, OwnerAccountId)
	VALUES((SELECT Id FROM LoginUser WHERE UserName='lpsec'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_LP_SITE_USERADMIN'),
	       1);

------------------------------------------------------------------------------
-- Load Allen company LoginUserRoles
------------------------------------------------------------------------------
INSERT INTO LoginUserRoles(LoginUserId, RoleId, OwnerAccountId)
	VALUES((SELECT Id FROM LoginUser WHERE UserName='allen'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_ALLEN_CO_SUPERADMIN'),
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO LoginUserRoles(LoginUserId, RoleId, OwnerAccountId)
	VALUES((SELECT Id FROM LoginUser WHERE UserName='adam'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_ALLEN_CO_SYSADMIN'),
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO LoginUserRoles(LoginUserId, RoleId, OwnerAccountId)
	VALUES((SELECT Id FROM LoginUser WHERE UserName='alex'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_ALLEN_CO_SITE_USERADMIN'),
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO LoginUserRoles(LoginUserId, RoleId, OwnerAccountId)
	VALUES((SELECT Id FROM LoginUser WHERE UserName='alan'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_ALLEN_CO_SITE_USER'),
	       (SELECT Id FROM Account WHERE Name='Allen Company'));

------------------------------------------------------------------------------
-- Load Bobby company LoginUserRoles
------------------------------------------------------------------------------
INSERT INTO LoginUserRoles(LoginUserId, RoleId, OwnerAccountId)
	VALUES((SELECT Id FROM LoginUser WHERE UserName='bobby'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_BOBBY_CO_SUPERADMIN'),
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO LoginUserRoles(LoginUserId, RoleId, OwnerAccountId)
	VALUES((SELECT Id FROM LoginUser WHERE UserName='bill'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_BOBBY_CO_SYSADMIN'),
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO LoginUserRoles(LoginUserId, RoleId, OwnerAccountId)
	VALUES((SELECT Id FROM LoginUser WHERE UserName='beth'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_BOBBY_CO_SITE_USERADMIN'),
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO LoginUserRoles(LoginUserId, RoleId, OwnerAccountId)
	VALUES((SELECT Id FROM LoginUser WHERE UserName='bart'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_BOBBY_CO_SITE_USER'),
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));

------------------------------------------------------------------------------
-- Load Cathy company LoginUserRoles
------------------------------------------------------------------------------
INSERT INTO LoginUserRoles(LoginUserId, RoleId, OwnerAccountId)
	VALUES((SELECT Id FROM LoginUser WHERE UserName='cathy'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_CATHY_CO_SUPERADMIN'),
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO LoginUserRoles(LoginUserId, RoleId, OwnerAccountId)
	VALUES((SELECT Id FROM LoginUser WHERE UserName='carl'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_CATHY_CO_SYSADMIN'),
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO LoginUserRoles(LoginUserId, RoleId, OwnerAccountId)
	VALUES((SELECT Id FROM LoginUser WHERE UserName='cate'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_CATHY_CO_SITE_USERADMIN'),
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO LoginUserRoles(LoginUserId, RoleId, OwnerAccountId)
	VALUES((SELECT Id FROM LoginUser WHERE UserName='carol'),
	       (SELECT Id FROM Role WHERE RoleName='ROLE_CATHY_CO_SITE_USER'),
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));

------------------------------------------------------------------------------
-- Load OperationalRight for LogixPath (OwnerAccountId = 1)
------------------------------------------------------------------------------
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('HomePage',
	       'Right for LogixPath home page access',
	       1);
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Search',
	       'Right for LogixPath performing search',
	       1);
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Read',
	       'Right for LogixPath read access',
	       1);
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Write',
	       'Right for LogixPath write access',
	       1);
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Delete',
	       'Right for LogixPath delete access',
	       1);
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Clone',
	       'Right for LogixPath clone access',
	       1);
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Export',
	       'Right for LogixPath export access',
	       1);
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Import',
	       'Right for LogixPath import access',
	       1);
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('AdminAll',
	       'Admin right LogixPath for all access',
	       1);
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('AdminSiteAll',
	       'Admin right LogixPath for all site access',
	       1);

------------------------------------------------------------------------------
-- Load OperationalRight for Allen company
------------------------------------------------------------------------------
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('HomePage',
	       'Right for Allen company home page access',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Search',
	       'Right for Allen company performing search',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Read',
	       'Right for Allen company read access',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Write',
	       'Right for Allen company write access',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Delete',
	       'Right for Allen company delete access',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Clone',
	       'Right for Allen company clone access',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Export',
	       'Right for Allen company export access',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Import',
	       'Right for Allen company import access',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('AdminAll',
	       'Admin right for Allen company for all access',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('AdminSiteAll',
	       'Admin right for Allen company for all site access',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));

------------------------------------------------------------------------------
-- Load OperationalRight for Bobby company
------------------------------------------------------------------------------
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('HomePage',
	       'Right for Bobby company home page access',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Search',
	       'Right for Bobby company performing search',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Read',
	       'Right for Bobby company read access',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Write',
	       'Right for Bobby company write access',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Delete',
	       'Right for Bobby company delete access',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Clone',
	       'Right for Bobby company clone access',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Export',
	       'Right for Bobby company export access',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Import',
	       'Right for Bobby company import access',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('AdminAll',
	       'Admin right for Bobby company for all access',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('AdminSiteAll',
	       'Admin right for Bobby company for all site access',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));

------------------------------------------------------------------------------
-- Load OperationalRight for Cathy company
------------------------------------------------------------------------------
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('HomePage',
	       'Right for Cathy company home page access',
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Search',
	       'Right for Cathy company performing search',
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Read',
	       'Right for Cathy company read access',
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Write',
	       'Right for Cathy company write access',
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Delete',
	       'Right for Cathy company delete access',
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Clone',
	       'Right for Cathy company clone access',
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Export',
	       'Right for Cathy company export access',
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Import',
	       'Right for Cathy company import access',
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('AdminAll',
	       'Admin right for Cathy company for all access',
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('AdminSiteAll',
	       'Admin right for Cathy company for all site access',
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));

------------------------------------------------------------------------------
-- Load AccessControlList for LogixPath (OwnerAccountId = 1)
------------------------------------------------------------------------------
INSERT INTO AccessControlList(RoleId, ObjectName, OperationRightId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_LP_SUPERADMIN'),
	       'Everything',
	       (SELECT Id FROM OperationRight WHERE OperationName='AdminAll' AND
	        OwnerAccountId=(SELECT Id FROM Account WHERE Name='LogixPath LLC')),
	       1);
INSERT INTO AccessControlList(RoleId, ObjectName, OperationRightId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_LP_SYSADMIN'),
	       'Everything',
	       (SELECT Id FROM OperationRight WHERE OperationName='Write' AND
	        OwnerAccountId=(SELECT Id FROM Account WHERE Name='LogixPath LLC')),
	       1);
INSERT INTO AccessControlList(RoleId, ObjectName, OperationRightId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_LP_SYSADMIN'),
	       'Everything',
	       (SELECT Id FROM OperationRight WHERE OperationName='Clone' AND
	        OwnerAccountId=(SELECT Id FROM Account WHERE Name='LogixPath LLC')),
	       1);
INSERT INTO AccessControlList(RoleId, ObjectName, OperationRightId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_LP_SYSADMIN'),
	       'Everything',
	       (SELECT Id FROM OperationRight WHERE OperationName='Delete' AND
	        OwnerAccountId=(SELECT Id FROM Account WHERE Name='LogixPath LLC')),
	       1);
INSERT INTO AccessControlList(RoleId, ObjectName, OperationRightId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_LP_SITE_ADMIN'),
	       'Site',
	       (SELECT Id FROM OperationRight WHERE OperationName='AdminSiteAll' AND
	        OwnerAccountId=(SELECT Id FROM Account WHERE Name='LogixPath LLC')),
	       1);
INSERT INTO AccessControlList(RoleId, ObjectName, OperationRightId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_LP_SITE_ADMIN'),
	       'Database',
	       (SELECT Id FROM OperationRight WHERE OperationName='Clone' AND
	        OwnerAccountId=(SELECT Id FROM Account WHERE Name='LogixPath LLC')),
	       1);
INSERT INTO AccessControlList(RoleId, ObjectName, OperationRightId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_LP_SITE_USERADMIN'),
	       'UserAccounts',
	       (SELECT Id FROM OperationRight WHERE OperationName='Clone' AND
	        OwnerAccountId=(SELECT Id FROM Account WHERE Name='LogixPath LLC')),
	       1);
INSERT INTO AccessControlList(RoleId, ObjectName, OperationRightId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_LP_SITE_USERADMIN'),
	       'UserAccounts',
	       (SELECT Id FROM OperationRight WHERE OperationName='Delete' AND
	        OwnerAccountId=(SELECT Id FROM Account WHERE Name='LogixPath LLC')),
	       1);

------------------------------------------------------------------------------
-- Load AccessControlList for Allen company
------------------------------------------------------------------------------
INSERT INTO AccessControlList(RoleId, ObjectName, OperationRightId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_ALLEN_CO_SUPERADMIN'),
	       'Everything',
	       (SELECT Id FROM OperationRight WHERE OperationName='AdminAll' AND
	        OwnerAccountId=(SELECT Id FROM Account WHERE Name='Allen Company')),
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO AccessControlList(RoleId, ObjectName, OperationRightId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_ALLEN_CO_SYSADMIN'),
	       'Everything',
	       (SELECT Id FROM OperationRight WHERE OperationName='Write' AND
	        OwnerAccountId=(SELECT Id FROM Account WHERE Name='Allen Company')),
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO AccessControlList(RoleId, ObjectName, OperationRightId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_ALLEN_CO_SITE_ADMIN'),
	       'Site',
	       (SELECT Id FROM OperationRight WHERE OperationName='AdminSiteAll' AND
	        OwnerAccountId=(SELECT Id FROM Account WHERE Name='Allen Company')),
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO AccessControlList(RoleId, ObjectName, OperationRightId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_ALLEN_CO_SITE_USERADMIN'),
	       'UserAccounts',
	       (SELECT Id FROM OperationRight WHERE OperationName='Clone' AND
	        OwnerAccountId=(SELECT Id FROM Account WHERE Name='Allen Company')),
	       (SELECT Id FROM Account WHERE Name='Allen Company'));

------------------------------------------------------------------------------
-- Load AccessControlList for Bobby company
------------------------------------------------------------------------------
INSERT INTO AccessControlList(RoleId, ObjectName, OperationRightId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_BOBBY_CO_SUPERADMIN'),
	       'Everything',
	       (SELECT Id FROM OperationRight WHERE OperationName='AdminAll' AND
	        OwnerAccountId=(SELECT Id FROM Account WHERE Name='Bobby Company')),
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO AccessControlList(RoleId, ObjectName, OperationRightId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_BOBBY_CO_SYSADMIN'),
	       'Everything',
	       (SELECT Id FROM OperationRight WHERE OperationName='Write' AND
	        OwnerAccountId=(SELECT Id FROM Account WHERE Name='Bobby Company')),
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO AccessControlList(RoleId, ObjectName, OperationRightId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_BOBBY_CO_SITE_ADMIN'),
	       'Site',
	       (SELECT Id FROM OperationRight WHERE OperationName='AdminSiteAll' AND
	        OwnerAccountId=(SELECT Id FROM Account WHERE Name='Bobby Company')),
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO AccessControlList(RoleId, ObjectName, OperationRightId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_BOBBY_CO_SITE_USERADMIN'),
	       'UserAccounts',
	       (SELECT Id FROM OperationRight WHERE OperationName='Clone' AND
	        OwnerAccountId=(SELECT Id FROM Account WHERE Name='Bobby Company')),
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));

------------------------------------------------------------------------------
-- Load AccessControlList for Bobby company
------------------------------------------------------------------------------
INSERT INTO AccessControlList(RoleId, ObjectName, OperationRightId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_CATHY_CO_SUPERADMIN'),
	       'Everything',
	       (SELECT Id FROM OperationRight WHERE OperationName='AdminAll' AND
	        OwnerAccountId=(SELECT Id FROM Account WHERE Name='Cathy Company')),
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO AccessControlList(RoleId, ObjectName, OperationRightId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_CATHY_CO_SYSADMIN'),
	       'Everything',
	       (SELECT Id FROM OperationRight WHERE OperationName='Write' AND
	        OwnerAccountId=(SELECT Id FROM Account WHERE Name='Cathy Company')),
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO AccessControlList(RoleId, ObjectName, OperationRightId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_CATHY_CO_SITE_ADMIN'),
	       'Site',
	       (SELECT Id FROM OperationRight WHERE OperationName='AdminSiteAll' AND
	        OwnerAccountId=(SELECT Id FROM Account WHERE Name='Cathy Company')),
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
INSERT INTO AccessControlList(RoleId, ObjectName, OperationRightId, OwnerAccountId)
	VALUES((SELECT Id FROM Role WHERE RoleName='ROLE_CATHY_CO_SITE_USERADMIN'),
	       'UserAccounts',
	       (SELECT Id FROM OperationRight WHERE OperationName='Clone' AND
	        OwnerAccountId=(SELECT Id FROM Account WHERE Name='Cathy Company')),
	       (SELECT Id FROM Account WHERE Name='Cathy Company'));
