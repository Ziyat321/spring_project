package kz.runtime.spring.controller;


import kz.runtime.spring.entity.Category;
import kz.runtime.spring.repository.CategoryRepository;
import kz.runtime.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/data_controller")
public class DataController {

    @Autowired
    private CategoryRepository categoryRepository;

//    @Autowired
//    private UserService userService;

    @GetMapping(path = "/resource_1")
    public Object firstResource(){
        Category category = categoryRepository.findById(1L).orElseThrow();
        return category.getName();
    }

    @GetMapping(path = "/resource_2", produces = "text/plain")
    public Object secondResource(){
        List<Category> categories = categoryRepository.findAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (Category category : categories) {
            stringBuilder.append(category.getName()).append("\n");
        }
        return stringBuilder.toString();
    }
}
