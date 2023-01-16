ALTER DATABASE [LabCreateDB-2_duvallar]
MODIFY FILE ( NAME = 'LabCreateDB-2_duvallarLog',
MAXSIZE = 30MB)
GO

ALTER DATABASE [LabCreateDB-2_duvallar]
MODIFY FILE ( NAME = 'LabCreateDB-2_duvallarLog',
SIZE = 10MB)
GO

ALTER DATABASE [LabCreateDB-2_duvallar]
MODIFY FILE ( NAME = 'LabCreateDB-2_duvallarLog',
FILEGROWTH = 22%)
GO