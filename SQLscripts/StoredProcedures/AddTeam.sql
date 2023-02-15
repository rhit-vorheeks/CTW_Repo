/****** Object:  StoredProcedure [dbo].[AddTeam]    Script Date: 2/15/2023 11:18:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--Keegan
CREATE PROCEDURE [dbo].[AddTeam]
    @Name VARCHAR(100),
	@League VARCHAR(100) = null
AS

BEGIN
	IF (@Name is null) BEGIN
		PRINT 'Name cannot be null.'
		RETURN -1
	END
		
    INSERT INTO Team([Name], League)
    VALUES (@Name, @League)
	RETURN @@identity
END