/****** Object:  StoredProcedure [dbo].[GetRoster]    Script Date: 2/15/2023 11:20:46 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE Proc [dbo].[GetRoster]
	@CoachUsername varchar(50)

AS

BEGIN
SELECT T.[Name] AS 'Team Name', P.FName AS 'First Name', P.Lname AS 'Last Name', P.Username as 'Username', PO.PositionName as 'Position'
FROM Team T Join PlaysOn PO on T.ID = PO.TeamID
JOIN Person P on P.ID = PO.PlayerID
JOIN Coaches C on C.TeamID = T.ID 
JOIN Person P2 on P2.ID = C.CoachID
WHERE P2.Username = @CoachUsername
GROUP BY T.[Name], P.Lname, P.FName, P.Username, PO.PositionName
END