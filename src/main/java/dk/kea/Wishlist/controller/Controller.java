package dk.kea.Wishlist.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {

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
}
