
--Declare @Name1 varchar(128)
--Declare @Name2 varchar(128)

--set @Name1 = 'D:\Database\MSSQL15.MSSQLSERVER\MSSQL\DATA\' + $(dbname) + '.mdf';
--set @Name2 = 'D:\Database\MSSQL15.MSSQLSERVER\MSSQL\DATA\' + $(dbname) + '.ldf';

CREATE DATABASE $(dbname)
ON
PRIMARY (NAME=$(dbname),
FILENAME= 'D:\Database\MSSQL15.MSSQLSERVER\MSSQL\DATA\file1.mdf',
SIZE=6MB,
MAXSIZE=30MB,
FILEGROWTH=12%)
LOG ON
( NAME=DBLog,
FILENAME= 'D:\Database\MSSQL15.MSSQLSERVER\MSSQL\DATA\file2.ldf',
SIZE=3MB,
MAXSIZE=22MB,
FILEGROWTH=17%)
COLLATE SQL_Latin1_General_Cp1_CI_AS

GO
Use $(dbname)
GO

CREATE USER [duvallar] FROM LOGIN [duvallar]; 
exec sp_addrolemember 'db_owner', 'duvallar'; 
CREATE USER [laneks] FROM LOGIN [laneks]; 
exec sp_addrolemember 'db_owner', 'laneks';
CREATE USER [CTW_DBUser];
exec sp_addrolemember 'db_owner', 'CTW_DBUser';

