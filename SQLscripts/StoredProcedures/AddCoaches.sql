USE [CTW_DB]
GO
/****** Object:  StoredProcedure [dbo].[AddCoaches]    Script Date: 2/7/2023 7:18:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- Keegan
CREATE PROCEDURE [dbo].[AddCoaches]
    @Username VARCHAR(50),
    @TeamID int,
	@StartDate date,
	@EndDate date = null
AS

BEGIN
	IF (@Username is null) BEGIN
		PRINT('Coach Username cannot be null.')
		RETURN 1
	END

	IF (@StartDate is null) BEGIN
		PRINT('Start Date cannot be null.')
		RETURN 2
	END

	IF (@TeamID is null) BEGIN
		PRINT('Team ID cannot be null.')
		RETURN 3
	END

	IF (@Username NOT IN(select Username From Person))
	BEGIN 
		PRINT('Coach is not a registerd person. Register person first.')
		RETURN 4
	END 

	DECLARE @ID int 
	SELECT @ID = ID From Person Where Username = @Username

	IF(@ID NOT IN (Select ID From Coach)) Begin
		PRINT('This person is not a Coach. Add coach first.')
		RETURN 5
	END
	

	IF (@TeamID NOT IN(select ID From Team))
	BEGIN 
		PRINT('Team is not a registerd team. Add team first.')
		RETURN 6
	END 
	
	IF(@EndDate<@StartDate)Begin
		PRINT('Illegal End Date.')
		RETURN 7
	END

	IF(GETDATE() < @StartDate)BEGIN
		PRINT('Illegal Start Date.')
		RETURN 8
	END

	

	Declare @TName varchar(50)
	Select @TName = [Name] From Team Where ID = @TeamID
	IF(EXISTS (Select t.[Name] From Team t join coaches c on t.ID = c.TeamID Where t.[Name] = @TName AND c.CoachID = @ID and (c.EndDate is null or c.EndDate = ''))) BEGIN
		Print 'Coach already coaches a team with this name'
		RETURN 11
	END


	IF(EXISTS(select CoachID, TeamID From Coaches Where CoachID = @ID AND TeamID = @TeamID ))BEGIN
		
		Declare @LastStartDate date
		Select @LastStartDate = MAX (StartDate) From Coaches Where CoachID = @ID AND TeamID = @TeamID Group by CoachID, TeamID
		
		Declare @End date
		select @End = EndDate from Coaches where TeamID = @TeamID AND CoachID= @ID  AND StartDate = @LastStartDate;

		IF(@End is null OR @End ='')Begin 
			PRINT('Coach still holds this position.')
			RETURN 10

		End 

		IF(Exists(Select StartDate From Coaches where CoachID = @ID AND TeamID = @TeamID and StartDate = @StartDate )) BEGIN
			PRINT 'Cannot start same position twice in one day'
			Return 12
		END
	END


	IF(EXISTS(select CoachID, TeamID From Coaches Where CoachID = @ID AND TeamID = @TeamID AND StartDate = @StartDate))BEGIN
		PRINT('Coach is already on the team.')
		RETURN 9
	END


    INSERT INTO Coaches ([CoachID], TeamID, StartDate,EndDate)
    VALUES (@ID, @TeamID, @StartDate, @EndDate)
	RETURN 0
END