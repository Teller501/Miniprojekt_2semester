package dk.kea.Wishlist.repository;

import dk.kea.Wishlist.dto.UserFormDTO;
import dk.kea.Wishlist.dto.WishlistWishCountDTO;

public interface IRepository {
    void addUser(UserFormDTO form);

    WishlistWishCountDTO getWishlistAndWishCountByUsername(String username);
}
