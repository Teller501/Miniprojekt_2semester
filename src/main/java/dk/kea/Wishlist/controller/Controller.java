package dk.kea.Wishlist.controller;

import dk.kea.Wishlist.dto.UserFormDTO;
import dk.kea.Wishlist.dto.WishlistWishCountDTO;
import dk.kea.Wishlist.model.User;
import dk.kea.Wishlist.repository.IRepository;
import dk.kea.Wishlist.utility.LoginSampleException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    IRepository repository;

    public Controller(ApplicationContext context, @Value("${wishlist.repository.impl}") String impl){
        repository = (IRepository) context.getBean(impl);
    }

    @GetMapping({"/",""})
    public String index(){
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
    public String deleteUser(HttpServletRequest request, @RequestParam("userId") long userId, Model model) throws LoginSampleException {
        repository.deleteUser(userId);
        request.getSession().invalidate();
        model.addAttribute("userId", userId);
        return "redirect:/";
    }


    @GetMapping("main")
    public String main(){
        return "main";
    }

    @GetMapping("create")
    public String create(){
        return "create";
    }

    @GetMapping("wishlist")
    public String showWishlist(HttpServletRequest request, Model model){
        long userID = (long) request.getSession().getAttribute("userID");

        List<WishlistWishCountDTO> wishlist = repository.getWishlistAndWishCountByUserID(userID);

        model.addAttribute("wishlist", wishlist);

        return "wishlist";
    }

    @PostMapping ("DeleteWishlist")
    public String deleteWishlist (HttpServletRequest request, @RequestParam("id") long id) {
        repository.deleteWishlist(id);
        request.getSession();
        return "redirect:/wishlist";
    }
}
