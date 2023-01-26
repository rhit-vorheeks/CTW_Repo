USE CTW_DB
GO
-- Kaylee
CREATE PROCEDURE AddStat
    @Name VARCHAR(50),
    @Desc text = null,
	@Type varchar(100) =null
AS

BEGIN
	IF (@Name is null) BEGIN
		PRINT('Name cannot be null.')
		RETURN 1
	END

	IF (@Name IN(select [Name] From Stat))
	BEGIN 
		PRINT('This stat already exists.')
		RETURN 2
	END 

	INSERT INTO Stat ([Name], [Description], [Type])
    VALUES (@Name, @Desc, @Type)
	RETURN 0
END