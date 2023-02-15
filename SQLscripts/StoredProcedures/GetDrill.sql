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