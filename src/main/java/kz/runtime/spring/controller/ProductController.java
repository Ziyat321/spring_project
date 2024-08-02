package kz.runtime.spring.controller;

import kz.runtime.spring.entity.*;
import kz.runtime.spring.repository.*;
import kz.runtime.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private UserService userService;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CartRepository cartRepository;

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

    @PostMapping(path = "/create_product")
    public String saveProduct(@RequestParam(name = "categoryId", required = false) Long categoryId,
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
        return "redirect:/products";
    }

    @GetMapping(path = "/products/change")
    public String changeProduct(@RequestParam(name = "productId", required = false) Long productId,
                                Model model) {

        Product product = productRepository.findById(productId).orElseThrow();
        List<Category> categories = categoryRepository.findAll();


        model.addAttribute("product", product);
        model.addAttribute("categories", categories);

        return "product_change_product_page";
    }

    @PostMapping(path = "/products/change")
    public String saveChangedProduct(Model model,
                                     @RequestParam(name = "productId", required = false) Long productId,
                                     @RequestParam(name = "categoryId", required = false) Long categoryId,
                                     @RequestParam(name = "name", required = false) String name,
                                     @RequestParam(name = "price", required = false) Integer price) {


        Product product = productRepository.findById(productId).orElseThrow();
        Long oldCategoryId = product.getCategory().getId();
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId).orElseThrow();
            product.setCategory(category);
        }
        if (name != null && !name.isEmpty()) {
            product.setName(name);
        }
        if (price != null) {
            product.setPrice(price);
        }
        productRepository.save(product);


        return "redirect:/products/change_characteristics?productId="
                + product.getId()
                + "&oldCategoryId=" + oldCategoryId;
    }

    @GetMapping(path = "/products/change_characteristics")
    public String changeCharacteristics(
            Model model,
            @RequestParam(name = "productId", required = false) Long productId,
            @RequestParam(name = "oldCategoryId", required = false) Long oldCategoryId
    ) {
        Product product = productRepository.findById(productId).orElseThrow();
        Category category = product.getCategory();
        Long categoryId = category.getId();
        Map<Characteristic, CharacteristicDescription> map = new HashMap<>();

        if (categoryId.equals(oldCategoryId)) {
            List<Characteristic> characteristics = product.getCategory().getCharacteristics();
            List<CharacteristicDescription> characteristicDescriptions = product.getCharacteristicDescriptions();


            for (Characteristic characteristic : characteristics) {
                boolean exists = false;
                for (CharacteristicDescription characteristicDescription : characteristicDescriptions) {
                    if (characteristicDescription.getCharacteristic().getName().equals(characteristic.getName())) {
                        map.put(characteristic, characteristicDescription);
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    CharacteristicDescription cd = new CharacteristicDescription();
                    cd.setDescription("");
                    map.put(characteristic, cd);
                }
            }
        } else {
            List<Characteristic> characteristics = category.getCharacteristics();
            for (Characteristic characteristic : characteristics) {
                map.put(characteristic, new CharacteristicDescription());
            }
            List<CharacteristicDescription> characteristicDescriptions = product.getCharacteristicDescriptions();
            characteristicDescriptionRepository.deleteAll(characteristicDescriptions);
        }


        model.addAttribute("product", product);
        model.addAttribute("map", map);

        return "characteristics_describe";
    }

    @PostMapping(path = "/products/change_characteristics")
    public String saveChangedCharacteristics(
            Model model,
            @RequestParam(name = "productId", required = false) Long productId,
            @RequestParam(name = "description", required = false) String... description
    ) {
        Product product = productRepository.findById(productId).orElseThrow();
        List<CharacteristicDescription> characteristicDescriptions = product.getCharacteristicDescriptions();
        for (int i = 0; i < description.length; i++) {
            if (description[i] != null && !description[i].isEmpty()) {
                boolean exists = false;
                CharacteristicDescription characteristicDescription = new CharacteristicDescription();
                if (!characteristicDescriptions.isEmpty()) {
                    for (CharacteristicDescription characteristicDescription1 : characteristicDescriptions) {
                        if (characteristicDescription1.getCharacteristic().getName().equals(
                                product.getCategory().getCharacteristics().get(i).getName()
                        )) {
                            exists = true;
                            characteristicDescription = characteristicDescription1;
                            break;
                        }
                    }
                }
                if (!exists) {
                    characteristicDescription.setDescription(description[i]);
                    characteristicDescription.setProduct(product);
                    characteristicDescription.setCharacteristic(product.getCategory().getCharacteristics().get(i));
                    characteristicDescriptionRepository.save(characteristicDescription);
                } else {
                    characteristicDescription.setDescription(description[i]);
                    characteristicDescriptionRepository.save(characteristicDescription);
                }

            }
        }
        return "redirect:/products";
    }

    @GetMapping(path = "/view_product")
    public String viewProduct(Model model,
                              @RequestParam(name = "productId", required = true) Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        User user = userService.getCurrentUser();
        List<Review> reviews = product.getReviews();
        double avgRating = 0;
        for (Review review : reviews) {
            avgRating += review.getRating();
        }
        avgRating = avgRating / reviews.size();

        boolean userWroteReview;
        Review review = reviewRepository.findByUserAndProduct(user, product).orElse(null);
        userWroteReview = review != null;

        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("avgRating", avgRating);
        model.addAttribute("addingReviewAvailable", !userWroteReview);
        return "product_view";
    }

    @PostMapping(path = "/save_commentary")
    public String saveCommentary(Model model,
                                 @RequestParam(name = "productId", required = true) Long productId,
                                 @RequestParam(name = "rating", required = true) Short rating,
                                 @RequestParam(name = "commentary", required = true) String commentary) {
        User user = userService.getCurrentUser();
        Review review = new Review();
        review.setUser(user);
        review.setProduct(productRepository.findById(productId).orElseThrow());
        review.setRating(rating);
        review.setCommentary(commentary);
        review.setPublished(true);
        review.setReviewDate(LocalDateTime.now());
        reviewRepository.save(review);
        return "redirect:/products";
    }

    @GetMapping(path = "/products/add_to_cart")
    public String addToCart(@RequestParam(name = "productId", required = true) Long productId) {
        User user = userService.getCurrentUser();
        Product product = productRepository.findById(productId).orElseThrow();
        Cart cart = cartRepository.findByUserAndProduct(user, product).orElse(new Cart());
        cart.setUser(user);
        cart.setProduct(product);
        if (cart.getAmount() == null) {
            cart.setAmount(1);
        } else {
            cart.setAmount(cart.getAmount() + 1);
        }
        cartRepository.save(cart);
        return "redirect:/products";
    }

    @GetMapping(path = "/products/cart")
    public String cart(Model model) {
        User user = userService.getCurrentUser();
        List<Cart> carts = user.getCarts();
        model.addAttribute("carts", carts);
        return "cart_page";
    }


    @GetMapping(path = "products/cart/decrement")
    public String decrementAmountInCart(@RequestParam(name="decrement", required = true)Integer decrement,
                                        @RequestParam(name = "cartId", required = true) Long cartId){
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        if (cart.getAmount() > 0) {
            cart.setAmount(cart.getAmount() - 1);
        }
        cartRepository.save(cart);
        return "redirect:/products/cart";
    }

    @GetMapping(path="/products/cart/increment")
    public String incrementAmountInCart(@RequestParam(name="increment", required = true)Integer increment,
                                        @RequestParam(name = "cartId", required = true) Long cartId){
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        cart.setAmount(cart.getAmount() + 1);
        cartRepository.save(cart);
        return "redirect:/products/cart";
    }

    @GetMapping(path = "/products/cart/delete")
    public String deleteProductInCart(@RequestParam(name="delete", required = true)Integer delete,
                                      @RequestParam(name = "cartId", required = true) Long cartId){
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        cartRepository.delete(cart);
        return "redirect:/products/cart";
    }

    @GetMapping(path = "/products/update_cart")
    public String updateCart(){
        User user = userService.getCurrentUser();
        List<Cart> carts = user.getCarts();
        for (Cart cart : carts) {
            if(cart.getAmount() == 0){
                cartRepository.delete(cart);
            }
        }
        return "redirect:/products";
    }
}
