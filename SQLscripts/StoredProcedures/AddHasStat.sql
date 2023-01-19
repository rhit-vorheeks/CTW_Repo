USE CTW_DB
GO
-- Kaylee
CREATE PROCEDURE AddHasStat
    @Name VARCHAR(50),
    @Username varchar(50),
	@Date date = null,
	@Quant int = 0
AS

BEGIN
	IF (@Name is null) BEGIN
		RAISERROR('Name cannot be null.', 14, 1)
		RETURN 1
	END

	IF (@Username is null) BEGIN
		RAISERROR('Username cannot be null.', 14, 1)
		RETURN 2
	END

	IF (@Date is null ) BEGIN
		set @Date = GetDate() 
	END
	

	IF (@Name NOT IN(select [Name] From Stat))
	BEGIN 
		RAISERROR('This stat does not exist.', 14, 1)
		RETURN 3
	END

	IF(@Username NOT IN (SELECT Username From Person Where Username = @Username)) BEGIN
		RAISERROR('This username does not exist. Add account first.', 14, 1)
		RETURN 4
	END
	
	DECLARE @ID int 
	SELECT @ID = ID From Person Where Username = @Username

	IF(@ID NOT IN (Select ID From Player)) Begin
		RAISERROR('This person is not a Player. Add player first.', 14, 1)
		RETURN 5
	END

	IF(@Quant<0) Begin
		RAISERROR('Illegal Quantity', 14, 1)
		RETURN 6
	END

	IF (@Date > GETDATE() ) BEGIN
		RAISERROR('Illegal Date.', 14, 1)
		RETURN 7
	END

	IF(EXISTS(select StatName, PlayerID From HasStat Where PlayerID = @ID AND StatName = @Name AND [Date] = @Date))BEGIN
		RAISERROR('Player already has stat on this date.', 14, 1)
		RETURN 8
	END

	INSERT INTO HasStat ([StatName], [PlayerID], Quantity, [Date])
    VALUES (@Name, @ID, @Quant, @Date)
	RETURN 0
END