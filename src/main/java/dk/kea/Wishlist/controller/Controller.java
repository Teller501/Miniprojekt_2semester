package dk.kea.Wishlist.controller;

import dk.kea.Wishlist.dto.UserFormDTO;
import dk.kea.Wishlist.dto.WishFormDTO;
import dk.kea.Wishlist.dto.WishlistFormDTO;
import dk.kea.Wishlist.dto.WishlistWishCountDTO;
import dk.kea.Wishlist.model.User;
import dk.kea.Wishlist.model.Wishlist;
import dk.kea.Wishlist.repository.IRepository;
import dk.kea.Wishlist.utility.LoginSampleException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Controller
public class Controller {

    IRepository repository;

    public Controller(ApplicationContext context, @Value("${wishlist.repository.impl}") String impl){
        repository = (IRepository) context.getBean(impl);
    }

    @GetMapping({"/",""})
    public String index(HttpServletRequest request){
        //Checking whether the user is logged in, if so, redirect to main page
        if(request.getSession().getAttribute("userID") != null){
            return "redirect:/main";
        }
        return "index";
    }

    @PostMapping({"/",""})
    public String indexPost(HttpServletRequest request, @ModelAttribute UserFormDTO form, Model model) {
        // Trying to login
        try {
            User user = repository.login(form.getEmail(), form.getPassword());

            request.getSession().setAttribute("userID", user.getId());
            return "redirect:/main";

        // if login fails, redirect to index with error message
        } catch (LoginSampleException e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "index";
        }
    }



