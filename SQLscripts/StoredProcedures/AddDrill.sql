USE CTW_DB
GO
--Ari
CREATE PROCEDURE AddDrill
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