USE CTW_DB
GO
--Keegan
CREATE PROCEDURE AddTeam
    @Name VARCHAR(100),
	@League VARCHAR(100) = null
AS

BEGIN
	IF (@Name is null) BEGIN
		RAISERROR('Name cannot be null.', 14, 1)
		RETURN 1
	END
		
    INSERT INTO Team([Name], League)
    VALUES (@Name, @League)
	RETURN 0
END