-- sqlcmd define variables 
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Person](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[DOB] [date] NULL,
	[FName] [varchar](100) NOT NULL,
	[Lname] [varchar](100) NOT NULL,
	[Username] [varchar](50) NOT NULL,
	[Password] [varchar](50) NOT NULL,
	[Type] [varchar](100) NULL,
	[Age]  AS (datediff(hour,[dob],getdate())/(8766)),
	[Salt] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[Username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Person]  WITH CHECK ADD CHECK  (([DOB]<getdate()))
GO

/****** Object:  Table [dbo].[Player]    Script Date: 2/15/2023 11:18:53 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Player](
	[ID] [int] NOT NULL,
	[Height] [int] NULL,
	[Weight] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Player]  WITH CHECK ADD FOREIGN KEY([ID])
REFERENCES [dbo].[Person] ([ID])
ON UPDATE CASCADE
GO

ALTER TABLE [dbo].[Player]  WITH CHECK ADD CHECK  (([Height]>(0)))
GO

ALTER TABLE [dbo].[Player]  WITH CHECK ADD CHECK  (([Weight]>(0)))
GO


/****** Object:  Table [dbo].[Coach]    Script Date: 2/15/2023 11:21:04 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Coach](
	[ID] [int] NOT NULL,
	[Rank] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Coach]  WITH CHECK ADD FOREIGN KEY([ID])
REFERENCES [dbo].[Person] ([ID])
ON UPDATE CASCADE
GO



/****** Object:  Table [dbo].[Position]    Script Date: 2/15/2023 11:21:37 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Position](
	[Name] [varchar](100) NOT NULL,
	[Description] [text] NULL,
PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO



/****** Object:  Table [dbo].[Team]    Script Date: 2/15/2023 11:22:00 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Team](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](100) NOT NULL,
	[League] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO


/****** Object:  Table [dbo].[Stat]    Script Date: 2/15/2023 11:23:08 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Stat](
	[Name] [varchar](100) NOT NULL,
	[Description] [text] NULL,
	[Type] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO


/****** Object:  Table [dbo].[Drill]    Script Date: 2/15/2023 11:23:24 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Drill](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](100) NOT NULL,
	[Description] [text] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO



/****** Object:  Table [dbo].[PlaysOn]    Script Date: 2/15/2023 11:23:48 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[PlaysOn](
	[PlayerID] [int] NOT NULL,
	[TeamID] [int] NOT NULL,
	[PositionName] [varchar](100) NOT NULL,
	[StartDate] [date] NOT NULL,
	[EndDate] [date] NULL,
 CONSTRAINT [PK__PlaysOn__8989B03CFC1D00A9] PRIMARY KEY CLUSTERED 
(
	[PlayerID] ASC,
	[TeamID] ASC,
	[PositionName] ASC,
	[StartDate] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[PlaysOn]  WITH CHECK ADD FOREIGN KEY([PlayerID])
REFERENCES [dbo].[Player] ([ID])
ON UPDATE CASCADE
GO

ALTER TABLE [dbo].[PlaysOn]  WITH CHECK ADD FOREIGN KEY([PositionName])
REFERENCES [dbo].[Position] ([Name])
ON UPDATE CASCADE
GO

ALTER TABLE [dbo].[PlaysOn]  WITH CHECK ADD FOREIGN KEY([TeamID])
REFERENCES [dbo].[Team] ([ID])
ON UPDATE CASCADE
GO

ALTER TABLE [dbo].[PlaysOn]  WITH CHECK ADD CHECK  (([EndDate]>=[StartDate]))
GO

ALTER TABLE [dbo].[PlaysOn]  WITH CHECK ADD CHECK  (([StartDate]<=getdate()))
GO



/****** Object:  Table [dbo].[Coaches]    Script Date: 2/15/2023 11:26:08 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Coaches](
	[CoachID] [int] NOT NULL,
	[TeamID] [int] NOT NULL,
	[StartDate] [date] NOT NULL,
	[EndDate] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[CoachID] ASC,
	[TeamID] ASC,
	[StartDate] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Coaches]  WITH CHECK ADD FOREIGN KEY([CoachID])
REFERENCES [dbo].[Coach] ([ID])
ON UPDATE CASCADE
GO

ALTER TABLE [dbo].[Coaches]  WITH CHECK ADD FOREIGN KEY([TeamID])
REFERENCES [dbo].[Team] ([ID])
ON UPDATE CASCADE
GO

ALTER TABLE [dbo].[Coaches]  WITH CHECK ADD CHECK  (([EndDate]>=[StartDate]))
GO

ALTER TABLE [dbo].[Coaches]  WITH CHECK ADD CHECK  (([StartDate]<=getdate()))
GO



/****** Object:  Table [dbo].[HasStat]    Script Date: 2/15/2023 11:26:31 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[HasStat](
	[StatName] [varchar](100) NOT NULL,
	[PlayerID] [int] NOT NULL,
	[Quantity] [int] NULL,
	[Date] [date] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[StatName] ASC,
	[PlayerID] ASC,
	[Date] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[HasStat]  WITH CHECK ADD FOREIGN KEY([PlayerID])
REFERENCES [dbo].[Player] ([ID])
ON UPDATE CASCADE
GO

ALTER TABLE [dbo].[HasStat]  WITH CHECK ADD FOREIGN KEY([StatName])
REFERENCES [dbo].[Stat] ([Name])
ON UPDATE CASCADE
GO

ALTER TABLE [dbo].[HasStat]  WITH CHECK ADD CHECK  (([Date]<=getdate()))
GO

ALTER TABLE [dbo].[HasStat]  WITH CHECK ADD CHECK  (([Quantity]>=(0)))
GO



/****** Object:  Table [dbo].[Targets]    Script Date: 2/15/2023 11:26:52 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Targets](
	[DrillID] [int] NOT NULL,
	[StatName] [varchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[DrillID] ASC,
	[StatName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Targets]  WITH CHECK ADD FOREIGN KEY([DrillID])
REFERENCES [dbo].[Drill] ([ID])
ON UPDATE CASCADE
GO

ALTER TABLE [dbo].[Targets]  WITH CHECK ADD FOREIGN KEY([StatName])
REFERENCES [dbo].[Stat] ([Name])
ON UPDATE CASCADE
GO


/****** Object:  StoredProcedure [dbo].[AddCoach]    Script Date: 2/15/2023 11:10:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- Keegan
CREATE PROCEDURE [dbo].[AddCoach]
    @Username VARCHAR(50),
    @Rank VARCHAR(50) = null
AS

BEGIN
	IF (@Username is null) BEGIN
		PRINT('Username cannot be null.')
		RETURN 1
	END

	IF (@Username NOT IN(select Username From Person))
	BEGIN 
		PRINT('Coach is not a registerd person. Register person first.')
		RETURN 2
	END 

	DECLARE @ID int 
	SELECT @ID = ID From Person Where Username = @Username
	
	IF(EXISTS(Select ID From Coach where ID = @ID))Begin
		PRINT('Coach already exists.')
		RETURN 3
	END


    INSERT INTO Coach (ID, Rank)
    VALUES (@ID, @Rank)
	RETURN 0
END

/****** Object:  StoredProcedure [dbo].[AddCoaches]    Script Date: 2/15/2023 11:10:32 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- Keegan
CREATE PROCEDURE [dbo].[AddCoaches]
    @Username VARCHAR(50),
    @TeamID int,
	@StartDate date,
	@EndDate date = null
AS

BEGIN
	IF (@Username is null) BEGIN
		PRINT('Coach Username cannot be null.')
		RETURN 1
	END

	IF (@StartDate is null) BEGIN
		PRINT('Start Date cannot be null.')
		RETURN 2
	END

	IF (@TeamID is null) BEGIN
		PRINT('Team ID cannot be null.')
		RETURN 3
	END

	IF (@Username NOT IN(select Username From Person))
	BEGIN 
		PRINT('Coach is not a registerd person. Register person first.')
		RETURN 4
	END 

	DECLARE @ID int 
	SELECT @ID = ID From Person Where Username = @Username

	IF(@ID NOT IN (Select ID From Coach)) Begin
		PRINT('This person is not a Coach. Add coach first.')
		RETURN 5
	END
	

	IF (@TeamID NOT IN(select ID From Team))
	BEGIN 
		PRINT('Team is not a registerd team. Add team first.')
		RETURN 6
	END 
	
	IF(@EndDate<@StartDate)Begin
		PRINT('Illegal End Date.')
		RETURN 7
	END

	IF(GETDATE() < @StartDate)BEGIN
		PRINT('Illegal Start Date.')
		RETURN 8
	END

	

	Declare @TName varchar(50)
	Select @TName = [Name] From Team Where ID = @TeamID
	IF(EXISTS (Select t.[Name] From Team t join coaches c on t.ID = c.TeamID Where t.[Name] = @TName AND c.CoachID = @ID and (c.EndDate is null or c.EndDate = ''))) BEGIN
		Print 'Coach already coaches a team with this name'
		RETURN 11
	END


	IF(EXISTS(select CoachID, TeamID From Coaches Where CoachID = @ID AND TeamID = @TeamID ))BEGIN
		
		Declare @LastStartDate date
		Select @LastStartDate = MAX (StartDate) From Coaches Where CoachID = @ID AND TeamID = @TeamID Group by CoachID, TeamID
		
		Declare @End date
		select @End = EndDate from Coaches where TeamID = @TeamID AND CoachID= @ID  AND StartDate = @LastStartDate;

		IF(@End is null OR @End ='')Begin 
			PRINT('Coach still holds this position.')
			RETURN 10

		End 

		IF(Exists(Select StartDate From Coaches where CoachID = @ID AND TeamID = @TeamID and StartDate = @StartDate )) BEGIN
			PRINT 'Cannot start same position twice in one day'
			Return 12
		END
	END


	IF(EXISTS(select CoachID, TeamID From Coaches Where CoachID = @ID AND TeamID = @TeamID AND StartDate = @StartDate))BEGIN
		PRINT('Coach is already on the team.')
		RETURN 9
	END


    INSERT INTO Coaches ([CoachID], TeamID, StartDate,EndDate)
    VALUES (@ID, @TeamID, @StartDate, @EndDate)
	RETURN 0
END
/****** Object:  StoredProcedure [dbo].[AddDrill]    Script Date: 2/15/2023 11:13:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--Ari
CREATE PROCEDURE [dbo].[AddDrill]
    @Name VARCHAR(100),
	@Desc text = null
AS

BEGIN
	IF (@Name is null) BEGIN
		PRINT('Name cannot be null.')
		RETURN 1
	END

	IF(@Name IN (Select [Name] from Drill))BEGIN
		PRINT('Drill Name is taken.')
		RETURN 2
	END
	
    INSERT INTO Drill([Name], [Description])
    VALUES (@Name, @Desc)
	RETURN 0
END
/****** Object:  StoredProcedure [dbo].[AddHasStat]    Script Date: 2/15/2023 11:13:23 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- Kaylee
CREATE PROCEDURE [dbo].[AddHasStat]
    @Name VARCHAR(50),
    @Username varchar(50),
	@Date date = null,
	@Quant int = 0
AS

BEGIN
	IF (@Name is null) BEGIN
		PRINT('Name cannot be null.')
		RETURN 1
	END

	IF (@Username is null) BEGIN
		PRINT('Username cannot be null.')
		RETURN 2
	END

	IF (@Date is null ) BEGIN
		set @Date = GetDate() 
	END
	

	IF (@Name NOT IN(select [Name] From Stat))
	BEGIN 
		PRINT('This stat does not exist.')
		RETURN 3
	END

	IF(@Username NOT IN (SELECT Username From Person Where Username = @Username)) BEGIN
		PRINT('This username does not exist. Add account first.')
		RETURN 4
	END
	
	DECLARE @ID int 
	SELECT @ID = ID From Person Where Username = @Username

	IF(@ID NOT IN (Select ID From Player)) Begin
		PRINT('This person is not a Player. Add player first.')
		RETURN 5
	END

	IF(@Quant<0) Begin
		PRINT('Illegal Quantity')
		RETURN 6
	END

	IF (@Date > GETDATE() ) BEGIN
		PRINT('Illegal Date.')
		RETURN 7
	END

	IF(EXISTS(select StatName, PlayerID From HasStat Where PlayerID = @ID AND StatName = @Name AND [Date] = @Date))BEGIN
		PRINT('Player already has stat on this date.')
		RETURN 8
	END

	INSERT INTO HasStat ([StatName], [PlayerID], Quantity, [Date])
    VALUES (@Name, @ID, @Quant, @Date)
	RETURN 0
END
/****** Object:  StoredProcedure [dbo].[AddPerson]    Script Date: 2/15/2023 11:14:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- Kaylee
CREATE PROCEDURE [dbo].[AddPerson]
	@DOB date = null,
	@FName varchar(100) ,
	@LName varchar(100),
	@Username varchar(50),
	@Password varchar(50),
	@Type varchar(100) = null,
	@Salt varchar(50)
AS

BEGIN
	IF (@FName is null OR @LName is null OR @FName = '' OR @LName='' ) BEGIN
		RAISERROR('First or last name cannot be null or empty.', 14, 1)
		RETURN 1
	END
	IF (@Username is null OR @Password is null OR @Username='' OR @Password='') BEGIN
		RAISERROR('Username or Password cannot be null.', 14, 1)
		RETURN 2
	END
	IF (@Salt is null Or @Salt ='') BEGIN
		RAISERROR('Salt cannot be null.', 14, 1)
		RETURN 3
	END
	IF(@Username IN (select Username From Person))BEGIN
		RAISERROR('This Username is already taken.', 14, 1)
		RETURN 4
	END


    INSERT INTO Person(DOB, FName, LName, Username, [Password], [Type], Salt )
    VALUES (@DOB, @FName, @LName, @Username, @Password, @Type, @Salt)
	RETURN 0
END
/****** Object:  StoredProcedure [dbo].[AddPlayer]    Script Date: 2/15/2023 11:15:22 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- Ari
CREATE PROCEDURE [dbo].[AddPlayer]
    @Username VARCHAR(50),
    @Height int = null,
	@Weight int =null
AS

BEGIN
	IF (@Username is null) BEGIN
		PRINT('Username cannot be null.')
		RETURN 1
	END

	IF (@Username NOT IN(select Username From Person))
	BEGIN 
		PRINT('Player is not a registerd person. Register person first.')
		RETURN 2
	END 

	DECLARE @ID int 
	SELECT @ID = ID From Person Where Username = @Username
	
	IF(EXISTS(Select ID From Player where ID = @ID))Begin
		PRINT('Player already exists.')
		RETURN 3
	END


    INSERT INTO Player (ID, Height, Weight)
    VALUES (@ID, @Height, @Weight)
	RETURN 0
END
/****** Object:  StoredProcedure [dbo].[AddPlaysOn]    Script Date: 2/15/2023 11:16:21 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- Ari
CREATE PROCEDURE [dbo].[AddPlaysOn]
    @TeamID int,
    @Username varchar(50),
	@PositionName varchar(100),
	@StartDate date = null,
	@EndDate date = null
	
AS

BEGIN
	IF (@TeamID is null) BEGIN
		PRINT('TeamID cannot be null.')
		RETURN 1
	END

	IF (@Username is null) BEGIN
		PRINT('Username cannot be null.')
		RETURN 2
	END

	IF (@PositionName is null) BEGIN
		PRINT('Position name cannot be null.')
		RETURN 3
	END

	IF (@StartDate is null ) BEGIN
		set @StartDate = GetDate() 
	END
	

	IF (@TeamID NOT IN(select ID From Team))
	BEGIN 
		PRINT('This team does not exist.')
		RETURN 4
	END
	
	IF(@Username NOT IN(select Username From Person))Begin
		PRINT('This person is not a Person. Add Person first.')
		RETURN 5

	End 

	DECLARE @ID int 
	SELECT @ID = ID From Person Where Username = @Username

	IF(@ID NOT IN (Select ID From Player)) Begin
		PRINT('This person is not a Player. Add player first.')
		RETURN 6
	END

	IF (@PositionName NOT IN(select [Name] From Position))
	BEGIN 
		PRINT('This position does not exist.')
		RETURN 7
	END

	IF (@StartDate > GETDATE() ) BEGIN
		PRINT('Illegal Start Date.')
		RETURN 8
	END

	IF (@StartDate > @EndDate ) BEGIN
		PRINT('Illegal End Date.')
		RETURN 9
	END


	IF(EXISTS(select PositionName, PlayerID, TeamID From PlaysOn Where PlayerID = @ID AND PositionName = @PositionName
	AND TeamID = @TeamID AND (EndDate is null or EndDate = '')))BEGIN
		PRINT('Player already plays this position on this team.')
		RETURN 10
	END



	IF(EXISTS(select PlayerID, TeamID, PositionName From PlaysOn Where PlayerID = @ID AND TeamID = @TeamID AND PositionName = @PositionName ))BEGIN
		
		Declare @LastStartDate date
		Select @LastStartDate = MAX (StartDate) From PlaysOn Where PlayerID = @ID AND TeamID = @TeamID Group by PlayerID, TeamID
		
		Declare @End date
		select @End = EndDate from PlaysOn where TeamID = @TeamID AND PlayerID= @ID  AND StartDate = @LastStartDate AND PositionName = @PositionName;

		IF(@End is null OR @End ='')Begin 
			PRINT('Player still plays this position on this team.')
			RETURN 11

		End 

		IF(Exists(Select StartDate From PlaysOn where PlayerID = @ID AND TeamID = @TeamID and StartDate = @StartDate and PositionName = @PositionName )) BEGIN
			PRINT 'Cannot start same position twice in one day'
			Return 12
		END


	END


	INSERT INTO PlaysOn(PlayerID, TeamID, PositionName, [StartDate], EndDate)
    VALUES (@ID, @TeamID, @PositionName, @StartDate, @EndDate)
	RETURN 0
END
/****** Object:  StoredProcedure [dbo].[AddPosition]    Script Date: 2/15/2023 11:16:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--Keegan
CREATE PROCEDURE [dbo].[AddPosition]
    @Name VARCHAR(100),
	@Desc text = null
AS

BEGIN
	IF (@Name is null) BEGIN
		PRINT('Name cannot be null.')
		RETURN 1
	END

	IF(@Name IN (Select [Name] from Position))BEGIN
		PRINT('Position Name is taken.')
		RETURN 2
	END
	
    INSERT INTO Position([Name], [Description])
    VALUES (@Name, @Desc)
	RETURN 0
END
/****** Object:  StoredProcedure [dbo].[AddStat]    Script Date: 2/15/2023 11:17:20 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- Kaylee
CREATE PROCEDURE [dbo].[AddStat]
    @Name VARCHAR(50),
    @Desc text = null,
	@Type varchar(100) =null
AS

BEGIN
	IF (@Name is null) BEGIN
		PRINT('Name cannot be null.')
		RETURN 1
	END

	IF (@Name IN(select [Name] From Stat))
	BEGIN 
		PRINT('This stat already exists.')
		RETURN 2
	END 

	INSERT INTO Stat ([Name], [Description], [Type])
    VALUES (@Name, @Desc, @Type)
	RETURN 0
END
/****** Object:  StoredProcedure [dbo].[AddTargets]    Script Date: 2/15/2023 11:17:43 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--Kaylee
CREATE PROCEDURE [dbo].[AddTargets]
    @Name VARCHAR(100),
	@DrillID int 
AS

BEGIN
	IF (@Name is null) BEGIN
		PRINT('Stat Name cannot be null.')
		RETURN 1
	END

	IF (@DrillID is null) BEGIN
		PRINT('Drill ID cannot be null.')
		RETURN 2
	END

	IF(@DrillID NOT IN (Select ID from Drill))BEGIN
		PRINT('This is not a drill.')
		RETURN 3
	END
	
	IF(@Name NOT IN (Select [Name] from Stat))BEGIN
		PRINT('This is not a stat.')
		RETURN 4
	END
	
	IF(EXISTS(select DrillID, StatName From Targets Where DrillID = @DrillID AND StatName = @Name))BEGIN
		PRINT('This drill already targets this stat.')
		RETURN 5
	END

    INSERT INTO Targets(DrillID, StatName)
    VALUES (@DrillID, @Name)
	RETURN 0
END
/****** Object:  StoredProcedure [dbo].[AddTeam]    Script Date: 2/15/2023 11:18:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--Keegan
CREATE PROCEDURE [dbo].[AddTeam]
    @Name VARCHAR(100),
	@League VARCHAR(100) = null
AS

BEGIN
	IF (@Name is null) BEGIN
		PRINT 'Name cannot be null.'
		RETURN -1
	END
		
    INSERT INTO Team([Name], League)
    VALUES (@Name, @League)
	RETURN @@identity
END
/****** Object:  StoredProcedure [dbo].[CheckRegister]    Script Date: 2/15/2023 11:18:36 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- Kaylee
CREATE PROCEDURE [dbo].[CheckRegister]
	@Username varchar(50)

	AS 
	BEGIN
		IF(@Username is null or @Username = '') BEGIN
			PRINT 'Must provide a username'
			RETURN 1
		END

		IF(EXISTS(Select Username From Person Where Username = @Username)) BEGIN
			PRINT 'This username is taken'
			RETURN 2
		END

		RETURN 0
	END
	
	
/****** Object:  StoredProcedure [dbo].[CoachAddsTeam]    Script Date: 2/15/2023 11:18:52 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[CoachAddsTeam]
	@CoachUsername varchar(50),
	@TeamName varchar(100),
	@TeamLeague varchar(100)


AS
BEGIN
BEGIN TRANSACTION
	
	Declare @TID int
	EXEC @TID = AddTeam 
	@Name = @TeamName,
	@League = @TeamLeague

	IF(@@ERROR <> 0) BEGIN
		PRINT 'Error adding team'
		ROLLBACK TRANSACTION
		RETURN 1
	END

	IF(@TID < 0) BEGIN
		PRINT 'Team name is null'
		ROLLBACK TRANSACTION
		RETURN 2
	END

	IF(NOT EXISTS(SELECT * FROM Team WHERE ID = @TID)) BEGIN
		PRINT 'Team was not added'
		ROLLBACK TRANSACTION
		RETURN 3
	END

	DECLARE @Date date
	SET @DATE = GETDATE()

	DECLARE @Status int
	EXEC @Status = AddCoaches 
	@Username = @CoachUsername,
	@TeamID = @TID,
	@StartDate = @Date

	IF(@@ERROR <> 0) BEGIN
		PRINT 'Error adding Coaches'
		ROLLBACK TRANSACTION
		RETURN 4

	END

	IF(@Status <> 0) BEGIN
		PRINT 'AddCoaches error'
		ROLLBACK TRANSACTION
		RETURN 5
	END


	COMMIT TRANSACTION
	RETURN 0

End
CREATE INDEX Username_Index
ON Person(Username)

CREATE Index Drill_Index
ON Drill([Name])

CREATE Index Team_Index
ON Team([Name])
/****** Object:  StoredProcedure [dbo].[GetDrill]    Script Date: 2/15/2023 11:19:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE Proc [dbo].[GetDrill]
	@statName varchar(50)

AS

BEGIN

IF (@statName = 'All') Begin 
	Select [Name], [Description]  from Drill;
end 
else begin 
SELECT [Name], [Description] from Drill
Join Targets on Drill.ID = Targets.DrillID 
Where Targets.StatName = @statName
end 
return 0;
END
/****** Object:  StoredProcedure [dbo].[GetRoster]    Script Date: 2/15/2023 11:37:17 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE Proc [dbo].[GetRoster]
	@CoachUsername varchar(50)

AS

BEGIN
SELECT T.[Name] AS 'Team Name', P.FName AS 'First Name', P.Lname AS 'Last Name', P.Username as 'Username', PO.PositionName as 'Position'
FROM Team T Join PlaysOn PO on T.ID = PO.TeamID
JOIN Person P on P.ID = PO.PlayerID
JOIN Coaches C on C.TeamID = T.ID 
JOIN Person P2 on P2.ID = C.CoachID
WHERE P2.Username = @CoachUsername
GROUP BY T.[Name], P.Lname, P.FName, P.Username, PO.PositionName
END

/****** Object:  StoredProcedure [dbo].[setCoachesEndDate]    Script Date: 2/15/2023 11:22:17 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE Proc [dbo].[setCoachesEndDate]
    @Username varchar(50),
	@TeamID int
	

AS
BEGIN


DECLARE @ID int 

SELECT @ID = ID From Person Where Username = @Username

Update Coaches SET EndDate = GetDate() WHERE CoachID = @ID and TeamID = @TeamID and EndDate is null



IF(EXISTS(SELECT CoachID, TeamID FROM Coaches WHERE TeamID = @TeamID and CoachID = @ID and EndDate is null))
BEGIN
	PRINT 'Coach still has their job'
	Return 1
END

	Print 'Coach does not have their job'
	RETURN 0
END

/****** Object:  StoredProcedure [dbo].[SetPlaysOnEndDate]    Script Date: 2/15/2023 11:22:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/****** Object:  StoredProcedure [dbo].[RemovePlaysOn]    Script Date: 1/31/2023 8:13:34 PM ******/

CREATE Proc [dbo].[SetPlaysOnEndDate]
	@TeamID int,
    @Username varchar(50),
	@PositionName varchar(100)

AS
BEGIN


DECLARE @ID int 

SELECT @ID = ID From Person Where Username = @Username

Update PlaysOn Set EndDate = GETDATE() WHERE PlayerID = @ID and TeamID = @TeamID and PositionName = @PositionName and EndDate is null



IF(EXISTS(SELECT PlayerID, TeamID, PositionName FROM PlaysOn WHERE TeamID = @TeamID and PositionName = @PositionName and PlayerID = @ID and EndDate is null))
BEGIN
	PRINT 'Player position did not end'
	Return 1
END

	Print 'Player ended specified position on the team'
	RETURN 0
END


 
 
 
/****** Object:  StoredProcedure [dbo].[UpdateHasStat]    Script Date: 2/15/2023 11:23:11 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE Proc [dbo].[UpdateHasStat]
	@Name VARCHAR(50),
    @Username varchar(50),
	@Date date = null,
	@Quant int = -1


AS

BEGIN
	IF (@Name is null) BEGIN
		PRINT('Name cannot be null.')
		RETURN 1
	END

	IF (@Username is null) BEGIN
		PRINT('Username cannot be null.')
		RETURN 2
	END

	IF (@Date is null ) BEGIN
		set @Date = GetDate() 
	END
	

	IF (@Name NOT IN(select [Name] From Stat))
	BEGIN 
		PRINT('This stat does not exist.')
		RETURN 3
	END

	IF(@Username NOT IN (SELECT Username From Person Where Username = @Username)) BEGIN
		PRINT('This username does not exist. Add account first.')
		RETURN 4
	END
	
	DECLARE @ID int 
	SELECT @ID = ID From Person Where Username = @Username

	IF(@ID NOT IN (Select ID From Player)) Begin
		PRINT('This person is not a Player. Add player first.')
		RETURN 5
	END

	IF(@Quant<0) Begin
		PRINT('Illegal Quantity')
		RETURN 6
	END

	IF (@Date > GETDATE() ) BEGIN
		PRINT('Illegal Date.')
		RETURN 7
	END

	IF(NOT EXISTS(select StatName, PlayerID From HasStat Where PlayerID = @ID AND StatName = @Name AND [Date] = @Date))BEGIN
		PRINT('Must add stat for this date before you can update')
		RETURN 8
	END

	IF(@Quant = -1) BEGIN
		SELECT @Quant = Quantity
		FROM HasStat
		WHERE StatName = @Name and [Date] = @Date and PlayerID = @ID
	END

	Update HasStat 
	SET Quantity = @Quant
    WHERE StatName = @Name and [Date] = @Date and PlayerID = @ID
	RETURN 0
END

/****** Object:  StoredProcedure [dbo].[UpdatePlayerInfo]    Script Date: 2/15/2023 11:24:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROC [dbo].[UpdatePlayerInfo]
    @Username varchar(50),
    @Height int = null,
	@Weight int = null

AS
BEGIN
	IF (@Username is null) BEGIN
		PRINT('Username cannot be null.')
		RETURN 1
	END

	IF (@Weight IS NOT NULL) BEGIN
		IF (@Weight < 0) BEGIN
			PRINT 'Weight cannot be negative'
			Return 2
		END
	END

	IF (@Height IS NOT NULL) BEGIN
		IF (@Height < 0) BEGIN
			PRINT 'Height cannot be negative'
			Return 3
		END
	END

	IF (@Username NOT IN(select Username From Person))
	BEGIN 
		PRINT('Username is not a registered person. Register person first.')
		RETURN 4
	END 

	DECLARE @ID int 
	SELECT @ID = ID From Person Where Username = @Username

	IF (@ID NOT IN(select ID From Player))
	BEGIN 
		PRINT('ID is not a registered player. Register as player first.')
		RETURN 5
	END 
	
	Update Player SET Height = @Height, [Weight] = @Weight WHERE [ID] = @ID
	RETURN 0
END
/*
Create Proc UpdatePlaysOn
	@TeamID int,
    @Username varchar(50),
	@PositionName varchar(100)

AS
BEGIN

DECLARE @ID int 
SELECT @ID = ID From Person Where Username = @Username

Update PlaysOn
SET PositionName = @PositionName
WHERE TeamID = @TeamID 

END*/
