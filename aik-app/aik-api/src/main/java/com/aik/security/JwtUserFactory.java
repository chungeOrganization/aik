package com.aik.security;

import com.aik.model.AccDoctorAccount;
import com.aik.model.AccUserAccount;

/**
 * Description:
 * Created by as on 2017/8/7.
 */
public final class JwtUserFactory {
    private JwtUserFactory() {
    }

    public static JwtUser create(AccDoctorAccount doctor) {
        return new JwtUser(
                doctor.getId(),
                doctor.getUserName(),
                doctor.getPassword(),
                null,
                doctor.getCreateDate()
        );
    }

    public static JwtUser create(AccUserAccount user) {
        return new JwtUser(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                null,
                user.getCreateDate()
        );
    }
}
