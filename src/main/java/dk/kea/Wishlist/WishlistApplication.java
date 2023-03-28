package dk.kea.Wishlist;

import dk.kea.Wishlist.repository.testrepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WishlistApplication {

	public static void main(String[] args) {
		testrepository test = new testrepository();
		SpringApplication.run(WishlistApplication.class, args);
		System.out.println(test.getTest());
	}

}
