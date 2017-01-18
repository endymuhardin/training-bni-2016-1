package com.brainmatics.pelatihan.controller;

import com.brainmatics.pelatihan.dao.InstitusiDao;
import com.brainmatics.pelatihan.dao.PesertaDao;
import com.brainmatics.pelatihan.entity.Institusi;
import com.brainmatics.pelatihan.entity.Peserta;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PesertaController {
    
    @Autowired 
    @Qualifier("onlineTestClient")
    private RestTemplate onlineTestClient;
    
    @Autowired private PesertaDao pesertaDao;
    @Autowired private InstitusiDao institusiDao;
    
    private JasperReport reportPesertaHasilCompile;
    
    @PreAuthorize("isAnonymous()")
    @RequestMapping("/peserta/registrasi/")
    public void registrasi(){}
    
    @RequestMapping("/api/peserta/{id}/")
    @ResponseBody
    public Peserta cariById(@PathVariable(name = "id") Peserta peserta){
        return peserta;
    }
    
    @RequestMapping(value = "/api/peserta/", method = RequestMethod.GET)
    @ResponseBody
    public Page<Peserta> semuaPeserta(Pageable page){
        return pesertaDao.findAll(page);
    }
    
    @PreAuthorize("hasAuthority('EDIT_PESERTA')")
    @RequestMapping(value = "/api/peserta/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void simpanPeserta(@RequestBody @Valid Peserta p){
        pesertaDao.save(p);
    }
    
    @PreAuthorize("hasAuthority('EDIT_PESERTA')")
    @RequestMapping("/api/peserta/{id}/nilai/")
    @ResponseBody
    public Iterable<Object> nilaiPeserta(@PathVariable(name = "id") Peserta peserta){
        if(peserta == null){
            return new ArrayList<>();
        }
        return onlineTestClient.getForObject("http://localhost:9090/hasil/"+peserta.getId()+"/", Iterable.class);
    }
    
    @PreAuthorize("hasAuthority('VIEW_PESERTA')")
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
    
    @PreAuthorize("hasAuthority('EDIT_PESERTA')")
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
    
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/report/peserta", method = RequestMethod.GET)
    public ModelMap reportPeserta(Authentication currentUser){
        ModelMap mm = new ModelMap();
        Iterable<Peserta> dataPeserta = pesertaDao.findAll();
        
        mm.addAttribute("format", "pdf");
        mm.addAttribute("dataDalamReport", dataPeserta);
        mm.addAttribute("tanggalCetak", new Date());
        mm.addAttribute("userCetak", currentUser.getPrincipal());
        
        return mm;
    }
    
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/peserta.pdf", method = RequestMethod.GET)
    public void daftarPesertaPdf(Authentication currentUser, HttpServletResponse response){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            response.setContentType("application/pdf");
            // header supaya memunculkan dialog download
            response.setHeader("Content-Disposition", "Attachment; filename=daftar-peserta-"
                    + formatter.format(new Date())+".pdf");
            JasperPrint reportSiapPrint = prepareJasperPrint(currentUser);
            JasperExportManager.exportReportToPdfStream(reportSiapPrint, response.getOutputStream());
        } catch (JRException | IOException ex) {
            Logger.getLogger(PesertaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/peserta.xlsx", method = RequestMethod.GET)
    public void daftarPesertaXls(Authentication currentUser, HttpServletResponse response){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            response.setContentType("application/vnd.ms-excel");
            // header supaya memunculkan dialog download
            response.setHeader("Content-Disposition", "Attachment; filename=daftar-peserta-"
                    + formatter.format(new Date())+".xlsx");
            
            JasperPrint reportSiapPrint = prepareJasperPrint(currentUser);
            
            JRXlsxExporter exporter = new JRXlsxExporter();

            exporter.setExporterInput(new SimpleExporterInput(reportSiapPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            exporter.exportReport();
        } catch (JRException | IOException ex) {
            Logger.getLogger(PesertaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private JasperPrint prepareJasperPrint(Authentication currentUser) throws JRException {
        
        Map<String, Object> params = new HashMap<>();
        params.put("userCetak", currentUser.getPrincipal());
        params.put("tanggalCetak", new Date());
        List<Peserta> daftarPeserta = new ArrayList<>();
        Iterable<Peserta> iterablePeserta = pesertaDao.findAll();
        // versi lambda (Java 8 only)
        iterablePeserta.forEach(daftarPeserta :: add);
        // versi non lambda
        /*
        Iterator<Peserta> iteratorPeserta = iterablePeserta.iterator();
        while(iteratorPeserta.hasNext()){
        Peserta p = iteratorPeserta.next();
        daftarPeserta.add(p);
        }
        */
        
        JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(daftarPeserta);
        JasperPrint reportSiapPrint = JasperFillManager.fillReport(getReportPeserta(), params, data);
        return reportSiapPrint;
    }
    
    private JasperReport getReportPeserta(){
        // kalau belum pernah compile, compile dulu
        if(reportPesertaHasilCompile == null){
            try {
                reportPesertaHasilCompile = JasperCompileManager
                        .compileReport(this.getClass().getResourceAsStream("/report/peserta.jrxml"));
            } catch (JRException ex) {
                Logger.getLogger(PesertaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // kalau sudah pernah compile, langsung return
        return reportPesertaHasilCompile;
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
