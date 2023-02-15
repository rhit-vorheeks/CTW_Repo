
/****** Object:  StoredProcedure [dbo].[UpdatePlayerInfo]    Script Date: 2/15/2023 11:24:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROC [dbo].[UpdatePlayerInfo]
    @Username varchar(50),
    @Height int = null,
	@Weight int = null

AS
BEGIN
	IF (@Username is null) BEGIN
		PRINT('Username cannot be null.')
		RETURN 1
	END

	IF (@Weight IS NOT NULL) BEGIN
		IF (@Weight < 0) BEGIN
			PRINT 'Weight cannot be negative'
			Return 2
		END
	END

	IF (@Height IS NOT NULL) BEGIN
		IF (@Height < 0) BEGIN
			PRINT 'Height cannot be negative'
			Return 3
		END
	END

	IF (@Username NOT IN(select Username From Person))
	BEGIN 
		PRINT('Username is not a registered person. Register person first.')
		RETURN 4
	END 

	DECLARE @ID int 
	SELECT @ID = ID From Person Where Username = @Username

	IF (@ID NOT IN(select ID From Player))
	BEGIN 
		PRINT('ID is not a registered player. Register as player first.')
		RETURN 5
	END 
	
	Update Player SET Height = @Height, [Weight] = @Weight WHERE [ID] = @ID
	RETURN 0
END