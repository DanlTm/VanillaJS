package com.inventario.controller;

import com.inventario.model.Category;
import com.inventario.service.CategoryService;
import com.inventario.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "categories/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        model.addAttribute("products", productService.findAll(null, id));
        return "categories/detail";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("title", "New Category");
        return "categories/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        model.addAttribute("title", "Edit Category");
        return "categories/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("category") Category category, BindingResult result,
                       Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("title", category.getId() == null ? "New Category" : "Edit Category");
            return "categories/form";
        }
        boolean isNew = category.getId() == null;
        categoryService.save(category);
        flash.addFlashAttribute("success", isNew ? "Category created successfully" : "Category updated successfully");
        return "redirect:/categories";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes flash) {
        try {
            categoryService.delete(id);
            flash.addFlashAttribute("success", "Category deleted successfully");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Cannot delete category with associated products");
        }
        return "redirect:/categories";
    }
}
