package com.lidor.coupon.dal;

import com.lidor.coupon.dto.CategoryDto;
import com.lidor.coupon.dto.CompanyData;
import com.lidor.coupon.entities.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryDal extends CrudRepository<Category,Long> {

    @Query("SELECT NEW com.lidor.coupon.dto.CategoryDto(cg.id, cg.name) FROM Category cg WHERE cg.id= :categoryId")
    CategoryDto getCategory(@Param("categoryId") long categoryId);

    @Query("SELECT NEW com.lidor.coupon.dto.CategoryDto(c.id, c.name) FROM Category c")
    List<CategoryDto> findAllCategories();

    boolean existsByName(String name);

}
