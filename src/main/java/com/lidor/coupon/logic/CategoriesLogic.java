package com.lidor.coupon.logic;

import com.lidor.coupon.dal.ICategoryDal;
import com.lidor.coupon.dto.CategoryDto;
import com.lidor.coupon.entities.Category;
import com.lidor.coupon.enums.ErrorType;
import com.lidor.coupon.exceptions.ServerException;
import com.lidor.coupon.util.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesLogic {

    private ICategoryDal categoriesDal;

    @Autowired
    public CategoriesLogic(ICategoryDal categoriesDal) {
        this.categoriesDal = categoriesDal;
    }

    public void createCategory(Category category) throws ServerException {
        categoryValidation(category);
        categoryNameExist(category);
        try {
            categoriesDal.save(category);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to create category " + category.getName());
        }
    }

    public void updateCategory(Category category) throws ServerException {
        categoryExist(category.getId());
        categoryValidation(category);
        try {
            categoriesDal.save(category);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to update category " + category.getName());
        }
    }

    public void removeCategory(long categoryId) throws ServerException {
        categoryExist(categoryId);
        try {
            categoriesDal.deleteById(categoryId);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to remove category " + categoryId);
        }
    }

    public List<CategoryDto> getAllCategories() throws ServerException {
        try {
            List<CategoryDto> categories =categoriesDal.findAllCategories();
            return categories;
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to get all categories");
        }
    }

    public CategoryDto getCategory(long categoryId) throws ServerException {
        categoryExist(categoryId);
        try {
            CategoryDto category = categoriesDal.getCategory(categoryId);
            return category;
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to get category " + categoryId);
        }

    }

    boolean categoryExist(long categoryId) throws ServerException {
        if (ValidatorUtils.isIdValid(categoryId) == false) {
            throw new ServerException(ErrorType.CATEGORY_DOES_NOT_EXIST, " category dose not exist " + categoryId);
        }
        if (!categoriesDal.existsById(categoryId)) {
            throw new ServerException(ErrorType.CATEGORY_DOES_NOT_EXIST, " category dose not exist " + categoryId);
        }
        return true;
    }

    private void categoryValidation(Category category) throws ServerException {
        if (!ValidatorUtils.isNameLengthValid(category.getName())) {
            throw new ServerException(ErrorType.INVALID_NAME, "name length wrong" + category.getName());
        }
        categoryNameExist(category);

    }

    private void categoryNameExist(Category category) throws ServerException {
        boolean isNameExist = categoriesDal.existsByName(category.getName());
        if (isNameExist) {
            throw new ServerException(ErrorType.NAME_ALREADY_EXIST, "name already exist " + category.getName());
        }
    }
}


