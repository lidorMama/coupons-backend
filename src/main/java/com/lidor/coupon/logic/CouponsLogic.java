package com.lidor.coupon.logic;

import com.lidor.coupon.Consts.Constants;
import com.lidor.coupon.dal.ICouponDal;
import com.lidor.coupon.dto.CouponData;
import com.lidor.coupon.entities.Coupon;
import com.lidor.coupon.enums.ErrorType;
import com.lidor.coupon.exceptions.ServerException;
import com.lidor.coupon.util.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class CouponsLogic {
    private ICouponDal couponsDal;
    private CompaniesLogic companiesLogic;
    private CategoriesLogic categoriesLogic;

    public CouponsLogic() {
    }

    @Autowired
    public CouponsLogic(ICouponDal couponsDal, CompaniesLogic companiesLogic, CategoriesLogic categoriesLogic) {
        this.couponsDal = couponsDal;
        this.companiesLogic = companiesLogic;
        this.categoriesLogic = categoriesLogic;
    }

    public void createCoupon(Coupon coupon) throws ServerException {
        couponValidation(coupon);
        couponExistByName(coupon.getName());
        couponsDal.save(coupon);
    }

    public void updateCoupon(Coupon coupon) throws ServerException {
        couponValidation(coupon);
        couponsDal.save(coupon);
    }

    public void removeCoupon(long couponId) throws ServerException {
        couponExistById(couponId);
        couponsDal.deleteById(couponId);
    }

    public CouponData getCoupon(long couponId) throws ServerException {
        couponExistById(couponId);
        CouponData coupon = couponsDal.getCompany(couponId);
        return coupon;
    }

    public List<CouponData> getCoupons(int pageNumber) throws ServerException {
        Pageable pageable = PageRequest.of(pageNumber - 1, Constants.AMOUNT_OF_ITEMS_IN_PAGE);
        List<CouponData> coupons = couponsDal.findAll(pageable);
        return coupons;
    }

    public List<CouponData> getCouponsByCompanyID(long companyId, int pageNumber) throws ServerException {
        companyValid(companyId);
        Pageable pageable = PageRequest.of(pageNumber - 1, Constants.AMOUNT_OF_ITEMS_IN_PAGE);
        List<CouponData> companyCoupons = couponsDal.findAllByCompanyId(companyId, pageable);
        return companyCoupons;
    }

    public List<CouponData> getCouponsByCategoryID(long categoryId, int pageNumber) throws ServerException {
        categoryValid(categoryId);
        Pageable pageable = PageRequest.of(pageNumber - 1, Constants.AMOUNT_OF_ITEMS_IN_PAGE);
        List<CouponData> categoryCoupons = couponsDal.findAllByCategoryId(categoryId, pageable);
        return categoryCoupons;
    }

    public List<CouponData> getAllCouponsByPriceRange(int minPrice, int maxPrice, int pageNumber) throws ServerException {
        Pageable pageable = PageRequest.of(pageNumber - 1, Constants.AMOUNT_OF_ITEMS_IN_PAGE);
        if (maxPrice < minPrice) {
            throw new ServerException(ErrorType.INVALID_NUMBER, "," + maxPrice + "," + minPrice);
        }
        List<CouponData> couponsByPrice = couponsDal.findAlByPriceRange(minPrice, maxPrice, pageable);
        return couponsByPrice;
    }

    void updateCouponAmountAfterPurchase(long couponId, int couponBuy) throws ServerException {
        Coupon coupon = couponsDal.findById(couponId).get();
        int amount = coupon.getAmount();
        amount = amount - couponBuy;
        if (amount == 0) {
            couponsDal.deleteById(couponId);
        }
        if (amount < 0) {
            throw new ServerException(ErrorType.COUPON_OUT_OF_STOCK, "Coupon out of stock");
        }
        coupon.setAmount(amount);
        couponsDal.save(coupon);
    }

    void couponExistById(long couponId) throws ServerException {
        if (!ValidatorUtils.isIdValid(couponId)) {
            throw new ServerException(ErrorType.COUPON_DOES_NOT_EXIST, "coupon dose not exist " + couponId);
        }
        if (!couponsDal.existsById(couponId)) {
            throw new ServerException(ErrorType.COUPON_DOES_NOT_EXIST, "coupon dose not exist " + couponId);
        }
    }

    private void couponExistByName(String name) throws ServerException {
        boolean isNmeExist = couponsDal.existsByName(name);
        if (isNmeExist) {
            throw new ServerException(ErrorType.NAME_ALREADY_EXIST, "name already exist " + name);
        }
    }

    private void couponValidation(Coupon coupon) throws ServerException {
        if (!ValidatorUtils.isNameLengthValid(coupon.getName())) {
            throw new ServerException(ErrorType.INVALID_NAME, coupon.getName());
        }
        if (coupon.getPrice() < 0) {
            throw new ServerException(ErrorType.NEGATIVE_PRICE, " " + coupon.getPrice());
        }
        if (coupon.getDescription().length() <= 0) {
            throw new ServerException(ErrorType.INVALID_DESCRIPTION, coupon.getDescription());
        }
        if (coupon.getDescription().length() > 45) {
            throw new ServerException(ErrorType.INVALID_DESCRIPTION, coupon.getDescription());
        }
        if (!coupon.getStartDate().before(coupon.getEndDate())) {
            throw new ServerException(ErrorType.INVALID_DATE, String.valueOf(coupon.getStartDate()));
        }
        categoryValid(coupon.getCategory().getId());
        companyValid(coupon.getCompany().getId());
    }

    private void categoryValid(long categoryId) throws ServerException {
        categoriesLogic.categoryExist(categoryId);
    }

    private void companyValid(long companyId) throws ServerException {
        companiesLogic.validCompanyExist(companyId);
    }

    public void removeExpiredCoupons(Date todayDate) {
        couponsDal.deleteAllExpiredCoupons(todayDate);
    }
}
