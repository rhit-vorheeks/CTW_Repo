/****** Object:  StoredProcedure [dbo].[CheckRegister]    Script Date: 2/15/2023 11:18:36 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- Kaylee
CREATE PROCEDURE [dbo].[CheckRegister]
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
	
	