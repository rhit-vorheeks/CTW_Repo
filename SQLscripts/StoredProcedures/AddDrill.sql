/****** Object:  StoredProcedure [dbo].[AddDrill]    Script Date: 2/15/2023 11:13:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--Ari
CREATE PROCEDURE [dbo].[AddDrill]
    @Name VARCHAR(100),
	@Desc text = null
AS

BEGIN
	IF (@Name is null) BEGIN
		PRINT('Name cannot be null.')
		RETURN 1
	END

	IF(@Name IN (Select [Name] from Drill))BEGIN
		PRINT('Drill Name is taken.')
		RETURN 2
	END
	
    INSERT INTO Drill([Name], [Description])
    VALUES (@Name, @Desc)
	RETURN 0
END