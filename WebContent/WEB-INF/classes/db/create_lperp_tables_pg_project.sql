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
	CustomerAccount                 int REFERENCES Account(Id),
	CustomerContact                 uuid REFERENCES Contact(UniqueId),
	Sponsor                         uuid REFERENCES Contact(UniqueId),
	ManagingDeptId                  int REFERENCES Department(Id),
	Objectives                      varchar(1024),
	Description                     varchar(2048),
	Budget                          decimal(14,2),
	CurrencyCode                    varchar(3),
	StartDate                       timestamp with time zone,
	EndDate                         timestamp with time zone,
	ParentProjectId                 int REFERENCES Project(Id),
	Notes                           varchar(2048),
	OwnerId                         uuid,
	OwnerAccountId                  int,
	CreatedDate                     timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
	CreatedById                     uuid,
	LastModifiedDate                timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
	LastModifiedById                uuid,
	UNIQUE (OwnerAccountId, ProjectCode)
);
CREATE INDEX PROJ_OAID_indx ON Project(OwnerAccountId NULLS FIRST);

------------------------------------------------------------------------------
-- Load Project
------------------------------------------------------------------------------
INSERT INTO Project(ProjectCode, Name, CurrentPhase, ProjectManager1Id, ProjectManager2Id,
	       CustomerAccount,
	       CustomerContact,
	       Sponsor,
	       ManagingDeptId, Objectives, Description,
	       Budget, CurrencyCode,
	       StartDate,
	       EndDate,
	       ParentProjectId, Notes, OwnerAccountId)
	VALUEs('Sidney', 'New Bridge', 'Study', '1', '2',
	       '1',
	       '07771AE4-236A-49d3-A49E-B1F9E1934D11',
	       '07771AE4-236A-49d3-A49E-B1F9E1934D10',
	       '1', 'Design Proposal', 'Come up with the design',
	       '25000.00', 'USD',
	       TIMESTAMP WITH TIME ZONE '2013-01-05 15:08:08-8',
	       TIMESTAMP WITH TIME ZONE '2013-02-15 16:08:08-8',
	       '1', 'This is the first study', '1');

INSERT INTO Project(ProjectCode, Name, CurrentPhase, ProjectManager1Id, ProjectManager2Id,
	       CustomerAccount,
	       CustomerContact,
	       Sponsor,
	       ManagingDeptId, Objectives, Description,
	       Budget, CurrencyCode,
	       StartDate,
	       EndDate,
	       ParentProjectId, Notes, OwnerAccountId)
	VALUEs('London', 'New Bridge', 'Implementation', '1', '2',
	       '1',
	       '07771AE4-236A-49d3-A49E-B1F9E1934D11',
	       '07771AE4-236A-49d3-A49E-B1F9E1934D10',
	       '1', 'Implementation Proposal', 'Come up with the implementation',
	       '200000.00', 'USD',
	       TIMESTAMP WITH TIME ZONE '2013-03-05 15:08:08-7',
	       TIMESTAMP WITH TIME ZONE '2013-04-15 16:08:08-7',
	       '1', 'Please get it done on time', '1');

INSERT INTO Project(ProjectCode, Name, CurrentPhase, ProjectManager1Id, ProjectManager2Id,
	       CustomerAccount,
	       CustomerContact,
	       Sponsor,
	       ManagingDeptId, Objectives, Description,
	       Budget, CurrencyCode,
	       StartDate,
	       EndDate,
	       ParentProjectId, Notes, OwnerAccountId)
	VALUEs('Paris', 'New Bridge', 'Implementation', '1', '2',
	       '1',
	       '07771AE4-236A-49d3-A49E-B1F9E1934D11',
	       '07771AE4-236A-49d3-A49E-B1F9E1934D10',
	       '1', 'Link Britain and France', 'A new bridge to shorten travel time',
	       '20000000.00', 'USD',
	       TIMESTAMP WITH TIME ZONE '2013-02-05 15:08:08-8',
	       TIMESTAMP WITH TIME ZONE '2014-04-15 16:08:08-7',
	       '1', 'Please get it done on time', '1');

INSERT INTO Project(ProjectCode, Name, CurrentPhase, ProjectManager1Id, ProjectManager2Id,
	       CustomerAccount,
	       CustomerContact,
	       Sponsor,
	       ManagingDeptId, Objectives, Description,
	       Budget, CurrencyCode,
	       StartDate,
	       EndDate,
	       ParentProjectId, Notes, OwnerAccountId)
	VALUEs('Shanghai', 'New Tunnel', 'Proposal', '4', '5',
	       '2',
	       '07771AE4-236A-49d3-A49E-B1F9E1934D21',
	       '07771AE4-236A-49d3-A49E-B1F9E1934D20',
	       '5', 'A new tunnel to link Shanghai and Haining', 'Give a proposal',
	       '100000.00', 'CNY',
	       TIMESTAMP WITH TIME ZONE '2013-02-05 15:08:08-8',
	       TIMESTAMP WITH TIME ZONE '2013-03-15 16:08:08-7',
	       '3', 'Forming the team', '2');

INSERT INTO Project(ProjectCode, Name, CurrentPhase, ProjectManager1Id, ProjectManager2Id,
	       CustomerAccount,
	       CustomerContact,
	       Sponsor,
	       ManagingDeptId, Objectives, Description,
	       Budget, CurrencyCode,
	       StartDate,
	       EndDate,
	       ParentProjectId, Notes, OwnerAccountId)
	VALUEs('Minghang', 'New School', 'Construction', '4', '5',
	       '2',
	       '07771AE4-236A-49d3-A49E-B1F9E1934D21',
	       '07771AE4-236A-49d3-A49E-B1F9E1934D20',
	       '5', 'A new school', 'New elementary school',
	       '10000000.00', 'CNY',
	       TIMESTAMP WITH TIME ZONE '2013-02-20 15:08:08-8',
	       TIMESTAMP WITH TIME ZONE '2013-05-15 16:08:08-7',
	       '3', 'Roof is completed', '2');

INSERT INTO Project(ProjectCode, Name, CurrentPhase, ProjectManager1Id, ProjectManager2Id,
	       CustomerAccount,
	       CustomerContact,
	       Sponsor,
	       ManagingDeptId, Objectives, Description,
	       Budget, CurrencyCode,
	       StartDate,
	       EndDate,
	       ParentProjectId, Notes, OwnerAccountId)
	VALUEs('JiaDing', 'New water treatment plant', 'Construction', '4', '5',
	       '2',
	       '07771AE4-236A-49d3-A49E-B1F9E1934D21',
	       '07771AE4-236A-49d3-A49E-B1F9E1934D20',
	       '5', 'Replace the aging old plant', 'a new bandnew water treatment plant',
	       '100000000.00', 'CNY',
	       TIMESTAMP WITH TIME ZONE '2013-02-22 15:08:08-8',
	       TIMESTAMP WITH TIME ZONE '2013-08-15 16:08:08-7',
	       '3', 'Phase three is in progress', '2');
