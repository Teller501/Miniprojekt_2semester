package dk.kea.Wishlist.controller;

import dk.kea.Wishlist.repository.IRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("register")
    public String register(){
        return "register";
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
    public String wishlist(Model model){
        return "wishlist";
    }
}
