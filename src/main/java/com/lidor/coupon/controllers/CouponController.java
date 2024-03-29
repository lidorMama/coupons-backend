package com.lidor.coupon.controllers;

import com.lidor.coupon.dto.CouponData;
import com.lidor.coupon.entities.Coupon;
import com.lidor.coupon.exceptions.ServerException;
import com.lidor.coupon.logic.CouponsLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/coupon")
public class CouponController {
    private CouponsLogic couponsLogic;

    @Autowired
    public CouponController(CouponsLogic couponsLogic) {
        this.couponsLogic = couponsLogic;
    }

    @PostMapping
    public void createCoupon(@RequestHeader String authorization, @RequestBody Coupon coupon) throws ServerException {
        couponsLogic.createCoupon(authorization, coupon);
    }

    @PutMapping
    public void updateCoupon(@RequestHeader String authorization, @RequestBody Coupon coupon) throws ServerException {
        couponsLogic.updateCoupon(authorization, coupon);
    }

    @DeleteMapping("{couponId}")
    public void removeCoupon(@RequestHeader String authorization, @PathVariable("couponId") long couponId) throws ServerException {
        couponsLogic.removeCoupon(authorization, couponId);
    }

    @GetMapping("{couponId}")
    public CouponData getCoupon(@PathVariable("couponId") int couponId) throws ServerException {
        return couponsLogic.getCoupon(couponId);
    }

    //http://localhost:8080/coupon/byCompanyId?companyId=1&pageNumber=1
    @GetMapping("/byCompanyId")
    public List<CouponData> getCouponsByCustomerId(@RequestParam("companyId") long companyId, @RequestParam("pageNumber") int pageNumber) throws ServerException {
        return couponsLogic.getCouponsByCompanyID(companyId, pageNumber);
    }

    //http://localhost:8080/coupon/byCategoryId?categoryId=1&pageNumber=1
    @GetMapping("/byCategoryId")
    public List<CouponData> getCouponsByCategoryId(@RequestParam("categoryId") long categoryId, @RequestParam("pageNumber") int pageNumber) throws ServerException {
        return couponsLogic.getCouponsByCategoryID(categoryId, pageNumber);
    }

    @GetMapping("/byPriceRange")
    public List<CouponData> getCouponsByPriceRange(@RequestParam("minPrice") int minPrice, @RequestParam("maxPrice") int maxPrice, @RequestParam("pageNumber") int pageNumber) throws ServerException {
        return couponsLogic.getAllCouponsByPriceRange(minPrice, maxPrice, pageNumber);
    }

    @GetMapping
    public List<CouponData> getCoupons(@RequestParam("pageNumber") int pageNumber) throws ServerException {
        return couponsLogic.getCoupons(pageNumber);
    }
}
