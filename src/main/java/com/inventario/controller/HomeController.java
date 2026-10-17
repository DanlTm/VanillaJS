package com.inventario.controller;

import com.inventario.service.CategoryService;
import com.inventario.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Main Home Controller
 *  GET /           -> Store Presentation Showcase
 *  GET /catalog    -> Redirects to /products
 */
@Controller
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public HomeController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalProducts", productService.count());
        model.addAttribute("totalCategories", categoryService.count());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("featuredProducts", productService.findAll(null, null));
        return "index";
    }

    @GetMapping("/catalog")
    public String catalog() {
        return "redirect:/products";
    }

    @GetMapping("/products-1")
    public String productsOne() {
        return "products-one";
    }
}
