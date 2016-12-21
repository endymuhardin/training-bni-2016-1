insert into t_institusi (id, kode, nama, alamat)
values ('i001', 'I-001', 'Institusi 1', 'Jakarta');

insert into t_peserta (id, nama, email, nomor_handphone, tanggal_lahir)
values ('p001', 'Peserta 001', 'p001@contoh.id', '081234567891', '2011-01-01');

insert into t_peserta (id, nama, email, nomor_handphone, tanggal_lahir)
values ('p002', 'Peserta 002', 'p002@contoh.id', '081234567892', '2012-01-01');

insert into t_peserta (id, nama, email, nomor_handphone, tanggal_lahir)
values ('p003', 'Peserta 003', 'p003@contoh.id', '081234567893', '2013-01-01');

insert into t_materi (id, kode, nama, durasi)
values ('j001', 'J-001', 'Java Fundamental', 40);

insert into t_materi (id, kode, nama, durasi)
values ('j002', 'J-002', 'Java Enterprise', 36);

insert into t_materi (id, kode, nama, durasi)
values ('p001', 'P-001', 'PHP Fundamental', 40);

insert into t_instruktur (id, nama, email, nomor_handphone)
values ('i001', 'Instruktur 001', 'i001@contoh.id', '081234567890');

insert into t_kelas (id, kode, nama, tanggal_mulai, tanggal_selesai, id_instruktur)
values ('k001', 'K-001', 'Kelas Java 001', '2010-01-01', '2010-01-20', 'i001');

insert into t_materi_kelas (id_kelas, id_materi)
values ('k001', 'j001');

insert into t_materi_kelas (id_kelas, id_materi)
values ('k001', 'j002');

insert into t_peserta_kelas (id_kelas, id_peserta)
values ('k001', 'p001');

insert into t_peserta_kelas (id_kelas, id_peserta)
values ('k001', 'p002');

insert into t_kehadiran (id, id_kelas, id_peserta, tanggal_hadir)
values ('kd001', 'k001', 'p001', '2010-01-01');

insert into t_kehadiran (id, id_kelas, id_peserta, tanggal_hadir)
values ('kd002', 'k001', 'p001', '2010-01-03');

insert into t_kehadiran (id, id_kelas, id_peserta, tanggal_hadir)
values ('kd003', 'k001', 'p001', '2010-01-05');