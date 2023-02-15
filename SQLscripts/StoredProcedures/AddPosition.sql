/****** Object:  StoredProcedure [dbo].[AddPosition]    Script Date: 2/15/2023 11:16:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--Keegan
CREATE PROCEDURE [dbo].[AddPosition]
    @Name VARCHAR(100),
	@Desc text = null
AS

BEGIN
	IF (@Name is null) BEGIN
		PRINT('Name cannot be null.')
		RETURN 1
	END

	IF(@Name IN (Select [Name] from Position))BEGIN
		PRINT('Position Name is taken.')
		RETURN 2
	END
	
    INSERT INTO Position([Name], [Description])
    VALUES (@Name, @Desc)
	RETURN 0
END