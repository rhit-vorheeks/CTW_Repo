USE CTW_DB
GO

create Proc UpdateHasStat
	@Name VARCHAR(50),
    @Username varchar(50),
	@Date date = null,
	@Quant int = -1


AS

BEGIN
	IF (@Name is null) BEGIN
		PRINT('Name cannot be null.')
		RETURN 1
	END

	IF (@Username is null) BEGIN
		PRINT('Username cannot be null.')
		RETURN 2
	END

	IF (@Date is null ) BEGIN
		set @Date = GetDate() 
	END
	

	IF (@Name NOT IN(select [Name] From Stat))
	BEGIN 
		PRINT('This stat does not exist.')
		RETURN 3
	END

	IF(@Username NOT IN (SELECT Username From Person Where Username = @Username)) BEGIN
		PRINT('This username does not exist. Add account first.')
		RETURN 4
	END
	
	DECLARE @ID int 
	SELECT @ID = ID From Person Where Username = @Username

	IF(@ID NOT IN (Select ID From Player)) Begin
		PRINT('This person is not a Player. Add player first.')
		RETURN 5
	END

	IF(@Quant<0) Begin
		PRINT('Illegal Quantity')
		RETURN 6
	END

	IF (@Date > GETDATE() ) BEGIN
		PRINT('Illegal Date.')
		RETURN 7
	END

	IF(NOT EXISTS(select StatName, PlayerID From HasStat Where PlayerID = @ID AND StatName = @Name AND [Date] = @Date))BEGIN
		PRINT('Must add stat for this date before you can update')
		RETURN 8
	END

	IF(@Quant = -1) BEGIN
		SELECT @Quant = Quantity
		FROM HasStat
		WHERE StatName = @Name and [Date] = @Date and PlayerID = @ID
	END

	Update HasStat 
	SET Quantity = @Quant
    WHERE StatName = @Name and [Date] = @Date and PlayerID = @ID
	RETURN 0
END