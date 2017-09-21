package com.aik.security;

/**
 * Description:
 * Created by as on 2017/8/11.
 */
public class AuthUserDetailsThreadLocal {
    private final static InheritableThreadLocal<JwtUser> currentDoctorThreadLocal = new InheritableThreadLocal<JwtUser>();

    public static void setCurrentUser(JwtUser jwtUser){
        currentDoctorThreadLocal.set(jwtUser);
    }

    public static JwtUser getCurrentUser(){
        return currentDoctorThreadLocal.get();
    }

    public static Integer getCurrentUserId() {
        return currentDoctorThreadLocal.get().getId();
    }
}
