/****** Object:  StoredProcedure [dbo].[AddHasStat]    Script Date: 2/15/2023 11:13:23 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- Kaylee
CREATE PROCEDURE [dbo].[AddHasStat]
    @Name VARCHAR(50),
    @Username varchar(50),
	@Date date = null,
	@Quant int = 0
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

	IF(EXISTS(select StatName, PlayerID From HasStat Where PlayerID = @ID AND StatName = @Name AND [Date] = @Date))BEGIN
		PRINT('Player already has stat on this date.')
		RETURN 8
	END

	INSERT INTO HasStat ([StatName], [PlayerID], Quantity, [Date])
    VALUES (@Name, @ID, @Quant, @Date)
	RETURN 0
END