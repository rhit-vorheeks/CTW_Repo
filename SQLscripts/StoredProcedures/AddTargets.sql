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