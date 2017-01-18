package com.brainmatics.pelatihan.controller;

import com.brainmatics.pelatihan.dao.InstrukturDao;
import com.brainmatics.pelatihan.dao.KelasDao;
import com.brainmatics.pelatihan.dao.MateriDao;
import com.brainmatics.pelatihan.dao.PesertaDao;
import com.brainmatics.pelatihan.entity.Instruktur;
import com.brainmatics.pelatihan.entity.Kelas;
import com.brainmatics.pelatihan.entity.Materi;
import com.brainmatics.pelatihan.entity.Peserta;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/kelas")
@SessionAttributes({"pilihanMateri", "pilihanPeserta"})
public class KelasController {
    @Autowired private InstrukturDao instrukturDao;
    @Autowired private MateriDao materiDao;
    @Autowired private PesertaDao pesertaDao;
    @Autowired private KelasDao kelasDao;
    
    @Value("classpath:/report/detail-kelas.jrxml") 
    private Resource templateReportKelas;
    @Value("classpath:/report/sr-daftar-materi.jrxml") 
    private Resource templateSubreportMateri;
    @Value("classpath:/report/sr-daftar-peserta.jrxml") 
    private Resource templateSubreportPeserta;
    
    private JasperReport reportKelas;
    private JasperReport subreportMateri;
    private JasperReport subreportPeserta;
    
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
    
    @PreAuthorize("hasAuthority('VIEW_KELAS')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelMap daftarKelas(@SortDefault("tanggalMulai") Pageable page){
        return new ModelMap()
                .addAttribute("daftarKelas", kelasDao.findAll(page));
    }
    
    @RequestMapping(value = "/detail")
    public ModelMap detailKelas(@RequestParam("id") Kelas kelas){
        return new ModelMap().addAttribute("kelas", kelas);
    }
    
    @RequestMapping(value = "/materi", method = RequestMethod.POST)
    public String pilihMateri(@RequestParam String action,
            @RequestParam("id") Materi materi,  
            @RequestParam("id_kelas") String idKelas,
            @SessionAttribute("pilihanMateri") List<Materi> pilihanMateri){
        if(materi != null){
            for (Materi mx : pilihanMateri) {
                if(mx.getId().equals(materi.getId())) {
                    // bila ada dalam daftar dan action = remove, maka hapus
                    if("remove".equalsIgnoreCase(action)) {
                        pilihanMateri.remove(mx);
                    }
                    return "redirect:form?edited=true&id="+idKelas;
                }
            }
            if("add".equalsIgnoreCase(action)) {
                pilihanMateri.add(materi);
            }
        }
        return "redirect:form?edited=true&id="+idKelas;
    }
    
    @RequestMapping(value = "/peserta", method = RequestMethod.POST)
    public String pilihPeserta(@RequestParam String action,
            @RequestParam("id") Peserta peserta, 
            @RequestParam("id_kelas") String idKelas,
            @SessionAttribute("pilihanPeserta") List<Peserta> pilihanPeserta){
        if(peserta != null){
            for (Peserta mx : pilihanPeserta) {
                if(mx.getId().equals(peserta.getId())) {
                    // bila ada dalam daftar dan action = remove, maka hapus
                    if("remove".equalsIgnoreCase(action)) {
                        pilihanPeserta.remove(mx);
                    }
                    return "redirect:form?edited=true&id="+idKelas;
                }
            }
            if("add".equalsIgnoreCase(action)) {
                pilihanPeserta.add(peserta);
            }
        }
        return "redirect:form?edited=true&id="+idKelas;
    }
    
    @PreAuthorize("hasAuthority('EDIT_KELAS')")
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public ModelMap tampilkanForm(@RequestParam(value = "id", required = false) Kelas kelas, 
            @RequestParam(value = "edited", required = false, defaultValue = "false") Boolean edited,
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
        
        System.out.println("ID Kelas : "+kelas.getId());
        System.out.println("Pilihan materi "+pilihanMateri);
        
        if(kelas.getId() != null){
            if(!edited) {
                pilihanMateri = kelas.getDaftarMateri();
                pilihanPeserta = kelas.getDaftarPeserta();
            }
        }
        
        return new ModelMap("kelas", kelas)
                .addAttribute("pilihanMateri", pilihanMateri)
                .addAttribute("pilihanPeserta", pilihanPeserta);
    }
    
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String prosesForm(@SessionAttribute(value = "pilihanMateri", required = false) List<Materi> pilihanMateri, 
            @SessionAttribute(value = "pilihanPeserta", required = false) List<Peserta> pilihanPeserta, 
            @ModelAttribute @Valid Kelas kelas, BindingResult errors, SessionStatus status){
        if(errors.hasErrors()) {
            return "kelas/form";
        }
        
        kelas.setDaftarMateri(pilihanMateri);
        kelas.setDaftarPeserta(pilihanPeserta);
        
        kelasDao.save(kelas);
        status.setComplete();
        return "redirect:list";
    }
    
    @RequestMapping(value = "/hapus", method = RequestMethod.POST)
    public String hapus(@RequestParam("id") Kelas kelas, SessionStatus status){
        kelasDao.delete(kelas);
        status.setComplete();
        return "redirect:list";
    }
    
    @RequestMapping(value = "/{kelas}/pdf", method = RequestMethod.GET)
    public void cetakDetailKelas(@PathVariable("kelas") Kelas kelas, HttpServletResponse response){
        try {
            if(kelas == null) {
                return;
            }
            response.setContentType("application/pdf");
            
            // header supaya memunculkan dialog download
            response.setHeader("Content-Disposition", "Attachment; filename=kelas-"
                    + kelas.getKode() +".pdf");
            
            JasperPrint reportSiapPrint = prepareJasperPrint(kelas);
            JasperExportManager.exportReportToPdfStream(reportSiapPrint, response.getOutputStream());
        } catch (JRException | IOException ex) {
            Logger.getLogger(KelasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private JasperPrint prepareJasperPrint(Kelas kelas) throws JRException, IOException {
        
        Map<String, Object> params = new HashMap<>();
        params.put("kelas", kelas);
        params.put("subreportMateri", getSubreportMateri());
        params.put("subreportPeserta", getSubreportPeserta());
        params.put("dsSubreportMateri", new JRBeanCollectionDataSource(kelas.getDaftarMateri()));
        params.put("dsSubreportPeserta", new JRBeanCollectionDataSource(kelas.getDaftarPeserta()));
        
        JasperPrint reportSiapPrint = JasperFillManager.fillReport(getReportKelas(), params, new JREmptyDataSource());
        return reportSiapPrint;
    }

    private JasperReport getReportKelas() throws IOException, JRException{
        if(reportKelas == null){
            reportKelas = JasperCompileManager.compileReport(templateReportKelas.getInputStream());
        }
        return reportKelas;
    }

    private JasperReport getSubreportMateri() throws IOException, JRException{
        if(subreportMateri == null){
            subreportMateri = JasperCompileManager.compileReport(templateSubreportMateri.getInputStream());
        }
        return subreportMateri;
    }
    
    private JasperReport getSubreportPeserta() throws IOException, JRException{
        if(subreportPeserta == null){
            subreportPeserta = JasperCompileManager.compileReport(templateSubreportPeserta.getInputStream());
        }
        return subreportPeserta;
    }
}
