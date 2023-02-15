/****** Object:  StoredProcedure [dbo].[AddStat]    Script Date: 2/15/2023 11:17:20 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- Kaylee
CREATE PROCEDURE [dbo].[AddStat]
    @Name VARCHAR(50),
    @Desc text = null,
	@Type varchar(100) =null
AS

BEGIN
	IF (@Name is null) BEGIN
		PRINT('Name cannot be null.')
		RETURN 1
	END

	IF (@Name IN(select [Name] From Stat))
	BEGIN 
		PRINT('This stat already exists.')
		RETURN 2
	END 

	INSERT INTO Stat ([Name], [Description], [Type])
    VALUES (@Name, @Desc, @Type)
	RETURN 0
END