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
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/kelas")
@SessionAttributes({"pilihanMateri", "pilihanPeserta"})
public class KelasController {
    @Autowired private InstrukturDao instrukturDao;
    @Autowired private MateriDao materiDao;
    @Autowired private PesertaDao pesertaDao;
    
    private List<Peserta> pilihanPeserta;
    private List<Materi> pilihanMateri;
    
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
    
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public ModelMap tampilkanForm(@RequestParam(value = "id", required = false) Kelas kelas){
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
