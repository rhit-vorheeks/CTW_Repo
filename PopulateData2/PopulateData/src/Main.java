import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";

        String fullUrl = url
                .replace("${dbServer}", "titan.csse.rose-hulman.edu")
                .replace("${dbName}", "FoodDeliveryMohan30")
                .replace("${user}", "testAccount")
                .replace("${pass}", "Password123");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(fullUrl);
            CallableStatement stmt = connection.prepareCall("{? = call OrderHistorySummary(?)}");
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, "gray17");
            ResultSet results = stmt.executeQuery();


            while (results.next()) {
                int orderID = results.getInt("OrderID");
                Date placed = results.getDate("Placed");
                float totalPrice = results.getFloat("TotalPrice");
                System.out.println("OrderID: " + orderID + ", Date Placed: " + placed + ", TotalPrice: " + totalPrice);
            }
            //This must be done after reading the result set (JDBC rules, apparently)
            int returnCode = stmt.getInt(1);
            if (returnCode == 0) {
                System.out.println("Success!");
            }
            else {
                System.out.println("Procedure reported an error.");
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (connection!=null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
