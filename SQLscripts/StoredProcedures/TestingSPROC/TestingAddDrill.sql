Use CTW_DB
go

Declare @status int
EXEC @status =  AddDrill
	@Name ='Layup',
	@Desc = 'This is a test drill.'
;
select @status
select* from Drill

Declare @status int
EXEC @status =  AddDrill
	@Name ='Down and Back',
	@Desc = 'This is a test drill.'
;
select @status
select* from Drill

--Test unique drill name
Declare @status int
EXEC @status =  AddDrill
	@Name ='Layup',
	@Desc = 'This is test drill 2.'
;
select @status
select* from Drill

--Test drill name not null
Declare @status int
EXEC @status =  AddDrill
	--@Name ='Layup',
	@Desc = 'This is test drill 2.'
;
select @status
select* from Drill