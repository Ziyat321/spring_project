package kz.runtime.spring.controller;

import kz.runtime.spring.entity.Category;
import kz.runtime.spring.entity.Product;
import kz.runtime.spring.repository.CategoryRepository;
import kz.runtime.spring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(path = "/products")
    public String allProducts(Model model,
                              @RequestParam(name = "categoryId", required = false) Long category){
        if(category != null){
            Category category1 = categoryRepository.findById(category).orElseThrow();
            List<Product> products = category1.getProducts();
            model.addAttribute("products", products);
        } else {
            List<Product> products = productRepository.findAll();
            model.addAttribute("products", products);
        }
        return "product_resource_1_page";
    }

    @GetMapping(path = "/create_product")
    public String createProduct(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "product", required = false) String product
    ){
        return "product_creation";
    }

}
