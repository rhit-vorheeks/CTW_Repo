Use CTW_DB
go

Declare @status int
EXEC @status = AddStat
	@Name = FreeThrows,
    @Desc = 'Free throw percentage for game'

select @status
select * from Stat

Declare @status int
EXEC @status = AddStat
	@Name = [3-pointers],
    @Type = 'offense'

select @status
select * from Stat
	

Declare @status int
EXEC @status = AddStat
	@Name = [Field Goals],
	@Desc = '2-point shots',
    @Type = 'offense'

select @status
select * from Stat

Declare @status int
EXEC @status = AddStat
	@Name = Fake
    

select @status
select * from Stat