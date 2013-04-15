------------------------------------------------------------------------------
-- DROP TABLEs
------------------------------------------------------------------------------
DROP TABLE IF EXISTS Project;

------------------------------------------------------------------------------
-- Create Table: Project
------------------------------------------------------------------------------
CREATE TABLE Project (
	Id                              serial NOT NULL PRIMARY KEY UNIQUE,
	ProjectCode                     varchar(32) NOT NULL,
	Name                            varchar(256) NOT NULL,
	CurrentPhase                    varchar(32) NOT NULL,
	ProjectManager1Id               int REFERENCES Employee(Id),
	ProjectManager2Id               int REFERENCES Employee(Id),
	CustomerAccountId               int REFERENCES Account(Id),
	CustomerContactId               uuid REFERENCES Contact(UniqueId),
	SponsorId                       uuid REFERENCES Contact(UniqueId),
	ManagingDeptId                  int REFERENCES Department(Id),
	Objectives                      varchar(1024),
	Description                     varchar(2048),
	Budget                          decimal(14,2),
	CurrencyCode                    varchar(3),
	StartDate                       timestamp without time zone,
	EndDate                         timestamp without time zone,
	ParentProjectId                 int REFERENCES Project(Id),
	Notes                           varchar(2048),
	OwnerId                         uuid,
	OwnerAccountId                  int,
	CreatedDate                     timestamp without time zone DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'UTC'),
	CreatedById                     uuid,
	LastModifiedDate                timestamp without time zone DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'UTC'),
	LastModifiedById                uuid,
	UNIQUE (OwnerAccountId, ProjectCode)
);
CREATE INDEX PROJ_OAID_indx ON Project(OwnerAccountId NULLS FIRST);

------------------------------------------------------------------------------
-- Load Project
------------------------------------------------------------------------------
INSERT INTO Project(ProjectCode, Name, CurrentPhase, ProjectManager1Id, ProjectManager2Id,
	       CustomerAccountId,
	       CustomerContactId,
	       SponsorId,
	       ManagingDeptId, Objectives, Description,
	       Budget, CurrencyCode,
	       StartDate,
	       EndDate,
	       ParentProjectId, Notes, OwnerAccountId)
	VALUEs('Sidney', 'New Bridge', 'Study', 1, 2,
	       4,
	       '07771AE4-236A-49d3-A49E-B1F9E1934D43',
	       '07771AE4-236A-49d3-A49E-B1F9E1934D10',
	       1, 'Reduce traffice conjestion', 'A new bridge linking the east and west side of the city',
	       25000.00, 'USD',
	       TIMESTAMP '2013-01-05 08:08:08',
	       TIMESTAMP '2013-02-15 17:08:08',
	       1, 'This is the first study', 1);

INSERT INTO Project(ProjectCode, Name, CurrentPhase, ProjectManager1Id, ProjectManager2Id,
	       CustomerAccountId,
	       CustomerContactId,
	       SponsorId,
	       ManagingDeptId, Objectives, Description,
	       Budget, CurrencyCode,
	       StartDate,
	       EndDate,
	       ParentProjectId, Notes, OwnerAccountId)
	VALUEs('London', 'New Bridge', 'Construction', 4, 5,
	       4,
	       '07771AE4-236A-49d3-A49E-B1F9E1934D42',
	       '07771AE4-236A-49d3-A49E-B1F9E1934D21',
	       5, 'Direct connection', 'Create a short distance travel via direct connection',
	       50000000.00, 'USD',
	       TIMESTAMP '2012-02-01 02:08:08',
	       TIMESTAMP '2015-01-31 20:08:08',
	       2, 'Please get it done on time', 2);

INSERT INTO Project(ProjectCode, Name, CurrentPhase, ProjectManager1Id, ProjectManager2Id,
	       CustomerAccountId,
	       CustomerContactId,
	       SponsorId,
	       ManagingDeptId, Objectives, Description,
	       Budget, CurrencyCode,
	       StartDate,
	       EndDate,
	       ParentProjectId, Notes, OwnerAccountId)
	VALUEs('Paris', 'Office Tower', 'Construction', 4, 5,
	       4,
	       '07771AE4-236A-49d3-A49E-B1F9E1934D41',
	       '07771AE4-236A-49d3-A49E-B1F9E1934D20',
	       6, '12 floors space with 10000 sq ft per floor', 'A brand new office tower',
	       250000000.00, 'EUR',
	       TIMESTAMP '2011-06-15 09:08:08',
	       TIMESTAMP '2016-01-05 10:08:08',
	       2, 'Work in progress according to schedule', 2);

INSERT INTO Project(ProjectCode, Name, CurrentPhase, ProjectManager1Id, ProjectManager2Id,
	       CustomerAccountId,
	       CustomerContactId,
	       SponsorId,
	       ManagingDeptId, Objectives, Description,
	       Budget, CurrencyCode,
	       StartDate,
	       EndDate,
	       ParentProjectId, Notes, OwnerAccountId)
	VALUEs('Shanghai', 'New Apartment', 'Construction', 4, 6,
	       3,
	       '07771AE4-236A-49d3-A49E-B1F9E1934D33',
	       '07771AE4-236A-49d3-A49E-B1F9E1934D20',
	       7, 'Achieving ROI 18% for the project', 'Two 30 story apartment buildings',
	       10000000.00, 'CNY',
	       TIMESTAMP '2012-10-03 07:08:08',
	       TIMESTAMP '2013-11-15 08:08:08',
	       4, 'Hired ABC Architecture Design', 2);

INSERT INTO Project(ProjectCode, Name, CurrentPhase, ProjectManager1Id, ProjectManager2Id,
	       CustomerAccountId,
	       CustomerContactId,
	       SponsorId,
	       ManagingDeptId, Objectives, Description,
	       Budget, CurrencyCode,
	       StartDate,
	       EndDate,
	       ParentProjectId, Notes, OwnerAccountId)
	VALUEs('Minghang', 'New Shopping Center', 'Study', 4, 6,
	       3,
	       '07771AE4-236A-49d3-A49E-B1F9E1934D32',
	       '07771AE4-236A-49d3-A49E-B1F9E1934D22',
	       7, 'Accommodates 150 merchants', 'Four story shopping complex',
	       624000000.00, 'CNY',
	       TIMESTAMP '2013-05-20 07:08:08',
	       TIMESTAMP '2015-05-01 12:08:08',
	       4, 'Submitted the second draft environment impact report', 2);

INSERT INTO Project(ProjectCode, Name, CurrentPhase, ProjectManager1Id, ProjectManager2Id,
	       CustomerAccountId,
	       CustomerContactId,
	       SponsorId,
	       ManagingDeptId, Objectives, Description,
	       Budget, CurrencyCode,
	       StartDate,
	       EndDate,
	       ParentProjectId, Notes, OwnerAccountId)
	VALUEs('JiaDing', 'New School', 'Construction', 4, 5,
	       3,
	       '07771AE4-236A-49d3-A49E-B1F9E1934D31',
	       '07771AE4-236A-49d3-A49E-B1F9E1934D21',
	       5, 'K-12 co-ed campus', 'The school can enroll 1500 students',
	       120000000.00, 'CNY',
	       TIMESTAMP '2013-02-22 12:08:08',
	       TIMESTAMP '2013-10-15 12:08:08',
	       6, 'Finished main building structure', 2);
