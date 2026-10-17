package com.inventario.controller;

import com.inventario.model.Product;
import com.inventario.service.CategoryService;
import com.inventario.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Products Controller
 *  GET  /products             -> Interactive Product Catalog for shoppers
 *  GET  /products/{id}        -> Product Detail
 *  GET  /products/new         -> Create Form
 *  GET  /products/edit/{id}   -> Edit Form
 *  POST /products/save        -> Save / Update
 *  POST /products/delete/{id} -> Delete
 */
@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    /** Interactive product catalog with real-time 0ms search for shoppers */
    @GetMapping
    public String catalog(Model model) {
        model.addAttribute("products", productService.findAll(null, null));
        model.addAttribute("categories", categoryService.findAll());
        return "catalog";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "products/detail";
    }

    @GetMapping("/new")
    public String create(Model model) {
        return showForm(new Product(), null, "New Product", model);
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Product p = productService.findById(id);
        Long categoryId = p.getCategory() != null ? p.getCategory().getId() : null;
        return showForm(p, categoryId, "Edit Product", model);
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("product") Product product, BindingResult result,
                       @RequestParam(required = false) Long categoryId,
                       Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            String title = product.getId() == null ? "New Product" : "Edit Product";
            return showForm(product, categoryId, title, model);
        }
        boolean isNew = product.getId() == null;
        Product saved = productService.save(product, categoryId);
        flash.addFlashAttribute("success", isNew ? "Product created successfully" : "Product updated successfully");
        return "redirect:/products/" + saved.getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes flash) {
        productService.delete(id);
        flash.addFlashAttribute("success", "Product deleted successfully");
        return "redirect:/products";
    }

    private String showForm(Product product, Long categoryId, String title, Model model) {
        model.addAttribute("product", product);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("title", title);
        return "products/form";
    }
}
