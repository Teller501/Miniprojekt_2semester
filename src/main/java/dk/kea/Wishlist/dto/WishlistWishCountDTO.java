package dk.kea.Wishlist.dto;

public class WishlistWishCountDTO {
    private String name;
    private int wishCount;
    private int id;

    public WishlistWishCountDTO(String name, int wishCount, int id) {
        this.name = name;
        this.wishCount = wishCount;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWishCount() {
        return wishCount;
    }

    public void setWishCount(int wishCount) {
        this.wishCount = wishCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
