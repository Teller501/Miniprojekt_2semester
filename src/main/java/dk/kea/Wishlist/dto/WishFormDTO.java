package dk.kea.Wishlist.dto;

public class WishFormDTO {
    private String listName;
    private long id;
    private String name;
    private String link;
    private String price;
    private int qty;
    private String description;
    private int wishlist_id;

    public WishFormDTO (String listName, long id, String name, String link, String price, int qty, String description) {
        this.listName = listName;
        this.id = id;
        this.name = name;
        this.link = link;
        this.price = price;
        this.qty = qty;
        this.description = description;
    }

    public WishFormDTO(long id, String name, String link, String price, int qty, String description) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.price = price;
        this.qty = qty;
        this.description = description;
    }

    public WishFormDTO () {
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWishlist_id() {
        return wishlist_id;
    }

    public void setWishlist_id(int wishlist_id) {
        this.wishlist_id = wishlist_id;
    }
}
