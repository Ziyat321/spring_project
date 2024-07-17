package kz.runtime.spring.controller;

import kz.runtime.spring.pojo.Human;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/view_controller")
public class ViewController {

    @GetMapping(path = "/resource_1")
    public String firstResource(Model model){
        String message = "Message from ViewController.firstResource";
        Human human = new Human("Jeff", 14);
        model.addAttribute("human", human);
        return "view_resource_1_page";
    }

    @GetMapping(path = "/resource_2")
    public String secondResource(Model model){
        Human[] humans = new Human[]{
                new Human("Bill", 34),
                new Human("Jeff", 15),
                new Human("Mark", 48)
        };
        model.addAttribute("humans", humans);
        return "view_resource_2_page";
    }

}
