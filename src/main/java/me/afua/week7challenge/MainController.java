package me.afua.week7challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Controller
public class MainController {
    @Autowired
    NewsService theWire;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AppRoleRepository roleRepo;


    @RequestMapping("/")
    public String showIndex(Model model, Authentication auth)
    {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Top General Stories"+theWire.getStories("top-headlines","general").toString());
        model.addAttribute("topstories",theWire.getStories("top-headlines","general"));

        if(auth!=null)
        {
            model.addAttribute("myNews",theWire.getUserNewsDisplay(auth));
            System.out.println(theWire.getUserNewsDisplay(auth).toString());
        }
        return "newindex";
    }

    @RequestMapping("/signup")
    public String signup(Model model)
    {
        model.addAttribute("thisUser",new AppUser());
        return "signup";
    }

    @RequestMapping("/myprofile")
    public String myProfile(Model model)
    {
        AppUser theUser = appUserRepository.findById(new Long (1)).get();
        model.addAttribute("thisUser",theUser);
        return "userprofile";
    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute("thisUser") AppUser thisUser, BindingResult result)
    {
        ArrayList<String> userCategories = new ArrayList<>();
        userCategories.add("business");
        userCategories.add("health");
        thisUser.setCategories(userCategories);
        appUserRepository.save(thisUser);
        thisUser.addRole(roleRepo.findAppRoleByRoleName("USER"));
        return "redirect:/";
    }



}
