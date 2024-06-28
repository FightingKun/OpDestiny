package com.op.des.web.utils;


import com.op.des.web.domain.UserLoginInfo;
import javafx.util.Pair;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UserLoginInfoCacheUtil {

    private static final ConcurrentHashMap<String, UserLoginInfo> userLoginInfoCache = new ConcurrentHashMap<>();

    public static void login(UserLoginInfo userLoginInfo) {
        if (userLoginInfo != null) {
            userLoginInfoCache.put(userLoginInfo.getPhone(), userLoginInfo);
        }
    }

    public static void loginOut(UserLoginInfo userLoginInfo) {
        if (userLoginInfo != null) {
            userLoginInfoCache.remove(userLoginInfo.getPhone());
        }
    }

    public static Pair<Boolean, UserLoginInfo> isLogin(UserLoginInfo userLoginInfo) {
        Map.Entry<String, UserLoginInfo> loginInfoEntry = userLoginInfoCache.entrySet().stream().filter(entry -> Objects.equals(entry.getKey(), userLoginInfo.getPhone())).findFirst().orElse(null);
        boolean isLogin = loginInfoEntry != null && Objects.equals(loginInfoEntry.getValue().getUserId(), userLoginInfo.getUserId()) && loginInfoEntry.getValue().getOutTime() >= System.currentTimeMillis();
        return isLogin ? new Pair<>(true, loginInfoEntry.getValue()) : new Pair<>(false, null);
    }

    static {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleWithFixedDelay(() -> {
            long currentTimeMillis = System.currentTimeMillis();
            Iterator<Map.Entry<String, UserLoginInfo>> iterator = userLoginInfoCache.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, UserLoginInfo> entry = iterator.next();
                if (entry.getValue().getOutTime() <= currentTimeMillis) {
                    iterator.remove();
                }
            }

        }, 30, 30, TimeUnit.MINUTES);
    }

}
