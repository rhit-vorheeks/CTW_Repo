USE CTW_DB
GO
--Keegan
CREATE PROCEDURE AddPosition
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