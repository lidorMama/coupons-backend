package com.lidor.coupon.timerTask;

import com.lidor.coupon.exceptions.ServerException;
import com.lidor.coupon.logic.CouponsLogic;

import java.sql.Date;
import java.util.Calendar;
import java.util.TimerTask;

public class ExpiredCoupon extends TimerTask {
    private CouponsLogic couponsLogic;

    public ExpiredCoupon() {
        this.couponsLogic = new CouponsLogic();
    }

    @Override
    public void run()  {
        long now = Calendar.getInstance().getTimeInMillis();
        Date todayDate = new Date(now);
        try {
            couponsLogic.removeExpiredCoupons(todayDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
