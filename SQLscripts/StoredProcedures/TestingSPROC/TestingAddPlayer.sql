Use CTW_DB
go

Declare @status int
EXEC @status =  AddPlayer
	@Username ='vorheeks',
	@Height = 52,
	@Weight = 50
;
select @status
select* from Player


-- testing Not in person table
Declare @status int
EXEC @status =  AddPlayer 
	@Username ='yourmom',
	@Height = 52,
	@Weight = 50
;

select @status
select* from Player