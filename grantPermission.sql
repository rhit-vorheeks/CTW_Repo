Use CTW_DB
CREATE USER [vorheeks] FROM LOGIN [vorheeks]; 
exec sp_addrolemember 'db_owner', 'vorheeks'; 
CREATE USER [laneks] FROM LOGIN [laneks]; 
exec sp_addrolemember 'db_owner', 'laneks'; 


GO