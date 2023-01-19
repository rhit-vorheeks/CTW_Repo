Use CTW_DB
go

Declare @status int
EXEC @status =  AddCoach 
	@Username ='duvallar',
	@Rank = 'Head'
;
select @status
select* from Coach

Declare @status int
EXEC @status =  AddCoach 
	@Username ='laneks',
	@Rank = 'Assistant'
;
select @status
select* from Coach

-- testing same user different position
Declare @status int
EXEC @status =  AddCoach 
	@Username ='laneks',
	@Rank = 'Head'
;
select @status
select* from Coach