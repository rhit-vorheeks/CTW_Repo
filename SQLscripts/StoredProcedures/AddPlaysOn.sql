USE [CTW_DB]
GO
/****** Object:  StoredProcedure [dbo].[AddPlaysOn]    Script Date: 2/7/2023 7:19:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- Ari
CREATE PROCEDURE [dbo].[AddPlaysOn]
    @TeamID int,
    @Username varchar(50),
	@PositionName varchar(100),
	@StartDate date = null,
	@EndDate date = null
	
AS

BEGIN
	IF (@TeamID is null) BEGIN
		PRINT('TeamID cannot be null.')
		RETURN 1
	END

	IF (@Username is null) BEGIN
		PRINT('Username cannot be null.')
		RETURN 2
	END

	IF (@PositionName is null) BEGIN
		PRINT('Position name cannot be null.')
		RETURN 3
	END

	IF (@StartDate is null ) BEGIN
		set @StartDate = GetDate() 
	END
	

	IF (@TeamID NOT IN(select ID From Team))
	BEGIN 
		PRINT('This team does not exist.')
		RETURN 4
	END
	
	IF(@Username NOT IN(select Username From Person))Begin
		PRINT('This person is not a Person. Add Person first.')
		RETURN 5

	End 

	DECLARE @ID int 
	SELECT @ID = ID From Person Where Username = @Username

	IF(@ID NOT IN (Select ID From Player)) Begin
		PRINT('This person is not a Player. Add player first.')
		RETURN 6
	END

	IF (@PositionName NOT IN(select [Name] From Position))
	BEGIN 
		PRINT('This position does not exist.')
		RETURN 7
	END

	IF (@StartDate > GETDATE() ) BEGIN
		PRINT('Illegal Start Date.')
		RETURN 8
	END

	IF (@StartDate > @EndDate ) BEGIN
		PRINT('Illegal End Date.')
		RETURN 9
	END


	IF(EXISTS(select PositionName, PlayerID, TeamID From PlaysOn Where PlayerID = @ID AND PositionName = @PositionName
	AND TeamID = @TeamID AND (EndDate is null or EndDate = '')))BEGIN
		PRINT('Player already plays this position on this team.')
		RETURN 10
	END



	IF(EXISTS(select PlayerID, TeamID, PositionName From PlaysOn Where PlayerID = @ID AND TeamID = @TeamID AND PositionName = @PositionName ))BEGIN
		
		Declare @LastStartDate date
		Select @LastStartDate = MAX (StartDate) From PlaysOn Where PlayerID = @ID AND TeamID = @TeamID Group by PlayerID, TeamID
		
		Declare @End date
		select @End = EndDate from PlaysOn where TeamID = @TeamID AND PlayerID= @ID  AND StartDate = @LastStartDate AND PositionName = @PositionName;

		IF(@End is null OR @End ='')Begin 
			PRINT('Player still plays this position on this team.')
			RETURN 11

		End 

		IF(Exists(Select StartDate From PlaysOn where PlayerID = @ID AND TeamID = @TeamID and StartDate = @StartDate and PositionName = @PositionName )) BEGIN
			PRINT 'Cannot start same position twice in one day'
			Return 12
		END


	END


	INSERT INTO PlaysOn(PlayerID, TeamID, PositionName, [StartDate], EndDate)
    VALUES (@ID, @TeamID, @PositionName, @StartDate, @EndDate)
	RETURN 0
END