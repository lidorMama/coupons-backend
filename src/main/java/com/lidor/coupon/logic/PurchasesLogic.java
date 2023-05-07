package com.lidor.coupon.logic;

import com.lidor.coupon.Consts.Constants;
import com.lidor.coupon.dal.IPurchaseDal;
import com.lidor.coupon.dto.PurchaseData;
import com.lidor.coupon.entities.Purchase;
import com.lidor.coupon.enums.ErrorType;
import com.lidor.coupon.exceptions.ServerException;
import com.lidor.coupon.util.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchasesLogic {

    private IPurchaseDal purchasesDal;
    private UsersLogic usersLogic;
    private CategoriesLogic categoriesLogic;
    private CouponsLogic couponsLogic;

    @Autowired
    public PurchasesLogic(IPurchaseDal purchasesDal, UsersLogic usersLogic, CategoriesLogic categoriesLogic, CouponsLogic couponsLogic) {
        this.purchasesDal = purchasesDal;
        this.usersLogic = usersLogic;
        this.categoriesLogic = categoriesLogic;
        this.couponsLogic = couponsLogic;
    }

    public void buyCoupon(Purchase purchase) throws ServerException {
        purchaseValidation(purchase);
        couponValid(purchase.getCoupon().getId());
        long couponId = purchase.getCoupon().getId();
        int amountOfCoupons = purchase.getAmount();
        couponsLogic.updateCouponAmountAfterPurchase(couponId, amountOfCoupons);
        purchasesDal.save(purchase);
    }

    public void updatePurchase(Purchase purchase) throws ServerException{
        purchaseValidation(purchase);
        purchasesDal.save(purchase);
    }

    public PurchaseData getPurchase(long purchaseId) throws ServerException {
        purchaseExistById(purchaseId);
        PurchaseData purchaseCoupon = purchasesDal.findPurchase(purchaseId);
        return purchaseCoupon;
    }

    public void removePurchase(long purchaseId) throws ServerException {
        purchaseExistById(purchaseId);
        purchasesDal.deleteById(purchaseId);
    }

    public List<PurchaseData> getPurchases(int pageNumber) throws ServerException {
        Pageable pageable = PageRequest.of(pageNumber - 1, Constants.AMOUNT_OF_ITEMS_IN_PAGE);
        List<PurchaseData> purchases = purchasesDal.findPurchases(pageable);
        return purchases;
    }

    public List<PurchaseData> getAllPurchasesByCouponId(long couponId, int pageNumber) throws ServerException {
        couponValid(couponId);
        Pageable pageable = PageRequest.of(pageNumber - 1, Constants.AMOUNT_OF_ITEMS_IN_PAGE);
        List<PurchaseData> couponPurchases = purchasesDal.findAllByCouponId(couponId, pageable);
        return couponPurchases;
    }

    public List<PurchaseData> getAllPurchasesByCustomerId(long customerId, int pageNumber) throws ServerException {
        userValid(customerId);
        Pageable pageable = PageRequest.of(pageNumber - 1, Constants.AMOUNT_OF_ITEMS_IN_PAGE);
        List<PurchaseData> userPurchases = purchasesDal.findAllByCustomerId(customerId, pageable);
        return userPurchases;
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
}
