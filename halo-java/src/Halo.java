package belajar.java;

import coba.Kalkulator;

public class Halo {
  public static void main(String[] x){
    System.out.println("Halo Java");

    Kalkulator k = new Kalkulator();
    Integer hasil = k.tambah(3,2);
    System.out.println("3 + 2 = "+hasil);
  }
}