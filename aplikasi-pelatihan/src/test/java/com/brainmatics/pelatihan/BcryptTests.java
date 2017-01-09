package com.brainmatics.pelatihan;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptTests {
    
    @Test
    public void testBcrypt(){
        System.out.println("Password : 1234");
        String hasil = new BCryptPasswordEncoder().encode("1234");
        System.out.println("Hasil : "+hasil);
        Assert.assertTrue(true);
    }
}
