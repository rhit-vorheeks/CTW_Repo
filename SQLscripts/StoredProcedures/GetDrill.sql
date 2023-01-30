USE CTW_DB
GO

create Proc GetDrill
	@drillName varchar(50)

AS

BEGIN
SELECT [Name], [Description] from Drill where [Name] = @drillName;
END