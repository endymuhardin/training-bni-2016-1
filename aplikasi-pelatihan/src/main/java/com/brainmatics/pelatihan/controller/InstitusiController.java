package com.brainmatics.pelatihan.controller;

import com.brainmatics.pelatihan.dao.InstitusiDao;
import com.brainmatics.pelatihan.entity.Institusi;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class InstitusiController {
    @Autowired private InstitusiDao institusiDao;
    
    @RequestMapping(value = "/institusi/form", method = RequestMethod.GET)
    public ModelMap tampilkanForm(@RequestParam(value = "id", required = false)Institusi institusi){
        if(institusi == null) {
            institusi = new Institusi();
        }
        return new ModelMap(institusi);
    }
    
    @RequestMapping(value = "/institusi/form", method = RequestMethod.POST)
    public String prosesForm(@ModelAttribute @Valid Institusi institusi, BindingResult errors, SessionStatus status){
        if(errors.hasErrors()){
            return "/institusi/form";
        }
        
        institusiDao.save(institusi);
        status.setComplete();
        return "redirect:/institusi/list";
    }
    
    @RequestMapping(value = "/institusi/list", method = RequestMethod.GET)
    public ModelMap daftarInstitusi(Pageable page){
        return new ModelMap("daftarInstitusi", institusiDao.findAll(page));
    }
}
