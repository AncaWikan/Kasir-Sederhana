public class Kasir {
    public void prosesPembayaran(MetodePembayaran metode) throws PembayaranGagalException { 
        prosesPembayaran(metode, 0); 
    }

    public void prosesPembayaran(MetodePembayaran metode, int retryCount) throws PembayaranGagalException { 
        metode.tampilkanStatus();

        if (!metode.validasi()) {
             throw new PembayaranGagalException("Validasi Awal Pembayaran Gagal.");
        }
        
        metode.proses(); 
        
        System.out.println("Transaksi Berhasil!");
        metode.cetakStruk();
        System.out.println("------------------------------------");
    }
}