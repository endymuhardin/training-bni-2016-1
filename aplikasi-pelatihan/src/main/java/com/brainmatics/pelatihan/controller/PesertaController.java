package com.brainmatics.pelatihan.controller;

import com.brainmatics.pelatihan.dao.PesertaDao;
import com.brainmatics.pelatihan.entity.Peserta;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

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
    
    @RequestMapping("/peserta/list/")
    public ModelMap daftarPeserta(Pageable page){
        ModelMap data = new ModelMap();
        data.put("daftarPeserta", pesertaDao.findAll(page));
        return data;
    }
    
    @RequestMapping(value = "/peserta/form/", method = RequestMethod.GET)
    public ModelMap tampilkanForm(@RequestParam(required = false, name = "id") Peserta p){
        ModelMap data = new ModelMap();
        
        if(p == null){
            p = new Peserta();
        }
        
        data.put("peserta", p);
        
        return data;
    }
    
    @RequestMapping(value = "/peserta/form/", method = RequestMethod.POST)
    public String prosesForm(@ModelAttribute @Valid Peserta p, BindingResult errors, SessionStatus status){
        if(errors.hasErrors()) {
            return "/peserta/form";
        }
        
        status.setComplete();
        return "redirect:/peserta/list/";
    }
}
