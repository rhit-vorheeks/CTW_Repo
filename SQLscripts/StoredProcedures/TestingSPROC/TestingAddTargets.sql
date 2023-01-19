Use CTW_DB
go

Declare @status int
EXEC @status =  AddTargets
	@Name = [3-pointers],
	@DrillID = 1

select @status
select * from Targets

Select * From Drill

Declare @status int
EXEC @status =  AddTargets
	@Name = [3-pointers],
	@DrillID = 2

select @status
select * from Targets

Select * From Drill

Declare @status int
EXEC @status =  AddTargets
	@Name = FreeThrows,
	@DrillID = 1

select @status
select * from Targets

Select * From Drill