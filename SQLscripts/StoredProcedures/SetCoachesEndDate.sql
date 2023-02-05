USE [CTW_DB]
GO

CREATE Proc [dbo].[setCoachesEndDate]
    @Username varchar(50),
	@TeamID int
	

AS
BEGIN


DECLARE @ID int 

SELECT @ID = ID From Person Where Username = @Username

Update Coaches SET EndDate = GetDate() WHERE CoachID = @ID and TeamID = @TeamID and EndDate is null



IF(EXISTS(SELECT CoachID, TeamID FROM Coaches WHERE TeamID = @TeamID and CoachID = @ID and EndDate is null))
BEGIN
	PRINT 'Coach still has their job'
	Return 1
END

	Print 'Coach does not have their job'
	RETURN 0
END


