// Kasir.java
public class Kasir {
    // Overloading: Proses Pembayaran (dibiarkan kosong)
    public void prosesPembayaran(MetodePembayaran metode) throws PembayaranGagalException { // Overloading (1)
        prosesPembayaran(metode, 0); // Memanggil overloading (2)
    }

    // Overloading: Proses Pembayaran dengan tambahan parameter (opsional)
    public void prosesPembayaran(MetodePembayaran metode, int retryCount) throws PembayaranGagalException { // Overloading (2)
        // Mekanisme utama yang fleksibel menggunakan Polimorfisme
        metode.tampilkanStatus();

        // 1. Cek validasi
        if (!metode.validasi()) {
             // Jika validasi dasar gagal (misalnya uang tunai kurang)
             throw new PembayaranGagalException("Validasi Awal Pembayaran Gagal.");
        }
        
        // 2. Proses transaksi (yang bisa melempar PembayaranGagalException)
        metode.proses(); 
        
        // 3. Jika berhasil mencapai sini:
        System.out.println("Transaksi Berhasil!");
        metode.cetakStruk();
        System.out.println("------------------------------------");
    }
}