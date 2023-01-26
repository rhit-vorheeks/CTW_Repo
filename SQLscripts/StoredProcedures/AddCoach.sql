USE CTW_DB
GO
-- Keegan
CREATE PROCEDURE AddCoach
    @Username VARCHAR(50),
    @Rank VARCHAR(50) = null
AS

BEGIN
	IF (@Username is null) BEGIN
		PRINT('Username cannot be null.')
		RETURN 1
	END

	IF (@Username NOT IN(select Username From Person))
	BEGIN 
		PRINT('Coach is not a registerd person. Register person first.')
		RETURN 2
	END 

	DECLARE @ID int 
	SELECT @ID = ID From Person Where Username = @Username
	
	IF(EXISTS(Select ID From Coach where ID = @ID))Begin
		PRINT('Coach already exists.')
		RETURN 3
	END


    INSERT INTO Coach (ID, Rank)
    VALUES (@ID, @Rank)
	RETURN 0
END