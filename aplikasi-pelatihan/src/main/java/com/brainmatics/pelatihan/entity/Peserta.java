package com.brainmatics.pelatihan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity @Table(name = "t_peserta")
public class Peserta {
    
    @Id @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @NotNull @NotEmpty @Email
    @Column(unique = true)
    private String email;
    
    @NotNull @NotEmpty
    private String nama;
    
    @Column(name = "nomor_handphone")
    private String nomorHandphone;
}
