/****** Object:  StoredProcedure [dbo].[SetPlaysOnEndDate]    Script Date: 2/15/2023 11:22:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/****** Object:  StoredProcedure [dbo].[RemovePlaysOn]    Script Date: 1/31/2023 8:13:34 PM ******/

CREATE Proc [dbo].[SetPlaysOnEndDate]
	@TeamID int,
    @Username varchar(50),
	@PositionName varchar(100)

AS
BEGIN


DECLARE @ID int 

SELECT @ID = ID From Person Where Username = @Username

Update PlaysOn Set EndDate = GETDATE() WHERE PlayerID = @ID and TeamID = @TeamID and PositionName = @PositionName and EndDate is null



IF(EXISTS(SELECT PlayerID, TeamID, PositionName FROM PlaysOn WHERE TeamID = @TeamID and PositionName = @PositionName and PlayerID = @ID and EndDate is null))
BEGIN
	PRINT 'Player position did not end'
	Return 1
END

	Print 'Player ended specified position on the team'
	RETURN 0
END

