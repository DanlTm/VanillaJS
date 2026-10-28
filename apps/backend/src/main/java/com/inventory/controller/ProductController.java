package com.inventory.controller;

import com.inventory.model.Product;
import com.inventory.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for the Products page using the MVC pattern.
 */
@Controller
@RequestMapping("/products")
public class ProductController {

    // Service dependency that provides product data
    private final ProductService productService;

    // Constructor injection for ProductService
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/products";
    }

    /**
     * HTTP GET /products
     * This method displays the products page.
     * It sends the list of products to the view.
     */
    @GetMapping
    public String products(Model model) {
        // Send list of all products to the HTML view
        model.addAttribute("products", productService.findAll());

        // Return the view name (src/main/resources/templates/products.html)
        return "products";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    @GetMapping("/{id}/edit")
    // Opens the form with the product data.
    public String editProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "product-form";
    }

    @PostMapping
    public String createProduct(
            @Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product-form";
        }

        productService.save(product);
        return "redirect:/products";
    }

    @PostMapping("/{id}")
    // Saves the new product data.
    public String updateProduct(
            @PathVariable Long id,
            @Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            product.setId(id);
            return "product-form";
        }

        productService.update(id, product);
        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }
}
