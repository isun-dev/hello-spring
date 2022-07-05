package hello.hellospring.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDbSource {
    // Connect to your database.
    // Replace server name, username, and password with your credentials
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        String connectionUrl =
                "jdbc:sqlserver://127.0.0.1:1433;"
                        + "database=master;"
                        + "user=sa;"
                        + "password=1StrongPwddocker images";
        System.out.println("데이터 베이스 커넥스 테스트 1-1");
        try (Connection connection = DriverManager.getConnection(connectionUrl);) {
            System.out.println("데이터 베이스 커넥스 테스트 [커넥트 성공]");
            // Code here.
            Statement stmt = connection.createStatement();
            System.out.println("데이터 베이스 커넥스 테스트 [커넥트 스트림 성공]");
            // ResultSet rs = stmt.executeQuery("SELECT TOP 3 AddressID, AddressLine1 FROM Person.address ORDER BY AddressID ASC");
            ResultSet rs = stmt.executeQuery("select * from category");
            System.out.println("데이터 베이스 커넥스 테스트 [쿼리 call 성공]");
            System.out.println(rs);
            while (rs.next()) {
                String categoryId = rs.getString("category_id");
                String categoryName = rs.getString("category_name");
                System.out.println(categoryId + ", " + categoryName);
                System.out.println("==========");
            }
            //
            //            while (rs.next()) {
            //                int AddressID = rs.getInt("AddressID");
            //                String AddressLine1 = rs.getString("AddressLine1");
            //
            //                System.out.println(AddressID + ", " + AddressLine1);
            //            }
            //            rs.close();
            stmt.close();
            connection.close();
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
