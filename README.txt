LPMINI Spring based application
Copy Right LogixPath, Co. 2010-2013


================================================================
Role included relationship is transitive. Let "->" mean "included".
Then A -> B and B -> C, A -> C.
================================================================

LogixPath Roles
================================================================
ROLE_LP_SUPERADMIN -> ROLE_LP_SYSADMIN

ROLE_LP_SYSADMIN -> ROLE_LP_SITE_ADMIN

ROLE_LP_SITE_ADMIN -> ROLE_LP_SITE_USERADMIN, ROLE_LP_SITE_PARTNER

ROLE_LP_SITE_USERADMIN -> ROLE_LP_SITE_SUPERVISOR

ROLE_LP_SITE_SUPERVISOR -> ROLE_LP_SITE_USER

ROLE_LP_SITE_USER

ROLE_LP_SITE_PARTNER
================================================================

Allen Company
================================================================
ROLE_ALLEN_CO_SUPERADMIN -> ROLE_ALLEN_CO_SYSADMIN

ROLE_ALLEN_CO_SYSADMIN -> ROLE_ALLEN_CO_SITE_ADMIN

ROLE_ALLEN_CO_SITE_ADMIN -> ROLE_ALLEN_CO_SITE_USERADMIN, ROLE_ALLEN_CO_SITE_PARTNER

ROLE_ALLEN_CO_SITE_USERADMIN -> ROLE_ALLEN_CO_SITE_SUPERVISOR

ROLE_ALLEN_CO_SITE_SUPERVISOR -> ROLE_ALLEN_CO_SITE_USER

ROLE_ALLEN_CO_SITE_USER

ROLE_ALLEN_CO_SITE_PARTNER
================================================================

Bobby Company
================================================================
ROLE_BOBBY_CO_SUPERADMIN -> ROLE_BOBBY_CO_SYSADMIN

ROLE_BOBBY_CO_SYSADMIN -> ROLE_BOBBY_CO_SITE_ADMIN

ROLE_BOBBY_CO_SITE_ADMIN -> ROLE_BOBBY_CO_SITE_USERADMIN, ROLE_BOBBY_CO_SITE_PARTNER

ROLE_BOBBY_CO_SITE_USERADMIN -> ROLE_BOBBY_CO_SITE_SUPERVISOR

ROLE_BOBBY_CO_SITE_SUPERVISOR -> ROLE_BOBBY_CO_SITE_USER

ROLE_BOBBY_CO_SITE_USER

ROLE_BOBBY_CO_SITE_PARTNER
================================================================

Cathy Company
================================================================
ROLE_CATHY_CO_SUPERADMIN -> ROLE_CATHY_CO_SYSADMIN

ROLE_CATHY_CO_SYSADMIN -> ROLE_CATHY_CO_SITE_ADMIN

ROLE_CATHY_CO_SITE_ADMIN -> ROLE_CATHY_CO_SITE_USERADMIN, ROLE_CATHY_CO_SITE_PARTNER

ROLE_CATHY_CO_SITE_USERADMIN -> ROLE_CATHY_CO_SITE_SUPERVISOR

ROLE_CATHY_CO_SITE_SUPERVISOR -> ROLE_CATHY_CO_SITE_USER

ROLE_CATHY_CO_SITE_USER

ROLE_CATHY_CO_SITE_PARTNER
================================================================

================================================================
Operation Name
================================================================
HomePage
Search
Read
Write
Delete
Clone
Export
Import
AdminAll
AdminSiteAll

================================================================
Object Name
================================================================
Everything
Site
UserAccounts
Database
Directory
JSPFile
JavaScript

================================================================
Access Control List
================================================================
ROLE_LP_SUPERADMIN,     Everything,   AdminAll,     1
ROLE_LP_SYSADMIN,       Everything,   Write,        1
ROLE_LP_SYSADMIN,       Everything,   Clone,        1
ROLE_LP_SYSADMIN,       Everything,   Delete,       1
ROLE_LP_SITE_ADMIN,     Site,         AdminSiteAll, 1
ROLE_LP_SITE_ADMIN,     Database,     Clone,        1
ROLE_LP_SITE_USERADMIN, UserAccounts, Clone,        1
ROLE_LP_SITE_USERADMIN, UserAccounts, Delete,       1

Projects
================================================================

ProjectCode : Sidney
CurrentPhase: Study
Manager1    : Lpmini LogixPath
Manager2    : Lpgreen LogixPath
Customer    : Cathy Company
Contact     : Carol Gamma
Sponsor     : Lpmini LogixPath
ManagingDept: Super Admin
Budget      : 25000.00 USD
StartDate   : 2013-01-05 08:08:08-8
EndDate     : 2013-02-15 17:08:08-8
OwnerAccount: 1

ProjectCode : London
CurrentPhase: Construction
Manager1    : Allen Alpha
Manager2    : Adam Alpha
Customer    : Cathy Company
Contact     : Cate Gamma
Sponsor     : Adam Alpha
ManagingDept: Higher Education
Budget      : 50000000.00 USD
StartDate   : 2012-02-01 02:08:08-8
EndDate     : 2015-01-31 20:08:08-8
OwnerAccount: 2

ProjectCode : Paris
CurrentPhase: Construction
Manager1    : Allen Alpha
Manager2    : Adam Alpha
Customer    : Cathy Company
Contact     : Carl Gamma
Sponsor     : Allen Alpha
ManagingDept: Higher Education Support
Budget      : 250000000.00 EUR
StartDate   : 2011-06-15 09:08:08-7
EndDate     : 2016-01-05 10:08:08-8
OwnerAccount: 2

ProjectCode : Shanghai
CurrentPhase: Construction
Manager1    : Allen Alpha
Manager2    : Alex Alpha
Customer    : Bobby Company
Contact     : Bart Beta
Sponsor     : Allen Alpha
ManagingDept: Higher Education Logistics
Budget      : 10000000.00 CNY
StartDate   : 2012-10-03 07:08:08-8
EndDate     : 2013-11-15 08:08:08-8
OwnerAccount: 2

ProjectCode : Minghang
CurrentPhase: Study
Manager1    : Allen Alpha
Manager2    : Alex Alpha
Customer    : Bobby Company
Contact     : Beth Beta
Sponsor     : Alex Alpha
ManagingDept: Higher Education Logistics
Budget      : 624000000.00 CNY
StartDate   : 2013-05-20 07:08:08-7
EndDate     : 2015-05-01 12:08:08-7
OwnerAccount: 2

ProjectCode : JiaDing
CurrentPhase: Construction
Manager1    : Allen Alpha
Manager2    : Adam Alpha
Customer    : Bobby Company
Contact     : Bill Beta
Sponsor     : Adam Alpha
ManagingDept: Higher Education
Budget      : 120000000.00 CNY
StartDate   : 2013-02-22 12:08:08-8
EndDate     : 2013-10-15 12:08:08-7
OwnerAccount: 2
