create table t_peserta (
    id VARCHAR(36), 
    email VARCHAR(255) NOT NULL,
    nama VARCHAR(255) NOT NULL,
    nomor_handphone VARCHAR(255),
    tanggal_lahir DATE NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
);
