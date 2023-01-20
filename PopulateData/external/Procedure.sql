ALTER PROCEDURE OrderHistorySummary
(@customerUsername nvarchar(20))
AS 
SELECT OrderID, Placed, TotalPrice
FROM CustomerOrderHistorySummary
WHERE CustomerUsername = @customerUsername

GO
EXEC OrderHistorySummary @customerUsername='gray17'