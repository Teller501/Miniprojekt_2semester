package dk.kea.Wishlist.dto;

public class WishlistFormDTO {
    private int id;
    private String name;
    private int user_id;

    public WishlistFormDTO (int id, String name, int user_id) {
        this.id = id;
        this.name = name;
        this.user_id = user_id;
    }

    public WishlistFormDTO () {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
