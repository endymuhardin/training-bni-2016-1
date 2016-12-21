package com.brainmatics.pelatihan.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

@Entity @Table(name = "t_kelas")
public class Kelas {
    @Id @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @NotNull @NotEmpty
    @Size(max = 20)
    @Column(unique = true)
    private String kode;
    
    @NotNull @NotEmpty
    @Size(max = 255)
    private String nama;
    
    @NotNull
    @Column(name = "tanggal_mulai")
    private LocalDate tanggalMulai;
    
    @NotNull
    @Column(name = "tanggal_selesai")
    private LocalDate tanggalSelesai;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_instruktur")
    private Instruktur instruktur;
    
    @ManyToMany
    @JoinTable(
            name = "t_materi_kelas", 
            joinColumns = @JoinColumn(name = "id_kelas"),
            inverseJoinColumns = @JoinColumn(name = "id_materi")
    )
    private List<Materi> daftarMateri = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
            name = "t_peserta_kelas", 
            joinColumns = @JoinColumn(name = "id_kelas"),
            inverseJoinColumns = @JoinColumn(name = "id_peserta")
    )
    private List<Peserta> daftarPeserta = new ArrayList<>();
    
    @OneToMany(mappedBy = "kelas", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Kehadiran> daftarKehadiran = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public LocalDate getTanggalMulai() {
        return tanggalMulai;
    }

    public void setTanggalMulai(LocalDate tanggalMulai) {
        this.tanggalMulai = tanggalMulai;
    }

    public LocalDate getTanggalSelesai() {
        return tanggalSelesai;
    }

    public void setTanggalSelesai(LocalDate tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
    }

    public Instruktur getInstruktur() {
        return instruktur;
    }

    public void setInstruktur(Instruktur instruktur) {
        this.instruktur = instruktur;
    }

    public List<Materi> getDaftarMateri() {
        return daftarMateri;
    }

    public void setDaftarMateri(List<Materi> daftarMateri) {
        this.daftarMateri = daftarMateri;
    }

    public List<Peserta> getDaftarPeserta() {
        return daftarPeserta;
    }

    public void setDaftarPeserta(List<Peserta> daftarPeserta) {
        this.daftarPeserta = daftarPeserta;
    }

    public List<Kehadiran> getDaftarKehadiran() {
        return daftarKehadiran;
    }

    public void setDaftarKehadiran(List<Kehadiran> daftarKehadiran) {
        this.daftarKehadiran = daftarKehadiran;
    }
    
    
}