package com.lidor.coupon.timerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class Initializer {

    private Timer timer;
    private TimerTask timerTask;

    @Autowired
    public Initializer(ExpiredCoupon myTimerTask) {
        this.timer = new Timer();
        this.timerTask = myTimerTask;
        createTimerTask();
    }

    private void createTimerTask() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
        c.set(Calendar.HOUR_OF_DAY, 00);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 00);
        c.set(Calendar.MILLISECOND, 0);
        Date date = c.getTime();
        timer.schedule(timerTask, date, 86400000);
    }


}
