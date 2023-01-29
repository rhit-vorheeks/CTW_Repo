/*
Create Proc UpdatePlaysOn
	@TeamID int,
    @Username varchar(50),
	@PositionName varchar(100)

AS
BEGIN

DECLARE @ID int 
SELECT @ID = ID From Person Where Username = @Username

Update PlaysOn
SET PositionName = @PositionName
WHERE TeamID = @TeamID 

END*/