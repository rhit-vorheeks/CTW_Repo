Use CTW_DB
Go

Declare @status int
EXEC @status =  AddCoaches
	@Username = 'laneks',
	@TeamID = 1,
	@StartDate = '2023/01/17'
	--@EndDate = 
;
select @status
select* from Coaches


Declare @status int
EXEC @status =  AddCoaches
	@Username = 'duvallar',
	@TeamID = 2,
	@StartDate = '2020/03/13',
	@EndDate = '2020/03/13'
;
select @status
select* from Coaches

-- Hiring Back
Declare @status int
EXEC @status =  AddCoaches
	@Username = 'duvallar',
	@TeamID = 2,
	@StartDate = '2021/03/13'
	--@EndDate = '2020/03/13'
;
select @status
select* from Coaches

-- Illegal Start
Declare @status int
EXEC @status =  AddCoaches
	@Username = 'duvallar',
	@TeamID = 2,
	@StartDate = '2025/03/13'
	--@EndDate = '2020/03/13'
;
select @status
select* from Coaches

-- Illegal EndDate
Declare @status int
EXEC @status =  AddCoaches
	@Username = 'duvallar',
	@TeamID = 2,
	@StartDate = '2019/03/13',
	@EndDate = '2020/03/13'
;
select @status
select* from Coaches

-- Illegal EndDate
Declare @status int
EXEC @status =  AddCoaches
	@Username = 'duvallar',
	@TeamID = 2,
	@StartDate = '2019/03/13',
	@EndDate = '2020/03/13'
;
select @status
select* from Coaches

-- Illegal Start (Already Coaching)
Declare @status int
EXEC @status =  AddCoaches
	@Username = 'duvallar',
	@TeamID = 2,
	@StartDate = '2021/03/14'
	--@EndDate = '2020/03/13'
;
select @status
select* from Coaches

--Test Hire back

Declare @status int
EXEC @status =  AddCoaches
	@Username = 'laneks',
	@TeamID = 2,
	@StartDate = '2019/03/13',
	@EndDate = '2020/03/13'
;
select @status
select* from Coaches

Declare @status int
EXEC @status =  AddCoaches
	@Username = 'laneks',
	@TeamID = 2,
	@StartDate = '2021/03/13'
	--@EndDate = '2020/03/13'
;
select @status
select* from Coaches




