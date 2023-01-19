Use CTW_DB
go

Declare @status int
EXEC @status =  AddPosition
	@Name ='Point Guard',
	@Desc = 'The point guard runs the offense and usually is the team''s best dribbler and passer. The point guard defends the opponent''s point guard and tries to steal the ball. The shooting guard is usually the team''s best shooter. The shooting guard can make shots from long distance and also is a good dribbler.'
;
select @status
select* from Position



-- testing pos with no description
Declare @status2 int
EXEC @status2 = AddPosition
	@Name ='Shooting Guard',
	@Desc = ''
;
select @status2
select* from Position

--Try point guard again, shouldn't work bc name is taken
Declare @status3 int
EXEC @status3 =  AddPosition
	@Name ='Point Guard',
	@Desc = 'The point guard runs the offense and usually is the team''s best dribbler and passer. The point guard defends the opponent''s point guard and tries to steal the ball. The shooting guard is usually the team''s best shooter. The shooting guard can make shots from long distance and also is a good dribbler.'
;
select @status3
select* from Position

--Test no null name values
Declare @status4 int
EXEC @status4 =  AddPosition
	@Name = null,
	@Desc = 'The point guard runs the offense and usually is the team''s best dribbler and passer. The point guard defends the opponent''s point guard and tries to steal the ball. The shooting guard is usually the team''s best shooter. The shooting guard can make shots from long distance and also is a good dribbler.'
;
select @status4
select* from Position