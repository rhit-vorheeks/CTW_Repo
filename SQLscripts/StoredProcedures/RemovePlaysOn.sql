USE CTW_DB
GO

Create Proc RemovePlaysOn
	@TeamID int,
    @Username varchar(50),
	@PositionName varchar(100)

AS
BEGIN


DECLARE @ID int 

SELECT @ID = ID From Person Where Username = @Username

DELETE FROM PlaysOn WHERE PlayerID = @ID and TeamID = @TeamID and PositionName = @PositionName



IF(EXISTS(SELECT PlayerID, TeamID, PositionName FROM PlaysOn WHERE TeamID = @TeamID and PositionName = @PositionName and PlayerID = @ID))
BEGIN
	PRINT 'Player was not deleted'
	Return 1
END

	Print 'Player was removed from specified position on the team'
	RETURN 0
END