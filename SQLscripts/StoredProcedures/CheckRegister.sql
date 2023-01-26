USE CTW_DB
GO
-- Kaylee
ALTER PROCEDURE CheckRegister
	@Username varchar(50)

	AS 
	BEGIN
		IF(@Username is null or @Username = '') BEGIN
			PRINT 'Must provide a username'
			RETURN 1
		END

		IF(EXISTS(Select Username From Person Where Username = @Username)) BEGIN
			PRINT 'This username is taken'
			RETURN 2
		END

		RETURN 0
	END
	