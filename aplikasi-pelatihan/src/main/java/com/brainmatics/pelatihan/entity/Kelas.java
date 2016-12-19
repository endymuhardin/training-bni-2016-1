package com.brainmatics.pelatihan.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
import org.hibernate.annotations.GenericGenerator;

@Entity @Table(name = "t_kelas")
public class Kelas {
    @Id @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
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
}