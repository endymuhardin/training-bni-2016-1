package com.brainmatics.pelatihan.controller;

import com.brainmatics.pelatihan.dao.InstrukturDao;
import com.brainmatics.pelatihan.dao.MateriDao;
import com.brainmatics.pelatihan.dao.PesertaDao;
import com.brainmatics.pelatihan.entity.Instruktur;
import com.brainmatics.pelatihan.entity.Kelas;
import com.brainmatics.pelatihan.entity.Materi;
import com.brainmatics.pelatihan.entity.Peserta;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/kelas")
@SessionAttributes({"pilihanMateri", "pilihanPeserta"})
public class KelasController {
    @Autowired private InstrukturDao instrukturDao;
    @Autowired private MateriDao materiDao;
    @Autowired private PesertaDao pesertaDao;
    
    @ModelAttribute("daftarMateri")
    public Iterable<Materi> daftarMateri(){
        return materiDao.findAll();
    }
    
    @ModelAttribute("daftarPeserta")
    public Iterable<Peserta> daftarPeserta(){
        return pesertaDao.findAll();
    }
    
    @ModelAttribute("daftarInstruktur")
    public Iterable<Instruktur> daftarInstruktur(){
        return instrukturDao.findAll();
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelMap daftarKelas(){
        return new ModelMap();
    }
    
    @RequestMapping(value = "/materi", method = RequestMethod.POST)
    public String pilihMateri(@RequestParam String action,
            @RequestParam("id") Materi materi, 
            @SessionAttribute("pilihanMateri") List<Materi> pilihanMateri){
        if(materi != null){
            for (Materi mx : pilihanMateri) {
                if(mx.getId().equals(materi.getId())) {
                    // bila ada dalam daftar dan action = remove, maka hapus
                    if("remove".equalsIgnoreCase(action)) {
                        pilihanMateri.remove(mx);
                    }
                    return "redirect:form";
                }
            }
            if("add".equalsIgnoreCase(action)) {
                pilihanMateri.add(materi);
            }
        }
        return "redirect:form";
    }
    
    @RequestMapping(value = "/peserta", method = RequestMethod.POST)
    public String pilihPeserta(@RequestParam String action,
            @RequestParam("id") Peserta peserta, 
            @SessionAttribute("pilihanPeserta") List<Peserta> pilihanPeserta){
        if(peserta != null){
            for (Peserta mx : pilihanPeserta) {
                if(mx.getId().equals(peserta.getId())) {
                    // bila ada dalam daftar dan action = remove, maka hapus
                    if("remove".equalsIgnoreCase(action)) {
                        pilihanPeserta.remove(mx);
                    }
                    return "redirect:form";
                }
            }
            if("add".equalsIgnoreCase(action)) {
                pilihanPeserta.add(peserta);
            }
        }
        return "redirect:form";
    }
    
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public ModelMap tampilkanForm(@RequestParam(value = "id", required = false) Kelas kelas, 
            @SessionAttribute(value = "pilihanMateri", required = false) List<Materi> pilihanMateri, 
            @SessionAttribute(value = "pilihanPeserta", required = false) List<Peserta> pilihanPeserta){
        if(kelas == null){
            kelas = new Kelas();
        }
        
        if(pilihanMateri == null){
            pilihanMateri = new ArrayList<>();
        }
        
        if(pilihanPeserta == null){
            pilihanPeserta = new ArrayList<>();
        }
        
        return new ModelMap("kelas", kelas)
                .addAttribute("pilihanMateri", pilihanMateri)
                .addAttribute("pilihanPeserta", pilihanPeserta);
    }
}
