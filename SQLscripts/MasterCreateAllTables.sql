USE CTW_DB

Create table Person(
	ID int identity(1,1),
	DOB date CHECK(DOB< GETDATE()), 
	FName varchar(100) not null,
	Lname varchar(100) not null,
	Username varchar(50) unique not null,
	[Password] varchar(50) not null ,
	[Salt] varchar(50) not null,
	[Type]  varchar(100),
	Age as DATEDIFF(HOUR, dob,GETDATE())/8766,
	Primary key(ID)
	
);


Create table Player(
	ID int References Person(ID)
		On update cascade, 
	Height int CHECK(Height>0), -- measured in inches
	[Weight] int CHECK([Weight]>0), -- measured in lbs

	primary key(ID)
	);

Create table Coach(
	ID int References Person(ID)
		On update cascade,
	[Rank] varchar(50),
	Primary key(ID)
);


Create Table Position(
	[Name] varchar (100),
	[Description] text,
	Primary key(Name)

);

Create Table Team(
	ID int identity(1,1),
	[Name] varchar(100) not null,
	League varchar(100),
	Primary key(ID)
);

Create Table Stat(
	[Name] varchar(100),
	[Description] text,
	[Type] varchar(100),
	Primary Key(Name)
);


Create Table Drill(
	ID int identity(1,1),
	[Name] varchar(100) not null,
	[Description] text,
	Primary Key (ID)
);

Create Table PlaysOn(
	PlayerID int References Player(ID)
		On update cascade,
	TeamID int References Team(ID)
		On update cascade,
	PositionName varchar(100) References Position([Name])
		On update cascade,
	StartDate date not null Check(StartDate <= GetDate()),
	EndDate date,
	CHECK(EndDate>= StartDate),
	Primary key(PlayerID, TeamID, PositionName)
);


Create Table Coaches(
	CoachID int references Coach(ID)
		on update cascade,
	TeamID int references Team(ID)
		on update cascade,
	StartDate date not null Check(StartDate <= GetDate()),
	EndDate date,
	CHECK(EndDate>= StartDate),
	Primary Key(CoachID, TeamID)
);

Create Table HasStat(
	StatName varchar(100) references Stat([Name])
		on update cascade,
	PlayerID int references Player(ID)
		on update cascade,
	Quantity int CHECK (Quantity >=0),
	[Date] date not null Check([Date] <= GetDate()),
	Primary Key(StatName, PlayerID)
);

Create Table Targets(
	DrillID int references Drill(ID)
		on update cascade,
	StatName varchar(100) references Stat([Name])
		on update cascade,
	Primary Key(DrillID, StatName)
);



