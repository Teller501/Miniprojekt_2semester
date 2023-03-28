package dk.kea.Wishlist.repository;

import dk.kea.Wishlist.utility.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class testrepository {
    public List<String> getTest() {

        List <String> test = new ArrayList<>();
        try (Connection conn = DBManager.getConnection()) {
            String sql = "SELECT * FROM users";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                test.add(username);
                test.add(password);
                test.add(email);
                test.add(first_name);
                test.add(last_name);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return test;
    }
}
