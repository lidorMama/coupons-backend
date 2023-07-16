package com.lidor.coupon.controllers;


import com.lidor.coupon.dto.CategoryDto;
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
    public void createCategory(@RequestHeader String authorization, @RequestBody Category category) throws ServerException {
        categoriesLogic.createCategory(authorization, category);
    }

    @PutMapping
    public void updateCategory(@RequestHeader String authorization, @RequestBody Category category) throws ServerException {
        categoriesLogic.updateCategory(authorization, category);
    }

    @GetMapping("{categoryId}")
    public CategoryDto getCategory(@PathVariable("categoryId") long categoryId) throws ServerException {
        return categoriesLogic.getCategory(categoryId);
    }

    @DeleteMapping("{categoryId}")
    public void deleteCategory(@RequestHeader String authorization, @PathVariable("categoryId") long categoryId) throws ServerException {
        categoriesLogic.removeCategory(authorization, categoryId);
    }

    @GetMapping
    public List<CategoryDto> getAllCategories() throws ServerException {
        return categoriesLogic.getAllCategories();
    }
}
