CREATE DATABASE [CTW_DB2]
ON
PRIMARY ( NAME=[CTW_DB2],
FILENAME='D:\Database\MSSQL15.MSSQLSERVER\MSSQL\DATA\CTW_DB2.mdf',
SIZE=6MB,
MAXSIZE=30MB,
FILEGROWTH=12%)
LOG ON
( NAME=[CTW_DB2Log],
FILENAME= 'D:\Database\MSSQL15.MSSQLSERVER\MSSQL\DATA\CTW_DB2.ldf',
SIZE=3MB,
MAXSIZE=22MB,
FILEGROWTH=17%)
COLLATE SQL_Latin1_General_Cp1_CI_AS

Use CTW_DB2
CREATE USER [vorheeks] FROM LOGIN [vorheeks]; 
exec sp_addrolemember 'db_owner', 'vorheeks'; 
CREATE USER [laneks] FROM LOGIN [laneks]; 
exec sp_addrolemember 'db_owner', 'laneks';
GO


USE CTW_DB2

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


