package com.lidor.coupon.dal;

import com.lidor.coupon.dto.CouponData;
import com.lidor.coupon.entities.Coupon;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ICouponDal extends CrudRepository<Coupon, Long> {

    @Query("SELECT NEW com.lidor.coupon.dto.CouponData(cp.id, cp.name,cp.price, cp.description, cp.startDate, cp.endDate,cp.imgURL, cp.category.name, cp.company.name) FROM Coupon cp Join Company cm ON cp.company.id = cm.id JOIN Category ct On cp.category.id = ct.id ")
    List<CouponData> findAll(Pageable pageable);

    @Query("SELECT NEW com.lidor.coupon.dto.CouponData(cp.id, cp.name,cp.price, cp.description, cp.startDate, cp.endDate,cp.imgURL, cp.category.name, cp.company.name) FROM Coupon cp Join Company cm ON cp.company.id = cm.id JOIN Category ct On cp.category.id = ct.id WHERE cp.id= :couponId")
    CouponData getCoupon(@Param("couponId") long couponId);

    @Query("SELECT NEW com.lidor.coupon.dto.CouponData(cp.id, cp.name,cp.price, cp.description, cp.startDate, cp.endDate,cp.imgURL, cp.category.name, cp.company.name) FROM Coupon cp Join Company cm ON cp.company.id = cm.id JOIN Category ct On cp.category.id = ct.id WHERE cp.company.id= :companyId")
    List<CouponData> findAllByCompanyId(@Param("companyId") long companyId, Pageable pageable);

    @Query("SELECT NEW com.lidor.coupon.dto.CouponData(cp.id, cp.name,cp.price, cp.description, cp.startDate, cp.endDate,cp.imgURL, cp.category.name, cp.company.name) FROM Coupon cp Join Company cm ON cp.company.id = cm.id JOIN Category ct On cp.category.id = ct.id WHERE cp.category.id= :categoryId")
    List<CouponData> findAllByCategoryId(@Param("categoryId") long categoryId, Pageable pageable);

    @Query("SELECT NEW com.lidor.coupon.dto.CouponData(cp.id, cp.name,cp.price, cp.description, cp.startDate, cp.endDate,cp.imgURL, cp.category.name, cp.company.name) FROM Coupon cp Join Company cm ON cp.company.id = cm.id JOIN Category ct On cp.category.id = ct.id WHERE cp.price between :minPrice and :maxPrice")
    List<CouponData> findAlByPriceRange(@Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice, Pageable pageable);

    @Query("delete from Coupon c where c.endDate = :date")
    void deleteAllExpiredCoupons(@Param("date")Date date);

    boolean existsByName(String name);

}
