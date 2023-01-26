USE CTW_DB
GO
-- Ari
CREATE PROCEDURE AddPlayer
    @Username VARCHAR(50),
    @Height int = null,
	@Weight int =null
AS

BEGIN
	IF (@Username is null) BEGIN
		PRINT('Username cannot be null.')
		RETURN 1
	END

	IF (@Username NOT IN(select Username From Person))
	BEGIN 
		PRINT('Player is not a registerd person. Register person first.')
		RETURN 2
	END 

	DECLARE @ID int 
	SELECT @ID = ID From Person Where Username = @Username
	
	IF(EXISTS(Select ID From Player where ID = @ID))Begin
		PRINT('Player already exists.')
		RETURN 3
	END


    INSERT INTO Player (ID, Height, Weight)
    VALUES (@ID, @Height, @Weight)
	RETURN 0
END