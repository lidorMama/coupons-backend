package com.lidor.coupon.controllers;


import com.lidor.coupon.entities.Category;
import com.lidor.coupon.exceptions.ServerException;
import com.lidor.coupon.logic.CategoriesLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private CategoriesLogic categoriesLogic;

    @Autowired
    public CategoryController(CategoriesLogic categoriesLogic) {
        this.categoriesLogic = categoriesLogic;
    }

    @PostMapping
    public long createCategory(@RequestBody Category category) throws ServerException {
        return categoriesLogic.createCategory(category);
    }

    @PutMapping
    public void updateCategory(@RequestBody Category category) throws ServerException {
        categoriesLogic.updateCategory(category);
    }

    @GetMapping("{categoryId}")
    public Category getCategory(@PathVariable("categoryId") long categoryId) throws ServerException {
        return categoriesLogic.getCategory(categoryId);
    }

    @DeleteMapping("{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") long categoryId) throws ServerException {
        categoriesLogic.removeCategory(categoryId);
    }

    @GetMapping
    public List<Category> getAllCategories() throws ServerException {
        return categoriesLogic.getAllCategories();
    }
}
