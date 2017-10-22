package com.aik.security;

import com.aik.dao.AccDoctorAccountMapper;
import com.aik.dao.AccUserAccountMapper;
import com.aik.model.AccDoctorAccount;
import com.aik.model.AccUserAccount;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Created by as on 2017/8/7.
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    private AccDoctorAccountMapper accDoctorAccountMapper;

    private AccUserAccountMapper accUserAccountMapper;

    @Value("${jwt.doctor-sign}")
    private String jwtDoctorSign;

    @Value("${jwt.user-sign}")
    private String jwtUserSign;

    @Autowired
    public void setAccDoctorAccountMapper(AccDoctorAccountMapper accDoctorAccountMapper) {
        this.accDoctorAccountMapper = accDoctorAccountMapper;
    }

    @Autowired
    public void setAccUserAccountMapper(AccUserAccountMapper accUserAccountMapper) {
        this.accUserAccountMapper = accUserAccountMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if (userName.contains(jwtDoctorSign)) {
            userName = userName.replace(jwtDoctorSign, "");

            AccDoctorAccount doctor = accDoctorAccountMapper.selectByUserName(userName);

            if (doctor == null) {
                throw new UsernameNotFoundException(String.format("No user found with username '%s'.", userName));
            } else {
                return JwtUserFactory.create(doctor);
            }
        } else if (userName.contains((jwtUserSign))) {
            userName = userName.replace(jwtUserSign, "");

            AccUserAccount user = accUserAccountMapper.selectByUserNameOrMobileNo(userName);

            if (user == null) {
                throw new UsernameNotFoundException(String.format("No user found with username '%s'.", userName));
            } else {
                return JwtUserFactory.create(user);
            }
        } else {
            throw new UsernameNotFoundException(String.format("No user found with userName '%s'.", userName));
        }

    }
}
