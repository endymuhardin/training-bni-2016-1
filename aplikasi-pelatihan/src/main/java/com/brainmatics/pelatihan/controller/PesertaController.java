package com.brainmatics.pelatihan.controller;

import com.brainmatics.pelatihan.dao.InstitusiDao;
import com.brainmatics.pelatihan.dao.PesertaDao;
import com.brainmatics.pelatihan.entity.Institusi;
import com.brainmatics.pelatihan.entity.Peserta;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PesertaController {
    
    @Autowired private PesertaDao pesertaDao;
    @Autowired private InstitusiDao institusiDao;
    
    @PreAuthorize("isAnonymous()")
    @RequestMapping("/peserta/registrasi/")
    public void registrasi(){}
    
    @RequestMapping(value = "/api/peserta/", method = RequestMethod.GET)
    @ResponseBody
    public Page<Peserta> semuaPeserta(Pageable page){
        return pesertaDao.findAll(page);
    }
    
    @PreAuthorize("hasRole('EDIT_PESERTA')")
    @RequestMapping(value = "/api/peserta/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void simpanPeserta(@RequestBody @Valid Peserta p){
        pesertaDao.save(p);
    }
    
    @RequestMapping("/api/peserta/{id}/")
    @ResponseBody
    public Peserta cariById(@PathVariable(name = "id") Peserta peserta){
        return peserta;
    }
    
    @PreAuthorize("hasRole('VIEW_PESERTA')")
    @RequestMapping("/peserta/list/")
    public ModelMap daftarPeserta(Pageable page){
        ModelMap data = new ModelMap();
        data.put("daftarPeserta", pesertaDao.findAll(page));
        return data;
    }
    
    @ModelAttribute("daftarInstitusi")
    public Iterable<Institusi> daftarInstitusi(){
        return institusiDao.findAll();
    }
    
    @PreAuthorize("hasRole('ADMIN')")
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
    public String prosesForm(@ModelAttribute @Valid Peserta p, BindingResult errors, SessionStatus status, @RequestParam MultipartFile foto){
        Peserta px = pesertaDao.findByEmail(p.getEmail());
        if(px != null){
            errors.rejectValue("email", "email.unique", "Alamat email sudah dipakai");
        }
        
        if(errors.hasErrors()) {
            return "/peserta/form";
        }
        
        prosesFoto(foto);
        
        pesertaDao.save(p);
        status.setComplete();
        return "redirect:/peserta/list/";
    }
    
    private void prosesFoto(MultipartFile foto){
        // tampilkan informasi
        System.out.println("Nama file : "+foto.getOriginalFilename());
        System.out.println("Jenis file : "+foto.getContentType());
        System.out.println("Ukuran file : "+foto.getSize());
        
        // best practices : simpan file di file server, jangan di local file system
        // contoh : upload ke Amazon S3, Dropbox, Google Drive, CDN
    }
}
