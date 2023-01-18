USE CTW_DB
GO
--Ari
CREATE PROCEDURE AddDrill
    @Name VARCHAR(100),
	@Desc text = null
AS

BEGIN
	IF (@Name is null) BEGIN
		RAISERROR('Name cannot be null.', 14, 1)
		RETURN 1
	END

	IF(@Name IN (Select [Name] from Drill))BEGIN
		RAISERROR('Drill Name is taken.', 14, 1)
		RETURN 2
	END
	
    INSERT INTO Drill([Name], [Description])
    VALUES (@Name, @Desc)
	RETURN 0
END