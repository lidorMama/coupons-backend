package com.lidor.coupon.dal;

import com.lidor.coupon.entities.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryDal extends CrudRepository<Category,Long> {

    boolean existsByName(String name);

}
