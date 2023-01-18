USE CTW_DB
GO
-- Keegan
CREATE PROCEDURE AddCoach
    @Username VARCHAR(50),
    @Rank VARCHAR(50) = null
AS

BEGIN
	IF (@Username is null) BEGIN
		RAISERROR('Username cannot be null.', 14, 1)
		RETURN 1
	END

	IF (@Username NOT IN(select Username From Person))
	BEGIN 
		RAISERROR('Coach is not a registerd person. Register person first.', 14, 1)
		RETURN 2
	END 

	DECLARE @ID int 
	SELECT @ID = ID From Person Where Username = @Username
	
	IF(EXISTS(Select ID From Coach where ID = @ID))Begin
		RAISERROR('Coach already exists.', 14, 1)
		RETURN 3
	END


    INSERT INTO Coach (ID, Rank)
    VALUES (@ID, @Rank)
	RETURN 0
END