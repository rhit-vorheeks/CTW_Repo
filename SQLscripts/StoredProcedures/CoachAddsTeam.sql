Use CTW_DB
GO
Create PROC CoachAddsTeam
	@CoachUsername varchar(50),
	@TeamName varchar(100),
	@TeamLeague varchar(100)


AS
BEGIN
BEGIN TRANSACTION
	
	Declare @TID int
	EXEC @TID = AddTeam 
	@Name = @TeamName,
	@League = @TeamLeague

	IF(@@ERROR <> 0) BEGIN
		PRINT 'Error adding team'
		ROLLBACK TRANSACTION
		RETURN 1
	END

	IF(@TID < 0) BEGIN
		PRINT 'Team name is null'
		ROLLBACK TRANSACTION
		RETURN 2
	END

	IF(NOT EXISTS(SELECT * FROM Team WHERE ID = @TID)) BEGIN
		PRINT 'Team was not added'
		ROLLBACK TRANSACTION
		RETURN 3
	END

	DECLARE @Date date
	SET @DATE = GETDATE()

	DECLARE @Status int
	EXEC @Status = AddCoaches 
	@Username = @CoachUsername,
	@TeamID = @TID,
	@StartDate = @Date

	IF(@@ERROR <> 0) BEGIN
		PRINT 'Error adding Coaches'
		ROLLBACK TRANSACTION
		RETURN 4

	END

	IF(@Status <> 0) BEGIN
		PRINT 'AddCoaches error'
		ROLLBACK TRANSACTION
		RETURN 5
	END


	COMMIT TRANSACTION
	RETURN 0

End
