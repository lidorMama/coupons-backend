package com.lidor.coupon.logic;

import com.lidor.coupon.dal.ICategoryDal;
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

    public long createCategory(Category category) throws ServerException {
        categoryValidation(category);
        categoryNameExist(category);
        categoriesDal.save(category);
        long id = category.getId();
        return id;
    }

    public void updateCategory(Category category) throws ServerException {
        categoryExist(category.getId());
        categoryValidation(category);
        categoriesDal.save(category);
    }

    public void removeCategory(long categoryId) throws ServerException {
        categoryExist(categoryId);
        categoriesDal.deleteById(categoryId);
    }

    public List<Category> getAllCategories() throws ServerException {
        List<Category> categories = (List<Category>) categoriesDal.findAll();
        return categories;
    }

    public Category getCategory(long categoryId) throws ServerException {
        categoryExist(categoryId);
        Category category = categoriesDal.findById(categoryId).get();
        return category;
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


