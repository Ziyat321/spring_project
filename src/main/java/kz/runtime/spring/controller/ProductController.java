package kz.runtime.spring.controller;

import kz.runtime.spring.entity.Product;
import kz.runtime.spring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping(path="/product_controller")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(path = "/products")
    public String allProducts(Model model){
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "product_resource_1_page";
    }
}