    @GetMapping("register")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new UserFormDTO());
        return "register";
    }


    @PostMapping("register")
    public String register(HttpServletRequest request, @ModelAttribute UserFormDTO form) throws LoginSampleException {
        User user = repository.createUser(form);
        System.out.println(user);

        if (user != null){
            request.getSession().setAttribute("userID", user.getId());
            return "redirect:/main";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("deleteUser")
    public String deleteUser(HttpServletRequest request, Model model) throws LoginSampleException {
        long userId = (long) request.getSession().getAttribute("userID");
        repository.deleteUser(userId);
        request.getSession().invalidate();
        model.addAttribute("userId", userId);
        return "redirect:/";
    }


    @GetMapping("main")
    public String main(Model model, HttpServletRequest request){
        long userID = (Long) request.getSession().getAttribute("userID");

        // Redirects to login site if user is not logged in
        if (userID == 0){
            return "redirect:/";
        }

        // retrieves username from database and adds it to the model
        model.addAttribute("username", repository.getUsername(userID));
        model.addAttribute("userID", userID);

        return "main";
    }

    @GetMapping("create")
    public String showCreate(HttpServletRequest request, Model model){
        long userID = (long) request.getSession().getAttribute("userID");
        model.addAttribute("username", repository.getUsername(userID));
        return "create";
    }

    @PostMapping("create")
    public String create(HttpServletRequest request, @ModelAttribute WishlistFormDTO form) {
        long userID = (long) request.getSession().getAttribute("userID");
        Wishlist wishlist = repository.createWishlist(form, userID);
        System.out.println(wishlist);
        return "redirect:/allWishlists";
    }

    @GetMapping("allWishlists")
    public String showAllWishlist(HttpServletRequest request, Model model){
        long userID = (long) request.getSession().getAttribute("userID");

        List<WishlistWishCountDTO> wishlist = repository.getWishlistAndWishCountByUserID(userID);

        model.addAttribute("wishlist", wishlist);
        model.addAttribute("username", repository.getUsername(userID));

        return "allWishlists";
    }

    @GetMapping("/deleteWishlist/{id}")
    public String deleteWishlist(@PathVariable("id") int id) {
        repository.deleteWishlist(id);
        System.out.println("Wishlist deleted: " + id);
        return "redirect:/allWishlists";
    }


    @GetMapping("/editWishlist/{id}")
    public String showEditWishlist(HttpServletRequest request, @PathVariable("id") int id, Model model) {
        // Check if the wishlist belongs to the user
        long userID = (long) request.getSession().getAttribute("userID");
        long wishlistOwnerId = repository.getWishlistOwnerId(id);
        if (wishlistOwnerId != userID) {
            return "error";
        }

        List<WishFormDTO> wish = repository.getWishlistByID(id);
        model.addAttribute("wish", wish);
        model.addAttribute("id", id);

        model.addAttribute("username", repository.getUsername(userID));

        return "editWishlist";
    }

    @GetMapping("/createWish/{id}")
    public String showCreateWish(HttpServletRequest request, @PathVariable("id") int id, Model model) {
        model.addAttribute("wishlistId", id); // Add the wishlist id as a model attribute
        model.addAttribute("wish", new WishFormDTO());
        // Retrieves username from database and adds it to the model:
        long userID = (long) request.getSession().getAttribute("userID");
        model.addAttribute("username", repository.getUsername(userID));
        return "createWish";
    }

    @PostMapping("/createWish/{id}")
    public String createWish(@PathVariable("id") int id, @ModelAttribute WishFormDTO form, Model model) {
        try {
            WishFormDTO wish = repository.createWish(form, id);
            System.out.println(wish);
            return "redirect:/editWishlist/" + id;
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            model.addAttribute("id", id);
            model.addAttribute("form", form);
            return "createWish";
        }
    }


    @GetMapping("/deleteWish/{id}")
    public String deleteWish(@PathVariable("id") long id) {
        repository.deleteWish(id);
        System.out.println("Wish deleted: " + id);
        return "redirect:/allWishlists";
    }

    @GetMapping("/editUser")
    public String showEditUserForm(HttpServletRequest request, Model model) {
        long userID = (long) request.getSession().getAttribute("userID");
        model.addAttribute("user", repository.getUserByID(userID));
        model.addAttribute("username", repository.getUsername(userID));
        model.addAttribute("userID", userID);
        return "editUser";
    }

    @PostMapping("/editUser")
    public String editUser(HttpServletRequest request, @ModelAttribute UserFormDTO form) {
        long userID = (long) request.getSession().getAttribute("userID");
        form.setId(userID);
        repository.editUser(form);
        return "redirect:/main";
    }



    @GetMapping("/editWish/{id}")
    public String showEditWish(HttpServletRequest request, @PathVariable("id") int id, Model model) {
        long userID = (long) request.getSession().getAttribute("userID");
        model.addAttribute("username", repository.getUsername(userID));
        model.addAttribute("wish", repository.getWishByID(id));
        return "editWish";
    }

    @PostMapping("/editWish")
    public String editWish(@ModelAttribute("wish") WishFormDTO wish) {
        repository.editWish(wish);
        return "redirect:/allWishlists";
    }

    @GetMapping("/viewWishlist/{id}")
    public String viewWishlist(@PathVariable("id") int id, Model model, HttpServletRequest request) {
        // Finds wishlist by id
        List<WishFormDTO> wishlist = repository.getWishlistByID(id);
        model.addAttribute("id", id);

        // Finds username and userid
        long userID = (long) request.getSession().getAttribute("userID");
        model.addAttribute("username", repository.getUsername(userID));

        // Check if correct user
        long wishlistOwnerId = repository.getWishlistOwnerId(id);
        if (wishlistOwnerId != userID) {
            return "error";
        }

        if (wishlist == null) {
            return "error";
        }

        model.addAttribute("wishlist", wishlist);
        return "viewWishlist";
    }

    @GetMapping("shareWishlist/{id}")
    public String generateShareLink(@PathVariable("id") int id, Model model, HttpServletRequest request) {
        // Check if the wishlist belongs to the user
        long userID = (long) request.getSession().getAttribute("userID");
        long wishlistOwnerId = repository.getWishlistOwnerId(id);
        if (wishlistOwnerId != userID) {
            return "error";
        }

        // Generate UID for share link
        String uid = UUID.randomUUID().toString();

        // Construct dynamic share link
        String baseURL = String.format("%s://%s:%d", request.getScheme(), request.getServerName(), request.getServerPort());
        String shareLink = String.format("%s/viewSharedWishlist?uid=%s", baseURL, uid);

        model.addAttribute("shareLink", shareLink);
        model.addAttribute("username", repository.getUsername(userID));

        // Save share link to database
        repository.createShareLink(id, uid, shareLink);

        return "shareWishlist";
    }

    @GetMapping("viewSharedWishlist")
    public String viewSharedWishlist(@RequestParam("uid") String uid, Model model) {
        // Find wishlist by UID
        int wishlistID = repository.getWishlistIDByUID(uid);
        model.addAttribute("wishlistID", wishlistID);

        List<WishFormDTO> wishlist = repository.getWishlistByID(wishlistID);
        model.addAttribute("wishlist", wishlist);

        return "viewSharedWishlist";
    }

    @GetMapping("/searchWish")
    public String searchWish(@RequestParam("searchTerm") String searchTerm, Model model, HttpServletRequest request) {
        long userID = (long) request.getSession().getAttribute("userID");
        List<Long> wishlistIDs = repository.findWishlistIDsByUserIDAndWishName(userID, searchTerm);
        if(wishlistIDs.isEmpty()) {
            // redirect back to allwishlists page with error message
            model.addAttribute("errorMessage", "No wish found matching search term.");
            return "redirect:/allWishlists";
        } else {
            // redirect to the first wishlist ID found
            return "redirect:/viewWishlist/" + wishlistIDs.get(0);
        }
    }



}
