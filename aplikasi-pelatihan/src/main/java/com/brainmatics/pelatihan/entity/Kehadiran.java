package com.brainmatics.pelatihan.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;

@Entity @Table(name = "t_kehadiran")
public class Kehadiran {
    @Id @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_kelas")
    private Kelas kelas;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_peserta")
    private Peserta peserta;
    
    @NotNull
    @Column(name = "tanggal_hadir")
    private LocalDate tanggalHadir;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Kelas getKelas() {
        return kelas;
    }

    public void setKelas(Kelas kelas) {
        this.kelas = kelas;
    }

    public Peserta getPeserta() {
        return peserta;
    }

    public void setPeserta(Peserta peserta) {
        this.peserta = peserta;
    }

    public LocalDate getTanggalHadir() {
        return tanggalHadir;
    }

    public void setTanggalHadir(LocalDate tanggalHadir) {
        this.tanggalHadir = tanggalHadir;
    }
    
    
    
}