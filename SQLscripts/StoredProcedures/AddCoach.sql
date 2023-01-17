USE CTW_DB
GO

CREATE PROCEDURE AddCoach
    @ID int,
    @Rank VARCHAR(50)
AS

BEGIN
	IF (@ID is null) BEGIN
		RAISERROR('ID cannot be null.', 14, 1)
		RETURN 1
	END
	
    INSERT INTO Coach (ID, Rank)
    VALUES (@ID, @Rank)
	RETURN 0
END