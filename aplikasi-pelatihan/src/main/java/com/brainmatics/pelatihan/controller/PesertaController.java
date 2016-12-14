package com.brainmatics.pelatihan.controller;

import com.brainmatics.pelatihan.dao.PesertaDao;
import com.brainmatics.pelatihan.entity.Peserta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PesertaController {
    
    @Autowired private PesertaDao pesertaDao;
    
    @RequestMapping("/peserta/registrasi/")
    public void registrasi(){}
    
    @RequestMapping("/api/peserta/")
    @ResponseBody
    public Page<Peserta> semuaPeserta(Pageable page){
        return pesertaDao.findAll(page);
    }
    
    @RequestMapping("/api/peserta/{id}/")
    @ResponseBody
    public Peserta cariById(@PathVariable(name = "id") Peserta peserta){
        return peserta;
    }
}
