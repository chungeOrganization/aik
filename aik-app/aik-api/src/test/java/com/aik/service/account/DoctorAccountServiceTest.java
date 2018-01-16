package com.aik.service.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Desc:
 * Create by as on 2018/1/16
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DoctorAccountServiceTest {
    @Autowired
    private DoctorAccountService doctorAccountService;

    @Test
    public void generateQrCodeTest() {
        try {
            System.out.println("??");
            doctorAccountService.generateQrCode(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
