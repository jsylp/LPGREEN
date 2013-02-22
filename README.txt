LPMINI Spring based application
Copy Right LogixPath, Co. 2010-2013


----------------------------------------------------------------
Role included relationship is transitive. Let "->" mean "included".
Then A -> B and B -> C, A -> C.
----------------------------------------------------------------

LogixPath Roles
----------------------------------------------------------------
ROLE_LP_SUPERADMIN -> ROLE_LP_SYSADMIN

ROLE_LP_SYSADMIN -> ROLE_LP_SITE_ADMIN

ROLE_LP_SITE_ADMIN -> ROLE_LP_SITE_USERADMIN, ROLE_LP_SITE_PARTNER

ROLE_LP_SITE_USERADMIN -> ROLE_LP_SITE_SUPERVISOR

ROLE_LP_SITE_SUPERVISOR -> ROLE_LP_SITE_USER

ROLE_LP_SITE_USER

ROLE_LP_SITE_PARTNER
----------------------------------------------------------------

Allen Company
----------------------------------------------------------------
ROLE_ALLEN_CO_SUPERADMIN -> ROLE_ALLEN_CO_SYSADMIN

ROLE_ALLEN_CO_SYSADMIN -> ROLE_ALLEN_CO_SITE_ADMIN

ROLE_ALLEN_CO_SITE_ADMIN -> ROLE_ALLEN_CO_SITE_USERADMIN, ROLE_ALLEN_CO_SITE_PARTNER

ROLE_ALLEN_CO_SITE_USERADMIN -> ROLE_ALLEN_CO_SITE_SUPERVISOR

ROLE_ALLEN_CO_SITE_SUPERVISOR -> ROLE_ALLEN_CO_SITE_USER

ROLE_ALLEN_CO_SITE_USER

ROLE_ALLEN_CO_SITE_PARTNER
----------------------------------------------------------------

Bobby Company
----------------------------------------------------------------
ROLE_BOBBY_CO_SUPERADMIN -> ROLE_BOBBY_CO_SYSADMIN

ROLE_BOBBY_CO_SYSADMIN -> ROLE_BOBBY_CO_SITE_ADMIN

ROLE_BOBBY_CO_SITE_ADMIN -> ROLE_BOBBY_CO_SITE_USERADMIN, ROLE_BOBBY_CO_SITE_PARTNER

ROLE_BOBBY_CO_SITE_USERADMIN -> ROLE_BOBBY_CO_SITE_SUPERVISOR

ROLE_BOBBY_CO_SITE_SUPERVISOR -> ROLE_BOBBY_CO_SITE_USER

ROLE_BOBBY_CO_SITE_USER

ROLE_BOBBY_CO_SITE_PARTNER
----------------------------------------------------------------

Cathy Company
----------------------------------------------------------------
ROLE_CATHY_CO_SUPERADMIN -> ROLE_CATHY_CO_SYSADMIN

ROLE_CATHY_CO_SYSADMIN -> ROLE_CATHY_CO_SITE_ADMIN

ROLE_CATHY_CO_SITE_ADMIN -> ROLE_CATHY_CO_SITE_USERADMIN, ROLE_CATHY_CO_SITE_PARTNER

ROLE_CATHY_CO_SITE_USERADMIN -> ROLE_CATHY_CO_SITE_SUPERVISOR

ROLE_CATHY_CO_SITE_SUPERVISOR -> ROLE_CATHY_CO_SITE_USER

ROLE_CATHY_CO_SITE_USER

ROLE_CATHY_CO_SITE_PARTNER
----------------------------------------------------------------

----------------------------------------------------------------
Operation Name
----------------------------------------------------------------
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

----------------------------------------------------------------
Object Name
----------------------------------------------------------------
Everything
Site
UserAccounts
Database
Directory
JSPFile
JavaScript

----------------------------------------------------------------
Access Control List
----------------------------------------------------------------
ROLE_LP_SUPERADMIN,     Everything,   AdminAll,     1
ROLE_LP_SYSADMIN,       Everything,   Write,        1
ROLE_LP_SYSADMIN,       Everything,   Clone,        1
ROLE_LP_SYSADMIN,       Everything,   Delete,       1
ROLE_LP_SITE_ADMIN,     Site,         AdminSiteAll, 1
ROLE_LP_SITE_ADMIN,     Database,     Clone,        1
ROLE_LP_SITE_USERADMIN, UserAccounts, Clone,        1
ROLE_LP_SITE_USERADMIN, UserAccounts, Delete,       1
