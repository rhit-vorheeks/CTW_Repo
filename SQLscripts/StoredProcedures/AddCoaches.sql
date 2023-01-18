USE CTW_DB
GO
-- Keegan
CREATE PROCEDURE AddCoaches
    @Username VARCHAR(50),
    @TeamID int,
	@StartDate date,
	@EndDate date = null
AS

BEGIN
	IF (@Username is null) BEGIN
		RAISERROR('Coach Username cannot be null.', 14, 1)
		RETURN 1
	END

	IF (@StartDate is null) BEGIN
		RAISERROR('Start Date cannot be null.', 14, 1)
		RETURN 2
	END

	IF (@TeamID is null) BEGIN
		RAISERROR('Team ID cannot be null.', 14, 1)
		RETURN 3
	END

	IF (@Username NOT IN(select Username From Person))
	BEGIN 
		RAISERROR('Coach is not a registerd person. Register person first.', 14, 1)
		RETURN 4
	END 

	DECLARE @ID int 
	SELECT @ID = ID From Person Where Username = @Username

	IF(@ID NOT IN (Select ID From Coach)) Begin
		RAISERROR('This person is not a Coach. Add coach first.', 14, 1)
		RETURN 5
	END
	

	IF (@TeamID NOT IN(select ID From Team))
	BEGIN 
		RAISERROR('Team is not a registerd team. Add team first.', 14, 1)
		RETURN 6
	END 
	
	IF(@EndDate>@StartDate)Begin
		RAISERROR('Illegal End Date.', 14, 1)
		RETURN 7
	END

	IF(GETDATE() > @StartDate)BEGIN
		RAISERROR('Illegal Start Date.', 14, 1)
		RETURN 8
	END

	IF(EXISTS(select CoachID, TeamID From Coaches Where CoachID = @ID AND TeamID = @TeamID))BEGIN
		RAISERROR('Coach is already on the team.', 14, 1)
		RETURN 9
	END


    INSERT INTO Coaches ([CoachID], TeamID, StartDate,EndDate)
    VALUES (@ID, @TeamID, @StartDate, @EndDate)
	RETURN 0
END