package dk.kea.Wishlist.dto;

public class WishlistWishCountDTO {
    private String name;
    private int wishCount;

    public WishlistWishCountDTO(String name, int wishCount) {
        this.name = name;
        this.wishCount = wishCount;
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
}
