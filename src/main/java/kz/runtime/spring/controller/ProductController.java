package kz.runtime.spring.controller;

import kz.runtime.spring.entity.Category;
import kz.runtime.spring.entity.Characteristic;
import kz.runtime.spring.entity.CharacteristicDescription;
import kz.runtime.spring.entity.Product;
import kz.runtime.spring.repository.CategoryRepository;
import kz.runtime.spring.repository.CharacteristicDescriptionRepository;
import kz.runtime.spring.repository.CharacteristicRepository;
import kz.runtime.spring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CharacteristicRepository characteristicRepository;
    @Autowired
    private CharacteristicDescriptionRepository characteristicDescriptionRepository;

    @GetMapping(path = "/products")
    public String allProducts(Model model,
                              @RequestParam(name = "categoryId", required = false) Long category) {
        if (category != null) {
            Category category1 = categoryRepository.findById(category).orElseThrow();
            List<Product> products = category1.getProducts();
            model.addAttribute("products", products);
        } else {
            List<Product> products = productRepository.findAll();
            model.addAttribute("products", products);
        }
        return "product_resource_1_page";
    }

    @GetMapping(path = "/category_choice")
    public String chooseCategory(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "category_choice";
    }

    @GetMapping(path = "/create_product")
    public String createProduct() {
        return "product_creation";
    }

    @PostMapping(path = "/create_product")
    public String fillProduct(
            Model model,
            @RequestParam(name = "categoryId", required = false) Long categoryId
    ) {
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId).orElseThrow();
            model.addAttribute("category", category);
            model.addAttribute("characteristics", category.getCharacteristics());
        }
        return "product_creation";
    }

    @PostMapping(path = "/products")
    public String saveProduct(Model model,
                              @RequestParam(name = "categoryId", required = false) Long categoryId,
                              @RequestParam(name = "product", required = false) String product,
                              @RequestParam(name = "price", required = false) Integer price,
                              @RequestParam(name = "description", required = false) String... description) {
        if (categoryId != null && product != null && price != null && description != null) {
            Category category = categoryRepository.findById(categoryId).orElseThrow();

            Product newProduct = new Product();
            newProduct.setName(product);
            newProduct.setPrice(price);
            newProduct.setVisibility(true);
            newProduct.setCategory(category);
            productRepository.save(newProduct);


            for (int i = 0; i < description.length; i++) {
                Characteristic characteristic = category.getCharacteristics().get(i);
                CharacteristicDescription characteristicDescription = new CharacteristicDescription();
                characteristicDescription.setDescription(description[i]);
                characteristicDescription.setProduct(newProduct);
                characteristicDescription.setCharacteristic(characteristic);
                characteristicDescriptionRepository.save(characteristicDescription);
            }
        }
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "product_resource_1_page";
    }

}
