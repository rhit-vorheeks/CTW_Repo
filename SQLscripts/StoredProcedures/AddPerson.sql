USE CTW_DB
GO
-- Kaylee
CREATE PROCEDURE AddPerson
	@DOB date,
	@FName varchar(100) ,
	@LName varchar(100),
	@Username varchar(50),
	@Password varchar(50),
	@Type varchar(100) = null,
	@Salt varchar(50)
AS

BEGIN
	IF (@FName is null OR @LName is null OR @FName = '' OR @LName='' ) BEGIN
		RAISERROR('First or last name cannot be null or empty.', 14, 1)
		RETURN 1
	END
	IF (@DOB is null OR @DOB = '') BEGIN
		RAISERROR('Date of Birth cannot be null or empty.', 14, 1)
		RETURN 2
	END
	IF (@Username is null OR @Password is null OR @Username='' OR @Password='') BEGIN
		RAISERROR('Username or Password cannot be null.', 14, 1)
		RETURN 3
	END
	IF (@Salt is null Or @Salt ='') BEGIN
		RAISERROR('Salt cannot be null.', 14, 1)
		RETURN 4
	END
	IF(@Username IN (select Username From Person))BEGIN
		RAISERROR('This Username is already taken.', 14, 1)
		RETURN 5
	END


    INSERT INTO Person(DOB, FName, LName, Username, [Password], [Type], Salt )
    VALUES (@DOB, @FName, @LName, @Username, @Password, @Type, @Salt)

	IF(NOT EXISTS(Select @Username From Person Where Username = @Username))
	BEGIN
		RAISERROR('Person add unsuccessful.', 14, 1)
		RETURN 6
	END


	RETURN 0
END