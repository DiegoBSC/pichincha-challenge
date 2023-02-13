package com.pichincha.transacctions.utils;

import java.time.LocalDateTime;

public class DateUtils {

    public static LocalDateTime setTimeFromLocalNowStart(LocalDateTime localDateTime){
        return localDateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }
    public static LocalDateTime setTimeFromLocalNowEnd(LocalDateTime localDateTime){
        return localDateTime.withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);
    }
}
