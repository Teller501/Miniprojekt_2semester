package dk.kea.Wishlist.repository;

import dk.kea.Wishlist.dto.UserFormDTO;

public interface IRepository {
    public void addUser(UserFormDTO form);
}
