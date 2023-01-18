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
		RAISERROR('Name cannot be null.', 14, 1)
		RETURN 1
	END

	IF (@Name IN(select [Name] From Stat))
	BEGIN 
		RAISERROR('This stat already exists.', 14, 1)
		RETURN 2
	END 

	INSERT INTO Stat ([Name], [Description], [Type])
    VALUES (@Name, @Desc, @Type)
	RETURN 0
END