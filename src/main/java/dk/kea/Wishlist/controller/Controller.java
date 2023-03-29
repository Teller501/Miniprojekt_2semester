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
    public String indexPost(HttpServletRequest request, @ModelAttribute UserFormDTO form) throws LoginSampleException {
        User user = repository.login(form.getEmail(), form.getPassword());

        if (user != null){
            request.getSession().setAttribute("userID", user.getId());
            return "redirect:/main";
        } else {
            return "redirect:/";
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
        String username = repository.getUsername(userID);
        model.addAttribute("username", username);

        return "main";
    }

    @GetMapping("create")
    public String showCreate(){
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

        return "allWishlists";
    }

    @GetMapping("/deleteWishlist/{id}")
    public String deleteWishlist(@PathVariable("id") int id) {
        repository.deleteWishlist(id);
        System.out.println("Wishlist deleted: " + id);
        return "redirect:/allWishlists";
    }


    @GetMapping("/editWishlist/{id}")
    public String showEditWishlist(@PathVariable("id") int id, Model model){
        List<WishFormDTO> wish = repository.getWishlistByID(id);
        model.addAttribute("wish", wish);
        model.addAttribute("id", id);
        return "editWishlist";
    }

    @GetMapping("/createWish/{id}")
    public String showCreateWish(@PathVariable("id") int id, Model model) {
        model.addAttribute("wishlistId", id); // Add the wishlist id as a model attribute
        model.addAttribute("wish", new WishFormDTO());
        return "createWish";
    }

    @PostMapping("/createWish/{id}")
    public String createWish(@PathVariable("id") int id, @ModelAttribute WishFormDTO form) {
        WishFormDTO wish = repository.createWish(form, id);
        System.out.println(wish);
        return "redirect:/allWishlists";
    }




}
