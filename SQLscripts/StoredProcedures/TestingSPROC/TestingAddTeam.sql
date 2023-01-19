Use CTW_DB
go

Declare @status int
EXEC @status =  AddTeam
	@Name = 'Legumes',
	@League = 'High School'
;
select @status
select* from Team

Declare @status int
EXEC @status =  AddTeam
	@Name = 'Hot Dogs',
	@League = 'High School'
;
select @status
select* from Team

-- testing no name
Declare @status int
EXEC @status =  AddTeam
	--@Name = 'Hot Dogs',
	@League = 'High School'
;
select @status
select* from Team