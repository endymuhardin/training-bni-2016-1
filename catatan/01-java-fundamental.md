# Dasar-dasar Pemrograman Java #

## Kompilasi ##

Mengubah source code menjadi binary.
Perintah : 

```
javac -d <folder tujuan> <file yang mau dicompile>
```

Contoh :

```
javac -d hasil src/*.java
```

## Run ##

Menjalankan kode program Java. Kode program yang bisa dijalankan harus memiliki method `public static void main (String[] x)`.

Perintah:

```
java nama.lengkap.ClassYangMauDijalankan
```

Contoh:

```
java belajar.java.Halo
```

## Classpath ##

Classpath adalah variabel yang menunjukkan lokasi class yang ingin dipakai, baik pada saat compile maupun pada saat run.

Classpath bisa diset dengan beberapa cara:

* menggunakan environment variable

        CLASSPATH=/folder/yang/berisi/class java belajar.java.Halo

* menggunakan opsi `-cp`

        java -cp /folder/yang/berisi/class belajar.java.Halo

Selain menunjuk ke lokasi folder, CLASSPATH juga bisa diarahkan ke file `jar`. Contohnya:

```
javac -cp coba.jar src/*.java
```

CLASSPATH juga bisa menunjuk ke beberapa lokasi sekaligus. Contohnya:

* di Windows

        CLASSPATH=c:/coba.jar;c:/Users/endy/halo-java/hasil java belajar.java.Halo

* di *Nix

        CLASSPATH=/home/endy/coba.jar:/tmp/halo-java/hasil java belajar.java.Halo

## Packing ##

Kode program yang sudah dicompile didistribusikan dalam format `jar`. File `jar` sebetulnya adalah file `zip` biasa, sehingga bisa dibuat dengan aplikasi kompresi apa saja. Walaupun demikian, ada beberapa hal yang harus diperhatikan:

* Algoritma yang digunakan harus `zip`. Tidak boleh `rar` atau yang lainnya.
* File kompresi harus langsung berisi folder package. Tidak boleh ada folder induknya lagi. Misalnya, bila class kita ada dalam package `belajar.java`, maka begitu file `zip` diextract, isinya haruslah folder `belajar`

Untuk mengecek isi file `jar` bisa menggunakan perintah berikut:

```
jar tvf namafile.jar
```

Outputnya seperti ini

```
     0 Mon Dec 05 19:25:58 WIB 2016 coba/
   416 Mon Dec 05 19:25:58 WIB 2016 coba/Kalkulator.class
```