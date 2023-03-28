package dk.kea.Wishlist.dto;

public class WishlistFormDTO {
    private long id;
    private String name;
    private int userID;

    public WishlistFormDTO (long id, String name, int userID) {
        this.id = id;
        this.name = name;
        this.userID = userID;
    }

    public WishlistFormDTO () {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
