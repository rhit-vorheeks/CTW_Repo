USE CTW_DB
GO
-- Kaylee
CREATE PROCEDURE CheckRegister
	@Username varchar(50)

	AS 
	BEGIN
		IF(@Username is null or @Username = '') BEGIN
			RAISERROR('Must provide a username', 14, 1)
			RETURN 1
		END

		IF(EXISTS(Select Username From Person Where Username = @Username)) BEGIN
			RAISERROR('This username is taken', 14, 1)
			RETURN 2
		END
	END
	