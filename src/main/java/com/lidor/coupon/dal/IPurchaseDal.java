package com.lidor.coupon.dal;

import com.lidor.coupon.dto.PurchaseData;
import com.lidor.coupon.entities.Purchase;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPurchaseDal extends CrudRepository<Purchase, Long> {
    @Query("SELECT NEW com.lidor.coupon.dto.PurchaseData(pr.id, pr.amount, pr.timestamp, pr.customer.firstName, pr.customer.lastName, pr.coupon.name, pr.coupon.price, pr.coupon.description ) FROM Purchase pr Join Customer cs ON pr.customer.id = cs.id JOIN Coupon cp ON pr.coupon.id= cp.id")
    List<PurchaseData> findPurchases(Pageable pageable);

    @Query("SELECT NEW com.lidor.coupon.dto.PurchaseData(pr.id, pr.amount, pr.timestamp, pr.customer.firstName, pr.customer.lastName, pr.coupon.name, pr.coupon.price, pr.coupon.description ) FROM Purchase pr Join Customer cs ON pr.customer.id = cs.id JOIN Coupon cp ON pr.coupon.id= cp.id WHERE pr.coupon.company.id= :companyId")
    List<PurchaseData> findAllByCompanyId(@Param("companyId") long companyId, Pageable pageable);

    @Query("SELECT NEW com.lidor.coupon.dto.PurchaseData(pr.id, pr.amount, pr.timestamp, pr.customer.firstName, pr.customer.lastName, pr.coupon.name, pr.coupon.price, pr.coupon.description ) FROM Purchase pr Join Customer cs ON pr.customer.id = cs.id JOIN Coupon cp ON pr.coupon.id= cp.id WHERE pr.customer.id= :customerId")
    List<PurchaseData> findAllByCustomerId(@Param("customerId") long customerId, Pageable pageable);

    @Query("SELECT NEW com.lidor.coupon.dto.PurchaseData(pr.id, pr.amount, pr.timestamp, pr.customer.firstName, pr.customer.lastName, pr.coupon.name, pr.coupon.price, pr.coupon.description ) FROM Purchase pr Join Customer cs ON pr.customer.id = cs.id JOIN Coupon cp ON pr.coupon.id= cp.id WHERE pr.id= :purchaseId")
    PurchaseData findPurchase(@Param("purchaseId") long purchaseId);

}
