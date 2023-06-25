package com.lidor.coupon.timerTask;

import com.lidor.coupon.logic.CouponsLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;


@Component
public class ExpiredCoupon extends TimerTask {
    private CouponsLogic couponsLogic;

    @Autowired
    public ExpiredCoupon(CouponsLogic couponsLogic) {
        this.couponsLogic = new CouponsLogic();
    }

    @Override
    public void run()  {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        try {
            couponsLogic.removeExpiredCoupons(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
