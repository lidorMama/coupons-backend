package com.lidor.coupon.timerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Timer;

@Component
public class Initializer {

    private Timer timer;
    private ExpiredCoupon expiredCouponDeletionTimerTask;


    @Autowired
    public Initializer(ExpiredCoupon expiredCouponDeletionTimerTask) {
        this.timer = new Timer();
        this.expiredCouponDeletionTimerTask = expiredCouponDeletionTimerTask;
        removeExpiredCoupons();
    }



    public void removeExpiredCoupons(){
        timer.schedule(expiredCouponDeletionTimerTask,0, 86400000);
    }



}
