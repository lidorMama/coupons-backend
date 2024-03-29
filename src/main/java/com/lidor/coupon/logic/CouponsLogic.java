package com.lidor.coupon.logic;

import com.lidor.coupon.Consts.Constants;
import com.lidor.coupon.dal.ICouponDal;
import com.lidor.coupon.dto.CompanyData;
import com.lidor.coupon.dto.CouponData;
import com.lidor.coupon.entities.Company;
import com.lidor.coupon.entities.Coupon;
import com.lidor.coupon.enums.ErrorType;
import com.lidor.coupon.enums.UserType;
import com.lidor.coupon.exceptions.ServerException;
import com.lidor.coupon.util.AuthorizationUtils;
import com.lidor.coupon.util.JWTUtils;
import com.lidor.coupon.util.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
//import java.util.Date;
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

    public void createCoupon(String authorization, Coupon coupon) throws ServerException {
        AuthorizationUtils.validatePermission(authorization, UserType.Company);
        couponValidation(coupon);
        couponExistByName(coupon.getName());
        Date todayDate = convertDate();
        coupon.setStartDate(todayDate);
//        Company company = getCompany(authorization);
//        coupon.setCompany(company);
        try {
            couponsDal.save(coupon);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to create coupon " + coupon.getName());
        }
    }

    public void updateCoupon(String authorization, Coupon coupon) throws ServerException {
        AuthorizationUtils.validatePermission(authorization, UserType.Company);
        Date todayDate = convertDate();
        coupon.setStartDate(todayDate);
        couponValidation(coupon);
        try {
            couponsDal.save(coupon);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to update coupon " + coupon.getName());
        }
    }

    public void removeCoupon(String authorization, long couponId) throws ServerException {
        AuthorizationUtils.validatePermission(authorization, UserType.Company);
        couponExistById(couponId);
        try {
            couponsDal.deleteById(couponId);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to remove coupon " + couponId);
        }
    }

    public CouponData getCoupon(long couponId) throws ServerException {
        couponExistById(couponId);
        try {
            CouponData coupon = couponsDal.getCoupon(couponId);
            return coupon;
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to get coupon " + couponId);
        }

    }

    public List<CouponData> getCoupons(int pageNumber) throws ServerException {
        Pageable pageable = PageRequest.of(pageNumber - 1, Constants.AMOUNT_OF_ITEMS_IN_PAGE);
        try {
            List<CouponData> coupons = couponsDal.findAll(pageable);
            return coupons;
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to get all coupons");
        }

    }

    public List<CouponData> getCouponsByCompanyID(long companyId, int pageNumber) throws ServerException {
        companyValid(companyId);
        Pageable pageable = PageRequest.of(pageNumber - 1, Constants.AMOUNT_OF_ITEMS_IN_PAGE);
        try {
            List<CouponData> companyCoupons = couponsDal.findAllByCompanyId(companyId, pageable);
            return companyCoupons;
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to get coupons by company id" + companyId);
        }
    }

    public List<CouponData> getCouponsByCategoryID(long categoryId, int pageNumber) throws ServerException {
        categoryValid(categoryId);
        Pageable pageable = PageRequest.of(pageNumber - 1, Constants.AMOUNT_OF_ITEMS_IN_PAGE);
        try {
            List<CouponData> categoryCoupons = couponsDal.findAllByCategoryId(categoryId, pageable);
            return categoryCoupons;
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to get coupons by category id" + categoryId);
        }
    }

    public List<CouponData> getAllCouponsByPriceRange(int minPrice, int maxPrice, int pageNumber) throws ServerException {
        Pageable pageable = PageRequest.of(pageNumber - 1, Constants.AMOUNT_OF_ITEMS_IN_PAGE);
        if (maxPrice < minPrice) {
            throw new ServerException(ErrorType.INVALID_NUMBER, "," + maxPrice + "," + minPrice);
        }
        try {
            List<CouponData> couponsByPrice = couponsDal.findAlByPriceRange(minPrice, maxPrice, pageable);
            return couponsByPrice;
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to get coupons by price range");
        }
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
        try {
            couponsDal.save(coupon);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to update coupons after purchase" + couponId);
        }
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
    }

    private void categoryValid(long categoryId) throws ServerException {
        categoriesLogic.categoryExist(categoryId);
    }

    private void companyValid(long companyId) throws ServerException {
        companiesLogic.validCompanyExist(companyId);
    }

    private Date convertDate() throws ServerException {
        java.util.Date today = Calendar.getInstance().getTime();
        Date sqlDate = new Date(today.getTime());
        return sqlDate;
    }

    private Company getCompany(String authorization) throws ServerException {
        int companyId = JWTUtils.getCompanyIdByToken(authorization);
        Company company = companiesLogic.getCompany(companyId);
        return company;
    }

    public void removeExpiredCoupons(java.util.Date todayDate) {
        couponsDal.deleteAllExpiredCoupons(todayDate);
    }


}
