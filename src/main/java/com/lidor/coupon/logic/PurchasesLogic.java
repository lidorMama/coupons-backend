package com.lidor.coupon.logic;

import com.lidor.coupon.Consts.Constants;
import com.lidor.coupon.dal.IPurchaseDal;
import com.lidor.coupon.dto.CustomerData;
import com.lidor.coupon.dto.PurchaseData;
import com.lidor.coupon.entities.Customer;
import com.lidor.coupon.entities.Purchase;
import com.lidor.coupon.enums.ErrorType;
import com.lidor.coupon.enums.UserType;
import com.lidor.coupon.exceptions.ServerException;
import com.lidor.coupon.util.AuthorizationUtils;
import com.lidor.coupon.util.JWTUtils;
import com.lidor.coupon.util.ValidatorUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Service
public class PurchasesLogic {

    private IPurchaseDal purchasesDal;
    private UsersLogic usersLogic;
    private CategoriesLogic categoriesLogic;
    private CouponsLogic couponsLogic;
    private CustomersLogic customersLogic;

    @Autowired
    public PurchasesLogic(IPurchaseDal purchasesDal, UsersLogic usersLogic, CategoriesLogic categoriesLogic, CouponsLogic couponsLogic) {
        this.purchasesDal = purchasesDal;
        this.usersLogic = usersLogic;
        this.categoriesLogic = categoriesLogic;
        this.couponsLogic = couponsLogic;
    }

    public void buyCoupon(String authorization, Purchase purchase) throws ServerException {
        purchaseValidation(purchase);
        couponValid(purchase.getCoupon().getId());
        long customerId = JWTUtils.validateToken(authorization);
        purchase.getCustomer().setId(customerId);
        Date todayDate = convertDate();
        purchase.setTimestamp(todayDate);
        long couponId = purchase.getCoupon().getId();
        int amountOfCoupons = purchase.getAmount();
        try {
            purchasesDal.save(purchase);
            couponsLogic.updateCouponAmountAfterPurchase(couponId, amountOfCoupons);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to buy coupon" + purchase.toString());
        }
    }

    public void updatePurchase(Purchase purchase) throws ServerException {
        purchaseValidation(purchase);
        try {
            purchasesDal.save(purchase);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to update purchase" + purchase.toString());
        }
    }

    public PurchaseData getPurchase(long purchaseId) throws ServerException {
        purchaseExistById(purchaseId);
        try {
            PurchaseData purchaseCoupon = purchasesDal.findPurchase(purchaseId);
            return purchaseCoupon;
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to update purchase" + purchaseId);
        }
    }

    public void removePurchase(long purchaseId) throws ServerException {
        purchaseExistById(purchaseId);
        try {
            purchasesDal.deleteById(purchaseId);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to remove purchase" + purchaseId);
        }
    }

    public List<PurchaseData> getPurchases(String authorization, int pageNumber) throws ServerException {
        AuthorizationUtils.validatePermission(authorization, UserType.Admin);
        Pageable pageable = PageRequest.of(pageNumber - 1, Constants.AMOUNT_OF_ITEMS_IN_PAGE);
        try {
            List<PurchaseData> purchases = purchasesDal.findPurchases(pageable);
            return purchases;
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to get purchases");
        }
    }

    public List<PurchaseData> getAllPurchasesByCompanyId(String authorization, int pageNumber) throws ServerException {
        Claims claims = JWTUtils.decodeJWT(authorization);
        long companyId = Long.parseLong(claims.getAudience());
        Pageable pageable = PageRequest.of(pageNumber - 1, Constants.AMOUNT_OF_ITEMS_IN_PAGE);
        try {
            List<PurchaseData> companyPurchases = purchasesDal.findAllByCompanyId(companyId, pageable);
            return companyPurchases;
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to get purchases by company id" + companyId);
        }
    }

    public List<PurchaseData> getAllPurchasesByCustomerId(String authorization, int pageNumber) throws ServerException {
        long customerId = JWTUtils.validateToken(authorization);
        Pageable pageable = PageRequest.of(pageNumber - 1, Constants.AMOUNT_OF_ITEMS_IN_PAGE);
        try {
            List<PurchaseData> userPurchases = purchasesDal.findAllByCustomerId(customerId, pageable);
            return userPurchases;
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to get purchases by customer id" + customerId);
        }
    }

    private void purchaseValidation(Purchase purchase) throws ServerException {
        if (purchase.getAmount() <= 0) {
            throw new ServerException(ErrorType.INVALID_PURCHASE, "amount is to low " + purchase.getAmount());
        }

    }

    private void purchaseExistById(long purchaseId) throws ServerException {
        if (!ValidatorUtils.isIdValid(purchaseId)) {
            throw new ServerException(ErrorType.PURCHASE_DOES_NOT_EXIST, "," + purchaseId);
        }
    }

    private void userValid(long userId) throws ServerException {
        usersLogic.validUserExistById(userId);
    }

    private void couponValid(long couponId) throws ServerException {
        couponsLogic.couponExistById(couponId);
    }

    private Date convertDate() throws ServerException {
        java.util.Date today = Calendar.getInstance().getTime();
        Date sqlDate = new Date(today.getTime());
        return sqlDate;
    }
}
