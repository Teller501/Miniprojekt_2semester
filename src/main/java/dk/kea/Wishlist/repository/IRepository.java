package dk.kea.Wishlist.repository;

import dk.kea.Wishlist.dto.UserFormDTO;
import dk.kea.Wishlist.dto.WishFormDTO;
import dk.kea.Wishlist.dto.WishlistFormDTO;
import dk.kea.Wishlist.dto.WishlistWishCountDTO;
import dk.kea.Wishlist.model.User;
import dk.kea.Wishlist.model.Wish;
import dk.kea.Wishlist.model.Wishlist;
import dk.kea.Wishlist.utility.LoginSampleException;

import java.util.List;

public interface IRepository {

    User login(String email, String password) throws LoginSampleException;

    User createUser(UserFormDTO form) throws LoginSampleException;

    void deleteUser(long userId) throws LoginSampleException;

    List<WishlistWishCountDTO> getWishlistAndWishCountByUserID(long userID);

    Wishlist createWishlist(WishlistFormDTO form, long userID);

    void deleteWishlist(long id);


    List<WishFormDTO> getWishlistByID(int id);

    WishFormDTO createWish(WishFormDTO form, long wishlistID);

    String getUsername(long userID);

    public List<WishFormDTO> editWishlist(long id, WishFormDTO form);

    void deleteWish(long id);

    WishFormDTO getWishByID(long id);

    UserFormDTO getUserByID(long userID);

    void editUser(UserFormDTO form);

    long getWishlistOwnerId(int wishlist_id);

    void editWish(WishFormDTO wish);

    void createShareLink(int id, String uid, String shareLink);


    int getWishlistIDByUID(String uid);

    List<Long> findWishlistIDsByUserIDAndWishName(long userID, String searchTerm);

}
