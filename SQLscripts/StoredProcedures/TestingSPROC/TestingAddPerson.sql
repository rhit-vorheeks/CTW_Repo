Use CTW_DB
go

Declare @status int
EXEC @status =  AddPerson 
	@DOB = '2002/11/01',
	@FName = 'Ari',
	@LName = 'Duvall',
	@Username = 'duvallar',
	@Password = '1234',
	@Type = 'Coach',
	@Salt = '5678'
;

Declare @status int
EXEC @status =  AddPerson 
	@DOB = '2003/04/28',
	@FName = 'Kaylee',
	@LName = 'Lane',
	@Username = 'laneks',
	@Password = 'sunshine',
	@Type = 'Coach',
	@Salt = 'pepper'
;
select @status
select * from Person


Declare @status int
EXEC @status =  AddPerson 
	@DOB = '2002/08/14',
	@FName = 'Keegan',
	@LName = 'Vorhees',
	@Username = 'vorheeks',
	@Password = '5678',
	@Type = 'Player',
	@Salt = '3'
;
select @status
select * from Person

--test duplicate
Declare @status int
EXEC @status =  AddPerson 
	@DOB = '2002/09/14',
	@FName = 'Keegan',
	@LName = 'Vorhees',
	@Username = 'vorheeks',
	@Password = '5678',
	@Type = 'Player',
	@Salt = '3'
;
select @status
select * from Person

--test null
Declare @status int
EXEC @status =  AddPerson 
	@DOB = '2002/09/14',
	@FName = null,
	@LName = 'Vorhees',
	@Username = 'vorheeks',
	@Password = '5678',
	@Type = 'Player',
	@Salt = '3'
;
select @status
select * from Person