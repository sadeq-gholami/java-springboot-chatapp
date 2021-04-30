package com.kth.chatapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kth.chatapp.model.User;
import com.kth.chatapp.repos.UserRepository;
@Controller
public class UserController {
	@Autowired
    private UserRepository userRepo;
     
    @GetMapping("/home")
    public String viewHomePage() {
        return "index";
    }
    
    @GetMapping("/register")
    public String showSignup(Model model) {
    	model.addAttribute("user",new User());
    	return "signup_form";
    }
    @PostMapping("/process_register")
    public String processRegister(User user, RedirectAttributes redirAttrs) {
    	try {
	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        String encodedPassword = passwordEncoder.encode(user.getPassword());
	        user.setPassword(encodedPassword);
	         
	        userRepo.save(user);
	         
	        return "success";
    	}catch(Exception e) {
    		redirAttrs.addFlashAttribute("error", "email is taken try again!");
			return "redirect:/register";
    	}
    }
   
}
