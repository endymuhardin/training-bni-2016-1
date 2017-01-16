package com.brainmatics.pelatihan.controller;

import com.brainmatics.pelatihan.dao.HasilTestDao;
import com.brainmatics.pelatihan.entity.HasilTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HasilTestController {
    
    @Autowired
    private HasilTestDao hasilTestDao;
    
    @RequestMapping("/hasil/{peserta}/")
    public Iterable<HasilTest> hasilByPeserta(@PathVariable("peserta") String peserta){
        return hasilTestDao.findByPeserta(peserta);
    }
}
