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
	CustomerAccount, CustomerContact, Sponsor, ManagingDeptId, Objectives, Description,
	Budget, CurrencyCode, StartDate, EndDate, ParentProjectId, Notes, OwnerAccountId)
	VALUEs('Sidney', 'New Bridge', 'Study', '1', '2', '2', '07771AE4-236A-49d3-A49E-B1F9E1934D21',
	       '07771AE4-236A-49d3-A49E-B1F9E1934D20', '2', 'Design Proposal', 'Come up with the design',
	       '25000.00', 'USD', TIMESTAMP WITH TIME ZONE '2013-01-05 15:08:08-8',
	       TIMESTAMP WITH TIME ZONE '2013-02-15 16:08:08-8', '1', 'Project Notes', '1');
