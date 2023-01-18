USE CTW_DB
GO
--Kaylee
CREATE PROCEDURE AddTargets
    @Name VARCHAR(100),
	@DrillID int 
AS

BEGIN
	IF (@Name is null) BEGIN
		RAISERROR('Stat Name cannot be null.', 14, 1)
		RETURN 1
	END

	IF (@DrillID is null) BEGIN
		RAISERROR('Drill ID cannot be null.', 14, 1)
		RETURN 2
	END

	IF(@DrillID NOT IN (Select ID from Drill))BEGIN
		RAISERROR('This is not a drill.', 14, 1)
		RETURN 3
	END
	
	IF(@Name NOT IN (Select [Name] from Stat))BEGIN
		RAISERROR('This is not a stat.', 14, 1)
		RETURN 4
	END
	
	IF(EXISTS(select DrillID, StatName From Targets Where DrillID = @DrillID AND StatName = @Name))BEGIN
		RAISERROR('This drill already targets this stat.', 14, 1)
		RETURN 5
	END

    INSERT INTO Targets(DrillID, StatName)
    VALUES (@DrillID, @Name)
	RETURN 0
END