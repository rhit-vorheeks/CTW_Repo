USE CTW_DB
GO

create Proc GetDrill
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