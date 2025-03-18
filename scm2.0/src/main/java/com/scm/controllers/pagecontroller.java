package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class pagecontroller {
     @Autowired
    private UserService userService;
    
    @RequestMapping("/home")
    public String home(Model model){
        System.out.println("Home Page Handler");
        model.addAttribute("name", "substring technologies");
        model.addAttribute("InstaId", "suvadip7080");
        return "home";
    }

    //about route
    @RequestMapping("/about")
    public String aboutPage(){
        System.out.println("About page loading");
        return "about";
    }

    //services route
    @RequestMapping("/services")
    public String servicesPage(){
        System.out.println("services page loading");
        return "services";
    }
    //contact
    @GetMapping("/contact")
    public  String contact(){
        return "contact";
    }
    //login
    @GetMapping("/login")
    public  String login(){
        return "login";
    }
   
    //register
    @GetMapping("/register")
    public  String register(Model model){
        UserForm userForm=new UserForm();
        model.addAttribute("userForm", userForm);
        return "register";
    }
    //processing register
    @RequestMapping(value = "/do-register",method=RequestMethod.POST)
    public String processregister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult,HttpSession session){
        System.out.println(userForm);
        //fetch from data
        //userform

        //validate from data
        if(rBindingResult.hasErrors()){
            return "register";
        }
        //save to database

        //Userservice   

        //User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
         //.about(userForm.getAbout())
         //.PhoneNumber(userForm.getPhoneNumber())
         //.profilePic(
         //"https://www.learncodewithdurgesh.com/_next/image?url=%2F_next%2Fstatic%2Fmedia%2Fdurgesh_sir.35c6cb78.webp&w=1920&q=75")
         //.build();

        User user=new User();
        user.setName(userForm.getName()); 
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("https://www.google.com/imgres?q=deadpool&imgurl=https%3A%2F%2Fm.media-amazon.com%2Fimages%2FS%2Fpv-target-images%2F9905f7b495e50ee0171beaee3ade123f054304a5ac953e109736daf53cf40835._SX1080_FMjpg_.jpg&imgrefurl=https%3A%2F%2Fwww.primevideo.com%2Fdetail%2FDeadpool-4K-UHD%2F0GM6BBN1M2GHBJFH84FR0IDGOV&docid=n3Nauq0CvIPVSM&tbnid=YjRq8pNWQnTY5M&vet=12ahUKEwiHtcXGkJyJAxV7UGcHHYZXBs4QM3oECDMQAA..i&w=1080&h=608&hcb=2&ved=2ahUKEwiHtcXGkJyJAxV7UGcHHYZXBs4QM3oECDMQAA");
        User savedUser = userService.saveUser(user);

        System.out.println("user saved :");
        
        //message="Registration successful"
        Message message=Message.builder().content("Registration successful").type(MessageType.green).build();
        session.setAttribute("message",message);
        //redirect to login page
        return "redirect:/register";
    }
}
