USE CTW_DB
GO
-- Ari
CREATE PROCEDURE AddPlaysOn
    @TeamID int,
    @Username varchar(50),
	@PositionName varchar(100),
	@StartDate date = null,
	@EndDate date = null
	
AS

BEGIN
	IF (@TeamID is null) BEGIN
		RAISERROR('TeamID cannot be null.', 14, 1)
		RETURN 1
	END

	IF (@Username is null) BEGIN
		RAISERROR('Username cannot be null.', 14, 1)
		RETURN 2
	END

	IF (@PositionName is null) BEGIN
		RAISERROR('Position name cannot be null.', 14, 1)
		RETURN 3
	END

	IF (@StartDate is null ) BEGIN
		set @StartDate = GetDate() 
	END
	

	IF (@TeamID NOT IN(select ID From Team))
	BEGIN 
		RAISERROR('This team does not exist.', 14, 1)
		RETURN 4
	END
	
	IF(@Username NOT IN(select Username From Person))Begin
		RAISERROR('This person is not a Person. Add Person first.', 14, 1)
		RETURN 5

	End 

	DECLARE @ID int 
	SELECT @ID = ID From Person Where Username = @Username

	IF(@ID NOT IN (Select ID From Player)) Begin
		RAISERROR('This person is not a Player. Add player first.', 14, 1)
		RETURN 6
	END

	IF (@PositionName NOT IN(select [Name] From Position))
	BEGIN 
		RAISERROR('This position does not exist.', 14, 1)
		RETURN 7
	END

	IF (@StartDate > GETDATE() ) BEGIN
		RAISERROR('Illegal Start Date.', 14, 1)
		RETURN 8
	END

	IF (@StartDate > @EndDate ) BEGIN
		RAISERROR('Illegal End Date.', 14, 1)
		RETURN 9
	END

	IF(EXISTS(select PositionName, PlayerID, TeamID From PlaysOn Where PlayerID = @ID AND PositionName = @PositionName
	AND TeamID = @TeamID))BEGIN
		RAISERROR('Player already plays on this position on this team.', 14, 1)
		RETURN 10
	END

	INSERT INTO PlaysOn(PlayerID, TeamID, PositionName, [StartDate], EndDate)
    VALUES (@ID, @TeamID, @PositionName, @StartDate, @EndDate)
	RETURN 0
END