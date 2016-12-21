create table t_institusi (
    id VARCHAR(36), 
    kode VARCHAR(20) NOT NULL,
    nama VARCHAR(255) NOT NULL,
    alamat VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (kode)
);

create table t_peserta (
    id VARCHAR(36), 
    id_institusi VARCHAR(36),
    email VARCHAR(255) NOT NULL,
    nama VARCHAR(255) NOT NULL,
    nomor_handphone VARCHAR(255),
    tanggal_lahir DATE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_institusi) REFERENCES t_institusi(id),
    UNIQUE (email)
);

create table t_materi (
    id VARCHAR(36), 
    PRIMARY KEY (id)
);

create table t_instruktur (
    id VARCHAR(36), 
    PRIMARY KEY (id)
);

create table t_kelas (
    id VARCHAR(36), 
    id_instruktur VARCHAR(36) not null, 
    PRIMARY KEY (id),
    FOREIGN KEY (id_instruktur) REFERENCES t_instruktur(id)
);

create table t_materi_kelas (
    id_kelas VARCHAR(36),
    id_materi VARCHAR(36), 
    PRIMARY KEY (id_kelas, id_materi),
    FOREIGN KEY (id_kelas) REFERENCES t_kelas(id),
    FOREIGN KEY (id_materi) REFERENCES t_materi(id)
);

create table t_peserta_kelas (
    id_kelas VARCHAR(36), 
    id_peserta VARCHAR(36), 
    PRIMARY KEY (id_kelas, id_peserta),
    FOREIGN KEY (id_kelas) REFERENCES t_kelas(id),
    FOREIGN KEY (id_peserta) REFERENCES t_peserta(id)
);

create table t_kehadiran (
    id VARCHAR(36), 
    id_peserta VARCHAR(36) not null,
    id_kelas VARCHAR(36) not null,
    PRIMARY KEY (id),
    FOREIGN KEY (id_peserta) REFERENCES t_peserta(id),
    FOREIGN KEY (id_kelas) REFERENCES t_kelas(id)
);