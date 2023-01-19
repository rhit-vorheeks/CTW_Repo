Use CTW_DB
go

Declare @status int
EXEC @status =  AddHasStat 
	@Name = '3-pointers',
	@Username ='vorheeks',
	@Date = '01/01/2023',
	@Quant = 3
;
select @status
select* from HasStat

-- test null name
Declare @status int
EXEC @status =  AddHasStat 
	@Name = null,
	@Username ='vorheeks',
	@Date = '01/01/2023',
	@Quant = 3
;
select @status
select* from HasStat


--test null username
Declare @status int
EXEC @status =  AddHasStat 
	@Name = '3-pointers',
	@Username = null,
	@Date = '01/01/2023',
	@Quant = 3
;
select @status
select* from HasStat


--test already exists
Declare @status int
EXEC @status =  AddHasStat 
	@Name = '3-pointers',
	@Username ='vorheeks',
	@Date = '01/01/2023',
	@Quant = 3
;
select @status
select* from HasStat

--stat doesn't exist
Declare @status int
EXEC @status =  AddHasStat 
	@Name = 'loser',
	@Username ='vorheeks',
	@Date = '01/01/2023',
	@Quant = 3
;
select @status
select* from HasStat

--Isn't a player
Declare @status int
EXEC @status =  AddHasStat 
	@Name = '3-pointers',
	@Username ='bozo',
	@Date = '01/01/2023',
	@Quant = 3
;
select @status
select* from HasStat

--Illegal quantity
Declare @status int
EXEC @status =  AddHasStat 
	@Name = '3-pointers',
	@Username ='vorheeks',
	@Date = '01/01/2023',
	@Quant = -1
;
select @status
select* from HasStat

--Illegal date
Declare @status int
EXEC @status =  AddHasStat 
	@Name = '3-pointers',
	@Username ='vorheeks',
	@Date = '05/10/2023',
	@Quant = 4
;
select @status
select* from HasStat