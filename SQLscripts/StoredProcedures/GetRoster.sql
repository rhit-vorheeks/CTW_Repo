USE CTW_DB
GO

CREATE Proc GetRoster
	@CoachUsername varchar(50)

AS

BEGIN
Declare @CoachID int
SELECT @CoachID = ID FROM Person WHERE Username = @CoachUsername


SELECT T.Name AS 'Team Name', P.FName AS 'First Name', P.Lname AS 'Last Name', P.Username as 'Username', PO.PositionName as 'Position'
FROM Team T Join PlaysOn PO on T.ID = PO.TeamID
JOIN Person P on P.ID = PO.PlayerID
JOIN Coaches C on C.TeamID = T.ID 
WHERE C.CoachID = @CoachID
GROUP BY T.Name, P.Lname, P.FName, P.Username, PO.PositionName

END