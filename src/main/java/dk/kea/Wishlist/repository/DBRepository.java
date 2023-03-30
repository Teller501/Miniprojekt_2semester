package dk.kea.Wishlist.repository;

import dk.kea.Wishlist.dto.UserFormDTO;
import dk.kea.Wishlist.dto.WishFormDTO;
import dk.kea.Wishlist.dto.WishlistFormDTO;
import dk.kea.Wishlist.dto.WishlistWishCountDTO;
import dk.kea.Wishlist.model.User;
import dk.kea.Wishlist.model.Wishlist;
import dk.kea.Wishlist.utility.DBManager;
import dk.kea.Wishlist.utility.LoginSampleException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository("wishlist_DB")
public class DBRepository implements IRepository{
    @Override
    public User login(String email, String password) throws LoginSampleException {
        try{
            Connection con = DBManager.getConnection();
            String SQL = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                long id = rs.getLong("id");
                String username = rs.getString("username");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                LocalDate birthday = rs.getDate("birthday").toLocalDate();
                User user = new User(email, password, username, firstName, lastName, birthday);
                user.setId(id);
                return user;
            } else {
                throw new LoginSampleException("Could not validate user");
            }
        } catch(SQLException ex){
            throw new LoginSampleException(ex.getMessage());
        }
    }

    @Override
    public User createUser(UserFormDTO form) throws LoginSampleException {
        try{
            Connection con = DBManager.getConnection();
            String SQL = "INSERT INTO users (email, password, username, first_name, last_name, birthday) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, form.getEmail());
            ps.setString(2, form.getPassword());
            ps.setString(3, form.getUsername());
            ps.setString(4, form.getFirstName());
            ps.setString(5, form.getLastName());
            LocalDate birthday = form.getBirthday();
            if (birthday != null) {
                ps.setDate(6, Date.valueOf(birthday));
            } else {
                ps.setNull(6, Types.DATE);
            }
            ps.executeUpdate();
            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            long id = ids.getLong(1);
            User user = new User(form.getEmail(), form.getPassword(), form.getUsername(), form.getFirstName(), form.getLastName(), form.getBirthday());
            user.setId(id);
            return user;
        } catch(SQLException ex){
            throw new LoginSampleException(ex.getMessage());
        }
    }

    @Override
    public void deleteUser(long userId) throws LoginSampleException {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "DELETE FROM users WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setLong(1, userId);
            ps.executeUpdate();
        } catch(SQLException ex){
            throw new LoginSampleException(ex.getMessage());
        }
    }

    @Override
    public Wishlist createWishlist(WishlistFormDTO form, long userID) {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "INSERT INTO wishlist (name, user_id)\n" +
                    "VALUES  (?, ?);";
            PreparedStatement ps = con.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, form.getName());
            ps.setLong(2, userID);
            ps.executeUpdate();
            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt(1);
            Wishlist wishlist = new Wishlist(form.getName(), form.getUser_id());
            wishlist.setId(id);
            return wishlist;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteWishlist(long id) {
        try (Connection conn = DBManager.getConnection()){
            String SQL = "DELETE FROM wishlist WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(SQL)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<WishFormDTO> getWishlistByID(int wishlist_id) {
                try{
                    Connection con = DBManager.getConnection();
                    String SQL = "SELECT wish.id, wishlist.name, wish.name, link, price, qty, description " +
                            "FROM wishlist LEFT JOIN wish ON wishlist.id = wish.wishlist_id " +
                            "WHERE wishlist_id = ?;";
                    PreparedStatement ps = con.prepareStatement(SQL);
                    ps.setLong(1, wishlist_id);
                    ResultSet rs = ps.executeQuery();
                    List<WishFormDTO> wishes = new ArrayList<>();
                    while (rs.next()){
                        long wishID = rs.getLong("wish.id");
                        String wishlistName = rs.getString("wishlist.name");
                        String wishName = rs.getString("wish.name");
                        String link = rs.getString("link");
                        double price = rs.getDouble("price");
                        String formattedPrice = String.format("%.2f", price).replace(".", ",");
                        int qty = rs.getInt("qty");
                        String description = rs.getString("description");
                        WishFormDTO wish = new WishFormDTO(wishlistName, wishID, wishName, link, formattedPrice, qty, description);
                        wishes.add(wish);
                    }
                    return wishes;
                } catch(SQLException ex){
                    return null;
                }
            }

    @Override
    public WishFormDTO createWish(WishFormDTO form, long wishlistID) {
        try{
            Connection con = DBManager.getConnection();
            String SQL = "INSERT INTO wish (name, link, price, qty, description, wishlist_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            // setting the values of the prepared statement
            ps.setString(1, form.getName());
            ps.setString(2, form.getLink());
            ps.setDouble(3, Double.parseDouble(form.getPrice().replace(",", ".")));
            ps.setInt(4, form.getQty());
            ps.setString(5, form.getDescription());
            ps.setLong(6, wishlistID);

            ps.executeUpdate();
            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt(1);

            WishFormDTO wish = new WishFormDTO();
            // Setting the attributes of the wish object
            wish.setName(form.getName());
            wish.setLink(form.getLink());
            wish.setPrice(form.getPrice());
            wish.setQty(form.getQty());
            wish.setDescription(form.getDescription());
            wish.setId(id);

            return wish;
        } catch(SQLException ex){
            return null;
        }
    }

    @Override
    public String getUsername(long userID) {
        try{
            Connection con = DBManager.getConnection();
            String SQL = "SELECT username FROM users WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setLong(1, userID);
            ResultSet rs = ps.executeQuery();
            String username = "";
            while (rs.next()){
                username = rs.getString("username");
            }
            return username;
        } catch(SQLException ex){
            return null;
        }
    }

    @Override
    public List<WishlistWishCountDTO> getWishlistAndWishCountByUserID(long userID) {
        try{
            Connection con = DBManager.getConnection();
            String SQL = "SELECT wishlist.name, wishlist.id, COUNT(wish.id) AS wish_count FROM wishlist LEFT JOIN wish ON wishlist.id = wish.wishlist_id WHERE wishlist.user_id = ? GROUP BY wishlist.id;";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setLong(1, userID);
            ResultSet rs = ps.executeQuery();
            List<WishlistWishCountDTO> wishlistWishCountDTOList = new ArrayList<>();
            while (rs.next()){
                String wishlistName = rs.getString("name");
                int wishCount = rs.getInt("wish_count");
                int id = rs.getInt("id");
                WishlistWishCountDTO wishlistWishCountDTO = new WishlistWishCountDTO(wishlistName, wishCount, id);
                wishlistWishCountDTOList.add(wishlistWishCountDTO);
            }
            return wishlistWishCountDTOList;
        } catch(SQLException ex){
          return null;
        }
    }
    @Override
    public List<WishFormDTO> editWishlist(long id, WishFormDTO form) {
        List<WishFormDTO> updatedWishlist = new ArrayList<>();
        try {
            Connection con = DBManager.getConnection();
            String SQL = "UPDATE wish SET listName=?, link=?, price=?, qty=?, description=? WHERE id=?;";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, form.getName());
            ps.setString(2, form.getLink());
            ps.setString(3, form.getPrice());
            ps.setInt(4, form.getQty());
            ps.setString(5, form.getDescription());
            ps.setLong(6, id);
            int rows = ps.executeUpdate();
            if (rows == 1) {
                form.setId(id);
                updatedWishlist.add(form);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updatedWishlist;
    }

    @Override
    public void editUser(UserFormDTO form) throws LoginSampleException {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "UPDATE users SET email=?, password=?, username=?, first_name=?, last_name=?, birthday=? WHERE id=?;";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, form.getEmail());
            ps.setString(2, form.getPassword());
            ps.setString(3, form.getUsername());
            ps.setString(4, form.getFirstName());
            ps.setString(5, form.getLastName());
            LocalDate birthday = form.getBirthday();
            if (birthday != null) {
                ps.setDate(6, Date.valueOf(birthday));
            } else {
                ps.setNull(6, Types.DATE);
            }
            ps.setLong(7, form.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new LoginSampleException(e.getMessage());
        }
    }



    @Override
    public void deleteWish(long id) {
        try (Connection conn = DBManager.getConnection()){
            String SQL = "DELETE FROM wish WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(SQL)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
