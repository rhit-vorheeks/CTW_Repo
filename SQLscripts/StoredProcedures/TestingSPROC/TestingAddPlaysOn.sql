Use CTW_DB
go

Declare @status int
EXEC @status =  AddPlaysOn
	@TeamID =1,
    @Username = 'vorheeks',
	@PositionName = 'Point Guard',
	@StartDate  = '01/10/2020',
	@EndDate = '1/20/2021'
;

select @status
select* from PlaysOn

--Testing default start date
Declare @status int
EXEC @status =  AddPlaysOn
	@TeamID =1,
    @Username = 'vorheeks',
	@PositionName = 'Shooting Guard'
	--@StartDate  = '01/10/2020',
	--@EndDate = '1/20/2021'
;

select @status
select* from PlaysOn

--Testing TeamID not null
Declare @status int
EXEC @status =  AddPlaysOn
	--@TeamID =1,
    @Username = 'jrain',
	@PositionName = 'Shooting Guard',
	@StartDate  = '01/10/2020',
	@EndDate = '1/20/2021'
;

select @status
select* from PlaysOn

--Testing TeamID not exist
Declare @status int
EXEC @status =  AddPlaysOn
	@TeamID =10,
    @Username = 'jrain',
	@PositionName = 'Shooting Guard',
	@StartDate  = '01/10/2020',
	@EndDate = '1/20/2021'
;

select @status
select* from PlaysOn


--Testing Player not exist
Declare @status int
EXEC @status =  AddPlaysOn
	@TeamID =1,
    @Username = 'Joe789',
	@PositionName = 'Shooting Guard',
	@StartDate  = '01/10/2020',
	@EndDate = '1/20/2021'
;

select @status
select* from PlaysOn

--Testing key already exist
Declare @status int
EXEC @status =  AddPlaysOn
	@TeamID =1,
    @Username = 'vorheeks',
	@PositionName = 'Point Guard',
	@StartDate  = '01/10/2020',
	@EndDate = '1/20/2021'
;

select @status
select* from PlaysOn


--Testing illegal start date
Declare @status int
EXEC @status =  AddPlaysOn
	@TeamID =1,
    @Username = 'jrain',
	@PositionName = 'Point Guard',
	@StartDate  = '01/10/2025',
	@EndDate = '1/20/2021'
;

select @status
select* from PlaysOn


--Testing illegal end date
Declare @status int
EXEC @status =  AddPlaysOn
	@TeamID =1,
    @Username = 'jrain',
	@PositionName = 'Point Guard',
	@StartDate  = '01/10/2023',
	@EndDate = '1/20/2021'
;

select @status
select* from PlaysOn

