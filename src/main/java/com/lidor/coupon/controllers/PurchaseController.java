package com.lidor.coupon.controllers;

import com.lidor.coupon.dto.PurchaseData;
import com.lidor.coupon.entities.Purchase;
import com.lidor.coupon.exceptions.ServerException;
import com.lidor.coupon.logic.PurchasesLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    private PurchasesLogic purchaseLogic;

    @Autowired
    public PurchaseController(PurchasesLogic purchaseLogic) {
        this.purchaseLogic = purchaseLogic;
    }

    @PostMapping
    public void createPurchase(@RequestHeader String authorization,@RequestBody Purchase purchase) throws ServerException {
        purchaseLogic.buyCoupon(authorization, purchase);
    }

    @PutMapping
    public void updatePurchase(@RequestBody Purchase purchase) throws ServerException {
        purchaseLogic.updatePurchase(purchase);
    }

    @DeleteMapping("{purchaseId}")
    public void removePurchase(@PathVariable("purchaseId") long purchaseId) throws ServerException {
        purchaseLogic.removePurchase(purchaseId);
    }

    @GetMapping("{purchaseId}")
    public PurchaseData getPurchaseByPurchaseID(@PathVariable("purchaseId") int purchaseId) throws ServerException {
        return purchaseLogic.getPurchase(purchaseId);
    }

    @GetMapping("/byCustomerId")
    public List<PurchaseData> getPurchasesByCustomerId(@RequestParam("customerId") long customerId, @RequestParam("pageNumber") int pageNumber) throws ServerException {
        return purchaseLogic.getAllPurchasesByCustomerId(customerId, pageNumber);
    }

    @GetMapping
    public List<PurchaseData> getPurchases(@RequestParam("pageNumber") int pageNumber) throws ServerException {
        return purchaseLogic.getPurchases(pageNumber);
    }

    @GetMapping("/byCouponId")
    public List<PurchaseData> getPurchasesByCouponIdId(@RequestParam("couponId") long couponId, @RequestParam("pageNumber") int pageNumber) throws ServerException {
        return purchaseLogic.getAllPurchasesByCouponId(couponId, pageNumber);
    }
}

